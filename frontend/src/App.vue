<script setup>
import {RouterLink, RouterView} from 'vue-router'
import HeaderNav from "@/components/HeaderNav.vue";
import BottomNav from "@/components/BottomNav.vue";
</script>

<template>
  <div class="app-container">
    <HeaderNav></HeaderNav>
    <main class="main-content">
      <RouterView/>
    </main>
    <BottomNav class="bottom-nav-fixed"></BottomNav>
  </div>
</template>

<style scoped>
html, body {
  height: 100%;
  margin: 0;
  padding: 0;
}

.app-container {
  display: flex;
  flex-direction: column;
  height: 100%;
}

.main-content {
  flex: 1;
  overflow-y: auto;
  padding: 16px;
}

.bottom-nav-fixed {
  position: fixed;
  bottom: 3%;
  left: 7%;
  right: 7%;
  height: 60px;
  border-radius: 99px;
  background-color: #79ad6f;
  box-shadow: 0 -0 20px #94dd98;
  z-index: 10;
}

</style>
