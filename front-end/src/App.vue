<template>
  <div id="app">
    <router-view />

  </div>
</template>

<script>

export default {
  name: 'app',
  components: {
  }
}

</script>

<style>
#app {
  font-family: "Avenir", Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
}

.small-table {
  margin-top: 60px;
  margin-left: 4em;
}

.main-table {
  margin-top: 60px;
  margin-right: 4em;
}

.space {
  margin-bottom: 3em;
}

h2 {
  font-family: Impact, Haettenschweiler, Franklin Gothic Bold, Charcoal,
    Helvetica Inserat, Bitstream Vera Sans Bold, Arial Black, sans serif;
}

.small-card-deck {
    display: flex;
    margin-right: 3em;
    margin-left: 3em;
    margin-bottom: 3em;
}
</style>
