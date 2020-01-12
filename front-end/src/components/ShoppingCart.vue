<template>
  <div class="container">
    <div class="shoppingCart">
      <div class="bucket">
        <h1 style="text-align: left; background-color: #c1cde0">Koszyk</h1>
      </div>

      <div class="listSummaryRow">
        <b-row>
          <b-col cols="8">
            <div class="cartList">
              <ul class="cartItems" v-for="item in productInfo" v-bind:key="item.itemName">
                <b-row>
                  <b-col sm="4">
                   <!-- <p> {{index}} </p> -->
                    <img :src="'http://localhost:8100/images/downloadAdditionalImage?idImage=' + item.mainImage" height="85px" width="85px" />
                  </b-col>
                  <b-col sm="4">
                    <li>{{item.name}}</li>
                    <li v-if="item.active === true">Dostępny</li>
                    <li v-else>Niedostępny</li>
                    <li>{{item.price}} zł</li>
                  </b-col>
                  <b-col sm="2">
                    <input
                      class="itemNumberSpinner"
                      v-if="item.active === true"
                      type="number"
                      min="1"
                      max="10"
                      step="1"
                      value="1"
                      size="2"
                    />
                    <input
                      class="itemNumberSpinner"
                      v-else
                      type="number"
                      min="0"
                      max="0"
                      step="1"
                      value="0"
                      size="2"
                    />
                  </b-col>
                  <b-col>
                    <b-button squared class="deleteItemButton">Kosz</b-button>
                  </b-col>
                </b-row>
              </ul>
            </div>
          </b-col>
          <b-col cols="4">
            <div class="cartSummary">
              <p v-if="cartItems.length > 1">{{cartItems.length}} produktów</p>
              <p v-else>{{cartItems.length}} produkt</p>
              <p>Wysyłka: {{this.kosztWysylki}} zł</p>
              <p>Razem: {{this.calculateCartValue()}} zł</p>
              <router-link to="/orderSummary" tag="b-button">Przejdź do kasy</router-link>
            </div>
          </b-col>
        </b-row>
      </div>

<!--
      <p>{{this.cartItems}}</p>
      <p>{{this.productInfo}}</p>
      -->

      <div style="float: left;">
        <router-link to="/" tag="b-button">Kontynuuj zakupy</router-link>
        <!-- Zmienic na poprzednia strone z route'a -->
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import apiConfig from "../config.js"

export default {
  name: "ShoppingCart",
  props: {},
  data: function() {
    return {
      cartItems: [],
      productInfo: [],
      bucket_id: apiConfig.bucketId,
      kosztWysylki: 32
    };
  },
  methods: {
    getProductInfo: function(productId, config) {
      let url = apiConfig.root + "/products/" + productId;

      axios.get(url, config).then(response => {
      this.productInfo.push(response.data);
    });
    },
    calculateCartValue: function() {
      let totalValue = 0;
      for (let item of this.cartItems)
      {
        totalValue += item.productQuantity*item.productPrice;
      }
      return totalValue+this.kosztWysylki;
    }
  },
  created() {
    let url = apiConfig.root + "/bucketPosition/getByUUID/" + this.bucket_id;

    const config = {
      headers: {
        Authorization: "Bearer " + apiConfig.token,
        "Content-type": "application/json"
      }
    };

    axios.get(url, config).then(response => {
        this.cartItems = response.data;
        
        for (let item of this.cartItems)
        {
          this.getProductInfo(item.productId, config)
        }
    });
  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
ul {
  list-style-type: none;
}

.cartSummary {
  border-style: solid;
  border-width: 0.1em;
}

.cartList {
  border-style: solid;
  border-width: 0.1em;
}

.cartItems {
  margin: 0px;
}

.deleteItemButton {
  margin-top: 20px;
}

.itemNumberSpinner {
  margin-top: 20px;
}

input[type="number"]::-webkit-inner-spin-button,
input[type="number"]::-webkit-outer-spin-button {
  opacity: 1;
}
</style>
