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

    export default {
        name: "Messenger",
        props: {
            token: String,
            addressee: String,
            role: String
        },
        data() {
            return {
                messages: null,
                text: ""
            }
        },
        methods: {
            loadMessages: function () {
                var root = "http://localhost:8080";
                var url = root + "/messages/user" + ( this.role == "admin" ? "/" + this.addressee : "");
                var token = this.token;

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
                var root = "http://localhost:8080";
                var token = this.token;


                const config = {
                    headers: {
                        Authorization: "Bearer " + token,
                        "Content-Type": "application/json"
                    }
                };

                let data = {
                    "receiver": this.addressee,
                    "text": this.text
                }
                axios.post(root + "/messages", data, config).then(function() {
                    this.text = "";
                    this.loadMessages();
                }).catch(error => {
                    /* eslint-disable no-console */
                    console.log(error);
                });
            }
        },
        mounted() {
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