<template>
  <li>
    <a href="#">{{root.category.name}}</a>
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
export default {
  components: {},
  name: "Category",
  props: {
    root: Object
  }
};
</script>
