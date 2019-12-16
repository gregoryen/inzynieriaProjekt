import Vue from 'vue'
import App from './App'
import VueRouter from 'vue-router'
import BootstrapVue from 'bootstrap-vue'
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'
import VueCurrencyInput from 'vue-currency-input'

import HelloWorld from './components/HelloWorld'
import ShoppingCart from './components/ShoppingCart'
import OrderSummary from './components/OrderSummary'
import AddProduct from "./components/AddProduct";

const pluginOptions = {
  globalOptions: { currency: 'PLN' }
}

Vue.use(VueRouter);
Vue.use(BootstrapVue);
Vue.use(VueCurrencyInput, pluginOptions)

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
    },
    {
      path: '/orderSummary',
      component: OrderSummary
    },
    {
      path: '/addProduct',
      component: AddProduct
    }
  ]
})

new Vue({
  router,
  render: h => h(App),
}).$mount('#app')
