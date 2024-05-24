import Vue from 'vue'

// import 'normalize.css/normalize.css' // A modern alternative to CSS resets
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'

import '@/assets/css/global.css'
// import '@/assets/css/theme/index.css'
import locale from 'element-ui/lib/locale/lang/zh-CN' // lang i18n

import '@/styles/index.scss' // global css

import App from './App'
import store from './store'
import router from './router'

import '@/icons' // icon
import '@/permission' // permission control
// main.js 或者专门用来注册全局组件的文件
import Header from "@/components/front/Header";

Vue.component('Header', Header);
import aliOssPlugin from '@/utils/aliOss.js'; // 阿里云oss上传插件
/**
 * If you don't want to use mock-server
 * you want to use MockJs for mock api
 * you can execute: mockXHR()
 *
 * Currently MockJs will be used in the production environment,
 * please remove it before going online ! ! !
 */
if (process.env.NODE_ENV === 'production') {
  const { mockXHR } = require('../mock')
  mockXHR()
}

// set ElementUI lang to EN
Vue.use(ElementUI, { locale })
// 如果想要中文版 element-ui，按如下方式声明
// Vue.use(ElementUI)

Vue.use(aliOssPlugin);  // 注册阿里云oss上传插件

Vue.config.productionTip = false
// Vue.prototype.$baseUrl = process.env.VUE_APP_BASEURL
Vue.prototype.$baseUrl = process.env.VUE_APP_BASE_API


new Vue({
  el: '#app',
  router,
  store,
  render: h => h(App),
  beforeCreate() {
		Vue.prototype.$bus = this //安装全局事件总线
	},
})
