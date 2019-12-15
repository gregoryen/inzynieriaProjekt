<template>
  <div id="app">

    

<!--    <img alt="Vue logo" src="./assets/logo.png">-->
    <HelloWorld msg="Welcome to Your Vue.js App"/>
    <WarehouseSupplyForm/>
    <router-view />

  </div>
</template>

<script>

// import HelloWorld from './components/HelloWorld.vue'
import WarehouseSupplyForm from './components/WarehouseSupplyForm.vue'

export default {
  name: 'app',
  components: {
    // HelloWorld,
    WarehouseSupplyForm
  }
}
r
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
