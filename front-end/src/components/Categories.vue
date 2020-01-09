<template>
  <nav>
    <ul>
      <li><a @click="emitAllProducts">Wszystkie produkty</a></li>
      <Category v-for="root in categories" v-bind:key="root.category.id" :root="root" :baseurl="baseurl" />
    </ul>
  </nav>
</template>

<script>
import axios from "axios";
//import { bus } from '../main';
import Category from "./Category.vue";

export default {
  name: "Categories",
  components: { Category },
  props: {
    baseurl: String
  },
  data: () => {
    return {
      categories: null,
    };
  },
  mounted() {
    axios.get(this.baseurl + "/categories/tree").then(response => {
      this.categories = response.data;
    });
  },
  methods: {
    emitAllProducts() {
      this.$store.dispatch('productsHeader',  this.baseurl + "/products/search/findAllByActiveIsTrue?projection=header");
    }
  }
};
</script>

<style>
nav {
  width:100%;
  margin: 0;
  background-color: #252323;
  color: white;
  text-align: left;
}

nav ul {
  padding: 0;
  margin: 0;
  list-style: none;
  position: relative;
}

nav ul li {
  z-index: 100;
  display: inline-block;
  background-color: #252323;
}

nav a {
  display: block;
  padding: 0 10px;
  color: #fff;
  font-size: 20px;
  line-height: 60px;
}

nav a:hover {
  background-color: #000000;
}

/* Hide Dropdowns by Default */
nav ul ul {
  display: none;
  position: absolute;
  top: 60px; /* the height of the main nav */
}

/* Display Dropdowns on Hover */
nav ul li:hover > ul {
  display: inherit;
}

/* Fisrt Tier Dropdown */
nav ul ul li {
  width: 170px;
  float: none;
  display: list-item;
  position: relative;
}

/* Second, Third and more Tiers	*/
nav ul ul ul li {
  position: relative;
  top: -60px;
  left: 170px;
}

/* Change this in order to change the Dropdown symbol */
li > a:after {
  content: " ⯆";
}
li > a:only-child:after {
  content: "";
}
</style>

