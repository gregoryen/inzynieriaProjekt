<template>
    <div id="productContainer">
        <p id="emptyProductsMessage" v-if="productsHeader === null || productsHeader.length === 0">BRAK PRODUKTÓW</p>
        <div v-else class="products" id="products">
            <div class="container">
                <h1 class="text-center p-5">Nasze Produkty</h1>
                <div class="row">
                    <div class="col-md-4" v-for="productHeader in productsHeader" v-bind:key="productHeader.name">
                        <ProductHeader :productHeader="productHeader" :baseurl="globalConfig.root"/>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    import ProductHeader from "./ProductHeader.vue";


    export default {
        components: {ProductHeader},
        name: "Products",
        computed: {
            productsHeader() {
                return this.$store.getters.productsHeader;
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
