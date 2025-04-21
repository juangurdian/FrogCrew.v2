<template>
  <div class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50" @click.self="closeModal">
    <div class="bg-white rounded-lg shadow-lg max-w-2xl w-full max-h-[90vh] overflow-auto">
      <!-- Header -->
      <div class="bg-gray-100 px-6 py-4 rounded-t-lg flex justify-between items-center">
        <h2 class="text-xl font-semibold text-gray-800">Shift Details</h2>
        <button @click="closeModal" class="text-gray-500 hover:text-gray-700">
          <svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12"></path>
          </svg>
        </button>
      </div>

      <!-- Loading State -->
      <div v-if="loading" class="p-6 flex justify-center">
        <div class="spinner"></div>
      </div>

      <!-- Error State -->
      <div v-else-if="error" class="p-6 bg-red-100 text-red-700">
        <p class="font-bold">Error loading shift details</p>
        <p>{{ error }}</p>
      </div>

      <!-- Shift Content -->
      <div v-else-if="shift" class="p-6">
        <div class="space-y-6">
          <!-- Basic Details -->
          <div>
            <h3 class="text-lg font-medium text-gray-900">{{ shift.title }}</h3>
            <div class="mt-2 grid grid-cols-2 gap-4 text-sm">
              <div>
                <p class="text-gray-500">Date</p>
                <p class="font-medium">{{ shift.date }}</p>
              </div>
              <div>
                <p class="text-gray-500">Time</p>
                <p class="font-medium">{{ shift.time }}</p>
              </div>
              <div>
                <p class="text-gray-500">Location</p>
                <p class="font-medium">{{ shift.location || 'Not specified' }}</p>
              </div>
              <div>
                <p class="text-gray-500">Staff Coverage</p>
                <div class="flex items-center">
                  <div class="w-24 bg-gray-200 rounded-full h-2.5 mr-2">
                    <div class="h-2.5 rounded-full" 
                      :class="getCoverageClass(shift.currentStaff, shift.requiredStaff)" 
                      :style="{width: getCoveragePercentage(shift.currentStaff, shift.requiredStaff) + '%'}">
                    </div>
                  </div>
                  <span>{{ shift.currentStaff }}/{{ shift.requiredStaff }}</span>
                </div>
              </div>
            </div>
          </div>

          <!-- Staff Assignment Section -->
          <div>
            <h4 class="text-md font-medium text-gray-900 mb-2">Assigned Staff</h4>
            <div v-if="shift.assignments && shift.assignments.length > 0" class="border rounded-md overflow-hidden">
              <table class="min-w-full divide-y divide-gray-200">
                <thead class="bg-gray-50">
                  <tr>
                    <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Name</th>
                    <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Position</th>
                    <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Status</th>
                  </tr>
                </thead>
                <tbody class="bg-white divide-y divide-gray-200">
                  <tr v-for="(assignment, index) in shift.assignments" :key="index">
                    <td class="px-6 py-4 whitespace-nowrap">{{ assignment.name }}</td>
                    <td class="px-6 py-4 whitespace-nowrap">{{ assignment.position }}</td>
                    <td class="px-6 py-4 whitespace-nowrap">
                      <span :class="getStatusClass(assignment.status)" class="px-2 py-1 text-xs rounded-full">
                        {{ assignment.status }}
                      </span>
                    </td>
                  </tr>
                </tbody>
              </table>
            </div>
            <div v-else class="text-center p-4 text-gray-500 border rounded-md">
              No staff assigned to this shift
            </div>
          </div>

          <!-- Notes Section (if available) -->
          <div v-if="shift.notes">
            <h4 class="text-md font-medium text-gray-900 mb-2">Notes</h4>
            <div class="border rounded-md p-4 bg-gray-50">
              <p class="text-sm">{{ shift.notes }}</p>
            </div>
          </div>
        </div>
      </div>

      <!-- Actions -->
      <div class="bg-gray-100 px-6 py-4 rounded-b-lg flex justify-end space-x-2">
        <button @click="closeModal" class="px-4 py-2 border border-gray-300 rounded-md text-sm font-medium text-gray-700 bg-white hover:bg-gray-50">
          Close
        </button>
        <button @click="editShift" class="px-4 py-2 border border-transparent rounded-md text-sm font-medium text-white bg-blue-600 hover:bg-blue-700">
          Edit Shift
        </button>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, defineProps, defineEmits } from 'vue';
import shiftService from '@/services/shiftService';

const props = defineProps({
  shiftId: {
    type: Number,
    required: true
  }
});

const emit = defineEmits(['close', 'edit']);

const loading = ref(true);
const error = ref('');
const shift = ref<any>(null);

const fetchShiftDetails = async () => {
  loading.value = true;
  error.value = '';
  
  try {
    const response = await shiftService.getShiftById(props.shiftId);
    console.log('API response:', response);
    
    // Get assignments for this shift
    const assignmentsResponse = await shiftService.getShiftAssignments(props.shiftId);
    
    // Process the data
    shift.value = {
      ...response.data,
      assignments: assignmentsResponse.data || []
    };
    
  } catch (err) {
    console.error('Error fetching shift details:', err);
    error.value = 'Failed to load shift details';
  } finally {
    loading.value = false;
  }
};

const closeModal = () => {
  emit('close');
};

const editShift = () => {
  emit('edit', props.shiftId);
};

const getCoveragePercentage = (current, required) => {
  if (required === 0) return 100;
  return Math.min(100, (current / required) * 100);
};

const getCoverageClass = (current, required) => {
  const percentage = getCoveragePercentage(current, required);
  if (percentage >= 90) return 'bg-green-500';
  if (percentage >= 70) return 'bg-yellow-500';
  return 'bg-red-500';
};

const getStatusClass = (status) => {
  switch (status.toLowerCase()) {
    case 'confirmed':
      return 'bg-green-100 text-green-800';
    case 'pending':
      return 'bg-yellow-100 text-yellow-800';
    case 'cancelled':
      return 'bg-red-100 text-red-800';
    default:
      return 'bg-gray-100 text-gray-800';
  }
};

onMounted(() => {
  fetchShiftDetails();
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