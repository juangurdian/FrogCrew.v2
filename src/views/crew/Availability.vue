<template>
  <div>
    <h1 class="text-3xl font-bold mb-8">Update Availability</h1>
    <div class="card max-w-2xl mx-auto">
      <form @submit.prevent="handleSubmit" class="space-y-4">
        <div>
          <label class="block text-sm font-medium text-gray-700 mb-2">Select Days</label>
          <div class="grid grid-cols-2 md:grid-cols-3 gap-4">
            <label v-for="day in days" :key="day" class="flex items-center space-x-2">
              <input
                type="checkbox"
                v-model="selectedDays"
                :value="day"
                class="rounded border-gray-300 text-primary focus:ring-primary"
              >
              <span>{{ day }}</span>
            </label>
          </div>
        </div>
        <div>
          <label class="block text-sm font-medium text-gray-700 mb-2">Available Times</label>
          <div class="space-y-4">
            <div v-for="day in selectedDays" :key="day" class="flex items-center space-x-4">
              <span class="w-24">{{ day }}</span>
              <input
                type="time"
                v-model="availability[day].start"
                class="px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-primary focus:border-transparent"
              >
              <span>to</span>
              <input
                type="time"
                v-model="availability[day].end"
                class="px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-primary focus:border-transparent"
              >
            </div>
          </div>
        </div>
        <button type="submit" class="btn-primary w-full">
          Save Availability
        </button>
      </form>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()
const days = ['Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday', 'Sunday']
const selectedDays = ref<string[]>([])
const availability = reactive<Record<string, { start: string; end: string }>>({})

const handleSubmit = () => {
  // TODO: Implement availability submission logic
  router.push('/crew/dashboard')
}
</script> 