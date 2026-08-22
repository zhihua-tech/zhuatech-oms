/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. · https://www.zhuatech.cn/ */
import { createRouter, createWebHistory } from 'vue-router'
import LoginView from '../views/LoginView.vue'
import MainLayout from '../components/MainLayout.vue'

const routes = [
  { path:'/login', component:LoginView, meta:{ public:true, title:'登录' } },
  { path:'/', component:MainLayout, children:[
    { path:'', component:() => import('../views/HomeView.vue'), meta:{ title:'订单指挥舱' } },
    { path:'workbench', component:() => import('../views/WorkbenchView.vue'), meta:{ title:'运营工作台' } },
    { path:'orders', component:() => import('../views/OrdersView.vue'), meta:{ title:'订单中心' } },
    { path:'fulfillment', component:() => import('../views/FulfillmentView.vue'), meta:{ title:'履约发运' } },
    { path:'after-sales', component:() => import('../views/AfterSalesView.vue'), meta:{ title:'售后中心' } },
    { path:'channels', component:() => import('../views/ChannelsView.vue'), meta:{ title:'渠道管理' } },
    { path:'analytics', component:() => import('../views/AnalyticsView.vue'), meta:{ title:'订单分析' } },
    { path:'rules', component:() => import('../views/RulesView.vue'), meta:{ title:'订单规则' } },
    { path:'profile', component:() => import('../views/ProfileView.vue'), meta:{ title:'个人中心' } }
  ] }
]

const router = createRouter({ history:createWebHistory(), routes, scrollBehavior:() => ({ top:0 }) })
router.beforeEach(to => {
  document.title = `${to.meta.title || '订单管理'}｜知华科技 OMS`
  if (!to.meta.public && !localStorage.getItem('zhuatech_oms_token')) return '/login'
  if (to.path === '/login' && localStorage.getItem('zhuatech_oms_token')) return '/'
})

export default router
