<template>
    <b-button variant="danger" v-on:click="addProductToCart()">Dodaj do koszyka</b-button>
</template>

<script>
import axios from "axios";
import config from "../config.js"
    export default {
        name: "AddToBucketButton",
        data: () => {
            return {
                loading: false,
            };
        },
        props: {
            productId: Number
        },
        methods: {
            addProductToCart: function() {
                let token = JSON.parse(localStorage.getItem('user')).jwtToken
                let bucketId = token.substring(0, token.length/4)

                axios.post(config.root + "/bucket/addProduct", {
                    product: this.$props.productId,
                    productName: null,
                    productPrice: null,
                    productQuantity: 0,
                    bucket: bucketId
                }).then( ()=> {
                    this.$router.push("/shoppingCart")
                })
            }
        }
    };
</script>

<style scoped>

</style>
