<template>
  <div class="space-y-4">
    <!-- Summary Cards -->
    <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-4">
      <!-- Total Crew Members -->
      <div class="bg-white rounded-lg shadow-sm p-4">
        <div class="flex items-center">
          <div class="p-3 rounded-full bg-blue-100 text-blue-600">
            <svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M17 20h5v-2a3 3 0 00-5.356-1.857M17 20H7m10 0v-2c0-.656-.126-1.283-.356-1.857M7 20H2v-2a3 3 0 015.356-1.857M7 20v-2c0-.656.126-1.283.356-1.857m0 0a5.002 5.002 0 019.288 0M15 7a3 3 0 11-6 0 3 3 0 016 0zm6 3a2 2 0 11-4 0 2 2 0 014 0zM7 10a2 2 0 11-4 0 2 2 0 014 0z" />
            </svg>
          </div>
          <div class="ml-4">
            <h3 class="text-sm font-medium text-gray-500">Total Crew Members</h3>
            <p class="text-2xl font-semibold text-gray-900">24</p>
          </div>
        </div>
      </div>

      <!-- Upcoming Shifts -->
      <div class="bg-white rounded-lg shadow-sm p-4">
        <div class="flex items-center">
          <div class="p-3 rounded-full bg-green-100 text-green-600">
            <svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M8 7V3m8 4V3m-9 8h10M5 21h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v12a2 2 0 002 2z" />
            </svg>
          </div>
          <div class="ml-4">
            <h3 class="text-sm font-medium text-gray-500">Upcoming Shifts</h3>
            <p class="text-2xl font-semibold text-gray-900">12</p>
          </div>
        </div>
      </div>

      <!-- Available Crew -->
      <div class="bg-white rounded-lg shadow-sm p-4">
        <div class="flex items-center">
          <div class="p-3 rounded-full bg-yellow-100 text-yellow-600">
            <svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 5H7a2 2 0 00-2 2v12a2 2 0 002 2h10a2 2 0 002-2V7a2 2 0 00-2-2h-2M9 5a2 2 0 002 2h2a2 2 0 002-2M9 5a2 2 0 012-2h2a2 2 0 012 2" />
            </svg>
          </div>
          <div class="ml-4">
            <h3 class="text-sm font-medium text-gray-500">Available Crew</h3>
            <p class="text-2xl font-semibold text-gray-900">18</p>
          </div>
        </div>
      </div>

      <!-- Pending Requests -->
      <div class="bg-white rounded-lg shadow-sm p-4">
        <div class="flex items-center">
          <div class="p-3 rounded-full bg-red-100 text-red-600">
            <svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 9v2m0 4h.01m-6.938 4h13.856c1.54 0 2.502-1.667 1.732-3L13.732 4c-.77-1.333-2.694-1.333-3.464 0L3.34 16c-.77 1.333.192 3 1.732 3z" />
            </svg>
          </div>
          <div class="ml-4">
            <h3 class="text-sm font-medium text-gray-500">Pending Requests</h3>
            <p class="text-2xl font-semibold text-gray-900">3</p>
          </div>
        </div>
      </div>
    </div>

    <!-- Calendar and Crew Status -->
    <div class="grid grid-cols-1 lg:grid-cols-3 gap-4">
      <!-- Mini Calendar -->
      <div class="bg-white rounded-lg shadow-sm">
        <div class="p-4">
          <h2 class="text-lg font-medium text-gray-900 mb-4">Calendar</h2>
          <div class="grid grid-cols-7 gap-1 text-center">
            <div v-for="day in ['Su', 'Mo', 'Tu', 'We', 'Th', 'Fr', 'Sa']" :key="day" class="text-sm font-medium text-gray-500">
              {{ day }}
            </div>
            <div v-for="(day, index) in calendarDays" :key="index" 
                 :class="[
                   'p-2 text-sm rounded-full cursor-pointer',
                   day.isToday ? 'bg-blue-100 text-blue-600' : 'hover:bg-gray-100',
                   day.hasShifts ? 'font-bold' : ''
                 ]">
              {{ day.date }}
            </div>
          </div>
        </div>
      </div>

      <!-- Crew Status Overview -->
      <div class="bg-white rounded-lg shadow-sm">
        <div class="p-4">
          <h2 class="text-lg font-medium text-gray-900 mb-4">Crew Status</h2>
          <div class="space-y-4">
            <div v-for="position in crewPositions" :key="position.name" class="flex items-center justify-between">
              <div class="flex items-center">
                <div class="w-3 h-3 rounded-full mr-2" :class="position.color"></div>
                <span class="text-sm text-gray-700">{{ position.name }}</span>
              </div>
              <span class="text-sm font-medium text-gray-900">{{ position.count }}</span>
            </div>
          </div>
        </div>
      </div>

      <!-- Shift Coverage -->
      <div class="bg-white rounded-lg shadow-sm">
        <div class="p-4">
          <h2 class="text-lg font-medium text-gray-900 mb-4">Shift Coverage</h2>
          <div class="space-y-4">
            <div v-for="shift in shiftCoverage" :key="shift.id" class="flex items-center justify-between">
              <div>
                <p class="text-sm font-medium text-gray-900">{{ shift.title }}</p>
                <p class="text-xs text-gray-500">{{ shift.time }}</p>
              </div>
              <div class="flex items-center">
                <div class="w-24 bg-gray-200 rounded-full h-2.5 mr-2">
                  <div class="h-2.5 rounded-full" :class="shift.coverageClass" :style="{ width: shift.coverage + '%' }"></div>
                </div>
                <span class="text-sm text-gray-600">{{ shift.coverage }}%</span>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- Recent Activity and Upcoming Shifts -->
    <div class="grid grid-cols-1 lg:grid-cols-2 gap-4">
      <!-- Recent Activity -->
      <div class="bg-white rounded-lg shadow-sm">
        <div class="p-4">
          <h2 class="text-lg font-medium text-gray-900 mb-4">Recent Activity</h2>
          <div class="space-y-4">
            <div v-for="activity in recentActivities" :key="activity.id" class="flex items-start">
              <div class="flex-shrink-0">
                <div :class="activity.iconBg" class="p-2 rounded-full">
                  <svg class="w-5 h-5" :class="activity.iconColor" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" :d="activity.iconPath" />
                  </svg>
                </div>
              </div>
              <div class="ml-3">
                <p class="text-sm font-medium text-gray-900">{{ activity.description }}</p>
                <p class="text-xs text-gray-500">{{ activity.time }}</p>
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- Upcoming Shifts -->
      <div class="bg-white rounded-lg shadow-sm">
        <div class="p-4">
          <h2 class="text-lg font-medium text-gray-900 mb-4">Upcoming Shifts</h2>
          <div class="space-y-4">
            <div v-for="shift in upcomingShifts" :key="shift.id" class="flex items-center justify-between">
              <div>
                <p class="text-sm font-medium text-gray-900">{{ shift.title }}</p>
                <p class="text-xs text-gray-500">{{ shift.date }} at {{ shift.time }}</p>
              </div>
              <div class="flex items-center space-x-2">
                <span class="px-2 py-1 text-xs rounded-full" :class="shift.statusClass">
                  {{ shift.status }}
                </span>
                <button class="text-blue-600 hover:text-blue-800 text-sm">
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
  name: 'AdminDashboard'
});

const calendarDays = ref(Array.from({ length: 35 }, (_, i) => ({
  date: i + 1,
  isToday: i + 1 === new Date().getDate(),
  hasShifts: [1, 5, 12, 15, 20, 25].includes(i + 1)
})));

const crewPositions = ref([
  { name: 'Camera Operators', count: 8, color: 'bg-blue-500' },
  { name: 'Directors', count: 3, color: 'bg-green-500' },
  { name: 'Producers', count: 4, color: 'bg-yellow-500' },
  { name: 'Audio Engineers', count: 5, color: 'bg-purple-500' },
  { name: 'Technical Directors', count: 2, color: 'bg-red-500' }
]);

const shiftCoverage = ref([
  { id: 1, title: 'Morning News', time: '6:00 AM - 9:00 AM', coverage: 100, coverageClass: 'bg-green-500' },
  { id: 2, title: 'Sports Coverage', time: '2:00 PM - 5:00 PM', coverage: 75, coverageClass: 'bg-yellow-500' },
  { id: 3, title: 'Evening News', time: '5:00 PM - 8:00 PM', coverage: 90, coverageClass: 'bg-green-500' },
  { id: 4, title: 'Late Night', time: '10:00 PM - 1:00 AM', coverage: 60, coverageClass: 'bg-red-500' }
]);

const recentActivities = ref([
  {
    id: 1,
    description: 'John Doe updated their availability',
    time: '2 hours ago',
    iconPath: 'M9 5H7a2 2 0 00-2 2v12a2 2 0 002 2h10a2 2 0 002-2V7a2 2 0 00-2-2h-2M9 5a2 2 0 002 2h2a2 2 0 002-2M9 5a2 2 0 012-2h2a2 2 0 012 2',
    iconBg: 'bg-blue-100',
    iconColor: 'text-blue-600'
  },
  {
    id: 2,
    description: 'New shift created: Morning Broadcast',
    time: '4 hours ago',
    iconPath: 'M8 7V3m8 4V3m-9 8h10M5 21h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v12a2 2 0 002 2z',
    iconBg: 'bg-green-100',
    iconColor: 'text-green-600'
  },
  {
    id: 3,
    description: 'Jane Smith requested time off',
    time: '1 day ago',
    iconPath: 'M12 8v4l3 3m6-3a9 9 0 11-18 0 9 9 0 0118 0z',
    iconBg: 'bg-yellow-100',
    iconColor: 'text-yellow-600'
  }
]);

const upcomingShifts = ref([
  {
    id: 1,
    title: 'Morning News Broadcast',
    date: 'Today',
    time: '6:00 AM - 9:00 AM',
    status: 'Fully Staffed',
    statusClass: 'bg-green-100 text-green-800'
  },
  {
    id: 2,
    title: 'Sports Coverage',
    date: 'Tomorrow',
    time: '2:00 PM - 5:00 PM',
    status: 'Needs Staff',
    statusClass: 'bg-red-100 text-red-800'
  },
  {
    id: 3,
    title: 'Evening News',
    date: 'Tomorrow',
    time: '5:00 PM - 8:00 PM',
    status: 'Partially Staffed',
    statusClass: 'bg-yellow-100 text-yellow-800'
  }
]);
</script> 