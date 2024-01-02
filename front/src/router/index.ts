import { createRouter, createWebHashHistory, RouteRecordRaw } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import ProductView from "@/views/ProductView.vue";

const routes: Array<RouteRecordRaw> = [
  {
    path: '/',
    name: 'home',
    component: HomeView
  },
  {
    path: '/product/:productId',
    component: ProductView
  }
]

const router = createRouter({
  history: createWebHashHistory(),
  routes
})

export default router
