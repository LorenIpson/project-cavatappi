<script setup>

import AdminOrderListCard from "@/components/admin/order/AdminOrderListCard.vue";
import {onMounted, ref} from "vue";
import {useToast} from "@/composables/useToast.js";
import axiosApi from "@/composables/useAxios.js";

const {showToast} = useToast();
const currentTab = ref('todayOrders');
const orders = ref([]);
const loading = ref(true);
const now = new Date().toISOString().slice(0, 10);

const handleFetchTodayOrders = async () => {
  try {
    const response = await axiosApi.get(`/api/admin/order/get/by/date?date=${now}`);
    orders.value = response.data.content;
    currentTab.value = 'todayOrders';
  } catch (e) {
    showToast('取得當日訂單時出錯了', 'error');
    console.error(e);
  } finally {
    loading.value = false;
  }
};

const handleFetchTodayOrdersNotCompleted = async () => {
  try {
    const response = await axiosApi.get(`/api/admin/order/get/by/is-not-completed/date?date=${now}`);
    orders.value = response.data.content;
    currentTab.value = 'todayNotCompleted';
  } catch (e) {
    showToast('取得當日未完成訂單時出錯了', 'error');
    console.error(e);
  } finally {
    loading.value = false;
  }
};

onMounted(() => {
  handleFetchTodayOrders();
});

</script>

<template>

  <div role="tablist" class="tabs tabs-border mb-5">
    <a @click="handleFetchTodayOrders"
       role="tab"
       class="tab"
       :class="{ 'tab-active': currentTab === 'todayOrders' }"
    >
      今日所有訂單
    </a>
    <a @click="handleFetchTodayOrdersNotCompleted"
       role="tab"
       class="tab"
       :class="{ 'tab-active': currentTab === 'todayNotCompleted' }"
    >
      今日未完成訂單
    </a>
    <a @click=""
       role="tab"
       class="tab"
       :class="{ 'tab-active': currentTab === 'isNotPaid' }"
    >
      查詢訂單
    </a>
  </div>

  <div v-if="orders.length ===0 && !loading" class="text-center py-16">
    <p class="text-lg text-gray-600 mb-2">沒有訂單</p>
    <p class="text-sm text-gray-400">該分類的訂單並不存在</p>
  </div>

  <div>
    <AdminOrderListCard
      v-for="order in orders"
      :key="order.orderId"
      :order="order"
    />
  </div>

</template>
