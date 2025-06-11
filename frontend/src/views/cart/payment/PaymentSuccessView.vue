<script setup>

import {useCartStore} from "@/stores/cartStore.js";
import {onMounted} from "vue";

const cartStore = useCartStore();

onMounted(() => {
  if (cartStore) {
    cartStore.clearCart();
  }
});

</script>

<template>
  <h3>付款成功</h3>
  <p>點我查看訂單</p>
  <p>點我回到首頁</p>
</template>
