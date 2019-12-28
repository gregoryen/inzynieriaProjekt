<template>
  <div class="product-container">
    <h1>{{product.name}}</h1>
    <div class="grid-container">
      <div class="item1">
        <img
          :src="product._links.mainImage.href"
        />
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
import { bus } from "../main.js";

const UPLOAD_ACTIVE_HEADER_PRODUCTS_BY_CATEGORY_ID = "/products/search/findByMainCategoryIdAndActiveIsTrue?projection=header&active=true&categoryId=";
export default {
  name: "Product",
    props: {
        baseurl: String
    },
  data: () => {
    return {
      product: null,
      tree: null
    };
  },
  mounted() {
    axios.get(this.$route.params.link).then(response => {
      this.product = response.data;
      axios
              .get(response.data._links.branch.href)
              .then(response => { this.tree = response.data; });
    });

  },
  methods: {
    emitProducts(id) {
      axios
        .get( this.baseurl + UPLOAD_ACTIVE_HEADER_PRODUCTS_BY_CATEGORY_ID + id
        )
        .then(response =>
          bus.$emit("products", response.data._embedded.products)
        );
    }
  }
};
</script>

<style scoped>
.product-container {
  height: 100%;
  width: calc(100% - 40px);
  padding: 20px;
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
