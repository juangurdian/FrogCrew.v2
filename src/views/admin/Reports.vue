<template>
  <div class="space-y-6">
    <!-- Header -->
    <div class="flex justify-between items-center">
      <h1 class="text-2xl font-semibold text-gray-900">Reports</h1>
      <div class="flex space-x-4">
        <button class="btn-secondary">
          <svg class="w-5 h-5 mr-2 inline-block" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 10v6m0 0l-3-3m3 3l3-3m2 8H7a2 2 0 01-2-2V5a2 2 0 012-2h5.586a1 1 0 01.707.293l5.414 5.414a1 1 0 01.293.707V19a2 2 0 01-2 2z" />
          </svg>
          Export Report
        </button>
      </div>
    </div>

    <!-- Report Type Selection -->
    <div class="bg-white rounded-lg shadow-sm p-4">
      <div class="grid grid-cols-1 md:grid-cols-3 gap-4">
        <div>
          <label class="block text-sm font-medium text-gray-700 mb-1">Report Type</label>
          <select v-model="selectedReportType" class="form-select w-full">
            <option v-for="type in reportTypes" :key="type.id" :value="type.id">
              {{ type.name }}
            </option>
          </select>
        </div>
        <div>
          <label class="block text-sm font-medium text-gray-700 mb-1">Date Range</label>
          <select v-model="selectedDateRange" class="form-select w-full">
            <option value="week">This Week</option>
            <option value="month">This Month</option>
            <option value="quarter">This Quarter</option>
            <option value="year">This Year</option>
            <option value="custom">Custom Range</option>
          </select>
        </div>
        <div v-if="selectedReportType === 'crew'">
          <label class="block text-sm font-medium text-gray-700 mb-1">Position</label>
          <select v-model="selectedPosition" class="form-select w-full">
            <option value="">All Positions</option>
            <option v-for="position in positions" :key="position.id" :value="position.id">
              {{ position.name }}
            </option>
          </select>
        </div>
      </div>
    </div>

    <!-- Report Content -->
    <div class="bg-white rounded-lg shadow-sm">
      <!-- Crew Hours Report -->
      <div v-if="selectedReportType === 'hours'" class="divide-y divide-gray-200">
        <div class="p-4">
          <h2 class="text-lg font-medium text-gray-900 mb-4">Crew Hours Summary</h2>
          <div class="grid grid-cols-1 md:grid-cols-3 gap-4">
            <div class="bg-blue-50 p-4 rounded-lg">
              <h3 class="text-sm font-medium text-blue-800">Total Hours</h3>
              <p class="text-2xl font-semibold text-blue-900">456</p>
            </div>
            <div class="bg-green-50 p-4 rounded-lg">
              <h3 class="text-sm font-medium text-green-800">Average Hours/Person</h3>
              <p class="text-2xl font-semibold text-green-900">19</p>
            </div>
            <div class="bg-purple-50 p-4 rounded-lg">
              <h3 class="text-sm font-medium text-purple-800">Total Events</h3>
              <p class="text-2xl font-semibold text-purple-900">24</p>
            </div>
          </div>
        </div>
        <div class="p-4">
          <h3 class="text-md font-medium text-gray-900 mb-4">Hours by Position</h3>
          <div class="space-y-4">
            <div v-for="position in hoursByPosition" :key="position.name" class="flex items-center">
              <div class="w-32 text-sm text-gray-600">{{ position.name }}</div>
              <div class="flex-1">
                <div class="relative">
                  <div class="h-4 bg-gray-200 rounded-full">
                    <div 
                      class="h-4 rounded-full bg-blue-600"
                      :style="{ width: `${(position.hours / maxHours) * 100}%` }"
                    ></div>
                  </div>
                </div>
              </div>
              <div class="w-20 text-right text-sm text-gray-900">{{ position.hours }}h</div>
            </div>
          </div>
        </div>
      </div>

      <!-- Crew Performance Report -->
      <div v-if="selectedReportType === 'performance'" class="divide-y divide-gray-200">
        <div class="p-4">
          <h2 class="text-lg font-medium text-gray-900 mb-4">Performance Metrics</h2>
          <div class="overflow-x-auto">
            <table class="min-w-full divide-y divide-gray-200">
              <thead class="bg-gray-50">
                <tr>
                  <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Crew Member</th>
                  <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Position</th>
                  <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Events</th>
                  <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Hours</th>
                  <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Rating</th>
                </tr>
              </thead>
              <tbody class="bg-white divide-y divide-gray-200">
                <tr v-for="member in crewPerformance" :key="member.id">
                  <td class="px-6 py-4 whitespace-nowrap">
                    <div class="flex items-center">
                      <div class="h-8 w-8 rounded-full bg-gray-200 flex items-center justify-center text-sm font-medium">
                        {{ member.initials }}
                      </div>
                      <div class="ml-4">
                        <div class="text-sm font-medium text-gray-900">{{ member.name }}</div>
                      </div>
                    </div>
                  </td>
                  <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-500">{{ member.position }}</td>
                  <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-500">{{ member.events }}</td>
                  <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-500">{{ member.hours }}</td>
                  <td class="px-6 py-4 whitespace-nowrap">
                    <div class="flex items-center">
                      <div class="flex">
                        <svg v-for="i in 5" :key="i" class="w-4 h-4" :class="i <= member.rating ? 'text-yellow-400' : 'text-gray-300'" fill="currentColor" viewBox="0 0 20 20">
                          <path d="M9.049 2.927c.3-.921 1.603-.921 1.902 0l1.07 3.292a1 1 0 00.95.69h3.462c.969 0 1.371 1.24.588 1.81l-2.8 2.034a1 1 0 00-.364 1.118l1.07 3.292c.3.921-.755 1.688-1.54 1.118l-2.8-2.034a1 1 0 00-1.175 0l-2.8 2.034c-.784.57-1.838-.197-1.539-1.118l1.07-3.292a1 1 0 00-.364-1.118L2.98 8.72c-.783-.57-.38-1.81.588-1.81h3.461a1 1 0 00.951-.69l1.07-3.292z" />
                        </svg>
                      </div>
                      <span class="ml-2 text-sm text-gray-600">{{ member.rating.toFixed(1) }}</span>
                    </div>
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
      </div>

      <!-- Event Statistics Report -->
      <div v-if="selectedReportType === 'events'" class="divide-y divide-gray-200">
        <div class="p-4">
          <h2 class="text-lg font-medium text-gray-900 mb-4">Event Statistics</h2>
          <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
            <div>
              <h3 class="text-md font-medium text-gray-900 mb-4">Events by Type</h3>
              <div class="space-y-4">
                <div v-for="type in eventsByType" :key="type.name" class="flex items-center">
                  <div class="w-32 text-sm text-gray-600">{{ type.name }}</div>
                  <div class="flex-1">
                    <div class="relative">
                      <div class="h-4 bg-gray-200 rounded-full">
                        <div 
                          class="h-4 rounded-full"
                          :class="type.color"
                          :style="{ width: `${(type.count / maxEvents) * 100}%` }"
                        ></div>
                      </div>
                    </div>
                  </div>
                  <div class="w-20 text-right text-sm text-gray-900">{{ type.count }}</div>
                </div>
              </div>
            </div>
            <div>
              <h3 class="text-md font-medium text-gray-900 mb-4">Staffing Success Rate</h3>
              <div class="space-y-4">
                <div v-for="stat in staffingStats" :key="stat.name" class="bg-gray-50 p-4 rounded-lg">
                  <div class="flex justify-between items-center mb-2">
                    <span class="text-sm font-medium text-gray-600">{{ stat.name }}</span>
                    <span class="text-sm font-semibold text-gray-900">{{ stat.value }}%</span>
                  </div>
                  <div class="w-full bg-gray-200 rounded-full h-2">
                    <div 
                      class="h-2 rounded-full"
                      :class="stat.color"
                      :style="{ width: `${stat.value}%` }"
                    ></div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed } from 'vue';

defineOptions({
  name: 'Reports'
});

// State
const selectedReportType = ref('hours');
const selectedDateRange = ref('month');
const selectedPosition = ref('');

// Report types
const reportTypes = [
  { id: 'hours', name: 'Crew Hours Report' },
  { id: 'performance', name: 'Crew Performance Report' },
  { id: 'events', name: 'Event Statistics' }
];

// Positions
const positions = [
  { id: 1, name: 'Camera Operator' },
  { id: 2, name: 'Director' },
  { id: 3, name: 'Producer' },
  { id: 4, name: 'Audio Engineer' },
  { id: 5, name: 'Technical Director' }
];

// Hours by position data
const hoursByPosition = [
  { name: 'Camera Operators', hours: 240 },
  { name: 'Directors', hours: 120 },
  { name: 'Producers', hours: 160 },
  { name: 'Audio Engineers', hours: 180 },
  { name: 'Technical Directors', hours: 100 }
];

const maxHours = Math.max(...hoursByPosition.map(p => p.hours));

// Crew performance data
const crewPerformance = [
  { 
    id: 1, 
    name: 'John Doe',
    initials: 'JD',
    position: 'Camera Operator',
    events: 15,
    hours: 45,
    rating: 4.5
  },
  { 
    id: 2, 
    name: 'Jane Smith',
    initials: 'JS',
    position: 'Director',
    events: 12,
    hours: 36,
    rating: 4.8
  },
  { 
    id: 3, 
    name: 'Mike Johnson',
    initials: 'MJ',
    position: 'Producer',
    events: 10,
    hours: 30,
    rating: 4.2
  }
];

// Event statistics data
const eventsByType = [
  { name: 'Football', count: 12, color: 'bg-blue-600' },
  { name: 'Basketball', count: 8, color: 'bg-green-600' },
  { name: 'Baseball', count: 6, color: 'bg-yellow-600' },
  { name: 'Other Sports', count: 4, color: 'bg-purple-600' }
];

const maxEvents = Math.max(...eventsByType.map(e => e.count));

const staffingStats = [
  { name: 'Fully Staffed Events', value: 85, color: 'bg-green-600' },
  { name: 'Partially Staffed Events', value: 12, color: 'bg-yellow-600' },
  { name: 'Understaffed Events', value: 3, color: 'bg-red-600' }
];
</script> 