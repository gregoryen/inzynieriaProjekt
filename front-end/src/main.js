import Vue from 'vue'
import App from './App.vue'
// import VueRouter from 'vue-router'
// import WarehouseSupplyForm from "./components/WarehouseSupplyForm";
// import HelloWorld from "./components/HelloWorld";

// Vue.use(VueRouter);

Vue.config.productionTip = false

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
  // router,
  render: h => h(App),
}).$mount('#app')
