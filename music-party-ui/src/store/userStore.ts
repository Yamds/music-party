import { defineStore } from "pinia";
import { reactive, ref } from "vue";

import { type returnUserInfoInter, type UserInfoInter, newUserInfo } from "@/types/account";
import { httpGetUserByLogin, httpIsLogin, httpLogin, httpLogout } from "@/api/user"
import { ElMessage } from "element-plus";
import { useRouter } from "vue-router";

// 定义一个store
export const useUserStore = defineStore('user', () => {
    let login_loading = ref(false)
    let isLogin = ref(false)
    const router = useRouter()

    const userInfo: UserInfoInter = reactive({
        id: "",
        name: "",
        password: "",
        bind: {
            netease: 0,
            bilibili: 0,
        },
        role: [],
        permission: [],
    })

    const setIsLogin = () => {
        httpIsLogin().then(data => {
            if (data.success) {
                isLogin.value = true
                return true
            }
            isLogin.value = false
            return false
        })
    }

    const login = (username: string, password: string) => {
        login_loading.value = true
        httpLogin(username, password).then(data => {
            if (data.success) {
                ElMessage({
                    message: data.msg.toString(),
                    type: 'success',
                })
                getUser().then(() => {
                    router.push({ name: "user-info" })
                })

            } else {
                console.log(data)
                ElMessage({
                    message: data.msg.toString(),
                    type: 'error',
                })
                getUser()
            }
        }).catch(() => {
            getUser()
        }).finally(() => {
            login_loading.value = false
        })
    }

    const logout = () => {
        httpLogout().then(data => {
            if (data.success) {
                ElMessage({
                    message: '账号已退出！',
                    type: 'success',
                })
                getUser().then(() => {
                    router.push({ name: "user-login" })
                })
            } else {
                if (data.code == 11011 || data.code == 11013)
                    ElMessage({
                        message: "已处于离线状态，请勿重复退出！",
                        type: 'warning',
                    })
                else
                    ElMessage({
                        message: "异常登出请求！",
                        type: 'error',
                    })
            }
        }).finally(() => {
            getUser()
        })
    }

    const getUser = async (): Promise<void> => {
        return httpGetUserByLogin().then(data => {
            // 成功: 置为get到的user，
            // 失败: 置为空user
            if (data.success) {
                // 更新本地userinfo
                const user_data = data.data as returnUserInfoInter
                userInfo.id = user_data.user.id.toString()
                userInfo.name = user_data.user.username
                userInfo.bind.bilibili = user_data.user.biliId
                userInfo.bind.netease = user_data.user.neteaseId
                userInfo.role = user_data.role_name
                userInfo.permission = user_data.permission_name
            } else {
                const temp = newUserInfo()
                userInfo.id = temp.user.id
                userInfo.name = temp.user.username
                userInfo.bind.bilibili = temp.user.biliId
                userInfo.bind.netease = temp.user.neteaseId
                userInfo.role = temp.role_name
                userInfo.permission = temp.permission_name
            }
        }).finally(() => {
            setIsLogin()
        })
    }

    return {
        isLogin,
        login_loading,
        userInfo,
        login,
        logout,
        getUser
    }
})