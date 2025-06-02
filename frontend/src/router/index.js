import {createRouter, createWebHistory} from 'vue-router'
import HomeView from '../views/HomeView.vue'
import LoginView from "@/views/LoginView.vue";
import MenuView from "@/views/MenuView.vue";
import CartView from "@/views/CartView.vue";
import ProfileView from "@/views/ProfileView.vue";
import RegistrationView from "@/views/RegistrationView.vue";
import OAuthCallbackView from "@/views/OAuthCallbackView.vue";
import ProfileEditView from "@/views/ProfileEditView.vue";
import PaymentResultView from "@/views/PaymentResultView.vue";

const routes = [
  {path: '/', name: 'Home', component: HomeView},
  {path: '/menu', name: 'Menu', component: MenuView},
  {path: '/cart', name: 'Cart', component: CartView},
  {path: '/login', name: 'Login', component: LoginView},
  {path: '/register', name: 'Register', component: RegistrationView},
  {path: '/oauth/callback', name: 'OAuth-Callback', component: OAuthCallbackView},
  {path: '/profile', name: 'Profile', component: ProfileView},
  {path: '/profile/edit', name: 'Profile-Edit', component: ProfileEditView},
  {path: '/cart/payment/confirm', name: 'Payment-Confirm', component: PaymentResultView},
  {path: '/cart/payment/cancel', name: 'Payment-Cancel', component: PaymentResultView},
  {path: '/cart/payment/error', name: 'Payment-Error', component: PaymentResultView}

]
const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
