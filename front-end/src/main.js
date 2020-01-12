import Vue from 'vue'
import App from './App.vue'
import BootstrapVue from 'bootstrap-vue'
import VueRouter from 'vue-router'
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'
import Home from './components/Home.vue'
import Login from './components/Login.vue'
import Register from './components/Register.vue'
import AdminPanel from './components/AdminPanel.vue'
import Profile from './components/Profile.vue'


import { ValidationProvider,ValidationObserver } from 'vee-validate';
import { required, min, max, email} from 'vee-validate/dist/rules';
import UserServices from './services/user.service'
import store from './store';
import { library } from '@fortawesome/fontawesome-svg-core';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import {
  faHome,
  faUser,
  faUserPlus,
  faSignInAlt,
  faSignOutAlt
} from '@fortawesome/free-solid-svg-icons';
library.add(faHome, faUser, faUserPlus, faSignInAlt, faSignOutAlt);


import { extend } from 'vee-validate';


extend('required', {
  ...required,
  message: 'This field is required'
});
extend('email', {
  ...email,
  message: 'This is not a email'
});

extend('min', {
  ...min,
  message: 'Field too short'
});
extend('max', {
  ...max,
  message: 'Field too long'
});

Vue.config.productionTip = false
Vue.component('ValidationProvider', ValidationProvider);
Vue.component('ValidationObserver', ValidationObserver);
Vue.use(BootstrapVue)
Vue.use(VueRouter)
Vue.component('font-awesome-icon', FontAwesomeIcon)


const ifNotAuthenticated = (to, from, next) => {
  if (!store.state.auth.status.loggedIn) {
    next()
    return
  }
  next('/')
}
const ifAuthenticated = (to, from, next) => {
  if (store.state.auth.status.loggedIn) {
    next()
    return
  }
  next('/login')
}
const ifHavePrivilege =(to,from,next)=>
{
   if (store.state.auth.status.loggedIn) {
        UserServices.getUserPrivileges().then(
          response =>{
         
         
     
          let ifHas = false;
          for(let i =0;i<response.data.length;i++)
          {
  
            if(response.data[i].authority == to.meta.requiredPrivilege)
              {
  
                    ifHas =true;
                    break;
              }
  }


if(ifHas){
  next()
}
else
{
next('/')
}}
)
  }
  else
  next('/')

}

const router = new VueRouter({
  mode: 'history',
  routes: [
    {
      path: '/',
      component: Home,
      
    },
    {
      path: '/login',
      component: Login,
      beforeEnter: ifNotAuthenticated
    },
    {
      path: '/register',
      component: Register,
      
    },
    {
      path:'/profile',
      component: Profile,
      
    },
    {
      path: '/adminPanel',
      name: 'adminPanel',
      // lazy-loaded
      component: AdminPanel,
      beforeEnter:ifHavePrivilege,
      meta:{
      requiredPrivilege: 'ADMIN_PRIVILEGE' 
      }
    },

  ]
})
router.beforeEach((to, from, next) => {
  if (!ifAuthenticated) next('/login')
  else next()
})
new Vue({
  router,
  store,
    data: () => ({
    value: '',
    email:''
  }),

  render: h => h(App),
}).$mount('#app')
