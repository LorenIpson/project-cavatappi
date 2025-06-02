<script setup>

import axios from 'axios';

const placeOrder = async () => {
  /* TODO: 前端尚未有組裝訂單功能，先寫死。*/
  try {
    const response = await axios.post('http://localhost:8080/api/order/place-new-order', {
      buyerName: "Daniel",
      buyerPhone: "0987696969",
      buyerMessage: "Hehe",
      receiveDate: "2025-05-30T18:30:00",
      items: [
        {
          itemType: "PIZZA",
          itemId: 1,
          sizeId: 3,
          doughId: 2,
          addons: [
            {addonId: 1},
            {addonId: 2}
          ]
        },
        {
          itemType: "PIZZA",
          itemId: 1,
          sizeId: 2,
          doughId: 1,
          addons: [
            {addonId: 1}
          ]
        }
      ],
      paymentMethod: "LINE_PAY"
    });

    window.location.href = response.data;

  } catch (error) {
    console.error("下單失敗：", error);
    alert("下單失敗");
  }
};

</script>

<template>
  <button @click="placeOrder">Line Pay 結帳假按鈕</button>
</template>

<style scoped>

</style>
