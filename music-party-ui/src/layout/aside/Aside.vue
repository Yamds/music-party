<template>
    <div>
        <!-- logo组件 -->
        <MenuLogo></MenuLogo>
        <!-- 只有一个子菜单打开 -->
        <el-menu :default-active="activeIndex" class="el-menu-vertical-demo" :router="true"
            :collapse="menuStore.isCollapse">
            <menu-item :menuList="menuStore.filteredMenuList"></menu-item>
        </el-menu>
    </div>
</template>

<script setup lang="ts">
import { computed } from 'vue';
import MenuItem from './MenuItem.vue';
import MenuLogo from './MenuLogo.vue';
import { useRoute } from 'vue-router';
import { useMenuStore } from '@/store/menuStore';

const menuStore = useMenuStore()
const route = useRoute()
// 获取激活的菜单
const activeIndex = computed(() => {
    const { path } = route;
    return path;
})


</script>

<style scoped></style>