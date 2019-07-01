import Vue from 'vue'
import Router from 'vue-router'
import HelloWorld from '@/components/HelloWorld'
import NoticeList from '@/components/NoticeList'
import Login from '@/components/Login'
import { store } from '../store'

Vue.use(Router)

const requireAuth = () => (from, to, next) => {
  let isAuthenticated = store.getters.isAuthenticated
  if (!isAuthenticated) {
    isAuthenticated = sessionStorage.getItem('accessToken') || false
  }
  console.log('isAuthenticated', isAuthenticated)
  if (isAuthenticated) return next()
  next('/login')
}

export default new Router({
  mnode: 'history',
  routes: [
    {
      path: '/hello',
      name: 'HelloWorld',
      component: HelloWorld
    },
    {
      path: '/',
      name: 'NoticeList',
      component: NoticeList,
      beforeEnter: requireAuth()
    },
    {
      path: '/login',
      name: 'Login',
      component: Login
    }
  ]
})
