<script setup>

import { useToast } from '@/composables/useToast.js';

const { resultMessage, resultType } = useToast();

</script>

<template>

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
