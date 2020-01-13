import axios from 'axios';
import authHeader from './auth-header';
import config from '../config.js'
const API_URL = config.root;


class AdminService {

    async getUsers() {
        return axios.get(API_URL + '/users', { headers: authHeader() }).then(response => {
            /* eslint-disable no-console */
            console.log(response.data._embedded.users);
            return response.data._embedded.users;
        })
    }

    async getRoles() {
        return axios.get(API_URL + '/roles', { headers: authHeader() }).then(response => {
            /* eslint-disable no-console */
            console.log(response.data._embedded.roles);
            return response.data._embedded.roles;
        })
    }

    async getPrivileges() {
        return axios.get(API_URL + '/privileges', { headers: authHeader() }).then(response => {
            /* eslint-disable no-console */
            console.log(response.data._embedded.privileges);
            return response.data._embedded.privileges;
        })
    }
}

export default new AdminService();