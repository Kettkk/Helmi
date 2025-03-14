import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'loginView',
      component: () => import('@/views/LoginView.vue')
    },
    {
      path: '/signup',
      name: 'signupView',
      component: () => import('@/views/SignUpView.vue')
    },
    {
      path:'/adminView',
      name:'adminView',
      component:()=>import('@/views/adminView.vue')
    }
  ]
})

export default router
