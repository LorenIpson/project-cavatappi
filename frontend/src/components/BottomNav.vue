<script setup>
import {RouterLink} from "vue-router";
import {useMemberStore} from "@/stores/memberStore.js";

const memberStore = useMemberStore();
</script>

<template>
  <nav class="bottom-nav">
    <RouterLink to="/">首 頁</RouterLink>
    <RouterLink to="/menu">餐 點</RouterLink>
    <RouterLink to="/cart">購 物 車</RouterLink>
    <RouterLink v-if="memberStore.isLoggedIn" to="/profile">個 人 資 訊</RouterLink> <!-- TODO: 改為動態讀取 user 權限，判斷 Auth 決定連結目標-->
    <RouterLink v-else to="/login">登 入</RouterLink>
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
