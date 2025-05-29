export interface UserInfoInter {
    id: string,
    name: string,
    password: string,
    bind: {
        netease: number,
        bilibili: number,
    },
    role: string[],
    permission: string[],
}

export interface returnUserInfoInter {
    user: {
        id: string,
        username: string,
        password: string,
        neteaseId: number,
        biliId: number,
    }
    role_name: string[],
    permission_name: string[],
    bili_info: string[],
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
        bili_info: [],
    };
}