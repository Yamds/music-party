export interface MenuInter {
    name: String,
    meta: {
        title: String,
        icon: String,
        permission: String[],
        isTop?: Boolean,
        isLogin?: Boolean,
        isAction?: Boolean,
    },
    children?: MenuInter[],
}