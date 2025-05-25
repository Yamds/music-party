<template>
    <div class="main-window">
        <DocBlock :type="'info'" title="网易云音乐" icon="fa-solid:user" context="绑定信息、点歌~" />
        <el-divider content-position="left">网易云音乐用户绑定</el-divider>
        <el-input v-model="bind_name" style="width: 240px" placeholder="可反复绑定哦~" />
    </div>
</template>

<script setup lang="ts">
import DocBlock from '@/components/DocBlock.vue';
import { ref } from 'vue';

const bind_name = ref('')
</script>

<style scoped>
.el-descriptions * {
    background-color: var(--el-bg-color);
}
</style>