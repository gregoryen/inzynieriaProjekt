<template>
    <div id="communicator">
        <ConversationsList v-if='showList' :token="token" @conversationChosen="conversationChosen"/>
        <Messenger v-else :token="token" :addressee="addressee" :role="role"/>
    </div>
</template>

<script>
    import ConversationsList from "./ConversationsList";
    import Messenger from "./Messenger";
    import axios from 'axios'

    export default {
        name: "Communicator",
        components: {Messenger, ConversationsList},
        data() {
            return {
                token: "eyJhbGciOiJSUzUxMiJ9.eyJyb2xlIjoiVVNFUiIsIm5hbWUiOiJHcnplZ29yeiBCYXJhbiIsInN1YiI6InVzZXIzQG9wLnBsIiwiaWF0IjoxNTc2NDE1Mzc5LCJleHAiOjE1NzkwMDczNzl9.s-QJFSWNiIU2tErvw5QrB0t5HJy0rLGqwkwZoz3WwOy68lO1-TuAVHgGtuDPaKMNPJxT8XQx3SGEgW-RaoaqEfEu48eZvGArO4IlucbcdeJPMsz4Oj0dtxUBxgnlv5N86ZRz3unvHgZqredQ3IFe5w_eiP5U1-IgWovJemvHjMWEveD81xm5euTGlL8ZruQVHfZcrdCF4KjtG4_RpgpRCQR7WWDzd_IsK1yjmBgCKHPyBa4xt03TYIQcVPCmAnZeKX7IZKOrIJcrPiAeFu4CRFn7M0gXe6-iqM-P2LiSlqKvkI5OQK9-_7u0YN9CFZDHC5AVZ6yeASm4WyvJQOqEgA",
                addressee: null,
                role: null,
                showList: false
            }
        },
        methods: {
            getRole: function () {
                var root = "http://localhost:8080";
                var token = this.token;

                const config = {
                    headers: {
                        Authorization: "Bearer " + token
                    }
                };
                axios.get(root + "/messages/userRole", config).then(response => {

                    this.role = response.data;
                    this.showList = (this.role == 'admin');
                }).catch(error => {
                    /* eslint-disable no-console */
                    console.log(error);
                });
            },
            conversationChosen: function (user) {
                this.addressee = user;
                this.showList = false;
            }
        },
        mounted() {
            this.getRole();
        }
    }
</script>

<style scoped>

</style>