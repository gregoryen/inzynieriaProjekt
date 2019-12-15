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
                    <img src="../assets/kremowka.jpeg" height="85px" width="85px" />
                  </b-col>
                  <b-col sm="4">
                    <li>{{this.getProductsInfo(item.productId)}}</li>
                    <li v-if="item.isAvailable === 'true'">Dostepny</li>
                    <li v-else>Niedostepny</li>
                    <li>{{item.price}} europapierze</li>
                  </b-col>
                  <b-col sm="2">
                    <input
                      class="itemNumberSpinner"
                      v-if="item.isAvailable === 'true'"
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
              <p v-if="cartItems.length > 1">{{cartItems.length}} produktow</p>
              <p v-else>{{cartItems.length}} produkt</p>
              <p>Wysyłka: Chcesz wysyłke za darmo? Pierdol sie 32 zł</p>
              <p>Razem: {{}} europapierze</p>
              <router-link to="/orderSummary" tag="b-button">Przejdz do kasy</router-link>
            </div>
          </b-col>
        </b-row>
      </div>

      <p>{{this.cartItems}}</p>

      <div style="float: left;">
        <router-link to="/" tag="b-button">Kontynuuj zakupy</router-link>
        <!-- Zmienic na poprzednia strone z route'a -->
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "ShoppingCart",
  props: {},
  data: function() {
    return {
      cartItems: [],
      currentProduct: {},
      bucket_id: "559767dd-74a3-4aba-8184-42f30a6c3de9"
    };
  },
  methods: {
    getProductInfo: function(productId) {
      let url = "http://localhost:8080/bucketPosition/getById/" + productId;

      var token =
        "eyJhbGciOiJSUzUxMiJ9.eyJyb2xlIjoiVVNFUiIsIm5hbWUiOiJzZGFzZCBzZGFzZGFzIiwic3ViIjoiY2h1akBjaHVqLmNvbSIsImlhdCI6MTU3NjQ0MzY5MiwiZXhwIjoxNTc5MDM1NjkyfQ.TJGwqRGKExwvhuVndpvZrkSg6AIxuCJGURDSz6ot9qzcnz5bQa7R47S1EH8KcUTmBwyHF9CRowwLt8vpQuLBgUFELQAHBUvrT89gVpaKxcni1dlHEUwV2GNi4HxmKSmTV2yKbmhKGjHcOe9H7He1LZte0JAGHBhtj0vS7RgtAm7aO4cXVKKaSl-faFcXDVFllXaW-ixx6Q0DKVIzK4UQboet3fdtEUBdnrBydVLcwpjy5Fvi9kcU8Wp0foqFtdcs0HriJy7_v8fTI7M0bjJoy1VS_vg6342No4q_ZGKtXxTosSp7sUXELV0AFgNnu4vyBxBkw07x5wRa8NSgTTn9ng";
      const config = {
        headers: {
          Authorization: "Bearer " + token,
          "Content-type": "application/json"
        }
      };

      axios.get(url, config).then(response => {
      // eslint-disable-next-line no-console
      console.log(response);
      this.currentProduct = response.data;
      // eslint-disable-next-line no-console
      console.log(this.currentProduct);
    });
    }
  },
  created() {
    let url =
      "http://localhost:8080/bucketPosition/getByUUID/" + this.bucket_id;

    var token =
      "eyJhbGciOiJSUzUxMiJ9.eyJyb2xlIjoiVVNFUiIsIm5hbWUiOiJzZGFzZCBzZGFzZGFzIiwic3ViIjoiY2h1akBjaHVqLmNvbSIsImlhdCI6MTU3NjQ0MzY5MiwiZXhwIjoxNTc5MDM1NjkyfQ.TJGwqRGKExwvhuVndpvZrkSg6AIxuCJGURDSz6ot9qzcnz5bQa7R47S1EH8KcUTmBwyHF9CRowwLt8vpQuLBgUFELQAHBUvrT89gVpaKxcni1dlHEUwV2GNi4HxmKSmTV2yKbmhKGjHcOe9H7He1LZte0JAGHBhtj0vS7RgtAm7aO4cXVKKaSl-faFcXDVFllXaW-ixx6Q0DKVIzK4UQboet3fdtEUBdnrBydVLcwpjy5Fvi9kcU8Wp0foqFtdcs0HriJy7_v8fTI7M0bjJoy1VS_vg6342No4q_ZGKtXxTosSp7sUXELV0AFgNnu4vyBxBkw07x5wRa8NSgTTn9ng";
    const config = {
      headers: {
        Authorization: "Bearer " + token,
        "Content-type": "application/json"
      }
    };

    axios.get(url, config).then(response => {
      // eslint-disable-next-line no-console
      console.log(response);
      this.cartItems = response.data;
      // eslint-disable-next-line no-console
      console.log(this.cartItems);
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
