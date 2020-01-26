<template>
  <div class="container">
    <div v-if="loading">
    <div class="shoppingCart">
      <div class="bucket">
        <h1 style="text-align: left; background-color: #c1cde0">Koszyk</h1>
      </div>

      <div class="listSummaryRow">
        <b-row>
          <b-col cols="8">
            <div class="cartList">
              <ul class="cartItems" v-for="item in cartItems" v-bind:key="item.itemName">
                <b-row>
                  <p> {{item.product.id}} </p>  <!-- Do wyrzucenia chyba ze chcemy id produktu wyswietlac -->
                  <b-col sm="4">
                    <img :src="'http://localhost:8100/images/downloadAdditionalImage?idImage=' 
                    + item.product.mainImage" height="85em" width="85em" />
                            <!-- DO TEGO LINKA WYZEJ TRZEBA ZMIENIC ADRES LOCAL HOSTA NA ZMIENNA -->
                  </b-col>
                  <b-col sm="4">
                    <li>{{item.product.name}}</li>
                    <li v-if="item.product.active === true">Dostępny</li>
                    <li v-else>Niedostępny</li>
                    <li>{{item.product.price}} zł</li>
                  </b-col>
                  <b-col sm="2">
                    <input
                      class="itemNumberSpinner"
                      v-bind:id="item.product.id"
                      v-if="item.product.active === true"
                      type="number"
                      min="1"
                      max="10"
                      step="1"
                      v-bind:value="item.productQuantity"
                      size="2"
                      v-on:change="updateProductQuantity(item.product.id)"
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
                    <b-button squared class="deleteItemButton" v-on:click="deleteCartItem(item.product.id)">Kosz</b-button>
                  </b-col>
                </b-row>
              </ul>
            </div>
          </b-col>
          <b-col cols="4">
            <div class="cartSummary">
              <p v-if="cartItems.length > 1">{{cartItems.length}} produktów</p>
              <p v-else>{{cartItems.length}} produkt</p>
              <p>Koszt: {{this.bucket.totalValue}} zł</p>
              <router-link to="/orderSummary" tag="b-button">Przejdź do kasy</router-link>
            </div>
          </b-col>
        </b-row>
      </div>

      <div style="float: left;">
        <!-- @click="$router.go("-1") alternatywnie cofnij do poprzedniej strony -->
        <b-button @click="$router.push('/overview')">Kontynuuj zakupy</b-button>
      </div>
    </div>
    </div>
    <div v-else>
      <p>Ładowanie strony</p>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import apiConfig from "../config.js"

export default {
  name: "ShoppingCart",
  props: {
    bucketId: String      //undefined nieuzywane atm
  },
  data: function() {
    return {
      loading: false,
      bucket: Object,
      cartItems: [],
      kosztWysylki: 32,
      totalValue: 0,
    };
  },
  methods: {
    deleteCartItem: function(id) {
      // eslint-disable-next-line no-console
        console.log(id)
        let token = JSON.parse(localStorage.getItem('user')).jwtToken
        let bucketId = token.substring(0, token.length/4)
        let url = apiConfig.root + "/bucket/deletePosition/" + bucketId + "/" + id;
        axios.delete(url)
        .then( (res) => {
          // eslint-disable-next-line no-console
          console.log(res)
          this.bucket = res.data
          this.cartItems = this.bucket.positions;
        })
    },
    updateProductQuantity: function(id) {
      let value = document.getElementById(id).value

      let token = JSON.parse(localStorage.getItem('user')).jwtToken
      let bucketId = token.substring(0, token.length/4)
      // eslint-disable-next-line no-console
      console.log(bucketId)
      // eslint-disable-next-line no-console
      console.log(document.getElementById(id))
      
      // eslint-disable-next-line no-console
      console.log(value)
      // <!--  /update/{bucketId}/{productId}/{quantity} -->
      let url = apiConfig.root + '/bucket/update/' + bucketId + '/' + id + '/' + value
      axios.put(url)
      .then( (res) => {
          // eslint-disable-next-line no-console
          console.log(res)
          this.bucket = res.data
          this.cartItems = res.data.positions
      })
    }
  },
  created() {
    let token = JSON.parse(localStorage.getItem('user')).jwtToken
    let bucketId = token.substring(0, token.length/4)
    // eslint-disable-next-line no-console
    console.log(bucketId)
    let url = apiConfig.root + "/bucket/getBucketById/" + bucketId;

    axios.get(url).then(response => {
        // eslint-disable-next-line no-console
        console.log(response.data)
        this.bucket = response.data
        this.totalValue = response.data.totalValue;
        this.cartItems = response.data.positions;
        // eslint-disable-next-line no-console
        console.log(response.data.positions) 
    }).then( () => {
      this.loading = true;
    });
  }
};
</script>

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
  margin: 0.1em;
  margin-bottom: 2em;
  border-style: solid;
  border-width: 0.01em;
}

.deleteItemButton {
  margin-top: 1.5em;
}

.itemNumberSpinner {
  margin-top: 1.5em;
}

input[type="number"]::-webkit-inner-spin-button,
input[type="number"]::-webkit-outer-spin-button {
  opacity: 1;
}
</style>
