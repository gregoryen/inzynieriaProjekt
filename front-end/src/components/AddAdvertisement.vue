<template>
    <div>
        <b-container>
            <b-row align-h="center" class="mt-5">
                <b-col cols="10">
                    <b-card-text class="p-3">
                        <h3 class="mb-4">Dodaj Ogłoszenie</h3>
                        <b-form @submit="onSubmit" @reset="onReset" v-if="show" dark>
                            <b-form-group
                                    id="input-group-1"
                                    label="Tytuł:"
                                    label-for="input-1"
                            >
                                <b-form-input
                                        id="input-1"
                                        v-model="form.product.name"
                                        type="text"
                                        required
                                        placeholder="Podaj tytuł"
                                ></b-form-input>
                            </b-form-group>

                            <b-form-group
                                    id="mainImage"
                                    label="Wybierz główne zdjęcie Twojego ogłoszenia:"
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
                            <b-form-group id="description"
                                          label="Krótki opis: "
                                          label-for="input-1">
                                <b-form-textarea
                                        id="description"
                                        v-model="form.product.description"
                                        placeholder="Opis"
                                        rows="3"
                                        max-rows="12"
                                ></b-form-textarea>
                            </b-form-group>

                            <b-form-group
                                    id="input-group-2"
                                    label="Data publikacji:"
                                    label-for="input-2"
                            >
                                <b-form-input
                                        id="input-2"
                                        v-model="form.product.name"
                                        type="date"
                                        required
                                        placeholder="Podaj datę początku ogłoszenia"
                                ></b-form-input>
                            </b-form-group>
                                <b-form-group
                                    id="input-group-3"
                                    label="Data wygaśniecia:"
                                    label-for="input-3"
                            >
                                <b-form-input
                                        id="input-3"
                                        v-model="form.product.name"
                                        type="date"
                                        required
                                        placeholder="Podaj date końca ogłoszneia"
                                ></b-form-input>
                            </b-form-group>
                            <b-button type="submit" variant="primary">Dodaj</b-button>
                            <b-button type="reset" variant="danger">Zresetuj</b-button>
                        </b-form>
                    </b-card-text>
                </b-col>
            </b-row>
        </b-container>
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

    // import the styles
    import '@riophae/vue-treeselect/dist/vue-treeselect.css'
    import globalConfig from '../config'

    const PRODUCTS = '/advertisement';
    const UPLOAD_IMAGE = '/advImages/uploadImage';

    export default {
        name:'AddAdvertisement',
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
            selectCategoryModal() {
                this.$refs["selectCategory"].show();
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
            hideSelectCategoryModal() {
                this.$refs["selectCategory"].hide();
            },
            resetMainImage() {
                this.$refs['mainImage'].reset();
                this.mainImageFile = null;
                this.form.product.mainImage = null;
                this.supported.mainImageURL = null;
                this.status.mainImageSend = true;
            },
            onSubmit(evt) {
                evt.preventDefault();
                if (this.form.product.mainCategoryId) {
                    if ( his.status.mainImageSend === true) {
                            this.form.additionalImages = []
                        }
                        this.form.additionalImages.push(this.form.product.mainImage);
                        const config = {
                            headers: {
                                'content-type': 'application/json'
                            },
                        };
                        axios.post(globalConfig.root + PRODUCTS, this.form, config)
                            .then(() => {
                                this.showSuccessModal();
                            }).catch(() => {
                            this.showFailModal();
                        });
                } 
            },
            onReset() {
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

                axios.post(globalConfig.root + UPLOAD_IMAGE, formData, config)

                    .then(res => {
                        if (res.status === 200) {
                            this.form.product.mainImage = res.data.id;
                            this.status.mainImageSend = true;
                        }
                    })
            },
        },
    }
</script>

