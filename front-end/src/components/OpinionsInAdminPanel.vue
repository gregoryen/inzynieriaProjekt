<template>
    <div id="opinion-form" class="container">
        <h1>Menadżer opinii</h1>
        <table class="table table-striped">
            <thead class="thead-dark">
                <tr>
                    <th>Komentarz</th>
                    <th>Ilosc gwiazdek</th>
                    <th>Id użytkownika</th>
                    <th>Usuń</th>
                </tr>
            </thead>
            <tbody v-if="opinions.length > 0">
                <tr v-for="index in Math.min(opinions.length, opinions.length)" v-bind:key="index">
                    <td>
                        {{opinions[index-1].description}}
                    </td>
                    <td>
                        <star-rating :rating=opinions[index-1].starsNumber :read-only="true" ></star-rating>
                    </td>
                    <td>
                        {{opinions[index-1].clientId}}
                    </td>
                    <td><button v-on:click="deleteOpinion(opinions[index-1].id)">Usuń</button></td>
                </tr>
            </tbody>
        </table>
    </div>
</template>
<script>
    import axios from 'axios';
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
                baseurl: "http://localhost:8100",
                opinions: [],
                opinion: {
                    productId: null,
                    clientId: null,
                    starsNumber: null,
                    description: '',
                    likesNumber: null,
                    dislikesNumber: null
                },
                clicked: false,

            };
        },
        methods: {
            getAllOpinions: function () {
                axios.get(this.baseurl +'/opinions/all', {
                    "Access-Control-Allow-Origin": "*",
                    "Content-Type": "application/json"
                })
            },
            deleteOpinion: function(id) {
                axios.delete(this.baseurl +'/opinions/'+id);
                this.getAllOpinions();
            }
        },
    }
</script>

<style scoped>

</style>
