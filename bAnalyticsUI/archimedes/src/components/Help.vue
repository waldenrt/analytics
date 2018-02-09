<template>
  <v-container fluid class="help pa-0" style="height:100%;">
    <v-layout row wrap class="mt-0 mb-3 pa-0" style="height:100%;">

      <v-flex xs12 >
        <div class="marT_img">
          <!-- Hero Row -->
          <v-layout wrap row class="accent elevation-1 pa-3 ma-0 bg_hero">
              <v-flex xs12>
                <div>
                  <h3 class="white--text text-xs-center pb-0 mb-0">Help & Support</h3>
                  <h5 class="white--text text-xs-center pb-0 mb-0">Have a Question?</h5>
                </div>
              </v-flex>
          </v-layout>
          <!-- //Hero Row -->
          <!-- FAQs Row -->
          <v-layout wrap row class="pl-4 pr-4 pt-3 pb-0">
              <v-flex xs12 class="pl-1 pr-1">
                <h5 class="grey--text">Frequent Asked Questions</h5>
              </v-flex>
              <v-flex xs12 sm6 md3 class="pl-1 pr-1 pb-3">

              </v-flex>
          </v-layout>
          <!-- //FAQs Row -->
          <!-- Helpful Docs Row -->
          <v-layout wrap row class="pl-4 pr-4 pt-3 pb-0">
              <v-flex xs12 class="pl-1 pr-1">
                <h5 class="grey--text">Helpful Documents</h5>
              </v-flex>
          </v-layout>
          <!-- //Helpful Docs Row -->

      </div>
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
  },
  mounted () {
    this.$store.commit('switchApp', {module: 'Help'})
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.marT_img {margin-top:59px;width:100%;}
.card_crnr {border-top-right-radius: 20px;border-bottom-left-radius: 20px;}
.wrap1 { display: table; width: 100%; height: 100%; }
.wrap2 { display: table-cell; vertical-align: middle; margin:0 auto; }
.bg_hero { min-height: 200px; }
</style>
