<template>
  <div class="space-y-6">
    <!-- Header -->
    <div class="flex justify-between items-center">
      <h1 class="text-2xl font-semibold text-gray-900">Shift Approvals</h1>
      <div class="flex space-x-4">
        <button class="btn-secondary">
          Export Report
        </button>
      </div>
    </div>

    <!-- Tabs -->
    <div class="border-b border-gray-200">
      <nav class="-mb-px flex space-x-8">
        <button
          v-for="tab in tabs"
          :key="tab.id"
          @click="currentTab = tab.id"
          :class="[
            'py-4 px-1 border-b-2 font-medium text-sm whitespace-nowrap',
            currentTab === tab.id
              ? 'border-primary text-primary'
              : 'border-transparent text-gray-500 hover:text-gray-700 hover:border-gray-300'
          ]"
        >
          {{ tab.name }}
          <span
            v-if="tab.count > 0"
            :class="[
              'ml-2 px-2.5 py-0.5 rounded-full text-xs font-medium',
              currentTab === tab.id
                ? 'bg-primary/10 text-primary'
                : 'bg-gray-100 text-gray-600'
            ]"
          >
            {{ tab.count }}
          </span>
        </button>
      </nav>
    </div>

    <!-- Filters -->
    <div class="bg-white rounded-lg shadow-sm p-4">
      <div class="grid grid-cols-1 md:grid-cols-3 gap-4">
        <div>
          <label class="block text-sm font-medium text-gray-700 mb-1">Status</label>
          <select v-model="selectedStatus" class="form-select w-full">
            <option value="">All Status</option>
            <option value="pending">Pending</option>
            <option value="approved">Approved</option>
            <option value="rejected">Rejected</option>
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
          <label class="block text-sm font-medium text-gray-700 mb-1">Search</label>
          <input type="text" v-model="searchQuery" placeholder="Search by name..." class="form-input w-full" />
        </div>
      </div>
    </div>

    <!-- Shift Exchange Requests -->
    <div v-if="currentTab === 'exchanges'" class="bg-white rounded-lg shadow-sm divide-y divide-gray-200">
      <div v-for="request in filteredExchangeRequests" :key="request.id" class="p-4">
        <div class="flex items-start justify-between">
          <div class="flex-1">
            <div class="flex items-center">
              <div class="h-10 w-10 rounded-full bg-gray-200 flex items-center justify-center text-sm font-medium">
                {{ request.requester.initials }}
              </div>
              <div class="ml-4">
                <h3 class="text-sm font-medium text-gray-900">
                  {{ request.requester.name }} → {{ request.recipient.name }}
                </h3>
                <p class="text-sm text-gray-500">
                  {{ request.shift.title }} ({{ request.shift.date }})
                </p>
              </div>
            </div>
            <div class="mt-2 text-sm text-gray-600">
              <p><span class="font-medium">Reason:</span> {{ request.reason }}</p>
              <p class="mt-1"><span class="font-medium">Requested:</span> {{ request.requestedAt }}</p>
            </div>
          </div>
          <div class="ml-4 flex-shrink-0 flex items-center space-x-2">
            <span :class="[
              'px-2 py-1 text-xs rounded-full',
              request.status === 'pending' ? 'bg-yellow-100 text-yellow-800' :
              request.status === 'approved' ? 'bg-green-100 text-green-800' :
              'bg-red-100 text-red-800'
            ]">
              {{ request.status }}
            </span>
            <div v-if="request.status === 'pending'" class="flex space-x-2">
              <button 
                @click="approveRequest(request.id)"
                class="btn-primary text-sm"
              >
                Approve
              </button>
              <button 
                @click="rejectRequest(request.id)"
                class="btn-secondary text-sm"
              >
                Reject
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- Time Off Requests -->
    <div v-if="currentTab === 'timeoff'" class="bg-white rounded-lg shadow-sm divide-y divide-gray-200">
      <div v-for="request in filteredTimeOffRequests" :key="request.id" class="p-4">
        <div class="flex items-start justify-between">
          <div class="flex-1">
            <div class="flex items-center">
              <div class="h-10 w-10 rounded-full bg-gray-200 flex items-center justify-center text-sm font-medium">
                {{ request.requester.initials }}
              </div>
              <div class="ml-4">
                <h3 class="text-sm font-medium text-gray-900">{{ request.requester.name }}</h3>
                <p class="text-sm text-gray-500">
                  {{ request.startDate }} - {{ request.endDate }}
                </p>
              </div>
            </div>
            <div class="mt-2 text-sm text-gray-600">
              <p><span class="font-medium">Type:</span> {{ request.type }}</p>
              <p><span class="font-medium">Reason:</span> {{ request.reason }}</p>
              <p class="mt-1"><span class="font-medium">Requested:</span> {{ request.requestedAt }}</p>
            </div>
          </div>
          <div class="ml-4 flex-shrink-0 flex items-center space-x-2">
            <span :class="[
              'px-2 py-1 text-xs rounded-full',
              request.status === 'pending' ? 'bg-yellow-100 text-yellow-800' :
              request.status === 'approved' ? 'bg-green-100 text-green-800' :
              'bg-red-100 text-red-800'
            ]">
              {{ request.status }}
            </span>
            <div v-if="request.status === 'pending'" class="flex space-x-2">
              <button 
                @click="approveRequest(request.id)"
                class="btn-primary text-sm"
              >
                Approve
              </button>
              <button 
                @click="rejectRequest(request.id)"
                class="btn-secondary text-sm"
              >
                Reject
              </button>
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
  name: 'ShiftApprovals'
});

// State
const currentTab = ref('exchanges');
const selectedStatus = ref('');
const selectedDateRange = ref('week');
const searchQuery = ref('');

// Tabs configuration
const tabs = [
  { id: 'exchanges', name: 'Shift Exchange Requests', count: 5 },
  { id: 'timeoff', name: 'Time Off Requests', count: 3 }
];

// Mock data
const exchangeRequests = ref([
  {
    id: 1,
    requester: {
      name: 'John Doe',
      initials: 'JD'
    },
    recipient: {
      name: 'Jane Smith',
      initials: 'JS'
    },
    shift: {
      title: 'Football Game Coverage',
      date: 'Oct 15, 2024'
    },
    reason: 'Family commitment',
    requestedAt: '2 hours ago',
    status: 'pending'
  },
  {
    id: 2,
    requester: {
      name: 'Mike Johnson',
      initials: 'MJ'
    },
    recipient: {
      name: 'Sarah Wilson',
      initials: 'SW'
    },
    shift: {
      title: 'Basketball Game Coverage',
      date: 'Oct 18, 2024'
    },
    reason: 'Doctor appointment',
    requestedAt: '1 day ago',
    status: 'approved'
  }
]);

const timeOffRequests = ref([
  {
    id: 1,
    requester: {
      name: 'Alice Brown',
      initials: 'AB'
    },
    startDate: 'Oct 20, 2024',
    endDate: 'Oct 25, 2024',
    type: 'Vacation',
    reason: 'Family vacation',
    requestedAt: '3 days ago',
    status: 'pending'
  },
  {
    id: 2,
    requester: {
      name: 'Bob Wilson',
      initials: 'BW'
    },
    startDate: 'Oct 22, 2024',
    endDate: 'Oct 22, 2024',
    type: 'Sick Leave',
    reason: 'Medical appointment',
    requestedAt: '1 day ago',
    status: 'approved'
  }
]);

// Computed properties
const filteredExchangeRequests = computed(() => {
  return exchangeRequests.value.filter(request => {
    if (selectedStatus.value && request.status !== selectedStatus.value) {
      return false;
    }
    if (searchQuery.value && !request.requester.name.toLowerCase().includes(searchQuery.value.toLowerCase())) {
      return false;
    }
    return true;
  });
});

const filteredTimeOffRequests = computed(() => {
  return timeOffRequests.value.filter(request => {
    if (selectedStatus.value && request.status !== selectedStatus.value) {
      return false;
    }
    if (searchQuery.value && !request.requester.name.toLowerCase().includes(searchQuery.value.toLowerCase())) {
      return false;
    }
    return true;
  });
});

// Methods
function approveRequest(requestId: number) {
  const exchangeRequest = exchangeRequests.value.find(r => r.id === requestId);
  if (exchangeRequest) {
    exchangeRequest.status = 'approved';
    return;
  }

  const timeOffRequest = timeOffRequests.value.find(r => r.id === requestId);
  if (timeOffRequest) {
    timeOffRequest.status = 'approved';
  }
}

function rejectRequest(requestId: number) {
  const exchangeRequest = exchangeRequests.value.find(r => r.id === requestId);
  if (exchangeRequest) {
    exchangeRequest.status = 'rejected';
    return;
  }

  const timeOffRequest = timeOffRequests.value.find(r => r.id === requestId);
  if (timeOffRequest) {
    timeOffRequest.status = 'rejected';
  }
}
</script> 