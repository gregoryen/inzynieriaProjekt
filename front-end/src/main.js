import Vue from 'vue'
import App from './App.vue'

import BootstrapVue from 'bootstrap-vue'
import VueRouter from 'vue-router'
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'
import Home from './components/Home'
import AdminPanel from './components/AdminPanel'



Vue.config.productionTip = false
Vue.use(BootstrapVue)
Vue.use(VueRouter)

const router = new VueRouter({
  routes: [
    {
      path: '/',
      component: Home
    },
    {
      path: '/adminPanel',
      component: AdminPanel
    }
  ]
})

// const router = new VueRouter({
//   routes: [
//     {
//       path: '/',
//       component: HelloWorld
//     },
//     {
//       path: '/supply_form',
//       component: WarehouseSupplyForm
//     }
//   ]
// })

new Vue({

  router,

  render: h => h(App),
}).$mount('#app')
