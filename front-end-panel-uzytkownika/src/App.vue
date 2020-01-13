<template>
  <div id="app">
    <nav class="navbar navbar-expand navbar-dark bg-dark">
      <a href="#" class="navbar-brand"></a>
      <div class="navbar-nav mr-auto">
        <li class="nav-item">
          <a href="/home" class="nav-link">
            <font-awesome-icon icon="home" /> Glowna
          </a>
        </li>
        <li class="nav-item" v-if="showAdminBoard">
          <a href="/admin" class="nav-link">Panel Admina</a>
        </li>
        <li class="nav-item">
          <a href="/userBoard" class="nav-link" v-if="currentUser">Uzytkownik</a>
        </li>
      </div>

      <div class="navbar-nav ml-auto" v-if="!currentUser">
        <li class="nav-item">
          <a href="/register" class="nav-link">
            <font-awesome-icon icon="user-plus" /> Rejestracja
          </a>
        </li>
        <li class="nav-item">
          <a href="/login" class="nav-link">
            <font-awesome-icon icon="sign-in-alt" /> Logowanie
          </a>
        </li>
      </div>

      <div class="navbar-nav ml-auto" v-if="currentUser">
        <li class="nav-item">
		
			<a href="/profile" class="nav-link">
            <font-awesome-icon icon="user" />
            {{currentUser.firstName}} {{currentUser.lastName}}
			
          </a>
		
        </li>
        <li class="nav-item">
          <a href class="nav-link" @click="logOut">
            <font-awesome-icon icon="sign-out-alt" /> Wyloguj
          </a>
        </li>
      </div>
    </nav>

    <div class="container">
      <router-view />
    </div>
  </div>
</template>

<script>
export default {
  computed: {
    currentUser() {
	
	
	
	return this.$store.state.auth.user;
	
	
	
	
      
    },
	
    showAdminBoard() {
      if (this.currentUser) {
        //return this.currentUser.roles.includes('ROLE_ADMIN');
      }

      return false;
    },
    showModeratorBoard() {
      if (this.currentUser) {
        //return this.currentUser.roles.includes('ROLE_MODERATOR');
      }

      return false;
    }
  },
  
  methods: {
    logOut() {
      this.$store.dispatch('auth/logout');
      this.$router.push('/login');
    }
  }
};
</script>