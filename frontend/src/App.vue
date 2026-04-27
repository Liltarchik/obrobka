<script setup>
import { ref, onMounted } from 'vue'

const items = ref([])
const loading = ref(true)
const form = ref({ id: null, name: '', mark: '', type: '', price: null })
const isEditing = ref(false)

const loadData = async () => {
  try {
    const response = await fetch('./cegla') // Стукаємо на /cegla
    items.value = await response.json()
  } catch (err) {
    console.error("Помилка завантаження:", err)
  } finally {
    loading.value = false
  }
}

const saveItem = async () => {
  const method = isEditing.value ? 'PUT' : 'POST'
  try {
    await fetch('./cegla', {
      method: method,
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(form.value)
    })
    resetForm()
    loadData()
  } catch (err) {
    console.error("Помилка збереження:", err)
  }
}

const deleteItem = async (id) => {
  if (!confirm('Видалити цю цеглу?')) return
  try {
    await fetch(`./cegla?id=${id}`, { method: 'DELETE' })
    loadData()
  } catch (err) {
    console.error("Помилка видалення:", err)
  }
}

const editItem = (item) => {
  form.value = { ...item }
  isEditing.value = true
}

const resetForm = () => {
  form.value = { id: null, name: '', mark: '', type: '', price: null }
  isEditing.value = false
}

onMounted(loadData)
</script>

<template>
  <div class="container">
    <h1>Керування складом цегли (Lab 3 CRUD)</h1>

    <div class="form-section">
      <h3>{{ isEditing ? 'Редагувати цеглу' : 'Додати нову цеглу' }}</h3>
      <div class="form-grid">
        <input v-model="form.name" placeholder="Назва (напр. Цегла силікатна)" />
        <input v-model="form.mark" placeholder="Марка (напр. М150)" />
        <input v-model="form.type" placeholder="Тип (напр. Одинарна)" />
        <input v-model.number="form.price" type="number" placeholder="Ціна (грн)" step="0.01" />
      </div>
      <div class="form-actions">
        <button @click="saveItem" class="btn-save">{{ isEditing ? 'Оновити' : 'Створити' }}</button>
        <button v-if="isEditing" @click="resetForm" class="btn-cancel">Скасувати</button>
      </div>
    </div>

    <hr />

    <div v-if="loading">Завантаження даних...</div>
    <table v-else class="crud-table">
      <thead>
        <tr>
          <th>ID</th>
          <th>Назва продукції</th>
          <th>Марка</th>
          <th>Тип</th>
          <th>Ціна (грн)</th>
          <th>Дії</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="item in items" :key="item.id">
          <td>{{ item.id }}</td>
          <td>**{{ item.name }}**</td>
          <td>{{ item.mark }}</td>
          <td>{{ item.type }}</td>
          <td class="price">{{ item.price ? item.price.toFixed(2) : '0.00' }}</td>
          <td class="actions-cell">
            <button @click="editItem(item)" class="btn-edit">✏️</button>
            <button @click="deleteItem(item.id)" class="btn-delete">🗑️</button>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<style scoped>
.container { max-width: 900px; margin: auto; font-family: sans-serif; padding: 20px; }
.form-section { background: #f9f9f9; padding: 20px; border-radius: 8px; margin-bottom: 25px; border: 1px solid #eee; }
.form-grid { display: grid; grid-template-columns: 1fr 1fr; gap: 10px; margin-bottom: 15px; }
input { padding: 10px; border: 1px solid #ccc; border-radius: 4px; }
.form-actions { display: flex; gap: 10px; }
.crud-table { width: 100%; border-collapse: collapse; margin-top: 20px; }
.crud-table th, .crud-table td { border: 1px solid #ddd; padding: 12px; text-align: left; }
.crud-table th { background-color: #f4f4f4; }
.price { font-weight: bold; color: #2c3e50; text-align: right; }
.actions-cell { display: flex; gap: 5px; justify-content: center; }
.btn-save { background: #42b883; color: white; border: none; padding: 10px 20px; border-radius: 4px; cursor: pointer; }
.btn-save:hover { background: #33a06f; }
.btn-cancel { background: #95a5a6; color: white; border: none; padding: 10px 20px; border-radius: 4px; cursor: pointer; }
.btn-delete { background: #ff5f5f; color: white; border: none; padding: 6px 10px; border-radius: 4px; cursor: pointer; }
.btn-edit { background: #ffbd4a; color: white; border: none; padding: 6px 10px; border-radius: 4px; cursor: pointer; }
</style>