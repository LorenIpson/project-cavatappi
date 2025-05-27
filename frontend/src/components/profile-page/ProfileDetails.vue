<script setup>

import {onMounted, ref} from "vue";
import axios from "axios";
import {useMemberStore} from "@/stores/memberStore.js";
import router from "@/router/index.js";

const memberStore = useMemberStore();
const userProfile = ref(null);
const loading = ref(true);
const error = ref(null);

const goProfileEdit = async () => {
  router.push("/profile/edit");
}

onMounted(async () => {

  try {
    const response = await axios.get("http://localhost:8080/api/user/profile/me", {
      headers: {
        Authorization: `Bearer ${memberStore.token}`
      }
    });
    userProfile.value = response.data;
  } catch (e) {
    e.value = "取得使用者資料出錯了。";
    console.error(e);
  } finally {
    loading.value = false;
  }

});

</script>

<template>

  <div class="profile-block">
    <div v-if="loading">載入中...</div>
    <div v-else-if="error">{{ error }}</div>
    <div v-else>
      <p>使用者 ID：{{ userProfile.username }}</p>
      <p>姓名：{{ userProfile.lastName }} {{ userProfile.firstName }}</p>
      <p>信箱：{{ userProfile.email }}</p>
      <p>電話：{{ userProfile.phone }}</p>
      <p>生日：{{ userProfile.birthDate }}</p>
      <p>地址：{{ userProfile.address }}</p>
    </div>
  </div>

  <div class="profile-operate-button">
    <button @click="goProfileEdit">編 輯</button>
  </div>

</template>

<style scoped>

</style>
