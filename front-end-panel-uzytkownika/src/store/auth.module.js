/**
 * wykorzystuje auth.service do tworzenia żądań autoryzacji
 */

import AuthService from '../services/auth.service';

const user = JSON.parse(localStorage.getItem('user'));

const initialState = user
  ? { status: { loggedIn: true }, user }
  : { status: {}, user: null };

export const auth = {
  namespaced: true,
  state: initialState,
  actions: {
    login({ commit }, user) {
      return AuthService.login(user).then(
        user => {
          commit('loginSuccess', user);
          return Promise.resolve(user);
        },
        error => {
          commit('loginFailure');
          return Promise.reject(error.response.data);
        }
      );
    },
    logout({ commit }) {
      AuthService.logout();
      commit('logout');
    },
    register({ commit }, user) {
      return AuthService.register(user).then(
        response => {
          commit('registerSuccess');
          return Promise.resolve(response.data);
        },
        error => {
          commit('registerFailure');
          return Promise.reject(error.response.data);
        }
      );
    },
    modify({ commit }, user) {
      return AuthService.modify(user).then(
        response => {
          const moded = {
            ...JSON.parse(localStorage.getItem('user')),
            lastName: user.lastname,
            firstName: user.firstname,
            email: user.email
          };
          localStorage.setItem("user", JSON.stringify(moded));
          //commit('modifySuccess');
          return Promise.resolve(response.data);
        },
        error => {
          commit('modifyFailure');
          return Promise.reject(error.response.data);
        }
      );
    },
	resetPass({ commit }, mail) {
		return AuthService.resetPass(mail).then(
		response => {
          commit('registerSuccess');
          return Promise.resolve(response.data);
        });
	},
	changePass({ commit }, obj) {
		return AuthService.changePass(obj).then(
		response => {
          commit('registerSuccess');
          return Promise.resolve(response.data);
        });
	}
  },
  mutations: {
    loginSuccess(state, user) {
      state.status = { loggedIn: true };
      state.user = user;
    },
    loginFailure(state) {
      state.status = {};
      state.user = null;
    },
    logout(state) {
      state.status = {};
      state.user = null;
    },
    registerSuccess(state) {
      state.status = {};
    },
    registerFailure(state) {
      state.status = {};
    }
  }
};