<template>
    <div class="main-window">
        <DocBlock :type="'info'" title="哔哩哔哩" icon="fa-solid:user" context="绑定信息、点歌~" />
        <el-divider content-position="left">b站用户绑定</el-divider>
        <el-descriptions title="" :column=1>
            <el-descriptions-item label="用户名">
                <span v-if="userInfo.bind.bilibili == null">获取失败</span>
                <span v-else-if="userInfo.bind.bilibili == -1">尚未绑定</span>
                <span v-else="!userInfo.bind.bilibili">{{ userBindName.biliName }}</span>
            </el-descriptions-item>
            <el-descriptions-item label="bid">
                <span v-if="userInfo.bind.bilibili == null">获取失败</span>
                <span v-else-if="userInfo.bind.bilibili == -1">尚未绑定</span>
                <span v-else="!userInfo.bind.bilibili">{{ userInfo.bind.bilibili }}</span>
            </el-descriptions-item>
        </el-descriptions>
        <div class="username-search">
            <el-input v-model="bind_name" style="width: 240px" placeholder="请输入b站用户名: 可反复绑定哦~" />
            <el-button type="primary" @click="biliStore.bindnameSearch(bind_name)">搜索</el-button>
            <div class="username-list">
                <el-tag v-for="item in biliStore.bindnameList?.result" size="large" @click="biliStore.bindBiliuser(item.mid, item.uname, item.upic)">
                    {{ item.uname }}
                </el-tag>
            </div>
        </div>
        <el-divider content-position="left">绑定用户收藏夹</el-divider>
        <div class="folder">
            <el-collapse accordion expand-icon-position="left">
                <el-collapse-item title="qwq">awa</el-collapse-item>
            </el-collapse>
        </div>
    </div>
</template>

<script setup lang="ts">
import DocBlock from '@/components/DocBlock.vue';
// import { type CollapseIconPositionType } from 'element-plus'
import { useBiliStore } from '@/store/biliStore';
import { useUserStore } from '@/store/userStore';
import { computed, ref } from 'vue';

const biliStore = useBiliStore()
const userStore = useUserStore()

const bind_name = ref('')

const userInfo = computed(() => userStore.userInfo)
const userBindName = computed(() => userStore.userBindName)

</script>

<style scoped>
.el-descriptions * {
    background-color: var(--el-bg-color);
}

.username-search>* {
    margin-right: 1rem;
}

.username-list .el-tag {
    margin: 0.25rem;
    cursor: pointer;
}
</style>