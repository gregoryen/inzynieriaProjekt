<template>
  <div id="app">
    <Categories :baseUrl="apiUrl" />
    <Products :baseUrl="apiUrl" />
<!--    <add-product :baseUrl="apiUrl"></add-product>-->
  </div>
</template>

<script>
import Categories from "./components/Categories.vue";
import Products from "./components/Products.vue";
/*import AddProduct from "./components/AddProduct";*/
import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap-vue/dist/bootstrap-vue.css";

export default {
  name: "app",
  components: {
    Categories,
    Products,
/*    AddProduct*/
  },
  data: function() {
    return {
      apiUrl: "http://localhost:8080"
    };
  }
};
</script>
