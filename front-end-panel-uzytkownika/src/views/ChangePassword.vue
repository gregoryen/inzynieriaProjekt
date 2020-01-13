<template>
  <div class="col-md-12">
    <div class="card card-container">
      <form name="form">
        <div class="form-group">
          <label for="password">Podaj haslo</label>
          <input
            type="password"
            class="form-control"
            name="password"
            v-model="password"
            v-validate="'required'"
          />
          <div
            class="alert alert-danger"
            role="alert"
            v-if="errors.has('password')"
          >Has³o jest wymagane!</div>
        </div>
		
		<div class="form-group">
          <label for="password">Potwierdz haslo</label>
          <input
            type="password"
            class="form-control"
            name="password"
            v-model="confirm"
            v-validate="'required'"
          />
          <div
            class="alert alert-danger"
            role="alert"
            v-if="errors.has('password')"
          >Has³o jest wymagane!</div>
        </div>
		
		
        
        
        <div class="form-group">
          <div class="alert alert-success" role="alert" v-if="message">{{message}}</div>
        </div>
      </form>
		<div class="form-group">
          <button class="btn btn-success btn-block" @click="changePass">
            <span>Zmien haslo</span>
          </button>
			
        </div>
    </div>
  </div>
</template>

<script>
export default {
	data() {
    return {
      password: '',
		confirm: '',
		token: '',
      loading: false,
      message: '',
		flet: this.$route.query.page
    };
  },
  methods: {
  changePass() {
  /*eslint-disable no-console*/
  
  console.log(this.$route.query.id);
  if(this.password==this.confirm) {
  console.log("hasla sa takie same");
  const obj = {
	passwd: this.password,
	id: this.$route.query.id
  }
  console.log(obj);
  this.$store.dispatch('auth/changePass', obj);
  this.message = "Haslo zostalo zmienione"
  //this.$router.push('/changePassword')
  }
  
  
  
  }
  
  }
}

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