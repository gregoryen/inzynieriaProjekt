<template>
    <div class="conversationsList">
        <h1>Your conversations</h1>
        <ul id="usersList">
            <ol>elo</ol>
            <ol>jo</ol>
        </ul>
        <input type="button" @click="smth()" value="Pokaż więcej" class="btn btn-info col-sm-12">
    </div>
</template>

<script>
    import axios from 'axios'

    export default {
        name: "ConversationsList",
        props: {
        },
        data() {
            return {
                token: "eyJhbGciOiJSUzUxMiJ9.eyJyb2xlIjoiQURNSU4iLCJuYW1lIjoiUGFyw7N3YSBJbmR5a3BvbCIsInN1YiI6InByYWNvd25pa0BvcC5jb20iLCJpYXQiOjE1NzYzNzUwODYsImV4cCI6MTU3ODk2NzA4Nn0.ee8T3o5GhCw8D0rcTfZYuATdqGYIvKomcmSuo-Y8gphzZMD31be5Hy6ctPvIU4rDqXKZtAscBBHAA07hk0PDkbk8sZiepY_GYuooC0OVyrHHXTpJDzIC3RdXWaWrpW_fsJWs6jU4L6RR8KwhayYBJU7WN8auya2PCjZP5VWSJS51VNUonfNNIAgegek1hBWzBeSPlKbi0NJdqSgQIi8mXPxQWS5DXA1FocXu_m85I4TDdDP6sCdA56zSwYFqWTeF9ENgRdU93zYc_XW8D7Dke5NaprQVDAjlu-w8lxUl_HFAMWTD7RgB5ymQgpn5pbgimvCnXr-v5IiQvxwnEo0VSA"
            }
        },
        methods: {
            smth: function() {
                var root = "http://localhost:8080";
                var token= "eyJhbGciOiJSUzUxMiJ9.eyJyb2xlIjoiQURNSU4iLCJuYW1lIjoiUGFyw7N3YSBJbmR5a3BvbCIsInN1YiI6InByYWNvd25pa0BvcC5jb20iLCJpYXQiOjE1NzYzNzUwODYsImV4cCI6MTU3ODk2NzA4Nn0.ee8T3o5GhCw8D0rcTfZYuATdqGYIvKomcmSuo-Y8gphzZMD31be5Hy6ctPvIU4rDqXKZtAscBBHAA07hk0PDkbk8sZiepY_GYuooC0OVyrHHXTpJDzIC3RdXWaWrpW_fsJWs6jU4L6RR8KwhayYBJU7WN8auya2PCjZP5VWSJS51VNUonfNNIAgegek1hBWzBeSPlKbi0NJdqSgQIi8mXPxQWS5DXA1FocXu_m85I4TDdDP6sCdA56zSwYFqWTeF9ENgRdU93zYc_XW8D7Dke5NaprQVDAjlu-w8lxUl_HFAMWTD7RgB5ymQgpn5pbgimvCnXr-v5IiQvxwnEo0VSA";
                const config = {
                    headers : {
                        Authorization : "Bearer " + token
                    }
                };
                axios.get(root + "/messages/users", config).then(response => {
                    // eslint-disable-next-line no-console
                    console.log(response);
                }).catch(error => {
                    /* eslint-disable no-console */
                    console.log(error);
                });
            }
        }
    }

</script>

<style scoped>

</style>