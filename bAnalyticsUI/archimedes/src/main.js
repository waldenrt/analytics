// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import Vuetify from 'vuetify'
import VeeValidate from 'vee-validate'
import store from './store'

Vue.config.productionTip = false
Vue.use(Vuetify)
Vue.use(VeeValidate, {
  errorBagName: 'vErrors'
})

/* eslint-disable no-new */
new Vue({
  el: '#app',
  store,
  router,
  render: h => h(App)
})
