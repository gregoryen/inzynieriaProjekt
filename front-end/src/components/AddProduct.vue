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
                                            <b-img thumbnail fluid :src="imageURL.image" :alt="imageURL.name"></b-img>
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
                                <treeselect
                                        :options="supported.treeCategories"
                                        v-model="form.product.mainCategoryId"
                                        :searchable="false"
                                        :show-count="true"
                                        :default-expand-level="1"
                                        placeholder="Kategoria główna"
                                >
                                </treeselect>
                            </b-form-group>

                            <b-form-group
                                    id="additionalCategory"
                                    label="Wybierz kategorię dodatkowe: "
                                    label-for="searcher">
                                <treeselect
                                        :options="supported.treeCategories"
                                        v-model="form.product.categories"
                                        :flat="true"
                                        :searchable="false"
                                        :show-count="true"
                                        :default-expand-level="1"
                                        :multiple="true"
                                        placeholder="Kategorie dodatkowe"
                                >
                                </treeselect>
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

                            <b-button type="submit" variant="primary">Dodaj</b-button>
                            <b-button type="reset" variant="danger">Zresetuj</b-button>
                        </b-form>
                    </b-card-text>
                </b-col>
            </b-row>
        </b-container>

        <b-card class="mt-3" header="Form Data Result">
            <pre class="m-0">{{ form }}</pre>
        </b-card>

        <b-modal ref="successCreate" hide-footer title="Utworzono nowy produkt">
            <div class="d-block text-center">
                <h3>Nowa produkt został dodany do sklepu</h3>
            </div>
            <b-button class="mt-3" variant="success" block @click="hideSuccessModal">OK</b-button>
        </b-modal>

        <b-modal ref="failCreate" hide-footer title="NIE stworzono nowego produktu">
            <div class="d-block text-center">
                <h3>Utworzenie nowego produktu nie udało się</h3>
            </div>
            <b-button class="mt-3" variant="danger" block @click="hideFailModal">OK</b-button>
        </b-modal>

        <b-modal ref="waitCreate" hide-footer title="Zdjęcia nie zostały jeszcze wysłane">
            <div class="d-block text-center">
                <h3>Proszę odczekać chwilę i spróbować zatwierdzić dodanie nowego produktu ponownie</h3>
            </div>
            <b-button class="mt-3" variant="danger" block @click="hideWaitModal">OK</b-button>
        </b-modal>

    </div>
</template>

<script>
    import axios from 'axios';
    import Treeselect from '@riophae/vue-treeselect'
    // import the styles
    import '@riophae/vue-treeselect/dist/vue-treeselect.css'

    const PRODUCTS = '/products';
    const UPLOAD_IMAGE = '/images/uploadImage';
    const UPLOAD_MULTIPLE_IMAGE = '/images/uploadMultipleImages';
    const WAREHOUSE = '/stock_amounts/add_empty';
    const CATEGORIES_TREE = '/categories/tree';

    export default {
        components: {Treeselect},
        props: {
            baseurl: String
        },
        data() {
            return {
                form: {
                    product: {
                        name: '',
                        description: '',
                        mainCategoryId: null,
                        categories: null,
                        price: null,
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
                    treeCategories: [],
                    mainImageURL: null,
                    additionalImagesURL: null,
                },
                status: {
                    mainImageSend: true,
                    additionalImagesSend: true,
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
            waitModal() {
                this.$refs["waitCreate"].show();
            },
            hideSuccessModal() {
                this.$refs["successCreate"].hide();
                this.onReset();
            },
            hideFailModal() {
                this.$refs["failCreate"].hide();
                this.onReset();
            },
            hideWaitModal() {
                this.$refs["waitCreate"].hide();
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
                        this.form.product.categories = []
                    }
                    if (this.form.product.categories.indexOf(this.form.product.mainCategoryId)) {
                        this.form.product.categories.push(this.form.product.mainCategoryId);
                    }
                    const config = {
                        headers: {
                            'content-type': 'application/json'
                        },
                    };
                    axios.post(this.baseurl + PRODUCTS, this.form, config)
                        .then((res) => {
                            axios.post(this.baseurl + WAREHOUSE, {
                                productId: res.id,
                                measure: 'UNIT',
                            }, config);
                            this.showSuccessModal();
                        }).catch(() => {
                        this.showFailModal();
                    });
                } else {
                    this.waitModal();
                }
                },
            onReset() {
                // Reset  form.products
                this.form.product.name = '';
                this.form.product.description = '';
                this.form.product.mainCategoryId = null;
                this.form.product.categories = null;
                this.form.product.price = null;
                this.form.product.reference = '';
                this.form.product.isbn = '';
                this.form.product.ean13 = '';
                this.form.product.active = true;

                //Reset form images
                this.resetMainImage();
                this.resetAdditionalImages();

                this.getTree();

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
                axios.post(this.baseurl + UPLOAD_IMAGE, formData, config)
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
                axios.post(this.baseurl + UPLOAD_MULTIPLE_IMAGE, formData, config)
                    .then(res => {
                        if (res.status === 200) {
                            for (let temp of res.data) {
                                this.form.additionalImages.push(temp.id);
                            }
                            this.status.additionalImagesSend = true;
                        }
                    });
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
            getTree() {
                const config = {
                    headers: {
                        'content-type': 'application/json'
                    }
                };
                axios.get(this.baseurl + CATEGORIES_TREE, config)
                    .then(res => {
                            if (res.status === 200) {
                                this.supported.treeCategories = this.createBranch(res.data);
                            }
                        }
                    );
            }
        },
        created() {
            this.getTree();
        }
    }
</script>
