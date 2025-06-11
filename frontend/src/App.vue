<script setup>
import {RouterView} from 'vue-router'
import DockPanel from "@/components/essential/DockPanel.vue";
import TopPanel from "@/components/essential/TopPanel.vue";
</script>

<template>

  <div>
    <TopPanel/>
  </div>

  <div class="min-h-screen bg-base-100 px-4 sm:px-6 md:px-8">
    <main class="max-w-screen-md mx-auto w-full py-6">
      <RouterView/>
      <div class="h-20"></div>
    </main>
  </div>
  <div>
    <DockPanel/>
  </div>

</template>
