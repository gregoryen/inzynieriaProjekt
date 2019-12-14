<template>
  <div id="container">
    <ul>
      <ProductHeader v-for="product in products" v-bind:key="product.name" :product="product" />
    </ul>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "Product",
  props: {
    baseUrl: String
  },
  data: () => {
    return {
      products: null
    };
  },
  mounted() {
    axios
      .get(
        this.baseUrl +
          "/products/search/findAllByActiveIsTrue?projection=header"
      )
      .then(response => {
        this.products = response.data._embedded.products;
      });
  },
  methods: {}
};
</script>

<style scoped>
#container {
  margin-top: 15px;
}
ul {
  list-style-type: none;
}
</style>
