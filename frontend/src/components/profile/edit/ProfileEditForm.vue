<script setup>

import {useToast} from "@/composables/useToast.js";
import {onMounted, ref} from "vue";
import axiosApi from "@/composables/useAxios.js";
import router from "@/router/index.js";

const {showToast} = useToast();

const form = ref({
  firstName: "",
  lastName: "",
  birthDate: "",
  address: "",
  phone: ""
});

const saveProfile = async () => {
  try {
    await axiosApi.put("/api/user/profile/edit", form.value);
    showToast('個人資料更新成功','success');
    await router.push("/profile");
  } catch (e) {
    showToast('更新使用者資訊時出錯了', 'error');
    console.log(e);
  }
};

onMounted(async () => {
  try {
    const response = await axiosApi.get('/api/user/profile/me');
    form.value = {
      firstName: response.data.firstName || '',
      lastName: response.data.lastName || '',
      address: response.data.address || '',
      phone: response.data.phone || ''
    };
  } catch (e) {
    showToast('取得個人資訊時出錯了', 'error')
    console.error(e);
  }
});

</script>

<template>

  <fieldset class="fieldset bg-base-200 border-base-300 rounded-box w-full border p-4 max-w-md">

    <label class="label">電話</label>
    <input type="tel" class="input w-full" placeholder="必須填寫｜輸入行動電話號碼"
           v-model="form.phone" @input="form.phone = form.phone.replace(/[^0-9]/g, '')"/>

    <label class="label">姓氏</label>
    <input type="text" class="input w-full" placeholder="必須填寫｜輸入姓氏"
           v-model="form.lastName"/>

    <label class="label">名字</label>
    <input type="text" class="input w-full" placeholder="輸入名字" v-model="form.firstName"/>

    <label class="label">地址</label>
    <input type="text" class="input w-full" placeholder="輸入住址" v-model="form.address"/>

    <button @click="saveProfile" class="btn btn-neutral mt-4">更新資料</button>

  </fieldset>

</template>
