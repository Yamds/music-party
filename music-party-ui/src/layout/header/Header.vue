<template>
    <div class="header">
        <div class="left">
            <Collapse class="collapse"></Collapse>
        </div>
        <div class="right">
            <RightBar></RightBar>
        </div>
    </div>
</template>

<script setup lang="ts">
import Collapse from './Collapse.vue';
import RightBar from './RightBar.vue';

</script>

<style scoped>
.header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    height: 100%;
}

.collapse {
    margin-left: 10px;
    margin-right: 10px;
}
</style>