<template>
<v-container fluid class="ma-0 pa-0">
<v-card class="white pa-3">
    <v-layout row wrap>
      <v-flex xs12 md3 class="text-xs-center">
          <v-btn success large router to="/Lifecycle/NewLifecycle" class="white--text text-xs-left marTB8">+ NEW LIFECYCLE INPUT</v-btn>
      </v-flex>
      <v-flex xs12 md9>
        <v-tabs id="mobile-tabs-3" grow light class="elevation-1">
          <v-tabs-bar slot="activators">
            <v-tabs-slider></v-tabs-slider>
            <v-tabs-item router to="/Lifecycle/Summary">Segment Summary</v-tabs-item>
            <v-tabs-item router to="/Lifecycle/Migration">Segment Migration</v-tabs-item>
            <v-tabs-item router to="/Lifecycle/Products">Product Index</v-tabs-item>
          </v-tabs-bar>
        </v-tabs>
      </v-flex>
    </v-layout>
</v-card>
</v-container>
</template>

<script>

  export default{
    name: 'lifecycleTabs',
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
