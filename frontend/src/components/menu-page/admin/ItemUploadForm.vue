<script setup>
import { ref } from 'vue'
import axios from 'axios'

const form = ref({
  name: '',
  description: '',
  imageBase64: '',
  basePrice: 0,
  sizes: [{ size: '', extraPrice: 0 }],
  doughs: [{ doughType: '', doughExtraPrice: 0 }],
  addons: [
    {
      addonName: '',
      addonSizePrice: [{ size: '', addonExtraPrice: 0 }]
    }
  ]
})

// 讀取圖片為 base64 字串（去除前綴）
const handleImageUpload = (event) => {
  const file = event.target.files[0]
  if (!file) return
  const reader = new FileReader()
  reader.onload = () => {
    form.value.imageBase64 = reader.result.split(',')[1] // 拿掉 data:image/... 部分
  }
  reader.readAsDataURL(file)
}

// 送出表單
const submitForm = async () => {
  try {
    const payload = {
      name: form.value.name,
      description: form.value.description,
      imageBase64: form.value.imageBase64,
      basePrice: form.value.basePrice,
      sizes: form.value.sizes,
      doughs: form.value.doughs,
      addons: form.value.addons
    }

    const res = await axios.post('http://localhost:8080/api/menu/pizza/create', payload)
    alert('上傳成功：' + res.data)
  } catch (err) {
    console.error(err)
    alert('上傳失敗')
  }
}
</script>

<template>
  <form @submit.prevent="submitForm">
    <h2>新增餐點</h2>

    <label>名稱：</label>
    <input v-model="form.name" required />

    <label>描述：</label>
    <textarea v-model="form.description" />

    <label>圖片：</label>
    <input type="file" @change="handleImageUpload" accept="image/*" />

    <label>基本價格：</label>
    <input type="number" v-model="form.basePrice" required />

    <hr />

    <!-- 尺寸 -->
    <h3>尺寸設定</h3>
    <div v-for="(size, index) in form.sizes" :key="index">
      <input v-model="size.size" placeholder="尺寸名稱" required />
      <input type="number" v-model="size.extraPrice" placeholder="加價" />
      <button type="button" @click="form.sizes.splice(index, 1)">刪除</button>
    </div>
    <button type="button" @click="form.sizes.push({ size: '', extraPrice: 0 })">+ 新增尺寸</button>

    <hr />

    <!-- 麵團 -->
    <h3>麵團選項</h3>
    <div v-for="(dough, index) in form.doughs" :key="index">
      <input v-model="dough.doughType" placeholder="麵團種類" required />
      <input type="number" v-model="dough.doughExtraPrice" placeholder="加價" />
      <button type="button" @click="form.doughs.splice(index, 1)">刪除</button>
    </div>
    <button type="button" @click="form.doughs.push({ doughType: '', doughExtraPrice: 0 })">+ 新增麵團</button>

    <hr />

    <!-- 加料 -->
    <h3>加料選項</h3>
    <div v-for="(addon, aIdx) in form.addons" :key="aIdx" class="addon-block">
      <input v-model="addon.addonName" placeholder="加料名稱" required />
      <button type="button" @click="form.addons.splice(aIdx, 1)">刪除加料</button>

      <h4>尺寸對應加價</h4>
      <div v-for="(sp, sIdx) in addon.addonSizePrice" :key="sIdx">
        <input v-model="sp.size" placeholder="尺寸" required />
        <input type="number" v-model="sp.addonExtraPrice" placeholder="加價" />
        <button type="button" @click="addon.addonSizePrice.splice(sIdx, 1)">刪除</button>
      </div>
      <button type="button" @click="addon.addonSizePrice.push({ size: '', addonExtraPrice: 0 })">
        + 新增對應尺寸
      </button>
    </div>
    <button type="button" @click="form.addons.push({ addonName: '', addonSizePrice: [{ size: '', addonExtraPrice: 0 }] })">
      + 新增加料
    </button>

    <hr />
    <button type="submit">送出</button>
  </form>
</template>

<style scoped>
form {
  max-width: 600px;
  padding: 16px;
  border: 1px solid #ccc;
  border-radius: 8px;
}
input,
textarea {
  display: block;
  width: 100%;
  margin-bottom: 12px;
}
hr {
  margin: 24px 0;
}
button {
  margin-right: 8px;
  margin-top: 4px;
}
</style>
