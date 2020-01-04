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

            <b-modal ref="successCreate" id="successModal" title="Usunięto kategorię">
                <p class="my-4">Kategoria została usunięta</p>
            </b-modal>

            <b-modal ref="failCreate" id="failModal" title="NIE usunięto kategorii">
                <p class="my-4">Usunięcie kategorii nie powiodło się</p>
            </b-modal>

            <b-modal ref="categoryNotSelected" id="failModal" title="Nie wybrano kategorii">
                <p class="my-4">Wybierz kategorię</p>
            </b-modal>

            <b-button type="submit" variant="primary">Usuń</b-button>
        </b-form>
    </div>
</template>

<script>
    import axios from 'axios';
    import Treeselect from '@riophae/vue-treeselect'
    import '@riophae/vue-treeselect/dist/vue-treeselect.css'

    const CATEGORIES_TREE = '/categories/tree';
    const CATEGORIES = '/categories';
    const CATEGORIES_WITHOUT_PRODUCTS = '/categories/withoutProducts';

    export default {
        components: {Treeselect},
        props: {
            baseurl: String
        },
        data() {
            return {
                categoryId: null,
                treeCategories: [],
            }
        },
        methods: {
            showSuccessModal() {
                this.$refs["successCreate"].show()
            },
            showFailModal() {
                this.$refs["failCreate"].show()
            },
            showSelectModal() {
                this.$refs["categoryNotSelected"].show()
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
                    axios.delete(this.baseurl + CATEGORIES, config)
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
            }

        },
        created() {
            const config = {
                headers: {
                    'content-type': 'application/json'
                }
            };
            let categoriesWithoutProducts = [];
            axios.get(this.baseurl + CATEGORIES_WITHOUT_PRODUCTS, config)
                .then(res => {
                        if (res.status === 200) {
                            for (let temp of res.data) {
                                categoriesWithoutProducts.push(temp.id);
                            }
                            axios.get(this.baseurl + CATEGORIES_TREE, config)
                                .then(res => {
                                        if (res.status === 200) {
                                            this.treeCategories = this.createBranch(res.data, categoriesWithoutProducts);
                                        }
                                    }
                                );
                        }
                    }
                );

        },
    }
</script>
