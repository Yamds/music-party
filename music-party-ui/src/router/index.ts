import { createRouter, createWebHistory, type RouteRecordRaw } from "vue-router";
import Layout from '@/layout/index.vue'

const routes: Array<RouteRecordRaw> = [
    {
        path: '/',
        component: Layout,
        redirect: '/dashboard',
        children: [
            {
                path: '/dashboard',
                component: () => import('@/views/dashboard/index.vue'),
                name: 'dashboard',
                meta: {
                    title: '首页',
                    icon: 'mingcute:music-3-fill'
                }
            }
        ]
    }, {
        path: '/account',
        component: Layout,
        redirect: '/account',
        children: [
            {
                path: '/account/info',
                component: () => import('@/views/account/UserInfo.vue'),
                name: 'user-info',
                meta: {
                    title: '账号信息',
                    icon: 'mingcute:user-info-fill'
                }
            }, {
                path: '/account/login',
                component: () => import('@/views/account/UserLogin.vue'),
                name: 'login',
                meta: {
                    title: '登录',
                    icon: 'streamline:login-1-solid'
                }
            }, {
                path: '/account/register',
                component: () => import('@/views/account/UserRegister.vue'),
                name: 'register',
                meta: {
                    title: '注册',
                    icon: 'mdi:register'
                }
            },
        ]
    }, {
        path: '/bind',
        component: Layout,
        redirect: '/bind',
        children: [
            {
                path: '/bind/netease-music',
                component: () => import('@/views/bind/NeteaseMusic.vue'),
                name: 'netease-music',
                meta: {
                    title: '绑定网易云音乐',
                    icon: 'tabler:brand-netease-music'
                }
            }, {
                path: '/bind/bilibili',
                component: () => import('@/views/bind/Bilibili.vue'),
                name: 'bilibili',
                meta: {
                    title: 'bilibili',
                    icon: 'ri:bilibili-fill'
                }
            }, {
                path: '/bind/song-list',
                component: () => import('@/views/bind/SongList.vue'),
                name: 'song-list',
                meta: {
                    title: '歌单收藏',
                    icon: 'streamline:music-folder-song'
                }
            }

        ]
    }, {
        path: '/room',
        component: Layout,
        redirect: '/room',
        children: [
            {
                path: '/room/add',
                component: () => import('@/views/room/AddRoom.vue'),
                name: 'add-room',
                meta: {
                    title: '新建房间',
                    icon: 'material-symbols-light:add-home-rounded'
                }
            }, {
                path: '/room/enter',
                component: () => import('@/views/room/EnterRoom.vue'),
                name: 'enter-room',
                meta: {
                    title: '加入房间',
                    icon: 'icon-park-solid:add-user'
                }
            }

        ]
    }, {
        path: '/backstage',
        component: Layout,
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
            }

        ]

    }

]

const router = createRouter({
    history: createWebHistory(),
    routes
})

export default router