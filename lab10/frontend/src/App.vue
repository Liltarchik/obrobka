<template>
  <div class="container py-5">
    <!-- Login Form -->
    <div class="row justify-content-center mb-5" v-if="!isAuthenticated">
      <div class="col-md-6">
        <div class="card shadow-sm">
          <div class="card-body p-4">
            <h2 class="text-center mb-4">Login</h2>
            <form @submit.prevent="handleLogin">
              <div class="mb-3">
                <label class="form-label">Username</label>
                <input type="text" class="form-control" v-model="username" required />
              </div>
              <div class="mb-3">
                <label class="form-label">Password</label>
                <input type="password" class="form-control" v-model="password" required />
              </div>
              <div v-if="loginError" class="alert alert-danger">{{ loginError }}</div>
              <button type="submit" class="btn btn-primary w-100">Login</button>
            </form>
          </div>
        </div>
      </div>
    </div>

    <!-- Courses List -->
    <div v-else>
      <div class="d-flex justify-content-between align-items-center mb-4">
        <h2>Courses Catalog</h2>
        <button class="btn btn-outline-danger" @click="handleLogout">Logout</button>
      </div>
      
      <div class="card shadow-sm mb-4">
        <div class="card-body">
          <button class="btn btn-success mb-3" @click="fetchCourses">Fetch Courses</button>
          <div v-if="fetchError" class="alert alert-danger">{{ fetchError }}</div>
          
          <div v-if="courses.length > 0" class="list-group">
            <div v-for="course in courses" :key="course.id || course.name" class="list-group-item">
              <h5 class="mb-1">{{ course.name || course.title }}</h5>
              <p class="mb-1 text-muted">{{ course.description || 'No description available.' }}</p>
            </div>
          </div>
          <p v-else-if="fetchAttempted" class="text-muted">No courses found.</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'

const API_URL = 'http://localhost:8080'

const username = ref('')
const password = ref('')
const isAuthenticated = ref(false)
const loginError = ref('')

const courses = ref([])
const fetchError = ref('')
const fetchAttempted = ref(false)

onMounted(() => {
  const token = localStorage.getItem('jwt_token')
  if (token) {
    isAuthenticated.value = true
  }
})

const handleLogin = async () => {
  loginError.value = ''
  try {
    const response = await fetch(`${API_URL}/login`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({
        username: username.value,
        password: password.value
      })
    })

    if (!response.ok) {
      throw new Error('Login failed. Please check your credentials.')
    }

    const authHeader = response.headers.get('Authorization')
    let token = null
    
    if (authHeader && authHeader.startsWith('Bearer ')) {
      token = authHeader.replace('Bearer ', '')
    } else {
      const data = await response.json().catch(() => ({}))
      token = data.token || data.jwt || data.accessToken
    }

    if (token) {
      localStorage.setItem('jwt_token', token)
      isAuthenticated.value = true
      username.value = ''
      password.value = ''
    } else {
      throw new Error('No token received from server.')
    }
  } catch (err) {
    loginError.value = err.message
  }
}

const handleLogout = () => {
  localStorage.removeItem('jwt_token')
  isAuthenticated.value = false
  courses.value = []
  fetchAttempted.value = false
}

const fetchCourses = async () => {
  fetchError.value = ''
  fetchAttempted.value = true
  try {
    const token = localStorage.getItem('jwt_token')
    const response = await fetch(`${API_URL}/api/courses`, {
      method: 'GET',
      headers: {
        'Authorization': `Bearer ${token}`
      }
    })

    if (!response.ok) {
      if (response.status === 401 || response.status === 403) {
        handleLogout()
        throw new Error('Session expired. Please login again.')
      }
      throw new Error('Failed to fetch courses.')
    }

    const data = await response.json()
    // Depending on Spring Data REST or custom controller format
    courses.value = data._embedded ? (data._embedded.courses || []) : data
  } catch (err) {
    fetchError.value = err.message
  }
}
</script>

<style scoped>
.container {
  max-width: 800px;
}
</style>
