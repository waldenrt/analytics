<template>
  <div class="settings">
    <h1>{{ msg }}</h1>
  </div>
</template>

<script>
  export default {
    name: 'settings',
    data () {
      return {
        msg: 'whatever settings we want them to be able to change....'
      }
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
