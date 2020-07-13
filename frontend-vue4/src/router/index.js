import { createRouter, createWebHistory } from 'vue-router'
import Home from '../views/Home.vue'
import Contact from '../views/Contact.vue'

const routerHistory = createWebHistory()

const router = createRouter({
  history: routerHistory,
  routes: [
    {
      path: '/',
      component: Home,
      meta: { requiresAuth: true , roles:['ROLE_ADMIN', 'ROLE_USERS']}
    },
    {
      path: '/contact',
      component: Contact
    }
  ]
})

export default router