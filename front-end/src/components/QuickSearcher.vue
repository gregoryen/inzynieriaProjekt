<template>
    <div>
        <div>
            <b-form-input  placeholder="Szukaj..." @input="this.search"></b-form-input>
        </div>

        <div id="productContainer">
            <div v-if="productsHeader !== null && productsHeader.length !== 0" class="products" id="products">
                <div class="container">
                    <div class="row">
                        <div class="col-md-4" v-for="productHeader in productsHeader" v-bind:key="productHeader.id">
                            <ProductHeader :productHeader="productHeader" />
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    import ProductHeader from "./ProductHeader.vue";
    import axios from 'axios';
    import globalConfig from '../config'

    const SEARCH = '/products/search/findAllByNameContainingIgnoreCaseAndActiveIsTrue?projection=header&name=';
    export default {
        components: {ProductHeader},
        data() {
            return {
                productsHeader: []
            }
        },
        methods: {
            search(text) {
                axios
                    .get(globalConfig.root + SEARCH + text)
                    .then(response => {
                        this.productsHeader = response.data._embedded.products;
                    });
            }
        }
    }
</script>

<style scoped>

</style>
