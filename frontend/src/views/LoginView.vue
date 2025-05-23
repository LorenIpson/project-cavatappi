<script setup>

import LoginForm from "@/components/login-page/LoginForm.vue";
// import axios from "axios";

// const loginWithGitHub = async () => {
//   try {
//     const response = await axios.get('http://localhost:8080/api/user/oauth/authorization/github');
//     window.location.href = response.data.redirectUri; // Response JSON 的 K，可以忽略
//   } catch (e) {
//     console.error('無法取得 GitHub 登入網址', e);
//   }
// };

// const loginWitLine = async () => {
//   window.location.href = 'http://localhost:8080/oauth2/authorization/line';
// };

const loginWithGoogle = async () => {
  window.location.href = 'http://localhost:8080/oauth2/authorization/google';
};

</script>

<template>
  <span>這裡是登入畫面耶</span>

  <div class="login-form">
    <LoginForm></LoginForm>
  </div>

  <div class="oauth-buttons">
    <button @click="loginWithGoogle">Google 登入</button>
    <!--    <button @click="loginWitLine">Line 登入</button>-->
    <!--    <button @click="loginWithGitHub">GitHub 登入</button>-->
    <button>註冊新帳號</button>
  </div>
</template>

<style scoped>

span {
  align-items: center;
  text-align: center;
  font-size: 24px;
}

.login-form {
  margin-top: 20px;
}

.oauth-buttons {
  margin-top: 20px;
}

.oauth-buttons button {
  font-size: 12px;
  margin-left: 10px;
}

</style>
