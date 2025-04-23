<template>
    <div class="main">
        <DocBlock :type="'info'" title="个人信息" icon="fa-solid:user" context="这是用户个人资料信息" />
        <el-divider content-position="left">个人信息</el-divider>
        <el-descriptions title="" column="1">
            <el-descriptions-item label="用户名">
                {{ userInfo.name }}
                <span v-if="!userInfo.name">未获取</span>
            </el-descriptions-item>
            <el-descriptions-item label="密码">
                {{ userInfo.password }}
                <span v-if="!userInfo.password">未获取</span>
            </el-descriptions-item>
            <el-descriptions-item label="网易云音乐">
                {{ userInfo.bind.netease }}
                <span v-if="!userInfo.bind.netease">未获取</span>
            </el-descriptions-item>
            <el-descriptions-item label="bilibili">
                {{ userInfo.bind.bilibili }}
                <span v-if="!userInfo.bind.bilibili">未获取</span>
            </el-descriptions-item>
            <el-descriptions-item label="role">
                <el-tag size="small" v-if="userInfo.role.length == 0">无角色</el-tag>
                <el-tag size="small" v-for="item in userInfo.role">{{ item }}</el-tag>
            </el-descriptions-item>
        </el-descriptions>
        <el-divider content-position="left">编辑信息</el-divider>
        <div class="edit_area">
            <el-form :model="userInfo" label-width="auto" style="max-width: 600px">
                <el-form-item label="用户名">
                    <el-input v-model="userInfo.name" />
                </el-form-item>
                <el-form-item label="密码">
                    <el-input v-model="userInfo.password" />
                </el-form-item>
                <el-form-item label="">
                    <el-button type="primary" @click="">保存</el-button>
                </el-form-item>
            </el-form>
        </div>
    </div>
</template>

<script setup lang="ts">
import { reactive } from 'vue'
import { type UserInfoInter } from '@/types/account';
import DocBlock from '@/components/DocBlock.vue';

const userInfo: UserInfoInter = reactive({
    name: "",
    password: "",
    bind: {
        netease: "",
        bilibili: ""
    },
    role: []
})




</script>

<style scoped>
.main {
    margin: 2rem;
}

.el-descriptions * {
    background-color: var(--el-bg-color);
}
</style>