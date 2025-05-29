<template>
    <div class="main-window">
        <DocBlock :type="'info'" title="哔哩哔哩" icon="fa-solid:user" context="绑定信息、点歌~" />
        <el-divider content-position="left">b站用户绑定</el-divider>
        <el-descriptions title="" :column=1>
            <el-descriptions-item label="">
                <span v-if="userInfo.bind.bilibili == null">获取失败</span>
                <span v-else-if="userInfo.bind.bilibili == -1">尚未绑定</span>
                <span v-else="!userInfo.bind.bilibili"><img :src="userBindName.biliInfo[2]" alt=""></span>
            </el-descriptions-item>
            <el-descriptions-item label="用户名">
                <span v-if="userInfo.bind.bilibili == null">获取失败</span>
                <span v-else-if="userInfo.bind.bilibili == -1">尚未绑定</span>
                <span v-else="!userInfo.bind.bilibili">{{ userBindName.biliInfo[1] }}</span>
            </el-descriptions-item>
            <el-descriptions-item label="bid">
                <span v-if="userInfo.bind.bilibili == null">获取失败</span>
                <span v-else-if="userInfo.bind.bilibili == -1">尚未绑定</span>
                <span v-else="!userInfo.bind.bilibili">{{ userInfo.bind.bilibili }}</span>
            </el-descriptions-item>
        </el-descriptions>
        <div class="username-search">
            <el-input v-model="bind_name" style="width: 240px" placeholder="请输入b站用户名: 可反复绑定哦~" />
            <el-button type="primary" @click="biliStore.bindnameSearch(bind_name)">搜索</el-button>
            <div class="username-list">
                <el-tag v-for="item in biliStore.bindnameList?.result" size="large"
                    @click="biliStore.bindBiliuser(item.mid, item.uname, item.upic)">
                    {{ item.uname }}
                </el-tag>
            </div>
        </div>
        <el-divider content-position="left">绑定用户收藏夹</el-divider>
        <el-button @click="biliStore.getFolderList(userStore.userInfo.id)">getFolderList</el-button>
        <div class="folder">
            <el-collapse accordion :expand-icon-position="'left'" v-model="active_page">
                <el-collapse-item v-for="item in biliStore.userFolderList?.fInfo" :title="item.info.title"
                    class="infinite-list" :name="item.info.title">
                    <ul infinite-scroll-immediate="true"
                        :infinite-scroll-disabled="!(item.page == 1 || (item.has_more && active_page == item.info.title))"
                        v-infinite-scroll="() => biliStore.getFolderInfo(item.info.title, item.info.id, item.page)"
                        infinite-scroll-distance="0">
                        <li v-for="i, index in item.medias" :key="i.id" class="infinite-list-item">
                            <div class="video_list">
                                <span>{{ index+1 }}.</span>
                                <img class="big_img" style="height: 0" :src="i.cover" referrerPolicy="no-referrer" alt="">
                                <img class="cover_img" :src="i.cover" referrerPolicy="no-referrer" alt="">
                                <div class="video_info">
                                    <span class="video_title">{{ i.title }}</span>
                                    <div class="other_info">
                                        <span class="video_upper">{{ i.upper.name }}</span>
                                        <span class="video_duration">
                                            {{ Math.floor(i.duration/3600)==0?"" : Math.floor(i.duration/3600).toString().padStart(2, "0") + ":"}}
                                            {{ Math.floor(i.duration%3600/60).toString().padStart(2, "0") }}:
                                            {{ (i.duration%60).toString().padStart(2, "0") }}
                                        </span>
                                    </div>
                                </div>
                            </div>
                        </li>
                        <li :class="[!biliStore.isLoading && item.has_more ? 'noloading' : '', 'loading']"
                            v-if="item.has_more">
                            <el-skeleton :rows="1" />
                        </li>
                    </ul>
                    <!-- <span>正在加载...</span> -->
                    <span v-if="!item.has_more">没有了QAQ</span>
                </el-collapse-item>
            </el-collapse>
        </div>
    </div>
</template>

<script setup lang="ts">
import DocBlock from '@/components/DocBlock.vue';
import { useBiliStore } from '@/store/biliStore';
import { useUserStore } from '@/store/userStore';
import { computed, ref } from 'vue';

const biliStore = useBiliStore()
const userStore = useUserStore()

const bind_name = ref('')

const userInfo = computed(() => userStore.userInfo)
const userBindName = computed(() => userStore.userBindName)

const active_page = ref("")

</script>

<style scoped lang="scss">
.el-descriptions * {
    background-color: var(--el-bg-color);
}

.username-search>* {
    margin-right: 1rem;
}

.username-list .el-tag {
    margin: 0.25rem;
    cursor: pointer;
}

.infinite-list {
    max-height: 300px;
    width: 100vh;
    overflow: auto;
}

.noloading {
    opacity: 0;
}

.loading {
    transition: all .5s;
}

.folder {
    position: relative;
}

/* ::v-deep .el-collapse-item.is-active */
::v-deep .el-collapse-item.is-active>*:first-child span {
    color: var(--el-color-primary) !important;
}
::v-deep .el-collapse-item.is-active>*:first-child {
    width: 100vh;
    color: var(--el-color-primary) !important;
    border-color: var(--el-color-primary) !important;
    position: absolute;
    z-index: 1;
    transition: all 0.3s;
}

::v-deep .el-collapse .el-collapse-item.is-active>*:last-child {
    transform: translateY(3rem);
}

::v-deep .el-collapse .el-collapse-item.is-active>*:last-child li {
    list-style: none;
}

.video_list {
    position: relative;
    display: flex;
    align-items: center;
    padding: .4rem .3rem .4rem 1rem;
    cursor: pointer;
    border-radius: 5px;
    transform: translatex(-2rem);
    transition: all .3s;

    .cover_img {
        width: 2.2rem;
        height: 2.2rem;
        margin-right: 0.5rem;
        object-fit: cover;
        transition: all .3s;
    }

    .big_img {
        margin-right: 0.5rem;
        transition: all .4s;
        border-radius: 10px;
    }

    .video_title {
        transition: all .3s;
    }

    .other_info {
        opacity: 0;
        position: absolute;
        transition: all .3s;
    }
    
    .video_duration {
        margin-left: 2rem;
    }
}

.video_list:hover {
    background-color: var(--el-bg-color-page);
    padding: 1rem .3rem 1rem 1rem;

    * {
        color: var(--el-color-primary) !important;
    }

    .video

    .video_upper {
        opacity: 1 !important;
    }

    .cover_img {
        /* height:0 */
        width: 0;
        opacity: 0;
        width: 0;
    }

    .big_img {
        height: 6rem !important;
    }

    .other_info {
        opacity: 1;
        position: relative;
    }
}
</style>