<template>
<v-container fluid class="mt-2 mb-0 pt-0 pb-0">
<v-card flat>
    <v-layout row wrap class="">
      <v-flex xs12 s12 md3 class="text-xs-center">
          <v-btn primary large router to="/Balor/NewBalor" class="white--text text-xs-left marTB20">+ NEW BALOR INPUT</v-btn>
      </v-flex>
      <v-flex xs12 s12 md9 class="pa-2">
        <v-stepper v-model="e1">
              <v-stepper-header>
                <v-stepper-step step="1" :complete="e1 > 1">Name of step 1</v-stepper-step>
                <v-divider></v-divider>
                <v-stepper-step step="2" :complete="e1 > 2">Name of step 2</v-stepper-step>
                <v-divider></v-divider>
                <v-stepper-step step="3">Name of step 3</v-stepper-step>
              </v-stepper-header>
              <!--<v-stepper-content step="1">
                <v-card class="grey lighten-1 mb-5" height="200px">
                  <v-card-title primary-title class="primary">
                    <h6 class="white--text text-xs-left mb-0">Annotated Bar Chart</h6>
                  </v-card-title>
                </v-card>
                <v-btn primary @click.native="e1 = 2">Continue</v-btn>
                <v-btn flat>Cancel</v-btn>
              </v-stepper-content>
              <v-stepper-content step="2">
                <v-card class="grey lighten-1 mb-5" height="200px"></v-card>
                <v-btn primary @click.native="e1 = 3">Continue</v-btn>
                <v-btn flat>Cancel</v-btn>
              </v-stepper-content>
              <v-stepper-content step="3">
                <v-card class="grey lighten-1 mb-5" height="200px"></v-card>
                <v-btn primary @click.native="e1 = 1">Continue</v-btn>
                <v-btn flat>Cancel</v-btn>
              </v-stepper-content>-->
      </v-stepper>
      </v-flex>
    </v-layout>
</v-card>
</v-container>
</template>

<script>

  export default{
    name: 'balorSteppers',
    data () {
      return {
        items: [
          { text: 'Input', disabled: false, link: '#/Balor/NewBalor' },
          { text: 'Cadence', disabled: false, link: '#/Balor/Cadence' },
          { text: 'BALOR Results', disabled: false, link: '#/Balor/Trend' },
          { text: 'Segment Trends', disabled: false, link: '#/Balor/SegmentTrend' }
        ],
        e1: 0
      }
    }
  }

</script>

<style>
.bread_font, .bread_font a{
  font-size:17px;
}
.marTB20{margin-top:20px; margin-bottom:20px;}
</style>
