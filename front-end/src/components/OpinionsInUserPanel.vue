<template>
    <div class="app-container">

        <div v-show="showEdit" id="opinion-edit" class="container">
            <h1>Edycja opinii</h1>
            <table class="table table-striped">
                <thead class="thead-dark">
                <tr>
                    <th>Komentarz</th>
                    <th>Ilosc gwiazdek</th>
                </tr>
                </thead>
                <tbody>
                <tr >
                    <td>
                        <input class="form-control" type='text' v-model="editedOpinion.description">
                    </td>
                    <td>
                        <star-rating v-model="editedOpinion.starsNumber" ></star-rating>
                    </td>

                    <td><button v-on:click="updateOpinion()">Zapisz</button></td>
                    <td><button v-on:click="hideSection()">Cofnij</button></td>
                </tr>
                </tbody>
            </table>
        </div>

    <div v-show="showTable" id="opinion-form" class="container">

        <h1>Menadżer opinii</h1>
        <table class="table table-striped">
            <thead class="thead-dark">
            <tr>
                <th>Id Produktu</th>
                <th>Komentarz</th>
                <th>Ocena</th>
                <th>Usuń</th>
                <th>Edytuj</th>
            </tr>
            </thead>
            <tbody v-if="opinions.length > 0">
            <tr v-for="index in Math.min(opinions.length, opinions.length)" v-bind:key="index">
                <td>
                    {{opinions[index-1].productId}}
                </td>
                <td>
                    {{opinions[index-1].description}}
                </td>
                <td>
                    <star-rating :rating=opinions[index-1].starsNumber :read-only="true" :increment="0.01"></star-rating>
                </td>
                <td><button class="btn btn-secondary" v-on:click="deleteOpinion(opinions[index-1].id)">Usuń</button></td>
                <td><button class="btn btn-secondary" v-on:click="() => {
                editOpinion(opinions[index-1]);
                showSection()
                }">Edytuj</button></td>
            </tr>
            </tbody>
        </table>
    </div>
    </div>
</template>
<script>
    import axios from 'axios';
    import config from '../config.js'
    const API_URL = config.root;
    export default {
        created() {
            this.getAllOpinions();
        },
        star: {
            rating: "No Rating Selected",
            currentRating: "No Rating",
            currentSelectedRating: "No Current Rating",
            boundRating: 3,
        },
        data: function () {
            return {
                listLoading: true,
                editedOpinion: {
                    productId: null,
                    clientEmail: null,
                    starsNumber: null,
                    description: '',
                    likesNumber: null,
                    dislikesNumber: null
                },
                showEdit: false,
                showTable: true,
                opinions: [],
                opinion: {
                    productId: null,
                    clientEmail: null,
                    starsNumber: null,
                    description: '',
                    likesNumber: null,
                    dislikesNumber: null
                },
                clicked: false,
                disabled: true,
            };
        },
        methods: {
            getAllOpinions: function () {
                axios.get(API_URL + '/opinions/clientEmail/' +  this.$store.state.auth.user.email, {
                    "Access-Control-Allow-Origin": "*",
                    "Content-Type": "application/json"
                }).then((response) => {
                    this.opinions = response.data;
                    // eslint-disable-next-line no-console
                    console.log(response)
                });
            },
            deleteOpinion: function(id) {
                axios.delete(API_URL + '/opinions/'+id);
                this.getAllOpinions();
            },
            editOpinion: function(opinion)  {
                this.editedOpinion = Object.assign({}, opinion);
            },
            showSection() {
                this.showEdit = true;
                this.showTable = false;
            },
            hideSection() {
                this.showEdit = false;
                this.showTable = true;
            },
            updateOpinion: function () {
                axios.post(API_URL + '/opinions/update', this.editedOpinion,
                    {
                    "Access-Control-Allow-Origin": "*",
                    "Content-Type": "application/json"
                })
                this.$router.go();
            },
        },
    }
</script>

<style scoped>

</style>
