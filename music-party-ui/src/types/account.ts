export interface UserInfoInter {
    id: String,
    name: String,
    password: String,
    bind: {
        netease: Number,
        bilibili: Number,
    },
    role: String[],
    permission: String[],
}

export interface returnUserInfoInter {
    user: {
        id: String,
        username: String,
        password: String,
        neteaseId: Number,
        biliId: Number,
    }
    role_name: String[],
    permission_name: String[],
}


// 定义一个函数来创建空的 returnUserInfoInter 对象
export function newUserInfo(): returnUserInfoInter {
    return {
        user: {
            id: '',
            username: '',
            password: '',
            neteaseId: 0,
            biliId: 0,
        },
        role_name: [],
        permission_name: [],
    };
}