<template>
  <div id="product">
    <div>
      <h4>{{product.name}}</h4>
    </div>
    <div id="container">
      <div id="image"></div>
      <div id="descriptionBox">
        <div id="price">Cena: {{product.price}} PLN</div>
        <p id="description">Opis: {{product.description}}</p>
      </div>
      <div id="categories"> </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "Product",
  props: {
    id: Number
  },
  data: () => {
    return {
      product: null,
      tree: null,
      image: null
    };
  },
  mounted() {
    axios.get("http://localhost:8080/products/" + this.id).then(response => {
      this.product = response.data;
    });
    axios.get("http://localhost:8080/products/tree/" + this.id).then(response => {
      this.product = response.data;
    });
  },
  methods: {}
};
</script>

<style scoped>
#product{
  width: 100%;
  height: 100%;
}

h4 {
  text-align: center;
}

#container {
  margin: 20px;
  width: 95%;
}

#descriptionBox {
  width: calc(100% - 60px - 350px);
  padding-right: 10px;
  float: right
}

#image {
  float: left;
  width: 350px;
  height: 350px;
  background-color: red;
}
</style>
