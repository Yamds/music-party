<template>
    <el-container class="common-layout">
        <el-aside style="width: auto;">
            <Aside></Aside>
        </el-aside>
        <el-container>
            <el-header>
                <Header></Header>
            </el-header>
            <el-main>
                <router-view></router-view>
            </el-main>
        </el-container>

    </el-container>
    <el-footer>Footer</el-footer>
</template>

<script setup lang="ts">
import Aside from './aside/Aside.vue';
import Header from './header/Header.vue';

</script>

<style scoped>
.common-layout {
    height: 100%;
}

.el-main {
    margin: 0;
    padding: 0;
}
</style>