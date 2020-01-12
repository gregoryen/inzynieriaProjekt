<template>
    <div class="conversationsList">
        <div class="container">
            <h1>Twoje konwersacje</h1>
            <ul id="usersList" v-for="i in userConversations" :key="i">
                <li>{{i}} <input type="button" @click="onClickButton(i)" value="Otwórz" class="btn btn-info"></li>
            </ul>
        </div>
    </div>
</template>

<script>
    import axios from 'axios'

    export default {
        name: "ConversationsList",
        props: {
            token: String
        },
        data() {
            return {
                userConversations: null
            }
        },
        methods: {
            getConversations: function () {
                var root = "http://localhost:8080";
                var token = this.token;
                const config = {
                    headers: {
                        Authorization: "Bearer " + token
                    }
                };
                axios.get(root + "/messages/users", config).then(response => {
                    this.userConversations = response.data.filter(user => user != null)
                }).catch(error => {
                    /* eslint-disable no-console */
                    console.log(error);
                });
            },
            onClickButton: function (user) {
                this.$emit('conversationChosen', user)
            }
        },
        mounted() {
            this.getConversations()
        }
    }

</script>

<style scoped>

</style>