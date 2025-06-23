<script setup>

import axiosApi from "@/composables/useAxios.js";
import ToastAlert from "@/components/essential/ToastAlert.vue";
import {useToast} from "@/composables/useToast.js";
import {ref} from "vue";

const {order} = defineProps(['order']);
const localOrder = ref({...order});
const {showToast} = useToast();

const formattedReceiveDate = new Date(order.receiveDate).toLocaleString('zh-TW', {
  year: 'numeric',
  month: '2-digit',
  day: '2-digit',
  hour: '2-digit',
  hour12: false,
  minute: '2-digit'
});

const handleConfirmOrder = async (orderId) => {
  try {
    await axiosApi.put(`/api/admin/order/${orderId}/confirmed`);
    localOrder.value.orderStatus = '確認訂單';
    localOrder.value.completed = false;
    showToast('訂單號碼 ' + orderId + ' 已確認', 'success');
  } catch (e) {
    showToast('操作確認訂單時失敗', 'error');
    console.log(e)
  }
};

const handlePreparingOrder = async (orderId) => {
  try {
    await axiosApi.put(`/api/admin/order/${orderId}/preparing`);
    localOrder.value.orderStatus = '正在準備餐點';
    localOrder.value.completed = false;
    showToast('訂單號碼 ' + orderId + ' 正在準備中', 'success');
  } catch (e) {
    showToast('操作準備餐點狀態時失敗', 'error');
    console.log(e)
  }
};

const handleOrderPrepared = async (orderId) => {
  try {
    await axiosApi.put(`/api/admin/order/${orderId}/prepared`);
    localOrder.value.orderStatus = '等待取餐';
    localOrder.value.completed = false;
    showToast('訂單號碼 ' + orderId + ' 正在等待取餐', 'success');
  } catch (e) {
    showToast('操作餐點準備完成時失敗', 'error');
    console.log(e)
  }
};

const handleOrderCompleted = async (orderId) => {
  try {
    await axiosApi.put(`/api/admin/order/${orderId}/completed`);
    localOrder.value.orderStatus = '訂單完成';
    localOrder.value.completed = true;
    showToast('訂單號碼 ' + orderId + ' 已完成取餐', 'success');
  } catch (e) {
    showToast('操作確認完成訂單時失敗', 'error');
    console.log(e)
  }
};

</script>

<template>

  <div class="collapse bg-base-100 w-full shadow-lg mb-5">

    <input type="checkbox"/>
    <div class="collapse-title font-semibold pr-4 py-4">

      <!-- ORDER STATUS -->
      <div class="flex justify-between mb-2">
        <div class="flex">
          <div class="flex items-center mr-2">
            <svg
              v-if="localOrder.completed"
              class="w-6 h-6"
              viewBox="0 0 24 24" fill="none"
              xmlns="http://www.w3.org/2000/svg"
            >
              <path fill-rule="evenodd" clip-rule="evenodd"
                    d="M12 1.25C6.06294 1.25 1.25 6.06294 1.25 12C1.25 17.9371 6.06294 22.75 12 22.75C17.9371 22.75 22.75 17.9371 22.75 12C22.75 6.06294 17.9371 1.25 12 1.25ZM7.53044 11.9697C7.23755 11.6768 6.76268 11.6768 6.46978 11.9697C6.17689 12.2626 6.17689 12.7374 6.46978 13.0303L9.46978 16.0303C9.76268 16.3232 10.2376 16.3232 10.5304 16.0303L17.5304 9.03033C17.8233 8.73744 17.8233 8.26256 17.5304 7.96967C17.2375 7.67678 16.7627 7.67678 16.4698 7.96967L10.0001 14.4393L7.53044 11.9697Z"
                    fill="currentColor"></path>
            </svg>
            <svg
              v-else
              class="w-6 h-6"
              stroke-width="1.5"
              viewBox="0 0 24 24" fill="none"
              xmlns="http://www.w3.org/2000/svg"
            >
              <path
                d="M12 22C17.5228 22 22 17.5228 22 12C22 6.47715 17.5228 2 12 2C6.47715 2 2 6.47715 2 12C2 17.5228 6.47715 22 12 22Z"
                stroke="currentColor"
                stroke-linecap="round"
                stroke-linejoin="round"
              />
            </svg>
          </div>
          <span class="text-base font-semibold"># {{ order.orderId }}</span>
        </div>
        <div
          @click=""
          class="badge font-semibold"
          :class="order.paid ? 'badge-neutral' : 'badge-outline'"
        >
          {{ order.paymentStatus }}
        </div>
      </div>

      <!-- ORDER INFO -->
      <div class="ml-8 text-base">
        <div class="flex justify-between mb-0.5">
          <span class="font-semibold">訂單狀態：</span>
          <span class="font-normal">{{ localOrder.orderStatus }}</span>
        </div>
        <div class="flex justify-between mb-0.5">
          <span class="font-semibold">訂購人：</span>
          <span class="font-normal">{{ order.buyerName }}</span>
        </div>
        <div class="flex justify-between mb-0.5">
          <span class="font-semibold">聯絡電話：</span>
          <span class="font-normal">{{ order.buyerPhone }}</span>
        </div>
        <div class="flex justify-between mb-0.5">
          <span class="font-semibold">訂單備註：</span>
          <span class="font-normal">{{ order.buyerMessage ? order.buyerMessage : '-' }}</span>
        </div>
        <div class="flex justify-between mb-0.5">
          <span class="font-semibold">取餐日期：</span>
          <span class="font-normal">{{ formattedReceiveDate }}</span>
        </div>
      </div>

    </div>

    <div class="collapse-content text-sm">
      <!-- ORDER DETAILS -->
      <div class="ml-8 text-base">
        <div class="mb-0.5">
          <span class="font-semibold">訂單內容：</span>
          <div class="bg-base-200 rounded-xl shadow-lg p-4 mt-3">
            <div v-for="item in order.items" class="">
              <div class="mb-1 flex justify-between">
                <span class="font-semibold">口味</span>
                <div class="">
                  <span class="">{{ item.itemName }}</span>
                </div>
              </div>
              <div class="mb-1 flex justify-between">
                <span class="font-semibold">尺寸</span>
                <div class="">
                  <span class="">{{ item.itemSpecs.size }}</span>
                </div>
              </div>
              <div class="mb-1 flex justify-between">
                <span class="font-semibold">餅皮</span>
                <div class="">
                  <span class="">{{ item.itemSpecs.doughType }}</span>
                </div>
              </div>
              <div class="divider"></div>
            </div>
            <button class="btn btn-neutral w-full mb-5">前往訂單詳細頁面</button>
            <button @click="handleConfirmOrder(order.orderId)" class="btn btn-neutral w-full mb-5">
              確認訂單
            </button>
            <button @click="handlePreparingOrder(order.orderId)" class="btn btn-neutral w-full mb-5">正在準備訂單</button>
            <button @click="handleOrderPrepared(order.orderId)" class="btn btn-neutral w-full mb-5">訂單製作完成</button>
            <button @click="handleOrderCompleted(order.orderId)" class="btn btn-neutral w-full ">訂單取餐完成</button>
          </div>
        </div>
      </div>

    </div>

  </div>

  <ToastAlert/>

</template>
