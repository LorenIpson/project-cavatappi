<script setup>

defineProps(["item"]);

// const itemDetails = ref({
//   pizzaId: 0,
//   name: "",
//   description: "",
//   // image
//   basePrice: 0,
//   isInStock: false,
//   isAvailable: false,
//   sizes: [{
//     size: "",
//     extraPrice: 0,
//     isInStock: false,
//     isAvailable: false
//   }],
//   doughs: [{
//     doughType: "",
//     doughExtraPrice: 0,
//     isInStock: false,
//     isAvailable: false
//   }],
//   addons: [{
//     addonName: "",
//     isInStock: false,
//     isAvailable: false,
//     addonSizePrice: [{size: "", extraPrice: 0, isInStock: false, isAvailable: false}]
//   }]
// });


// const showDetails = async () => {
//   try {
//     itemDetails.value = await axios.get(`http://localhost:8080/api/menu/pizza/get/${props.item.pizzaId}`);
//   } catch (e) {
//     console.error('取得餐點細節時出錯了', e);
//   }
// }

</script>

<template>

  <div class="item-card" @click="showDetails">

    <!--    <div class="item-image">-->
    <!--      <img :src="'data:image/jpeg;base64,' + item.pizzaImage" alt="img">-->
    <!--    </div>-->

    <div class="item-text">
      <h5 class="item-name">{{ item.pizzaName }}</h5>
      <h5 class="item-description">{{ item.pizzaDescription }}</h5>
      <h5 class="item-base-price">{{ item.basePrice }}</h5>
    </div>

  </div>

</template>

<style scoped>

.item-card {
  border: #79ad6f 1px solid;
}

</style>
