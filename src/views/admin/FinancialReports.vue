<template>
  <div>
    <h1 class="text-3xl font-bold mb-8">Financial Reports</h1>
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
      <div class="grid grid-cols-1 md:grid-cols-3 gap-6 mb-6">
        <div class="card">
          <h3 class="text-lg font-semibold mb-2">Total Revenue</h3>
          <p class="text-2xl font-bold text-primary">$0.00</p>
        </div>
        <div class="card">
          <h3 class="text-lg font-semibold mb-2">Total Expenses</h3>
          <p class="text-2xl font-bold text-red-600">$0.00</p>
        </div>
        <div class="card">
          <h3 class="text-lg font-semibold mb-2">Net Income</h3>
          <p class="text-2xl font-bold text-green-600">$0.00</p>
        </div>
      </div>
      <div class="text-center text-gray-500">
        No financial data available
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'

const selectedPeriod = ref('week')
</script> 