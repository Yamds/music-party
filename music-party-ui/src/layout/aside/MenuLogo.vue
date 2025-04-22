<template>
    <div class="logo" :class="isCollapse ? '' : 'logo-hide'">
        <img :src="iconImg" alt="">
        <h3>{{ title_text }}</h3>
    </div>
</template>

<script setup lang="ts">
import { ref, computed } from 'vue'
import iconImg from "@/assets/Kumiko_chann.png"
import { menuStore } from '@/store/menu';
const store = menuStore();
const isCollapse = computed(() => {
    return !store.getCollapse;
})

let title_text = ref("音乐派对！")

</script>

<style scoped>
.logo {
    /* width: 200px; */
    height: 60px;
    overflow: hidden;
    /* background-color: aqua; */
    display: flex;
    justify-content: center;
    align-items: center;
    padding: 0 15px 0 15px;
}

.logo>* {
    /* margin-left: 10px; */
    margin: 5px;
}

.logo img {
    width: 38px;
    height: 38px;
}

.logo span {
    display: inline-block;
    font-weight: bold;
    overflow: hidden;
    transition: all 0.5s;
    width: 130px;
    opacity: 1;
    white-space: nowrap !important;
}

.logo-hide span {
    width: 0px;
    opacity: 0;
}
</style>