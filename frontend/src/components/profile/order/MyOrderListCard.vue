<script setup>

import router from "@/router/index.js";

const {order} = defineProps(['order']);

const formattedReceiveDate = new Date(order.receiveDate).toLocaleString('zh-TW', {
  year: 'numeric',
  month: '2-digit',
  day: '2-digit',
  hour: '2-digit',
  hour12: false,
  minute: '2-digit'
});

const handleGoToOrderDetail = () => {
  router.push(`/profile/my-order/detail/${order.orderId}`);
};

</script>

<template>

  <div class="card bg-base-100 w-full shadow-lg mb-5">
    <div class="card-body p-4">

      <div class="flex justify-between mb-1">
        <div class="flex ">
          <div class="flex items-center mr-2">
            <svg
                v-if="order.completed"
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
            @click="handleGoToOrderDetail"
            class="badge font-semibold"
            :class="order.paid ? 'badge-neutral' : 'badge-outline'"
        >
          {{ order.paymentStatus }}
        </div>
      </div>

      <div class="ml-8 text-base">
        <div class="flex justify-between mb-0.5">
          <span class="font-semibold">訂購人：</span>
          <span>{{ order.buyerName }}</span>
        </div>

        <div class="flex justify-between mb-0.5">
          <span class="font-semibold">取餐日期：</span>
          <span>{{ formattedReceiveDate }}</span>
        </div>

        <div class="flex justify-between mb-0.5">
          <span class="font-semibold">訂單金額：</span>
          <span>NT$ {{ order.totalPrice }}</span>
        </div>
      </div>
      <button @click="handleGoToOrderDetail" class="btn btn-soft mt-1">查看細節</button>

    </div>
  </div>

</template>
