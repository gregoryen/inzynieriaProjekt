<template>
  <div class="container">
    <div v-if="loading">
    <div class="shoppingCart">
      <div class="bucket">
        <h1 style="text-align: left; background-color: #c1cde0">Podsumowanie</h1>
      </div>

      <div class="listSummaryRow">
        <b-row>
          <b-col cols="8">
            <div class="cartList">
              <ul class="cartItems" v-for="item in cartItems" v-bind:key="item.itemName">
                <b-row>
                  <b-col sm="2">
                    <img :src="'http://localhost:8100/images/downloadAdditionalImage?idImage=' 
                    + item.product.mainImage" height="40em" width="40em" />
                  </b-col>
                  <b-col sm="3">
                    <li>Nazwa: {{item.product.name}}</li>
                  </b-col>
                  <b-col sm="3">
                    <li>Cena :{{item.product.price}} zł</li>
                  </b-col>
                  <b-col sm="2">
                    <p> Ilosc: {{item.productQuantity}} </p>
                  </b-col>
                </b-row>
              </ul>
            </div>
          </b-col>
          <b-col cols="4">
            <div class="cartSummary">
              <p style="text-align: center;"  v-if="cartItems.length > 1">{{cartItems.length}} produktów</p>
              <p style="text-align: center;"  v-else>{{cartItems.length}} produkt</p>
              <p style="text-align: center;" >Koszt: {{this.bucket.totalValue}} zł</p>
            </div>
          </b-col>
        </b-row>
      </div>

      <div style="float: left;">
        <b-button @click="executePayment69()">Zapłać</b-button>
      </div>
    </div>
    </div>
    <div v-else>
      <p>Ładowanie strony</p>
    </div>

    
    <b-modal ref="successCreate" hide-footer title="Złożono zamówienie">
      <div class="d-block text-center">
        <h3>Płatność zakończona sukcesem.</h3>
      </div>
      <b-button class="mt-3" variant="success" block @click="hideSuccessModal">OK</b-button>
    </b-modal>

    <b-modal ref="failCreate" hide-footer title="Nie złożono zamówienia">
      <div class="d-block text-center">
        <h3>Nie udało się przetworzyć płatności. Spróbuj jeszcze raz.</h3>
      </div>
      <b-button class="mt-3" variant="danger" block @click="hideFailModal">OK</b-button>
    </b-modal>
  </div>
</template>

<script>
import axios from "axios";
import apiConfig from "../config.js"

export default {
  name: "Payment",
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
      showSuccessModal() {
      this.$refs["successCreate"].show()
    },
    showFailModal() {
      this.$refs["failCreate"].show()
    },
    hideSuccessModal() {
      this.$refs["successCreate"].hide();
      this.$router.push("/");
    },
    hideFailModal() {
      this.$refs["failCreate"].hide();
    },

    executePayment69() {
        let queryString = window.location.href.split('/');
        // eslint-disable-next-line no-console
        console.log(queryString[queryString.length - 1])

        let url = apiConfig.root + "/payment/create";
        axios.post(url, {
            orderId: queryString[queryString.length - 1],
            paymentType: "Credit Card"
        }).then(response => {
            // eslint-disable-next-line no-console
            console.log(response)
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
