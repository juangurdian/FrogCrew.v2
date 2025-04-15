<template>
  <div>
    <h1 class="text-3xl font-bold mb-8">Dashboard</h1>
    <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
      <div class="card">
        <h2 class="text-xl font-semibold mb-4">Upcoming Shifts</h2>
        <div class="space-y-4">
          <div v-for="shift in upcomingShifts" :key="shift.id" class="p-4 border rounded-lg">
            <p class="font-medium">{{ shift.event }}</p>
            <p class="text-sm text-gray-500">{{ shift.date }}</p>
            <p class="text-sm text-gray-500">{{ shift.location }}</p>
          </div>
        </div>
      </div>
      <div class="card">
        <h2 class="text-xl font-semibold mb-4">Notifications</h2>
        <div class="space-y-4">
          <div v-for="notification in notifications" :key="notification.id" class="p-4 border rounded-lg">
            <p class="font-medium">{{ notification.title }}</p>
            <p class="text-sm text-gray-500">{{ notification.message }}</p>
            <p class="text-xs text-gray-400">{{ notification.time }}</p>
          </div>
        </div>
      </div>
      <div class="card">
        <h2 class="text-xl font-semibold mb-4">Quick Actions</h2>
        <div class="space-y-4">
          <button class="btn-primary w-full">Update Availability</button>
          <button class="btn-primary w-full">View Schedule</button>
          <button class="btn-primary w-full">Request Time Off</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'

const upcomingShifts = ref([
  {
    id: 1,
    event: 'Football Game vs Rivals',
    date: '2024-04-15',
    location: 'Amon G. Carter Stadium'
  },
  {
    id: 2,
    event: 'Basketball Game',
    date: '2024-04-20',
    location: 'Ed and Rae Schollmaier Arena'
  }
])

const notifications = ref([
  {
    id: 1,
    title: 'New Schedule Posted',
    message: 'The schedule for next month has been posted.',
    time: '2 hours ago'
  },
  {
    id: 2,
    title: 'Shift Exchange Request',
    message: 'John Doe wants to exchange shifts with you.',
    time: '1 day ago'
  }
])
</script> 