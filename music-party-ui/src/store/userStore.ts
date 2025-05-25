import { defineStore } from "pinia";
import { reactive, ref } from "vue";

import { type returnUserInfoInter, type UserInfoInter, newUserInfo } from "@/types/account";
import { httpChangeUserInfo, httpGetUserByLogin, httpIsLogin, httpLogin, httpLogout } from "@/api/userApi"
import { ElMessage } from "element-plus";
import { useRouter } from "vue-router";
// import UserInfo from "@/views/account/UserInfo.vue";

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

    const setIsLogin = async () => {
        httpIsLogin().then(data => {
            if (data.success) {
                isLogin.value = true
                return true
            }
            isLogin.value = false
            return false
        })
    }

    const login = async (username: string, password: string) => {
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

    const logout = async () => {
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

    // 页面刷新，路由筛选那边会调用一次
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

    const changeUserInfo = (username: string, password: string) => {
        if (username == "" && password == "") {
            ElMessage({
                message: "如需修改，请至少填写一项！",
                type: 'warning',
            })
            return
        }
        // 校验用户名
        if (username != "") {
            if (username.length < 3 || username.length > 32) {
                ElMessage({
                    message: "用户名长度在3~32之间！",
                    type: 'warning',
                })
                return
            }
            if (username == userInfo.name) {
                ElMessage({
                    message: "修改的用户名与当前一致！",
                    type: 'warning',
                })
                return
            }
        }
        // 校验密码
        if (password != "") {
            if (password.length < 6 || password.length > 32) {
                ElMessage({
                    message: "密码长度在6~32之间！",
                    type: 'warning',
                })
                return
            }
            const hasLower = /[a-z]/.test(password);  // 小写字母
            const hasUpper = /[A-Z]/.test(password);  // 大写字母
            const hasNumber = /\d/.test(password);    // 数字
            const hasSpecial = /[!@#$%^&*()_+\-=[\]{};':"\\|,.<>/?]/.test(password); // 特殊符号

            const categoryCount = [hasLower, hasUpper, hasNumber, hasSpecial].filter(Boolean).length;

            if (categoryCount < 2) {
                ElMessage({
                    message: "密码需包含大写、小写字母、数字、符号中的至少两项！",
                    type: 'warning',
                })
                return
            }
        }


        httpChangeUserInfo(username, password).then(data => {
            if (data.success) {
                ElMessage({
                    message: data.msg.toString(),
                    type: 'success',
                })
            } else {
                ElMessage({
                    message: data.msg.toString(),
                    type: 'error',
                })
            }
            getUser()
        })
    }

    return {
        isLogin,
        login_loading,
        userInfo,
        login,
        logout,
        getUser,
        changeUserInfo,
    }
})