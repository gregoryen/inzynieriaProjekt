<template>
    <div class="messenger">
        <div class="container" id="chatBox">
            <input type="button" @click="onClickBackButton()" value="Back" class="btn btn-info">
            <h2 v-if="addressee == null">Conversation with: Helpdesk</h2>
            <h2 v-else>Conversation with: {{addressee}}</h2>
            <div class="container rounded" id="messageArea">
                <ul v-for="message in messages" v-bind:key="message.id">
                    <li v-if="message.sender == addressee" name="leftSide">{{message.text}}</li>
                    <li v-else name="rightSide">{{message.text}}</li>
                </ul>
            </div>
            <form class="form-inline" id="messageForm">
                <input type="text" id="message" v-model="text" placeholder="Type a message..." autocomplete="off"
                       class="form-control"/>
                <input type="button" id="bt" @click="sendMessage()" value="Send" class="btn btn-info">
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
                var url = root + "/messages/user" + (this.$store.state.auth.user.privileges == "ADMIN" ? "/" + this.addressee : "");
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
            },
            onClickBackButton: function() {
                this.$emit('backButtonClicked',);
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
        /*border: solid grey 2px;*/
        margin-top: 5%;
        min-height: 50vh;
    }

    #messageArea {
        border: solid grey 2px;
        min-height: 50vh;
    }

    .form-inline {
        padding: auto;
        margin: auto;

    }

    #messageForm {
        min-width: 50%;
        margin: 0px;
        padding: 0px;
    }

    #message {
        min-width: 90%;
        padding: auto;
        margin: auto;
    }

    #bt {
        min-width: 10%;
    }

    ul {
        list-style-type: none;
    }

    li[name="leftSide"] {
        text-align: left;
        font-size: 20px;
    }

    li[name="rightSide"] {
        text-align: right;
        color: blue;
        font-size: 20px;
    }

</style>