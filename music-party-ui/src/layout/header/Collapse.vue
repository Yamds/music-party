<template>
    <div>
        <el-icon @click="store.setCollapse" style="cursor: pointer;">
            <component :is="store.isCollapse ? ArrowRightBold : ArrowLeftBold"></component>
        </el-icon>
    </div>
</template>

<script setup lang="ts">
import { useMenuStore } from '@/store/menuStore';
import { ArrowLeftBold, ArrowRightBold } from '@element-plus/icons-vue';
// 获取store
const store = useMenuStore();

</script>

<style scoped></style>