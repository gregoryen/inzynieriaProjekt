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
                                        v-model="form.advertisement.name"
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
                                        v-model="form.advertisement.description"
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
                                        v-model="form.advertisement.date"
                                        type="date"
                                        required
                                        placeholder="Podaj datę początku ogłoszenia"
                                ></b-form-input>
                            </b-form-group>
                            <b-button type="submit" variant="primary">Dodaj</b-button>
                            <b-button type="reset" variant="danger">Zresetuj</b-button>
                        </b-form>
                    </b-card-text>
                </b-col>
            </b-row>
        </b-container>
          <b-modal ref="successCreate" hide-footer title="Utworzono nowy produkt">
            <div class="d-block text-center">
                <h3>Nowe ogłoszenie zostało dodane</h3>
            </div>
            <b-button class="mt-3" variant="success" block @click="hideSuccessModal">OK</b-button>
        </b-modal>

        <b-modal ref="failCreate" hide-footer title="NIE stworzono nowego produktu">
            <div class="d-block text-center">
                <h3>Utworzenie nowego ogłoszenia nie udało się</h3>
            </div>
            <b-button class="mt-3" variant="danger" block @click="hideFailModal">OK</b-button>
        </b-modal>

        <b-modal ref="waitCreate" hide-footer title="Zdjęcia nie zostały jeszcze wysłane">
            <div class="d-block text-center">
                <h3>Proszę odczekać chwilę i spróbować zatwierdzić dodanie ogłoszenia ponownie</h3>
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

    const ADVERTISEMENTS = '/advertisement';
    const UPLOAD_IMAGE = '/advImages/uploadImage';

    export default {
        name:'AddAdvertisement',
        data() {
            return {

                form: {
                    advertisement: {
                        name: '',
                        description: '',
                        mainImage: null,
                        date:''
                    }
                },
                mainImageFile: null,
                show: true,
                supported: {
                    mainImageURL: null,
                },
                status: {
                    mainImageSend: true,
               
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
                this.form.advertisement.mainImage = null;
                this.supported.mainImageURL = null;
                this.status.mainImageSend = true;
            },
            onSubmit(evt) {
                evt.preventDefault();
              
                    if ( this.status.mainImageSend === true) {
                            this.form.additionalImages = []
                        }
                        
                        const config = {
                            headers: {
                                'content-type': 'application/json'
                            },
                        };
                      
                        axios.post(globalConfig.root + ADVERTISEMENTS, this.form.advertisement, config)
                            .then(() => {
                                this.showSuccessModal();
                            }).catch(() => {
                            this.showFailModal();
                        });
                
            },
            onReset() {
                this.form.advertisement.name = '';
                this.form.advertisement.description = '';
                //Reset form images
                this.resetMainImage();
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
                this.form.advertisement.mainImage = null;
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
                            this.form.advertisement.mainImage = res.data.id;
                            this.status.mainImageSend = true;
                        }
                    })
            },
        },
    }
</script>

