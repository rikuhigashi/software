import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router'
import LoginNav from '../views/LoginView.vue'

const routes: Array<RouteRecordRaw> = [

  {
    path: '/',
    name: 'LoginNav',
    component: LoginNav
  },
  {
    path: '/index',
    name: 'index',
    component: () => import('../views/index.vue'),
    children: [
      {
        path: '/HomeView',
        name: 'HomeView',
        meta: {
          title: '首页'
        },
        component: () => import('../views/HomeView.vue')
      },
    ]
  },


]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
