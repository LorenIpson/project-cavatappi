<script setup>

import OrderDetailStatusBar from "@/components/profile/order/OrderDetailStatusBar.vue";
import axiosApi from "@/composables/useAxios.js";
import {useRoute} from "vue-router";
import {onMounted, ref} from "vue";
import {useToast} from "@/composables/useToast.js";
import ToastAlert from "@/components/essential/ToastAlert.vue";
import OrderDetaiPanel from "@/components/profile/order/OrderDetaiPanel.vue";

const order = ref([]);
const {showToast} = useToast();
const orderId = useRoute().params.orderId;

onMounted(async () => {
  try {
    const response = await axiosApi.get(`/api/order/my-order/detail/${orderId}`);
    order.value = response.data;
  } catch (e) {
    showToast('取得訂單詳細資訊時出錯，請重新整理', 'error')
    console.log(e)
  }
})

</script>

<template>

  <OrderDetailStatusBar
      :order="order"
  />

  <OrderDetaiPanel
      :order="order"
  />

  <ToastAlert/>

</template>
