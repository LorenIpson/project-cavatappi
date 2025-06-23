<script setup>

import {ref} from "vue";
import {useToast} from "@/composables/useToast.js";
import ToastAlert from "@/components/essential/ToastAlert.vue";
import axiosApi from "@/composables/useAxios.js";

const {showToast} = useToast();

const form = ref({
  name: '',
  description: '',
  imageBase64: '',
  basePrice: 0,
  sizes: [{size: '', extraPrice: 0}],
  doughs: [{doughType: '', doughExtraPrice: 0}],
  addons: [
    {
      addonName: '',
      addonSizePrice: [{size: '', addonExtraPrice: 0}]
    }
  ]
});

const MAX_IMAGE_SIZE = 2 * 1024 * 1024;

const handleImageUpload = (event) => {
  const file = event.target.files[0];
  if (!file) return;

  const allowedTypes = ['image/jpeg', 'image/png'];
  if (!allowedTypes.includes(file.type)) {
    showToast("只允許上傳 JPG 或 PNG 圖片", "error");
    event.target.value = '';
    return;
  }

  if (file.size > MAX_IMAGE_SIZE) {
    showToast("圖片大小不能超過 2MB", "error");
    event.target.value = '';
    return;
  }

  const reader = new FileReader();
  reader.onload = () => {
    form.value.imageBase64 = reader.result;
  };
  reader.onerror = (e) => {
    console.error("圖片讀取失敗", e);
    showToast("圖片讀取失敗", "error");
  };
  reader.readAsDataURL(file);
  console.log(form.value.imageBase64);
};

const removeImage = () => {
  form.value.imageBase64 = "";
  const input = document.querySelector('input[type="file"]');
  if (input) input.value = '';
};

// 新增尺寸
const addNewSize = () => {
  form.value.sizes.push({size: '', extraPrice: 0});
};
const removeNewSize = (index) => {
  form.value.sizes.splice(index, 1);
};

// 新增餅皮
const addNewDough = () => {
  form.value.doughs.push({doughType: '', doughExtraPrice: 0});
};
const removeNewDough = (index) => {
  form.value.doughs.splice(index, 1);
};

// 新增配料
const addNewAddon = () => {
  form.value.addons.push({
    addonName: '',
    addonSizePrice: [{size: '', addonExtraPrice: 0}]
  });
};
const removeAddon = (addonIndex) => {
  form.value.addons.splice(addonIndex, 1);
};

// 新增配料尺寸價格
const addNewAddonSizePrice = (addonIndex) => {
  form.value.addons[addonIndex].addonSizePrice.push({size: '', addonExtraPrice: 0});
};
const removeAddonSizePrice = (addonIndex, priceIndex) => {
  form.value.addons[addonIndex].addonSizePrice.splice(priceIndex, 1);
};

const submitForm = async () => {
  try {
    await axiosApi.post('/api/menu/pizza/create', form.value);
    showToast('餐點新增成功', 'success');
  } catch (e) {
    console.error(e);
    showToast('餐點新增失敗', 'error');
  }
};

</script>

<template>
  <div class="flex justify-center">
    <fieldset
      class="fieldset bg-base-100 shadow-xl rounded-box w-full border border-base-200 p-4 max-w-md mb-5">

      <!-- 基本資訊 -->
      <label class="label">餐點名稱</label>
      <input v-model="form.name" class="input w-full mb-1" placeholder="必須填寫｜輸入餐點名稱"/>

      <label class="label">餐點介紹</label>
      <input v-model="form.description" class="input w-full mb-1"
             placeholder="必須填寫｜輸入餐點介紹"/>

      <label class="label">上傳圖片．限制 2MB．JPG / PNG</label>
      <input
        type="file"
        accept=".jpg,.jpeg,.png"
        class="file-input file-input-bordered w-full mb-1"
        @change="handleImageUpload"
      />

      <div v-if="form.imageBase64" class="mb-1">
        <img :src="form.imageBase64" alt="預覽圖片" class="w-full rounded-2xl mb-2"/>
        <button class="btn btn-ghost w-full" @click="removeImage">移除圖片</button>
      </div>

      <label class="label">基礎價格</label>
      <input v-model.number="form.basePrice" class="input w-full" type="number"
             placeholder="輸入基礎價格"/>

      <!-- 尺寸 -->
      <div class="divider mb-2">尺寸</div>
      <div v-for="(size, index) in form.sizes" :key="index" class="flex gap-2 mb-2">
        <input v-model="size.size" class="input" placeholder="尺寸名稱"/>
        <input v-model.number="size.extraPrice" class="input" type="number" placeholder="加價"/>
        <button class="btn btn-ghost" @click="removeNewSize(index)">移除</button>
      </div>
      <button class="btn btn-soft w-full mb-4" @click="addNewSize">再新增一筆尺寸</button>

      <!-- 餅皮 -->
      <div class="divider mb-2">餅皮</div>
      <div v-for="(dough, index) in form.doughs" :key="index" class="flex gap-2 mb-2">
        <input v-model="dough.doughType" class="input" placeholder="餅皮名稱"/>
        <input v-model.number="dough.doughExtraPrice" class="input" type="number"
               placeholder="加價"/>
        <button class="btn btn-ghost" @click="removeNewDough(index)">移除</button>
      </div>
      <button class="btn btn-soft w-full mb-4" @click="addNewDough">再新增一筆餅皮</button>

      <!-- 額外配料 -->
      <div class="divider mb-2">額外配料</div>

      <div v-for="(addon, addonIndex) in form.addons" :key="addonIndex"
           class="mb-4 rounded-2xl p-3 bg-base-200 shadow-lg border border-base-200">
        <input v-model="addon.addonName" class="input w-full mb-2 mt-1" placeholder="加料名稱"/>

        <div v-for="(price, priceIndex) in addon.addonSizePrice" :key="priceIndex"
             class="flex gap-2 mb-2">
          <input v-model="price.size" class="input" placeholder="對應尺寸"/>
          <input v-model.number="price.addonExtraPrice" class="input" type="number"
                 placeholder="加價"/>
          <button class="btn btn-ghost" @click="removeAddonSizePrice(addonIndex, priceIndex)">移除
          </button>
        </div>

        <button class="btn btn-soft w-full mb-2" @click="addNewAddonSizePrice(addonIndex)">
          再新增一筆配料的對應尺寸
        </button>
        <button class="btn btn-ghost w-full" @click="removeAddon(addonIndex)">移除配料</button>
      </div>
      <button class="btn btn-soft w-full" @click="addNewAddon">再新增一筆額外配料</button>

      <div class="divider mb-4"></div>

      <button class="btn btn-neutral w-full mb-2" @click="submitForm">送出表單</button>

    </fieldset>
  </div>

  <ToastAlert/>

</template>


