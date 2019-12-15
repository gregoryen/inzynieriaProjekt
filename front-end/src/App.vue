<template>
  <div id="app">
    <Communicator/>
  </div>
</template>

<script>
import Communicator from "./components/Communicator";

export default {
  name: 'app',
  components: {
    Communicator
  }
}
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
