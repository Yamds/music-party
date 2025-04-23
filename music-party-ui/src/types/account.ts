export interface UserInfoInter {
    name: string,
    password: string,
    bind: {
        netease: string,
        bilibili: string,
    },
    role: string[],
}