<template>
    <div class="card product-item">
        <img :src="this.productHeader._links.mainImage.href" width="250px" height="250px"
             class="card-img-top" alt="Zdjęcie produktu">

        <div class="card-body">
            <div class="d-flex justify-content-between">
                <h5 class="card-title">{{productHeader.name}}</h5>
                <h6 class="card-price">{{productHeader.price}} PLN</h6>
            </div>

            <router-link :to="{name: 'product', params: {link: productHeader._links.self.href, baseurl: this.baseurl}}">
                <b-button variant="success">Zobacz szczegóły</b-button>
            </router-link>
        </div>

    </div>
</template>

<script>

    export default {
        name: "ProductHeader",
        props: {
            productHeader: Object,
            baseurl: String
        },
    };

</script>
<style scoped>
    .card {
        margin-bottom: 1rem;
    }
</style>
