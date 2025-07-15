<script setup>

import {computed, onMounted, ref} from "vue";
import axios from "axios";
import {useToast} from "@/composables/useToast.js";
import MenuItemCard from "@/components/menu/MenuItemCard.vue";
import MenuItemModal from "@/components/menu/MenuItemModal.vue";
import ToastAlert from "@/components/essential/ToastAlert.vue";

const baseURL = import.meta.env.VITE_API_URL;
const {showToast} = useToast();
const itemList = ref(null);
const itemDetails = ref([]);
const isModalOpen = ref(false);
const isLoading = ref(true);
const showSkeleton = computed(() => isLoading.value || !itemList.value);

const handleOpenModal = async (pizzaId) => {
  try {
    const response = await axios.get(baseURL + `/api/menu/pizza/get/${pizzaId}`);
    itemDetails.value = response.data;
    isModalOpen.value = true;
  } catch (e) {
    showToast('取得餐點詳細資訊時出錯了，請重新整理', 'error')
    console.log(e)
  }
};

onMounted(async () => {
  try {
    const response = await axios.get(baseURL + '/api/menu/pizza/get/all');
    itemList.value = response.data.content;
  } catch (e) {
    showToast("取得餐點時出錯，請重新整理", "error");
    console.error("餐點載入失敗：", e);
  } finally {
    isLoading.value = false;
  }
});

</script>

<template>

  <div v-if="showSkeleton">
    <div class="grid grid-cols-2 sm:grid-cols-3 md:grid-cols-4 lg:grid-cols-4 gap-4">
      <div class="skeleton h-48 w-full"></div>
      <div class="skeleton h-48 w-full"></div>
      <div class="skeleton h-48 w-full"></div>
      <div class="skeleton h-48 w-full"></div>
      <div class="skeleton h-48 w-full"></div>
      <div class="skeleton h-48 w-full"></div>
      <div class="skeleton h-48 w-full"></div>
      <div class="skeleton h-48 w-full"></div>
    </div>
  </div>

  <div v-else>
    <div class="grid grid-cols-2 sm:grid-cols-3 md:grid-cols-4 lg:grid-cols-4 gap-4">
      <MenuItemCard
        v-for="item in itemList"
        :key="item.pizzaId"
        :item="item"
        @open-modal="handleOpenModal"
      />
    </div>
  </div>

  <div>
    <MenuItemModal
      v-if="isModalOpen"
      :item="itemDetails"
      @close-modal="isModalOpen = false"
    />
  </div>

  <ToastAlert/>

</template>
