<template>
  <div id="productContainer">
    <ul>
      <ProductHeader v-for="product in products" v-bind:key="product.name" :product="product" />
    </ul>
  </div>
</template>

<script>
import axios from "axios";
import { bus } from "../main";
import ProductHeader from "./ProductHeader.vue";

export default {
  components: { ProductHeader },
  name: "Products",
  props: {
    baseUrl: String
  },
  data: () => {
    return {
      products: null
    };
  },
  mounted() {},
  created() {
    axios
      .get(
        this.baseUrl +
          "/products/search/findAllByActiveIsTrue?projection=header"
      )
      .then(response => {
        this.products = response.data._embedded.products;
      });
    bus.$on("PRODUCTS", data => {
      this.products = data;
    });
  },
  methods: {}
};
</script>

<style scoped>
#productContainer {
  margin-top: 15px;
}
ul {
  list-style-type: none;
}
</style>
