<template>
  <div class="container">
    <header class="jumbotron">
      <h3>
        <strong>Profil uzytkownika {{currentUser.firstName}} {{currentUser.lastName}}</strong>
      </h3>
      <button class="btn btn-primary" @click="modify">Edytuj dane</button>
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
  methods: {
    modify() {
    this.$router.push('/modify')
    }
  },
  mounted() {
    if (!this.currentUser) {
      this.$router.push('/login');
    }
  }
};
</script>