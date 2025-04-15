<template>
  <div class="min-h-screen bg-gray-50 flex">
    <AdminSidebar />
    <div class="flex-1 ml-64">
      <AppHeader />
      <!-- Main Content -->
      <main class="p-4 overflow-auto mt-16">
        <router-view></router-view>
      </main>
    </div>
  </div>
</template>

<script setup lang="ts">
import AdminSidebar from '@/components/admin/AdminSidebar.vue';
import AppHeader from '@/components/AppHeader.vue';
</script> 