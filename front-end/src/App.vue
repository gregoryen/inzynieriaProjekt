<template>
  <div id="app">
    <AdminPanel />
  </div>
</template>

<script>
import AdminPanel from './components/AdminPanel.vue'

export default {
  name: 'app',
  components: {
    AdminPanel
  }
}
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
}

.small-table{
  margin-top: 60px;
  margin-left: 4em;
}

.main-table{
  margin-top: 60px;
  margin-right: 4em;
}
</style>
