/**
 * Daje trzy metody potrzebne do żądań i odpowiedzi http
 * (razem z axios)
 *      login() - metoda POST (login i hasło) i save jwt do bazy
 *      logout() - remove jwt z bazy
 *      register() - metoda POST (login, email i hasło)
 * 
 * dodatkowo jest handleResponse, które w przypadku wystąpienia 401
 * (nieautoryzowany; jwt nieważny) wylogowuje użytkownika
 * (czyli usuwa jwt z bazy)
 */    

import axios from 'axios';


const API_URL = 'http://localhost:8080/';

class AuthService {

  login(user) {
    return axios.post(API_URL + 'logIn', {
		password: user.password,
        email: user.email
      })
      .then(this.handleResponse)
      .then(response => {
        if (response.data.jwtToken) {
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
		password: user.password,
		email: user.email,
		firstName: user.firstname,
		lastName: user.lastname,
    });
  }
  
  modify(user) {
    return axios.put(API_URL + 'modify', {
		password: user.password,
		email: user.email,
		firstName: user.firstname,
		lastName: user.lastname,
		id: user.id,
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