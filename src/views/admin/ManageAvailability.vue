<template>
  <div class="space-y-6">
    <!-- Header -->
    <div class="flex justify-between items-center">
      <h1 class="text-2xl font-semibold text-gray-900">Manage Crew Availability</h1>
      <div class="flex space-x-4">
        <button class="btn-primary">
          Export Schedule
        </button>
        <button class="btn-secondary">
          Send Availability Request
        </button>
      </div>
    </div>

    <!-- Filters -->
    <div class="bg-white rounded-lg shadow-sm p-4">
      <div class="grid grid-cols-1 md:grid-cols-4 gap-4">
        <div>
          <label class="block text-sm font-medium text-gray-700 mb-1">Position</label>
          <select v-model="selectedPosition" class="form-select w-full">
            <option value="">All Positions</option>
            <option v-for="position in positions" :key="position.id" :value="position.id">
              {{ position.name }}
            </option>
          </select>
        </div>
        <div>
          <label class="block text-sm font-medium text-gray-700 mb-1">Date Range</label>
          <select v-model="selectedDateRange" class="form-select w-full">
            <option value="week">This Week</option>
            <option value="month">This Month</option>
            <option value="custom">Custom Range</option>
          </select>
        </div>
        <div>
          <label class="block text-sm font-medium text-gray-700 mb-1">Availability Status</label>
          <select v-model="selectedStatus" class="form-select w-full">
            <option value="">All Status</option>
            <option value="available">Available</option>
            <option value="unavailable">Unavailable</option>
            <option value="pending">Pending Response</option>
          </select>
        </div>
        <div>
          <label class="block text-sm font-medium text-gray-700 mb-1">Search Crew</label>
          <input type="text" v-model="searchQuery" placeholder="Search by name..." class="form-input w-full" />
        </div>
      </div>
    </div>

    <!-- Calendar View -->
    <div class="bg-white rounded-lg shadow-sm">
      <div class="p-4 border-b">
        <div class="flex items-center justify-between">
          <div class="flex items-center space-x-4">
            <button @click="previousMonth" class="p-2 hover:bg-gray-100 rounded-full">
              <svg class="w-5 h-5 text-gray-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 19l-7-7 7-7" />
              </svg>
            </button>
            <h2 class="text-lg font-medium text-gray-900">{{ currentMonthYear }}</h2>
            <button @click="nextMonth" class="p-2 hover:bg-gray-100 rounded-full">
              <svg class="w-5 h-5 text-gray-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 5l7 7-7 7" />
              </svg>
            </button>
          </div>
          <div class="flex items-center space-x-2">
            <span class="text-sm text-gray-500">View:</span>
            <button 
              v-for="view in views" 
              :key="view"
              @click="selectedView = view"
              :class="[
                'px-3 py-1 text-sm rounded-md',
                selectedView === view ? 'bg-primary text-white' : 'text-gray-600 hover:bg-gray-100'
              ]"
            >
              {{ view }}
            </button>
          </div>
        </div>
      </div>

      <!-- Calendar Grid -->
      <div class="p-4">
        <div class="grid grid-cols-7 gap-px bg-gray-200">
          <div v-for="day in weekDays" :key="day" class="bg-gray-50 p-2 text-center text-sm font-medium text-gray-700">
            {{ day }}
          </div>
          <template v-for="date in calendarDates" :key="date.date">
            <div 
              :class="[
                'bg-white p-2 min-h-[100px]',
                date.isToday ? 'bg-blue-50' : '',
                date.isCurrentMonth ? '' : 'bg-gray-50'
              ]"
            >
              <div class="flex justify-between items-center mb-2">
                <span :class="[
                  'text-sm font-medium',
                  date.isCurrentMonth ? 'text-gray-900' : 'text-gray-400'
                ]">
                  {{ date.dayNumber }}
                </span>
                <span v-if="date.availableCount" class="text-xs bg-green-100 text-green-800 px-2 py-0.5 rounded-full">
                  {{ date.availableCount }} available
                </span>
              </div>
              <div class="space-y-1">
                <div 
                  v-for="crew in date.availableCrew" 
                  :key="crew.id"
                  class="text-xs p-1 rounded bg-gray-50 truncate"
                  :title="crew.name"
                >
                  {{ crew.name }}
                </div>
              </div>
            </div>
          </template>
        </div>
      </div>
    </div>

    <!-- Crew List View (shown when selectedView is 'List') -->
    <div v-if="selectedView === 'List'" class="bg-white rounded-lg shadow-sm">
      <div class="overflow-x-auto">
        <table class="min-w-full divide-y divide-gray-200">
          <thead class="bg-gray-50">
            <tr>
              <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Name</th>
              <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Position</th>
              <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Availability</th>
              <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Status</th>
              <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Actions</th>
            </tr>
          </thead>
          <tbody class="bg-white divide-y divide-gray-200">
            <tr v-for="crew in filteredCrewList" :key="crew.id">
              <td class="px-6 py-4 whitespace-nowrap">
                <div class="flex items-center">
                  <div class="h-8 w-8 rounded-full bg-gray-200 flex items-center justify-center">
                    {{ crew.initials }}
                  </div>
                  <div class="ml-4">
                    <div class="text-sm font-medium text-gray-900">{{ crew.name }}</div>
                    <div class="text-sm text-gray-500">{{ crew.email }}</div>
                  </div>
                </div>
              </td>
              <td class="px-6 py-4 whitespace-nowrap">
                <div class="text-sm text-gray-900">{{ crew.position }}</div>
              </td>
              <td class="px-6 py-4 whitespace-nowrap">
                <div class="text-sm text-gray-900">{{ crew.availabilityDays }}</div>
              </td>
              <td class="px-6 py-4 whitespace-nowrap">
                <span :class="[
                  'px-2 py-1 text-xs rounded-full',
                  crew.status === 'available' ? 'bg-green-100 text-green-800' :
                  crew.status === 'unavailable' ? 'bg-red-100 text-red-800' :
                  'bg-yellow-100 text-yellow-800'
                ]">
                  {{ crew.status }}
                </span>
              </td>
              <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
                <button class="text-primary hover:text-primary-dark">View Details</button>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed } from 'vue';

defineOptions({
  name: 'ManageAvailability'
});

// Filter states
const selectedPosition = ref('');
const selectedDateRange = ref('week');
const selectedStatus = ref('');
const searchQuery = ref('');
const selectedView = ref('Calendar');

// Calendar states
const currentDate = ref(new Date());
const views = ['Calendar', 'List'];
const weekDays = ['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'];

// Mock data
const positions = ref([
  { id: 1, name: 'Camera Operator' },
  { id: 2, name: 'Director' },
  { id: 3, name: 'Producer' },
  { id: 4, name: 'Audio Engineer' },
  { id: 5, name: 'Technical Director' }
]);

const crewList = ref([
  {
    id: 1,
    name: 'John Doe',
    email: 'john@example.com',
    position: 'Camera Operator',
    initials: 'JD',
    availabilityDays: 'Mon, Wed, Fri',
    status: 'available'
  },
  // Add more mock crew members here
]);

// Computed properties
const currentMonthYear = computed(() => {
  return currentDate.value.toLocaleString('default', { month: 'long', year: 'numeric' });
});

const calendarDates = computed(() => {
  const dates = [];
  const year = currentDate.value.getFullYear();
  const month = currentDate.value.getMonth();
  const firstDay = new Date(year, month, 1);
  const lastDay = new Date(year, month + 1, 0);
  
  // Add days from previous month
  const firstDayOfWeek = firstDay.getDay();
  for (let i = firstDayOfWeek - 1; i >= 0; i--) {
    const date = new Date(year, month, -i);
    dates.push({
      date: date.toISOString(),
      dayNumber: date.getDate(),
      isCurrentMonth: false,
      isToday: isSameDate(date, new Date()),
      availableCount: Math.floor(Math.random() * 5),
      availableCrew: generateMockCrewForDate(date)
    });
  }
  
  // Add days of current month
  for (let i = 1; i <= lastDay.getDate(); i++) {
    const date = new Date(year, month, i);
    dates.push({
      date: date.toISOString(),
      dayNumber: i,
      isCurrentMonth: true,
      isToday: isSameDate(date, new Date()),
      availableCount: Math.floor(Math.random() * 5),
      availableCrew: generateMockCrewForDate(date)
    });
  }
  
  // Add days from next month
  const remainingDays = 42 - dates.length; // 6 rows × 7 days
  for (let i = 1; i <= remainingDays; i++) {
    const date = new Date(year, month + 1, i);
    dates.push({
      date: date.toISOString(),
      dayNumber: i,
      isCurrentMonth: false,
      isToday: isSameDate(date, new Date()),
      availableCount: Math.floor(Math.random() * 5),
      availableCrew: generateMockCrewForDate(date)
    });
  }
  
  return dates;
});

const filteredCrewList = computed(() => {
  return crewList.value.filter(crew => {
    if (selectedPosition.value && crew.position !== positions.value.find(p => p.id === Number(selectedPosition.value))?.name) {
      return false;
    }
    if (selectedStatus.value && crew.status !== selectedStatus.value) {
      return false;
    }
    if (searchQuery.value && !crew.name.toLowerCase().includes(searchQuery.value.toLowerCase())) {
      return false;
    }
    return true;
  });
});

// Helper functions
function isSameDate(date1: Date, date2: Date) {
  return date1.getDate() === date2.getDate() &&
         date1.getMonth() === date2.getMonth() &&
         date1.getFullYear() === date2.getFullYear();
}

function generateMockCrewForDate(date: Date) {
  const count = Math.floor(Math.random() * 3);
  return Array.from({ length: count }, (_, i) => ({
    id: i + 1,
    name: `Crew Member ${i + 1}`
  }));
}

function previousMonth() {
  currentDate.value = new Date(
    currentDate.value.getFullYear(),
    currentDate.value.getMonth() - 1,
    1
  );
}

function nextMonth() {
  currentDate.value = new Date(
    currentDate.value.getFullYear(),
    currentDate.value.getMonth() + 1,
    1
  );
}
</script> 