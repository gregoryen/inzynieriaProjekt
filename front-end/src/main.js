import Vue from 'vue'
import App from './App.vue'
import VueRouter from 'vue-router'
import BootstrapVue from 'bootstrap-vue'
import VueCurrencyInput from 'vue-currency-input'

import Categories from './components/Categories.vue'
import Products from './components/Products.vue'
import Product from './components/Product.vue'

Vue.use(VueRouter)
Vue.use(BootstrapVue)
Vue.use(VueCurrencyInput, pluginOptions)

Vue.config.productionTip = false;

const routes = [
  { path: '/product/:id', name: 'product', component: Product },
  { path: '/products', name: 'products', component: Products },
  { path: '/categories', name: 'categories', compoment: Categories }
];

const pluginOptions = { globalOptions: { currency: 'PLN' } };

const router = new VueRouter({ routes });

export const bus = new Vue();

new Vue({
  router,
  render: h => h(App),
}).$mount('#app')