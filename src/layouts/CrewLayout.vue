<template>
  <div class="min-h-screen bg-gray-50 flex">
    <aside class="fixed top-0 left-0 h-screen w-64 bg-[#4d2e7d] text-white overflow-y-auto">
      <div class="flex items-center p-6 border-b border-purple-700">
        <img src="@/assets/frog-mascot.png" alt="Frog Mascot" class="w-12 h-12 mr-3" />
        <h1 class="text-2xl font-bold">FrogCrew</h1>
      </div>
      <nav class="p-4">
        <ul class="space-y-2">
          <li>
            <router-link 
              to="/crew/dashboard" 
              class="flex items-center px-4 py-3 rounded-lg hover:bg-purple-700 transition-colors" 
              active-class="bg-purple-800"
            >
              <svg class="w-5 h-5 mr-3" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M3 12l2-2m0 0l7-7 7 7M5 10v10a1 1 0 001 1h3m10-11l2 2m-2-2v10a1 1 0 01-1 1h-3m-6 0a1 1 0 001-1v-4a1 1 0 011-1h2a1 1 0 011 1v4a1 1 0 001 1m-6 0h6" />
              </svg>
              Dashboard
            </router-link>
          </li>
          <li>
            <router-link 
              to="/crew/schedule" 
              class="flex items-center px-4 py-3 rounded-lg hover:bg-purple-700 transition-colors" 
              active-class="bg-purple-800"
            >
              <svg class="w-5 h-5 mr-3" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M8 7V3m8 4V3m-9 8h10M5 21h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v12a2 2 0 002 2z" />
              </svg>
              My Schedule
            </router-link>
          </li>
          <li>
            <router-link 
              to="/crew/availability" 
              class="flex items-center px-4 py-3 rounded-lg hover:bg-purple-700 transition-colors" 
              active-class="bg-purple-800"
            >
              <svg class="w-5 h-5 mr-3" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 5H7a2 2 0 00-2 2v12a2 2 0 002 2h10a2 2 0 002-2V7a2 2 0 00-2-2h-2M9 5a2 2 0 002 2h2a2 2 0 002-2M9 5a2 2 0 012-2h2a2 2 0 012 2" />
              </svg>
              My Availability
            </router-link>
          </li>
          <li>
            <router-link 
              to="/crew/events" 
              class="flex items-center px-4 py-3 rounded-lg hover:bg-purple-700 transition-colors" 
              active-class="bg-purple-800"
            >
              <svg class="w-5 h-5 mr-3" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 11H5m14 0a2 2 0 012 2v6a2 2 0 01-2 2H5a2 2 0 01-2-2v-6a2 2 0 012-2m14 0V9a2 2 0 00-2-2M5 11V9a2 2 0 012-2m0 0V5a2 2 0 012-2h6a2 2 0 012 2M7 7h10" />
              </svg>
              Events
            </router-link>
          </li>
          <li>
            <router-link 
              to="/crew/shift-exchange" 
              class="flex items-center px-4 py-3 rounded-lg hover:bg-purple-700 transition-colors" 
              active-class="bg-purple-800"
            >
              <svg class="w-5 h-5 mr-3" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M8 7h12m0 0l-4-4m4 4l-4 4m0 6H4m0 0l4 4m-4-4l4-4" />
              </svg>
              Shift Exchange
            </router-link>
          </li>
          <li>
            <router-link 
              to="/crew/profile" 
              class="flex items-center px-4 py-3 rounded-lg hover:bg-purple-700 transition-colors" 
              active-class="bg-purple-800"
            >
              <svg class="w-5 h-5 mr-3" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M16 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z" />
              </svg>
              Profile
            </router-link>
          </li>
          <li>
            <router-link 
              to="/crew/settings" 
              class="flex items-center px-4 py-3 rounded-lg hover:bg-purple-700 transition-colors" 
              active-class="bg-purple-800"
            >
              <svg class="w-5 h-5 mr-3" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M10.325 4.317c.426-1.756 2.924-1.756 3.35 0a1.724 1.724 0 002.573 1.066c1.543-.94 3.31.826 2.37 2.37a1.724 1.724 0 001.065 2.572c1.756.426 1.756 2.924 0 3.35a1.724 1.724 0 00-1.066 2.573c.94 1.543-.826 3.31-2.37 2.37a1.724 1.724 0 00-2.572 1.065c-.426 1.756-2.924 1.756-3.35 0a1.724 1.724 0 00-2.573-1.066c-1.543.94-3.31-.826-2.37-2.37a1.724 1.724 0 00-1.065-2.572c-1.756-.426-1.756-2.924 0-3.35a1.724 1.724 0 001.066-2.573c-.94-1.543.826-3.31 2.37-2.37.996.608 2.296.07 2.572-1.065z" />
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 12a3 3 0 11-6 0 3 3 0 016 0z" />
              </svg>
              Settings
            </router-link>
          </li>
        </ul>
      </nav>
    </aside>
    <div class="flex-1 ml-64">
      <AppHeader />
      <!-- Main Content -->
      <main class="p-4 overflow-auto">
        <router-view></router-view>
      </main>
    </div>
  </div>
</template>

<script setup lang="ts">
import AppHeader from '@/components/AppHeader.vue';

defineOptions({
  name: 'CrewLayout'
});
</script>

<style scoped>
/* Hide scrollbar for Chrome, Safari and Opera */
aside::-webkit-scrollbar {
  display: none;
}

/* Hide scrollbar for IE, Edge and Firefox */
aside {
  -ms-overflow-style: none;  /* IE and Edge */
  scrollbar-width: none;  /* Firefox */
}
</style> 