<template>
    <div :class="['custom-block', props.type]">
        <!-- 显示图标（如果有） -->
        <div class="icon">
            <el-icon v-if="props.icon">
                <IconifyIcon :icon="props.icon" />
            </el-icon>
        </div>
        <div class="context">
            <!-- 显示标题 -->
            <p class="title" v-if="props.title">{{ props.title }}</p>

            <!-- 显示内容 -->
            <div class="content">
                {{ props.context }}
            </div>
        </div>
    </div>
</template>

<script setup lang="ts">
const props = defineProps<DocBlockProps>()

interface DocBlockProps {
    type: 'info' | 'warning' | 'error'
    title: string
    icon?: string
    context: string
}

</script>

<style scoped>
.custom-block {
    border-left: 4px solid;
    padding: 1rem;
    margin: 1rem 0;
    border-radius: 4px;
    min-width: 100px;
    display: flex;
    align-items: center;
    justify-content: flex-start;
}

.icon {
    margin-right: 1em;
    display: flex;
    align-items: center;
}

.icon * {
    font-size: 2em !important;
}

.info {
    background-color: var(--el-color-primary-light-8);
    border-color: var(--el-color-primary);
}

.warning {
    background-color: var(--el-color-warning-light-8);
    border-color: var(--el-color-warning);
}

.error {
    background-color: var(--el-color-error-light-8);
    border-color: var(--el-color-error);
}

.title {
    font-weight: bold;
    margin-top: 0;
}
</style>