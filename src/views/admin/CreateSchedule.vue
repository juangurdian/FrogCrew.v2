<template>
  <div class="space-y-6">
    <div class="header">
      <h1 class="text-2xl font-bold text-gray-900">Create Schedule</h1>
      <p class="text-gray-600">Create and assign shifts for upcoming events</p>
    </div>

    <div class="grid grid-cols-1 lg:grid-cols-3 gap-6">
      <!-- Calendar Section -->
      <div class="lg:col-span-2">
        <div class="bg-white rounded-lg shadow">
          <div class="p-4 border-b border-gray-200">
            <div class="flex items-center justify-between mb-4">
              <h2 class="text-lg font-medium text-gray-900">Calendar</h2>
              <div class="flex space-x-2">
                <button 
                  @click="previousMonth"
                  class="p-2 text-gray-600 hover:text-gray-900"
                >
                  <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 19l-7-7 7-7" />
                  </svg>
                </button>
                <span class="text-lg font-medium">{{ currentMonthYear }}</span>
                <button 
                  @click="nextMonth"
                  class="p-2 text-gray-600 hover:text-gray-900"
                >
                  <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 5l7 7-7 7" />
                  </svg>
                </button>
              </div>
            </div>
            <div class="grid grid-cols-7 gap-px bg-gray-200">
              <div v-for="day in ['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat']" 
                   :key="day" 
                   class="bg-gray-50 p-2 text-center text-sm font-medium text-gray-500"
              >
                {{ day }}
              </div>
              <div v-for="date in calendarDays" 
                   :key="date.day"
                   :class="[
                     'bg-white p-2 h-32 cursor-pointer hover:bg-gray-50',
                     date.isCurrentMonth ? 'text-gray-900' : 'text-gray-400',
                     date.isToday ? 'bg-blue-50' : '',
                     selectedDate?.day === date.day ? 'ring-2 ring-blue-500' : ''
                   ]"
                   @click="selectDate(date)"
              >
                <div class="flex justify-between">
                  <span class="text-sm">{{ date.day }}</span>
                  <span v-if="date.shifts?.length" 
                        class="text-xs bg-blue-100 text-blue-800 px-1.5 rounded-full"
                  >
                    {{ date.shifts.length }}
                  </span>
                </div>
                <div class="mt-2 space-y-1">
                  <div v-for="shift in date.shifts" 
                       :key="shift.id"
                       class="text-xs p-1 rounded truncate"
                       :class="shift.type === 'sports' ? 'bg-green-100 text-green-800' : 'bg-purple-100 text-purple-800'"
                  >
                    {{ shift.title }}
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- Shift Creation Form -->
      <div class="bg-white rounded-lg shadow p-6">
        <h2 class="text-lg font-medium text-gray-900 mb-4">Create New Shift</h2>
        <form @submit.prevent="createShift" class="space-y-4">
          <div>
            <label class="block text-sm font-medium text-gray-700 mb-2">Event Title</label>
            <input 
              v-model="newShift.title"
              type="text" 
              required
              class="w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:ring-primary focus:border-primary"
              placeholder="e.g., Basketball Game Coverage"
            />
          </div>

          <div>
            <label class="block text-sm font-medium text-gray-700 mb-2">Event Type</label>
            <select 
              v-model="newShift.type"
              required
              class="w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:ring-primary focus:border-primary"
            >
              <option value="sports">Sports</option>
              <option value="news">News</option>
              <option value="special">Special Event</option>
            </select>
          </div>

          <div class="grid grid-cols-2 gap-4">
            <div>
              <label class="block text-sm font-medium text-gray-700 mb-2">Start Time</label>
              <input 
                v-model="newShift.startTime"
                type="time" 
                required
                class="w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:ring-primary focus:border-primary"
              />
            </div>
            <div>
              <label class="block text-sm font-medium text-gray-700 mb-2">End Time</label>
              <input 
                v-model="newShift.endTime"
                type="time" 
                required
                class="w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:ring-primary focus:border-primary"
              />
            </div>
          </div>

          <div>
            <label class="block text-sm font-medium text-gray-700 mb-2">Required Positions</label>
            <div class="space-y-2">
              <div v-for="position in positions" :key="position.id" class="flex items-center justify-between">
                <span class="text-sm text-gray-700">{{ position.name }}</span>
                <input 
                  v-model="newShift.positions[position.id]"
                  type="number" 
                  min="0"
                  class="w-20 px-2 py-1 border border-gray-300 rounded-md shadow-sm focus:ring-primary focus:border-primary"
                />
              </div>
            </div>
          </div>

          <div>
            <label class="block text-sm font-medium text-gray-700 mb-2">Notes</label>
            <textarea 
              v-model="newShift.notes"
              rows="3"
              class="w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:ring-primary focus:border-primary"
              placeholder="Any additional information about the shift..."
            ></textarea>
          </div>

          <div class="flex justify-end space-x-3">
            <button 
              type="button"
              @click="resetForm"
              class="px-4 py-2 text-sm bg-gray-100 rounded hover:bg-gray-200"
            >
              Clear
            </button>
            <button 
              type="submit"
              class="px-4 py-2 text-sm text-white bg-blue-600 rounded hover:bg-blue-700"
            >
              Create Shift
            </button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed } from 'vue';

interface CalendarDate {
  day: number;
  isCurrentMonth: boolean;
  isToday: boolean;
  shifts?: Shift[];
}

interface Shift {
  id: number;
  title: string;
  type: 'sports' | 'news' | 'special';
  startTime: string;
  endTime: string;
  positions: Record<number, number>;
  notes?: string;
}

interface Position {
  id: number;
  name: string;
}

defineOptions({
  name: 'CreateSchedule'
});

const currentDate = ref(new Date());
const selectedDate = ref<CalendarDate | null>(null);

const positions = ref<Position[]>([
  { id: 1, name: 'Camera Operator' },
  { id: 2, name: 'Audio Technician' },
  { id: 3, name: 'Director' },
  { id: 4, name: 'Producer' },
  { id: 5, name: 'Technical Director' }
]);

const newShift = ref<Shift>({
  id: 0,
  title: '',
  type: 'sports',
  startTime: '',
  endTime: '',
  positions: {},
  notes: ''
});

const currentMonthYear = computed(() => {
  return currentDate.value.toLocaleString('default', { month: 'long', year: 'numeric' });
});

const calendarDays = computed(() => {
  const year = currentDate.value.getFullYear();
  const month = currentDate.value.getMonth();
  const firstDay = new Date(year, month, 1);
  const lastDay = new Date(year, month + 1, 0);
  
  const days: CalendarDate[] = [];
  
  // Add days from previous month
  const firstDayOfWeek = firstDay.getDay();
  for (let i = firstDayOfWeek - 1; i >= 0; i--) {
    const date = new Date(year, month, -i);
    days.push({
      day: date.getDate(),
      isCurrentMonth: false,
      isToday: isSameDate(date, new Date()),
      shifts: []
    });
  }
  
  // Add days of current month
  for (let i = 1; i <= lastDay.getDate(); i++) {
    const date = new Date(year, month, i);
    days.push({
      day: i,
      isCurrentMonth: true,
      isToday: isSameDate(date, new Date()),
      shifts: []
    });
  }
  
  // Add days from next month
  const remainingDays = 42 - days.length; // 6 rows × 7 days
  for (let i = 1; i <= remainingDays; i++) {
    const date = new Date(year, month + 1, i);
    days.push({
      day: i,
      isCurrentMonth: false,
      isToday: isSameDate(date, new Date()),
      shifts: []
    });
  }
  
  return days;
});

const isSameDate = (date1: Date, date2: Date) => {
  return date1.getDate() === date2.getDate() &&
         date1.getMonth() === date2.getMonth() &&
         date1.getFullYear() === date2.getFullYear();
};

const previousMonth = () => {
  currentDate.value = new Date(
    currentDate.value.getFullYear(),
    currentDate.value.getMonth() - 1,
    1
  );
};

const nextMonth = () => {
  currentDate.value = new Date(
    currentDate.value.getFullYear(),
    currentDate.value.getMonth() + 1,
    1
  );
};

const selectDate = (date: CalendarDate) => {
  selectedDate.value = date;
};

const createShift = () => {
  if (!selectedDate.value) {
    alert('Please select a date first');
    return;
  }

  // Here you would typically make an API call to save the shift
  console.log('Creating shift:', {
    ...newShift.value,
    date: selectedDate.value.day
  });

  // Add the shift to the calendar
  if (!selectedDate.value.shifts) {
    selectedDate.value.shifts = [];
  }
  selectedDate.value.shifts.push({
    ...newShift.value,
    id: Date.now() // Temporary ID generation
  });

  resetForm();
};

const resetForm = () => {
  newShift.value = {
    id: 0,
    title: '',
    type: 'sports',
    startTime: '',
    endTime: '',
    positions: {},
    notes: ''
  };
  selectedDate.value = null;
};
</script> 