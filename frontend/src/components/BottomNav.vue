<script setup>
import {RouterLink, useRoute} from "vue-router";
import {useMemberStore} from "@/stores/memberStore.js";
import {computed} from "vue";

const memberStore = useMemberStore();
const route = useRoute()

const isPersonalAccountRelatePage = computed(() => {
  return ['/login', '/register', '/profile', '/profile/edit'].includes(route.path) // location.pathname 不支援 reactive
});

const accountLinkPath = computed(() => {
  return memberStore.isLoggedIn ? '/profile' : '/login';
});

const accountLinkText = computed(() => {
  return memberStore.isLoggedIn ? '個 人 資 訊' : '登 入';
});

console.log("登入狀態檢查：" + memberStore.isLoggedIn);

</script>

<template>
  <nav class="bottom-nav">
    <RouterLink to="/">首 頁</RouterLink>
    <RouterLink to="/menu">餐 點</RouterLink>
    <RouterLink to="/cart">購 物 車</RouterLink>
    <RouterLink
      :to="accountLinkPath"
      :class="{ 'router-link-active': isPersonalAccountRelatePage }"
    >
      {{ accountLinkText }}
    </RouterLink>
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
