<template>
    <div id="communicator">
        <ConversationsList v-if='showList' @conversationChosen="conversationChosen"/>
        <Messenger v-else :addressee="addressee" @backButtonClicked="backButtonClicked"/>
    </div>
</template>

<script>
    import ConversationsList from "./ConversationsList";
    import Messenger from "./Messenger";
    // import axios from 'axios'
    // import conf from './../config';


    export default {
        name: "Communicator",
        components: {Messenger, ConversationsList},
        data() {
            return {
                addressee: null,
                showList: true
            }
        },
        methods: {
            getRole: function () {
                // let root = conf.root;
                // let token = this.$store.state.auth.user.jwtToken;
                //
                // const config = {
                //     headers: {
                //         Authorization: "Bearer " + token
                //     }
                // };
                // axios.get(root + "/messages/userRole", config).then(response => {
                //
                //     let role = response.data;
                //     this.showList = (role == 'admin');
                // }).catch(error => {
                //     /* eslint-disable no-console */
                //     console.log(error);
                // });

                //Tymczasowe póki nie ma zdefiniowanych roli
                let privileges = this.$store.state.auth.user.privileges;
                this.showList = (privileges == 'ADMIN');
            },
            conversationChosen: function (user) {
                this.addressee = user;
                this.showList = false;
            },
            backButtonClicked: function() {
                this.addressee = null;
                this.getRole();
            }
        },
        mounted() {
            this.getRole();
        }
    }
</script>

<style scoped>

</style>