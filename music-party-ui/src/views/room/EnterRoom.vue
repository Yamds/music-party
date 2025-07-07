<template>
    <div class="main-window">
        <DocBlock :type="'info'" title="加入房间" icon="fa-solid:user" context="房间列表~" />
        <ul>
            <li class="room-card" v-for="i, index in room_list">
                <div class="info">
                    <span> {{ index+1 }}.</span>
                    <IconifyIcon :icon="i.roomPassword == 'true'? 'material-symbols:lock-outline': 'material-symbols:lock-open-outline-rounded'" />
                    <span class="room-name">{{ i.roomName }}</span>
                    <span class="room-author">by {{ i.roomCreator }}</span>
                    <span>{{ localDate(i.createTime) }}</span>
                </div>
                <div class="enter">
                    <el-button type="primary" plain>加入</el-button>
                    <el-input
                        v-if="i.roomPassword == 'true'"
                        type="password"
                        placeholder="请输入房间密码"
                        show-password
                        v-model="pass"
                        @focus="pass = ''"></el-input>
                </div>
            </li>
        </ul>
    </div>
</template>

<script setup lang="ts">
import DocBlock from '@/components/DocBlock.vue';
import { useRoomStore } from '@/store/roomStore';
import { computed, onMounted, ref } from 'vue';

const roomStore = useRoomStore()
const room_list = computed(() => roomStore.room_list)

const localDate = (timeStamp: string) => {
    const date = new Date(parseInt(timeStamp))
    return date.toLocaleString()
}

const pass = ref('')

onMounted(() => {
    roomStore.getRoomList()
})

</script>

<style scoped lang="scss">
.room-card {
    list-style:none;
    padding: 1rem 1rem 1rem 1rem;
    border-radius: 1rem;
    margin-bottom: .6rem;
    transition: all .3s;
    max-width: 100vh;

    .info {

        display: flex;
        justify-items: center;
        align-items: center;
        margin-bottom: .5rem;
        cursor: pointer;

        *:not(:nth-child(-n+2)) {
            margin-right: 2rem;
        }

        *:not(:nth-child(n+2)) {
            margin-right: 1rem;
        }
    }

    .enter {
        transition: max-height 0.5s;
        // transition: opacity .4s;
        max-height: 0;
        opacity: 0;
        display: flex;

        * {
            margin-right: .5rem;
        }
    }
}

.room-card:hover {
    transform: translateX(1rem);
        background-color: var(--el-bg-color-page);
        box-shadow: .3rem .3rem 2px var(--el-bg-color-overlay);
    .enter {
        max-height: 100px;
        opacity: 1;
    }
}
</style>