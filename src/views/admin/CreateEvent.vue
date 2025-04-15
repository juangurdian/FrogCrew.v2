<template>
  <div>
    <h1 class="text-3xl font-bold mb-8">Create New Event</h1>
    <div class="card max-w-2xl mx-auto">
      <form @submit.prevent="handleCreate" class="space-y-4">
        <div>
          <label for="eventName" class="block text-sm font-medium text-gray-700 mb-1">Event Name</label>
          <input
            id="eventName"
            v-model="eventName"
            type="text"
            required
            class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-primary focus:border-transparent"
            placeholder="e.g., TCU vs Baylor Football"
          >
        </div>
        <div>
          <label for="eventDate" class="block text-sm font-medium text-gray-700 mb-1">Event Date</label>
          <input
            id="eventDate"
            v-model="eventDate"
            type="datetime-local"
            required
            class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-primary focus:border-transparent"
          >
        </div>
        <div>
          <label for="eventLocation" class="block text-sm font-medium text-gray-700 mb-1">Location</label>
          <input
            id="eventLocation"
            v-model="eventLocation"
            type="text"
            required
            class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-primary focus:border-transparent"
            placeholder="e.g., Amon G. Carter Stadium"
          >
        </div>
        <div>
          <label for="eventType" class="block text-sm font-medium text-gray-700 mb-1">Event Type</label>
          <select
            id="eventType"
            v-model="eventType"
            required
            class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-primary focus:border-transparent"
          >
            <option value="">Select event type</option>
            <option value="football">Football</option>
            <option value="basketball">Basketball</option>
            <option value="baseball">Baseball</option>
            <option value="other">Other</option>
          </select>
        </div>
        <button type="submit" class="btn-primary w-full">
          Create Event
        </button>
      </form>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()
const eventName = ref('')
const eventDate = ref('')
const eventLocation = ref('')
const eventType = ref('')

const handleCreate = () => {
  // TODO: Implement event creation logic
  router.push('/admin/events')
}
</script> 