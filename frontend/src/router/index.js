import {createRouter, createWebHistory} from 'vue-router'
import HomeView from "@/views/HomeView.vue";
import MenuView from "@/views/menu/MenuView.vue";
import CartView from "@/views/cart/CartView.vue";
import CheckoutView from "@/views/cart/checkout/CheckoutView.vue";
import PaymentSuccessView from "@/views/cart/payment/PaymentSuccessView.vue";
import PaymentFailedView from "@/views/cart/payment/PaymentFailedView.vue";
import LinePayConfirmView from "@/views/cart/payment/linepay/LinePayConfirmView.vue";
import LinePayCancelView from "@/views/cart/payment/linepay/LinePayCancelView.vue";
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

  // PAYMENT
  {path: '/cart/payment/success', name: 'Payment-Success', component: PaymentSuccessView},
  {path: '/cart/payment/error', name: 'Payment-Error', component: PaymentFailedView},

  // PAYMENT - LINE PAY
  {path: '/cart/payment/line-pay/confirm', name: 'Line-Pay-Confirm', component: LinePayConfirmView},
  {path: '/cart/payment/line-pay/confirm', name: 'Line-Pay-Cancel', component: LinePayCancelView},

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
