<template>
    <div class="main-window">
        <DocBlock :type="'info'" title="新建房间" icon="fa-solid:user" context="请输入房间名~" />
        <div class="room-add">
            <el-input v-model="room_name" placeholder="请输入房间名" />
            <el-input v-model="room_password" placeholder="请输入房间密码(可选)" />
            <el-button type="primary" @click="roomStore.addRoom(room_name.trim(), room_password.trim())">创建</el-button>
        </div>
    </div>
</template>

<script setup lang="ts">
import DocBlock from '@/components/DocBlock.vue';
import { ref } from 'vue';
import { useRoomStore } from '@/store/roomStore';

const roomStore = useRoomStore()

const room_name = ref('')
const room_password = ref('')

</script>

<style scoped>
.room-add {
    max-width: 600px;

    * {
        margin-bottom: 1rem;
    }
}
</style>