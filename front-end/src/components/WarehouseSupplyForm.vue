<template>
    <div id="warehouse-supply-form">
        <form  id="supplier-info" class="container">
            <div class="form-group row">
                <label for="input-firstname" class="col-sm-2 col-form-label">Imię:</label>
                <input
                        type="text"
                        class="form-control"
                        id="input-firstname"
                        placeholder="imię"
                        v-model="inputLastname"
                />
            </div>
            <div class="form-group row">
                <label for="input-lastname" class="col-sm-2 col-form-label">Nazwisko:</label>
                <input
                        type="text"
                        class="form-control"
                        id="input-lastname"
                        placeholder="nazwisko"
                        v-model="inputLastname"
                />
            </div>
            <div class="form-group row">
                <label for="input-phone" class="col-sm-2 col-form-label">Tel.:</label>
                <input
                        type="text"
                        class="form-control"
                        id="input-phone"
                        placeholder="000-000-000"
                        v-model="inputPhoneNumber"
                >
            </div>
            <div class="form-group row">
                <label for="input-company" class="col-sm-2 col-form-label">Firma:</label>
                <input
                        type="text"
                        class="form-control"
                        id="input-company"
                        placeholder="firma"
                        v-model="inputCompany"
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
                    <td><input type="text" v-model="product.inputProductId"></td>
                    <td><input type="text" v-model="product.inputMeasure"></td>
                    <td><input type="text" v-model="product.inputAmount"></td>
                    <td><button v-if="index === products.length-1" v-on:click="addProduct" style="cursor: pointer">+</button></td>
                </tr>
                </tbody>
            </table>
        <button v-on:click="submit">Zatwierdź</button>
    </div>
</template>


<script>
    import axios from 'axios';

    export default {
        props: {
        },
        data: function () {
            return {
                inputFirstname: "",
                inputLastname: "",
                inputPhoneNumber: "",
                inputCompany: "",
                products: [
                    {
                        inputProductId: "",
                        inputMeasure: "",
                        inputAmount: ""
                    }
                ]
            };
        },
        methods: {
            addProduct: function() {
                document.createElement('tr');
                this.products.push({
                    inputProductId: "",
                    inputMeasure: "",
                    inputAmount: ""
                });
            },
            submit: function () {
                axios.post('http://localhost:8081/suppliers/add',{
                    firstname: this.inputFirstname,
                    lastname: this.inputLastname,
                    company: this.inputCompany,
                    phoneNumber: this.inputPhoneNumber
                });
            }
        }
    };
</script>

<style scoped>
    #supplier-info {
    }
</style>