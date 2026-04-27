import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

export default defineConfig({
  base: '/lab2/', 
  plugins: [vue()],
  server: {
    proxy: {
      '/lab2/api': {
        target: 'http://localhost:8080', 
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/lab2\/api/, '/lab2') 
      }
    }
  }
})