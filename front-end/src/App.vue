<template>
  <div id="app">
    <QuickSearcher :baseurl="this.baseurl"/>
  </div>
</template>

<script>
import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap-vue/dist/bootstrap-vue.css";
import QuickSearcher from "./components/QuickSearcher";

export default {
  components: {
    QuickSearcher
  },
  name: "app",
  data() {
    return {
      baseurl: "http://localhost:8080"
    }
  }
};
</script>

<style scoped>
  #app {
    width: 100vw;
    height: 100vh;
    background-color: #313131
  }
</style>
