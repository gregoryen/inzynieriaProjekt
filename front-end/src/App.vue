<template>
  <div id="app">
    <Categories baseUrl = 'localhost:8080' />
  </div>
</template>

<script>
import Categories from './components/Categories.vue'
import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap-vue/dist/bootstrap-vue.css";

export default {
  name: 'app',
  components: {
    Categories
  },
  data: function () {
    return {
      apiUrl: 'http://localhost:8080'
    }
  }
}
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
