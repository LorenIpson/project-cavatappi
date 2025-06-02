import {createRouter, createWebHistory} from 'vue-router'
import HomeView from '../views/HomeView.vue'
import LoginView from "@/views/LoginView.vue";
import MenuView from "@/views/MenuView.vue";
import CartView from "@/views/CartView.vue";
import ProfileView from "@/views/ProfileView.vue";
import RegistrationView from "@/views/RegistrationView.vue";
import OAuthCallbackView from "@/views/OAuthCallbackView.vue";
import ProfileEditView from "@/views/ProfileEditView.vue";
import LinePayConfirmView from "@/views/linepay/LinePayConfirmView.vue";
import LinePaySuccessView from "@/views/linepay/LinePaySuccessView.vue";
import LinePayCancelView from "@/views/linepay/LinePayCancelView.vue";
import CheckoutView from "@/views/checkout/CheckoutView.vue";

const routes = [
  {path: '/', name: 'Home', component: HomeView},
  {path: '/menu', name: 'Menu', component: MenuView},
  {path: '/cart', name: 'Cart', component: CartView},
  {path: '/login', name: 'Login', component: LoginView},
  {path: '/register', name: 'Register', component: RegistrationView},
  {path: '/oauth/callback', name: 'OAuth-Callback', component: OAuthCallbackView},
  {path: '/profile', name: 'Profile', component: ProfileView},
  {path: '/profile/edit', name: 'Profile-Edit', component: ProfileEditView},

  // LINE PAY
  {path: '/cart/payment/line-pay/confirm', name: 'Line-pay-Confirm', component: LinePayConfirmView},
  {path: '/cart/payment/line-pay/success', name: 'Line-pay-Success', component: LinePaySuccessView},
  {path: '/cart/payment/line-pay/cancel', name: 'Line-pay-Cancel', component: LinePayCancelView},

  // TEMP
  {path: '/temp/checkout', name: 'TEMP-Checkout', component: CheckoutView},

]
const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
