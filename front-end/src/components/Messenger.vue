<template>
    <div class="messenger">
        <div class="container" id="chatBox">
            <input v-if="addressee != null" type="button" @click="onClickBackButton()" value="Back" class="btn btn-info">
            <h2 v-if="addressee == null">Conversation with: Helpdesk</h2>
            <h2 v-else>Conversation with: {{addressee}}</h2>

            <div class="container rounded" id="messageArea" >
                <ul id="messagesList" v-chat-scroll="{always: true, smooth: true}">
                    <li v-for="message in messages" v-bind:key="message.id">
                        <div v-if="message.sender == addressee" name="leftSide">{{message.text}}</div>
                        <div v-else name="rightSide">{{message.text}}</div>
                    </li>
                </ul>
            </div>
            <div class="form-inline" id="messageForm">
                <input type="text" id="message" v-model="text" placeholder="Type a message..." autocomplete="off"
                       class="form-control" @keydown.enter="sendMessage()"/>
                <input type="button" id="bt" @click="sendMessage()" value="Send" class="btn btn-info">
            </div>
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

                var root = conf.root

                var url = root + "/messages/user" + (this.$store.state.auth.user.privileges.includes("PRIVILEGE_COMMUNICATOR_ADMIN") ? "/" + this.addressee : "");
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
                    // this.messagesList.scrollTop = this.messagesList.scrollHeight;
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
        margin-top: 5vh;
        min-height: 50vh;
    }

    #messageArea {
        border: solid grey 2px;
        height: 50vh;
        overflow-y: auto;
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
        padding-left: 0;
    }

    div[name="leftSide"] {
        text-align: left;
        padding-top: 20px;
        font-size: 20px;
        list-style: none;
    }

    div[name="rightSide"] {
        text-align: right;
        padding-top: 20px;
        color: blue;
        font-size: 20px;
        list-style: none;
    }


</style>
