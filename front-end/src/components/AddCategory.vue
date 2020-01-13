<template>
    <div>
        <b-form @submit="onSubmit" @reset="onReset" v-if="show">
            <b-form-group
                    id="input-group-1"
                    label="Nazwa kategorii:"
                    label-for="text"
            >
                <b-form-input
                        id="input-1"
                        v-model="form.name"
                        type="text"
                        required
                        placeholder="Podaj nazwe kategorii"
                ></b-form-input>
            </b-form-group>

            <b-form-group id="description"
                          label="Krótki opis: "
                          label-for="input-1">
                <b-form-textarea
                        id="description"
                        v-model="form.description"
                        placeholder="Opisz kategorię"
                        rows="3"
                        max-rows="8"
                ></b-form-textarea>
            </b-form-group>

            <b-form-group
                    id="addMainCategory"
                    label="Czy chcesz dodać kategorię główną:"
                    label-for="ean13"
            >
                <b-form-checkbox v-model="supported.mainCategory" name="check-button" @change="addMainCategory" switch>
                </b-form-checkbox>
                <b v-if="supported.mainCategory">Tak</b>
                <b v-else>Nie</b>
            </b-form-group>

            <b-form-group
                    id="mainCategory"
                    label="Wybierz kategorię nadrzędną: "
                    label-for="searcher"
                    v-if="!supported.mainCategory">

                <treeselect
                        :options="this.supported.treeCategories"
                        v-model="form.parentId"
                        :searchable="false"
                        :show-count="true"
                        @input="getChildren"
                        :default-expand-level="1"
                >
                </treeselect>
            </b-form-group>

            <b-form-group
                    v-if="supported.childrenCategories.length"
                    id="positionCategory"
                    label="Wybierz pozycję nowej kategorii: "
                    label-for="searcher">
                <b-form-select :options="supported.positions"
                               :value="supported.selectedPosition" @change="changeDisplay"></b-form-select>
            </b-form-group>

            <b-form-group
                    id="viewPositionCategory"
                    label="Animacja przedstawia pozycję nowej kategorii na tle już isniejących: "
                    label-for="searcher"
                    v-if="supported.childrenCategories.length">
            <b-list-group>
                <b-list-group-item v-for="category in supported.childrenCategories" :key="category.value"
                                   :variant="category.variant">{{category.text}}
                </b-list-group-item>
            </b-list-group>
            </b-form-group>

            <b-button type="submit" variant="primary">Dodaj</b-button>
            <b-button type="reset" variant="danger">Zresetuj</b-button>
        </b-form>


        <b-modal ref="successCreate" hide-footer title="Utworzono nową kategorię">
            <div class="d-block text-center">
                <h3>Nowa kategoria zostala dodany do sklepu</h3>
            </div>
            <b-button class="mt-3" variant="success" block @click="hideSuccessModal">OK</b-button>
        </b-modal>

        <b-modal ref="failCreate" hide-footer title="NIE stworzono nowej kategorii">
            <div class="d-block text-center">
                <h3>Utworzenie nowej kategorii nie udało się</h3>
            </div>
            <b-button class="mt-3" variant="danger" block @click="hideFailModal">OK</b-button>
        </b-modal>

        <b-modal ref="selectCategory" hide-footer title="Wybierz kategorię nadrzędną">
            <div class="d-block text-center">
                <h3>Proszę wybrać kategorię nadrzędną</h3>
            </div>
            <b-button class="mt-3" variant="danger" block @click="hideSelectCategoryModal">OK</b-button>
        </b-modal>

<!--        <b-card class="mt-3" header="Form Data Result">
            <pre class="m-0">{{ form }}</pre>
        </b-card>-->
    </div>
</template>

<script>
    import axios from 'axios';
    // import the component
    import Treeselect from '@riophae/vue-treeselect'
    // import the styles
    import '@riophae/vue-treeselect/dist/vue-treeselect.css'
    import globalConfig from '../config'

    const CATEGORIES_CHILDREN = '/categories/children';
    const CATEGORIES_TREE = '/categories/tree';
    const CATEGORIES_MAIN = '/categories/mainCategories';
    const ADD_CATEGORIES = '/categories';

    export default {
        // register the component
        components: {Treeselect},
        data() {
            return {
                form: {
                    name: '',
                    description: '',
                    parentId: null,
                    previousCategoryId: null
                },
                show: true,
                supported: {
                    treeCategories: [],
                    childrenCategories: [],
                    selectedPosition: 1,
                    positions: [],
                    mainCategory: false
                },
                newCategory: {
                    value: 0,
                    text: "Nowa Kategoria",
                    variant: "primary"
                }
            }
        },
        methods: {
            showSuccessModal() {
                this.$refs["successCreate"].show()
            },
            showFailModal() {
                this.$refs["failCreate"].show()
            },
            showSelectCategoryModal() {
                this.$refs["selectCategory"].show()
            },
            hideSuccessModal() {
                this.$refs["successCreate"].hide();
                this.onReset();
            },
            hideFailModal() {
                this.$refs["failCreate"].hide();
                this.onReset();
            },
            hideSelectCategoryModal() {
                this.$refs["selectCategory"].hide();
            },
            onSubmit(evt) {
                evt.preventDefault();
                if (this.form.parentId || this.supported.mainCategory) {
                    const config = {
                        headers: {
                            'content-type': 'application/json'
                        },
                    };
                    axios.post(globalConfig.root + ADD_CATEGORIES, this.form, config)
                        .then(() => {
                            this.showSuccessModal()
                        }).catch(() => {
                        this.showFailModal()
                    });
                } else {
                    this.showSelectCategoryModal();
                }

            },
            onReset() {
                // Reset our form values
                this.form.name = '';
                this.form.description = '';
                this.form.parentId = null;
                this.form.previousCategoryId = null;

                this.supported.childrenCategories = [];
                this.supported.selectedPosition = 1;
                this.supported.positions = [];
                this.supported.mainCategory = false;

                this.getCategories();

                // Trick to reset/clear native browser form validation state
                this.show = false;
                this.$nextTick(() => {
                    this.show = true
                });
            },
            changeDisplay: function (val) {
                let oldValue = this.supported.selectedPosition;
                let newValue = val;
                this.supported.selectedPosition = newValue;
                if (oldValue != null) {
                    this.supported.childrenCategories.splice(oldValue - 1, 1);
                }
                this.supported.childrenCategories.splice(newValue - 1, 0, this.newCategory);
                if (newValue === 1) {
                    this.form.previousCategoryId = null;
                } else {
                    this.form.previousCategoryId = this.supported.childrenCategories[newValue - 2].value;
                }
            },
            getChildren() {
                this.supported.childrenCategories = [];
                this.supported.positions = [];
                const config = {
                    params: {
                        parentId: this.form.parentId
                    },
                    headers: {
                        'content-type': 'application/json'
                    }
                };
                axios.get(globalConfig.root + CATEGORIES_CHILDREN, config)
                    .then(res => {
                            if (res.status === 200) {
                                for (let i = 0; i < res.data.length; i++) {
                                    let item = {
                                        value: res.data[i].id,
                                        text: res.data[i].name,
                                        variant: null
                                    };
                                    this.supported.positions.push(i + 1);
                                    this.supported.childrenCategories.push(item);
                                }
                                this.supported.positions.push(this.supported.positions.length + 1);
                                this.supported.childrenCategories.splice(this.supported.selectedPosition - 1, 0, this.newCategory);
                                this.supported.selectedPosition = 1;
                            }
                        }
                    );
            },
            getMainCategories() {
                this.supported.childrenCategories = [];
                this.supported.positions = [];
                const config = {
                    headers: {
                        'content-type': 'application/json'
                    }
                };
                axios.get(globalConfig.root + CATEGORIES_MAIN, config)
                    .then(res => {
                            if (res.status === 200) {
                                for (let i = 0; i < res.data.length; i++) {
                                    let item = {
                                        value: res.data[i].id,
                                        text: res.data[i].name,
                                        variant: null
                                    };
                                    this.supported.positions.push(i + 1);
                                    this.supported.childrenCategories.push(item);
                                }
                                this.supported.positions.push(this.supported.positions.length + 1);
                                this.supported.childrenCategories.splice(this.supported.selectedPosition - 1, 0, this.newCategory);
                                this.supported.selectedPosition = 1;
                            }
                        }
                    );
            },
            addMainCategory(value) {
                if (value) {
                    this.form.parentId = null;
                    this.supported.mainCategory = true;
                    this.getMainCategories();
                } else {
                    this.supported.mainCategory = false;
                    this.supported.childrenCategories = [];
                    this.supported.positions = [];
                }
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
            getCategories() {
                const config = {
                    headers: {
                        'content-type': 'application/json'
                    }
                };

                axios.get(globalConfig.root + CATEGORIES_TREE, config)
                    .then(res => {
                            if (res.status === 200) {
                                this.supported.treeCategories = this.createBranch(res.data);
                            }
                        }
                    );
            }
        },
        mounted() {
            this.getCategories();
        },
    }
</script>
