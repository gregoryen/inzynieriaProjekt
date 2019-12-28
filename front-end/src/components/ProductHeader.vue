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
                                                <img :src="this.productHeader._links.mainImage.href" width="300"
                                                     height="220">
                                            </b-col>
                                            <b-col cols="1"/>
                                            <b-col cols="5" class="nameFont" align-self="center">{{productHeader.name}}</b-col>
                                            <b-col cols="2" class="nameFont" align-self="end">
                                                <router-link :to="{name: 'product', params: {link: productHeader._links.self.href, baseurl: this.baseurl}}">
                                                    <b-button variant="success">Zobacz szczegóły</b-button>
                                                </router-link>
                                                Cena:
                                                <br/> {{productHeader.price}} PLN
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
            productHeader: Object,
            baseurl: String
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
