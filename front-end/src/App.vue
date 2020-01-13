<template>
  <div id="app">
    <nav class="navbar navbar-expand navbar-dark bg-dark">
      <a href="#" class="navbar-brand">GardenShop</a>
      <div class="navbar-nav mr-auto">
        <li class="nav-item">
          <a href="/" class="nav-link">
            <font-awesome-icon icon="home" />Home
          </a>
        </li>
      </div>
      <div class="navbar-nav ml-auto" v-if="!currentUser">
        <li class="nav-item">
          <a href="/register" class="nav-link">
            <font-awesome-icon icon="user-plus" />Sign Up
          </a>
        </li>
        <li class="nav-item">
          <a href="/login" class="nav-link">
            <font-awesome-icon icon="sign-in-alt" />Login
          </a>
        </li>
      </div>
      <div class="navbar-nav ml-auto" v-if="currentUser">
        <li class="nav-item" v-if="ifHavePrivilege('ADMIN_PRIVILEGE')">
          <a href="/adminPanel" class="nav-link">
           Admin Panel
          </a>
        </li>
        <li class="nav-item">
          <a href="/shoppingCart" :bucketId=createBucket() class="nav-link">
            <BucketButton />
          </a>
        </li>
        <li class="nav-item">
          <a href="/profile" class="nav-link">
            <font-awesome-icon icon="user" />
            {{currentUser.username}}
          </a>
        </li>
        <li class="nav-item">
          <a href class="nav-link" @click="logOut">
            <font-awesome-icon icon="sign-out-alt" />LogOut
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
import BucketButton from "./utils/BucketButton"
import config from "./config"
import axios from "axios"
export default {
  components: {
    BucketButton
  },
  computed: {
    currentUser() {
      return this.$store.state.auth.user;
    }
  },
  methods: {
    logOut() {
      this.$store.dispatch("auth/logout");
      this.$router.push("/login");
    },
    ifHavePrivilege(privilegeName) {
      if (this.$store.state.auth.status.loggedIn) {
        
        let userInfo = this.$store.state.auth.user.privileges.split(',');
        for (let i = 0; i < userInfo.length; i++) {
          if (userInfo[i] == privilegeName) {
            return true;
          }
          
        }
        return false;
      }
      return false;
    },
    createBucket() {
      let user = JSON.parse(localStorage.getItem('user'))
      axios.post(config.root + "/bucket/createBucket", {
        id: user.jwtToken.substr(0, user.jwtToken.length/4)
      }).then( (res) => {
          // eslint-disable-next-line no-console
          console.log(res)
      })
      return user.jwtToken
    } 
  }
};
</script>
<style>
#app {
  font-family: "Avenir", Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
}
.small-table {
  margin-top: 60px;
  margin-left: 4em;
}
.main-table {
  margin-top: 60px;
  margin-right: 4em;
}
.space {
  margin-bottom: 3em;
}
h2 {
  font-family: Impact, Haettenschweiler, Franklin Gothic Bold, Charcoal,
    Helvetica Inserat, Bitstream Vera Sans Bold, Arial Black, sans serif;
}
.small-card-deck {
  display: flex;
  margin-right: 3em;
  margin-left: 3em;
  margin-bottom: 3em;
}
