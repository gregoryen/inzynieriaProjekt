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

        <b-modal ref="successCreate" id="successModal" title="Usunięto produkt">
            <p class="my-4">Produkt został usunięty</p>
        </b-modal>

        <b-modal ref="failCreate" id="failModal" title="NIE usunięto produktu">
            <p class="my-4">Produkt NIE został usunięty</p>
        </b-modal>
    </div>
</template>

<script>
    const PRODUCTS = '/products/';
    import axios from 'axios';

    export default {
        name: "DeleteProductHeader",
        props: {
            productHeader: Object,
            baseurl: String
        },
        methods: {
            showSuccessModal() {
                this.$refs["successCreate"].show()
            },
            showFailModal() {
                this.$refs["failCreate"].show()
            },
            delete() {
                const config = {
                    headers: {
                        'content-type': 'application/json'
                    }
                };
                axios.delete(this.baseurl + PRODUCTS + this.productHeader.id, config)
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
