<script setup>

import {onMounted, ref} from "vue";
import {useToast} from "@/composables/useToast.js";
import axios from "axios";
import MenuItemCard from "@/components/menu/MenuItemCard.vue";
import ToastAlert from "@/components/essential/ToastAlert.vue";
import MenuItemModal from "@/components/menu/MenuItemModal.vue";

const {showToast} = useToast();
const itemList = ref([]);
const itemDetails = ref([]);
const isModalOpen = ref(false);

const handleOpenModal = async (pizzaId) => {
  try {
    console.log(pizzaId + ' MODAL');
    const response = await axios.get(`http://localhost:8080/api/menu/pizza/get/${pizzaId}`)
    itemDetails.value = response.data;
    isModalOpen.value = true;
    console.log(pizzaId + ' MODAL 2');
    console.log(itemDetails.value.name);
  } catch (e) {
    showToast('取得餐點詳細資訊時出錯了，請重新整理', 'error')
    console.log(e)
  }
};

onMounted(async () => {
  try {
    const response = await axios.get('http://localhost:8080/api/menu/pizza/get/all');
    itemList.value = response.data.content;
  } catch (e) {
    showToast("取得餐點時出錯，請重新整理", "error");
    console.error("餐點載入失敗：", e);
  }
});

</script>

<template>

  <div class="grid grid-cols-2 sm:grid-cols-3 md:grid-cols-4 lg:grid-cols-4 gap-4">
    <MenuItemCard
      v-for="item in itemList"
      :key="item.pizzaId"
      :item="item"
      @open-modal="handleOpenModal"
    />
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
