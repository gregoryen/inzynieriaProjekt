<template>
    <div>
        <b-container>
            <b-row align-h="center" class="mt-5">
                <b-col cols="10">
                    <b-card-text class="p-3">
                        <h3 class="mb-4">Dodaj Produkt</h3>
                        <b-form @submit="onSubmit" @reset="onReset" v-if="show" dark>
                            <b-form-group
                                    id="input-group-1"
                                    label="Nazwa produktu:"
                                    label-for="input-1"
                            >
                                <b-form-input
                                        id="input-1"
                                        v-model="form.product.name"
                                        type="text"
                                        required
                                        placeholder="Podaj nazwe produktu"
                                ></b-form-input>
                            </b-form-group>

                            <b-form-group
                                    id="mainImage"
                                    label="Wybierz główne zdjęcie Twojego produktu:"
                                    label-for="mainImage"
                            >
                                <b-form-file
                                        id="mainImage"
                                        ref="mainImage"
                                        v-model="mainImageFile"
                                        :state="Boolean(mainImageFile)"
                                        placeholder="Przeciągnij i upuść plik tutaj"
                                        drop-placeholder="Upouść plik tutaj..."
                                        required
                                        accept="image/*"
                                        @change="onMainImagePicked"
                                ></b-form-file>
                                <div v-if="supported.mainImageURL" xs12 sm6 offset-sm3>
                                    <img :src="supported.mainImageURL" height="150">
                                </div>
                            </b-form-group>

                            <b-button type="reset" variant="danger" v-if="supported.mainImageURL"
                                      @click="resetMainImage" class="mr-2">Zresetuj zjdęcie główne
                            </b-button>

                            <b-form-group
                                    id="additionalImages"
                                    label="Wybierz dodatkowe zdjęcia Twojego produktu:"
                                    label-for="additionalImages"
                            >
                                <b-form-file
                                        id="additionalImages"
                                        ref="additionalImages"
                                        v-model="additionalImagesFiles"
                                        :state="Boolean(form.additionalImages)"
                                        accept="image/*"
                                        multiple
                                        placeholder="Przeciągnij i upuść pliki tutaj"
                                        drop-placeholder="Upouść pliki tutaj..."
                                        @change="onAdditionalImagesPicked"
                                ></b-form-file>
                                <b-container v-if="supported.additionalImagesURL" fluid class="p-4">
                                    <b-row>
                                        <b-col v-for="imageURL in supported.additionalImagesURL"
                                               v-bind:key="imageURL.id">
                                            <b-img thumbnail fluid :src="imageURL.image" :alt="imageURL.id"></b-img>
                                        </b-col>
                                    </b-row>
                                </b-container>
                            </b-form-group>

                            <b-button type="reset" variant="danger" v-if="supported.additionalImagesURL"
                                      @click="resetAdditionalImages" class="mr-2">Zresetuj zjdęcia dodatkowe
                            </b-button>

                            <b-form-group id="description"
                                          label="Krótki opis: "
                                          label-for="input-1">
                                <b-form-textarea
                                        id="description"
                                        v-model="form.product.description"
                                        placeholder="Opisz produkt"
                                        rows="3"
                                        max-rows="8"
                                ></b-form-textarea>
                            </b-form-group>

                            <b-form-group
                                    id="mainCategory"
                                    label="Wybierz kategorię główną: "
                                    label-for="searcher">
                                <b-form-input
                                        id="searcher"
                                        v-model="supported.searchMainCategories"
                                        type="text"
                                        placeholder="Wpisz szukaną kategorię"
                                ></b-form-input>
                                <div>
                                    <b-form-select v-model="form.product.mainCategoryId"
                                                   :options="filteredMainCategoriesId" :select-size="4"
                                                   required></b-form-select>
                                    <div v-if="form.product.mainCategoryId" class="mt-3">Wybrana kategoria: <strong>{{
                                        form.product.mainCategoryId }}</strong></div>
                                </div>
                            </b-form-group>

                            <b-form-group
                                    id="additionalCategory"
                                    label="Wybierz kategorię dodatkowe: "
                                    label-for="searcher">
                                <b-form-input
                                        id="searcher"
                                        v-model="supported.searchAdditionalCategories"
                                        type="text"
                                        placeholder="Wpisz szukaną kategorie"
                                ></b-form-input>
                                <div>
                                    <b-form-select v-model="form.product.categories"
                                                   :options="filteredAdditionalCategoriesId" :select-size="4"
                                                   multiple></b-form-select>
                                    <div class="mt-3">Wybrane kategorie: <strong>{{ form.product.categories }}</strong>
                                    </div>
                                </div>
                            </b-form-group>

                            <b-form-group id="price"
                                          label="Cena: "
                                          label-for="price">
                                <currency-input
                                        id="price"
                                        v-model="form.product.price"
                                        placeholder="Podaj cene"
                                        required
                                        currency="PLN"
                                        locale="pl"></currency-input>
                            </b-form-group>

                            <b-form-group
                                    id="reference"
                                    label="Kod REFERENCE:"
                                    label-for="reference"
                            >
                                <b-form-input
                                        id="reference"
                                        v-model="form.product.reference"
                                        type="text"
                                        required
                                        placeholder="Podaj kod REFERENCE produktu"
                                ></b-form-input>
                            </b-form-group>

                            <b-form-group
                                    id="isbn"
                                    label="Kod ISBN:"
                                    label-for="isbn"
                            >
                                <b-form-input
                                        id="isbn"
                                        v-model="form.product.isbn"
                                        type="text"
                                        required
                                        placeholder="Podaj kod ISBN produktu"
                                ></b-form-input>
                            </b-form-group>

                            <b-form-group
                                    id="ean13"
                                    label="Kod EAN13:"
                                    label-for="ean13"
                            >
                                <b-form-input
                                        id="ean13"
                                        v-model="form.product.ean13"
                                        type="text"
                                        required
                                        placeholder="Podaj kod EAN13 produktu"
                                ></b-form-input>
                            </b-form-group>


                            <b-form-group
                                    id="active"
                                    label="Czy produkt jest dostępny:"
                                    label-for="ean13"
                            >
                                <b-form-checkbox v-model="form.product.active" name="check-button" switch>
                                </b-form-checkbox>
                                <b v-if="form.product.active">Dostępny</b>
                                <b v-else>Niedostępny</b>
                            </b-form-group>

                            <b-button type="submit" variant="primary">Submit</b-button>
                            <b-button type="reset" variant="danger">Reset</b-button>
                        </b-form>
                    </b-card-text>
                </b-col>
            </b-row>
        </b-container>

        <b-card class="mt-3" header="Form Data Result">
            <pre class="m-0">{{ form }}</pre>
        </b-card>

        <b-modal ref="successCreate" id="successModal" title="Utworzono nowy produkt">
            <p class="my-4">Nowy produkt zostal dodany do sklepu</p>
        </b-modal>

        <b-modal ref="failCreate" id="failModal" title="NIE stworzono nowego produktu">
            <p class="my-4">Utworzenie nowego produktu nie udało się</p>
        </b-modal>

    </div>
</template>

<script>
    import axios from 'axios';

    const PRODUCTS = '/products';
    const UPLOAD_IMAGE = '/images/uploadImage';
    const UPLOAD_MULTIPLE_IMAGE = '/images/uploadMultipleImages';
    const CATEGORIES_WITH_ID = '/categories?projection=withId';
    const WAREHOUSE = '/stock_amounts/add_empty';

    export default {
        props: {
            baseUrl: String
        },
        data() {
            return {
                form: {
                    product: {
                        name: '',
                        description: '',
                        mainCategoryId: null,
                        categories: null,
                        price: '',
                        reference: '',
                        isbn: '',
                        ean13: '',
                        active: true,
                        mainImage: null
                    },
                    additionalImages: null
                },
                additionalImagesFiles: null,
                mainImageFile: null,
                show: true,
                supported: {
                    mainImageURL: null,
                    additionalImagesURL: null,
                    allCategories: [],
                    searchedMainCategories: [],
                    searchedAdditionalCategories: [],
                    searchMainCategories: '',
                    searchAdditionalCategories: '',
                },
                status: {
                    mainImageSend: true,
                    additionalImagesSend: true,
                    mainCategoryIdSelected: false,
                    additionalCategoriesIdSelected: false
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
            resetMainImage() {
                this.$refs['mainImage'].reset();
                this.mainImageFile = null;
                this.form.product.mainImage = null;
                this.supported.mainImageURL = null;
                this.status.mainImageSend = true;
            },
            resetAdditionalImages() {
                this.$refs['additionalImages'].reset();
                this.additionalImagesFiles = null;
                this.form.additionalImages = null;
                this.supported.additionalImagesURL = null;
                this.status.additionalImagesSend = true;
            },
            onSubmit(evt) {
                evt.preventDefault();
                if (this.status.additionalImagesSend === true && this.status.mainImageSend === true) {
                    if (this.form.additionalImages === null) {
                        this.form.additionalImages = []
                    }
                    this.form.additionalImages.push(this.form.product.mainImage);
                    if (this.form.product.categories === null) {
                        this.form.product.categories  = []
                    }
                    if (this.form.product.categories.indexOf(this.form.product.mainCategoryId)) {
                        this.form.product.categories.push(this.form.product.mainCategoryId);
                    }
                    const config = {
                        headers: {
                            'content-type': 'application/json'
                        },
                    };
                    axios.post(this.baseUrl + PRODUCTS, this.form, config)
                        .then(res => {
                            if (res.status === 200) {
                                axios.post(this.baseUrl + WAREHOUSE, {
                                    productId: res.id,
                                    measure: 'UNIT',
                                }, config)
                                this.showSuccessModal();
                            } else {
                                this.showFailModal();
                            }
                        });
                    this.onReset();
                }
                },
            onReset(evt) {
                evt.preventDefault();
                // Reset  form.products
                this.form.product.name = '';
                this.form.product.description = '';
                this.form.product.mainCategoryId = null;
                this.form.product.categories = null;
                this.form.product.price = '';
                this.form.product.reference = '';
                this.form.product.isbn = '';
                this.form.product.ean13 = '';
                this.form.product.active = true;

                //Reset form images
                this.resetMainImage();
                this.resetAdditionalImages();

                //Reset all view
                this.resetView()
            },
            resetView() {
                this.show = false;
                this.$nextTick(() => {
                    this.show = true
                })
            },
            onMainImagePicked(event) {
                this.status.mainImageSend = false;
                this.form.product.mainImage = null;
                const files = event.target.files;

                //Create imageURL to display selected image to user
                const fileReader = new FileReader();
                fileReader.addEventListener('load', () => {
                    this.supported.mainImageURL = fileReader.result
                });
                fileReader.readAsDataURL(files[0]);

                //Send image
                const formData = new FormData();
                formData.append('file', files[0]);
                const config = {
                    headers: {
                        'content-type': 'multipart/form-data'
                    }
                };
                axios.post(this.baseUrl + UPLOAD_IMAGE, formData, config)
                    .then(res => {
                        if (res.status === 200) {
                            this.form.product.mainImage = res.data.id;
                            this.status.mainImageSend = true;
                        }
                    })
            },

            onAdditionalImagesPicked(event) {
                this.status.additionalImagesSend = false;
                this.form.additionalImages = [];
                const files = event.target.files;
                this.supported.additionalImagesURL = [];

                for (let i = 0; i < files.length; i++) {
                    const fileReader = new FileReader();
                    fileReader.addEventListener('load', () => {
                        let temp = {
                            image: fileReader.result,
                            id: i
                        };
                        this.supported.additionalImagesURL.push(temp)
                    });
                    fileReader.readAsDataURL(files[i])
                }

                const formData = new FormData();
                for (let file of files) {
                    formData.append('files', file);
                }

                const config = {
                    headers: {
                        'content-type': 'multipart/form-data'
                    }
                };
                axios.post(this.baseUrl + UPLOAD_MULTIPLE_IMAGE, formData, config)
                    .then(res => {
                        if (res.status === 200) {
                            for (let temp of res.data) {
                                this.form.additionalImages.push(temp.id);
                            }
                            this.status.additionalImagesSend = true;
                        }
                    });
            }
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
        created() {
            const config = {
                headers: {
                    'content-type': 'application/json'
                }
            };
            axios.get(this.baseUrl + CATEGORIES_WITH_ID, config)
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
        }
    }
</script>
