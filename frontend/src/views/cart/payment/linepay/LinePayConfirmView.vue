<script setup>

import {onMounted, ref} from 'vue';
import {useRoute, useRouter} from 'vue-router';
import axios from 'axios';

const route = useRoute();
const router = useRouter();
const loading = ref(true);
const errorMessage = ref(null);

onMounted(async () => {
  const orderId = route.query.orderId;

  if (!orderId) {
    errorMessage.value = '付款驗證資訊缺失';
    loading.value = false;
    return;
  }

  try {
    const response = await axios.post('http://localhost:8080/api/order/payment/confirm', null, {
      params: {
        orderId: orderId,
      }
    });

    console.log('付款完成：', response.data);
    router.push('/cart/payment/success');
  } catch (e) {
    errorMessage.value = '付款失敗';
    console.error(e);
  } finally {
    loading.value = false;
  }
});

</script>

<template>
  <div class="payment-confirm">
    <div v-if="loading">確認付款中，請稍候...</div>
    <div v-else-if="errorMessage">{{ errorMessage }}</div>
    <div v-else>付款確認成功，頁面即將跳轉...</div>
  </div>
</template>
