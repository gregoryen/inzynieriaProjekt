/**
 * nie zawiera username
 */

export default class User {
    constructor(password, email, firstname, lastname) {
		this.password = password;
		this.email = email;
		this.firstname = firstname;
		this.lastname = lastname;
		this.id=0;
    }
  }