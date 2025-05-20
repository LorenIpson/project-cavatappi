<script setup>

import {RouterLink} from "vue-router";
</script>

<template>
  <nav class="bottom-nav">
    <RouterLink to="/">首 頁</RouterLink>
    <RouterLink to="/menu">餐 點</RouterLink>
    <RouterLink to="/cart">購 物 車</RouterLink>
    <RouterLink to="/login">登 入</RouterLink> <!-- TODO: 改為動態讀取 user 姓氏，判斷 Auth 決定連結目標-->
    <!--    <RouterLink to="/about">About</RouterLink>-->
  </nav>
</template>

<style scoped>

.bottom-nav {
  display: flex;
  justify-content: space-around;
  align-items: center;
  height: 60px;
  border-top: 1px solid #ddd;
  background-color: #759f6d;
}

.bottom-nav a {
  font-weight: bold;
  font-size: 16px;
}

.bottom-nav .router-link-active {
  font-size: 18px;
  text-shadow: 0 0 10px #ffffff;
}

</style>
