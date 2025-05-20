<template>
    <div class="main">
        <DocBlock :type="'info'" title="个人信息" icon="fa-solid:user" context="用户个人资料信息" />
        <el-divider content-position="left">个人信息</el-divider>
        <el-descriptions title="" :column=1>
            <el-descriptions-item label="用户名">
                {{ userInfo.name }}
                <span v-if="!userInfo.name">未获取</span>
            </el-descriptions-item>
            <el-descriptions-item label="网易云音乐">
                <span v-if="userInfo.bind.netease">{{ userInfo.bind.netease }}</span>
                <span v-if="!userInfo.bind.netease">未获取</span>
            </el-descriptions-item>
            <el-descriptions-item label="bilibili">
                <span v-if="userInfo.bind.bilibili">{{ userInfo.bind.bilibili }}</span>
                <span v-if="!userInfo.bind.bilibili">未获取</span>
            </el-descriptions-item>
            <el-descriptions-item label="身份">
                <el-tag size="small" v-if="userInfo.role != null && userInfo.role.length == 0">无身份</el-tag>
                <el-tag size="small" v-for="item in userInfo.role">{{ item }}</el-tag>
            </el-descriptions-item>
            <el-descriptions-item label="">
                <el-button type="primary" plain @click="store.getUser()">更新</el-button>
            </el-descriptions-item>
        </el-descriptions>
        <el-divider content-position="left">编辑信息</el-divider>
        <div class="edit_area">
            <el-form :model="userInfo" label-width="auto" style="max-width: 600px">
                <el-form-item label="用户名">
                    <el-input v-model="changeInfo.name" />
                </el-form-item>
                <el-form-item label="密码">
                    <el-input v-model="changeInfo.password" />
                </el-form-item>
                <el-form-item label="">
                    <el-button type="primary" @click="store.changeUserInfo()">保存</el-button>
                </el-form-item>
            </el-form>
        </div>
    </div>
</template>

<script setup lang="ts">
import { computed, reactive } from 'vue'
import DocBlock from '@/components/DocBlock.vue';
import { useUserStore } from '@/store/userStore';

const store = useUserStore();
const userInfo = computed(() => store.userInfo);

const changeInfo = reactive({
    name: "",
    password: ""
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