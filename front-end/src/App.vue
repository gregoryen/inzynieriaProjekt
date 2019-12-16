<template>
  <div id="app">
    <Categories />
    <router-view />
  </div>
</template>

<script>
import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap-vue/dist/bootstrap-vue.css";
import Categories from './components/Categories.vue'

export default {
  components: {
    Categories
  },
  name: "app"
};
</script>

<style scoped>
  #app {
    width: 100vw;
    height: 100vh;
    background-color: #313131
  }
</style>
