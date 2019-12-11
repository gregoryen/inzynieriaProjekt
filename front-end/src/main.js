import Vue from 'vue'
import App from './App'
import VueRouter from 'vue-router'
import BootstrapVue from 'bootstrap-vue'
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'

import HelloWorld from './components/HelloWorld'
import ShoppingCart from './components/ShoppingCart'

Vue.use(VueRouter);
Vue.use(BootstrapVue);

Vue.config.productionTip = false

const router = new VueRouter({
  routes: [
    {
      path: '/',
      component: HelloWorld
    },
    {
      path: '/shoppingCart',
      component: ShoppingCart
    }
  ]
})

new Vue({
  router,
  render: h => h(App),
}).$mount('#app')
