import Vue from 'vue'
import Router from 'vue-router'
import Login from '@/components/Login'
import SignUp from '@/components/SignUp'
import User from '@/components/User'
import FindPW from '@/components/FindPW'
import VueRouter from 'vue-router'

Vue.use(Router)

const routes = [
  {
    path: '/login',
    component: Login
  },
  {
    path: '/signup',
    component: SignUp
  },
  {
    path: '/user',
    component: User
  },
  {
    path: '/findPW',
    component: FindPW
  }
]

export default new Router({
  routes: [
    {
      path: '/',
      component: Login
    },
    {
      path: '/signup',
      component: SignUp
    },
    {
      path: '/user',
      component: User
    },
    {
      path: '/findPW',
      component: FindPW
    }
  ]
})
