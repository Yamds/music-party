import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import { resolve } from 'path'

// https://vite.dev/config/
export default defineConfig({
    plugins: [vue()],
    server: {
        host: '0.0.0.0',    // 设置前端服务器ip地址
        port: 8081,         // 设置前端端口号
        hmr: true,          // 热更新
        open: true,        // 项目启动自动打开浏览器网页
    },
    resolve: {
        alias: [{
            find: '@',
            replacement: resolve(__dirname, 'src')
        }]
    }
})
