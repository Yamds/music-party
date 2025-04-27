import { defineStore } from "pinia";
import { reactive } from "vue";

import { type UserInfoInter } from "@/types/account";
import { httpGetUserById, httpLogin } from "@/api/user"

// 定义一个store
export const useUserStore = defineStore('user', () => {
    const userInfo: UserInfoInter = reactive({
        id: 0,
        name: "",
        password: "",
        bind: {
            netease: 0,
            bilibili: 0
        },
        role: []
    })

    const login = (username: string, password: string) => {
        httpLogin(username, password).then(data => {
            console.log(data);
        })
    }

    const getUser = (userID: Number) => {
        httpGetUserById(userID).then(data => {
            // 更新本地userinfo
            // console.log(data)
            userInfo.id = data.user.id
            userInfo.name = data.user.username
            userInfo.password = data.user.password
            userInfo.bind.bilibili = data.user.biliId
            userInfo.bind.netease = data.user.neteaseId
            userInfo.role = data.role_name
        })
    }

    return {
        userInfo,
        login,
        getUser
    }
})