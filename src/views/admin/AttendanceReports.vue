<template>
  <div>
    <h1 class="text-3xl font-bold mb-8">Attendance Reports</h1>
    <div class="card">
      <div class="flex justify-between items-center mb-4">
        <h2 class="text-xl font-semibold">Filter Reports</h2>
        <div class="flex space-x-4">
          <select
            v-model="selectedPeriod"
            class="px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-primary focus:border-transparent"
          >
            <option value="week">This Week</option>
            <option value="month">This Month</option>
            <option value="quarter">This Quarter</option>
            <option value="year">This Year</option>
          </select>
          <button class="btn-primary">
            Export Report
          </button>
        </div>
      </div>
      <div class="text-center text-gray-500">
        No attendance data available
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'

const selectedPeriod = ref('week')
</script> 