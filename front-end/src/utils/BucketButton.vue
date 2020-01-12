<template>
    <router-link to="/shoppingCart">
        <img src="../assets/shoppingCartLogo.png" width="50" height="50"/>
        <p>{{itemNumber}}</p>
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
            axios.get(config.root + "/bucket/itemNumber/" + "user1") //this.bucketId //ZMIEN
            .then( (res) => {
                // eslint-disable-next-line no-console
                console.log(res)
                this.itemNumber = res.data;
            })
        }
    };
</script>

<style scoped>

</style>
