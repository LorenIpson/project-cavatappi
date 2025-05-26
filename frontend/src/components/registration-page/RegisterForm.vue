<script setup>

import {ref} from "vue";
import axios from "axios";
import router from "@/router/index.js";

const form = ref({
  username: "",
  email: "",
  phone: "",
  password: "",
  confirmPassword: "",
  firstName: "",
  lastName: "",
  birthDate: "",
  address: "",
});

const loading = ref(false);
const error = ref(null);
const successMessage = ref(null);

const register = async () => {

  error.value = null;
  successMessage.value = null;
  loading.value = true;

  if (form.value.password !== form.value.confirmPassword) {
    error.value = "密碼驗證失敗";
    loading.value = false;
    return;
  }

  const requestBody = {
    username: form.value.username,
    email: form.value.email,
    firstName: form.value.firstName,
    lastName: form.value.lastName,
    birthDate: form.value.birthDate,
    phone: form.value.phone,
    address: form.value.address,
    password: form.value.password,
  };

  try {
    const response = await axios
      .post('http://localhost:8080/api/user/register/memberRegister', requestBody);
    /* TODO:
        1. 後端新增 APIResponse。
        2. 後端新增動態偵測重複使用者 ID 與電子信相。
     */
    successMessage.value = "註冊成功，請登入";
    setTimeout(() => {
      router.push("/login");
    }, 2000);
  } catch (e) {
    error.value = "註冊失敗";
    console.log(e);
  } finally {
    loading.value = false;
  }

};

</script>

<template>

  <h2>填寫註冊資料的元件</h2>

  <form @submit.prevent="register">
    <div v-if="error" class="error">{{ error }}</div>
    <div v-if="successMessage" class="success">{{ successMessage }}</div>

    <label>使用者名稱</label>
    <input v-model="form.username" placeholder="註冊後不得更改" type="text" required/>

    <label>電子信箱</label>
    <input v-model="form.email" type="email" required/>

    <label>密碼</label>
    <input v-model="form.password" placeholder="長度至少六個字" type="password" required/>

    <label>確認密碼</label>
    <input v-model="form.confirmPassword" type="password" required/>

    <label>電話</label>
    <input v-model="form.phone" type="text" required/>

    <label>姓氏</label>
    <input v-model="form.lastName" type="text" required/>

    <label>名字</label>
    <input v-model="form.firstName" type="text"/>

    <label>生日</label>
    <input v-model="form.birthDate" type="date"/>

    <label>地址</label>
    <input v-model="form.address" type="text"/>

    <button type="submit" :disabled="loading">儲 存</button>
  </form>

</template>

<style scoped>

</style>
