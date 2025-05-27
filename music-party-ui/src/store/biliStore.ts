import { defineStore } from 'pinia';
import { ref } from 'vue';
import { ElMessage } from 'element-plus';
import type { BiliBackstageReturnInter, BiliQRcodeLoginReturnInter, BiliQRcodeReturnInter, BiliSearchTypeUserReturnInter } from '@/types/bilibili';
import { httpBindBiliUser, httpBindnameSearch, httpGetQrCode, httpGetSessdata, httpQrCodeLogin, httpSaveSessdata } from '@/api/biliApi';
import { useUserStore } from './userStore';


export const useBiliStore = defineStore('bili', () => {
    let isPolling = ref(false); // 是否处于登录轮询的状态
    let sessdata = ref("")
    let biliQrCode = ref<BiliQRcodeReturnInter>({
        qrcode_key: "",
        url: "",
    })
    let bindnameList = ref<BiliSearchTypeUserReturnInter>();
    let login_status = ref('')

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
                sessdata.value = temp.bili_config?.cookieContext as string
            } else {
                ElMessage.error(data.msg)
            }
        })
    }

    const bindBiliuser = async (biliId: string, biliName: string, biliPic: string) => {
        const userId = useUserStore().userInfo.id
        await httpBindBiliUser(userId, biliId, biliName, biliPic).then(data => {
            if (data.success) {
                useUserStore().getUser()
                bindnameList.value = {}
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
                console.log(bindnameList.value)
                ElMessage.success("成功获取用户列表")
            }
        })
    }

    return {
        biliQrCode,
        sessdata,
        login_status,
        isPolling,
        bindnameList,
        saveSessdata,
        getSessdata,
        getQrCode,
        bindnameSearch,
        bindBiliuser,
    }
})