<script setup>

import {ref} from "vue";
import router from "@/router/index.js";
import axios from "axios";
import {useToast} from "@/composables/useToast.js";

const {resultMessage, resultType, showToast} = useToast();

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

const handleRegister = async () => {

  if (
    !form.value.username ||
    !form.value.password ||
    !form.value.confirmPassword ||
    !form.value.phone ||
    !form.value.email ||
    !form.value.lastName
  ) {
    showToast("資料未填寫完整", "error");
    return;
  } else if (
    form.value.password !== form.value.confirmPassword
  ) {
    showToast("密碼驗證錯誤", "error");
    return;
  }

  const isValidEmail = (email) => {
    return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email);
  };
  if (!isValidEmail(form.value.email)) {
    showToast("信箱格式不正確", "error");
    return;
  }

  const requestBody = {
    username: form.value.username,
    email: form.value.email,
    phone: form.value.phone,
    password: form.value.password,
    firstName: form.value.firstName,
    lastName: form.value.lastName,
    birthDate: "",
    address: form.value.address,
  };

  try {
    await axios
      .post('http://localhost:8080/api/user/register/memberRegister', requestBody);
    showToast("註冊成功，請操作登入", "success");
    setTimeout(() => {
      router.push("/profile/login");
    }, 2000);
  } catch (e) {
    showToast("註冊失敗", "error");
    console.log(e);
  }

}

const loginWithGoogle = async () => {
  window.location.href = 'http://localhost:8080/oauth2/authorization/google';
};

</script>

<template>

  <fieldset class="fieldset bg-base-200 border-base-300 rounded-box w-full border p-4 max-w-md">

    <label class="label">使用者名稱</label>
    <input type="text" class="input w-full" placeholder="必須填寫｜輸入使用者名稱"
           v-model="form.username"
           @input="form.username = form.username.replace(/[^a-zA-Z0-9]/g, '')"/>

    <label class="label">密碼</label>
    <input type="password" class="input w-full" placeholder="必須填寫｜pepperoni-extra-cheese"
           v-model="form.password"/>

    <label class="label">確認密碼</label>
    <input type="password" class="input w-full" placeholder="必須填寫｜再次輸入密碼確認"
           v-model="form.confirmPassword"/>

    <label class="label">電話</label>
    <input type="tel" class="input w-full" placeholder="必須填寫｜輸入行動電話號碼"
           v-model="form.phone" @input="form.phone = form.phone.replace(/[^0-9]/g, '')"/>

    <label class="label">信箱</label>
    <input type="email" class="input w-full" placeholder="必須填寫｜pepperoni@mail.com"
           v-model="form.email"/>

    <label class="label">姓氏</label>
    <input type="text" class="input w-full" placeholder="必須填寫｜輸入姓氏"
           v-model="form.lastName"/>

    <label class="label">名字</label>
    <input type="text" class="input w-full" placeholder="輸入名字" v-model="form.firstName"/>

    <label class="label">地址</label>
    <input type="text" class="input w-full" placeholder="輸入住址" v-model="form.address"/>

    <button @click="handleRegister" class="btn btn-neutral mt-4">註冊</button>

    <div class="divider">或是</div>
    <button @click="loginWithGoogle" class="btn btn-soft">使用 Google 登入</button>
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
