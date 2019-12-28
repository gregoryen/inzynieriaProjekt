<template>
    <li>
            <b-container>
                <b-row align-h="center" class="mt-5">
                    <b-col cols="10">
                        <div>
                            <b-card style="background-color: grey">
                                <b-card-body>
                                    <b-container class="bv-example-row bv-example-row-flex-cols">
                                        <b-row>
                                            <b-col cols="4" align-self="start">
                                                <img :src="this.imageUrl + this.product.mainImage" width="300"
                                                     height="220">
                                            </b-col>
                                            <b-col cols="1"/>
                                            <b-col cols="5" class="nameFont" align-self="center">{{product.name}}</b-col>
                                            <b-col cols="2" class="nameFont" align-self="end">
                                                <router-link :to="{name: 'product', params: {link: product._links.self.href}}">
                                                    <b-button variant="success">Zobacz szczegóły</b-button>
                                                </router-link>
                                                Cena:
                                                <br/>  {{product.price}} PLN
                                            </b-col>
                                        </b-row>
                                    </b-container>
                                </b-card-body>
                            </b-card>
                        </div>
                    </b-col>
                </b-row>
            </b-container>
    </li>
</template>

<script>

    export default {
        name: "ProductHeader",
        props: {
            product: Object
        },
        data: () => {
            return {
                imageUrl: "http://localhost:8080/images/downloadAdditionalImage?idImage=",
                image: null
            };
        }
    };
</script>
<style scoped>
    @import url('https://fonts.googleapis.com/css?family=Lato:400,700');
    #product {
        display: flex;

    }

    li > a {
        height: 60px;
    }

    div:first-child {
        padding-right: 20px;
    }

    .nameFont {
        font-family: 'Lato', sans-serif;
        font-size: 20px;
    }
</style>
