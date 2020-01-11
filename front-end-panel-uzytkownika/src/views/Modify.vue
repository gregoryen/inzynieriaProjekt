<template>
  <div class="col-md-12">
    <div class="card card-container">
      <form name="form" @submit.prevent="handleModify">
        <div v-if="!successful">
          <div class="form-group">
            <label for="email">Email</label>
            <input
              type="email"
              class="form-control"
              name="email"
              v-model="currentUser.email"
              v-validate="'required|email|max:50'"
            />
            <div
              class="alert-danger"
              v-if="submitted && errors.has('email')"
            >{{errors.first('email')}}</div>
          </div>
          <div class="form-group">
            <label for="firstname">Imie</label>
            <input
              type="text"
              class="form-control"
              name="firstname"
              v-model="currentUser.firstName"
              v-validate="'required|min:3|max:20'"
            />
            <div
              class="alert-danger"
              v-if="submitted && errors.has('firstname')"
            >{{errors.first('firstname')}}</div>
          </div>
          <div class="form-group">
            <label for="lastname">Nazwisko</label>
            <input
              type="text"
              class="form-control"
              name="lastname"
              v-model="currentUser.lastName"
              v-validate="'required|min:3|max:20'"
            />
            <div
              class="alert-danger"
              v-if="submitted && errors.has('lastname')"
            >{{errors.first('lastname')}}</div>
          </div>
          <div class="form-group">
            <button class="btn btn-primary btn-block">Edytuj</button>
          </div>
        </div>
      </form>
	
      <div
        class="alert"
        :class="successful ? 'alert-success' : 'alert-danger'"
        v-if="message"
      >{{message}}
      </div>
      <div class="form-group" v-if="message">
        <button class="btn btn-primary btn-block" @click="rtn">Zobacz profil</button>
      </div>
	
    </div>
  </div>
</template>

<script>
import User from '../models/user';

export default {
  name: 'register',
  computed: {
    loggedIn() {
      return this.$store.state.auth.status.loggedIn;
    },
    currentUser() {
      return this.$store.state.auth.user;
    }
  },
  data() {
    return {
      user: new User('', '', ''),
      submitted: false,
      successful: false,
      message: ''
    };
  },
  mounted() {
    if (this.loggedIn) {
      //this.$router.push('/profile');
    }
  },
  methods: {
    handleModify() {
		this.user.email = this.currentUser.email;
		this.user.firstname = this.currentUser.firstName;
		this.user.lastname = this.currentUser.lastName;
		this.user.id = this.currentUser.id;
    this.message = '';
    this.submitted = true;
    this.$validator.validate().then(valid => {
      if (valid) {
        this.$store.dispatch('auth/modify', this.user).then(
          data => {
            this.message = data.message;
            this.message = "Edycja przebiegla pomyslnie";
            this.successful = true;
          },
          error => {
            this.message = error.message;
            this.successful = false;
          }
        );
      }
    });
   },
    rtn() {
      this.$router.push('/profile');
    }
  }
};
</script>

<style scoped>
label {
  display: block;
  margin-top: 10px;
}

.card-container.card {
  max-width: 350px !important;
  padding: 40px 40px;
}

.card {
  background-color: #f7f7f7;
  padding: 20px 25px 30px;
  margin: 0 auto 25px;
  margin-top: 50px;
  -moz-border-radius: 2px;
  -webkit-border-radius: 2px;
  border-radius: 2px;
  -moz-box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);
  -webkit-box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);
  box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);
}

.profile-img-card {
  width: 96px;
  height: 96px;
  margin: 0 auto 10px;
  display: block;
  -moz-border-radius: 50%;
  -webkit-border-radius: 50%;
  border-radius: 50%;
}
</style>