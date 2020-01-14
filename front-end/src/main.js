import Vue from 'vue'
import App from './App'
import VueRouter from 'vue-router'
import BootstrapVue from 'bootstrap-vue'
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'
import VueCurrencyInput from 'vue-currency-input'

import ShoppingCart from './components/ShoppingCart'
import OrderSummary from './components/OrderSummary'
import AddProduct from "./components/AddProduct";
import Home from './components/Home'
import AdminPanel from './components/AdminPanel'

const pluginOptions = {
  globalOptions: { currency: 'PLN' }
}

Vue.use(VueRouter);
Vue.use(BootstrapVue);
Vue.use(VueCurrencyInput, pluginOptions)


import BootstrapVue from 'bootstrap-vue'
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'

Vue.use(BootstrapVue)
Vue.config.productionTip = false;



const router = new VueRouter({
  routes: [
    {
      path: '/',
      component: Home
    },
    {
      path: '/shoppingCart',
      component: ShoppingCart
    },
    {
      path: '/orderSummary',
      component: OrderSummary
    },
    {
      path: '/addProduct',
      component: AddProduct
    },
    {
      path: '/adminPanel',
      component: AdminPanel
    }
  ]
})

new Vue({
  router,
  render: h => h(App),
}).$mount('#app')
