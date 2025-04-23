import { defineStore } from "pinia";
import { ref } from "vue";

// 定义一个store
export const useMenuStore = defineStore('menu', () => {
    let isCollapse = ref(false)
    const setCollapse = () => { isCollapse.value = !isCollapse.value }

    return {
        isCollapse,
        setCollapse
    }
})