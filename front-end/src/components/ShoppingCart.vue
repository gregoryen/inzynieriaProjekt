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
                    <li>{{item.itemName}}</li>
                    <li v-if="item.isAvailable === 'true'">Dostepny</li>
                    <li v-else>Niedostepny</li>
                    <li>{{item.price}} europapierze</li>
                  </b-col>
                  <b-col sm="2">
                    <input class="itemNumberSpinner" v-if="item.isAvailable === 'true'" type="number" min="1" max="10" step="1" value="1" size="2" />
                    <input class="itemNumberSpinner" v-else type="number" min="0" max="0" step="1" value="0" size="2" />
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
              <p>Razem: {{this.calcCartPrice()}} europapierze</p>
              <router-link to="/orderSummary" tag="b-button">Przejdz do kasy</router-link>
            </div>
          </b-col>
        </b-row>
      </div>

      <div style="float: left;">
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
          price: 25,
          isAvailable: "true",
          img: "../assets/logo.png",
          quantity: 1
        },
        {
          itemName: "Poduszka pod pizde",
          price: 2137,
          isAvailable: "false",
          img: "../assets/logo.png",
          quantity: 1
        },
        {
          itemName: "Wiosna dla biedronia",
          price: 7,
          isAvailable: "true",
          img: "../assets/logo.png",
          quantity: 1
        },
        {
          itemName: "Kremowka tak o",
          price: 2005,
          isAvailable: "true",
          img: "../assets/logo.png",
          quantity: 1
        },
        {
          itemName: "Upojna noc z Guzkiem",
          price: 69,
          isAvailable: "false",
          img: "../assets/logo.png",
          quantity: 1
        }
      ]
    };
  },
  methods: {    //prawdopodobnie sie zmieni po zebraniu danych z bazy
      calcCartPrice: function() {
        var totalPrice = 0;
        for(let item of this.cartItems)
        {
            totalPrice = totalPrice + item.price;
        }
        return totalPrice;
      }
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
