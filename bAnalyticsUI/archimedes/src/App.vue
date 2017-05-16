<template>
  <div id="app">
    <div class="container">
      <headernav></headernav>

      <div class="row">
        <modulenav></modulenav>
      </div>
    </div>
  </div>
</template>

<script>
  import modulenav from './components/ModuleNav.vue'
  import headernav from './components/HeaderNav.vue'

  export default {
    name: 'app',
    components: {
      headernav,
      modulenav
    }
  }
</script>

<style lang="scss">
  @import '../src/assets/_custom.sass';
  @import "../node_modules/bootstrap/scss/bootstrap.scss";
  body {
    padding-top: 60px;
  }
  @media (max-width: 979px) {
    body {
      padding-top: 0px;
    }
  }

</style>
