<template>
    <div class="container">
        <table align="center" class="container">
            <thead>
            <tr>
                <td><strong>Nazwa</strong></td>
                <td><strong>Ilość</strong></td>
                <td><strong>Jednostka</strong></td>
                <td></td>
            </tr>
            </thead>
            <tbody>
            <tr v-for="(stock, index) in stocks" v-bind:item="stock" v-bind:key="index">
                <td>
                    <select
                            class="form-control"
                            id="input-product-name"
                            v-model="stock.name"
                    >
                        <option :disabled="stocks.filter(item => (item.name === product.name)).length > 0"
                                v-for="(product, index) in products" v-bind:item="stock" v-bind:key="index">
                            <strong>
                                {{product.name}}
                            </strong>
                        </option>
                        <option style="color: darkgray" disabled value="">Wybierz produkt</option>
                    </select>
                </td>
                <td><input class="form-control" type="text" v-model="stock.amount"></td>
                <td>
                    <select
                            class="form-control"
                            type="text"
                            v-model="stock.measure"
                    >
                        <option v-for="(measure, index) in measures" v-bind:item="measure" v-bind:key="index">
                            {{measure}}
                        </option>
                        <option style="color: darkgray" disabled value="">jednostka</option>

                    </select>
                </td>
                <td>
                    <button :disabled="(stock.name === '' || stock.amount === '' || stock.measure === '') && (index === stocks.length - 1)"
                            class="form-control" v-if="index === stocks.length-1" v-on:click="addStock"
                            style="cursor: pointer">+
                    </button>
                </td>
                <td>
                    <button :disabled="index === 0" class="form-control" v-on:click="removeStock(index)"
                            v-if="index === stocks.length-1" style="cursor: pointer">-
                    </button>
                </td>
            </tr>
            </tbody>

        </table>
        <div id="submit-button-div" class="container">
            <button class="form-control" v-on:click="submit">Zatwierdź</button>
        </div>
    </div>
</template>


<script>
    import axios from 'axios';

    const URL = 'http://localhost:8100';
    const GET_PRODUCTS = '/products/search/findAllByActiveIsTrue?projection=header';
    const POST_STOCKS = '/stocks/addStocks';

    export default {
        created() {
            this.getProducts();
        },
        data: function () {
            return {
                stocks: [
                    {
                        productId: "",
                        name: "",
                        measure: "",
                        amount: "",
                        available: false
                    }
                ],
                products: [],
                measures: [
                    'KG', 'L', 'SZT'
                ],
                date: "",
                time: "",
                deliveryDateTime: "",
                clicked: false,
            };
        },
        methods: {
            getProducts: function () {
                axios.get(URL + GET_PRODUCTS, {
                    "Access-Control-Allow-Origin": "*",
                    "Content-Type": "application/json"
                }).then((response) => {
                    this.products = response.data._embedded.products;
                    // eslint-disable-next-line no-console
                    console.log(response)
                });
            },
            addStock: function () {
                document.createElement('tr');
                this.stocks.push({
                    productId: "",
                    name: "",
                    measure: "",
                    amount: "",
                    available: false
                });
            },
            removeStock: function (index) {
                this.stocks.splice(index, 1);
            },
            submit: function () {
                if (this.isFormCorrect() == true) {
                    this.acceptDelivery();
                } else {
                    alert("POPRAWNE DANE TO NIE SĄ");
                }
            },
            isFormCorrect: function () {
                let correct = true;
                if (this.stocks.length == 1) {
                    correct = false;
                } else {
                    for (let idx = 0; idx < this.stocks.length - 1; idx++) {
                        if (this.stocks[idx].name == "" ||
                            this.stocks[idx].measure == "" ||
                            this.stocks[idx].amount == "") {
                            // eslint-disable-next-line no-console
                            console.log(this.stocks.length);
                            correct = false;
                        }
                    }
                }
                return correct;
            },
            acceptDelivery: function () {
                this.deliveryDateTime = this.date + "T" + this.time + ":00";
                let stocks = this.stocks.slice();
                let products = this.products.slice();

                stocks.pop();

                for (let i = 0; i < stocks.length; i++) {
                    let productName = stocks[i].name;
                    let productId = products.filter(p => (p.name === productName))[0].id;
                    stocks[i].productId = productId;
                    stocks[i].available = true;
                }

                stocks.forEach(function (item) {
                    delete item.name
                });

                axios.post(URL + POST_STOCKS, {}, {
                    "Access-Control-Allow-Origin": "*",
                    "Content-Type": "application/json"
                }).then(response => {
                    // eslint-disable-next-line no-console
                    console.log(response)
                }).catch(error => {
                    // eslint-disable-next-line no-console
                    console.log(error.response)
                });
            }
        }
    };
</script>

<style scoped>
    form > div {
        text-align: left;
    }
</style>