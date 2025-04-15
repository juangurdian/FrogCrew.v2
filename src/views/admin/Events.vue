<template>
  <div class="events-view">
    <div class="flex justify-between items-center mb-8">
      <h1 class="text-3xl font-bold">Events</h1>
      <router-link to="/admin/events/create" class="btn-primary">
        Create New Event
      </router-link>
    </div>
    <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
      <div class="card">
        <h2 class="text-xl font-semibold mb-2">Upcoming Events</h2>
        <div class="text-center text-gray-500">
          No upcoming events
        </div>
      </div>
      <div class="card">
        <h2 class="text-xl font-semibold mb-2">Past Events</h2>
        <div class="text-center text-gray-500">
          No past events
        </div>
      </div>
      <div class="card">
        <h2 class="text-xl font-semibold mb-2">Event Statistics</h2>
        <div class="text-center text-gray-500">
          No statistics available
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
defineOptions({
  name: 'EventsView'
})
// Events management logic will go here
</script> 