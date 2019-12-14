<template>
  <li>
    <a id="product" href="#product">
      <div>{{product.name}}</div>
      <div>{{product.price}}</div>
    </a>
  </li>
</template>

<script>
import axios from "axios";

export default {
  name: "ProductHeader",
  props: {
    product: Object
  },
  data: () => {
    return {
      imageUrl: null,
      image: null
    };
  },
  mounted() {
    this.imageUrl = this.product.links;
    axios.get("http://localhost:8080").then(response => {
      this.products = response.data;
    });
  },
  methods: {}
};
</script>

<style scoped>
#product{
  display: flex;

}
li > a {
  height: 60px;
}

div:first-child{
  padding-right: 20px;
}

div:last-child::after{
content: '\00a0PLN';
}
</style>
