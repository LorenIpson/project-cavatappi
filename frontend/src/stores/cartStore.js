import { defineStore } from 'pinia'

export const useCartStore = defineStore('cart', {

  state: () => ({
    items: JSON.parse(localStorage.getItem('cart') || '[]')
  }),

  actions: {

    addItem(item) {
      this.items.push(item)
      localStorage.setItem('cart', JSON.stringify(this.items))
    },

    clearCart() {
      this.items = [];
      localStorage.removeItem('cart');
    },

    removeItem(seqId) {
      this.items = this.items.filter(i => i.itemSeqId !== seqId);
      localStorage.setItem('cart', JSON.stringify(this.items));
    }

  }
});
