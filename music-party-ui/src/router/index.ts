import { createRouter, createWebHistory, type RouteRecordRaw } from 'vue-router';

import { usePermissionStore } from '@/store/permissionStore';
import Layout from '@/layout/index.vue'
import { useUserStore } from '@/store/userStore';

const routes: Array<RouteRecordRaw> = [
    {
        path: '/',
        name: 'main',
        component: Layout,
        redirect: '/dashboard',
        children: [
            {
                path: '/dashboard',
                component: () => import('@/views/dashboard/index.vue'),
                name: 'dashboard',
                meta: {
                    title: '首页',
                    icon: 'mingcute:music-3-fill',
                    permission: [],
                    isTop: true,
                }
            }
        ]
    }, {
        path: '/account',
        name: 'account',
        component: Layout,
        redirect: '/account',
        children: [
            {
                path: '/account/info',
                component: () => import('@/views/account/UserInfo.vue'),
                name: 'user-info',
                meta: {
                    title: '账号信息',
                    icon: 'mingcute:user-info-fill',
                    permission: ["user:info"],
                }
            }, {
                path: '/account/login',
                component: () => import('@/views/account/UserLogin.vue'),
                name: 'user-login',
                meta: {
                    title: '登录',
                    icon: 'streamline:login-1-solid',
                    permission: [],
                    isLogin: false,
                }
            }, {
                path: '/account/register',
                component: () => import('@/views/account/UserRegister.vue'),
                name: 'user-register',
                meta: {
                    title: '注册',
                    icon: 'mdi:register',
                    permission: [],
                    isLogin: false,
                }
            },
        ]
    }, {
        path: '/bind',
        name: 'bind',
        component: Layout,
        meta: { permission: ["user:bind"] },
        redirect: '/bind',
        children: [
            {
                path: '/bind/netease-music',
                component: () => import('@/views/bind/NeteaseMusic.vue'),
                name: 'bind-netease-music',
                meta: {
                    title: '绑定网易云音乐',
                    icon: 'tabler:brand-netease-music'
                }
            }, {
                path: '/bind/bilibili',
                component: () => import('@/views/bind/Bilibili.vue'),
                name: 'bind-bilibili',
                meta: {
                    title: 'bilibili',
                    icon: 'ri:bilibili-fill'
                }
            }, {
                path: '/bind/song-list',
                component: () => import('@/views/bind/SongList.vue'),
                name: 'bind-song-list',
                meta: {
                    title: '歌单收藏',
                    icon: 'streamline:music-folder-song',
                    permission: [],
                    isAction: true,
                }
            }

        ]
    }, {
        path: '/room',
        name: 'room',
        component: Layout,
        meta: { permission: ["user:room"] },
        redirect: '/room',
        children: [
            {
                path: '/room/add',
                component: () => import('@/views/room/AddRoom.vue'),
                name: 'add-room',
                meta: {
                    title: '新建房间',
                    icon: 'material-symbols-light:add-home-rounded',
                    permission: ["user:room"],
                }
            }, {
                path: '/room/enter',
                component: () => import('@/views/room/EnterRoom.vue'),
                name: 'enter-room',
                meta: {
                    title: '加入房间',
                    icon: 'icon-park-solid:add-user',
                    permission: ["user:room"],
                }
            }
        ]
    }, {
        path: '/backstage',
        name: 'backstage',
        component: Layout,
        meta: { permission: ["master:backstage"] },
        redirect: '/backstage',
        children: [
            {
                path: '/backstage/user-manager',
                component: () => import('@/views/backstage/UserManager.vue'),
                name: 'user-manager',
                meta: {
                    title: '用户管理',
                    icon: 'material-symbols-light:add-home-rounded'
                }
            },
            {
                path: '/backstage/api-config',
                component: () => import('@/views/backstage/ApiConfig.vue'),
                name: 'api-config',
                meta: {
                    title: 'api配置',
                    icon: 'material-symbols-light:add-home-rounded'
                }
            }

        ]

    }

]



const router = createRouter({
    history: createWebHistory(),
    routes
})

router.beforeEach(async (to) => {
    const userStore = useUserStore()
    const permStore = usePermissionStore()

    // 条件: 已登录&组件需要未登录
    // 比如login，登录后就不需要login了，直接跳回首页
    if (to.meta.isLogin === false && userStore.isLogin) {
        return '/' // 跳转到首页
    }

    // 进入没权限的，跳转首页
    if (!await permStore.isRouteVisible(to)) {

        return '/'
    }
    return true
})

export default router