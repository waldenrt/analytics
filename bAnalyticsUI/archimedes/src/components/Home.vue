<template>
  <v-parallax src="/static/bp_logo_reverse_color2.png" height="1000" class="bg_img mt-0 mb-0">
    <v-layout column align-center justify-center>
      <h1 class="primary--text text-xs-center">Consumer Insights</h1>
      <h3 class="grey--text text-xs-center">Dashboard</h3>
    </v-layout>
  </v-parallax>
</template>

<script>
export default {
  name: 'home',
  data () {
    return {
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.bg_img {
  margin-top:90px;
}
</style>
