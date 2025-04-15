<template>
  <div class="fixed left-0 top-0 h-full w-64 bg-[#4d2e7d] text-white">
    <div class="p-6 flex items-center gap-3">
      <div class="w-10 h-10 bg-white/10 rounded-lg flex items-center justify-center">
        <i class="fas fa-frog text-2xl"></i>
      </div>
      <div>
        <h1 class="text-xl font-bold">FrogCrew</h1>
        <p class="text-sm opacity-80">Admin</p>
      </div>
    </div>
    
    <nav class="mt-6">
      <router-link 
        to="/admin/dashboard" 
        class="flex items-center px-6 py-3 text-white hover:bg-white/10"
        :class="{ 'bg-white/10': $route.path === '/admin/dashboard' }"
      >
        <i class="fas fa-home w-6"></i>
        <span>Dashboard</span>
      </router-link>
      
      <router-link 
        to="/admin/crew/invite" 
        class="flex items-center px-6 py-3 text-white hover:bg-white/10"
        :class="{ 'bg-white/10': $route.path === '/admin/crew/invite' }"
      >
        <i class="fas fa-user-plus w-6"></i>
        <span>Invite Crew</span>
      </router-link>
      
      <router-link 
        to="/admin/crew" 
        class="flex items-center px-6 py-3 text-white hover:bg-white/10"
        :class="{ 'bg-white/10': $route.path === '/admin/crew' }"
      >
        <i class="fas fa-users w-6"></i>
        <span>Manage Crew</span>
      </router-link>
      
      <router-link 
        to="/admin/schedule/create" 
        class="flex items-center px-6 py-3 text-white hover:bg-white/10"
        :class="{ 'bg-white/10': $route.path === '/admin/schedule/create' }"
      >
        <i class="fas fa-calendar-plus w-6"></i>
        <span>Create Schedule</span>
      </router-link>
      
      <router-link 
        to="/admin/availability" 
        class="flex items-center px-6 py-3 text-white hover:bg-white/10"
        :class="{ 'bg-white/10': $route.path === '/admin/availability' }"
      >
        <i class="fas fa-clock w-6"></i>
        <span>Manage Availability</span>
      </router-link>
      
      <router-link 
        to="/admin/approvals" 
        class="flex items-center px-6 py-3 text-white hover:bg-white/10"
        :class="{ 'bg-white/10': $route.path === '/admin/approvals' }"
      >
        <i class="fas fa-check-circle w-6"></i>
        <span>Shift Approvals</span>
      </router-link>
      
      <router-link 
        to="/admin/reports" 
        class="flex items-center px-6 py-3 text-white hover:bg-white/10"
        :class="{ 'bg-white/10': $route.path === '/admin/reports' }"
      >
        <i class="fas fa-chart-bar w-6"></i>
        <span>Reports</span>
      </router-link>
    </nav>
  </div>
</template>

<script setup lang="ts">
// No additional setup needed
</script> 