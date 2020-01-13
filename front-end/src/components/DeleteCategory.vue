<template>
    <div>
        <b-form @submit="onSubmit">

            <b-form-group
                    id="mainCategory"
                    label="Wybierz kategorię do usunięcia: "
                    label-for="searcher">

                <treeselect
                        :options="treeCategories"
                        v-model="categoryId"
                        :searchable="false"
                        :show-count="true"
                        :disable-branch-nodes="true"
                        :default-expand-level="1"
                >
                </treeselect>
            </b-form-group>

            <b-modal ref="successDelete" hide-footer title="Usunięto kategorię">
                <div class="d-block text-center">
                    <h3>Kategoria została usunięta</h3>
                </div>
                <b-button class="mt-3" variant="success" block @click="hideSuccessModal">OK</b-button>
            </b-modal>

            <b-modal ref="failDelete" hide-footer title="NIE usunięto kategorii">
                <div class="d-block text-center">
                    <h3>Usunięcie kategorii nie powiodło się</h3>
                </div>
                <b-button class="mt-3" variant="danger" block @click="hideFailModal">OK</b-button>
            </b-modal>

            <b-modal ref="categoryNotSelected" hide-footer title="Nie wybrano kategorii">
                <div class="d-block text-center">
                    <h3>Wybierz kategorię</h3>
                </div>
                <b-button class="mt-3" variant="danger" block @click="hideSelectModal">OK</b-button>
            </b-modal>

            <b-button type="submit" variant="primary">Usuń</b-button>
        </b-form>
    </div>
</template>

<script>
    import axios from 'axios';
    import Treeselect from '@riophae/vue-treeselect'
    import '@riophae/vue-treeselect/dist/vue-treeselect.css'
    import globalConfig from '../config'

    const CATEGORIES_TREE = '/categories/tree';
    const CATEGORIES = '/categories';
    const CATEGORIES_WITHOUT_PRODUCTS = '/categories/withoutProducts';

    export default {
        components: {Treeselect},
        data() {
            return {
                categoryId: null,
                treeCategories: [],
            }
        },
        methods: {
            showSuccessModal() {
                this.$refs["successDelete"].show()
            },
            showFailModal() {
                this.$refs["failDelete"].show()
            },
            showSelectModal() {
                this.$refs["categoryNotSelected"].show()
            },
            hideSuccessModal() {
                this.$refs["successDelete"].hide();
                this.onReset();
            },
            hideFailModal() {
                this.$refs["failDelete"].hide();
                this.onReset();
            },
            hideSelectModal() {
                this.$refs["categoryNotSelected"].hide()
            },
            onSubmit(evt) {
                evt.preventDefault();
                if (this.categoryId !== null) {
                    const config = {
                        params: {
                            id: this.categoryId
                        },
                        headers: {
                            'content-type': 'application/json'
                        }
                    };
                    axios.delete(globalConfig.root + CATEGORIES, config)
                        .then(() => {
                                this.showSuccessModal()
                            }
                        ).catch(() => {
                        this.showFailModal()
                    });
                } else {
                    this.showSelectModal();
                }
            },
            onReset() {
                this.categoryId= null;
                this.treeCategories= [];
                this.getTree();
            },
            createBranch: function (oldBranch, categoriesWithoutProducts) {
                let newBranch = [];
                for (let e of oldBranch) {
                    let temp = {
                        id: e.category.id,
                        label: e.category.name,
                    };
                    let children = this.createBranch(e.children, categoriesWithoutProducts);
                    if (children.length > 0) {
                        temp.children = children;
                    } else if (categoriesWithoutProducts.indexOf(e.category.id) === -1) {
                        temp.isDisabled = true;
                    }
                    newBranch.push(temp);
                }
                return newBranch;
            },
            getTree() {
                const config = {
                    headers: {
                        'content-type': 'application/json'
                    }
                };
                let categoriesWithoutProducts = [];
                axios.get(globalConfig.root + CATEGORIES_WITHOUT_PRODUCTS, config)
                    .then(res => {
                            if (res.status === 200) {
                                for (let temp of res.data) {
                                    categoriesWithoutProducts.push(temp.id);
                                }
                                axios.get(globalConfig.root + CATEGORIES_TREE, config)
                                    .then(res => {
                                            if (res.status === 200) {
                                                this.treeCategories = this.createBranch(res.data, categoriesWithoutProducts);
                                            }
                                        }
                                    );
                            }
                        }
                    );
            }
        },
        created() {
            this.getTree();
        },
    }
</script>
