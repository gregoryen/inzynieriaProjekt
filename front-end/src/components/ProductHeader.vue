<template>
  <li>
    <router-link :to="{name: 'product', params: {id: product.id}}" id="product" >
      <div>{{product.name}}</div>
      <div>{{product.price}} PLN</div>
    </router-link>
  </li>
</template>

<script>
// import axios from "axios";

export default {
  name: "ProductHeader",
  props: {
    product: Object
  },
  data: () => {
    return {
      imageUrl: null,
      image: null
    };
  },
  mounted() {
    this.imageUrl = this.product.links;
  },
  created() {
    // axios.get("http://localhost:8080").then(response => {
    //   this.products = response.data;
    // });
  },
  methods: {}
};
</script>

<style scoped>
#product{
  display: flex;

}
li > a {
  height: 60px;
}

div:first-child{
  padding-right: 20px;
}
</style>
