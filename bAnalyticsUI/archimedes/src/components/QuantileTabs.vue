<template>
<v-container fluid class="mt-2 mb-0 pt-0 pb-0">
<v-card class="white pa-3">
    <v-layout row wrap>
      <v-flex xs12 s12 md3 class="text-xs-center">
          <v-btn primary large router to="/Quantile/NewQuantile" class="white--text text-xs-left marTB8">+ NEW QUANTILE INPUT</v-btn>
      </v-flex>
      <v-flex xs12 s12 md9>
        <v-tabs id="mobile-tabs-3" grow light class="elevation-1">
          <v-tabs-bar slot="activators">
            <v-tabs-slider></v-tabs-slider>
            <v-tabs-item router to="/Quantile/NewQuantile">Input</v-tabs-item>
            <v-tabs-item router to="/Quantile/Summary">Quantile Summary & Profiles</v-tabs-item>
            <v-tabs-item router to="/Quantile/Migration">Quantile Migration</v-tabs-item>
            <v-tabs-item router to="/Quantile/Products">Top/Bottom Products</v-tabs-item>
          </v-tabs-bar>
        </v-tabs>
      </v-flex>
    </v-layout>
</v-card>
</v-container>
</template>

<script>

  export default{
    name: 'quantileTabs',
    data () {
      return {
        text: 'Lorem ipsum dolor sit amet.'
      }
    }
  }

</script>

<style>
.bread_font, .bread_font a{
  font-size:17px;
}
.marTB8{margin-top:8px; margin-bottom:8px;}
</style>
