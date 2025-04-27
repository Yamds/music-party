<template>
    <div class="main">
        <div class="theme">
            <el-tooltip class="box-item" effect="light" content="切换Catppuccin主题" placement="bottom">
                <el-button type="primary" plain @click="change_theme">{{ active_theme }}</el-button>
            </el-tooltip>
        </div>
        <div class="hello-user">
            <span>Hello，{{ userInfo.name }}！</span>
        </div>
    </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from 'vue';
import { useUserStore } from '@/store/userStore';

const store = useUserStore();

const userInfo = computed(() => store.userInfo);

onMounted(() => {
    store.getUser(1)
})

const theme = ["latte", "frappe", "macchiato", "mocha"]
const active_theme = ref("latte")

const change_theme = () => {
    const index = theme.findIndex(value => value == active_theme.value)
    active_theme.value = theme[(index + 1) % 4]
    const html = document.documentElement
    html.className = "theme-" + active_theme.value
}
</script>

<style scoped>
.main {
    display: flex;
    align-items: center;
}

.theme {
    margin-right: 1rem;
}
</style>