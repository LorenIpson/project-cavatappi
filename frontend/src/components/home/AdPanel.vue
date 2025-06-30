<!--suppress HtmlUnknownTag -->
<script setup>

import {computed, onMounted, ref} from "vue";
import axios from "axios";
import router from "@/router/index.js";

const baseURL = import.meta.env.VITE_API_URL;
const newestPizza = ref(null);
const isLoading = ref(true);
const showSkeleton = computed(() => isLoading.value || !newestPizza.value);

onMounted(async () => {
  try {
    const response = await axios.get(baseURL + '/api/menu/ad/newest-pizza');
    newestPizza.value = response.data;
  } catch (e) {
    console.error(e);
  } finally {
    isLoading.value = false;
  }
});

</script>

<template>

  <div v-if="showSkeleton">
    <div class="flex w-full flex-col gap-4">
      <div class="skeleton h-32 w-full"></div>
      <div class="skeleton h-4 w-28"></div>
      <div class="skeleton h-4 w-full"></div>
      <div class="skeleton h-4 w-full"></div>
    </div>
  </div>

  <div v-else>
    <div @click="router.push('/menu')" class="card bg-base-100 w-auto shadow-lg">
      <figure>
        <img
          :src="'data:image/jpeg;base64,' + newestPizza.image"
          alt="ad"/>
      </figure>
      <div class="card-body">
        <h2 class="card-title">
          {{ newestPizza.name }}
          <div class="badge badge-secondary">新上市</div>
        </h2>
        <p>{{ newestPizza.description }}</p>
        <div class="card-actions justify-end">
        </div>
      </div>
    </div>
  </div>

</template>
