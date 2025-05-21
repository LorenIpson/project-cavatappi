<script setup>
import {useMemberStore} from "@/stores/memberStore.js";

const memberStore = useMemberStore();
</script>

<template>
  <h1>這裡是個人資訊頁面耶</h1>
  <h3>使用者名稱：{{ memberStore.username }}</h3>
  <h3>UUID： {{ memberStore.uuid }}</h3>
  <h3>使用者權限：{{ memberStore.authorities?.[0] }}</h3>
  <h3>成年：{{ memberStore.adult }}</h3>

  <button @click="memberStore.logout(); $router.push('/login')">登出</button>
</template>

<style scoped>

</style>
