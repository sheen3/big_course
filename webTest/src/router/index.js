import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/login',
      name: 'login',
      component:() => import('../views/Login.vue')
    },
    {
      path: '/register',
      name: 'register',
      component:() => import('../views/Register.vue')
    },
    {
      path: '/guanliyuan',
      name: 'guanliyuan',
      component:() => import('../views/guanliyuan.vue')
    },
    {
      path: '/shengchanshang',
      name: 'shengchanshang',
      component:() => import('../views/shengchanshang.vue')
    },
    {
      path: '/wuliu',
      name: 'wuliu',
      component:() => import('../views/wuliu.vue')
    },
    {
      path: '/zhijian',
      name: 'zhijian',
      component:() => import('../views/zhijian.vue')
    },
    {
      path: '/',
      component:() => import('../views/index.vue')
    },
    {
      path: '',
      component:() => import('../views/index.vue')
    },
  ]
})

export default router
