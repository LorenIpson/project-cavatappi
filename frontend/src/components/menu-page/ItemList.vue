<script setup>

import {onMounted, ref} from "vue";
import axios from "axios";
import ItemCard from "@/components/menu-page/ItemCard.vue";

const itemList = ref([]);

onMounted(async () => {

  try {
    const response = await axios.get('http://localhost:8080/api/menu/pizza/get/all');
    itemList.value = response.data.content;
  } catch (e) {
    console.error("餐點載入失敗：", e);
  }

});

</script>

<template>

  <h3>這是餐點清單耶</h3>

  <div class="item-list" v-for="item in itemList" :key="item.pizzaId">
    <ItemCard :item="item"/>
  </div>

</template>

<style scoped>

.item-list {
  margin: 10px 0;
  border: #79ad6f 1px solid;
}

</style>
