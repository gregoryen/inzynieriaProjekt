<template>
  <div id="app">
    <Categories :baseurl="this.baseurl"/>
  <router-view :baseurl="this.baseurl" />
  </div>
</template>

<script>
import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap-vue/dist/bootstrap-vue.css";
import Categories from "./components/Categories";

export default {
  components: {
    Categories
  },
  name: "app",
  data() {
    return {
      baseurl: "http://localhost:8080"
    }
  }
};
</script>

<style scoped>
  #app {
    width: 100vw;
    height: 100vh;
    background-color: #313131
  }
</style>
