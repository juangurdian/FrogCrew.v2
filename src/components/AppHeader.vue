<template>
  <header class="bg-white shadow-sm">
    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
      <div class="flex justify-between items-center py-4">
        <div class="flex items-center">
          <h1 class="text-xl font-semibold text-gray-900">FrogCrew</h1>
        </div>
        
        <div class="flex items-center">
          <!-- User Menu -->
          <div class="flex items-center space-x-3">
            <span class="text-sm text-gray-700">{{ userName }}</span>
            <img
              :src="userAvatar"
              alt="User avatar"
              class="h-8 w-8 rounded-full"
            />
            <button 
              @click="logout" 
              class="px-3 py-1.5 text-sm font-medium text-red-600 hover:text-red-800 hover:bg-red-50 rounded-md"
            >
              Logout
            </button>
          </div>
        </div>
      </div>
    </div>
  </header>
</template>

<script setup lang="ts">
import { computed } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'

const store = useStore()
const router = useRouter()

const user = computed(() => store.getters.currentUser)
const userName = computed(() => {
  if (!user.value) return 'User'
  return `${user.value.firstName} ${user.value.lastName}`
})
const userAvatar = computed(() => 'https://via.placeholder.com/32')

const logout = () => {
  store.dispatch('logout')
}
</script>

<style scoped>
.mode-switcher {
  display: flex;
  gap: 10px;
  padding: 10px;
  background-color: #f5f5f5;
  border-radius: 4px;
}

.mode-btn {
  padding: 8px 16px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-weight: 500;
  transition: all 0.3s ease;
}

.mode-btn:hover {
  opacity: 0.9;
}

.admin-btn {
  background-color: #e3f2fd;
  color: #1976d2;
}

.crew-btn {
  background-color: #e8f5e9;
  color: #2e7d32;
}

.mode-btn.active {
  transform: scale(1.05);
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.admin-btn.active {
  background-color: #1976d2;
  color: white;
}

.crew-btn.active {
  background-color: #2e7d32;
  color: white;
}
</style> 