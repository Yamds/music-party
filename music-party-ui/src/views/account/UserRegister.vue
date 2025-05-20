<template>
    <div class="main">
        <DocBlock :type="'info'" title="注册" icon="fa-solid:user" context="用户注册" />
        <el-divider content-position="left">注册</el-divider>
        <div class="edit_area">
            <el-form :model="userForm" ref="ruleFormRef" :rules="rules" label-width="auto" style="max-width: 600px">
                <el-form-item label="用户名" prop="username">
                    <el-input v-model="userForm.username" />
                </el-form-item>
                <el-form-item label="密码" prop="password">
                    <el-input v-model="userForm.password" show-password />
                </el-form-item>
                <el-form-item label="确认密码" prop="passwordCheck">
                    <el-input v-model="userForm.passwordCheck" show-password />
                </el-form-item>
                <el-form-item label="">
                    <el-button type="primary" @click="submitForm(ruleFormRef)">注册</el-button>
                </el-form-item>
            </el-form>
        </div>
    </div>
</template>

<script setup lang="ts">
import { ref, reactive } from 'vue';
import DocBlock from '@/components/DocBlock.vue';
import { type FormInstance, type FormRules } from 'element-plus'
import { httpRegister, httpUsernameExist } from '@/api/user';
import { ElMessage } from 'element-plus'

const ruleFormRef = ref<FormInstance>()

const userForm = reactive({
    username: '',
    password: '',
    passwordCheck: '',
})

const checkUsername = (_rule: any, value: any, callback: any) => {
    if (!value)
        return callback(new Error('请输入用户名！'))

    setTimeout(() => {
        httpUsernameExist(value).then(data => {
            if (!data.success) {
                return callback(new Error('已存在该用户名！'))
            }
            if (value.length < 3 || value.length > 32) {
                callback(new Error('用户名长度在3~32之间'))
            } else {
                callback()
            }
        })
    }, 1000)
}

const checkPassword = (_rule: any, value: any, callback: any) => {
    if (value === '') {
        return callback(new Error('请输入密码！'));
    }
    if (value.length < 6 || value.length > 36) {
        return callback(new Error('密码长度在6~32位之间!'));
    }

    const hasLower = /[a-z]/.test(value);  // 小写字母
    const hasUpper = /[A-Z]/.test(value);  // 大写字母
    const hasNumber = /\d/.test(value);    // 数字
    const hasSpecial = /[!@#$%^&*()_+\-=[\]{};':"\\|,.<>/?]/.test(value); // 特殊符号

    const categoryCount = [hasLower, hasUpper, hasNumber, hasSpecial].filter(Boolean).length;

    if (categoryCount < 2) {
        callback(new Error('密码需包含大写、小写字母、数字、符号中的至少两项'));
    } else {
        callback();
    }
};

const checkPassword2 = (_rule: any, value: any, callback: any) => {
    if (value === '') {
        return callback(new Error('请输入密码！'));
    }
    if (value != userForm.password) {
        return callback(new Error('两次密码不一致！'));
    } else {
        callback()
    }
};

const rules = reactive<FormRules<typeof userForm>>({
    username: [{ validator: checkUsername, trigger: 'blur' }],
    password: [{ validator: checkPassword, trigger: 'blur' }],
    passwordCheck: [{ validator: checkPassword2, trigger: 'blur' }],
})

const submitForm = (formEl: FormInstance | undefined) => {
    if (!formEl) return
    formEl.validate((valid) => {
        if (valid) {
            httpRegister(userForm.username, userForm.password).then(data => {
                if (data.success) {
                    ElMessage({
                        message: '注册成功！',
                        type: 'success',
                    })
                } else {
                    ElMessage({
                        message: `${data.msg}`,
                        type: 'error',
                    })
                }
            })
            userForm.password = ''
            userForm.passwordCheck = ''
            userForm.username = ''

        } else {
            ElMessage({
                message: '请先填写字段。',
                type: 'warning',
            })
        }
    })
}

</script>

<style scoped>
.main {
    margin: 2rem;
}
</style>