<script setup>
import { ref, onMounted } from 'vue'

const inventory = ref([])
const loading = ref(true)

const loadData = async () => {
  try {
    // ВАЖЛИВО: використовуємо './cegla' (з крапкою на початку)
    // Це означає: "шукай файл cegla в тій же папці, де лежить index.html"
    const response = await fetch('./cegla') 
    
    if (!response.ok) throw new Error('Помилка сервера: ' + response.status)
    inventory.value = await response.json()
  } catch (err) {
    console.error("Не вдалося завантажити дані:", err)
  } finally {
    loading.value = false
  }
}

onMounted(loadData)
</script>

<template>
  <div class="container">
    <header>
      <h1>🏗️ Інформаційна система: Склад цегли</h1>
      <button @click="loadData" class="refresh-btn">Оновити дані</button>
    </header>

    <main>
      <div v-if="loading" class="loader">Завантаження даних...</div>
      
      <table v-else class="brick-table">
        <thead>
          <tr>
            <th>Назва продукції</th>
            <th>Марка</th>
            <th>Тип</th>
            <th>Ціна (грн)</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(item, index) in inventory" :key="index">
            <td>**{{ item.name }}**</td>
            <td>{{ item.mark }}</td>
            <td>{{ item.type }}</td>
            <td class="price">{{ item.price.toFixed(2) }}</td>
          </tr>
        </tbody>
      </table>
    </main>
  </div>
</template>

<style scoped>
.container { font-family: sans-serif; max-width: 800px; margin: 2rem auto; padding: 1rem; border: 1px solid #ddd; border-radius: 8px; }
header { display: flex; justify-content: space-between; align-items: center; margin-bottom: 2rem; }
.brick-table { width: 100%; border-collapse: collapse; }
.brick-table th, .brick-table td { border: 1px solid #eee; padding: 12px; text-align: left; }
.brick-table th { background-color: #f4f4f4; color: #333; }
.price { font-weight: bold; color: #2c3e50; }
.refresh-btn { background: #42b883; color: white; border: none; padding: 8px 16px; border-radius: 4px; cursor: pointer; }
.refresh-btn:hover { background: #33a06f; }
</style>
