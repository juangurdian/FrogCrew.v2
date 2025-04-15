<template>
  <div class="space-y-6">
    <!-- Header -->
    <div class="flex justify-between items-center">
      <h1 class="text-2xl font-bold text-gray-900">My Availability</h1>
      <button 
        @click="saveAvailability" 
        class="px-4 py-2 bg-[#4d2e7d] text-white rounded-lg hover:bg-purple-800 transition-colors"
      >
        Save Changes
      </button>
    </div>

    <!-- Preferences Section -->
    <div class="bg-white rounded-lg shadow-sm">
      <div class="p-4">
        <h2 class="text-lg font-medium text-gray-900 mb-4">Preferences</h2>
        <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
          <!-- Position Preferences -->
          <div>
            <h3 class="text-sm font-medium text-gray-700 mb-2">Preferred Positions</h3>
            <div class="space-y-2">
              <label v-for="position in positions" :key="position.value" class="flex items-center space-x-2">
                <input 
                  type="checkbox" 
                  v-model="preferences.positions"
                  :value="position.value"
                  class="rounded text-[#4d2e7d] focus:ring-[#4d2e7d]"
                >
                <span class="text-sm text-gray-600">{{ position.label }}</span>
              </label>
            </div>
          </div>

          <!-- Shift Type Preferences -->
          <div>
            <h3 class="text-sm font-medium text-gray-700 mb-2">Preferred Shift Types</h3>
            <div class="space-y-2">
              <label v-for="type in shiftTypes" :key="type.value" class="flex items-center space-x-2">
                <input 
                  type="checkbox" 
                  v-model="preferences.shiftTypes"
                  :value="type.value"
                  class="rounded text-[#4d2e7d] focus:ring-[#4d2e7d]"
                >
                <span class="text-sm text-gray-600">{{ type.label }}</span>
              </label>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- Weekly Schedule -->
    <div class="bg-white rounded-lg shadow-sm">
      <div class="p-4">
        <h2 class="text-lg font-medium text-gray-900 mb-4">Weekly Schedule</h2>
        
        <!-- Days Selection -->
        <div class="flex justify-between mb-6">
          <div v-for="day in days" :key="day.value" class="flex flex-col items-center">
            <button
              @click="availability[day.value].isAvailable = !availability[day.value].isAvailable"
              :class="[
                'w-12 h-12 rounded-lg flex items-center justify-center transition-colors',
                availability[day.value].isAvailable 
                  ? 'bg-[#4d2e7d] text-white' 
                  : 'bg-gray-100 text-gray-600 hover:bg-gray-200'
              ]"
            >
              <span class="text-sm font-medium">{{ day.label.charAt(0) }}</span>
            </button>
            <span class="text-xs text-gray-500 mt-1">{{ day.label }}</span>
          </div>
        </div>

        <!-- Time Slots for Selected Days -->
        <div class="space-y-4">
          <div v-for="day in days" :key="day.value">
            <div v-if="availability[day.value].isAvailable" class="border rounded-lg p-4">
              <div class="flex items-center justify-between mb-3">
                <h3 class="font-medium text-gray-900">{{ day.label }}</h3>
              </div>
              
              <!-- Time Slots -->
              <div class="space-y-4">
                <div v-for="(timeSlot, index) in availability[day.value].timeSlots" 
                     :key="index" 
                     class="border rounded-lg p-4 bg-gray-50">
                  <div class="flex justify-between items-start mb-3">
                    <h4 class="text-sm font-medium text-gray-700">Time Slot {{ index + 1 }}</h4>
                    <button 
                      v-if="index > 0"
                      @click="removeTimeSlot(day.value, index)"
                      class="text-red-500 hover:text-red-700"
                    >
                      <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 7l-.867 12.142A2 2 0 0116.138 21H7.862a2 2 0 01-1.995-1.858L5 7m5 4v6m4-6v6m1-10V4a1 1 0 00-1-1h-4a1 1 0 00-1 1v3M4 7h16" />
                      </svg>
                    </button>
                  </div>

                  <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
                    <!-- Time Range -->
                    <div>
                      <label class="block text-sm font-medium text-gray-700 mb-1">Time Range</label>
                      <div class="flex items-center space-x-2">
                        <select 
                          v-model="timeSlot.startTime"
                          class="rounded-lg border-gray-300 text-sm focus:border-[#4d2e7d] focus:ring-[#4d2e7d]"
                        >
                          <option v-for="time in timeOptions" :key="time" :value="time">
                            {{ time }}
                          </option>
                        </select>
                        <span class="text-gray-500">to</span>
                        <select 
                          v-model="timeSlot.endTime"
                          class="rounded-lg border-gray-300 text-sm focus:border-[#4d2e7d] focus:ring-[#4d2e7d]"
                        >
                          <option v-for="time in timeOptions" :key="time" :value="time">
                            {{ time }}
                          </option>
                        </select>
                      </div>
                    </div>

                    <!-- Position Preference -->
                    <div>
                      <label class="block text-sm font-medium text-gray-700 mb-1">Position Preference</label>
                      <select 
                        v-model="timeSlot.positionPreference"
                        class="w-full rounded-lg border-gray-300 text-sm focus:border-[#4d2e7d] focus:ring-[#4d2e7d]"
                      >
                        <option value="">Any Position</option>
                        <option v-for="position in positions" 
                                :key="position.value" 
                                :value="position.value">
                          {{ position.label }}
                        </option>
                      </select>
                    </div>

                    <!-- Shift Type Preference -->
                    <div>
                      <label class="block text-sm font-medium text-gray-700 mb-1">Shift Type Preference</label>
                      <select 
                        v-model="timeSlot.shiftTypePreference"
                        class="w-full rounded-lg border-gray-300 text-sm focus:border-[#4d2e7d] focus:ring-[#4d2e7d]"
                      >
                        <option value="">Any Type</option>
                        <option v-for="type in shiftTypes" 
                                :key="type.value" 
                                :value="type.value">
                          {{ type.label }}
                        </option>
                      </select>
                    </div>

                    <!-- Notes -->
                    <div>
                      <label class="block text-sm font-medium text-gray-700 mb-1">Notes</label>
                      <input 
                        type="text" 
                        v-model="timeSlot.notes"
                        placeholder="Add notes for this time slot..."
                        class="w-full rounded-lg border-gray-300 text-sm focus:border-[#4d2e7d] focus:ring-[#4d2e7d]"
                      >
                    </div>
                  </div>
                </div>

                <button 
                  @click="addTimeSlot(day.value)"
                  class="text-[#4d2e7d] hover:text-purple-800 text-sm font-medium flex items-center space-x-1"
                >
                  <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 4v16m8-8H4" />
                  </svg>
                  <span>Add Time Slot</span>
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- Notes Section -->
    <div class="bg-white rounded-lg shadow-sm">
      <div class="p-4">
        <h2 class="text-lg font-medium text-gray-900 mb-4">Additional Notes</h2>
        <textarea 
          v-model="notes"
          rows="4"
          class="w-full rounded-lg border-gray-300 focus:border-[#4d2e7d] focus:ring-[#4d2e7d]"
          placeholder="Add any additional notes about your availability..."
        ></textarea>
      </div>
    </div>

    <!-- Recurring Schedule -->
    <div class="bg-white rounded-lg shadow-sm">
      <div class="p-4">
        <h2 class="text-lg font-medium text-gray-900 mb-4">Recurring Schedule</h2>
        <div class="space-y-4">
          <div class="flex items-center space-x-4">
            <label class="flex items-center space-x-2">
              <input 
                type="checkbox" 
                v-model="hasRecurringSchedule"
                class="rounded text-[#4d2e7d] focus:ring-[#4d2e7d]"
              >
              <span class="text-sm text-gray-600">I have a recurring schedule</span>
            </label>
          </div>
          <div v-if="hasRecurringSchedule" class="space-y-4">
            <div v-for="(schedule, index) in recurringSchedules" 
                 :key="index" 
                 class="border rounded-lg p-4">
              <div class="flex justify-between items-start mb-3">
                <h3 class="font-medium text-gray-900">Schedule {{ index + 1 }}</h3>
                <button 
                  @click="removeRecurringSchedule(index)"
                  class="text-red-500 hover:text-red-700"
                >
                  <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 7l-.867 12.142A2 2 0 0116.138 21H7.862a2 2 0 01-1.995-1.858L5 7m5 4v6m4-6v6m1-10V4a1 1 0 00-1-1h-4a1 1 0 00-1 1v3M4 7h16" />
                  </svg>
                </button>
              </div>
              <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
                <div>
                  <label class="block text-sm font-medium text-gray-700 mb-1">Start Date</label>
                  <input 
                    type="date" 
                    v-model="schedule.startDate"
                    class="w-full rounded-lg border-gray-300 focus:border-[#4d2e7d] focus:ring-[#4d2e7d]"
                  >
                </div>
                <div>
                  <label class="block text-sm font-medium text-gray-700 mb-1">End Date</label>
                  <input 
                    type="date" 
                    v-model="schedule.endDate"
                    class="w-full rounded-lg border-gray-300 focus:border-[#4d2e7d] focus:ring-[#4d2e7d]"
                  >
                </div>
              </div>
            </div>
            <button 
              @click="addRecurringSchedule"
              class="text-[#4d2e7d] hover:text-purple-800 text-sm font-medium flex items-center space-x-1"
            >
              <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 4v16m8-8H4" />
              </svg>
              <span>Add Recurring Schedule</span>
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue';

defineOptions({
  name: 'Availability'
});

const days = [
  { label: 'Monday', value: 'monday' },
  { label: 'Tuesday', value: 'tuesday' },
  { label: 'Wednesday', value: 'wednesday' },
  { label: 'Thursday', value: 'thursday' },
  { label: 'Friday', value: 'friday' },
  { label: 'Saturday', value: 'saturday' },
  { label: 'Sunday', value: 'sunday' }
];

const positions = [
  { label: 'Camera Operator', value: 'camera_operator' },
  { label: 'Director', value: 'director' },
  { label: 'Producer', value: 'producer' },
  { label: 'Audio Engineer', value: 'audio_engineer' },
  { label: 'Technical Director', value: 'technical_director' },
  { label: 'Graphics Operator', value: 'graphics_operator' },
  { label: 'Replay Operator', value: 'replay_operator' }
];

const shiftTypes = [
  { label: 'Morning Shift', value: 'morning' },
  { label: 'Afternoon Shift', value: 'afternoon' },
  { label: 'Evening Shift', value: 'evening' },
  { label: 'Night Shift', value: 'night' },
  { label: 'Weekend Shift', value: 'weekend' },
  { label: 'Special Event', value: 'special_event' }
];

// Generate time options in 30-minute intervals
const timeOptions = Array.from({ length: 48 }, (_, i) => {
  const hours = Math.floor(i / 2);
  const minutes = (i % 2) * 30;
  return `${hours.toString().padStart(2, '0')}:${minutes.toString().padStart(2, '0')}`;
});

const preferences = ref({
  positions: [] as string[],
  shiftTypes: [] as string[]
});

const availability = ref(
  days.reduce((acc, day) => {
    acc[day.value] = {
      isAvailable: false,
      timeSlots: [{
        startTime: '09:00',
        endTime: '17:00',
        positionPreference: '',
        shiftTypePreference: '',
        notes: ''
      }]
    };
    return acc;
  }, {} as Record<string, { 
    isAvailable: boolean; 
    timeSlots: { 
      startTime: string; 
      endTime: string;
      positionPreference: string;
      shiftTypePreference: string;
      notes: string;
    }[] 
  }>)
);

const notes = ref('');
const hasRecurringSchedule = ref(false);
const recurringSchedules = ref([
  { startDate: '', endDate: '' }
]);

const addTimeSlot = (day: string) => {
  availability.value[day].timeSlots.push({
    startTime: '09:00',
    endTime: '17:00',
    positionPreference: '',
    shiftTypePreference: '',
    notes: ''
  });
};

const removeTimeSlot = (day: string, index: number) => {
  availability.value[day].timeSlots.splice(index, 1);
};

const addRecurringSchedule = () => {
  recurringSchedules.value.push({ startDate: '', endDate: '' });
};

const removeRecurringSchedule = (index: number) => {
  recurringSchedules.value.splice(index, 1);
};

const saveAvailability = () => {
  // TODO: Implement save functionality
  console.log('Saving availability:', {
    preferences: preferences.value,
    availability: availability.value,
    notes: notes.value,
    recurringSchedules: recurringSchedules.value
  });
};
</script> 