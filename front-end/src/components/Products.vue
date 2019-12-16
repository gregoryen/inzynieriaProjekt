<template>
  <div id="productContainer">
    <p id="emptyProductsMessage" v-if="products === null || products.length === 0">BRAK PRODUKTÓW</p>
    <ul v-else>
      <ProductHeader v-for="product in products" v-bind:key="product.name" :product="product" />
    </ul>
  </div>
</template>

<script>
import axios from "axios";
import { bus } from "../main.js";
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
  mounted() {
    bus.$on("products", products => {
      this.products = products;
    });
  },
  created() {
    bus.$on("products", products => {
      if (products === null) {
        axios
          .get(
            "http://localhost:8080/products/search/findAllByActiveIsTrue?projection=header"
          )
          .then(response => {
            this.products = response.data._embedded.products;
          });
      }
    });
  },
  methods: {
    getProducts(event) {
      this.products = event;
    }
  }
};
</script>

<style scoped>
#productContainer {
  margin-top: 15px;
}

#emptyProductsMessage {
  text-align: center;
  font-size: ;
}
ul {
  list-style-type: none;
}
</style>
