<script setup>

import router from "@/router/index.js";
import {useMemberStore} from "@/stores/memberStore.js";
import {useRoute} from "vue-router";
import {computed} from "vue";

const memberStore = useMemberStore();
const route = useRoute();

const goToHome = () => {
  router.push("/");
};

const goToMenu = () => {
  router.push("/menu");
};

const goToCart = () => {
  router.push("/cart");
};

const goToLogin = () => {
  router.push("/profile/login");
};

const goToProfile = () => {
  router.push("/profile");
};

const goTo = () => {
  !memberStore.isLoggedIn ? goToLogin() : goToProfile();
};

const isActive = (paths) => {
  return paths.some(path => route.path.startsWith(path));
};

const accountLinkText = computed(() => {
  return memberStore.isLoggedIn ? '個人資訊' : '登入';
});

console.log("登入狀態檢查：" + memberStore.isLoggedIn);

</script>

<template>

  <div class="dock dock-xl max-w-screen-md mx-auto rounded-t-4xl tracking-widest" style="box-shadow: 0 -4px 50px rgba(0,0,0,0.1);">
    <button @click="goToHome" :class="{'dock-active': route.path === '/'}">
      <svg class="size-[1.2em]" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24"><g fill="currentColor" stroke-linejoin="miter" stroke-linecap="butt"><polyline points="1 11 12 2 23 11" fill="none" stroke="currentColor" stroke-miterlimit="10" stroke-width="2"></polyline><path d="m5,13v7c0,1.105.895,2,2,2h10c1.105,0,2-.895,2-2v-7" fill="none" stroke="currentColor" stroke-linecap="square" stroke-miterlimit="10" stroke-width="2"></path><line x1="12" y1="22" x2="12" y2="18" fill="none" stroke="currentColor" stroke-linecap="square" stroke-miterlimit="10" stroke-width="2"></line></g></svg>
      <span class="dock-label">首頁</span>
    </button>

    <button @click="goToMenu" :class="{'dock-active': isActive(['/menu'])}">
      <svg class="size-[1.2em]" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg" color="#000000"><path d="M14 9.01L14.01 8.99889" stroke="#000000" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"></path><path d="M8 8.01L8.01 7.99889" stroke="#000000" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"></path><path d="M8 14.01L8.01 13.9989" stroke="#000000" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"></path><path d="M6 19L2.23626 3.0041C2.13087 2.55618 2.54815 2.16122 2.98961 2.29106L19 7" stroke="#000000" stroke-width="1.7"></path><path d="M22.198 8.42467C22.4324 7.48703 21.8623 6.5369 20.9247 6.30249C19.987 6.06808 19.0369 6.63816 18.8025 7.5758C18.4106 9.14318 16.9015 11.6241 14.5753 13.9503C12.2743 16.2513 9.42714 18.1442 6.60672 18.7951C5.66497 19.0124 5.07771 19.952 5.29504 20.8937C5.51236 21.8355 6.45198 22.4227 7.39373 22.2054C11.0734 21.3563 14.4762 18.9991 17.0502 16.4252C19.5989 13.8764 21.5898 10.8573 22.198 8.42467Z" stroke="#000000" stroke-width="1.7" stroke-linecap="round"></path></svg>
      <span class="dock-label">菜單</span>
    </button>

    <button @click="goToCart" :class="{'dock-active': isActive(['/cart'])}">
      <svg class="size-[1.2em]" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg" color="#000000"><path d="M19.5 22C20.3284 22 21 21.3284 21 20.5C21 19.6716 20.3284 19 19.5 19C18.6716 19 18 19.6716 18 20.5C18 21.3284 18.6716 22 19.5 22Z" fill="#000000" stroke="#000000" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"></path><path d="M9.5 22C10.3284 22 11 21.3284 11 20.5C11 19.6716 10.3284 19 9.5 19C8.67157 19 8 19.6716 8 20.5C8 21.3284 8.67157 22 9.5 22Z" fill="#000000" stroke="#000000" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"></path><path d="M16.5 4H22L20 15H15.5M16.5 4L15.5 15M16.5 4H10.75M15.5 15H11.5M10.75 4H5L7 15H11.5M10.75 4L11.5 15" stroke="#000000" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"></path><path d="M5 4C4.83333 3.33333 4 2 2 2" stroke="#000000" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"></path><path d="M20 15H7H5.23077C3.44646 15 2.5 15.7812 2.5 17C2.5 18.2188 3.44646 19 5.23077 19H19.5" stroke="#000000" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"></path></svg>
      <span class="dock-label">購物車</span>
    </button>

    <button @click="goTo" :class="{'dock-active': isActive(['/profile'])}">
      <svg class="size-[1.2em]" stroke-width="1.8" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg" color="#000000"><path d="M12 2C6.47715 2 2 6.47715 2 12C2 17.5228 6.47715 22 12 22C17.5228 22 22 17.5228 22 12C22 6.47715 17.5228 2 12 2Z" stroke="#000000" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round"></path><path d="M4.271 18.3457C4.271 18.3457 6.50002 15.5 12 15.5C17.5 15.5 19.7291 18.3457 19.7291 18.3457" stroke="#000000" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round"></path><path d="M12 12C13.6569 12 15 10.6569 15 9C15 7.34315 13.6569 6 12 6C10.3431 6 9 7.34315 9 9C9 10.6569 10.3431 12 12 12Z" stroke="#000000" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round"></path></svg>
      <span class="dock-label">{{ accountLinkText }}</span>
    </button>
  </div>

</template>
