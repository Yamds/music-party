<template>
    <div class="header">
        <Collapse class="collapse"></Collapse>
    </div>
</template>

<script setup lang="ts">
import Collapse from './Collapse.vue';

</script>

<style scoped>
.header {
    display: flex;
    justify-content: flex-start;
    align-items: center;
    height: 100%;
}

.collapse {
    margin-left: 10px;
    margin-right: 10px;
}
</style>