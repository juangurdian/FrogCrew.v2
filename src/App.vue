<template>
  <router-view></router-view>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()
const currentRole = ref('admin') // Default to admin for now

onMounted(() => {
  // Redirect to admin dashboard on initial load
  router.push('/admin/dashboard')
})

const switchRole = (role: string) => {
  currentRole.value = role
  if (role === 'admin') {
    router.push('/admin/dashboard')
  } else {
    router.push('/crew/dashboard')
  }
}
</script>

<style>
@import './assets/main.css';
</style>
