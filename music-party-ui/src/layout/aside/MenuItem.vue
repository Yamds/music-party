<template>
    <template v-for="menu in menuList" :key="menu.path">
        <el-sub-menu v-if="menu.children && menu.children.length > 0" :index="menu.path" class="menu-main-item">
            <template #title>
                <el-icon>
                    <IconifyIcon :icon="menu.meta.icon" />
                </el-icon>
                <span>{{ menu.meta.title }}</span>
            </template>
            <menu-item :menu-list="menu.children"></menu-item>
        </el-sub-menu>

        <el-menu-item v-else-if="!menu.meta.isAction" :index="menu.path"
            :class="menu.meta.isTop ? 'menu-sub-item2' : 'menu-sub-item1'">
            <el-icon>
                <IconifyIcon :icon="menu.meta.icon" />
            </el-icon>
            <template #title>
                <span>{{ menu.meta.title }}</span>
            </template>
        </el-menu-item>

        <!-- 只触发不跳转(退出，清除) -->
        <el-menu-item v-else class="action-menu-item" @click="handleMenuAction(menu.name)"
            :class="menu.meta.isTop ? 'menu-sub-item2' : 'menu-sub-item1'">
            <el-icon>
                <IconifyIcon :icon="menu.meta.icon" />
            </el-icon>
            <span>{{ menu.meta.title }}</span>
        </el-menu-item>
    </template>
</template>

<script setup lang="ts">
import { useUserStore } from '@/store/userStore';

const store = useUserStore()

defineProps(["menuList"])

const handleMenuAction = (action: string) => {
    if (action == "logout") {
        store.logout()
    }
}

</script>

<style scoped></style>