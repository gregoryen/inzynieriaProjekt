import AdminService from '../services/admin.service';

export const admin = {
    namespaced: true,
    state: {
        mainTable: [],
        changeMainTable: "empty"
    },
    actions: {
        async getUsers({ commit }) {
            return AdminService.getUsers().then(
                users => {
                    commit('usersSuccess', users);
                    return Promise.resolve(users);
                },
                error => {
                    /* eslint-disable no-console */
                    console.log(error);
                    return Promise.reject(error.response.data);
                }
            );
        },
        async getRoles({ commit }) {
            return AdminService.getRoles().then(
                roles => {
                    commit('rolesSuccess', roles);
                    return Promise.resolve(roles);
                },
                error => {
                    /* eslint-disable no-console */
                    console.log(error);
                    return Promise.reject(error.response.data);
                }
            );
        },
        async getPrivileges({ commit }) {
            return AdminService.getPrivileges().then(
                privileges => {
                    commit('privilegesSuccess', privileges);
                    return Promise.resolve(privileges);
                },
                error => {
                    /* eslint-disable no-console */
                    console.log(error);
                    return Promise.reject(error.response.data);
                }
            );
        },

    },
    mutations: {
        usersSuccess(state, users) {
            state.mainTable = users;
            state.changeMainTable = "users";
        },
        rolesSuccess(state, roles) {
            state.mainTable = roles;
            state.changeMainTable = "roles";
        },
        privilegesSuccess(state, privileges) {
            state.mainTable = privileges;
            state.changeMainTable = "privileges";
        },
    }
};