<template>
    <div class="messenger">
        <div class="container" id="chatBox">
            <div class="container" id="messageArea">
                <ul v-for="message in messages" v-bind:key="message.id">
                    <li v-if="message.sender == addressee" name="leftSide">{{message.text}}</li>
                    <li v-else name="rightSide">{{message.text}}</li>
                </ul>
            </div>
            <form class="form-inline" id="messageForm">
                <input type="text" id="message" v-model="text" placeholder="Type a message..." autocomplete="off"
                       class="form-control"/>
                <input type="button" @click="sendMessage()" value="Wyślij" class="btn btn-info">
            </form>
        </div>
    </div>
</template>

<script>
    import axios from 'axios'
    import conf from './../config'

    export default {
        name: "Messenger",
        props: {
            addressee: String
        },
        data() {
            return {
                messages: null,
                text: ""
            }
        },
        methods: {
            loadMessages: function () {
                // eslint-disable-next-line no-console
                console.log(this.$store.state.auth.user);

                var root = conf.root;

                // Privileges niżej będą zmienione, jak zostaną ustalone role
                var url = root + "/messages/user" + ( this.$store.state.auth.user.privileges == "ADMIN" ? "/" + this.addressee : "");
                var token = this.$store.state.auth.user.jwtToken;

                const config = {
                    headers: {
                        Authorization: "Bearer " + token
                    }
                };
                axios.get(url, config).then(response => {
                    this.messages = response.data;
                }).catch(error => {
                    /* eslint-disable no-console */
                    console.log(error);
                });
            },
            sendMessage: function () {
                var root = conf.root;
                var token = this.$store.state.auth.user.jwtToken;

                const config = {
                    headers: {
                        Authorization: "Bearer " + token,
                        "Content-Type": "application/json"
                    }
                };

                let data = {
                    "receiver": this.addressee,
                    "text": this.text
                };
                axios.post(root + "/messages", data, config).then(() => {
                    this.text = "";
                    this.loadMessages();
                }).catch(error => {
                    /* eslint-disable no-console */
                    console.log(error);
                });
            }
        },
        mounted() {
            console.log(this.$store.state.auth.user);
            this.loadMessages();
        }
    }
</script>

<style scoped>
    #chatBox {
        border: solid grey 1px;
    }

    .form-inline {
        padding: auto;
        margin: auto;

    }

    #messageForm {
        margin: 0px;
        padding: 0px;
    }

    #message {
        width: 70%;
        padding: auto;
        margin: auto;
    }

    li[name="leftSide"] {
        text-align: left;
    }

    li[name="rightSide"] {
        text-align: right;
        color: blue;
    }

</style>