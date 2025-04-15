<template>
  <router-view></router-view>
</template>

<script setup lang="ts">
import { onMounted, watch } from 'vue'
import { useRouter } from 'vue-router'
import { useStore } from 'vuex'
import { userService } from '@/services/api'

const router = useRouter()
const store = useStore()

onMounted(async () => {
  // Check if user is authenticated
  if (store.getters.isAuthenticated) {
    try {
      // Fetch current user data if needed
      const response = await userService.getCurrentUser()
      store.commit('setUser', response.data)
      
      // Redirect based on user role
      redirectBasedOnRole()
    } catch (error) {
      // If error fetching user, log out
      store.dispatch('logout')
      router.push('/login')
    }
  } else {
    // If not authenticated, redirect to login
    if (router.currentRoute.value.path !== '/login') {
      router.push('/login')
    }
  }
})

// Watch for authentication changes
watch(() => store.getters.isAuthenticated, (isAuthenticated) => {
  if (!isAuthenticated && router.currentRoute.value.path !== '/login') {
    router.push('/login')
  }
})

// Redirect based on user role
const redirectBasedOnRole = () => {
  const userRole = store.getters.userRole
  
  if (userRole === 'ADMIN') {
    store.dispatch('switchMode', 'admin')
    
    if (!router.currentRoute.value.path.startsWith('/admin')) {
      router.push('/admin/dashboard')
    }
  } else {
    store.dispatch('switchMode', 'crew')
    
    if (!router.currentRoute.value.path.startsWith('/crew')) {
      router.push('/crew/dashboard')
    }
  }
}
</script>

<style>
@import './assets/main.css';
</style>
