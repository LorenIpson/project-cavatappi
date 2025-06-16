<script setup>

import {computed, onMounted, ref} from "vue";
import axios from "axios";
import CartItemCard from "@/components/cart/CartItemCard.vue";
import ToastAlert from "@/components/essential/ToastAlert.vue";
import {useToast} from "@/composables/useToast.js";
import router from "@/router/index.js";
import {useCartStore} from "@/stores/cartStore.js";
import {useMemberStore} from "@/stores/memberStore.js";

const {showToast} = useToast();

const memberStore = useMemberStore();
const cartStore = useCartStore();
const cartLocalItems = cartStore.items;
const cartItemList = ref(null);

const calculateTotalPrice = computed(() => {
  if (!cartItemList.value) return 0;

  return cartItemList.value.reduce((total, item) => {
    const base = item.basePrice || 0;
    const size = item.size?.extraPrice || 0;
    const dough = item.dough?.extraPrice || 0;
    const addons = item.addons?.reduce((sum, addon) => sum + (addon.extraPrice || 0), 0) || 0;
    return total + base + size + dough + addons;
  }, 0);
});

const goToCheckout = () => {
  if (cartStore.items && cartStore.items.length === 0) {
    showToast('購物車內還沒有餐點', 'info')
    return;
  }
  if (!memberStore.isLoggedIn) {
    showToast('請先登入才能進行結帳', 'info');
    return;
  }
  router.push({
    path: '/cart/checkout',
    state: {
      totalPrice: calculateTotalPrice.value
    }
  });
};

const handleRemoveItem = (itemSeqId, itemName) => {
  console.log('Handle remove item 觸發');
  console.log(itemSeqId);
  cartStore.removeItem(itemSeqId);
  cartItemList.value = cartItemList.value.filter(item => item.itemSeqId !== itemSeqId);
  showToast('成功移除 ' + itemName, 'success')
};

onMounted(async () => {
  try {
    const response = await axios.post("http://localhost:8080/api/order/cart/preview", cartLocalItems);
    cartItemList.value = response.data.map((item, index) => {
      const localItem = cartLocalItems[index];
      return {
        ...item,
        itemSeqId: localItem.itemSeqId
      };
    });
  } catch (e) {
    showToast("取得購物車資訊時出錯了", "error");
    console.error(e);
  }
});

</script>

<template>

  <div v-if="cartStore.items && cartStore.items.length === 0"
       class=" bg-base-100 rounded-box  text-center py-16 m-20">
    <p class="text-lg text-gray-600 mb-2">購物車是空的</p>
    <p class="text-sm text-gray-400">請在菜單頁面進行點餐</p>
  </div>

  <CartItemCard
    v-for="item in cartItemList"
    :key="item.itemSeqId"
    :item="item"
    @remove-item="handleRemoveItem"
  />

  <div v-if="cartStore.items && cartStore.items.length > 0">
    <div class="p-4 bg-base-100 rounded-2xl shadow-md">
      <div class="flex justify-between pb-2">
        <div class="pl-14">總價</div>
        <div class="pr-14">{{ calculateTotalPrice }}</div>
      </div>
      <div class="flex justify-center">
        <button @click="goToCheckout" class="btn btn-wide btn-primary w-full max-w-2xl mt-1">
          結帳
        </button>
      </div>
    </div>
  </div>

  <ToastAlert/>

</template>
