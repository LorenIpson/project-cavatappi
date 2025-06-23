<script setup>

import {useCartStore} from "@/stores/cartStore.js";
import {computed, ref} from "vue";
import ToastAlert from "@/components/essential/ToastAlert.vue";
import {useToast} from "@/composables/useToast.js";

const emit = defineEmits(["close-modal"]);
const props = defineProps(['item']);
const cartLocal = useCartStore();
const {showToast} = useToast();

const selectedSizeId = ref(null);
const selectedDoughId = ref(null);
const selectedAddOnIds = ref([]);

const selectedSize = computed(() => {
  return props.item?.sizes?.find(s => s.sizeId === selectedSizeId.value)?.size || null;
});

const getAddonPrice = (addon, sizeName) => {
  return addon.addonSizePrice.find(p => p.size === sizeName)?.addonExtraPrice ?? 0;
};

const calculateTotalPrice = computed(() => {
  if (!props.item) {
    return 0;
  }
  let totalPrice = props.item.basePrice;

  const selectedSizeObj = props.item.sizes.find(s => s.sizeId === selectedSizeId.value);
  if (selectedSizeObj) {
    totalPrice += selectedSizeObj.extraPrice;
  }

  const selectedDoughObj = props.item.doughs.find(d => d.doughId === selectedDoughId.value);
  if (selectedDoughObj) {
    totalPrice += selectedDoughObj.doughExtraPrice;
  }

  if (selectedSize.value) {
    for (const addOn of props.item.addons) {
      if (selectedAddOnIds.value.includes(addOn.addonId)) {
        const match = addOn.addonSizePrice.find(p => p.size === selectedSize.value);
        if (match) {
          totalPrice += match.addonExtraPrice;
        }
      }
    }
  }
  return totalPrice;
});

const addToCartLocal = () => {
  console.log('Add to cart 觸發');
  if (!selectedSizeId.value || !selectedDoughId.value) {
    showToast('請確認尺寸和餅皮', 'error');
    return;
  }

  const item = {
    itemSeqId: crypto.randomUUID(),
    itemType: 'PIZZA',
    itemId: props.item.pizzaId,
    sizeId: selectedSizeId.value,
    doughId: selectedDoughId.value,
    addons: selectedAddOnIds.value.map(id => ({addonId: id}))
  };
  showToast('成功加入購物車', 'success');
  cartLocal.addItem(item);
  emit("close-modal");
};

</script>

<template>
  <Teleport to="body">
    <div
      class="fixed inset-0 z-40 flex items-center justify-center backdrop-blur-sm bg-black/40 p-2"
      @click.self="emit('close-modal')"
    >
      <div class="m-3 p-7 bg-base-200  rounded-box shadow-xl w-96">
        <h2 class="text-xl font-bold mb-4">{{ item.name }}</h2>

        <!--客製化選項-->
        <!-- SIZE -->
        <div class="mb-4">
          <p class="font-semibold mb-2">尺寸</p>
          <div class="flex flex-wrap gap-3">
            <label
              v-for="size in item.sizes.filter(s => s.available)"
              :key="size.sizeId"
              class="flex items-center gap-2 cursor-pointer"
            >
              <input
                type="radio"
                class="radio radio-xs mr-1"
                name="size-group"
                :value="size.sizeId"
                v-model="selectedSizeId"
                :disabled="!size.inStock"
              />
              <span>
                {{ size.size }} (+${{ size.extraPrice.toFixed(2) }})
              </span>
            </label>
          </div>
        </div>

        <!-- DOUGH -->
        <div class="mb-4">
          <p class="font-semibold mb-2">餅皮</p>
          <div class="flex flex-wrap gap-3">
            <label
              v-for="dough in item.doughs.filter(d => d.available)"
              :key="dough.doughId"
              class="flex items-center gap-2 cursor-pointer"
            >
              <input
                type="radio"
                class="radio radio-xs mr-1"
                name="dough-group"
                :value="dough.doughId"
                v-model="selectedDoughId"
                :disabled="!dough.inStock"
              />
              <span>
                {{ dough.doughType }} (+${{ dough.doughExtraPrice.toFixed(2) }})
              </span>
            </label>
          </div>
        </div>


        <!-- ADD-ONS -->
        <div class="mb-4">
          <p class="font-semibold mb-2">額外配料</p>
          <div class="flex flex-col gap-2 max-h-40 overflow-auto pr-1">
            <label
              v-for="addOn in item.addons.filter(a => a.available)"
              :key="addOn.addonId"
              class="flex items-center justify-between mb-2 rounded-lg "
            >
              <!--suppress HtmlUnknownTag -->
              <div class="flex items-center gap-2">
                <input
                  type="checkbox"
                  class="checkbox checkbox-xs"
                  :value="addOn.addonId"
                  v-model="selectedAddOnIds"
                  :disabled="!selectedSizeId || !addOn.inStock"
                >
                <span>{{ addOn.addonName }}</span>
              </div>

              <!--suppress HtmlUnknownTag -->
              <div>
                <template v-if="selectedSizeId">
                  <span v-if="selectedSizeId">
                    +${{ getAddonPrice(addOn, selectedSize)?.toFixed(2) }}
                  </span>
                </template>
              </div>

            </label>
          </div>
        </div>

        <div class="modal-action mt-6 flex justify-between items-center mr-1">
          <span class="font-bold text-lg">NT$ {{ calculateTotalPrice.toFixed(2) }}</span>
          <button type="button"
                  class="btn btn-primary"
                  @click="addToCartLocal">
            加入購物車
          </button>
        </div>

      </div>
      <ToastAlert/>
    </div>
  </Teleport>

</template>
