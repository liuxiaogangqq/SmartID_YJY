import Vue from 'vue'
import Router from 'vue-router'

// 直接加载
import Index from '../pages/index.vue'

Vue.use(Router);

export default new Router({
  routes: [
    {
      path: '/',
      name: 'first',
      component: resolve => require(['../pages/login.vue'], resolve)
    },
    {
      path: '/login',
      name: 'login',
      component: resolve => require(['../pages/login.vue'], resolve),
      meta: {
        title: '停车场系统'
      }
    },
    {
      path: '/index', name: 'index', component: Index,
      meta: {
        title: '停车场系统'
      }
    },
    {
      path: '/staff',
      name: 'staff',
      component: resolve => require(['../pages/staff.vue'], resolve),
      meta: {
        title: '停车场系统'
      }
    },
    {
      path: '/record',
      name: 'record',
      component: resolve => require(['../pages/record.vue'], resolve),
      meta: {
        title: '历史记录'
      }
    },
    {
      path: '/staffRecord',
      name: 'staffRecord',
      component: resolve => require(['../pages/staffRecord.vue'], resolve),
      meta: {
        title: '历史记录'
      }
    },
    {
      path: '/appointmentCompany',
      name: 'appointmentCompany',
      component: resolve => require(['../pages/appointmentCompany.vue'], resolve),
      meta: {
        title: '车位预约'
      }
    },
    {
      path: '/appointment',
      name: 'appointment',
      component: resolve => require(['../pages/appointment.vue'], resolve),
      meta: {
        title: '车位预约'
      }
    },
    {
      path: '/detailsCompany',
      name: 'detailsCompany',
      component: resolve => require(['../pages/detailsCompany.vue'], resolve)
    },
    {
      path: '/details',
      name: 'details',
      component: resolve => require(['../pages/details.vue'], resolve),
      meta: {
        title: '预约详情'
      }
    },
    {
      path: '/helpgs',
      name: 'helpgs',
      component: resolve => require(['../pages/helpGs.vue'], resolve),
      meta: {
        title: '帮助'
      }
    },
    {
      path: '/helpgr',
      name: 'helpgr',
      component: resolve => require(['../pages/helpGr.vue'], resolve),
      meta: {
        title: '帮助'
      }
    }
  ]
})
