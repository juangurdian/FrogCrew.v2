<template>
  <div class="space-y-4">
    <!-- Summary Cards -->
    <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
      <!-- Upcoming Shifts -->
      <div class="bg-white rounded-lg shadow-sm p-4">
        <div class="flex items-center">
          <div class="p-3 rounded-full bg-blue-100 text-blue-600">
            <svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M8 7V3m8 4V3m-9 8h10M5 21h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v12a2 2 0 002 2z" />
            </svg>
          </div>
          <div class="ml-4">
            <h3 class="text-sm font-medium text-gray-500">Upcoming Shifts</h3>
            <p class="text-2xl font-semibold text-gray-900">{{ upcomingShifts.length }}</p>
          </div>
        </div>
      </div>

      <!-- Available Hours -->
      <div class="bg-white rounded-lg shadow-sm p-4">
        <div class="flex items-center">
          <div class="p-3 rounded-full bg-green-100 text-green-600">
            <svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 8v4l3 3m6-3a9 9 0 11-18 0 9 9 0 0118 0z" />
            </svg>
          </div>
          <div class="ml-4">
            <h3 class="text-sm font-medium text-gray-500">Available Hours</h3>
            <p class="text-2xl font-semibold text-gray-900">24</p>
          </div>
        </div>
      </div>

      <!-- Pending Requests -->
      <div class="bg-white rounded-lg shadow-sm p-4">
        <div class="flex items-center">
          <div class="p-3 rounded-full bg-yellow-100 text-yellow-600">
            <svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 17h5l-1.405-1.405A2.032 2.032 0 0118 14.158V11a6.002 6.002 0 00-4-5.659V5a2 2 0 10-4 0v.341C7.67 6.165 6 8.388 6 11v3.159c0 .538-.214 1.055-.595 1.436L4 17h5m6 0v1a3 3 0 11-6 0v-1m6 0H9" />
            </svg>
          </div>
          <div class="ml-4">
            <h3 class="text-sm font-medium text-gray-500">Pending Requests</h3>
            <p class="text-2xl font-semibold text-gray-900">{{ notifications.length }}</p>
          </div>
        </div>
      </div>
    </div>

    <!-- Main Content -->
    <div class="grid grid-cols-1 lg:grid-cols-2 gap-4">
      <!-- Upcoming Shifts -->
      <div class="bg-white rounded-lg shadow-sm">
        <div class="p-4">
          <h2 class="text-lg font-medium text-gray-900 mb-4">Upcoming Shifts</h2>
          <div class="space-y-4">
            <div v-for="shift in upcomingShifts" :key="shift.id" class="flex items-center justify-between p-4 border rounded-lg">
              <div>
                <p class="font-medium text-gray-900">{{ shift.event }}</p>
                <p class="text-sm text-gray-500">{{ shift.date }}</p>
                <p class="text-sm text-gray-500">{{ shift.location }}</p>
              </div>
              <button class="text-blue-600 hover:text-blue-800 text-sm">
                View Details
              </button>
            </div>
          </div>
        </div>
      </div>

      <!-- Notifications -->
      <div class="bg-white rounded-lg shadow-sm">
        <div class="p-4">
          <h2 class="text-lg font-medium text-gray-900 mb-4">Notifications</h2>
          <div class="space-y-4">
            <div v-for="notification in notifications" :key="notification.id" class="flex items-start p-4 border rounded-lg">
              <div class="flex-shrink-0">
                <div class="p-2 rounded-full" :class="notification.type === 'schedule' ? 'bg-blue-100' : 'bg-yellow-100'">
                  <svg class="w-5 h-5" :class="notification.type === 'schedule' ? 'text-blue-600' : 'text-yellow-600'" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" :d="notification.type === 'schedule' ? 'M8 7V3m8 4V3m-9 8h10M5 21h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v12a2 2 0 002 2z' : 'M15 17h5l-1.405-1.405A2.032 2.032 0 0118 14.158V11a6.002 6.002 0 00-4-5.659V5a2 2 0 10-4 0v.341C7.67 6.165 6 8.388 6 11v3.159c0 .538-.214 1.055-.595 1.436L4 17h5m6 0v1a3 3 0 11-6 0v-1m6 0H9'" />
                  </svg>
                </div>
              </div>
              <div class="ml-3">
                <p class="text-sm font-medium text-gray-900">{{ notification.title }}</p>
                <p class="text-sm text-gray-500">{{ notification.message }}</p>
                <p class="text-xs text-gray-400 mt-1">{{ notification.time }}</p>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- Quick Actions -->
    <div class="bg-white rounded-lg shadow-sm">
      <div class="p-4">
        <h2 class="text-lg font-medium text-gray-900 mb-4">Quick Actions</h2>
        <div class="grid grid-cols-1 md:grid-cols-3 gap-4">
          <button class="btn-primary flex items-center justify-center">
            <svg class="w-5 h-5 mr-2" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 5H7a2 2 0 00-2 2v12a2 2 0 002 2h10a2 2 0 002-2V7a2 2 0 00-2-2h-2M9 5a2 2 0 002 2h2a2 2 0 002-2M9 5a2 2 0 012-2h2a2 2 0 012 2" />
            </svg>
            Update Availability
          </button>
          <button class="btn-primary flex items-center justify-center">
            <svg class="w-5 h-5 mr-2" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M8 7V3m8 4V3m-9 8h10M5 21h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v12a2 2 0 002 2z" />
            </svg>
            View Schedule
          </button>
          <button class="btn-primary flex items-center justify-center">
            <svg class="w-5 h-5 mr-2" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 8v4l3 3m6-3a9 9 0 11-18 0 9 9 0 0118 0z" />
            </svg>
            Request Time Off
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'

defineOptions({
  name: 'CrewDashboard'
})

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
    time: '2 hours ago',
    type: 'schedule'
  },
  {
    id: 2,
    title: 'Shift Exchange Request',
    message: 'John Doe wants to exchange shifts with you.',
    time: '1 day ago',
    type: 'request'
  }
])
</script> 