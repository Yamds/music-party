import type { MenuInter } from "@/types/menu";
import { defineStore } from "pinia";
import { computed, reactive, ref } from "vue";

import { usePermissionStore } from "./permissionStore";

// 定义一个store
export const useMenuStore = defineStore('menu', () => {
    let isCollapse = ref(false)
    const setCollapse = () => { isCollapse.value = !isCollapse.value }

    const filteredMenuList = computed(() => usePermissionStore().filterMenu(menuList))

    let menuList = reactive<MenuInter[]>([
        {
            name: "dashboard",
            meta: {
                title: "首页",
                icon: "mingcute:music-3-fill",
                permission: [],
                isTop: true
            }
        }, {
            name: "account",
            meta: {
                title: "账号管理",
                icon: "ri:user-5-fill",
                permission: [],
            },
            children: [
                {
                    name: "user-info",
                    meta: {
                        title: "账号信息",
                        icon: "mingcute:user-info-fill",
                        permission: ["user:info"],
                    }
                }, {
                    name: "user-login",
                    meta: {
                        title: "登录",
                        icon: "streamline:login-1-solid",
                        isLogin: false,
                        permission: [],
                    }
                }, {
                    name: "user-register",
                    meta: {
                        title: "注册",
                        icon: "mdi:register",
                        isLogin: false,
                        permission: [],
                    }
                }, {
                    name: "logout",
                    meta: {
                        title: "退出",
                        icon: "solar:logout-2-bold",
                        permission: ["user:logout"],
                        isAction: true,
                    }
                }
            ]
        }, {
            name: "bind",
            meta: {
                title: "账号歌单",
                icon: "eos-icons:role-binding",
                permission: ["user:bind"],
            },
            children: [
                {
                    name: "bind-netease-music",
                    meta: {
                        title: "网易云音乐",
                        icon: "tabler:brand-netease-music",
                        permission: [],
                    }
                }, {
                    name: "bind-bilibili",
                    meta: {
                        title: "bilibili",
                        icon: "ri:bilibili-fill",
                        permission: [],
                    }
                }, {
                    name: "bind-song-list",
                    meta: {
                        title: "收藏歌单",
                        icon: "streamline:music-folder-song",
                        permission: [],
                    }
                }, {
                    name: "unbind",
                    meta: {
                        title: "清除绑定",
                        icon: "material-symbols:cancel",
                        permission: [],
                        isAction: true,
                    }
                },
            ]
        }, {
            name: "room",
            meta: {
                title: "房间",
                icon: "fluent:door-16-filled",
                permission: ["user:room"],
            },
            children: [
                {
                    name: "add-room",
                    meta: {
                        title: "新建房间",
                        icon: "material-symbols-light:add-home-rounded",
                        permission: [],
                    }
                }, {
                    name: "enter-room",
                    meta: {
                        title: "加入房间",
                        icon: "icon-park-solid:add-user",
                        permission: [],
                    }
                }, {
                    name: "leave-room",
                    meta: {
                        title: "退出房间",
                        icon: "pepicons-pop:leave",
                        permission: [],
                        isAction: true,
                    }
                },
            ]
        }, {
            name: "backstage",
            meta: {
                title: "后台",
                icon: "fluent:door-16-filled",
                isTop: true,
                permission: ["admin:info"],
            }
        }
    ])

    return {
        filteredMenuList,
        isCollapse,
        setCollapse
    }
})