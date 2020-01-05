<template>
    <div id="productContainer">
        <p id="emptyProductsMessage" v-if="productsHeader === null || productsHeader.length === 0">BRAK PRODUKTÓW</p>
        <div v-else class="products" id="products">
            <div class="container">
                <h1 class="text-center p-5">Nasze Produkty</h1>
                <div class="row">
                    <div class="col-md-4" v-for="productHeader in productsHeader" v-bind:key="productHeader.name">
                        <ProductHeader :productHeader="productHeader" :baseurl="baseurl"/>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    import axios from "axios";
    import {bus} from "../main.js";
    import ProductHeader from "./ProductHeader.vue";

    const UPLOAD_ALL_ACTIVE_HEADER_PRODUCTS = "/products/search/findAllByActiveIsTrue?projection=header";

    export default {
        components: {ProductHeader},
        name: "Products",
        props: {
            baseurl: String
        },
        data: () => {
            return {
                productsHeader: null
            };
        },
        mounted() {
            bus.$on("products", products => {
                axios
                    .get(products)
                    .then(response => {
                        this.productsHeader = response.data._embedded.products;
                    });
                this.productsHeader = products;
            });
        },
        created() {
            if (this.productsHeader === null) {
                axios
                    .get(
                        this.baseurl + UPLOAD_ALL_ACTIVE_HEADER_PRODUCTS
                    )
                    .then(response => {
                        this.productsHeader = response.data._embedded.products;
                    });
            }
        },
        methods: {
            getProducts(event) {
                this.productsHeader = event;
            }
        }
    };
</script>

<style scoped>
    #productContainer {
        margin-top: 15px;
    }

    #emptyProductsMessage {
        text-align: center;
        font-size: 30;
    }

    ul {
        list-style-type: none;
    }
</style>
