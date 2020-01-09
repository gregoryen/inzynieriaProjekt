<template>
  <div class="container">
    <header class="jumbotron">
      <h3>
        <strong>Profil uzytkownika {{currentUser.userName}}</strong>
      </h3>
    </header>
    <p>
      <strong>Token:</strong>
      {{currentUser.jwtToken.substring(0, 20)}} ... {{currentUser.jwtToken.substr(currentUser.jwtToken.length - 20)}}
    </p>
    <p>
      <strong>Id:</strong>
      {{currentUser.id}}
    </p>
    <p>
      <strong>Email:</strong>
      {{currentUser.email}}
    </p>
    <strong>Uprawnienia:</strong>
    {{currentUser.privileges}}
  </div>
</template>

<script>
export default {
  name: 'profile',
  computed: {
    currentUser() {

      return this.$store.state.auth.user;
    }
  },
  mounted() {
    if (!this.currentUser) {
      this.$router.push('/login');
    }
  }
};
</script>