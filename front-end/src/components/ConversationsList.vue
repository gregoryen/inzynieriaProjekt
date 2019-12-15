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
    // import axios from 'axios'

    export default {
        name: "ConversationsList",
        props: {
        },
        data() {
            return {
                token: "Bearer eyJhbGciOiJSUzUxMiJ9.eyJyb2xlIjoiVVNFUiIsIm5hbWUiOiJHcnplZ29yeiBCYXJhbiIsInN1YiI6InVzZXIzQG9wLnBsIiwiaWF0IjoxNTc2NDE1Mzc5LCJleHAiOjE1NzkwMDczNzl9.s-QJFSWNiIU2tErvw5QrB0t5HJy0rLGqwkwZoz3WwOy68lO1-TuAVHgGtuDPaKMNPJxT8XQx3SGEgW-RaoaqEfEu48eZvGArO4IlucbcdeJPMsz4Oj0dtxUBxgnlv5N86ZRz3unvHgZqredQ3IFe5w_eiP5U1-IgWovJemvHjMWEveD81xm5euTGlL8ZruQVHfZcrdCF4KjtG4_RpgpRCQR7WWDzd_IsK1yjmBgCKHPyBa4xt03TYIQcVPCmAnZeKX7IZKOrIJcrPiAeFu4CRFn7M0gXe6-iqM-P2LiSlqKvkI5OQK9-_7u0YN9CFZDHC5AVZ6yeASm4WyvJQOqEgA"
            }
        },
        methods: {
            smth: function() {
                // eslint-disable-next-line no-console
                console.log(this.token)
                var req = new XMLHttpRequest();
                req.open('GET', 'http://localhost:8080/messages/users', true);
                req.setRequestHeader("Authorization", this.token);
                // req.setRequestHeader("Access-Control-Allow-Origin", "http://localhost:8081");
                req.send(null);

                // eslint-disable-next-line no-console
                console.log("Content-Type: " + req.getResponseHeader("Content-Type") + "\n");
            }
        },
        mounted: function () {

            // let config = {
            //     headers: {'Authorization': 'Bearer eyJhbGciOiJSUzUxMiJ9.eyJyb2xlIjoiVVNFUiIsIm5hbWUiOiJHcnplZ29yeiBCYXJhbiIsInN1YiI6InVzZXIzQG9wLnBsIiwiaWF0IjoxNTc2NDM3OTE3LCJleHAiOjE1NzkwMjk5MTd9.mNr3rUj-1AuKo51MajBhR_kk76DTWcUGjOYWBPxbILcHU7rHfOSTOac3ijG2n5JxKkdLlO7J8RmnGTvFjZ2L9fETFKpKDnKS75uv8r9uCsPy_X9v-W0u8sO01JQdEOwnVrueiFGvous4zrCjJRFQCfs-Q1lBwOvxUXk4tO9AlQSq9D6bCbQqAa_iVbxcF8cLgfDLdVmyPNA1jCNWueOvE6ZLkr2wcQueU0Yy2FL-qvNF2_HoGs_Qja_5q4VMBfXD7llhGDqo06UWG3hiz1Js1QAATwtAG6jbyXIqsAzsy47LFKVzIMCW4OnBm7giHBBfHmRTI0ezHauc58aAfyfVgQ'}
            // };
            //
            // // let bodyParameters = {
            // //     key: "value"
            // // }
            //
            //
            // axios.get('http://localhost:8080/messages/users', config)
            //     .then(function (data) {
            //         // eslint-disable-next-line no-console
            //         console.log(data)
            //     }).catch(function (error) {
            //     // eslint-disable-next-line no-console
            //     console.log(error);
            // });
        }
    }

</script>

<style scoped>

</style>