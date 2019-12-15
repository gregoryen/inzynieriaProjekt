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
                  id="nameInput"
                  class="form-control"
                />
              </div>
            </div>

            <div class="form-group row">
              <label class="col-sm-4 col-form-label" for="surnameInput">Nazwisko</label>
              <div class="col-sm-8">
                <input
                  type="text"
                  id="surnameInput"
                  class="form-control"
                />
              </div>
            </div>

            <div class="form-group row">
              <label class="col-sm-4 col-form-label" for="companyInput">Firma</label>
              <div class="col-sm-8">
                <input
                  type="text"
                  id="companyInput"
                  class="form-control"
                />
              </div>
            </div>

            <div class="form-group row">
              <label class="col-sm-4 col-form-label" for="nipInput">NIP</label>
              <div class="col-sm-8">
                <input
                  type="text"
                  id="nipInput"
                  class="form-control"
                />
              </div>
            </div>

            <div class="form-group row">
              <label class="col-sm-4 col-form-label" for="streetInput">Ulica</label>
              <div class="col-sm-8">
                <input
                  type="text"
                  id="streetInput"
                  class="form-control"
                />
              </div>
            </div>

            <div class="form-group row">
              <label class="col-sm-4 col-form-label" for="homeNumberInput">Numer domu</label>
              <div class="col-sm-8">
                <input
                  type="text"
                  id="homeNumberInput"
                  class="form-control"
                />
              </div>
            </div>

            <div class="form-group row">
              <label class="col-sm-4 col-form-label" for="postCodeInput">Kod pocztowy</label>
              <div class="col-sm-8">
                <input
                  type="text"
                  id="postCodeInput"
                  class="form-control"
                />
              </div>
            </div>

            <div class="form-group row">
              <label class="col-sm-4 col-form-label" for="cityInput">Miasto</label>
              <div class="col-sm-8">
                <input
                  type="text"
                  id="cityInput"
                  class="form-control"
                />
              </div>
            </div>

            <div class="form-group row">
              <label class="col-sm-4 col-form-label" for="countryInput">Kraj</label>
              <div class="col-sm-8">
                <input
                  type="text"
                  id="countryInput"
                  class="form-control"
                />
              </div>
            </div>
            
          </div>
        </b-col>
        <b-col cols="4">
          <div class="cartSummary">
            <p>{{productCount}} produktow</p>
            <p>Wysyłka: Chcesz wysyłke za darmo? Pierdol sie 32 zł</p>
            <p>Razem: {{totalPrice}} europapierze</p>
          </div>
        </b-col>
      </b-row>
    </div>
  </div>
</template>



<script>
export default {
  name: "OrderSummary",
  props: {
    productCount: Number,
    totalPrice: Number
  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
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
