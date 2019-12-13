<template>
  <div>
    <nav class="navbar navbar-light bg-light justify-content-between">
      <Category/>
      <form class="form-inline">
        <input class="form-control mr-sm-2" type="search" placeholder="Search" aria-label="Search" />
        <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
      </form>
    </nav>
  </div>
</template>

<script>
import axios from "axios";
import Category from "./Category.vue";

export default {
  name: "Categories",
  components: { Category },
  props: {
    baseUrl: String
  },
  data: () => {
    return {
      categories: null,
      response: null
    };
  },
  mounted() {
    axios.get("http://localhost:8080/categories/tree").then(response => {
      this.categories = response.data._embedded.categories;
    });
  },
  methods: {}
};
</script>