<template>
    <div class="card product-item">
        <img :src="this.productHeader._links.mainImage.href" width="250px" height="250px"
             class="card-img-top" alt="Zdjęcie produktu">

        <div class="card-body">
            <div class="d-flex justify-content-between">
                <h5 class="card-title">{{productHeader.name}}</h5>
                <h6 class="card-price">{{productHeader.price}} PLN</h6>
            </div>

            <b-button variant="danger"  v-on:click="this.delete" >Usuń</b-button>
        </div>

        <b-modal ref="successDelete" hide-footer title="Usunięto produkt">
            <div class="d-block text-center">
                <h3>Produkt został usunięty</h3>
            </div>
            <b-button class="mt-3" variant="success" block @click="hideSuccessModal">OK</b-button>
        </b-modal>

        <b-modal ref="failDelete" hide-footer title="NIE usunięto produktu">
            <div class="d-block text-center">
                <h3>Produkt NIE został usunięty</h3>
            </div>
            <b-button class="mt-3" variant="danger" block @click="hideFailModal">OK</b-button>
        </b-modal>
    </div>
</template>

<script>
    const PRODUCTS = '/products/';
    import axios from 'axios';
    import { bus } from '../main'
    import globalConfig from '../config'

    export default {
        name: "DeleteProductHeader",
        props: {
            productHeader: Object,
        },
        methods: {
            showSuccessModal() {
                this.$refs["successDelete"].show()
            },
            showFailModal() {
                this.$refs["failDelete"].show()
            },
            hideSuccessModal() {
                this.$refs["successDelete"].hide();
                this.emitDeleteProduct();
            },
            hideFailModal() {
                this.$refs["failDelete"].hide();
            },
            emitDeleteProduct () {
                bus.$emit('deleteProduct');
            },
            delete() {
                const config = {
                    headers: {
                        'content-type': 'application/json'
                    }
                };
                axios.delete(globalConfig.root + PRODUCTS + this.productHeader.id, config)
                    .then(() => {
                            this.showSuccessModal()
                        }
                    ).catch(() => {
                    this.showFailModal()
                });
            }
        }
    };
</script>
<style scoped>
    .card {
        margin-bottom: 1rem;
    }
</style>
