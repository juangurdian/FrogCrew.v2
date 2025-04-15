<template>
  <div>
    <h1 class="text-3xl font-bold mb-8">Upcoming Events</h1>
    <div class="card max-w-4xl mx-auto">
      <div class="overflow-x-auto">
        <table class="min-w-full divide-y divide-gray-200">
          <thead class="bg-gray-50">
            <tr>
              <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Date</th>
              <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Event</th>
              <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Location</th>
              <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Status</th>
            </tr>
          </thead>
          <tbody class="bg-white divide-y divide-gray-200">
            <tr v-for="event in events" :key="event.id">
              <td class="px-6 py-4 whitespace-nowrap">{{ event.date }}</td>
              <td class="px-6 py-4 whitespace-nowrap">{{ event.name }}</td>
              <td class="px-6 py-4 whitespace-nowrap">{{ event.location }}</td>
              <td class="px-6 py-4 whitespace-nowrap">
                <span :class="getStatusClass(event.status)">{{ event.status }}</span>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'

const events = ref([
  {
    id: 1,
    date: '2024-04-15',
    name: 'Football Game vs Rivals',
    location: 'Amon G. Carter Stadium',
    status: 'Scheduled'
  },
  {
    id: 2,
    date: '2024-04-20',
    name: 'Basketball Game',
    location: 'Ed and Rae Schollmaier Arena',
    status: 'Pending'
  }
])

const getStatusClass = (status: string) => {
  switch (status.toLowerCase()) {
    case 'scheduled':
      return 'px-2 inline-flex text-xs leading-5 font-semibold rounded-full bg-green-100 text-green-800'
    case 'pending':
      return 'px-2 inline-flex text-xs leading-5 font-semibold rounded-full bg-yellow-100 text-yellow-800'
    default:
      return 'px-2 inline-flex text-xs leading-5 font-semibold rounded-full bg-gray-100 text-gray-800'
  }
}
</script> 