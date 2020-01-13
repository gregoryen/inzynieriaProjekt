<template>
  <div class="col-md-12">
    <div class="card card-container">
      <img
        id="profile-img"
        src="//ssl.gstatic.com/accounts/ui/avatar_2x.png"
        class="profile-img-card"
      />
       <ValidationObserver v-slot="{handleSubmit}">
      <form name="form" @submit.prevent="handleSubmit(handleRegister)">
        <div v-if="!successful">
          <div class="form-group">
            <label for="firstName">FirstName</label>
            <validation-provider rules="required|min:3|max:20" v-slot="{ errors }">
              <input v-model="user.firstName" name="firstName" type="text" class="form-control"/>
              <span>{{ errors[0] }}</span>
            </validation-provider>
          <div class="form-group">
            <label for="lastName">lastName</label>
            <validation-provider rules="required|min:3|max:20" v-slot="{ errors }">
              <input v-model="user.lastName" name="lastName" type="text" class="form-control"/>
              <span>{{ errors[0] }}</span>
            </validation-provider>
          </div>
          <div class="form-group">
            <label for="email">Email</label>
            <validation-provider rules="required|min:3|max:50|email" v-slot="{ errors }">
              <input v-model="user.email" name="email" type="text" class="form-control"/>
              <span>{{ errors[0] }}</span>
            </validation-provider>
          </div>
          <div class="form-group">
            <label for="password">Password</label>
           <validation-provider rules="required|min:3|max:20" v-slot="{ errors }">
              <input v-model="user.password" name="password" type="password" class="form-control"/>
              <span>{{ errors[0] }}</span>
            </validation-provider>
          </div>
          <div class="form-group">
            <button type="submit" class="btn btn-primary btn-block">Sign Up</button>
          </div>
        </div>
        </div>
      </form>
      </ValidationObserver>
      </div>
      <div
        class="alert"
        :class="successful ? 'alert-success' : 'alert-danger'"
        v-if="message"
      >{{message}}</div>
  </div>
</template>

<script>
import User from '../models/user';

export default {
  name: 'register',
  computed: {
    loggedIn() {
      return this.$store.state.auth.status.loggedIn;
    }
  },
  data() {
    return {
      user: new User('', '', '',''),
      submitted: false,
      successful: false,
      message: ''
    };
  },
  mounted() {
    if (this.loggedIn) {
      this.$router.push('/profile');
    }
  },
  methods: {
    handleRegister() {
      this.message = '';
      this.submitted = true;
      
    
           this.$store.dispatch('auth/register', this.user).then(
             data => {
               this.message = data.message;
               this.successful = true;
               alert("Sucesfully");
               this.$router.push("/login");
             },
             error => {
               this.message = error.message;
               this.successful = false;
               alert("Email existing");
             }
           );
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
