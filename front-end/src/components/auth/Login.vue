<template>
  <div class="col-md-12">
    <div class="card card-container">
      <ValidationObserver v-slot="{handleSubmit}">
      <form name="form" @submit.prevent="handleSubmit(handleLogin)">
        <div class="form-group">
          <label for="email">Email</label>
          <validation-provider rules="required" v-slot="{ errors }">
            <input v-model="user.email" name="email" type="text" class="form-control"/>
            <span>{{ errors[0] }}</span>
            </validation-provider>
        </div>
        <div class="form-group">
          <label for="password">Password</label>
          <validation-provider rules="required" v-slot="{ errors }">
            <input v-model="user.password" name="password" type="text" class="form-control"/>
            <span>{{ errors[0] }}</span>
            </validation-provider>
    
        </div>
        <div class="form-group">
          <button type ="submit" class="btn btn-primary btn-block" :disabled="loading">
            <span class="spinner-border spinner-border-sm" v-show="loading"></span>
            <span>Login</span>
          </button>
        </div>
        <div class="form-group">
          <div class="alert alert-danger" role="alert" v-if="message">{{message}}</div>
        </div>
      </form>
      </ValidationObserver>
    </div>
  </div>
</template>

<script>
import User from '../../models/user';


export default {
  name: 'login',
  computed: {
    loggedIn() {
      return this.$store.state.auth.status.loggedIn;
    }
  },
  data() {
    return {
      user: new User('','','',''),
      loading: false,
      message: '',
      email:''
    };
  },
  mounted() {
    if (this.loggedIn) {
      this.$router.push('/profile');
    }
  },
  methods: {
    handleLogin() {
      this.loading = true;
      

      if (this.user.email && this.user.password) {
        this.$store.dispatch('auth/login', this.user).then(
          () => {
            this.$router.push('/profile');
          },
          error => {
            this.loading = false;
            this.message = error.message;
          }
        );
      }
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