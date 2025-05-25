<template>
    <div class="main-window">
        <DocBlock :type="'info'" title="API Config" icon="fa-solid:user" context="网站各种api配置、修改" />
        <el-divider content-position="left">bili SESSDATA状态</el-divider>
        <el-descriptions title="" :column=1 label-width="4rem">
            <el-descriptions-item label="登录状态">
                <span>未登录</span>
            </el-descriptions-item>
            <el-descriptions-item label="SESSDATA">
                <span>{{ sessdata == "" ? "获取失败" : sessdata }}</span>
            </el-descriptions-item>

        </el-descriptions>
        <el-form label-width="auto" style="max-width: 600px">
            <el-form-item label="修改SESSDATA">
                <el-input v-model="input_sessdata" />
            </el-form-item>
            <el-form-item label="">
                <el-button type="primary" @click="store.saveSessdata(input_sessdata.trim())">保存</el-button>
            </el-form-item>
        </el-form>
        <el-divider content-position="left">bili 扫码登录</el-divider>
        <div class="qrcode-login">
            <div>
                <span v-if="qrCodeValue.length > 0">
                    <span>扫码状态: </span>
                    <span>{{ login_status }}...</span>
                </span>
            </div>
            <div>
                <el-button type="primary" @click="store.getQrCode()" :loading="isPolling">获取登录二维码</el-button>
            </div>
            <div v-if="qrCodeValue.length > 0">
                <vue-qrcode :value="qrCodeValue" width="2rem"></vue-qrcode>
            </div>
        </div>
    </div>
</template>

<script setup lang="ts">
import DocBlock from '@/components/DocBlock.vue';
import { computed, onMounted, ref } from 'vue';
import { useBiliStore } from '@/store/biliStore';

const store = useBiliStore();

const sessdata = computed(() => store.sessdata)
const qrCodeValue = computed(() => store.biliQrCode.qrcode_url)
const login_status = computed(() => store.login_status)
const isPolling = computed(() => store.isPolling)
const input_sessdata = ref("")

onMounted(() => {
    store.getSessdata()
})

</script>

<style scoped>
.el-descriptions * {
    background-color: var(--el-bg-color);
}

.qrcode-login>div {
    margin-bottom: 1rem;
}
</style>