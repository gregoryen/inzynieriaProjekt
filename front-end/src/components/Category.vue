<template>
  <li>
    <a @click="emitProducts">{{root.category.name}}</a>
    <ul v-if="root.children.length != 0">
      <Category
        v-for="subcategory in root.children"
        v-bind:key="subcategory.id"
        :root="subcategory"
      />
    </ul>
  </li>
</template>

<script>
import axios from "axios";
import { bus } from '../main'
export default {
  name: "Category",
  props: {
    root: Object
  },
  methods: {
    emitProducts () {
      axios.get('http://localhost:8080/products/search/findByMainCategoryId?categoryId=' + this.root.category.id).then(response => bus.$emit('products', response.data._embedded.products));
    }
  }
};
</script>
