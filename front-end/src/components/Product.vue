<template>
  <div class="product-container">
    <h1>{{product.name}}</h1>
    <div class="grid-container">
      <div class="item1">
        <b-carousel
                id="carousel-1"
                :interval="4000"
                controls
                indicators
                img-width="800"
                img-height="480"
                style="text-shadow: 1px 1px 2px #333;"
        >

          <b-carousel-slide v-for="(image, index) in product._links.additionalImages" v-bind:key="index">
            <template v-slot:img>
              <img
                      class="d-block img-fluid w-100"
                      width="800"
                      height="480"
                      :src="image.href"
                      alt="Zdjęcie produktu"
              >
            </template>
          </b-carousel-slide>

        </b-carousel>
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
    emitProducts (id) {
      this.$store.dispatch('productsHeader',  this.baseurl + UPLOAD_ACTIVE_HEADER_PRODUCTS_BY_CATEGORY_ID + id);
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
