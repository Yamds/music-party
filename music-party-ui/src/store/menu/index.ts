import { defineStore } from "pinia";

// 定义一个store
export const menuStore = defineStore('menuStore', {
    state: () => {
        return {
            collapse: false
        }
    },
    getters: {
        getCollapse(): boolean {
            return this.collapse;
        }
    },
    actions: {
        setCollapse(collapse: boolean) {
            this.collapse = collapse
        }
    }
})