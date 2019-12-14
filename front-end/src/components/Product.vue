<template>
  <div id="product">
    <div>
      <h4>product.name</h4>
    </div>
    <div>
      <div id="image"></div>
      <div id="description"></div>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "Product",
  props: {
    product: Object
  },
  data: () => {
    return {
      image: null
    };
  },
  mounted() {
    axios.get(this.baseUrl + "").then(response => {
      this.image = response;
    });
  },
  methods: {}
};
</script>

<style scoped>

</style>
