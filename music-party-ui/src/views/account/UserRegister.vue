<template>
    <div class="main">
        <DocBlock :type="'info'" title="注册" icon="fa-solid:user" context="用户注册" />
        <el-divider content-position="left">注册</el-divider>
        <div class="edit_area">
            <el-form :model="userInfo" label-width="auto" style="max-width: 600px">
                <el-form-item label="用户名">
                    <el-input v-model="userInfo.name" />
                </el-form-item>
                <el-form-item label="密码">
                    <el-input v-model="userInfo.password" />
                </el-form-item>
                <el-form-item label="确认密码">
                    <el-input v-model="userPasswordRepeat" />
                </el-form-item>
                <el-form-item label="">
                    <el-button type="primary" @click="">注册</el-button>
                </el-form-item>
            </el-form>
        </div>
    </div>
</template>

<script setup lang="ts">
import { ref, reactive } from 'vue';
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
const userPasswordRepeat = ref("")

</script>

<style scoped>
.main {
    margin: 2rem;
}
</style>