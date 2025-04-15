<template>
  <div class="invite-crew">
    <div class="header">
      <h1 class="text-2xl font-semibold text-gray-900">Invite Crew Members</h1>
      <p class="text-gray-600">Send invitations to new crew members to join the team</p>
    </div>

    <div class="invite-form">
      <div class="form-section">
        <h2 class="text-lg font-medium text-gray-900">Single Invite</h2>
        <form @submit.prevent="sendSingleInvite">
          <div class="form-group">
            <label class="block text-sm font-medium text-gray-700">Email Address</label>
            <input 
              type="email" 
              v-model="singleInvite.email" 
              placeholder="crew.member@example.com"
              class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-purple-500 focus:ring-purple-500"
              required
            />
          </div>
          <div class="form-group">
            <label class="block text-sm font-medium text-gray-700">Position</label>
            <select 
              v-model="singleInvite.position" 
              class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-purple-500 focus:ring-purple-500"
              required
            >
              <option value="">Select Position</option>
              <option value="camera">Camera Operator</option>
              <option value="audio">Audio Technician</option>
              <option value="director">Director</option>
              <option value="producer">Producer</option>
              <option value="editor">Video Editor</option>
            </select>
          </div>
          <div class="form-group">
            <label class="block text-sm font-medium text-gray-700">Message (Optional)</label>
            <textarea 
              v-model="singleInvite.message" 
              placeholder="Add a personal message to the invitation"
              rows="3"
              class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-purple-500 focus:ring-purple-500"
            ></textarea>
          </div>
          <button type="submit" class="w-full bg-[#4d2e7d] text-white py-2 px-4 rounded-md hover:bg-[#4d2e7d]/90 focus:outline-none focus:ring-2 focus:ring-purple-500 focus:ring-offset-2">
            Send Invitation
          </button>
        </form>
      </div>

      <div class="divider">
        <span class="bg-white px-4 text-gray-500">OR</span>
      </div>

      <div class="form-section">
        <h2 class="text-lg font-medium text-gray-900">Bulk Invite</h2>
        <form @submit.prevent="sendBulkInvite">
          <div class="form-group">
            <label class="block text-sm font-medium text-gray-700">Upload CSV File</label>
            <div class="file-upload mt-1">
              <input 
                type="file" 
                accept=".csv"
                @change="handleFileUpload"
                id="csvFileInput"
                class="hidden"
              />
              <button 
                type="button" 
                class="bg-gray-100 text-gray-700 py-2 px-4 rounded-md border border-gray-300 hover:bg-gray-200 focus:outline-none focus:ring-2 focus:ring-purple-500 focus:ring-offset-2"
                @click="triggerFileInput"
              >
                Choose File
              </button>
              <span v-if="bulkInvite.fileName" class="ml-2 text-sm text-gray-600">{{ bulkInvite.fileName }}</span>
            </div>
            <small class="text-sm text-gray-500 mt-2 block">CSV format: email,position,message</small>
          </div>
          <button 
            type="submit" 
            class="w-full bg-[#4d2e7d] text-white py-2 px-4 rounded-md hover:bg-[#4d2e7d]/90 focus:outline-none focus:ring-2 focus:ring-purple-500 focus:ring-offset-2 disabled:bg-gray-400 disabled:cursor-not-allowed"
            :disabled="!bulkInvite.file"
          >
            Send Bulk Invitations
          </button>
        </form>
      </div>
    </div>

    <div class="recent-invites" v-if="recentInvites.length > 0">
      <h2 class="text-lg font-medium text-gray-900 mb-4">Recent Invitations</h2>
      <div class="overflow-x-auto">
        <table class="min-w-full divide-y divide-gray-200">
          <thead class="bg-gray-50">
            <tr>
              <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Email</th>
              <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Position</th>
              <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Status</th>
              <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Sent Date</th>
              <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Actions</th>
            </tr>
          </thead>
          <tbody class="bg-white divide-y divide-gray-200">
            <tr v-for="invite in recentInvites" :key="invite.id">
              <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-900">{{ invite.email }}</td>
              <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-900">{{ invite.position }}</td>
              <td class="px-6 py-4 whitespace-nowrap">
                <span :class="[
                  'px-2 py-1 text-xs rounded-full',
                  getStatusClass(invite.status)
                ]">
                  {{ invite.status }}
                </span>
              </td>
              <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-900">{{ formatDate(invite.sentDate) }}</td>
              <td class="px-6 py-4 whitespace-nowrap text-sm">
                <button 
                  @click="resendInvite(invite)"
                  class="text-[#4d2e7d] hover:text-[#4d2e7d]/90 mr-2 disabled:text-gray-400"
                  :disabled="invite.status === 'pending'"
                >
                  Resend
                </button>
                <button 
                  @click="cancelInvite(invite)"
                  class="text-red-600 hover:text-red-700 disabled:text-gray-400"
                  :disabled="invite.status !== 'pending'"
                >
                  Cancel
                </button>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue';

interface SingleInvite {
  email: string;
  position: string;
  message: string;
}

interface BulkInvite {
  file: File | null;
  fileName: string;
}

interface Invite {
  id: number;
  email: string;
  position: string;
  status: 'pending' | 'accepted' | 'declined' | 'cancelled';
  sentDate: string;
}

const singleInvite = ref<SingleInvite>({
  email: '',
  position: '',
  message: ''
});

const bulkInvite = ref<BulkInvite>({
  file: null,
  fileName: ''
});

const recentInvites = ref<Invite[]>([
  {
    id: 1,
    email: 'john.doe@example.com',
    position: 'Camera Operator',
    status: 'pending',
    sentDate: '2024-03-15'
  },
  {
    id: 2,
    email: 'jane.smith@example.com',
    position: 'Audio Technician',
    status: 'accepted',
    sentDate: '2024-03-14'
  }
]);

const triggerFileInput = () => {
  const fileInput = document.getElementById('csvFileInput') as HTMLInputElement;
  if (fileInput) {
    fileInput.click();
  }
};

const sendSingleInvite = () => {
  // TODO: Implement API call to send invitation
  console.log('Sending single invite:', singleInvite.value);
  recentInvites.value.unshift({
    id: Date.now(),
    email: singleInvite.value.email,
    position: singleInvite.value.position,
    status: 'pending',
    sentDate: new Date().toISOString().split('T')[0]
  });
  singleInvite.value = {
    email: '',
    position: '',
    message: ''
  };
};

const handleFileUpload = (event: Event) => {
  const input = event.target as HTMLInputElement;
  const file = input.files?.[0];
  if (file) {
    bulkInvite.value.file = file;
    bulkInvite.value.fileName = file.name;
  }
};

const sendBulkInvite = () => {
  // TODO: Implement API call to send bulk invitations
  console.log('Sending bulk invites:', bulkInvite.value);
  bulkInvite.value = {
    file: null,
    fileName: ''
  };
  const fileInput = document.getElementById('csvFileInput') as HTMLInputElement;
  if (fileInput) {
    fileInput.value = '';
  }
};

const resendInvite = (invite: Invite) => {
  // TODO: Implement API call to resend invitation
  console.log('Resending invite:', invite);
};

const cancelInvite = (invite: Invite) => {
  // TODO: Implement API call to cancel invitation
  console.log('Canceling invite:', invite);
  invite.status = 'cancelled';
};

const formatDate = (date: string) => {
  return new Date(date).toLocaleDateString();
};

const getStatusClass = (status: Invite['status']) => {
  const classes = {
    pending: 'bg-yellow-100 text-yellow-800',
    accepted: 'bg-green-100 text-green-800',
    declined: 'bg-red-100 text-red-800',
    cancelled: 'bg-gray-100 text-gray-800'
  };
  return classes[status];
};
</script>

<style scoped>
.invite-form {
  @apply grid grid-cols-1 lg:grid-cols-[1fr_auto_1fr] gap-8 mb-8;
}

.form-section {
  @apply bg-white p-6 rounded-lg shadow-sm;
}

.form-group {
  @apply mb-4;
}

.divider {
  @apply flex items-center justify-center relative;
}

.divider::before,
.divider::after {
  content: '';
  @apply block w-px h-full bg-gray-200 absolute;
}

.divider::before {
  @apply -left-4;
}

.divider::after {
  @apply -right-4;
}

input[type="email"],
select,
textarea {
  @apply mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-purple-500 focus:ring-purple-500;
}

.recent-invites {
  @apply bg-white p-6 rounded-lg shadow-sm;
}
</style> 