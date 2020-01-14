<template>
    <div id="opinion-form" class="container">
        <h1>Menadżer opinii</h1>
        <table class="table table-striped table-sm">
            <thead class="thead-dark">
                <tr>
                    <th>Komentarz</th>
                    <th>Ocena</th>
                    <th>Email użytkownika</th>
                    <th>Usuń</th>
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
                        {{opinions[index-1].clientEmail}}
                    </td>
                    <td><button class="btn btn-secondary" v-on:click="deleteOpinion(opinions[index-1])">Usuń</button></td>
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
        props: {},
        star: {
            rating: "No Rating Selected",
            currentRating: "No Rating",
            currentSelectedRating: "No Current Rating",
            boundRating: 3,
        },
        data: function () {
            return {
                opinions: [],
                opinion: {
                    productId: null,
                    clientEmail: null,
                    starsNumber: null,
                    description: '',
                    likesNumber: null,
                    dislikesNumber: null
                },
            };
        },
        methods: {
            getAllOpinions: function () {
                axios.get(API_URL +'/opinions/all', {
                    "Access-Control-Allow-Origin": "*",
                    "Content-Type": "application/json"
                }).then((response) => {
                    this.opinions = response.data;
                });
            },
            deleteOpinion: function(opinion) {
                axios.delete(API_URL +'/opinions/'+ opinion.id);
                this.opinions = this.opinions.filter(e => e !== opinion);
            }
        },
    }
</script>

<style scoped>
    tr, td {
        text-align: center;
        vertical-align: middle;
    }
</style>
