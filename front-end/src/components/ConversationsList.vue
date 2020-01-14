<template>
    <div class="conversationsList">
        <div class="container">
            <h1>Your conversations</h1>
            <ul id="usersList" v-for="i in userConversations" :key="i">
                <li>{{i}} <input type="button" @click="onClickButton(i)" value="Open" class="btn btn-info"></li>
            </ul>
        </div>
    </div>
</template>

<script>
    import axios from 'axios';
    import conf from './../config';


    export default {
        name: "ConversationsList",
        props: {},
        data() {
            return {
                userConversations: null
            }
        },
        methods: {
            getConversations: function () {
                var root = conf.root;
                var token = this.$store.state.auth.user.jwtToken;
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

    * {
        text-align: center;
    }

    ul {
        list-style-type: none;
    }

</style>