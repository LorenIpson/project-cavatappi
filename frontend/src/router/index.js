import {createRouter, createWebHistory} from 'vue-router'
import HomeView from '../views/home/HomeView.vue'
import LoginView from "@/views/user/LoginView.vue";
import MenuView from "@/views/menu/MenuView.vue";
import CartView from "@/views/cart/CartView.vue";
import ProfileView from "@/views/user/ProfileView.vue";
import RegistrationView from "@/views/user/RegistrationView.vue";
import OAuthCallbackView from "@/views/user/OAuthCallbackView.vue";
import ProfileEditView from "@/views/user/ProfileEditView.vue";
import MenuEditView from "@/views/menu/admin/MenuEditView.vue";

const routes = [
  {path: '/', name: 'Home', component: HomeView},
  {path: '/menu', name: 'Menu', component: MenuView},
  {path: '/cart', name: 'Cart', component: CartView},
  {path: '/login', name: 'Login', component: LoginView},
  {path: '/register', name: 'Register', component: RegistrationView},
  {path: '/oauth/callback', name: 'OAuth-Callback', component: OAuthCallbackView},
  {path: '/profile', name: 'Profile', component: ProfileView},
  {path: '/profile/edit', name: 'Profile-Edit', component: ProfileEditView},

  // ========= admin role required =========
  {path: '/menu/admin/edit', name: 'Admin-Menu-Edit', component: MenuEditView}

]
const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
