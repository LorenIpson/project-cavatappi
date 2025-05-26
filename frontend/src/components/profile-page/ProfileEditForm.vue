<script setup>

import {onMounted, ref} from 'vue'
import axios from 'axios'
import {useMemberStore} from '@/stores/memberStore.js'
import router from '@/router/index.js'

const memberStore = useMemberStore()

const form = ref({
  firstName: "",
  lastName: "",
  birthDate: "",
  address: "",
  phone: ""
});

const loading = ref(true);
const error = ref(null);
const successMessage = ref(null);

onMounted(async () => {

  try {
    const response = await axios.get('http://localhost:8080/api/user/profile/me', {
      headers: {
        Authorization: `Bearer ${memberStore.token}`
      }
    });
    form.value = {...response.data};
  } catch (e) {
    error.value = '無法取得使用者資料';
    console.error(e)
  } finally {
    loading.value = false;
  }

})

const saveProfile = async () => {

  try {
    await axios.put('http://localhost:8080/api/user/profile/edit', form.value, {
      headers: {
        Authorization: `Bearer ${memberStore.token}`
      }
    });
    successMessage.value = '儲存成功！';
    router.push('/profile');
  } catch (e) {
    error.value = '儲存失敗';
    console.error(e);
  }

}

</script>

<template>

  <h3>這是編輯表單</h3>
  <div v-if="loading">載入中...</div>

  <form v-else @submit.prevent="saveProfile">
    <div v-if="error" class="error">{{ error }}</div>
    <div v-if="successMessage" class="success">{{ successMessage }}</div>

    <label>名字</label>
    <input v-model="form.firstName" type="text"/>

    <label>姓氏</label>
    <input v-model="form.lastName" type="text"/>

    <label>電話</label>
    <input v-model="form.phone" type="text"/>

    <label>生日</label>
    <input v-model="form.birthDate" type="date"/>

    <label>地址</label>
    <input v-model="form.address" type="text"/>

    <button type="submit">儲 存</button>
    <button type="button" @click="router.push('/profile')">取 消</button>
  </form>

</template>

<style scoped>

</style>
