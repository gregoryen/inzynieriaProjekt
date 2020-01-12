import axios from 'axios';
import authHeader from './auth-header';
import config from '../config.js'
const API_URL = config.root;


class UserService {

  async getUserPrivileges() {
    return axios.get(API_URL + '/getUserPrivileges', {headers: authHeader()});
  }
}

export default new UserService();