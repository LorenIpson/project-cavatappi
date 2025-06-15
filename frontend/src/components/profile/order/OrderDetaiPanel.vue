<script setup>

import {computed} from "vue";

const {order} = defineProps(['order']);

const paymentMethods = computed(() => {
  switch (order.paymentMethod) {
    case 'LINE_PAY':
      return 'LINE Pay';
    case 'CASH_PAY':
      return '待取餐付款';
    default:
      return '其它';
  }
});

const calcSinglePrice = (item) => {
  const base = item.itemBasePrice || 0;
  const size = item.itemSpecs?.sizeExtraPrice || 0;
  const dough = item.itemSpecs?.doughExtraPrice || 0;
  const addons = item.itemAddons?.reduce((sum, addon) => sum + (addon.addonExtraPrice || 0), 0) || 0;
  return base + size + dough + addons;
};

const formattedReceiveDate = computed(() => {
  return new Date(order.receiveDate).toLocaleString('zh-TW', {
    year: 'numeric',
    month: '2-digit',
    day: '2-digit',
    hour: '2-digit',
    minute: '2-digit',
    hour12: false
  });
});

const formattedOrderDate = computed(() => {
  return new Date(order.orderedDate).toLocaleString('zh-TW', {
    year: 'numeric',
    month: '2-digit',
    day: '2-digit',
    hour: '2-digit',
    minute: '2-digit',
    hour12: false
  });
});

const handleGoToPaymentURL = () => {
  const url = order.paymentRedirectURL;
  if (url) {
    window.location.href = url;
  } else {
    console.error('沒有導向連結');
  }
}

</script>

<template>

  <div class="card bg-base-100 w-full shadow-xl mb-5 max-w-lg mx-auto">
    <div class="card-body p-4">

      <div class="flex justify-between mb-2">
        <div class="flex ">
          <span class="text-xl font-semibold"># {{ order.orderId }}</span>
        </div>
        <div
            class="badge badge-lg font-semibold"
            :class="order.paid ? 'badge-neutral' : 'badge-outline'"
        >
          {{ order.paymentStatus }}
        </div>
      </div>

      <div class="mb-1">
        <span class="text-xl font-semibold">訂單內容</span>
      </div>

      <div class="bg-base-200 rounded-xl shadow-lg p-4">

        <div v-for="item in order.items">
          <div class="grid grid-cols-12 mb-1">
            <span class="font-semibold col-span-5 ">口味</span>
            <div class="col-span-7 flex justify-between">
              <span class="">{{ item.itemName }}</span>
              <span class="">NT$ {{ item.itemSpecs.sizeExtraPrice }}</span>
            </div>
          </div>

          <div class="grid grid-cols-12 mb-1">
            <span class="font-semibold col-span-5 ">尺寸</span>
            <div class="col-span-7 flex justify-between">
              <span class="">{{ item.itemSpecs.size }}</span>
              <span class="">NT$ {{ item.itemBasePrice }}</span>
            </div>
          </div>

          <div class="grid grid-cols-12 mb-1">
            <span class="font-semibold col-span-5 ">餅皮</span>
            <div class="col-span-7 flex justify-between">
              <span class="">{{ item.itemSpecs.doughType }}</span>
              <span class="">NT$ {{ item.itemSpecs.doughExtraPrice }}</span>
            </div>
          </div>


          <div class="grid grid-cols-12 mb-1">
            <span class="font-semibold col-span-5">額外配料</span>
            <div class="col-span-7 w-full">
              <div
                  v-for="addon in item.itemAddons"
                  :key="addon.name"
                  class="flex justify-between"
              >
                <span>{{ addon.name }}</span>
                <span>NT$ {{ addon.addonExtraPrice }}</span>
              </div>
            </div>
          </div>
          <div class="grid grid-cols-12 text-base font-semibold mt-5 rounded-box -m-2 p-2">
            <span class="col-span-5">單項總價</span>
            <div class="col-span-7 flex justify-between">
              <span></span>
              <span>NT$ {{ calcSinglePrice(item) }}</span>
            </div>
          </div>

          <div class="divider"></div>
        </div>

        <div class="text-base font-semibold flex justify-between mb-2">
          <span>訂單總金額</span>
          <span>NT$ {{ order.totalPrice }}</span>
        </div>
        <div class="text-base font-semibold flex justify-between mb-3">
          <span>付款方式</span>
          <span>{{ paymentMethods }}</span>
        </div>

        <div class="flex justify-center">
          <button v-if="paymentMethods === 'LINE Pay' && order.paid === false " @click="handleGoToPaymentURL"
                  class="btn btn-neutral w-full">前往 LINE Pay 付款頁面
          </button>
        </div>

      </div>

      <div class="divider mb-1"></div>


      <div class="mb-1">
        <span class="text-xl font-semibold">額外資訊</span>
      </div>

      <div class="bg-base-200 rounded-xl shadow-lg p-4 mb-1">

        <div class="flex justify-between mb-2">
          <span class="font-semibold">訂購人：</span>
          <span>{{ order.buyerName }}</span>
        </div>
        <div class="flex justify-between mb-2">
          <span class="font-semibold">聯絡電話：</span>
          <span>{{ order.buyerPhone }}</span>
        </div>
        <div class="flex justify-between mb-2">
          <span class="font-semibold">訂單備註：</span>
          <span>{{ order.buyerMessage }}</span>
        </div>

        <div class="flex justify-between mb-2">
          <span class="font-semibold">訂購日期：</span>
          <span>{{ formattedOrderDate }}</span>
        </div>

        <div class="flex justify-between mb">
          <span class="font-semibold">預計取餐日期：</span>
          <span>{{ formattedReceiveDate }}</span>
        </div>

      </div>

    </div>
  </div>

</template>
