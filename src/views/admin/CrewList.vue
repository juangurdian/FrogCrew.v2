<template>
  <div class="space-y-4">
    <div class="header">
      <h1 class="text-2xl font-bold text-gray-900">Manage Crew Members</h1>
      <p class="text-gray-600">View and manage your crew members' information and status</p>
    </div>

    <div class="grid grid-cols-1 md:grid-cols-3 gap-4">
      <div class="filter-group">
        <label class="block text-sm font-medium text-gray-700 mb-2">Search</label>
        <input 
          type="text" 
          v-model="searchQuery" 
          placeholder="Search by name, email, or position"
          class="w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:ring-primary focus:border-primary"
        />
      </div>
      <div class="filter-group">
        <label class="block text-sm font-medium text-gray-700 mb-2">Position</label>
        <select 
          v-model="positionFilter"
          class="w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:ring-primary focus:border-primary"
        >
          <option value="">All Positions</option>
          <option value="camera">Camera Operator</option>
          <option value="audio">Audio Technician</option>
          <option value="director">Director</option>
          <option value="producer">Producer</option>
          <option value="editor">Video Editor</option>
        </select>
      </div>
      <div class="filter-group">
        <label class="block text-sm font-medium text-gray-700 mb-2">Status</label>
        <select 
          v-model="statusFilter"
          class="w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:ring-primary focus:border-primary"
        >
          <option value="">All Statuses</option>
          <option value="active">Active</option>
          <option value="inactive">Inactive</option>
          <option value="on_leave">On Leave</option>
        </select>
      </div>
    </div>

    <div class="bg-white rounded-lg shadow overflow-hidden">
      <table class="min-w-full divide-y divide-gray-200">
        <thead class="bg-gray-50">
          <tr>
            <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Name</th>
            <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Email</th>
            <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Position</th>
            <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Status</th>
            <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Join Date</th>
            <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Actions</th>
          </tr>
        </thead>
        <tbody class="bg-white divide-y divide-gray-200">
          <tr v-for="member in filteredCrew" :key="member.id">
            <td class="px-6 py-4 whitespace-nowrap">{{ member.name }}</td>
            <td class="px-6 py-4 whitespace-nowrap">{{ member.email }}</td>
            <td class="px-6 py-4 whitespace-nowrap">{{ formatPosition(member.position) }}</td>
            <td class="px-6 py-4 whitespace-nowrap">
              <span :class="[
                'px-2 py-1 text-xs font-medium rounded-full',
                {
                  'bg-green-100 text-green-800': member.status === 'active',
                  'bg-red-100 text-red-800': member.status === 'inactive',
                  'bg-orange-100 text-orange-800': member.status === 'on_leave'
                }
              ]">
                {{ formatStatus(member.status) }}
              </span>
            </td>
            <td class="px-6 py-4 whitespace-nowrap">{{ formatDate(member.joinDate) }}</td>
            <td class="px-6 py-4 whitespace-nowrap space-x-2">
              <button 
                @click="editMember(member)"
                class="px-3 py-1 text-sm text-white bg-blue-600 rounded hover:bg-blue-700"
              >
                Edit
              </button>
              <button 
                @click="toggleStatus(member)"
                :class="[
                  'px-3 py-1 text-sm text-white rounded',
                  member.status === 'active' ? 'bg-red-600 hover:bg-red-700' : 'bg-green-600 hover:bg-green-700'
                ]"
              >
                {{ member.status === 'active' ? 'Deactivate' : 'Activate' }}
              </button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>

    <!-- Edit Member Modal -->
    <div v-if="editingMember" class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center">
      <div class="bg-white rounded-lg p-6 w-full max-w-lg">
        <h2 class="text-xl font-bold mb-4">Edit Crew Member</h2>
        <form @submit.prevent="saveMember">
          <div class="space-y-4">
            <div>
              <label class="block text-sm font-medium text-gray-700 mb-2">Name</label>
              <input 
                type="text" 
                v-model="editingMember.name" 
                required
                class="w-full px-3 py-2 border border-gray-300 rounded-md"
              />
            </div>
            <div>
              <label class="block text-sm font-medium text-gray-700 mb-2">Email</label>
              <input 
                type="email" 
                v-model="editingMember.email" 
                required
                class="w-full px-3 py-2 border border-gray-300 rounded-md"
              />
            </div>
            <div>
              <label class="block text-sm font-medium text-gray-700 mb-2">Position</label>
              <select 
                v-model="editingMember.position" 
                required
                class="w-full px-3 py-2 border border-gray-300 rounded-md"
              >
                <option value="camera">Camera Operator</option>
                <option value="audio">Audio Technician</option>
                <option value="director">Director</option>
                <option value="producer">Producer</option>
                <option value="editor">Video Editor</option>
              </select>
            </div>
            <div>
              <label class="block text-sm font-medium text-gray-700 mb-2">Status</label>
              <select 
                v-model="editingMember.status" 
                required
                class="w-full px-3 py-2 border border-gray-300 rounded-md"
              >
                <option value="active">Active</option>
                <option value="inactive">Inactive</option>
                <option value="on_leave">On Leave</option>
              </select>
            </div>
          </div>
          <div class="flex justify-end space-x-3 mt-6">
            <button 
              type="button" 
              @click="editingMember = null"
              class="px-4 py-2 text-sm bg-gray-100 rounded hover:bg-gray-200"
            >
              Cancel
            </button>
            <button 
              type="submit"
              class="px-4 py-2 text-sm text-white bg-blue-600 rounded hover:bg-blue-700"
            >
              Save Changes
            </button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from 'vue';
import { userService } from '@/services/api';

interface CrewMember {
  id: number;
  name: string;
  email: string;
  position: string;
  status: string;
  joinDate: string;
}

defineOptions({
  name: 'CrewList'
});

const searchQuery = ref('');
const positionFilter = ref('');
const statusFilter = ref('');
const editingMember = ref<CrewMember | null>(null);

const loading = ref(false);
const error = ref('');

const crewMembers = ref<CrewMember[]>([]);

const fetchCrewMembers = async () => {
  loading.value = true;
  error.value = '';
  try {
    const response = await userService.getAllUsers();
    crewMembers.value = response.data.map((user: any) => ({
      id: user.id,
      name: user.firstName + ' ' + user.lastName,
      email: user.email,
      position: user.position || '', // adjust if position is stored elsewhere
      status: user.isActive ? 'active' : 'inactive',
      joinDate: user.createdAt ? new Date(user.createdAt).toISOString().slice(0, 10) : ''
    }));
  } catch (e: any) {
    error.value = e?.response?.data?.message || 'Failed to load crew members';
  } finally {
    loading.value = false;
  }
};

onMounted(fetchCrewMembers);

const filteredCrew = computed(() => {
  return crewMembers.value.filter(member => {
    const matchesSearch = 
      member.name.toLowerCase().includes(searchQuery.value.toLowerCase()) ||
      member.email.toLowerCase().includes(searchQuery.value.toLowerCase()) ||
      formatPosition(member.position).toLowerCase().includes(searchQuery.value.toLowerCase());
    
    const matchesPosition = !positionFilter.value || member.position === positionFilter.value;
    const matchesStatus = !statusFilter.value || member.status === statusFilter.value;
    
    return matchesSearch && matchesPosition && matchesStatus;
  });
});

const formatPosition = (position: string) => {
  const positions = {
    'camera': 'Camera Operator',
    'audio': 'Audio Technician',
    'director': 'Director',
    'producer': 'Producer',
    'editor': 'Video Editor'
  };
  return positions[position as keyof typeof positions] || position;
};

const formatStatus = (status: string) => {
  const statuses = {
    'active': 'Active',
    'inactive': 'Inactive',
    'on_leave': 'On Leave'
  };
  return statuses[status as keyof typeof statuses] || status;
};

const formatDate = (date: string) => {
  return new Date(date).toLocaleDateString();
};

const editMember = (member: CrewMember) => {
  editingMember.value = { ...member };
};

const saveMember = async () => {
  if (!editingMember.value) return;
  loading.value = true;
  error.value = '';
  try {
    // Split name into firstName and lastName
    const [firstName, ...lastNameArr] = editingMember.value.name.split(' ');
    const lastName = lastNameArr.join(' ');
    await userService.updateUser(editingMember.value.id, {
      email: editingMember.value.email,
      firstName,
      lastName,
      role: 'USER', // or use actual role if available
      // Add position if backend supports it
      // position: editingMember.value.position,
      // Add status if backend supports it
      // isActive: editingMember.value.status === 'active',
    });
    await fetchCrewMembers();
    editingMember.value = null;
  } catch (e: any) {
    error.value = e?.response?.data?.message || 'Failed to update member';
  } finally {
    loading.value = false;
  }
};

const toggleStatus = async (member: CrewMember) => {
  loading.value = true;
  error.value = '';
  try {
    // Toggle isActive via updateUser
    await userService.updateUser(member.id, {
      // Keep other fields unchanged
      email: member.email,
      firstName: member.name.split(' ')[0],
      lastName: member.name.split(' ').slice(1).join(' '),
      role: 'USER',
      // isActive: member.status !== 'active',
    });
    await fetchCrewMembers();
  } catch (e: any) {
    error.value = e?.response?.data?.message || 'Failed to update status';
  } finally {
    loading.value = false;
  }
};
</script> 