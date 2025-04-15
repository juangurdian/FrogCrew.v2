<template>
  <div>
    <h1 class="text-3xl font-bold mb-8">My Profile</h1>
    <div class="card max-w-2xl mx-auto">
      <form @submit.prevent="handleSubmit" class="space-y-4">
        <div>
          <label class="block text-sm font-medium text-gray-700 mb-2">Name</label>
          <input
            type="text"
            v-model="profile.name"
            class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-primary focus:border-transparent"
          >
        </div>
        <div>
          <label class="block text-sm font-medium text-gray-700 mb-2">Email</label>
          <input
            type="email"
            v-model="profile.email"
            class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-primary focus:border-transparent"
          >
        </div>
        <div>
          <label class="block text-sm font-medium text-gray-700 mb-2">Phone</label>
          <input
            type="tel"
            v-model="profile.phone"
            class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-primary focus:border-transparent"
          >
        </div>
        <button type="submit" class="btn-primary w-full">
          Save Profile
        </button>
      </form>
    </div>
  </div>
</template>

<script setup lang="ts">
import { reactive } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()
const profile = reactive({
  name: '',
  email: '',
  phone: ''
})

const handleSubmit = () => {
  // TODO: Implement profile update logic
  router.push('/crew/dashboard')
}
</script> 