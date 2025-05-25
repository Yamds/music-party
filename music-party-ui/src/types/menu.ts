export interface MenuInter {
    name: string,
    path?: string,
    meta: {
        title: string,
        icon: string,
        permission: string[],
        isTop?: boolean,
        isLogin?: boolean,
        isAction?: boolean,
    },
    children?: MenuInter[],
}