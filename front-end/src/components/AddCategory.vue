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
                    id="mainCategory"
                    label="Wybierz kategorię nadrzędną: "
                    label-for="searcher">
                <b-form-input
                        id="searcher"
                        v-model="supported.searchMainCategories"
                        type="text"
                        placeholder="Wpisz szukaną kategorię"
                ></b-form-input>
                <div>
                    <b-form-select v-model="form.parentId"
                                   :options="filteredMainCategoriesId" :select-size="4"
                                   required
                                   @change="getChildren"></b-form-select>
                </div>
            </b-form-group>

            <b-form-group
                    v-if="supported.childrenCategories.length"
                    id="positionCategory"
                    label="Wybierz pozycje nowej kategorii: "
                    label-for="searcher">
            <b-form-select  :options="supported.positions"
                           :value="supported.selectedPosition" @change="changeDisplay"></b-form-select>
            </b-form-group>

            <b-list-group>
                <b-list-group-item v-for="category in supported.childrenCategories" :key="category.value" :variant="category.variant">{{category.text}}</b-list-group-item>
            </b-list-group>



            <b-button type="submit" variant="primary">Submit</b-button>
            <b-button type="reset" variant="danger">Reset</b-button>
        </b-form>
        <b-card class="mt-3" header="Form Data Result">
            <pre class="m-0">{{ form }}</pre>
        </b-card>
    </div>
</template>

<script>
    import axios from 'axios';

/*    const PRODUCTS = '/products';
    const UPLOAD_IMAGE = '/images/uploadImage';
    const UPLOAD_MULTIPLE_IMAGE = '/images/uploadMultipleImages';*/
    const CATEGORIES_WITH_ID = '/categories?projection=withId';
    const CATEGORIES_CHILDREN = '/categories/children';
/*    const WAREHOUSE = '/stock_amounts/add_empty';*/

    export default {
        props: {
            baseurl: String
        },
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
                    allCategories: [],
                    childrenCategories: [],
                    selectedPosition: 1,
                    positions: []
                },
                newCategory: {
                    value: 0,
                    text: "Nowa Kategoria",
                    variant: "primary"
                }
            }
        },
        methods: {
            onSubmit(evt) {
                evt.preventDefault()
                alert(JSON.stringify(this.form))
            },
            onReset(evt) {
                evt.preventDefault()
                // Reset our form values
                this.form.name = ''
                this.form.description = ''
                this.form.parentId = null
                this.form.previousCategoryId = null
                // Trick to reset/clear native browser form validation state
                this.show = false
                this.$nextTick(() => {
                    this.show = true
                })
            },
            changeDisplay: function (val) {
                let oldValue = this.supported.selectedPosition;
                let newValue = val;
                this.supported.selectedPosition = newValue;
                if(oldValue!=null){
                    this.supported.childrenCategories.splice(oldValue-1, 1);
                }
                this.supported.childrenCategories.splice(newValue-1, 0,this.newCategory);
            },
            getChildren() {
                this.supported.childrenCategories=[];
                this.supported.positions=[];
                const config = {
                    params: {
                        parentId: this.form.parentId
                    },
                    headers: {
                        'content-type': 'application/json'
                    }
                };
                axios.get(this.baseurl + CATEGORIES_CHILDREN, config)
                    .then(res => {
                            if (res.status === 200) {
                                for (let i=0;i<res.data.length;i++) {
                                    let item = {
                                        value: res.data[i].id,
                                        text: res.data[i].name,
                                        variant: null
                                    };
                                    this.supported.positions.push(i+1);
                                    this.supported.childrenCategories.push(item);
                                }
                                this.supported.positions.push(this.supported.positions.length+1);
                                this.supported.childrenCategories.splice(this.supported.selectedPosition-1, 0,this.newCategory);
                                this.supported.selectedPosition=1;
                            }
                        }
                    );
            },
        },
        created() {
            const config = {
                headers: {
                    'content-type': 'application/json'
                }
            };
            axios.get(this.baseurl + CATEGORIES_WITH_ID, config)
                .then(res => {
                        if (res.status === 200) {
                            for (let temp of res.data._embedded.categories) {
                                let item = {
                                    value: temp.id,
                                    text: temp.name
                                };
                                this.supported.allCategories.push(item);
                            }
                        }
                    }
                );
        },
        computed: {
            filteredMainCategoriesId: function () {
                return this.supported.allCategories.filter((category) => {
                    return category.text.match(this.supported.searchMainCategories);
                })
            },
            filteredAdditionalCategoriesId: function () {
                return this.supported.allCategories.filter((category) => {
                    return category.text.match(this.supported.searchAdditionalCategories);
                })
            }
        },
    }
</script>
