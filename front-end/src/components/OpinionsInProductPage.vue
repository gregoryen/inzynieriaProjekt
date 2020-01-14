<template>

    <div class="new-opinion-form">
        <div v-show="showNewOpinion" id="new-opinion" class="new-opinion">
            <table id="supply-products" align="center" class="table table-striped table-sm">
                <thead>
                <tr>
                    <td><strong>Komentarz</strong></td>
                    <td><strong>Ocena</strong></td>
                    <td></td>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td>
                        <div>
                            <input  placeholder="komentarz" className="form-control" type="text"
                                    data-error="Please enter your description." v-model="opinion.description" required/>
                        </div>
                    </td>
                    <td>
                        <star-rating v-model="opinion.starsNumber" :star-size="20"></star-rating>
                    </td>
                    <td>
                        <button type="submit" class="btn btn-secondary" v-on:click="() => {
                        addOpinion();
                        this.showAddOpinionButton = true;
                        this.showNewOpinion = false;
                                   }">Zatwierdź</button>
                    </td>
                    <td>
                        <button type="submit" class="btn btn-secondary" v-on:click="() => {
                        this.showAddOpinionButton = true;
                        this.showNewOpinion = false;
                                   }">Cofnij</button>
                    </td>
                </tr>
                </tbody>

            </table>
        </div>
        <div v-show="showAddOpinionButton">
            <button type="button" class="btn btn-primary" v-on:click="() => {
            this.showNewOpinion = true;
            this.showAddOpinionButton = false
            }">Dodaj opinie</button>
        </div>
        <table class="table table-striped table-sm">
            <thead class="thead-dark">
            <tr>
                <th>Komentarz</th>
                <th>Ocena</th>
                <th></th>
                <th></th>
            </tr>
            </thead>
            <tbody v-if="opinions.length > 0">
            <tr v-for="index in Math.min(opinions.length, opinions.length)" v-bind:key="index">
                <td>
                    {{opinions[index-1].description}}
                </td>
                <td>
                    <star-rating :rating=opinions[index-1].starsNumber :read-only="true" :star-size="20"></star-rating>
                </td>
                <td>
                    <a>{{opinions[index-1].likesList.length}}</a>
                    <button class="btn btn-secondary" v-on:click="likeOpinion(opinions[index-1])">Like</button>
                </td>
                <td>
                    <a>{{opinions[index-1].dislikesList.length}}</a>
                    <button class="btn btn-secondary" v-on:click="() => {
                dislikeOpinion(opinions[index-1]);
                }">Dislike</button>
                </td>
            </tr>
            </tbody>
        </table>
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
        props: {
             productId: Number
        },
        star: {
            rating: "No Rating Selected",
            currentRating: "No Rating",
            currentSelectedRating: "No Current Rating",
            boundRating: 3,
        },
        data: function () {
            return {
                opinions: [],
                showNewOpinion: false,
                showAddOpinionButton: true,
                opinion: {
                    productId: null,
                    clientEmail: null,
                    starsNumber: null,
                    description: '',
                    likesList: null,
                    dislikesList: null
                },
                clicked: false,
            };
        },
        methods: {
            getAllOpinions: function () {
                axios.get(API_URL + '/opinions/productId/' + this.productId, {
                    "Access-Control-Allow-Origin": "*",
                    "Content-Type": "application/json"
                }).then((response) => {
                    this.opinions = response.data;
                    // eslint-disable-next-line no-console
                    console.log(response)
                });
            },
            addOpinion: function () {
                axios.post(API_URL + '/opinions/add', {
                    productId: this.productId,
                    clientEmail: this.$store.state.auth.user.email,
                    starsNumber: this.opinion.starsNumber,
                    description: this.opinion.description,
                    likesList: [],
                    dislikesList: []
                }, {
                    "Access-Control-Allow-Origin": "*",
                    "Content-Type": "application/json"
                }).then(response => {
                    // eslint-disable-next-line no-console
                    console.log(response)
                }).catch(error => {
                    // eslint-disable-next-line no-console
                    console.log(error.response)
                });
                this.$router.go();
            },
            likeOpinion: function (opinion)  {
                opinion.likesList.push(this.$store.state.auth.user.email);
                axios.post(API_URL + '/opinions/update', opinion,
                    {
                        "Access-Control-Allow-Origin": "*",
                        "Content-Type": "application/json"
                    })
            },
            dislikeOpinion: function (opinion)  {
                opinion.dislikesList.push(this.$store.state.auth.user.email);
                axios.post(API_URL + '/opinions/update', opinion,
                    {
                        "Access-Control-Allow-Origin": "*",
                        "Content-Type": "application/json"
                    })
            }
        },
    }
</script>

<style scoped>
    div.new-opinion{
        margin: 25px;
    }
    button.btn-primary {
        margin: 25px;
    }
    a   {
        margin: 15px;
    }
</style>
