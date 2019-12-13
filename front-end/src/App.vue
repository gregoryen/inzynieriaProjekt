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
