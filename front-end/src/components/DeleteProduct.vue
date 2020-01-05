<template>
    <div>
        <b-form @submit="onSubmit" @reset="onReset" v-if="show">

            <b-form-group
                    id="mainCategory"
                    label="Wybierz kategorię z której chcesz usunąć produkty: "
                    label-for="searcher">

                <treeselect
                        :options="treeCategories"
                        v-model="categoryId"
                        :searchable="false"
                        :show-count="true"
                        @input="getProducts"
                        :default-expand-level="1"
                >
                </treeselect>
            </b-form-group>

            <div id="productContainer">
                <p id="emptyProductsMessage" v-if="productsHeader === null || productsHeader.length === 0">BRAK PRODUKTÓW</p>
                <ul v-else>
                    <ProductHeader v-for="productHeader in productsHeader" v-bind:key="productHeader.name" :productHeader="productHeader" :baseurl="baseurl"></ProductHeader>
                </ul>
            </div>

            <b-modal ref="successCreate" id="successModal" title="Usunięto kategorię">
                <p class="my-4">{{respondMessage}}</p>
            </b-modal>

            <b-modal ref="failCreate" id="failModal" title="NIE usunięto kategorii">
                <p class="my-4">{{respondMessage}}</p>
            </b-modal>

            <b-button type="submit" variant="primary">Submit</b-button>
            <b-button type="reset" variant="danger">Reset</b-button>
        </b-form>
        <b-card class="mt-3" header="Form Data Result">
            <pre class="m-0">{{ categoryId }}</pre>
        </b-card>
    </div>
</template>

<script>
    import axios from 'axios';
    // import the component
    import Treeselect from '@riophae/vue-treeselect'
    // import the styles
    import '@riophae/vue-treeselect/dist/vue-treeselect.css'
    import ProductHeader from "./ProductHeader.vue";


    const CATEGORIES_TREE = '/categories/tree';
    const UPLOAD_ACTIVE_HEADER_PRODUCTS_BY_CATEGORY_ID = "/products/search/findByMainCategoryIdAndActiveIsTrue?projection=header&active=true&categoryId=";
    const CATEGORIES = '/categories';
    export default {
        components: {Treeselect, ProductHeader},
        props: {
            baseurl: String
        },
        data() {
            return {
                categoryId: null,
                productsHeader: [],
                treeCategories: [],
                show: true,
                respondMessage: "",
                es: null
            }
        },
        methods: {
            showSuccessModal() {
                this.$refs["successCreate"].show()
            },
            showFailModal() {
                this.$refs["failCreate"].show()
            },
            onSubmit(evt) {
                evt.preventDefault()
                const config = {
                    params: {
                        id: this.categoryId
                    },
                    headers: {
                        'content-type': 'application/json'
                    }
                };
                axios.delete(this.baseurl + CATEGORIES, config)
                    .then(res => {
                            // eslint-disable-next-line no-console
                            console.log(res);
                            this.respondMessage = res.data.message;
                            this.showSuccessModal();
                        }
                    ).catch(res => {
                    // eslint-disable-next-line no-console
                    console.log(res);
                    this.es = res;
                    this.showFailModal();
                });
            },
            onReset(evt) {
                evt.preventDefault();
                this.show = false;
                this.$nextTick(() => {
                    this.show = true
                })
            },
            createBranch: function (oldBranch) {
                let newBranch = [];
                for (let e of oldBranch) {
                    let temp = {
                        id: e.category.id,
                        label: e.category.name,
                    };
                    let children = this.createBranch(e.children);
                    if (children.length > 0) {
                        temp.children = children;
                    }
                    newBranch.push(temp);
                }
                return newBranch;
            },
            getProducts: function () {
                axios
                    .get(
                        this.baseurl + UPLOAD_ACTIVE_HEADER_PRODUCTS_BY_CATEGORY_ID + this.categoryId
                    )
                    .then(response => {
                        this.productsHeader = response.data._embedded.products;
                    });

            }
        },
        created() {
            const config = {
                headers: {
                    'content-type': 'application/json'
                }
            };
            axios.get(this.baseurl + CATEGORIES_TREE, config)
                .then(res => {
                        if (res.status === 200) {
                            this.treeCategories = this.createBranch(res.data);
                        }
                    }
                );
        },
    }
</script>
