<template>
  <div class="space-y-4">
    <!-- Loading state -->
    <div v-if="loading" class="flex justify-center py-8">
      <div class="spinner"></div>
    </div>

    <!-- Error state -->
    <div v-else-if="error" class="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded relative" role="alert">
      <strong class="font-bold">Error:</strong>
      <span class="block sm:inline">{{ error }}</span>
      <button @click="loadDashboardData" class="px-4 py-2 bg-red-600 text-white rounded-md mt-2">Retry</button>
    </div>

    <!-- Dashboard content -->
    <div v-else>
      <!-- Summary Cards -->
      <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
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
              <p class="text-2xl font-semibold text-gray-900">{{ summary.totalCrewMembers || 0 }}</p>
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
              <p class="text-2xl font-semibold text-gray-900">{{ summary.upcomingShifts || 0 }}</p>
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
              <p class="text-2xl font-semibold text-gray-900">{{ summary.availableCrew || 0 }}</p>
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
              <p class="text-2xl font-semibold text-gray-900">{{ summary.pendingRequests || 0 }}</p>
            </div>
          </div>
        </div>
      </div>

      <!-- Calendar and Crew Status -->
      <div class="grid grid-cols-1 lg:grid-cols-3 gap-6 mt-6">
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
                   day.hasShifts ? 'font-bold' : '',
                   day.date === 0 ? 'text-gray-300' : ''
                 ]">
                {{ day.date !== 0 ? day.date : '' }}
              </div>
            </div>
          </div>
        </div>

        <!-- Crew Status Overview -->
        <div class="bg-white rounded-lg shadow-sm">
          <div class="p-4">
            <h2 class="text-lg font-medium text-gray-900 mb-4">Crew Status</h2>
            <div v-if="crewPositions.length === 0" class="text-center py-4 text-gray-500">
              No crew position data available
            </div>
            <div v-else class="space-y-4">
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
            <div v-if="shiftCoverage.length === 0" class="text-center py-4 text-gray-500">
              No shift coverage data available
            </div>
            <div v-else class="space-y-4">
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
      <div class="grid grid-cols-1 lg:grid-cols-2 gap-6 mt-6">
        <!-- Recent Activity -->
        <div class="bg-white rounded-lg shadow-sm">
          <div class="p-4">
            <h2 class="text-lg font-medium text-gray-900 mb-4">Recent Activity</h2>
            <div v-if="recentActivities.length === 0" class="text-center py-4 text-gray-500">
              No recent activities
            </div>
            <div v-else class="space-y-4">
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
            <div v-if="upcomingShifts.length === 0" class="text-center py-4 text-gray-500">
              No upcoming shifts
            </div>
            <div v-else class="space-y-4">
              <div v-for="shift in upcomingShifts" :key="shift.id" class="flex items-center justify-between">
                <div>
                  <p class="text-sm font-medium text-gray-900">{{ shift.title }}</p>
                  <p class="text-xs text-gray-500">{{ shift.date }} at {{ shift.time }}</p>
                </div>
                <div class="flex items-center space-x-2">
                  <span class="px-2 py-1 text-xs rounded-full" :class="shift.statusClass">
                    {{ shift.status }}
                  </span>
                  <button class="text-blue-600 hover:text-blue-800 text-sm" @click="viewShiftDetails(shift.id)">
                    View Details
                  </button>
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
import { ref, onMounted } from 'vue';
import { dashboardService } from '@/services/dashboardService';

defineOptions({
  name: 'AdminDashboard'
});

const summary = ref({
  totalCrewMembers: 0,
  upcomingShifts: 0,
  pendingRequests: 0,
  availableCrew: 0
});

const calendarDays = ref([]);
const crewPositions = ref([]);
const shiftCoverage = ref([]);
const recentActivities = ref([]);
const upcomingShifts = ref([]);

const loading = ref(true);
const error = ref('');

// Function to load dashboard data
const loadDashboardData = async () => {
  loading.value = true;
  error.value = '';
  
  try {
    console.log('Fetching dashboard data...');
    const response = await dashboardService.getDashboard();
    console.log('Dashboard API response:', response);
    
    const data = response.data;
    console.log('Dashboard data:', data);
    
    // Update state with received data
    summary.value = data.summary || {};
    calendarDays.value = data.calendarDays || [];
    crewPositions.value = data.crewPositions || [];
    shiftCoverage.value = data.shiftCoverage || [];
    recentActivities.value = data.recentActivities || [];
    upcomingShifts.value = data.upcomingShifts || [];
    
  } catch (err: any) {
    console.error('Error loading dashboard data:', err);
    console.error('Error response:', err.response);
    console.error('Error status:', err.response?.status);
    console.error('Error data:', err.response?.data);
    
    let errorMessage = 'Failed to load dashboard data. ';
    if (err.response?.status === 401) {
      errorMessage += 'Your session has expired. Please log in again.';
    } else if (err.response?.status === 403) {
      errorMessage += 'You do not have permission to view this data.';
    } else if (err.response?.data?.message) {
      errorMessage += err.response.data.message;
    } else {
      errorMessage += 'Check your connection and permissions.';
    }
    
    error.value = errorMessage;
  } finally {
    loading.value = false;
  }
};

// Function to view shift details
const viewShiftDetails = (shiftId: number) => {
  // This would typically navigate to a shift details page
  console.log('View shift details:', shiftId);
  // router.push(`/admin/shifts/${shiftId}`);
};

// Load data when component mounts
onMounted(() => {
  loadDashboardData();
});
</script>

<style scoped>
.spinner {
  border: 4px solid rgba(0, 0, 0, 0.1);
  width: 36px;
  height: 36px;
  border-radius: 50%;
  border-left-color: #09f;
  animation: spin 1s linear infinite;
}

@keyframes spin {
  0% {
    transform: rotate(0deg);
  }
  100% {
    transform: rotate(360deg);
  }
}
</style> 