<template>
  <div id="app">
    <Categories :baseUrl="apiUrl" />
    <Products :baseUrl="apiUrl" />
  </div>
</template>

<script>
import Categories from "./components/Categories.vue";
import Products from "./components/Products.vue";
import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap-vue/dist/bootstrap-vue.css";

export default {
  name: "app",
  components: {
    Categories,
    Products
  },
  data: function() {
    return {
      apiUrl: "http://localhost:8080"
    };
  }
};
</script>
