import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

export default defineConfig({
  base: '/lab3/', 
  plugins: [vue()],
  server: {
    proxy: {
      '/lab3/api': {
        target: 'http://localhost:8080',
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/lab3\/api/, '/lab3') 
      }
    }
  }
})