import axios from 'axios';
import config from '../config'
const API_URL = config.root;

class AuthService {
  login(user) {
    return axios
      .post(API_URL + '/logIn', {
        email: user.email,
        password: user.password
      })
      .then(this.handleResponse)
      .then(response => {

        if (response.data.jwtToken) {
          response.data.email = user.email;
          
          localStorage.setItem('user', JSON.stringify(response.data));

        }
        
        return response.data;
      });
  }

  logout() {
    localStorage.removeItem('user');
  }

  register(user) {
    return axios.post(API_URL + 'signUp', {
      firstName: user.firstName,
      email: user.email,
      password: user.password,
      lastName : user.lastName
    });
  }

  handleResponse(response) {
    if (response.status === 401) {
      this.logout();
      location.reload(true);

      const error = response.data && response.data.message;
      return Promise.reject(error);
    }

    return Promise.resolve(response);
  }
}

export default new AuthService();