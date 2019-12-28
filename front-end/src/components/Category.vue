<template>
  <li>
    <a @click="emitProducts">{{root.category.name}}</a>
    <ul v-if="root.children.length !== 0">
      <Category
        v-for="subcategory in root.children"
        v-bind:key="subcategory.id"
        :root="subcategory"
        :baseurl="baseurl"
      />
    </ul>
  </li>
</template>

<script>
import { bus } from '../main'

const UPLOAD_ACTIVE_HEADER_PRODUCTS_BY_CATEGORY_ID = "/products/search/findByMainCategoryIdAndActiveIsTrue?projection=header&active=true&categoryId=";

export default {
  name: "Category",
  props: {
    root: Object,
    baseurl: String
  },
  methods: {
    emitProducts () {
      bus.$emit('products', this.baseurl + UPLOAD_ACTIVE_HEADER_PRODUCTS_BY_CATEGORY_ID + this.root.category.id);
    }
  }
};
</script>
