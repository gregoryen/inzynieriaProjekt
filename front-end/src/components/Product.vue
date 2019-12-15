<template>
  <div class="product-container">
    <h1>{{product.name}}</h1>
    <div class="grid-container">
      <div class="item1">
        <img src="https://static.appvn.com/a/uploads/thumbnails/112015/mr-square_icon.png" />
      </div>
      <div class="item2">
        <div id="price">Cena: {{product.price}} PLN</div>
        <p id="description">Opis: {{product.description}}</p>
      </div>
      <div id="categories" class="item3">
        <ul>
          <li v-for="category in tree" v-bind:key="category.id" @click="emitProducts(category.id)">
            <router-link :to="{name: 'products', params: {id: product.id}}">{{category.name}}</router-link>
          </li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import {bus} from '../main.js';

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
    axios
      .get("http://localhost:8080/products/branch/" + this.id)
      .then(response => {
        this.tree = response.data;
      });
  },
  methods: {
    emitProducts (id) {
      axios.get('http://localhost:8080/products/search/findByMainCategoryId?categoryId=' + id).then(response => bus.$emit('PRODUCTS', response.data._embedded.products));
    }
  }
};
</script>

<style scoped>
.product-container{
  height: 100%;
  background-color: #232323;
  color: white;
}

h1 {
  text-align: center;
}

.product-container {
  padding: 20px;
  width: calc(100%-40px);
}
.grid-container {
  display: grid;
  grid-template-columns: auto auto auto;
  grid-gap: 10px;
  padding: 10px;
}

#price {
  font-size: 30px;
  text-align: left;
}

#description {
  text-align: left;
  font-size: 25px;
}

img {
  display: block;
  margin-left: auto;
  margin-right: auto;
  width: 350px;
  height: 350px;
}

.item2 {
  padding-left: 25px;
  grid-column-start: 2;
  grid-column-end: 4;
  grid-row-start: 1;
  grid-row-end: 4;
}

#categories {
  text-align: center;
  font-size: 20px;
}

#categories > ul {
  font-size: 30px;
  list-style: none;
}
</style>
