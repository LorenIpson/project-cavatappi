<script setup>

import {useCartStore} from "@/stores/cartStore.js";
import {onMounted} from "vue";
import router from "@/router/index.js";

const cartStore = useCartStore();

onMounted(() => {
  if (cartStore) {
    cartStore.clearCart();
  }
});

</script>

<template>
  <div class=" bg-base-100 rounded-box  text-center py-16 m-20">
    <p class="text-lg text-gray-600 mb-2">訂單送出成功</p>
    <button @click="router.push('/profile/my-order')" class="text-sm text-gray-400">前往訂單紀錄查看細節</button>
  </div>
</template>
