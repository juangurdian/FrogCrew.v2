<template>
  <div>
    <h1 class="text-3xl font-bold mb-8">My Schedule</h1>
    <div class="flex justify-end mb-4">
      <select
        v-model="selectedView"
        class="px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-primary focus:border-transparent"
      >
        <option value="week">Week View</option>
        <option value="month">Month View</option>
        <option value="list">List View</option>
      </select>
    </div>
    <div class="card">
      <div v-if="selectedView === 'list'" class="space-y-4">
        <div v-for="shift in shifts" :key="shift.id" class="p-4 border rounded-lg">
          <div class="flex justify-between items-start">
            <div>
              <p class="font-medium">{{ shift.event }}</p>
              <p class="text-sm text-gray-500">{{ shift.date }}</p>
              <p class="text-sm text-gray-500">{{ shift.location }}</p>
            </div>
            <span :class="getStatusClass(shift.status)">{{ shift.status }}</span>
          </div>
        </div>
      </div>
      <div v-else class="text-center text-gray-500 py-8">
        <p>No upcoming shifts scheduled</p>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'

const selectedView = ref('week')

const shifts = ref([
  {
    id: 1,
    event: 'Football Game vs Rivals',
    date: '2024-04-15',
    location: 'Amon G. Carter Stadium',
    status: 'Confirmed'
  },
  {
    id: 2,
    event: 'Basketball Game',
    date: '2024-04-20',
    location: 'Ed and Rae Schollmaier Arena',
    status: 'Pending'
  }
])

const getStatusClass = (status: string) => {
  switch (status.toLowerCase()) {
    case 'confirmed':
      return 'px-2 inline-flex text-xs leading-5 font-semibold rounded-full bg-green-100 text-green-800'
    case 'pending':
      return 'px-2 inline-flex text-xs leading-5 font-semibold rounded-full bg-yellow-100 text-yellow-800'
    default:
      return 'px-2 inline-flex text-xs leading-5 font-semibold rounded-full bg-gray-100 text-gray-800'
  }
}
</script> 