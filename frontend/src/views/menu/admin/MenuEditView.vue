<script setup>

import MenuEditList from "@/components/menu-page/admin/MenuEditList.vue";
import ItemUploadForm from "@/components/menu-page/admin/ItemUploadForm.vue";
import {ref} from "vue";

const currentTab = ref('edit')

</script>

<template>

  <h1>這裡是編輯餐點的頁面耶</h1>

  <div>

    <div>
      <button
        :class="currentTab === 'edit' ? 'active-tab' : ''"
        @click="currentTab = 'edit'"
      >
        編輯現有餐點
      </button>
      <button
        :class="currentTab === 'create' ? 'active-tab' : ''"
        @click="currentTab = 'create'"
      >
        新增新餐點
      </button>
    </div>

    <div>
      <MenuEditList v-if="currentTab === 'edit'"/>
      <ItemUploadForm v-if="currentTab === 'create'"/>
    </div>

  </div>

</template>

<style scoped>

button {
  margin-right: 8px;
  padding: 8px 16px;
  background-color: transparent;
  border: 1px solid gray;
  cursor: pointer;
}

.active-tab {
  background-color: #ccc;
  font-weight: bold;
}

</style>
