<script setup>

defineProps(["item"]);
const emit = defineEmits(["open-modal"]);

const openItemModal = (pizzaId) => {
  emit("open-modal", pizzaId);
}

</script>

<template>

  <div class="card bg-base-100 w-full shadow-sm">

<!--    <figure><img src="https://img.daisyui.com/images/stock/photo-1606107557195-0e29a4b5b4aa.webp"-->
<!--                 alt="Shoes"/></figure>-->
    <figure><img :src="'data:image/jpeg;base64,' + item.pizzaImage" alt="Pizza"/></figure>

    <div class="card-body p-4">
      <h2 class="card-title justify-between">
        {{ item.pizzaName }}
        <!--suppress HtmlUnknownTag -->
        <div v-if="item.isInStock" class="badge badge-secondary badge-sm">售完</div>
      </h2>

      <p>{{ item.pizzaDescription }}</p>

      <div class="card-actions justify-between mt-2 space-x-2">
        <button @click="openItemModal(item.pizzaId)" class="btn btn-soft btn-xs btn-outline">查看細節
        </button>
        <button @click="openItemModal(item.pizzaId)" class="btn btn-soft btn-xs btn-outline">
          ${{ item.basePrice }}
        </button>
      </div>
    </div>

  </div>

</template>
