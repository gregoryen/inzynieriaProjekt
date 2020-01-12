<template>
    <b-button variant="danger" v-on:click="addProductToCart()">Dodaj do koszyka</b-button>
</template>

<script>
import axios from "axios";
import config from "../config.js"
    export default {
        name: "AddToBucketButton",
        props: {
            productId: Number
        },
        methods: {
            addProductToCart: function() {
                // eslint-disable-next-line no-console
                console.log(this.props.productId)
                axios.post(config.root + "bucket/addProduct", {
                    product: this.props.productId,
                    productName: null,
                    productPrice: null,
                    productQuantity: 1,
                    bucket: "user1" //ZMIEN TO NA MILOSC BOSKA CZLOWIEKU JAK DOSTANIESZ 
                                                 //TOKENY OD TEGO IMBECYLA
                })
            }
        }
    };
</script>

<style scoped>

</style>
