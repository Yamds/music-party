<template>
    <div class="main-window">
        <DocBlock :type="'info'" title="收藏歌单" icon="fa-solid:user" context="在bili和网易云中收藏的歌曲都在这里~" />
        <ul>
            <li class="video-card" v-for="i, index in favMusicList">
                <span>{{ index + 1 }}.</span>
                <el-icon class="type-icon" v-if="i.type == 'bili'" size="32">
                    <IconifyIcon icon="ri:bilibili-fill" />
                </el-icon>
                <el-icon class="delete-fav-icon" size="32" @click="biliStore.deleteFavMusic(i.musicId || '')">
                    <IconifyIcon icon="fluent:star-dismiss-16-regular" />
                </el-icon>
                <el-image style="width: 112px; height: 63px" :src="i.musicPic" fit="cover" class="cover-img" />
                <div class="video-info">
                    <span class="video-title">{{ i.musicName }}</span>
                    <span class="video-upper">{{ i.musicAuthor }}</span>
                </div>
            </li>
            <li v-if="favMusicList.length == 0">
                <span>什么都没有喔！</span>
            </li>
        </ul>
    </div>
</template>

<script setup lang="ts">
import DocBlock from '@/components/DocBlock.vue';
import { useBiliStore } from '@/store/biliStore';
import { computed, onMounted } from 'vue';

const biliStore = useBiliStore();
const favMusicList = computed(() => biliStore.favMusicList)

onMounted(() => {
    biliStore.getFavMusic()
})

</script>

<style scoped lang="scss">
.video-card {
    display: flex;
    justify-items: center;
    align-items: center;
    margin-bottom: .6rem;

    * {
        transition: all 0.3s;
        margin-right: .8rem;
    }

    .video-info span {
        display: block;
    }
    .cover-img {
        background-color: var(--el-bg-color-overlay);
    }

    .delete-fav-icon {
        opacity: 0;
        width: 0;
        cursor: pointer;
    }
}

.video-card:hover {
    // background-color: black;

    .delete-fav-icon {
        opacity: 1;
        width: 2rem;
    }
}
</style>