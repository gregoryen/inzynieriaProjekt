<template>
    <router-link to="/shoppingCart">
        <font-awesome-icon icon="shopping-cart" />
        <!-- <div class="container">
            <div class="row justify-content-end">
                <div class="col-1">
                    <font-awesome-icon icon="shopping-cart" />
                </div>
                <div class="col-1">
                    <p>{{itemNumber}}</p>
                </div> CHWILOWO WYRZUCAM BO NIE MAM CZASU NAPRAWIC
            </div>
        </div> -->
    </router-link>
</template>

<script>
import config from "../config.js"
import axios from "axios"
    
    export default {
        name: "BucketButton",
        props: {
            bucketId: String
        },
        data: () => {
            return {
                itemNumber: 0
            }
        },
        mounted() {
            let token = localStorage.getItem('user').jwtToken
            let id = token.substring(0, token.length/4)
            axios.get(config.root + "/bucket/itemNumber/" + id)
            .then( (res) => {
                this.itemNumber = res.data;
            })
        }
    };
</script>

<style scoped>

</style>
