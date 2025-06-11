<script setup>

import {useMemberStore} from "@/stores/memberStore.js";
import {useToast} from "@/composables/useToast.js";
import {useRouter} from "vue-router";

const router = useRouter();
const memberStore = useMemberStore();

const {resultMessage, resultType, showToast} = useToast();

const handleLogout = () => {
  memberStore.logout();
  showToast("登出成功", "success");
  setTimeout(() => {
    router.push("/profile/login");
  }, 1000);
}

</script>

<template>
  <ul class="menu bg-base-200 rounded-box w-full">
    <li>
      <a class="text-base">
        <svg
          xmlns="http://www.w3.org/2000/svg"
          class="h-5 w-5"
          fill="none"
          viewBox="0 0 24 24"
          stroke-width="1.5"
          color="#000000">
          <path d="M8 14L16 14" stroke="#000000" stroke-width="1.5" stroke-linecap="round"
                stroke-linejoin="round"></path>
          <path d="M8 10L10 10" stroke="#000000" stroke-width="1.5" stroke-linecap="round"
                stroke-linejoin="round"></path>
          <path d="M8 18L12 18" stroke="#000000" stroke-width="1.5" stroke-linecap="round"
                stroke-linejoin="round"></path>
          <path
            d="M10 3H6C4.89543 3 4 3.89543 4 5V20C4 21.1046 4.89543 22 6 22H18C19.1046 22 20 21.1046 20 20V5C20 3.89543 19.1046 3 18 3H14.5M10 3V1M10 3V5"
            stroke="#000000" stroke-width="1.5" stroke-linecap="round"
            stroke-linejoin="round"></path>
        </svg>
        我的訂單
      </a>
    </li>
    <li>
      <a class="text-base">
        <svg class="h-5 w-5" stroke-width="1.5" viewBox="0 0 24 24" fill="none"
             xmlns="http://www.w3.org/2000/svg" color="#000000">
          <path
            d="M18.1336 11C18.7155 16.3755 21 18 21 18H3C3 18 6 15.8667 6 8.4C6 6.70261 6.63214 5.07475 7.75736 3.87452C8.88258 2.67428 10.4087 2 12 2C12.3373 2 12.6717 2.0303 13 2.08949"
            stroke="#000000" stroke-width="1.5" stroke-linecap="round"
            stroke-linejoin="round"></path>
          <path
            d="M19 8C20.6569 8 22 6.65685 22 5C22 3.34315 20.6569 2 19 2C17.3431 2 16 3.34315 16 5C16 6.65685 17.3431 8 19 8Z"
            stroke="#000000" stroke-width="1.5" stroke-linecap="round"
            stroke-linejoin="round"></path>
          <path
            d="M13.73 21C13.5542 21.3031 13.3019 21.5547 12.9982 21.7295C12.6946 21.9044 12.3504 21.9965 12 21.9965C11.6496 21.9965 11.3054 21.9044 11.0018 21.7295C10.6982 21.5547 10.4458 21.3031 10.27 21"
            stroke="#000000" stroke-width="1.5" stroke-linecap="round"
            stroke-linejoin="round"></path>
        </svg>
        通知
      </a>
    </li>
    <li>
      <a class="text-base">
        <svg class="h-5 w-5" viewBox="0 0 24 24" stroke-width="1.5" fill="none"
             xmlns="http://www.w3.org/2000/svg" color="#000000">
          <path
            d="M14.3632 5.65156L15.8431 4.17157C16.6242 3.39052 17.8905 3.39052 18.6716 4.17157L20.0858 5.58579C20.8668 6.36683 20.8668 7.63316 20.0858 8.41421L18.6058 9.8942M14.3632 5.65156L4.74749 15.2672C4.41542 15.5993 4.21079 16.0376 4.16947 16.5054L3.92738 19.2459C3.87261 19.8659 4.39148 20.3848 5.0115 20.33L7.75191 20.0879C8.21972 20.0466 8.65806 19.8419 8.99013 19.5099L18.6058 9.8942M14.3632 5.65156L18.6058 9.8942"
            stroke="#000000" stroke-width="1.5" stroke-linecap="round"
            stroke-linejoin="round"></path>
        </svg>
        編輯個人資訊
      </a>
    </li>
    <li @click="handleLogout">
      <a class="text-base">
        <svg class="h-5 w-5" stroke-width="1.5" viewBox="0 0 24 24" fill="none"
             xmlns="http://www.w3.org/2000/svg" color="#000000">
          <path d="M12 12H19M19 12L16 15M19 12L16 9" stroke="#000000" stroke-width="1.5"
                stroke-linecap="round" stroke-linejoin="round"></path>
          <path
            d="M19 6V5C19 3.89543 18.1046 3 17 3H7C5.89543 3 5 3.89543 5 5V19C5 20.1046 5.89543 21 7 21H17C18.1046 21 19 20.1046 19 19V18"
            stroke="#000000" stroke-width="1.5" stroke-linecap="round"
            stroke-linejoin="round"></path>
        </svg>
        登出
      </a>
    </li>
  </ul>

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
