import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import { resolve } from 'path'
import vueDevTools from 'vite-plugin-vue-devtools'

// https://vite.dev/config/
export default defineConfig({
    plugins: [
        vue(),
        vueDevTools()
    ],
    server: {
        host: '0.0.0.0',    // 设置前端服务器ip地址
        port: 20721,         // 设置前端端口号
        hmr: true,          // 热更新
        open: false,        // 项目启动自动打开浏览器网页
    },
    resolve: {
        alias: [{
            find: '@',
            replacement: resolve(__dirname, 'src')
        }]
    },
})