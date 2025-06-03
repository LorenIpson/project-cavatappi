import { ref } from 'vue';

const resultMessage = ref('');
const resultType = ref(''); // 'success' | 'error' | 'info'

const showToast = (message, type = 'info', duration = 2000) => {
  resultMessage.value = message;
  resultType.value = type;

  setTimeout(() => {
    resultMessage.value = '';
    resultType.value = '';
  }, duration);
};

export function useToast() {
  return {
    resultMessage,
    resultType,
    showToast,
  };
}
