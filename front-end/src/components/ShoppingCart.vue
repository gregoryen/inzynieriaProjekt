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
                    <img src="../assets/logo.png" height="85px" width="85px" />
                  </b-col>
                  <b-col sm="4">
                    <li>{{item.itemName}}</li>
                    <li v-if="item.isAvailable === 'true'">Dostepny</li>
                    <li v-else>Niedostepny</li>
                    <li>{{item.price}}</li>
                  </b-col>
                  <b-col sm="2">
                    <input type="number" min="1" max="10" step="1" value="1" size="2" />
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
              <p>{{cartItems.length}} produktow</p>
              <router-link to="/orderSummary" tag="b-button">Przejdz do kasy</router-link>
            </div>
          </b-col>
        </b-row>
      </div>

      <div style="float: left;">
        <router-link to="/orderSummary" tag="b-button">Przejdz do kasy</router-link>
        <br/>
        <router-link to="/" tag="b-button">Kontynuuj zakupy</router-link> <!-- Zmienic na poprzednia strone z route'a -->
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "ShoppingCart",
  props: {},
  data: function() {
    return {
      cartItems: [
        {
          itemName: "Zmywak do cipy",
          price: "25 zł",
          isAvailable: "true",
          img: "../assets/logo.png",
          quantity: 1
        },
        {
          itemName: "Poduszka pod pizde",
          price: "2137 papieży",
          isAvailable: "false",
          img: "../assets/logo.png",
          quantity: 1
        }
      ]
    };
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

input[type="number"]::-webkit-inner-spin-button,
input[type="number"]::-webkit-outer-spin-button {
  opacity: 1;
}
</style>
