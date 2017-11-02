<template>
<v-container fluid class="ma-0 pa-0">
<v-card class="white pa-3">
    <v-layout row wrap>
      <v-flex xs12 s3 md3 class="text-xs-center">
          <v-btn primary large router to="/BRelevant/NewBRelevant" class="white--text text-xs-left marTB8">+ NEW BRELEVANT INPUT</v-btn>
      </v-flex>
      <v-flex xs12 s9 md9>
        <v-tabs id="mobile-tabs-3" grow light style="z-index: 1 !important;">
          <v-tabs-bar slot="activators">
            <v-tabs-slider></v-tabs-slider>
            <v-tabs-item router to="/BRelevant/NewBRelevant">Input</v-tabs-item>
            <v-tabs-item router to="/BRelevant/Products">Products</v-tabs-item>
            <v-tabs-item router to="/BRelevant/Customer">Customer</v-tabs-item>
          </v-tabs-bar>
        </v-tabs>
      </v-flex>
    </v-layout>
</v-card>
</v-container>
</template>

<script>

  export default{
    name: 'bRelevantTabs',
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
.bottom-nav{z-index: 1;}
</style>
