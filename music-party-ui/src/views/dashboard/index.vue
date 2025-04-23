<template>
    <div class="main">
        <div class="background-icon">
            <el-icon>
                <IconifyIcon icon="material-symbols:kid-star-outline" />
            </el-icon>
        </div>
        <div class="title">
            <span>音乐派对！</span>
            <span>Music！</span>
            <span>Let's Enjoy!</span>
        </div>



    </div>
</template>

<script setup lang="ts">
import '@/styles/theme/catppuccin.scss'

</script>

<style scoped>
span {
    display: block;
    /* display: grid; */
    grid-template-columns: repeat(14, 1fr);
    width: 100%;
    background-color: var(--catppuccin-mauve);
    background-image: linear-gradient(135deg,
            var(--catppuccin-rosewater),
            var(--catppuccin-flamingo),
            var(--catppuccin-pink),
            var(--catppuccin-mauve),
            var(--catppuccin-red),
            var(--catppuccin-maroon),
            var(--catppuccin-peach),
            var(--catppuccin-yellow),
            var(--catppuccin-green),
            var(--catppuccin-teal),
            var(--catppuccin-sky),
            var(--catppuccin-sapphire),
            var(--catppuccin-blue),
            var(--catppuccin-lavender),
            var(--catppuccin-rosewater),
            var(--catppuccin-flamingo),
            var(--catppuccin-pink),
            var(--catppuccin-mauve),
            var(--catppuccin-red),
            var(--catppuccin-maroon),
            var(--catppuccin-peach),
            var(--catppuccin-yellow),
            var(--catppuccin-green),
            var(--catppuccin-teal),
            var(--catppuccin-sky),
            var(--catppuccin-sapphire),
            var(--catppuccin-blue),
            var(--catppuccin-lavender),
            var(--catppuccin-rosewater));
    background-size: 2000% 100%;
    background-position: right;
    background-clip: text;
    -webkit-background-clip: text;
    animation: gradient 60s linear infinite forwards;

    font-size: 8rem;
    font-weight: 1000;
    color: transparent;
}

.main {
    padding: 5rem;
    overflow: hidden;
    height: 100%;
    box-sizing: border-box;
}

.title {
    position: absolute;
    user-select: none;
}

.background-icon {
    float: left;

    transform: translate(0%, 5%);
    font-size: 150rem;
    /* animation: rotate 30s linear infinite; */
}

.background-icon>* {
    animation: rotate 30s linear infinite;
}

.background-icon * {
    color: var(--catppuccin-crust) !important;
}


@keyframes rotate {
    from {
        transform: translate(-50%, -50%) rotate(0deg);
    }

    to {
        transform: translate(-50%, -50%) rotate(360deg);
    }
}

@keyframes gradient {
    100% {
        background-position:
            left;
    }
}
</style>