<template>
  <header class="bg-white shadow-sm h-16 fixed top-0 right-0 left-64 z-10">
    <div class="flex items-center justify-between h-full px-6">
      <div class="flex items-center space-x-4">
        <button class="p-2 rounded-full hover:bg-gray-100">
          <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z" />
          </svg>
        </button>
      </div>
      
      <div class="flex items-center space-x-4">
        <button class="relative p-2 rounded-full hover:bg-gray-100">
          <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 17h5l-1.405-1.405A2.032 2.032 0 0118 14.158V11a6.002 6.002 0 00-4-5.659V5a2 2 0 10-4 0v.341C7.67 6.165 6 8.388 6 11v3.159c0 .538-.214 1.055-.595 1.436L4 17h5m6 0v1a3 3 0 11-6 0v-1m6 0H9" />
          </svg>
          <span class="absolute top-0 right-0 w-2 h-2 bg-red-500 rounded-full"></span>
        </button>
        
        <div class="flex items-center space-x-2">
          <img :src="userAvatar" alt="User Avatar" class="w-8 h-8 rounded-full">
          <div class="text-sm">
            <p class="font-medium">{{ userName }}</p>
            <p class="text-gray-500">{{ userRole }}</p>
          </div>
        </div>

        <!-- Mode Switcher -->
        <div class="mode-switcher">
          <button 
            @click="switchToAdmin" 
            :class="{ active: isAdminMode }"
            class="mode-btn admin-btn"
          >
            Admin Mode
          </button>
          <button 
            @click="switchToCrew" 
            :class="{ active: isCrewMode }"
            class="mode-btn crew-btn"
          >
            Crew Mode
          </button>
        </div>
      </div>
    </div>
  </header>
</template>

<script>
import { mapGetters, mapActions } from 'vuex';
import { useRouter } from 'vue-router';

export default {
  name: 'AppHeader',
  setup() {
    const router = useRouter();
    return { router };
  },
  data() {
    return {
      userName: 'John Doe',
      userRole: 'Admin',
      userAvatar: 'https://ui-avatars.com/api/?name=John+Doe&background=4d2e7d&color=fff'
    };
  },
  computed: {
    ...mapGetters(['isAdminMode', 'isCrewMode'])
  },
  methods: {
    ...mapActions(['switchMode']),
    switchToAdmin() {
      this.switchMode('admin');
      this.router.push('/admin');
    },
    switchToCrew() {
      this.switchMode('crew');
      this.router.push('/crew');
    }
  }
};
</script>

<style scoped>
.mode-switcher {
  display: flex;
  gap: 10px;
  padding: 10px;
  background-color: #f5f5f5;
  border-radius: 4px;
}

.mode-btn {
  padding: 8px 16px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-weight: 500;
  transition: all 0.3s ease;
}

.mode-btn:hover {
  opacity: 0.9;
}

.admin-btn {
  background-color: #e3f2fd;
  color: #1976d2;
}

.crew-btn {
  background-color: #e8f5e9;
  color: #2e7d32;
}

.mode-btn.active {
  transform: scale(1.05);
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.admin-btn.active {
  background-color: #1976d2;
  color: white;
}

.crew-btn.active {
  background-color: #2e7d32;
  color: white;
}
</style> 