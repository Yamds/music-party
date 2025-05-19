import { createApp } from 'vue'
import { createPinia } from 'pinia'
// import './style.css'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import App from './App.vue'
import router from './router/index'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
import piniaPluginPersistedstate from 'pinia-plugin-persistedstate'
import '@/styles/global.scss'
import '@/styles/theme/catppuccin.scss'
import { Icon as IconifyIcon } from '@iconify/vue'

const pinia = createPinia()
const app = createApp(App);

app.use(router)
app.use(ElementPlus)
app.use(pinia)
app.component('IconifyIcon', IconifyIcon) // 全局注册
pinia.use(piniaPluginPersistedstate)

app.mount('#app')

// 全局注册图标文件
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
}