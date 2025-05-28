<script setup>

import {useMemberStore} from "@/stores/memberStore.js";
import {useRoute, useRouter} from "vue-router";
import {onMounted} from "vue";

const memberStore = useMemberStore();
const route = useRoute();
const router = useRouter();

onMounted(async () => {

  const token = route.query.token;

  if (!token) {
    console.error("登入失敗，沒有 Token");
    router.replace("/login");
    return;
  }

  if (token) {
    localStorage.setItem("access_token", token);
    memberStore.setLogin({token});
    router.replace({path: '/'});
  } else {
    console.error("OAuth 登入失敗，Token 出錯");
    router.replace({path: '/login'});
  }

})

</script>

<template>
  <p>登入中 ...</p>
</template>

<style scoped>

</style>
