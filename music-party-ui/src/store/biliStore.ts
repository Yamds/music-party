import { defineStore } from 'pinia';
import { ref } from 'vue';
import { ElMessage } from 'element-plus';
import type { BiliBackstageReturnInter, BiliFavListInter, BiliFolderInfoInter, BiliFolerListInter, BiliMusicItem, BiliQRcodeLoginReturnInter, BiliQRcodeReturnInter, BiliSearchTypeUserReturnInter } from '@/types/bilibili';
import { httpBindBiliUser, httpBindnameSearch, httpDeleteFavMusic, httpGetFavMusic, httpGetFolderInfo, httpGetFolderList, httpGetQrCode, httpGetSessdata, httpQrCodeLogin, httpSaveFavMusic, httpSaveSessdata } from '@/api/biliApi';
import { useUserStore } from './userStore';


export const useBiliStore = defineStore('bili', () => {
    let isPolling = ref(false); // 是否处于登录轮询的状态
    let sessdata = ref("")
    let biliQrCode = ref<BiliQRcodeReturnInter>({
        qrcode_key: "",
        url: "",
    })
    let bindnameList = ref<BiliSearchTypeUserReturnInter>()
    let login_status = ref('')
    let userFolderList = ref({
        fList: {} as BiliFolerListInter,
        fInfo: [] as BiliFolderInfoInter[],
    })

    let favMusicList = ref([] as BiliMusicItem[])

    let isLoading = ref(false)

    const saveSessdata = async (sessdata: string) => {
        if (sessdata == "") {
            ElMessage.warning("请输入数据再提交~")
        } else {
            await httpSaveSessdata(sessdata).then(data => {
                if (data.success) {
                    getSessdata()
                    ElMessage.success(data.msg)
                } else {
                    ElMessage.error(data.msg)
                }
            })
        }

    }

    const getSessdata = async () => {
        await httpGetSessdata().then(data => {
            if (data.success) {
                const temp = data.data as BiliBackstageReturnInter
                sessdata.value = temp.bili_cookie?.cookieContext as string
            } else {
                ElMessage.error(data.msg)
            }
        })
    }

    const bindBiliuser = async (biliId: string, biliName: string, biliPic: string) => {
        await httpBindBiliUser(biliId, biliName, biliPic).then(data => {
            if (data.success) {
                useUserStore().getUser()
                bindnameList.value = {}
                getFavMusic()
                ElMessage.success("bili绑定成功！")
            } else {
                ElMessage.error(data.msg)
            }
        })
    }

    const getQrCode = async () => {
        await httpGetQrCode().then(data => {
            biliQrCode.value = data.data as BiliQRcodeReturnInter
            qrCodeLogin(biliQrCode.value.qrcode_key)
        })
    }

    const qrCodeLogin = async (qrcode_key: string) => {
        isPolling.value = true
        const pollInterval = setInterval(async () => {
            try {
                const response = await httpQrCodeLogin(qrcode_key);
                if (!response.success) {
                    throw new Error('请求失败');
                }

                const qrcode_data = response.data as BiliQRcodeLoginReturnInter;
                const code = Number(qrcode_data.code) ?? -1;
                const msg = qrcode_data.message || '未知状态';

                switch (code) {
                    case 86101: // 未扫码
                        login_status.value = msg;
                        break;
                    case 86090: // 已扫码未确认
                        login_status.value = msg;
                        break;
                    case 0:     // 登录成功
                        login_status.value = msg;
                        ElMessage.success("登录成功，正在获取SESSDATA！");
                        biliQrCode.value = { qrcode_key: "", url: "" };
                        clearInterval(pollInterval); // 停止轮询
                        isPolling.value = false

                        // 获取cookie
                        const url = qrcode_data.url || "获取失败"
                        const queryString = url.split('?')[1]; // 获取查询的参数部分
                        const param_list = queryString.split('&');
                        for (const param of param_list) {
                            if (param.startsWith('SESSDATA=')) {
                                const sessdata = param.split('=')[1];
                                saveSessdata(sessdata)
                                ElMessage.success("成功获取SESSDATA")
                                break;
                            }
                        }
                        return
                    case 86038: // 二维码过期
                        login_status.value = msg;
                        ElMessage.error(`${msg}，请重新获取二维码！`);
                        biliQrCode.value = { qrcode_key: "", url: "" };
                        clearInterval(pollInterval); // 停止轮询
                        isPolling.value = false
                        return;
                    default:
                        console.warn('未知状态码:', code);
                        biliQrCode.value = { qrcode_key: "", url: "" };
                        isPolling.value = false
                        clearInterval(pollInterval); // 停止轮询
                        return
                }
            } catch (error) {
                console.error('轮询失败:', error);
                clearInterval(pollInterval); // 出错时停止轮询
                ElMessage.error('检查登录状态失败，请重试');
                isPolling.value = false
            }
        }, 1000); // 每秒执行一次

        // 可添加外部停止条件（如超时）
        setTimeout(() => {
            clearInterval(pollInterval);
            ElMessage.warning('登录超时，请重试');
            isPolling.value = false
            return
        }, 180 * 1000); // 180秒超时
    };

    const bindnameSearch = async (bind_name: string) => {
        await httpBindnameSearch(bind_name).then(data => {
            if (data.success) {
                bindnameList.value = data.data
                ElMessage.success("成功获取用户列表")
            }
        })
    }

    const getFolderList = async (update: boolean) => {
        if (!update && userFolderList.value.fInfo.length != 0) {
            return
        }

        userFolderList.value = {
            fList: {} as BiliFolerListInter,
            fInfo: [] as BiliFolderInfoInter[],
        }
        await httpGetFolderList().then(data => {
            if (data.success) {
                userFolderList.value.fList = data.data as BiliFolerListInter
                for (let item of userFolderList.value.fList.list) {
                    let temp = { info: { id: "" } } as BiliFolderInfoInter
                    temp.info.id = item.id
                    temp.has_more = false
                    temp.info.title = item.title
                    temp.medias = []
                    temp.page = 1
                    userFolderList.value.fInfo.push(temp)
                }
                ElMessage.success("成功获取收藏夹")
            } else {
                ElMessage.error(data.msg)
            }
        })
    }

    const getFolderInfo = async (name: string, media_id: string, pn: number) => {
        if (isLoading.value)
            return
        isLoading.value = true
        setTimeout(async () => {
            // console.log("正在请求" + name + " :" + pn)
            await httpGetFolderInfo(media_id, pn).then(data => {
                if (data.success) {
                    // console.log(data)
                    const temp = data.data as BiliFolderInfoInter
                    for (const item of userFolderList.value.fInfo) {
                        if (item?.info.id == temp.info.id) {
                            item.info = temp.info
                            item.page += 1
                            item.has_more = temp.has_more
                            item.medias.push(...temp.medias)
                            return
                        }
                    }
                    // userFolderList.value.fInfo.push(temp)
                    ElMessage.success("成功获取收藏夹详细")
                }
            }).finally(() => {
                isLoading.value = false
            })
        }, 100)

    }

    const changeFavMusic = (music_id: string, music_name: string, music_author: string, music_pic: string) => {
        if (favMusicList.value.some(item => item.musicId === music_id)) {
            deleteFavMusic(music_id)
        } else {
            saveFavMusic(music_id, music_name, music_author, music_pic)
        }
    }

    const saveFavMusic = async (music_id: string, music_name: string, music_author: string, music_pic: string) => {
        await httpSaveFavMusic(music_id, music_name, music_author, music_pic).then(data => {
            if (data.success) {
                favMusicList.value.push({musicId: music_id, musicName: music_name, musicAuthor: music_author, musicPic: music_pic, type: 'bili'})
            }
        })
    }

    const deleteFavMusic = async (music_id: string) => {
        await httpDeleteFavMusic(music_id).then(data => {
            if (data.success) {
                favMusicList.value = favMusicList.value.filter(item => item.musicId != music_id)
            }
        })
    }

    const getFavMusic = async () => {
        await httpGetFavMusic().then(data => {
            if (data.success) {
                const temp = data.data as BiliFavListInter
                favMusicList.value = temp.favMusicList || []
                console.log(favMusicList)
            }
        })
    }


    return {
        biliQrCode,
        sessdata,
        login_status,
        isPolling,
        bindnameList,
        userFolderList,
        isLoading,
        favMusicList,
        saveSessdata,
        getSessdata,
        getQrCode,
        bindnameSearch,
        bindBiliuser,
        getFolderList,
        getFolderInfo,
        changeFavMusic,
        getFavMusic,
        deleteFavMusic,
    }
})