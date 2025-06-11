<script setup>

import OrderDetailForm from "@/components/cart/checkout/OrderDetailForm.vue";

const totalPrice = history.state?.totalPrice;

</script>

<template>

    <OrderDetailForm
        :totalPrice="totalPrice"
    />

</template>
