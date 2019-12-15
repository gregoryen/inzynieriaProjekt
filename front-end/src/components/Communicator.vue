<template>
    <div id="communicator">
        <ConversationsList :token="token"/>
    </div>
</template>

<script>
    import ConversationsList from "./ConversationsList";
    export default {
        name: "Communicator",
        components: {ConversationsList},
        data() {
            return {
                token: "eyJhbGciOiJSUzUxMiJ9.eyJyb2xlIjoiVVNFUiIsIm5hbWUiOiJHcnplZ29yeiBCYXJhbiIsInN1YiI6InVzZXIzQG9wLnBsIiwiaWF0IjoxNTc2NDE1Mzc5LCJleHAiOjE1NzkwMDczNzl9.s-QJFSWNiIU2tErvw5QrB0t5HJy0rLGqwkwZoz3WwOy68lO1-TuAVHgGtuDPaKMNPJxT8XQx3SGEgW-RaoaqEfEu48eZvGArO4IlucbcdeJPMsz4Oj0dtxUBxgnlv5N86ZRz3unvHgZqredQ3IFe5w_eiP5U1-IgWovJemvHjMWEveD81xm5euTGlL8ZruQVHfZcrdCF4KjtG4_RpgpRCQR7WWDzd_IsK1yjmBgCKHPyBa4xt03TYIQcVPCmAnZeKX7IZKOrIJcrPiAeFu4CRFn7M0gXe6-iqM-P2LiSlqKvkI5OQK9-_7u0YN9CFZDHC5AVZ6yeASm4WyvJQOqEgA"
            }
        },
    }
</script>

<style scoped>

</style>