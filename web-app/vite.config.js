import { fileURLToPath, URL } from "url";
import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [vue()],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url)),
      '@assets': fileURLToPath(new URL('./src/assets', import.meta.url)),
      '@images': fileURLToPath(new URL('./src/assets/img', import.meta.url)),
      '@scss': fileURLToPath(new URL('./src/assets/scss', import.meta.url)),
      '@layouts': fileURLToPath(new URL('./src/views/layouts', import.meta.url)),
      '@components': fileURLToPath(new URL('./src/views/components', import.meta.url)),
      '@pages': fileURLToPath(new URL('./src/views/pages', import.meta.url)),
    },
  },
})
