import { createRouter, createWebHistory } from 'vue-router';

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
      path: '/adminView',
      name: 'adminView',
      component: () => import('@/views/adminView.vue'),
      redirect: '/adminView/home', // 添加重定向
      children: [
        {
          path: 'home',
          name: 'home',
          component: () => import('@/components/HomeComponent.vue')
        },
        {
          path: 'userList',
          name: 'userList',
          component: () => import('@/components/UserListComponent.vue')
        }
      ]
    }
  ]
});

export default router;
