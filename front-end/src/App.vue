<template>
  <div id="app">
    <Categories :baseUrl="apiUrl" />
    <Products :baseUrl="apiUrl" />
    <!-- <Product :id="26"/> -->
  </div>
</template>

<script>
import Categories from "./components/Categories.vue";
import Products from "./components/Products.vue";
// import Product from './components/Product.vue';
import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap-vue/dist/bootstrap-vue.css";

export default {
  name: "app",
  components: {
    Categories,
    Products
    // Product
  },
  data: function() {
    return {
      apiUrl: "http://localhost:8080"
    };
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
