/**
 * Przeszukuje bazę w poszukiwaniu obiektu user;
 * jeżeli user jest zalogowany (jwt), zwraca autoryzację
 * w przeciwnym wypadku zwraca null
 */

export default function authHeader() {
    let user = JSON.parse(localStorage.getItem('user'));

  
    if (user && user.jwtToken) {
      return { Authorization: 'Bearer ' + user.jwtToken };
    } else {
      return {};
    }
    //return null;
  }