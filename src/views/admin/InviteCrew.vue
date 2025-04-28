<template>
  <div class="container mx-auto px-4 py-8">
    <h1 class="text-3xl font-bold mb-8">Invite Crew</h1>

    <!-- Error Alert -->
    <div v-if="error" class="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded relative mb-4" role="alert">
      <span class="block sm:inline">{{ error }}</span>
    </div>

    <div class="grid grid-cols-1 md:grid-cols-2 gap-8">
      <!-- Single Invite Form -->
      <div class="bg-white rounded-lg shadow p-6">
        <h2 class="text-xl font-semibold mb-4">Single Invite</h2>
        <form @submit.prevent="sendSingleInvite" class="space-y-4">
          <div>
            <label for="email" class="block text-sm font-medium text-gray-700">Email</label>
            <input
              type="email"
              id="email"
              v-model="singleInvite.email"
              required
              class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-blue-500 focus:ring-blue-500"
              :disabled="loading"
            />
          </div>
          <div>
            <label for="position" class="block text-sm font-medium text-gray-700">Position</label>
            <select
              id="position"
              v-model="singleInvite.position"
              required
              class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-blue-500 focus:ring-blue-500"
              :disabled="loading"
            >
              <option value="ADMIN">Administrator</option>
              <option value="USER">Crew Member</option>
            </select>
          </div>
          <div>
            <label for="message" class="block text-sm font-medium text-gray-700">Message (Optional)</label>
            <textarea
              id="message"
              v-model="singleInvite.message"
              rows="3"
              class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-blue-500 focus:ring-blue-500"
              :disabled="loading"
            ></textarea>
          </div>
          <button
            type="submit"
            class="w-full bg-blue-600 text-white py-2 px-4 rounded-md hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-offset-2 disabled:opacity-50"
            :disabled="loading"
          >
            <span v-if="loading">Sending...</span>
            <span v-else>Send Invite</span>
          </button>
        </form>
      </div>

      <!-- Bulk Invite Form -->
      <div class="bg-white rounded-lg shadow p-6">
        <h2 class="text-xl font-semibold mb-4">Bulk Invite</h2>
        <form @submit.prevent="sendBulkInvite" class="space-y-4">
          <div>
            <label class="block text-sm font-medium text-gray-700">CSV File</label>
            <input
              type="file"
              id="csvFileInput"
              accept=".csv"
              @change="handleFileUpload"
              class="hidden"
              :disabled="loading"
            />
            <div class="mt-1 flex items-center">
              <button
                type="button"
                @click="triggerFileInput"
                class="inline-flex items-center px-4 py-2 border border-gray-300 rounded-md shadow-sm text-sm font-medium text-gray-700 bg-white hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500"
                :disabled="loading"
              >
                Choose File
              </button>
              <span class="ml-3 text-sm text-gray-500">{{ bulkInvite.fileName || 'No file chosen' }}</span>
            </div>
          </div>
          <button
            type="submit"
            class="w-full bg-blue-600 text-white py-2 px-4 rounded-md hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-offset-2 disabled:opacity-50"
            :disabled="!bulkInvite.file || loading"
          >
            <span v-if="loading">Sending...</span>
            <span v-else>Send Bulk Invites</span>
          </button>
        </form>
      </div>
    </div>

    <!-- Recent Invites -->
    <div class="mt-12">
      <h2 class="text-xl font-semibold mb-4">Recent Invites</h2>
      <div class="bg-white rounded-lg shadow overflow-hidden">
        <table class="min-w-full divide-y divide-gray-200">
          <thead class="bg-gray-50">
            <tr>
              <th scope="col" class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Email</th>
              <th scope="col" class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Position</th>
              <th scope="col" class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Status</th>
              <th scope="col" class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Sent Date</th>
              <th scope="col" class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Actions</th>
            </tr>
          </thead>
          <tbody class="bg-white divide-y divide-gray-200">
            <tr v-if="loading && recentInvites.length === 0">
              <td colspan="5" class="px-6 py-4 text-center text-sm text-gray-500">Loading...</td>
            </tr>
            <tr v-else-if="recentInvites.length === 0">
              <td colspan="5" class="px-6 py-4 text-center text-sm text-gray-500">No invites found</td>
            </tr>
            <tr v-for="invite in recentInvites" :key="invite.id">
              <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-900">{{ invite.email }}</td>
              <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-900">{{ invite.position }}</td>
              <td class="px-6 py-4 whitespace-nowrap">
                <span :class="['px-2 inline-flex text-xs leading-5 font-semibold rounded-full', getStatusClass(invite.status)]">
                  {{ invite.status }}
                </span>
              </td>
              <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-900">{{ formatDate(invite.createdAt) }}</td>
              <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
                <button
                  v-if="invite.status === 'PENDING'"
                  @click="cancelInvite(invite)"
                  class="text-red-600 hover:text-red-900 mr-4"
                  :disabled="loading"
                >
                  Cancel
                </button>
                <button
                  v-if="['CANCELLED', 'EXPIRED'].includes(invite.status)"
                  @click="resendInvite(invite)"
                  class="text-blue-600 hover:text-blue-900"
                  :disabled="loading"
                >
                  Resend
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
import { ref, onMounted } from 'vue';
import { invitationService, type Invitation, type SingleInvite } from '@/services/invitationService';

interface BulkInvite {
  file: File | null;
  fileName: string;
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

const recentInvites = ref<Invitation[]>([]);
const loading = ref(false);
const error = ref<string | null>(null);

onMounted(async () => {
  try {
    recentInvites.value = await invitationService.getRecentInvitations();
  } catch (err) {
    error.value = 'Failed to load recent invitations';
    console.error(err);
  }
});

const triggerFileInput = () => {
  const fileInput = document.getElementById('csvFileInput') as HTMLInputElement;
  if (fileInput) {
    fileInput.click();
  }
};

const sendSingleInvite = async () => {
  loading.value = true;
  error.value = null;
  
  try {
    const invitation = await invitationService.sendSingleInvite(singleInvite.value);
    recentInvites.value.unshift(invitation);
    singleInvite.value = {
      email: '',
      position: '',
      message: ''
    };
  } catch (err: any) {
    error.value = err.response?.data?.error || 'Failed to send invitation';
    console.error(err);
  } finally {
    loading.value = false;
  }
};

const handleFileUpload = (event: Event) => {
  const input = event.target as HTMLInputElement;
  const file = input.files?.[0];
  if (file) {
    bulkInvite.value.file = file;
    bulkInvite.value.fileName = file.name;
  }
};

const sendBulkInvite = async () => {
  if (!bulkInvite.value.file) return;
  
  loading.value = true;
  error.value = null;
  
  try {
    const invitations = await invitationService.sendBulkInvite(bulkInvite.value.file);
    recentInvites.value.unshift(...invitations);
    bulkInvite.value = {
      file: null,
      fileName: ''
    };
    const fileInput = document.getElementById('csvFileInput') as HTMLInputElement;
    if (fileInput) {
      fileInput.value = '';
    }
  } catch (err: any) {
    error.value = err.response?.data?.error || 'Failed to send bulk invitations';
    console.error(err);
  } finally {
    loading.value = false;
  }
};

const resendInvite = async (invite: Invitation) => {
  loading.value = true;
  error.value = null;
  
  try {
    const updatedInvitation = await invitationService.resendInvitation(invite.id);
    const index = recentInvites.value.findIndex(i => i.id === invite.id);
    if (index !== -1) {
      recentInvites.value[index] = updatedInvitation;
    }
  } catch (err: any) {
    error.value = err.response?.data?.error || 'Failed to resend invitation';
    console.error(err);
  } finally {
    loading.value = false;
  }
};

const cancelInvite = async (invite: Invitation) => {
  loading.value = true;
  error.value = null;
  
  try {
    await invitationService.cancelInvitation(invite.id);
    const index = recentInvites.value.findIndex(i => i.id === invite.id);
    if (index !== -1) {
      recentInvites.value[index] = { ...invite, status: 'CANCELLED' };
    }
  } catch (err: any) {
    error.value = err.response?.data?.error || 'Failed to cancel invitation';
    console.error(err);
  } finally {
    loading.value = false;
  }
};

const formatDate = (date: string) => {
  return new Date(date).toLocaleDateString();
};

const getStatusClass = (status: Invitation['status']) => {
  const classes = {
    PENDING: 'bg-yellow-100 text-yellow-800',
    ACCEPTED: 'bg-green-100 text-green-800',
    DECLINED: 'bg-red-100 text-red-800',
    CANCELLED: 'bg-gray-100 text-gray-800',
    EXPIRED: 'bg-gray-100 text-gray-800'
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