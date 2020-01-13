<template>
  <li>
    <a @click="emitProducts">{{root.category.name}}</a>
    <ul v-if="root.children.length !== 0">
      <Category
        v-for="subcategory in root.children"
        v-bind:key="subcategory.id"
        :root="subcategory"
      />
    </ul>
  </li>
</template>

<script>
const UPLOAD_ACTIVE_HEADER_PRODUCTS_BY_CATEGORY_ID = "/products/search/findByMainCategoryIdAndActiveIsTrue?projection=header&active=true&categoryId=";
import globalConfig from '../config'
export default {
  name: "Category",
  props: {
    root: Object,
  },
  methods: {
    emitProducts () {
      this.$store.dispatch('productsHeader', globalConfig.root + UPLOAD_ACTIVE_HEADER_PRODUCTS_BY_CATEGORY_ID + this.root.category.id);
    }
  }
};
</script>
