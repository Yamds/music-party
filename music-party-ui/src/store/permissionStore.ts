import { defineStore } from "pinia";
import { computed } from "vue";
import { useUserStore } from "./userStore";
import type { MenuInter } from "@/types/menu";
import type { RouteLocationNormalized } from "vue-router";

// 定义一个store
export const usePermissionStore = defineStore('permission', () => {
    const store = useUserStore()

    // 根据用户store 拿到权限list
    let permission_list = computed(() => store.userInfo.permission)

    // 权限检查方法 查看某一项menu 需要哪些权限
    const hasMenuPermission = (menu: MenuInter) => {
        const perms = menu.meta?.permission || []
        if (perms.length === 0) return true // 没设权限的默认可见
        return perms.some(p => permission_list.value.includes(p))
    }

    // 是否应该显示（包括登录状态和权限）
    const isMenuVisible = (menu: MenuInter) => {
        if (menu.meta?.isLogin === false && store.isLogin) return false // 登录后不显示 isLogin:false 的页面
        if (!hasMenuPermission(menu)) return false
        return true
    }

    // 递归过滤菜单
    const filterMenu = (menus: MenuInter[]): MenuInter[] => {
        return menus
            .filter(menu => isMenuVisible(menu))
            .map(menu => {
                if (menu.children) {
                    const filteredChildren = filterMenu(menu.children)
                    return { ...menu, children: filteredChildren }
                }
                return menu
            })
    }

    // 路由权限过滤
    // 逻辑判断基本与上方菜单一致
    const hasRoutePermission = async (route_permission: string[]): Promise<boolean> => {
        if (route_permission.length === 0) return true // 没设权限的默认可见
        await store.getUser()
        return route_permission.some(p => permission_list.value.includes(p))
    }

    const isRouteVisible = async (route: RouteLocationNormalized) => {
        if (route.meta?.isLogin === false && useUserStore().isLogin) return false
        if (await hasRoutePermission(route.meta?.permission as string[] || [])) {
            return true
        }
        return false
    }

    return {
        isRouteVisible,
        filterMenu,
    }
})