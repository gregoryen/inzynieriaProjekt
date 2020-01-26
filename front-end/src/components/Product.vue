<template>
    <div>
        <div v-if="loading">
            <div class="product-container">
               <h1>{{product.name}}</h1>
                <div class="grid-container">
                    <div class="item1">
                        <b-carousel
                                id="carousel-1"
                                :interval="4000"
                                controls
                                indicators
                                img-width="400"
                                img-height="280"
                                style="text-shadow: 1px 1px 2px #333;"
                        >
                            <div v-if="Array.isArray(product._links.additionalImages)">
                                <b-carousel-slide v-for="(image, index) in product._links.additionalImages"
                                                  v-bind:key="index">
                                    <template v-slot:img>
                                        <img
                                                class="d-block"
                                                width="400"
                                                height="280"
                                                :src="image.href"
                                                alt="Zdjęcie produktu"
                                        >
                                    </template>
                                </b-carousel-slide>
                            </div>
                            <div v-else>
                                <b-carousel-slide>
                                    <template v-slot:img>
                                        <img
                                                class="d-block"
                                                width="400"
                                                height="280"
                                                :src="product._links.additionalImages.href"
                                                alt="Zdjęcie produktu"
                                        >
                                    </template>
                                </b-carousel-slide>
                            </div>


                        </b-carousel>
                    </div>
                    <div class="item2">
                        <div id="price">Cena: {{product.price}} PLN</div>
                        <p id="description">Opis: {{product.description}}</p>
                        <AddToBucketButton v-bind:productId="this.product.id"/>
                    </div>
                          <div id="categories" class="item3">
                            <ul>
                              <li v-for="category in tree" v-bind:key="category.id" @click="emitProducts(category.id)">
                                <router-link :to="{name: 'products', params: {id: product.id}}">{{category.name}}</router-link>
                              </li>
                            </ul>
                          </div>
                </div>
            </div>

        </div>
        <div v-else>
            <div class="text-center">
                <h4>Trwa ładowanie strony</h4>
            </div>
        </div>
            <OpinionsInProductPage v-bind:productId="product.id"/>
        </div>
</template>

<script>
   import AddToBucketButton from "../utils/AddToBucketButton"
   import axios from "axios";
   import globalConfig from '../config'
   import OpinionsInProductPage from "./OpinionsInProductPage";

    const UPLOAD_ACTIVE_HEADER_PRODUCTS_BY_CATEGORY_ID = "/products/search/findByMainCategoryIdAndActiveIsTrue?projection=header&active=true&categoryId=";
    export default {
        name: "Product",
        components: {
            AddToBucketButton,
            OpinionsInProductPage
        },
        props: {
            baseurl: String
        },
        data: () => {
            return {
                loading: false,
                product: null,
                tree: null
            };
        },
        async mounted() {
            await axios.get(this.$route.params.link).then(async response => {
                //this.productId = parseInt(response.data._links.product.href.split('/')[4].split('{')[0]) //Do wywalenia - czekam aż Seba mi doda id do obiektu z produktem
                this.product = response.data;
                // eslint-disable-next-line no-console
                console.log(this.product);
                await axios
                    .get(response.data._links.branch.href)
                    .then(response => {
                        this.tree = response.data;
                        this.loading = true;
                    });
            });
        },
        methods: {
            emitProducts(id) {
                this.$store.dispatch('productsHeader', globalConfig.root + UPLOAD_ACTIVE_HEADER_PRODUCTS_BY_CATEGORY_ID + id);
            }
        }
    };
</script>

<style scoped>
    .product-container {
        height: 100%;
        width: calc(100% - 40px);
        padding: 20px;
        background-color: #232323;
        color: white;
    }

    h1 {
        text-align: center;
    }

    .product-container {
        padding: 20px;
        width: calc(100% - 40px);
    }

    .grid-container {
        display: grid;
        grid-template-columns: auto auto auto;
        grid-gap: 10px;
        padding: 10px;
    }

    #price {
        font-size: 30px;
        text-align: left;
    }

    #description {
        text-align: left;
        font-size: 25px;
    }

    img {
        display: block;
        margin-left: auto;
        margin-right: auto;
        width: 350px;
        height: 350px;
    }

    .item2 {
        padding-left: 25px;
        grid-column-start: 2;
        grid-column-end: 4;
        grid-row-start: 1;
        grid-row-end: 4;
    }

    #categories {
        text-align: center;
        font-size: 20px;
    }

    #categories > ul {
        font-size: 30px;
        list-style: none;
    }
</style>
