// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import axios from 'axios'
import { initPlugins, initI18N } from './init-plugins'
import App from './App'
import router from './router'

import Vuelidate from 'vuelidate'
// 引入echarts
import echarts from 'echarts'

Vue.use(Vuelidate)
Vue.prototype.$echarts = echarts

import MintUI from 'mint-ui'
Vue.use(MintUI)
import { Loadmore } from 'mint-ui';
import { Picker } from 'mint-ui';
Vue.component(Loadmore.name, Loadmore);
Vue.component(Picker.name, Picker);

import { Calendar } from 'vant';
import 'vant/lib/calendar/index.css';
Vue.use(Calendar);

//init the third-party plugins
//初始化第三方的组件
initPlugins();
//get i18n object
const i18n = initI18N('zh');

Vue.prototype.$axios = axios;
axios.defaults.withCredentials = true;    //让axios携带cookie
axios.defaults.headers.post['Content-Type'] = 'application/json';

axios.defaults.baseURL = window.url.SERVICE_CONTEXT_PATH;
const url = window.url.SERVICE_CONTEXT_PATH;

/* 路由发生变化修改页面title */
router.beforeEach((to, from, next) => {
  if (to.meta.title) {
    document.title = to.meta.title
  }
  next()
})

axios.interceptors.request.use(
  config => {
    let token = localStorage.getItem("userToken");
    if (token) {  // 判断是否存在token，如果存在的话，则每个http header都加上token
      config.headers.Authorization = `${token}`;
    }
    if (config.url.indexOf(url) === -1 && config.url.indexOf("http") === -1) {
      config.url = url + config.url;/*拼接完整请求路径*/
    }else{
      config.url = config.url;
    }
    return config;
  },
  err => {
    return Promise.reject(err);
  }
);

new Vue({
  el: '#app',
  router,
  i18n,
  template: '<App/>',
  components: { App }
})

/*Vue.config.productionTip = false;*/
/*document.addEventListener('deviceready', function() {
  new Vue({
    el: '#app',
    router,
    store,
    template: '<App/>',
    components: {App}
  })
  window.navigator.splashscreen.hide()
}, false);*/
