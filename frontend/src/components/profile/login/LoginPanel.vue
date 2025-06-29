<script setup>

import {useMemberStore} from "@/stores/memberStore.js";
import {ref} from "vue";
import {useRouter} from "vue-router";
import {useToast} from "@/composables/useToast.js";
import axiosApi from "@/composables/useAxios.js";
import ToastAlert from "@/components/essential/ToastAlert.vue";

const router = useRouter();
const memberStore = useMemberStore();
const {showToast} = useToast();

const loginForm = ref({
  username: '',
  password: ''
});

async function handleLogin() {

  if (!loginForm.value.username || !loginForm.value.password) {
    showToast("請填寫帳號與密碼", "error");
    return;
  }

  try {
    const response = await axiosApi.post('/api/user/login', loginForm.value);
    console.log(response);
    if (response.status !== 200) {
      console.log('inner');
      showToast("帳號或密碼錯誤", "error");
      return;
    }

    const data = await response.data;
    memberStore.setLogin(data);

    showToast("登入成功", "success");
    setTimeout(() => {
      router.push("/");
    }, 600);
  } catch (e) {
    console.log('catch');
    showToast("帳號或密碼錯誤", "error");
  }

}

const loginWithGoogle = () => {
  const gatewayBaseUrl = import.meta.env.VITE_API_URL;
  window.location.href = `${gatewayBaseUrl}/oauth2/authorization/google`;
};

const goToRegister = async () => {
  await router.push("/profile/register");
};

</script>

<template>

  <!-- 登入功能與註冊按鈕 -->
  <fieldset class="fieldset bg-base-200 border-base-300 rounded-box w-full border p-4 max-w-md">
    <!--<legend class="fieldset-legend">登入</legend>-->

    <label class="label">使用者名稱</label>
    <input type="email" class="input w-full" placeholder="輸入使用者名稱"
           v-model="loginForm.username"/>

    <label class="label">密碼</label>
    <input type="password" class="input w-full" placeholder="pepperoni-extra-cheese"
           v-model="loginForm.password"/>

    <button @click="handleLogin" class="btn btn-neutral mt-4">登入</button>

    <div class="divider">或是</div>
    <button @click="loginWithGoogle" class="btn btn-soft">使用 Google 登入</button>
    <button @click="goToRegister" class="btn btn-soft mt-4">註冊新會員</button>
  </fieldset>

  <ToastAlert/>

</template>
