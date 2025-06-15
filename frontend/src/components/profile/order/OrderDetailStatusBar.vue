<script setup>

import {computed} from "vue";

const {order} = defineProps(['order']);
const stepStages = ['收到訂單', '正在準備餐點', '等待取餐', '訂單完成'];

const currentStepIndex = computed(() => {
  return stepStages.indexOf(order.orderStatus);
});

</script>

<template>

  <div class="-mx-5">
    <ul class="steps text-sm w-full mb-5">
      <li
          v-for="(step, index) in stepStages"
          :key="step"
          class="step"
          :class="{ 'step-neutral': index <= currentStepIndex }"
      >
        {{ step }}
      </li>
    </ul>
  </div>

</template>
