import Vue from 'vue';
import Vuex from 'vuex';

import { auth } from './auth.module';
import axios from "axios";

Vue.use(Vuex);

export default new Vuex.Store({
  modules: {
    auth
  },
  state: {
    productsHeader: []
  },
  getters: {
    productsHeader(state) {
      return state.productsHeader;
    }
  },
  mutations: {
    productsHeader(state, productsHeader) {
      state.productsHeader = productsHeader;
    }
  },
  actions: {
    productsHeader(context,url) {
      axios
          .get(url)
          .then(response => {
            context.commit('productsHeader', response.data._embedded.products);
          });
    }
  }
});
