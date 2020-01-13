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
              <ul class="cartItems" v-for="item in cartItems" v-bind:key="item.itemName">
                <b-row>
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
                  <b-col sm="2"> <!-- NIZEJ MIN ZAMIENIC NA BUCKETPOSITION.QUANTITY -->
                    <input
                      class="itemNumberSpinner"
                      v-if="item.product.active === true"
                      type="number"
                      min="1"       
                      max="10"
                      step="1"
                      value="1"
                      size="2"
                    />              <!-- PRZY AKTUALIZACJI SPINNERA AKTUALIZUJ QUANTITY NA BACKENDZIE -->
                                    <!--  /update/{bucketId}/{productId}/{quantity} -->
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
              <p>Koszt: {{this.totalValue}} zł</p>
              <router-link to="/orderSummary" tag="b-button">Przejdź do kasy</router-link>
            </div>
          </b-col>
        </b-row>
      </div>

      <div style="float: left;">
        <b-button @click="$router.go(-1)">Kontynuuj zakupy</b-button>
        <!-- Wraca do poprzedniej strony -->
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import apiConfig from "../config.js"

export default {
  name: "ShoppingCart",
  // props: {
  //   bucketId: String
  // },
  data: function() {
    return {
      cartItems: [],
      kosztWysylki: 32,
      totalValue: 0
    };
  },
  methods: {
    deleteCartItem: (id) => {
        let url = apiConfig.root + "/bucket/deletePosition" + "/user1/" + id;  //ID KOSZYKA do zmiany
        // eslint-disable-next-line no-console
        console.log(id)
        axios.delete(url)
        .then( (res) => {
          // eslint-disable-next-line no-console
          console.log(res)
        })
    }
  },
  created() {
    let url = apiConfig.root + "/bucket/getBucketById/" + "user1";  //ID KOSZYKA do zmiany

    // const config = {
    //   headers: {
    //     Authorization: "Bearer " + apiConfig.token,
    //     "Content-type": "application/json"
    //   }
    // };

    axios.get(url).then(response => {
        // eslint-disable-next-line no-console
        console.log(response.data)
        this.totalValue = response.data.totalValue;
        this.cartItems = response.data.positions;
        // eslint-disable-next-line no-console
        console.log(response.data.positions)

        // this.$router.go(0);    //odswiez strone
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
