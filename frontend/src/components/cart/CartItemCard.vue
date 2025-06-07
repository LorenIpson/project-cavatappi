<script setup>

import {computed} from "vue";

const props = defineProps(["item"]);
const emit = defineEmits(["remove-item"]);

const removeItem = (itemSeqId, itemName) => {
  emit("remove-item", itemSeqId, itemName);
}

const singlePrice = computed(() => {
  const base = props.item.basePrice || 0;
  const size = props.item.size.extraPrice || 0;
  const dough = props.item.dough.extraPrice || 0;
  const addons = props.item.addons?.reduce((sum, addon) => sum + (addon.extraPrice || 0), 0) || 0;
  return base + size + dough + addons;
});

</script>

<template>

  <ul class="list bg-base-100 rounded-box shadow-md mb-2">

    <li class="list-row">

      <!--<div class="text-4xl font-thin opacity-30 tabular-nums">01</div>-->
      <div><img class="size-10 rounded-box"
                src="https://img.daisyui.com/images/profile/demo/1@94.webp"
                alt="Yolo"/>
      </div>

      <div class="list-col-grow">
        <div class="flex justify-between pb-1">
          <span>{{ item.itemName }}</span>
          <span class="opacity-60">{{ item.basePrice }}</span>
        </div>

        <div>
          <hr class="pb-2 opacity-60">
        </div>

        <div class="text-xs uppercase opacity-60 pb-1">
          <div class="font-semibold">尺寸：</div>
          <div class="flex justify-between pl-1">
            <span>- {{ item.size.size }}</span>
            <span>{{ item.size.extraPrice }}</span>
          </div>
        </div>

        <div class="text-xs uppercase opacity-60 pb-1">
          <div class="font-semibold">餅皮：</div>
          <div class="flex justify-between pl-1">
            <span>- {{ item.dough.doughType }}</span>
            <span>{{ item.dough.extraPrice }}</span>
          </div>
        </div>

        <div class="text-xs uppercase font-semibold opacity-60">額外配料：</div>
        <ul class="text-xs pb-2">
          <li class="opacity-60 flex justify-between" v-for="addon in item.addons"
              :key="addon.addonId">
            <span>- {{ addon.name }}</span>
            <span>{{ addon.extraPrice }}</span>
          </li>
        </ul>

        <div>
          <hr class="pb-2 opacity-60">
        </div>

        <div class="flex justify-between">
          <div>單價：</div>
          <span>{{ singlePrice }}</span>
        </div>
      </div>

      <button @click="removeItem(item.itemSeqId, item.itemName)" class="btn btn-square btn-ghost">
        <svg width="24px" height="24px" stroke-width="1.5" viewBox="0 0 24 24" fill="none"
             xmlns="http://www.w3.org/2000/svg" color="#000000">
          <path
            d="M9.17218 14.8284L12.0006 12M14.829 9.17157L12.0006 12M12.0006 12L9.17218 9.17157M12.0006 12L14.829 14.8284"
            stroke="#000000" stroke-width="1.5" stroke-linecap="round"
            stroke-linejoin="round"></path>
          <path
            d="M12 22C17.5228 22 22 17.5228 22 12C22 6.47715 17.5228 2 12 2C6.47715 2 2 6.47715 2 12C2 17.5228 6.47715 22 12 22Z"
            stroke="#000000" stroke-width="1.5" stroke-linecap="round"
            stroke-linejoin="round"></path>
        </svg>
      </button>

    </li>

  </ul>

</template>
