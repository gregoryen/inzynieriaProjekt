import Vue from 'vue'
import App from './App.vue'
import BootstrapVue from 'bootstrap-vue'
import VueRouter from 'vue-router'

Vue.config.productionTip = false
Vue.use(VueRouter)
Vue.use(BootstrapVue)

const Product = {
  props: ['id'],
  template: '<Product :id="id" :/>'
}

const Products = {
  props: ['products'],
  template: '<Products :id="id" :/>'
}

const routes = [
  { path: '/product/:id', component: Product },
  { path: '/products/:products', component: Products }
]

const router = new VueRouter({
  routes
})

export const bus = new Vue();

new Vue({
  router,
  render: h => h(App),
}).$mount('#app')