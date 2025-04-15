<template>
  <div class="space-y-6">
    <!-- Header -->
    <div class="flex justify-between items-center">
      <h1 class="text-2xl font-bold text-gray-900">Events</h1>
      <div class="flex items-center space-x-4">
        <!-- View Toggle -->
        <div class="flex items-center space-x-2 bg-white rounded-lg p-1">
          <button
            @click="currentView = 'calendar'"
            :class="[
              'px-3 py-1 rounded-md text-sm font-medium transition-colors',
              currentView === 'calendar' 
                ? 'bg-[#4d2e7d] text-white' 
                : 'text-gray-600 hover:bg-gray-100'
            ]"
          >
            Calendar
          </button>
          <button
            @click="currentView = 'list'"
            :class="[
              'px-3 py-1 rounded-md text-sm font-medium transition-colors',
              currentView === 'list' 
                ? 'bg-[#4d2e7d] text-white' 
                : 'text-gray-600 hover:bg-gray-100'
            ]"
          >
            List
          </button>
        </div>
      </div>
    </div>

    <!-- Calendar View -->
    <div v-if="currentView === 'calendar'" class="bg-white rounded-lg shadow-sm">
      <div class="p-4">
        <div class="flex items-center justify-between mb-4">
          <h2 class="text-lg font-medium text-gray-900">{{ currentMonth }} {{ currentYear }}</h2>
          <div class="flex items-center space-x-2">
            <button 
              @click="previousMonth"
              class="p-2 rounded-lg hover:bg-gray-100"
            >
              <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 19l-7-7 7-7" />
              </svg>
            </button>
            <button 
              @click="nextMonth"
              class="p-2 rounded-lg hover:bg-gray-100"
            >
              <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 5l7 7-7 7" />
              </svg>
            </button>
          </div>
        </div>

        <!-- Calendar Grid -->
        <div class="grid grid-cols-7 gap-1">
          <!-- Weekday Headers -->
          <div v-for="day in weekDays" :key="day" class="text-center text-sm font-medium text-gray-500 py-2">
            {{ day }}
          </div>

          <!-- Calendar Days -->
          <div 
            v-for="(day, index) in calendarDays" 
            :key="index"
            :class="[
              'p-2 min-h-[100px] border rounded-lg',
              day.isCurrentMonth ? 'bg-white' : 'bg-gray-50',
              day.isToday ? 'border-[#4d2e7d]' : 'border-gray-200'
            ]"
          >
            <div class="flex justify-between items-start">
              <span :class="[
                'text-sm font-medium',
                day.isToday ? 'text-[#4d2e7d]' : 'text-gray-900'
              ]">
                {{ day.date }}
              </span>
              <span v-if="day.hasEvents" class="w-2 h-2 rounded-full bg-[#4d2e7d]"></span>
            </div>

            <!-- Events for the Day -->
            <div class="mt-2 space-y-1">
              <div 
                v-for="event in day.events" 
                :key="event.id"
                class="text-xs p-1 rounded bg-[#4d2e7d] text-white truncate"
                @click="openEventDetails(event)"
              >
                {{ event.title }}
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- List View -->
    <div v-else class="space-y-4">
      <!-- Upcoming Events -->
      <div class="bg-white rounded-lg shadow-sm">
        <div class="p-4">
          <h2 class="text-lg font-medium text-gray-900 mb-4">Upcoming Events</h2>
          <div class="space-y-4">
            <div 
              v-for="event in upcomingEvents" 
              :key="event.id"
              class="border rounded-lg p-4 hover:bg-gray-50 cursor-pointer"
              @click="openEventDetails(event)"
            >
              <div class="flex justify-between items-start">
                <div>
                  <h3 class="text-lg font-medium text-gray-900">{{ event.title }}</h3>
                  <p class="text-sm text-gray-500">{{ event.date }} at {{ event.time }}</p>
                </div>
                <span class="px-2 py-1 text-xs rounded-full" :class="event.statusClass">
                  {{ event.status }}
                </span>
              </div>
              <div class="mt-2 flex items-center space-x-4">
                <div class="flex items-center text-sm text-gray-500">
                  <svg class="w-4 h-4 mr-1" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M17.657 16.657L13.414 20.9a1.998 1.998 0 01-2.827 0l-4.244-4.243a8 8 0 1111.314 0z" />
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 11a3 3 0 11-6 0 3 3 0 016 0z" />
                  </svg>
                  {{ event.location }}
                </div>
                <div class="flex items-center text-sm text-gray-500">
                  <svg class="w-4 h-4 mr-1" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 4.354a4 4 0 110 5.292M15 21H3v-1a6 6 0 0112 0v1zm0 0h6v-1a6 6 0 00-9-5.197M13 7a4 4 0 11-8 0 4 4 0 018 0z" />
                  </svg>
                  {{ event.assignedCrew }} crew members
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- Past Events -->
      <div class="bg-white rounded-lg shadow-sm">
        <div class="p-4">
          <h2 class="text-lg font-medium text-gray-900 mb-4">Past Events</h2>
          <div class="space-y-4">
            <div 
              v-for="event in pastEvents" 
              :key="event.id"
              class="border rounded-lg p-4 hover:bg-gray-50 cursor-pointer"
              @click="openEventDetails(event)"
            >
              <div class="flex justify-between items-start">
                <div>
                  <h3 class="text-lg font-medium text-gray-900">{{ event.title }}</h3>
                  <p class="text-sm text-gray-500">{{ event.date }} at {{ event.time }}</p>
                </div>
                <span class="px-2 py-1 text-xs rounded-full bg-gray-100 text-gray-600">
                  Completed
                </span>
              </div>
              <div class="mt-2 flex items-center space-x-4">
                <div class="flex items-center text-sm text-gray-500">
                  <svg class="w-4 h-4 mr-1" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M17.657 16.657L13.414 20.9a1.998 1.998 0 01-2.827 0l-4.244-4.243a8 8 0 1111.314 0z" />
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 11a3 3 0 11-6 0 3 3 0 016 0z" />
                  </svg>
                  {{ event.location }}
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- Event Details Modal -->
    <div v-if="selectedEvent" class="fixed inset-0 bg-gray-500 bg-opacity-75 flex items-center justify-center">
      <div class="bg-white rounded-lg max-w-2xl w-full mx-4">
        <div class="p-6">
          <div class="flex justify-between items-start mb-4">
            <h2 class="text-xl font-bold text-gray-900">{{ selectedEvent.title }}</h2>
            <button @click="selectedEvent = null" class="text-gray-400 hover:text-gray-500">
              <svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12" />
              </svg>
            </button>
          </div>
          
          <div class="space-y-4">
            <div class="flex items-center space-x-4">
              <div class="flex items-center text-gray-500">
                <svg class="w-5 h-5 mr-2" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M8 7V3m8 4V3m-9 8h10M5 21h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v12a2 2 0 002 2z" />
                </svg>
                {{ selectedEvent.date }} at {{ selectedEvent.time }}
              </div>
              <div class="flex items-center text-gray-500">
                <svg class="w-5 h-5 mr-2" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M17.657 16.657L13.414 20.9a1.998 1.998 0 01-2.827 0l-4.244-4.243a8 8 0 1111.314 0z" />
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 11a3 3 0 11-6 0 3 3 0 016 0z" />
                </svg>
                {{ selectedEvent.location }}
              </div>
            </div>

            <div class="border-t pt-4">
              <h3 class="text-lg font-medium text-gray-900 mb-2">Event Details</h3>
              <p class="text-gray-600">{{ selectedEvent.description }}</p>
            </div>

            <div class="border-t pt-4">
              <h3 class="text-lg font-medium text-gray-900 mb-2">Crew Assignments</h3>
              <div class="space-y-2">
                <div v-for="assignment in selectedEvent.crewAssignments" 
                     :key="assignment.id"
                     class="flex items-center justify-between p-2 bg-gray-50 rounded-lg">
                  <div class="flex items-center">
                    <div class="w-8 h-8 rounded-full bg-gray-200 flex items-center justify-center mr-3">
                      <span class="text-sm font-medium text-gray-600">
                        {{ assignment.name.charAt(0) }}
                      </span>
                    </div>
                    <div>
                      <p class="text-sm font-medium text-gray-900">{{ assignment.name }}</p>
                      <p class="text-xs text-gray-500">{{ assignment.position }}</p>
                    </div>
                  </div>
                  <span class="text-sm text-gray-500">{{ assignment.shift }}</span>
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

interface CrewAssignment {
  id: number;
  name: string;
  position: string;
  shift: string;
}

interface Event {
  id: number;
  title: string;
  date: string;
  time: string;
  location: string;
  status: string;
  statusClass: string;
  description: string;
  assignedCrew: number;
  crewAssignments: CrewAssignment[];
}

defineOptions({
  name: 'Events'
});

const currentView = ref<'calendar' | 'list'>('calendar');
const currentDate = ref(new Date());
const selectedEvent = ref<Event | null>(null);

const weekDays = ['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'];
const months = [
  'January', 'February', 'March', 'April', 'May', 'June',
  'July', 'August', 'September', 'October', 'November', 'December'
];

const currentMonth = computed(() => months[currentDate.value.getMonth()]);
const currentYear = computed(() => currentDate.value.getFullYear());

const calendarDays = computed(() => {
  const year = currentDate.value.getFullYear();
  const month = currentDate.value.getMonth();
  
  // Get first day of the month
  const firstDay = new Date(year, month, 1);
  const startingDay = firstDay.getDay();
  
  // Get last day of the month
  const lastDay = new Date(year, month + 1, 0);
  const totalDays = lastDay.getDate();
  
  // Get days from previous month
  const prevMonth = new Date(year, month, 0);
  const prevMonthDays = prevMonth.getDate();
  
  const days = [];
  
  // Add days from previous month
  for (let i = startingDay - 1; i >= 0; i--) {
    days.push({
      date: prevMonthDays - i,
      isCurrentMonth: false,
      isToday: false,
      hasEvents: false,
      events: []
    });
  }
  
  // Add days from current month
  const today = new Date();
  for (let i = 1; i <= totalDays; i++) {
    const hasEvents = events.value.some(event => {
      const eventDate = new Date(event.date);
      return eventDate.getDate() === i && 
             eventDate.getMonth() === month && 
             eventDate.getFullYear() === year;
    });
    
    const dayEvents = events.value.filter(event => {
      const eventDate = new Date(event.date);
      return eventDate.getDate() === i && 
             eventDate.getMonth() === month && 
             eventDate.getFullYear() === year;
    });
    
    days.push({
      date: i,
      isCurrentMonth: true,
      isToday: i === today.getDate() && 
               month === today.getMonth() && 
               year === today.getFullYear(),
      hasEvents,
      events: dayEvents
    });
  }
  
  // Add days from next month
  const remainingDays = 42 - days.length; // 6 rows * 7 days
  for (let i = 1; i <= remainingDays; i++) {
    days.push({
      date: i,
      isCurrentMonth: false,
      isToday: false,
      hasEvents: false,
      events: []
    });
  }
  
  return days;
});

const events = ref([
  {
    id: 1,
    title: 'Football Game vs Texas',
    date: '2024-09-15',
    time: '7:00 PM',
    location: 'Amon G. Carter Stadium',
    status: 'Upcoming',
    statusClass: 'bg-green-100 text-green-800',
    description: 'TCU vs Texas football game. Full crew required for broadcast.',
    assignedCrew: 12,
    crewAssignments: [
      { id: 1, name: 'John Smith', position: 'Camera Operator', shift: 'Full Game' },
      { id: 2, name: 'Sarah Johnson', position: 'Director', shift: 'Full Game' },
      { id: 3, name: 'Mike Brown', position: 'Audio Engineer', shift: 'Full Game' }
    ]
  },
  {
    id: 2,
    title: 'Basketball Game vs Baylor',
    date: '2024-09-20',
    time: '6:30 PM',
    location: 'Ed and Rae Schollmaier Arena',
    status: 'Upcoming',
    statusClass: 'bg-green-100 text-green-800',
    description: 'TCU vs Baylor basketball game. Standard crew required.',
    assignedCrew: 8,
    crewAssignments: [
      { id: 1, name: 'Emily Davis', position: 'Camera Operator', shift: 'Full Game' },
      { id: 2, name: 'Robert Wilson', position: 'Director', shift: 'Full Game' }
    ]
  },
  {
    id: 3,
    title: 'Baseball Game vs Oklahoma',
    date: '2024-08-15',
    time: '2:00 PM',
    location: 'Lupton Stadium',
    status: 'Completed',
    statusClass: 'bg-gray-100 text-gray-800',
    description: 'TCU vs Oklahoma baseball game. Standard crew required.',
    assignedCrew: 6,
    crewAssignments: [
      { id: 1, name: 'David Lee', position: 'Camera Operator', shift: 'Full Game' },
      { id: 2, name: 'Lisa Chen', position: 'Director', shift: 'Full Game' }
    ]
  }
]);

const upcomingEvents = computed(() => 
  events.value.filter(event => new Date(event.date) >= new Date())
);

const pastEvents = computed(() => 
  events.value.filter(event => new Date(event.date) < new Date())
);

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

const openEventDetails = (event: Event) => {
  selectedEvent.value = event;
};
</script> 