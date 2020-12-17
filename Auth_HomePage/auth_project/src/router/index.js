import Vue from 'vue'
import Router from 'vue-router'
import Login from '@/components/Login'
import SignUp from '@/components/SignUp'
import User from '@/components/User'
import FindPW from '@/components/FindPW'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Login',
      component: Login
    },
    {
      path: '/signup',
      name: 'SignUp',
      component: SignUp
    },
    {
      path: '/user',
      name: 'User',
      component: User
    },
    {
      path: '/findPW',
      name: 'FindPW',
      component: FindPW
    }
  ]
})
