<template>
    <div id="communicator">
        <ConversationsList v-if='showList' @conversationChosen="conversationChosen"/>
        <Messenger v-else :addressee="addressee" @backButtonClicked="backButtonClicked"/>
    </div>
</template>

<script>
    import ConversationsList from "./ConversationsList";
    import Messenger from "./Messenger";

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
            checkPrivileges: function () {
                // throws error
                // this.showList = this.$parent.$options.methods.ifHavePrivilege('ADMIN');
                this.showList = this.$store.state.auth.user.privileges.split(',').includes('PRIVILEGE_COMMUNICATOR_ADMIN');
            },
            conversationChosen: function (user) {
                this.addressee = user;
                this.showList = false;
            },
            backButtonClicked: function() {
                this.addressee = null;
                this.checkPrivileges();
            }
        },
        mounted() {
            this.checkPrivileges();
        }
    }
</script>

<style scoped>

</style>