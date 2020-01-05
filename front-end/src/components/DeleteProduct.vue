<template>
    <div>
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
            <div v-if="productsHeader !== null && productsHeader.length !== 0" class="products" id="products">
                <div class="container">
                    <div class="row">
                        <div class="col-md-4" v-for="productHeader in productsHeader" v-bind:key="productHeader.name">
                            <DeleteProductHeader :productHeader="productHeader" :baseurl="baseurl"/>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    import axios from 'axios';
    // import the component
    import Treeselect from '@riophae/vue-treeselect'
    // import the styles
    import '@riophae/vue-treeselect/dist/vue-treeselect.css'
    import DeleteProductHeader from "./DeleteProductHeader.vue";

    const CATEGORIES_TREE = '/categories/tree';
    const UPLOAD_ACTIVE_HEADER_PRODUCTS_BY_CATEGORY_ID = "/products/search/findByMainCategoryIdAndActiveIsTrue?projection=header&active=true&categoryId=";
    export default {
        components: {Treeselect, DeleteProductHeader},
        props: {
            baseurl: String
        },
        data() {
            return {
                categoryId: null,
                productsHeader: [],
                treeCategories: [],
            }
        },
        methods: {
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
