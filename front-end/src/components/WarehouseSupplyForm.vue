<template>
    <div id="warehouse-supply-form">
        <form id="supplier-info" class="container">
            <div class="form-group row">
                <label for="input-firstname" class="col-sm-2 col-form-label">Imię:</label>
                <input
                        type="text"
                        class="form-control"
                        id="input-firstname"
                        placeholder="imię"
                        v-model="supplier.inputFirstname"
                />
            </div>
            <div class="form-group row">
                <label for="input-lastname" class="col-sm-2 col-form-label">Nazwisko:</label>
                <input
                        type="text"
                        class="form-control"
                        id="input-lastname"
                        placeholder="nazwisko"
                        v-model="supplier.inputLastname"
                />
            </div>
            <div class="form-group row">
                <label for="input-phone" class="col-sm-2 col-form-label">Tel.:</label>
                <input
                        type="text"
                        class="form-control"
                        id="input-phone"
                        placeholder="000-000-000"
                        v-model="supplier.inputPhoneNumber"
                >
            </div>
            <div class="form-group row">
                <label for="input-company" class="col-sm-2 col-form-label">Firma:</label>
                <input
                        type="text"
                        class="form-control"
                        id="input-company"
                        placeholder="firma"
                        v-model="supplier.inputCompany"
                >
            </div>
        </form>
        <table id="supply" align="center">
            <thead>
            <tr>
                <td><strong>Id produktu</strong></td>
                <td><strong>Jednostka</strong></td>
                <td><strong>Ilość</strong></td>
                <td></td>
            </tr>
            </thead>
            <tbody>
            <tr v-for="(product, index) in products" v-bind:item="product" v-bind:key="index">
                <td><input type="text" v-model="product.productId"></td>
                <td><input type="text" v-model="product.measure"></td>
                <td><input type="text" v-model="product.amount"></td>
                <td>
                    <button v-if="index === products.length-1" v-on:click="addProduct" style="cursor: pointer">+
                    </button>
                </td>
            </tr>
            </tbody>
        </table>
        <button v-on:click="submit">Zatwierdź</button>
    </div>
</template>


<script>
    import axios from 'axios';

    export default {
        props: {},
        data: function () {
            return {
                supplier: {
                    inputFirstname: "",
                    inputLastname: "",
                    inputPhoneNumber: "",
                    inputCompany: ""
                },
                products: [
                    {
                        productId: "",
                        measure: "",
                        amount: ""
                    }
                ]
            };
        },
        methods: {
            addProduct: function () {
                document.createElement('tr');
                this.products.push({
                    productId: "",
                    measure: "",
                    amount: ""
                });
            },
            submit: function () {
                axios.post('http://localhost:8081/supplies/accept_delivery', {
                    supplier: {
                        firstname: this.supplier.inputFirstname,
                        lastname: this.supplier.inputLastname,
                        company: this.supplier.inputCompany,
                        phoneNumber: this.supplier.inputPhoneNumber
                    },
                    stockAmounts: this.products
                }, {
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
    #supplier-info {
    }
</style>