import { fileURLToPath, URL } from 'url'

import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import vueJsx from '@vitejs/plugin-vue-jsx'
import AutoImport from 'unplugin-auto-import/vite'
import Components from 'unplugin-vue-components/vite'
import { ElementPlusResolver } from 'unplugin-vue-components/resolvers'
import { createStyleImportPlugin, ElementPlusResolve } from 'vite-plugin-style-import'
import { createSvgIconsPlugin } from 'vite-plugin-svg-icons'
import vueSetupExtend from 'vite-plugin-vue-setup-extend'
// https://vitejs.dev/config/
export default defineConfig({
    // base: '/admin/',
    server: {
        host: '0.0.0.0',
        port: 8889,
/*        proxy: {
            // 代理所有 /api 请求到后端
            '/api': {
                target: 'http://like-admin:8086', // 后端地址
                changeOrigin: true, // 允许跨域
                rewrite: (path) => path, // 保持路径不变
                // 可选：设置请求头
                headers: {
                    // 如果你需要传递原始Host信息
                    // 'X-Forwarded-Host': 'localhost:6688'
                }
            }
        }*/

    },
    plugins: [
        vue(),
        vueJsx(),
        AutoImport({
            imports: ['vue', 'vue-router'],
            resolvers: [ElementPlusResolver()],
            eslintrc: {
                enabled: true
            }
        }),
        Components({
            directoryAsNamespace: true,
            resolvers: [ElementPlusResolver()]
        }),
        createStyleImportPlugin({
            resolves: [ElementPlusResolve()]
        }),
        createSvgIconsPlugin({
            // 配置路劲在你的src里的svg存放文件
            iconDirs: [fileURLToPath(new URL('./src/assets/icons', import.meta.url))],
            symbolId: 'local-icon-[dir]-[name]'
        }),
        vueSetupExtend()
    ],
    resolve: {
        alias: {
            '@': fileURLToPath(new URL('./src', import.meta.url))
        }
    },
    build: {
        rollupOptions: {
            manualChunks(id) {
                if (id.includes('node_modules')) {
                    return id.toString().split('node_modules/')[1].split('/')[0].toString()
                }
            }
        }
    }
})
