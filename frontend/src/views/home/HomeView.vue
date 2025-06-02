<script setup>
import AdSlides from "@/components/home-page/AdSlides.vue";
import Recommendation from "@/components/home-page/Recommendation.vue";
import router from "@/router/index.js";
</script>

<template>
  <h1>Main Content Here 480px ===============</h1>
  <AdSlides></AdSlides>
  <Recommendation></Recommendation>

  <h4>臨時用捷徑</h4>
  <button @click="router.push('/menu/admin/edit')">餐點編輯頁面</button>
</template>
