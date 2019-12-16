<template>
  <li>
    <router-link :to="{name: 'product', params: {id: product.id}}" id="product" >
      <b-container class="bv-example-row">
        <b-row>
          <b-col>
            <img :src="this.imageUrl + this.product.mainImage" width="50" height="50">
          </b-col>
          <b-col>
            <div>{{product.name}}</div>
          </b-col>
          <b-col>
            <div>{{product.price}} PLN</div>
          </b-col>
        </b-row>
      </b-container>
    </router-link>
  </li>
</template>

<script>

export default {
  name: "ProductHeader",
  props: {
    product: Object
  },
  data: () => {
    return {
      imageUrl: "http://localhost:8080/images/downloadAdditionalImage?idImage=",
      image: null
    };
  }
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
