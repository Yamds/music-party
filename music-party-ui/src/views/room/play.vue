<template>
    <div class="main-window">
        <video ref="videoPlayer" controls class="w-full"></video>
    </div>
</template>

<script setup lang="ts">
import { onBeforeUnmount, onMounted, ref } from 'vue';

import * as dashjs from 'dashjs';

// 引用video元素
const videoPlayer = ref<HTMLVideoElement | null>(null);
// 保存播放器实例
const dashPlayer = ref<dashjs.MediaPlayerClass | null>(null);
// 假设这是你的MPD文件URL，你需要替换为实际的URL
const mpdUrl = '/qwq.mpd';

onMounted(() => {
    // setCookie()
    if (videoPlayer.value) {
        // 初始化dashjs播放器
        dashPlayer.value = dashjs.MediaPlayer().create();

        // 将播放器绑定到video元素
        dashPlayer.value.initialize(videoPlayer.value, mpdUrl, true);
    }
});

onBeforeUnmount(() => {
    if (dashPlayer.value) {
        // 销毁播放器
        dashPlayer.value.reset();
    }
});


</script>

<style scoped></style>