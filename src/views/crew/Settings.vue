<template>
  <div>
    <h1 class="text-3xl font-bold mb-8">Settings</h1>
    <div class="card max-w-2xl mx-auto">
      <form @submit.prevent="handleSubmit" class="space-y-4">
        <div>
          <label class="block text-sm font-medium text-gray-700 mb-2">Email Notifications</label>
          <div class="space-y-2">
            <label class="flex items-center space-x-2">
              <input
                type="checkbox"
                v-model="settings.emailNotifications"
                class="rounded border-gray-300 text-primary focus:ring-primary"
              >
              <span>Receive email notifications</span>
            </label>
          </div>
        </div>
        <div>
          <label class="block text-sm font-medium text-gray-700 mb-2">Password</label>
          <input
            type="password"
            v-model="settings.password"
            class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-primary focus:border-transparent"
            placeholder="Enter new password"
          >
        </div>
        <button type="submit" class="btn-primary w-full">
          Save Settings
        </button>
      </form>
    </div>
  </div>
</template>

<script setup lang="ts">
import { reactive } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()
const settings = reactive({
  emailNotifications: true,
  password: ''
})

const handleSubmit = () => {
  // TODO: Implement settings update logic
  router.push('/crew/dashboard')
}
</script> 