<template>
  <div class="space-y-6">
    <!-- Header with View Options -->
    <div class="flex justify-between items-center">
      <h1 class="text-2xl font-bold text-gray-900">My Schedule</h1>
      <div class="flex space-x-2">
        <button 
          v-for="view in viewOptions" 
          :key="view.value"
          @click="currentView = view.value"
          :class="[
            'px-4 py-2 rounded-lg text-sm font-medium transition-colors',
            currentView === view.value 
              ? 'bg-[#4d2e7d] text-white' 
              : 'bg-white text-gray-700 hover:bg-gray-50'
          ]"
        >
          {{ view.label }}
        </button>
      </div>
    </div>

    <!-- Calendar View -->
    <div v-if="currentView === 'calendar'" class="bg-white rounded-lg shadow-sm">
      <div class="p-4">
        <div class="flex justify-between items-center mb-4">
          <h2 class="text-lg font-medium text-gray-900">Calendar</h2>
          <div class="flex space-x-2">
            <button class="p-2 rounded-lg hover:bg-gray-100">
              <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 19l-7-7 7-7" />
              </svg>
            </button>
            <span class="px-4 py-2 text-sm font-medium text-gray-700">March 2024</span>
            <button class="p-2 rounded-lg hover:bg-gray-100">
              <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 5l7 7-7 7" />
              </svg>
            </button>
          </div>
        </div>
        <div class="grid grid-cols-7 gap-1 text-center">
          <div v-for="day in ['Su', 'Mo', 'Tu', 'We', 'Th', 'Fr', 'Sa']" 
               :key="day" 
               class="text-sm font-medium text-gray-500 py-2">
            {{ day }}
          </div>
          <div v-for="(day, index) in calendarDays" 
               :key="index" 
               :class="[
                 'p-2 text-sm rounded-lg cursor-pointer',
                 day.isToday ? 'bg-[#4d2e7d] text-white' : 'hover:bg-gray-100',
                 day.hasShifts ? 'font-bold' : ''
               ]">
            {{ day.date }}
            <div v-if="day.shifts" class="mt-1">
              <div v-for="shift in day.shifts" 
                   :key="shift.id" 
                   class="text-xs bg-purple-100 text-purple-800 rounded px-1 py-0.5 mb-1">
                {{ shift.time }}
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- List View -->
    <div v-if="currentView === 'list'" class="space-y-4">
      <!-- Upcoming Shifts -->
      <div class="bg-white rounded-lg shadow-sm">
        <div class="p-4">
          <h2 class="text-lg font-medium text-gray-900 mb-4">Upcoming Shifts</h2>
          <div class="space-y-4">
            <div v-for="shift in upcomingShifts" 
                 :key="shift.id" 
                 class="flex items-center justify-between p-4 border rounded-lg hover:bg-gray-50">
              <div>
                <p class="text-sm font-medium text-gray-900">{{ shift.title }}</p>
                <p class="text-xs text-gray-500">{{ shift.date }} at {{ shift.time }}</p>
                <p class="text-xs text-gray-500 mt-1">{{ shift.location }}</p>
              </div>
              <div class="flex items-center space-x-2">
                <span class="px-2 py-1 text-xs rounded-full" :class="shift.statusClass">
                  {{ shift.status }}
                </span>
                <button class="text-[#4d2e7d] hover:text-purple-800 text-sm">
                  View Details
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- Past Shifts -->
      <div class="bg-white rounded-lg shadow-sm">
        <div class="p-4">
          <h2 class="text-lg font-medium text-gray-900 mb-4">Past Shifts</h2>
          <div class="space-y-4">
            <div v-for="shift in pastShifts" 
                 :key="shift.id" 
                 class="flex items-center justify-between p-4 border rounded-lg hover:bg-gray-50">
              <div>
                <p class="text-sm font-medium text-gray-900">{{ shift.title }}</p>
                <p class="text-xs text-gray-500">{{ shift.date }} at {{ shift.time }}</p>
                <p class="text-xs text-gray-500 mt-1">{{ shift.location }}</p>
              </div>
              <div class="flex items-center space-x-2">
                <span class="px-2 py-1 text-xs rounded-full" :class="shift.statusClass">
                  {{ shift.status }}
                </span>
                <button class="text-[#4d2e7d] hover:text-purple-800 text-sm">
                  View Details
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue';

defineOptions({
  name: 'Schedule'
});

const currentView = ref('calendar');

const viewOptions = [
  { label: 'Calendar', value: 'calendar' },
  { label: 'List', value: 'list' }
];

const calendarDays = ref(Array.from({ length: 35 }, (_, i) => ({
  date: i + 1,
  isToday: i + 1 === new Date().getDate(),
  hasShifts: [1, 5, 12, 15, 20, 25].includes(i + 1),
  shifts: [1, 5, 12, 15, 20, 25].includes(i + 1) ? [
    { id: 1, time: '9:00 AM' },
    { id: 2, time: '2:00 PM' }
  ] : undefined
})));

const upcomingShifts = ref([
  {
    id: 1,
    title: 'Morning News Broadcast',
    date: 'Today',
    time: '6:00 AM - 9:00 AM',
    location: 'Studio A',
    status: 'Confirmed',
    statusClass: 'bg-green-100 text-green-800'
  },
  {
    id: 2,
    title: 'Sports Coverage',
    date: 'Tomorrow',
    time: '2:00 PM - 5:00 PM',
    location: 'Field 1',
    status: 'Confirmed',
    statusClass: 'bg-green-100 text-green-800'
  },
  {
    id: 3,
    title: 'Evening News',
    date: 'March 15',
    time: '5:00 PM - 8:00 PM',
    location: 'Studio B',
    status: 'Pending',
    statusClass: 'bg-yellow-100 text-yellow-800'
  }
]);

const pastShifts = ref([
  {
    id: 4,
    title: 'Morning News Broadcast',
    date: 'March 1',
    time: '6:00 AM - 9:00 AM',
    location: 'Studio A',
    status: 'Completed',
    statusClass: 'bg-gray-100 text-gray-800'
  },
  {
    id: 5,
    title: 'Sports Coverage',
    date: 'March 3',
    time: '2:00 PM - 5:00 PM',
    location: 'Field 1',
    status: 'Completed',
    statusClass: 'bg-gray-100 text-gray-800'
  }
]);
</script> 