<template>
    <div class="main">
        <DocBlock :type="'info'" title="哔哩哔哩" icon="fa-solid:user" context="绑定信息、点歌~" />
        <el-divider content-position="left">b站用户绑定</el-divider>
        <el-descriptions title="" :column=1>
            <el-descriptions-item label="用户名">
                <span>尚未绑定</span>
            </el-descriptions-item>
            <el-descriptions-item label="bid">
                <span>尚未绑定</span>
            </el-descriptions-item>
        </el-descriptions>
        <el-input v-model="bind_name" style="width: 240px" placeholder="请输入b站用户名: 可反复绑定哦~" />
    </div>
</template>

<script setup lang="ts">
import DocBlock from '@/components/DocBlock.vue';
import { ref } from 'vue';

const bind_name = ref('')
</script>

<style scoped>
.main {
    margin: 2rem;
}
.el-descriptions * {
    background-color: var(--el-bg-color);
}
</style>