import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

// https://vite.dev/config/
export default defineConfig({
  plugins: [react()],
  define: {
    global: 'window' // lgunos paquetes usan variables globales de Node.js (global, process) en entornos de navegador. Las nuevas versiones de empaquetadores como Vite ya no incluyen estos polyfills por defecto.
  }
})

