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

import Categories from './components/Categories.vue'
import Products from './components/Products.vue'
import Product from './components/Product.vue'
import AddCategory from "./components/AddCategory";
import DeleteCategory from "./components/DeleteCategory";
import DeleteProduct from "./components/DeleteProduct";
import {store} from './store';

const pluginOptions = {
  globalOptions: { currency: 'PLN' }
}

Vue.use(VueRouter);
Vue.use(BootstrapVue);
Vue.use(VueCurrencyInput, pluginOptions)

Vue.config.productionTip = false;


export const bus = new Vue();


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
    },
    { path: '/product', name: 'product', component: Product },
    { path: '/products', name: 'products', component: Products },
    { path: '/categories', name: 'categories', component: Categories },
    { path: '/addCategory', name: 'addCategory', component: AddCategory},
    { path: '/addProduct', name: 'addProduct', component: AddProduct },
    { path: '/deleteCategory', name: 'deleteCategory', component: DeleteCategory },
    { path: '/deleteProduct', name: 'deleteProduct', component: DeleteProduct }
  ]
})

new Vue({
  router,
  render: h => h(App),
  store,
}).$mount('#app')
