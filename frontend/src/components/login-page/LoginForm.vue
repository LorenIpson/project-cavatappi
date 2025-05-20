<script setup>

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
