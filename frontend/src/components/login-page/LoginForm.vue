<script setup>

import {ref} from "vue";
import {useMemberStore} from "@/stores/memberStore.js";
import {useRouter} from "vue-router";

const username = ref('');
const password = ref('');
const errorMessage = ref('');

const router = useRouter();
const memberStore = useMemberStore();

async function handleLogin() {

  errorMessage.value = ''; // 記得重置錯誤ㄉ訊息

  try {

    const response = await fetch('http://localhost:8080/api/user/login', {
      method: 'POST',
      headers: {'Content-Type': 'application/json'},
      body: JSON.stringify({
        username: username.value,
        password: password.value,
      })
    });

    if (!response.ok) {
      errorMessage.value = '帳號或密碼錯誤'
      return
    }

    const data = await response.json();
    memberStore.setLogin(data);

    router.push('/');

  } catch (e) {
    errorMessage.value = '帳號或密碼錯誤';
  }

}

</script>

<template>
  <form class="login-form" @submit.prevent="handleLogin">
    <input v-model="username" placeholder="Username"/>
    <input v-model="password" type="password" placeholder="Password"/>
    <button type="submit">登 入</button>
    <p class="error" v-if="errorMessage">{{ errorMessage }}</p>
  </form>
</template>

<style scoped>

.login-form {
  display: flex;
  flex-direction: column;
  gap: 24px;
}

input {
  padding: 10px;
  font-size: 16px;
}

button {
  padding: 12px;
  background-color: #759f6d;
  color: white;
  font-size: 16px;
  border: none;
  border-radius: 8px;
}

.error {
  color: red;
  font-size: 14px;
}

</style>
