import Vue from 'vue'
import App from './App.vue'
import BootstrapVue from 'bootstrap-vue'
import VueRouter from 'vue-router'
import VueCurrencyInput from 'vue-currency-input'

const pluginOptions = {
  globalOptions: { currency: 'PLN' }
}

Vue.config.productionTip = false
Vue.use(VueCurrencyInput, pluginOptions)
Vue.use(VueRouter)
Vue.use(BootstrapVue)

const Product = {
  props: ['id'],
  template: '<Product :id="id" :/>'
}

const Products = {
  props: ['products'],
  template: '<Products/>'
}

const routes = [
  { path: '/product/:id', name: 'product', component: Product },
  { path: '/products', name: 'products', component: Products }
]

const router = new VueRouter({
  routes
})

export const bus = new Vue();

new Vue({
  router,
  render: h => h(App),
}).$mount('#app')