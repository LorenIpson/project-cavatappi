import {createRouter, createWebHistory} from 'vue-router'
import HomeView from '../views/HomeView.vue'
import LoginView from "@/views/LoginView.vue";
import MenuView from "@/views/MenuView.vue";
import CartView from "@/views/CartView.vue";
import ProfileView from "@/views/ProfileView.vue";

const routes = [
  {path: '/', name: 'Home', component: HomeView},
  {path: '/menu', name: 'Menu', component: MenuView},
  {path: '/cart', name: 'Cart', component: CartView},
  {path: '/login', name: 'Login', component: LoginView},
  {path: '/profile', name: 'Profile', component: ProfileView}
]
const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
