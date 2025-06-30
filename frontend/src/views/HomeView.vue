<script setup>

import CarouselPanel from "@/components/home/CarouselPanel.vue";
import AdPanel from "@/components/home/AdPanel.vue";

</script>

<template>

  <CarouselPanel class="mb-5"/>

  <div class="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-2 lg:grid-cols-2 gap-4">
    <AdPanel class="mb-5"/>
  </div>

</template>
