<template>
  <v-container fluid class="home pa-0" style="height:100%;">
    <v-layout row wrap class="mt-0 mb-3 pa-0" style="height:100%;">

      <v-flex xs12 class="ma-0 pt-0 pl-5 pr-5 bg">
        <v-layout column align-center justify-center class="marT90">
          <h1 class="primary--text text-xs-center">Consumer Insights</h1>
          <h3 class="grey--text text-xs-center">Dashboard</h3>
        </v-layout>
      </v-flex>

    </v-layout>
  </v-container>
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
.marT90 {margin-top:90px;}
.bg{
  background-image: url("/static/bp_logo_reverse_color2.png");
  background-color: #eff3f6;
  background-repeat: no-repeat;
  background-size: cover;
  -webkit-background-size: cover;
  -moz-background-size: cover;
  -o-background-size: cover;
  background-position: center top;
  width: 100%;
  height: 100%;
}
</style>
