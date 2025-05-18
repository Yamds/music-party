<template>
    <div>
        <!-- logo组件 -->
        <MenuLogo></MenuLogo>
        <!-- 只有一个子菜单打开 -->
        <el-menu :default-active="activeIndex" class="el-menu-vertical-demo" :router="true"
            :collapse="menuStore.isCollapse">
            <menu-item :menuList="menuList"></menu-item>
        </el-menu>
    </div>
</template>

<script setup lang="ts">
import { reactive, computed, onMounted } from 'vue';
import MenuItem from './MenuItem.vue';
import MenuLogo from './MenuLogo.vue';
import { useRoute } from 'vue-router';
import { useMenuStore } from '@/store/menuStore';
import { useUserStore } from '@/store/userStore';

const menuStore = useMenuStore()
const userStore = useUserStore()
const route = useRoute()
// 获取激活的菜单
const activeIndex = computed(() => {
    const { path } = route;
    return path;
})

onMounted(() => {
    userStore.getUser()
})

let menuList = reactive([
    {
        path: "/dashboard",
        component: "/dashboard/index",
        name: "dashboard",
        meta: {
            title: "首页",
            icon: "mingcute:music-3-fill",
            permission: [],
            isTop: true
        }
    }, {
        path: "/account",
        component: "Layout",
        name: "account",
        meta: {
            title: "账号管理",
            icon: "ri:user-5-fill",
            permission: [],
        },
        children: [
            {
                path: "/account/info",
                component: "/system/user/UserInfo",
                name: "user-info",
                meta: {
                    title: "账号信息",
                    icon: "mingcute:user-info-fill",
                    permission: ["user:info"],
                }
            }, {
                path: "/account/login",
                component: "/system/user/UserLogin",
                name: "user-login",
                meta: {
                    title: "登录",
                    icon: "streamline:login-1-solid",
                    isLogin: false
                }
            }, {
                path: "/account/register",
                component: "/system/user/UserRegister",
                name: "user-register",
                meta: {
                    title: "注册",
                    icon: "mdi:register",
                    isLogin: false
                }
            }, {
                path: "/logout",
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
        path: "/bind",
        component: "Layout",
        name: "bind",
        meta: {
            title: "账号歌单",
            icon: "eos-icons:role-binding",
            permission: ["user:bind"],
        },
        children: [
            {
                path: "/bind/netease-music",
                component: "/system/bind/NeteaseMusic",
                name: "bind-netease-music",
                meta: {
                    title: "网易云音乐",
                    icon: "tabler:brand-netease-music",
                }
            }, {
                path: "/bind/bilibili",
                component: "/system/bind/Bilibili",
                name: "bind-bilibili",
                meta: {
                    title: "bilibili",
                    icon: "ri:bilibili-fill",
                }
            }, {
                path: "/bind/song-list",
                component: "/system/bind/SongList",
                name: "song-list",
                meta: {
                    title: "收藏歌单",
                    icon: "streamline:music-folder-song",
                }
            }, {
                name: "unbind",
                meta: {
                    title: "清除绑定",
                    icon: "material-symbols:cancel",
                    isAction: true
                }
            },
        ]
    }, {
        path: "/room",
        component: "Layout",
        name: "room",
        meta: {
            title: "房间",
            icon: "fluent:door-16-filled",
            permission: ["user:room"],
        },
        children: [
            {
                path: "/room/add",
                component: "/system/room/AddRoom",
                name: "add-room",
                meta: {
                    title: "新建房间",
                    icon: "material-symbols-light:add-home-rounded",
                }
            }, {
                path: "/room/enter",
                component: "/system/room/EnterRoom",
                name: "enter-room",
                meta: {
                    title: "加入房间",
                    icon: "icon-park-solid:add-user",
                }
            }, {
                name: "leave-room",
                meta: {
                    title: "退出房间",
                    icon: "pepicons-pop:leave",
                    isAction: true
                }
            },
        ]
    }, {
        path: "/backstage",
        component: "/backstage/index",
        name: "backstage",
        meta: {
            title: "后台",
            icon: "fluent:door-16-filled",
            isTop: true,
            permission: ["admin:info"],
        }
    }
])

</script>

<style scoped></style>