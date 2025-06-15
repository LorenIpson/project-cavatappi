<script setup>

import MyOrderListCard from "@/components/profile/order/MyOrderListCard.vue";
import {onMounted, ref} from "vue";
import {useToast} from "@/composables/useToast.js";
import axiosApi from "@/composables/useAxios.js";
import ToastAlert from "@/components/essential/ToastAlert.vue";

const {showToast} = useToast();
const currentTab = ref('isNotCompleted'); // isNotCompleted, all, isNotPaid
const orders = ref([]);

const handleFetchIsNotCompletedOrders = async () => {
  try {
    const response = await axiosApi.get("/api/order/my-order/all/is-not-completed");
    console.log(response.data);
    orders.value = response.data.content;
    currentTab.value = "isNotCompleted";
  } catch (e) {
    showToast('取得正在處理的訂單時出錯了，請重新整理', 'error')
    console.log(e);
  }
};

const handleFetchAllOrders = async () => {
  try {
    const response = await axiosApi.get("/api/order/my-order/all");
    orders.value = response.data.content;
    currentTab.value = "all";
  } catch (e) {
    showToast('取得所有訂單時出錯了，請重新整理', 'error');
    console.log(e);
  }
};

const handleFetchIsNotPaidOrders = async () => {
  try {
    const response = await axiosApi.get("/api/order/my-order/all/is-not-paid");
    orders.value = response.data.content;
    currentTab.value = "isNotPaid";
  } catch (e) {
    showToast('取得未付款的訂單時出錯了，請重新整理', 'error');
    console.log(e);
  }
};

onMounted(() => {
  handleFetchIsNotCompletedOrders();
});

</script>

<template>

  <div role="tablist" class="tabs tabs-border mb-5">
    <a @click="handleFetchIsNotCompletedOrders"
       role="tab"
       class="tab"
       :class="{ 'tab-active': currentTab === 'isNotCompleted' }"
    >
      正在處理的訂單
    </a>
    <a @click="handleFetchAllOrders"
       role="tab"
       class="tab"
       :class="{ 'tab-active': currentTab === 'all' }"
    >
      所有歷史訂單
    </a>
    <a @click="handleFetchIsNotPaidOrders"
       role="tab"
       class="tab"
       :class="{ 'tab-active': currentTab === 'isNotPaid' }"
    >
      未付款訂單
    </a>
  </div>

  <div v-if="orders.length ===0" class="text-center py-16">
    <p class="text-lg text-gray-600 mb-2">沒有訂單</p>
    <p class="text-sm text-gray-400">該分類的訂單並不存在</p>
  </div>

  <div v-else>
    <MyOrderListCard
        v-for="order in orders"
        :key="order.orderId"
        :order="order"
    />
  </div>

  <ToastAlert/>

</template>
