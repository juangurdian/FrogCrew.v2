<template>
  <div>
    <h1 class="text-3xl font-bold mb-8">Shift Exchange</h1>
    <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
      <div class="card">
        <h2 class="text-xl font-semibold mb-4">Request Shift Exchange</h2>
        <form @submit.prevent="handleSubmit" class="space-y-4">
          <div>
            <label class="block text-sm font-medium text-gray-700 mb-2">Select Shift</label>
            <select
              v-model="exchange.shiftId"
              class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-primary focus:border-transparent"
            >
              <option v-for="shift in myShifts" :key="shift.id" :value="shift.id">
                {{ shift.date }} - {{ shift.event }}
              </option>
            </select>
          </div>
          <div>
            <label class="block text-sm font-medium text-gray-700 mb-2">Reason</label>
            <textarea
              v-model="exchange.reason"
              class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-primary focus:border-transparent"
              rows="3"
            ></textarea>
          </div>
          <button type="submit" class="btn-primary w-full">
            Request Exchange
          </button>
        </form>
      </div>
      <div class="card">
        <h2 class="text-xl font-semibold mb-4">Pending Requests</h2>
        <div class="space-y-4">
          <div v-for="request in pendingRequests" :key="request.id" class="p-4 border rounded-lg">
            <div class="flex justify-between items-start">
              <div>
                <p class="font-medium">{{ request.event }}</p>
                <p class="text-sm text-gray-500">{{ request.date }}</p>
              </div>
              <div class="flex space-x-2">
                <button class="btn-primary">Accept</button>
                <button class="btn-secondary">Decline</button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { reactive, ref } from 'vue'

const myShifts = ref([
  {
    id: 1,
    date: '2024-04-15',
    event: 'Football Game vs Rivals'
  },
  {
    id: 2,
    date: '2024-04-20',
    event: 'Basketball Game'
  }
])

const pendingRequests = ref([
  {
    id: 1,
    event: 'Baseball Game',
    date: '2024-04-18',
    requester: 'John Doe'
  }
])

const exchange = reactive({
  shiftId: '',
  reason: ''
})

const handleSubmit = () => {
  // TODO: Implement shift exchange request logic
  console.log('Submitting exchange request:', exchange)
}
</script> 