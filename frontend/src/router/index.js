import {createRouter, createWebHistory} from 'vue-router'
import HomeView from "@/views/HomeView.vue";
import LoginView from "@/views/profile/LoginView.vue";
import ProfileView from "@/views/profile/ProfileView.vue";
import RegisterView from "@/views/profile/RegisterView.vue";
import OAuthCallbackView from "@/views/profile/OAuthCallbackView.vue";

const routes = [

  // HOME
  {path: '/', name: 'Home', component: HomeView},

  // MENU

  // CART

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
