<template>
  <div class="app-container">
    <header class="header">
      <h1>КіноПортал</h1>
    </header>

    <main class="main-content">
      <!-- Form to add a movie -->
      <section class="add-movie-section">
        <h2>Додати фільм</h2>
        <form @submit.prevent="addMovie" class="movie-form">
          <div class="form-group">
            <input 
              type="text" 
              id="name" 
              v-model="newMovie.name" 
              required 
              placeholder="Назва фільму" 
            />
          </div>
          <div class="form-group">
            <textarea 
              id="description" 
              v-model="newMovie.description" 
              required 
              placeholder="Короткий опис"
            ></textarea>
          </div>
          <div class="form-group">
            <input 
              type="url" 
              id="imageUrl" 
              v-model="newMovie.imageUrl" 
              placeholder="URL обкладинки (необов'язково)" 
            />
          </div>
          <button type="submit" class="btn-primary" :disabled="isSubmitting">
            {{ isSubmitting ? 'Додавання...' : 'Додати' }}
          </button>
        </form>
      </section>

      <!-- Status messages -->
      <div v-if="error" class="alert error-message">
        {{ error }}
      </div>
      
      <div v-if="loading" class="alert loading-message">
        Завантаження...
      </div>

      <!-- Movie List -->
      <section v-else class="movies-section">
        <div v-if="movies.length === 0" class="alert no-movies">
          Фільмів поки немає.
        </div>
        
        <div v-else class="movies-grid">
          <div v-for="movie in movies" :key="movie.id" class="movie-card">
            <button 
              @click="deleteMovie(movie.id)" 
              class="btn-delete" 
              :disabled="deletingId === movie.id"
              title="Видалити"
            >
              &times;
            </button>
            <div class="movie-image">
              <img 
                :src="movie.imageUrl || placeholderImage" 
                :alt="movie.name" 
                @error="handleImageError" 
              />
            </div>
            <div class="movie-info">
              <h3>{{ movie.name }}</h3>
              <p class="description">{{ movie.description }}</p>
            </div>
          </div>
        </div>
      </section>
    </main>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';

const API_URL = 'http://localhost:8080/api/movies';
const placeholderImage = 'https://via.placeholder.com/400x600/f0f0f0/cccccc?text=Немає+обкладинки';

const movies = ref([]);
const loading = ref(false);
const error = ref(null);
const isSubmitting = ref(false);
const deletingId = ref(null);

const newMovie = ref({
  name: '',
  description: '',
  imageUrl: ''
});

// GET: Отримання всіх фільмів
const fetchMovies = async () => {
  loading.value = true;
  error.value = null;
  try {
    const response = await axios.get(API_URL);
    movies.value = response.data.sort((a, b) => b.id - a.id);
  } catch (err) {
    error.value = 'Помилка завантаження: ' + (err.response?.data?.message || err.message);
    console.error(err);
  } finally {
    loading.value = false;
  }
};

// POST: Додавання фільму
const addMovie = async () => {
  if (!newMovie.value.name.trim() || !newMovie.value.description.trim()) return;
  
  isSubmitting.value = true;
  error.value = null;
  
  try {
    const payload = {
      name: newMovie.value.name.trim(),
      description: newMovie.value.description.trim(),
      imageUrl: newMovie.value.imageUrl.trim() || ''
    };
    
    const response = await axios.post(API_URL, payload);
    movies.value.unshift(response.data);
    newMovie.value = { name: '', description: '', imageUrl: '' };
  } catch (err) {
    error.value = 'Помилка додавання: ' + (err.response?.data?.message || err.message);
    console.error(err);
  } finally {
    isSubmitting.value = false;
  }
};

// DELETE: Видалення фільму за ID
const deleteMovie = async (id) => {
  if (!confirm('Ви впевнені, що хочете видалити цей фільм?')) return;
  
  deletingId.value = id;
  error.value = null;
  
  try {
    await axios.delete(`${API_URL}/${id}`);
    movies.value = movies.value.filter(movie => movie.id !== id);
  } catch (err) {
    error.value = 'Помилка видалення: ' + (err.response?.data?.message || err.message);
    console.error(err);
  } finally {
    deletingId.value = null;
  }
};

// Обробка битих зображень
const handleImageError = (e) => {
  if (e.target.src !== placeholderImage) {
    e.target.src = placeholderImage;
  }
};

onMounted(() => {
  fetchMovies();
});
</script>

<style scoped>
/* Глобальні стилі контейнера */
.app-container {
  font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, Helvetica, Arial, sans-serif;
  max-width: 1000px;
  margin: 0 auto;
  padding: 2rem;
  background-color: #fff;
  min-height: 100vh;
}

/* Шапка */
.header {
  margin-bottom: 2.5rem;
  border-bottom: 1px solid #eaeaea;
  padding-bottom: 1rem;
}

.header h1 {
  margin: 0;
  font-size: 1.8rem;
  font-weight: 600;
  color: #222;
}

.main-content {
  display: flex;
  flex-direction: column;
  gap: 3rem;
}

/* Форма додавання */
.add-movie-section {
  max-width: 500px;
}

.add-movie-section h2 {
  font-size: 1.2rem;
  margin-top: 0;
  margin-bottom: 1.2rem;
  font-weight: 500;
  color: #333;
}

.form-group {
  margin-bottom: 1rem;
}

/* ВАЖЛИВО: Виправлення кольору тексту в інпутах */
.form-group input,
.form-group textarea {
  width: 100%;
  padding: 0.8rem 1rem;
  border: 1px solid #dcdcdc;
  border-radius: 4px;
  font-size: 1rem;
  background-color: #fff;
  color: #333; /* Текст тепер темний і добре читається */
  box-sizing: border-box;
}

.form-group textarea {
  resize: vertical;
  min-height: 90px;
}

/* Світло-сірі плейсхолдери */
.form-group input::placeholder,
.form-group textarea::placeholder {
  color: #999;
}

.form-group input:focus,
.form-group textarea:focus {
  outline: none;
  border-color: #888;
}

/* Проста, плоска кнопка */
.btn-primary {
  padding: 0.8rem 1.5rem;
  background-color: #444; /* Темно-сірий колір */
  color: #fff;
  border: none;
  border-radius: 4px;
  font-size: 1rem;
  cursor: pointer;
  transition: background-color 0.2s;
}

.btn-primary:hover:not(:disabled) {
  background-color: #222;
}

.btn-primary:disabled {
  opacity: 0.5;
  cursor: not-allowed;
}

/* Статусні повідомлення */
.alert {
  padding: 1rem;
  border-radius: 4px;
  font-size: 1rem;
}

.error-message {
  background-color: #fff0f0;
  color: #d32f2f;
  border: 1px solid #ffcdd2;
}

.loading-message, .no-movies {
  color: #666;
}

/* Сітка карток */
.movies-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(220px, 1fr));
  gap: 1.5rem;
}

/* Мінімалістична картка */
.movie-card {
  position: relative;
  border: 1px solid #eaeaea; /* Дуже тонка рамка */
  border-radius: 4px;
  background: #fff;
  display: flex;
  flex-direction: column;
}

.movie-image {
  height: 320px;
  width: 100%;
  background: #f9f9f9;
  border-bottom: 1px solid #eaeaea;
}

.movie-image img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  display: block;
}

.movie-info {
  padding: 1rem;
  flex-grow: 1;
}

.movie-info h3 {
  margin: 0 0 0.5rem 0;
  font-size: 1.1rem;
  font-weight: 600;
  color: #222;
}

.movie-info .description {
  margin: 0;
  color: #666;
  font-size: 0.9rem;
  line-height: 1.5;
}

/* Кнопка видалення (хрестик) */
.btn-delete {
  position: absolute;
  top: 8px;
  right: 8px;
  width: 26px;
  height: 26px;
  background-color: #e53935;
  color: white;
  border: none;
  border-radius: 50%;
  font-size: 18px;
  line-height: 1;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  padding: 0;
  z-index: 10;
  transition: background-color 0.2s;
  box-shadow: 0 1px 3px rgba(0,0,0,0.2); /* Дуже легка тінь, щоб виділялась на фоні картинки */
}

.btn-delete:hover:not(:disabled) {
  background-color: #c62828;
}

.btn-delete:disabled {
  opacity: 0.5;
  cursor: not-allowed;
}

/* Адаптивність */
@media (max-width: 600px) {
  .movies-grid {
    grid-template-columns: 1fr;
  }
}
</style>