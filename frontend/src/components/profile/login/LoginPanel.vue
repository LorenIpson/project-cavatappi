<script setup>

import {useMemberStore} from "@/stores/memberStore.js";
import {ref} from "vue";
import {useRouter} from "vue-router";
import {useToast} from "@/composables/useToast.js";

const router = useRouter();
const memberStore = useMemberStore();
const {resultMessage, resultType, showToast} = useToast();

const username = ref('');
const password = ref('');

async function handleLogin() {

  if (!username.value || !password.value) {
    showToast("請填寫帳號與密碼", "error");
    return;
  }

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
      showToast("帳號或密碼錯誤", "error");
    }

    const data = await response.json();
    memberStore.setLogin(data);

    showToast("登入成功", "success");
    setTimeout(() => {
      router.push("/");
    }, 600);
  } catch (e) {
    showToast("帳號或密碼錯誤", "error");
  }

}

const loginWithGoogle = async () => {
  window.location.href = 'http://localhost:8080/oauth2/authorization/google';
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
    <input type="email" class="input w-full" placeholder="輸入使用者名稱" v-model="username"/>

    <label class="label">密碼</label>
    <input type="password" class="input w-full" placeholder="pepperoni-extra-cheese"
           v-model="password"/>

    <button @click="handleLogin" class="btn btn-neutral mt-4">登入</button>

    <div class="divider">或是</div>
    <button @click="loginWithGoogle" class="btn btn-soft">使用 Google 登入</button>
    <button @click="goToRegister" class="btn btn-soft mt-4">註冊新會員</button>
  </fieldset>

  <transition
    enter-active-class="transition-opacity duration-300"
    enter-from-class="opacity-0"
    enter-to-class="opacity-100"
    leave-active-class="transition-opacity duration-500"
    leave-from-class="opacity-100"
    leave-to-class="opacity-0"
  >
    <div class="toast toast-center pb-20" v-if="resultMessage">
      <div :class="['alert', resultType === 'success' ? 'alert-success' : 'alert-error']">
        <span>{{ resultMessage }}</span>
      </div>
    </div>
  </transition>

</template>
