<template>
  <div class="container">
    <header class="jumbotron">
      <h3>
        <strong>{{currentUser.name + currentUser.lastName}}</strong> Profile
      </h3>
    </header>
    <p>
      <strong>Token:</strong>
      {{currentUser.jwtToken.substring(0, 20)}} ... {{currentUser.jwtToken.substr(currentUser.jwtToken.length - 20)}}
    </p>
    <p>
      <strong>Email:</strong>
      {{currentUser.email}}
    </p>
    <strong>Authorities:</strong>
    <u>
      <li v-for="(privilege,index) in currentUser.privileges.split(',') " :key="index">{{privilege}}</li>
    </u>
    <OpinionsInUserPanel/>
  </div>
</template>

<script>
  import OpinionsInUserPanel from "./OpinionsInUserPanel";
export default {
  components: {
    OpinionsInUserPanel
  },
  name: 'profile',
  computed: {
    currentUser() {
     
      return  this.$store.state.auth.user;
    }
  },
  mounted() {
    if (!this.currentUser) {
      this.$router.push('/login');
    }
  }
};
</script>
