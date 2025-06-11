<script setup>

// TODO: 如購物車是空的，不開放結帳。
import {ref} from "vue";
import {useCartStore} from "@/stores/cartStore.js";
import ToastAlert from "@/components/essential/ToastAlert.vue";
import {useToast} from "@/composables/useToast.js";
import axios from "axios";
import router from "@/router/index.js";

const props = defineProps(["totalPrice"]);
const price = props.totalPrice;
const cartStore = useCartStore();
const {showToast} = useToast();

// 設定 datetime-local 的限制
const now = new Date();
const sevenDaysLater = new Date();
sevenDaysLater.setDate(now.getDate() + 7);
const formateDatetime = (date) => {
  return date.toISOString().slice(0, 16);
};
const minDateTime = ref(formateDatetime(now));
const maxDateTime = ref(formateDatetime(sevenDaysLater));

const orderForm = ref({
  "buyerName": "",
  "buyerPhone": "",
  "buyerMessage": "",
  "receiveDate": "",
  "paymentMethod": ""
})

const handleSubmitOrder = async () => {
  if (
    !orderForm.value.buyerName ||
    !orderForm.value.buyerPhone ||
    !orderForm.value.receiveDate) {
    showToast("資料未填寫完整", "error")
    return;
  } else if (!orderForm.value.paymentMethod) {
    showToast("請選擇付款方式", "error")
    return;
  }

  const orderRequestBody = {
    ...orderForm.value,
    items: cartStore.items.map(item => ({
      itemType: item.itemType,
      itemId: item.itemId,
      sizeId: item.sizeId,
      doughId: item.doughId,
      addons: item.addons.map(addon => ({addonId: addon.addonId}))
    }))
  };
  console.log(orderRequestBody);

  try {
    const response = await axios.post('http://localhost:8080/api/order/place-new-order', orderRequestBody);
    if (!response.data.success) {
      showToast("訂單送出失敗", "error")
      return;
    }
    if (response.data.external) {
      window.location.href = response.data.redirectURL;
    } else {
      showToast("訂單送出成功", "success")
      await router.push(response.data.redirectURL);
    }
    // /cart/payment/confirm
    // https://sandbox-web-pay.line.me/web/payment/wait?transactionReserveId=Q0MzMGZrQm9vZmZBRzNXQ2dHSVFOZzJjUGt4U0p6RjVpbGNxazJ2YmkrVUpRK2Q1aHk5aElrQ2JJUnNQTU42NA
    // /cart/payment/line-pay/confirm?orderId=46&transactionId=2025061102289228610
  } catch (e) {
    showToast("伺服器連線失敗", "error")
  }

};

</script>

<template>

  <div class="flex justify-center">
    <fieldset
      class="fieldset bg-base-200 border-base-300 rounded-box w-full border p-4 max-w-md mb-4">

      <label class="label">訂購人稱呼</label>
      <input type="text" class="input w-full" placeholder="必須填寫｜輸入訂購人稱呼"
             v-model="orderForm.buyerName"
      />

      <label class="label">電話</label>
      <input type="tel" class="input validator w-full" required
             placeholder="必須填寫｜輸入行動電話號碼"
             pattern="[0-9]*" minlength="10" maxlength="10" title="Must be 10 digits"
             v-model="orderForm.buyerPhone"
      />

      <label class="label">取餐時間</label>
      <input type="datetime-local" class="input validator w-full text-black"
             :min="minDateTime"
             :max="maxDateTime"
             v-model="orderForm.receiveDate"
      />

      <label class="label">訂單備註</label>
      <input type="text" class="input w-full"
             v-model="orderForm.buyerMessage"
      />

    </fieldset>
  </div>

  <div class="flex justify-center">
    <fieldset class="fieldset bg-base-200 border-base-300 rounded-box w-full border p-4 max-w-md">


      <label class="label">付款方式</label>
      <select class="select w-full"
              v-model="orderForm.paymentMethod"
      >
        <option disabled selected value="">請選擇付款方式</option>
        <option value="LINE_PAY">LINE Pay</option>
        <option value="CASH_PAY">取餐付款</option>
      </select>

      <label class="label">總金額</label>
      <button class="btn">NT$ {{ price }}</button>

      <button @click="handleSubmitOrder" class="btn btn-neutral mt-4">送出訂單</button>
    </fieldset>
  </div>

  <ToastAlert/>

</template>
