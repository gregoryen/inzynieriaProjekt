<template>
  <div class="container">
    <div class="orderSummary">
      <b-row>
        <b-col cols="8">
          <div class="clientInfo">
            <h1 style="margin-top: 0.1em; margin-bottom: 0.1em;">Dane klienta</h1>
            <div class="form-group row">
              <label class="col-sm-4 col-form-label" for="nameInput">Imie</label>
              <div class="col-sm-8">
                <input
                  type="text"
                  v-model="nameInput"
                  class="form-control"
                />
              </div>
            </div>

            <div class="form-group row">
              <label class="col-sm-4 col-form-label" for="surnameInput">Nazwisko</label>
              <div class="col-sm-8">
                <input
                  type="text"
                  v-model="surnameInput"
                  class="form-control"
                />
              </div>
            </div>

            <div class="form-group row">
              <label class="col-sm-4 col-form-label" for="telephoneNumber">Telefon</label>
              <div class="col-sm-8">
                <input
                  type="text"
                  v-model="telephoneNumber"
                  class="form-control"
                />
              </div>
            </div>


            <div class="form-group row">
              <label class="col-sm-4 col-form-label" for="streetInput">Ulica</label>
              <div class="col-sm-8">
                <input
                  type="text"
                  v-model="streetInput"
                  class="form-control"
                />
              </div>
            </div>

            <div class="form-group row">
              <label class="col-sm-4 col-form-label" for="homeNumberInput">Numer domu</label>
              <div class="col-sm-8">
                <input
                  type="text"
                  v-model="homeNumberInput"
                  class="form-control"
                />
              </div>
            </div>

            <div class="form-group row">
              <label class="col-sm-4 col-form-label" for="postCodeInput">Kod pocztowy</label>
              <div class="col-sm-8">
                <input
                  type="text"
                  v-model="postCodeInput"
                  class="form-control"
                />
              </div>
            </div>

            <div class="form-group row">
              <label class="col-sm-4 col-form-label" for="cityInput">Miasto</label>
              <div class="col-sm-8">
                <input
                  type="text"
                  v-model="cityInput"
                  class="form-control"
                />
              </div>
            </div>

            <div class="form-group row">
              <label class="col-sm-4 col-form-label" for="countryInput">Kraj</label>
              <div class="col-sm-8">
                <input
                  type="text"
                  v-model="countryInput"
                  class="form-control"
                />
              </div>
            </div>
            

            <b-button class="dark" v-on:click=executeOrder66() >Złóż zamówienie</b-button>
          </div>
        </b-col>
        <!-- <b-col cols="4">
          <div class="cartSummary">
            <p>{{productCount}} produktow</p>
            <p>Wysyłka: 32 zł</p>
            <p>Razem: {{totalPrice}} zł</p>
          </div>
        </b-col> -->
      </b-row>
    </div>

    <b-modal ref="successCreate" hide-footer title="Złożono zamówienie">
      <div class="d-block text-center">
        <h3>Zamówienie zakończono sukcesem</h3>
      </div>
      <b-button class="mt-3" variant="success" block @click="hideSuccessModal">OK</b-button>
    </b-modal>

    <b-modal ref="failCreate" hide-footer title="Nie złożono zamówienia">
      <div class="d-block text-center">
        <h3>Nie udało się złożyć zamówienia</h3>
      </div>
      <b-button class="mt-3" variant="danger" block @click="hideFailModal">OK</b-button>
    </b-modal>
  </div>
</template>



<script>
import config from "../config"
import axios from "axios"
export default {
  name: "OrderSummary",
  data() {
    return({
      nameInput: '',
      surnameInput: '',
      telephoneNumber: '',
      streetInput: '',
      homeNumberInput: '',
      postCodeInput: '',
      cityInput: '',
      countryInput: ''
    })
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
    
    executeOrder66() {
      let user = JSON.parse(localStorage.getItem('user'))
      // eslint-disable-next-line no-console
      console.log(user)
      let token = user.jwtToken
      let bucketId = token.substring(0, token.length/4)
      let totalValue = null

      axios.get(config.root + '/bucket/getBucketById/' + bucketId)
      .then( (res) => {
        totalValue = res.data.totalValue
      }).then( () => {
        axios.post(config.root + '/order/addOrder', {
          firstName: this.nameInput,
          lastName: this.surnameInput,
          adress: this.streetInput + '' + this.homeNumberInput + '' + this.postCodeInput + '' 
          + this.cityInput + '' + this.countryInput,
          phoneNumber: this.telephoneNumber,
          email: user.email,
          orderBucketId: bucketId,
          orderValue: totalValue,
          isPaid: true
        }).then( (res) => {
          this.showSuccessModal()
          // eslint-disable-next-line no-console
          console.log(res)
        }).catch( () => {
          this.showFailModal()
        })
      })
    }
  }
};
</script>

<style scoped>
ul {
  list-style-type: none;
}

.clientInfo {
  border-style: solid;
  border-width: 0.1 em;
}

.cartSummary {
  border-style: solid;
  border-width: 0.1 em;
}
</style>
