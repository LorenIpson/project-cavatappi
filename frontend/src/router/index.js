import {createRouter, createWebHistory} from 'vue-router'
import HomeView from "@/views/HomeView.vue";
import MenuView from "@/views/menu/MenuView.vue";
import CartView from "@/views/cart/CartView.vue";
import CheckoutView from "@/views/cart/checkout/CheckoutView.vue";
import ProfileView from "@/views/profile/ProfileView.vue";
import LoginView from "@/views/profile/login/LoginView.vue";
import RegisterView from "@/views/profile/register/RegisterView.vue";
import OAuthCallbackView from "@/views/profile/OAuthCallbackView.vue";

const routes = [

  // HOME
  {path: '/', name: 'Home', component: HomeView},

  // MENU
  {path: '/menu', name: 'Menu', component: MenuView},

  // CART
  {path: '/cart', name: 'Cart', component: CartView},
  {path: '/cart/checkout', name: 'Checkout', component: CheckoutView},


  // PROFILE
  {path: '/profile', name: 'Profile', component: ProfileView},
  {path: '/profile/login', name: 'Login', component: LoginView},
  {path: '/profile/register', name: 'Registration', component: RegisterView},
  {path: '/oauth/callback', name: 'OAuth-Callback', component: OAuthCallbackView}

]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
