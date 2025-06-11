<!--suppress ALL -->
<script setup>

import {onMounted, ref} from "vue";
import {useRouter} from "vue-router";
import axios from "axios";
import {useMemberStore} from "@/stores/memberStore.js";
import {useToast} from "@/composables/useToast.js";

const memberStore = useMemberStore();
const router = useRouter();
const userProfile = ref('');
const {resultMessage, resultType, showToast} = useToast();

onMounted(async () => {
  try {
    const response = await axios.get("http://localhost:8080/api/user/profile/me", {
      headers: {
        Authorization: `Bearer ${memberStore.token}`
      }
    });
    userProfile.value = response.data;
  } catch (e) {
    console.error(e);
    showToast("取得登入者資訊時發生問題，請重新登入", "error");
    router.push("/profile/login");
  }
});

</script>

<template>

  <ul class="grid grid-cols-12 bg-base-200 rounded-box w-full mb-5 p-4 gap-y-2 gap-x-2">

    <li class="col-span-4 ml-1">使用者名稱</li>
    <li
      class="col-span-8 text-right text-gray-600 truncate max-w-full tooltip"
      :data-tip="userProfile.username">
      {{ userProfile.username }}
    </li>

    <li class="col-span-4 ml-1">信箱</li>
    <li
      class="col-span-8 text-right text-gray-600 truncate max-w-full tooltip"
      :data-tip="userProfile.email">
      {{ userProfile.email }}
    </li>

    <li class="col-span-4 ml-1">電話</li>
    <li
      class="col-span-8 text-right text-gray-600 truncate max-w-full tooltip"
      :data-tip="userProfile.phone">
      {{ userProfile.phone }}
    </li>

    <li class="col-span-4 ml-1">地址</li>
    <li
      class="col-span-8 text-right text-gray-600 truncate max-w-full tooltip"
      :data-tip="userProfile.address">
      {{ userProfile.address }}
    </li>

  </ul>

</template>
