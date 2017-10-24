<template>
  <v-container fluid class="quantileSummary pl-3 pr-3 mb-3">
    <!-- =====ROW1===== -->
    <v-layout row wrap class="pt-0 mt-0">
      <v-flex xs12>
        <v-card class="pa-0 ma-0 grey lighten-2">
          <v-card-title primary-title class="primary">
            <h6 class="white--text text-xs-left mb-0">Segment Summary</h6>
          </v-card-title>
          <v-layout row wrap>
            <!--Dropdown1-->
            <v-flex xs12 sm3>
              <v-card flat class="pl-2 pr-2 pt-0 pb-0 grey lighten-2">
                <v-layout row wrap>
                  <v-flex xs12>
                    <div class="primary--text text-xs-left pl-0 pr-0 pb-0 pt-2">
                      Time Period:
                    </div>
                  </v-flex>
                  <v-flex xs12>
                    <v-card class="white pa-0">
                      <v-select v-bind:items="quantiles"
                                v-model="quantileSelect"
                                label="Select Quantiles"
                                multiple
                                single-line
                                bottom
                                v-on:input="selectQuantile()"
                                class="pl-1 pr-1 m-0">
                      </v-select>
                    </v-card>
                  </v-flex>
                </v-layout>
              </v-card>
            </v-flex>
            <!--//Dropdown1-->
          </v-layout>
        </v-card>
      </v-flex>
    </v-layout>
    <!-- //=====ROW1===== -->
    <!-- =====ROW2===== -->
    <v-layout wrap row>
      <!--+++++col1+++++-->
      <v-flex xs5 class="pt-0 mt-0">
        <v-card class="white pl-3 pr-3 pt-1 pb-1">
          <div class="title primary--text text-xs-center pa-1">Aggregate Metrics for Time Period 1</div>
          <!-- CHART GOES HERE -->
        </v-card>
      </v-flex>
      <!--//+++++col1+++++-->
      <!--+++++col2+++++-->
      <v-flex xs7 class="pt-0 mt-0">
        <v-card class="white pl-3 pr-3 pt-1 pb-1">
          <div class="title primary--text text-xs-center pa-1">Segment Percent of Total for Time Period 1</div>
          <!-- CHART GOES HERE -->
        </v-card>
      </v-flex>
      <!--//+++++col2+++++-->
    </v-layout>
    <!-- //=====ROW2===== -->
    <!-- =====ROW3===== -->
    <v-layout wrap row>
      <!--+++++col1+++++-->
      <v-flex xs5 class="pt-0 mt-0">
        <v-card class="white pl-3 pr-3 pt-1 pb-1">
          <div class="title primary--text text-xs-center pa-1">Overall Metrics Time Period 1</div>
          <!-- CHART GOES HERE -->
        </v-card>
      </v-flex>
      <!--//+++++col1+++++-->
      <!--+++++col2+++++-->
      <v-flex xs7 class="pt-0 mt-0">
        <v-card class="white pl-3 pr-3 pt-1 pb-1">
          <div class="title primary--text text-xs-center pa-1">Segment Metrics for Time Period 1</div>
          <!-- CHART GOES HERE -->
        </v-card>
      </v-flex>
      <!--//+++++col2+++++-->
    </v-layout>
    <!-- //=====ROW3===== -->
  </v-container>
</template>

<script>
  import { summary } from './javascript/lifecycle.service'

  export default {
    name: 'lifecycleSummary',
    components: {
    },
    data () {
      return {
        incomingJson: {},
        tpSelect: 1,
        prods: [],
        segments: [],
        viewType: 'Counts',
        views: ['Counts', 'Percentages'],
        tpArray: [],
        quantArray: [],
        jobId: 'testLifecycle'
      }
    },
    computed: {
      jsonMsg: function () {
        return this.incomingJson.data
      }
    },
    mounted () {
      this.getResults()
    },
    methods: {
      getResults () {
        summary(this.jobId)
          .catch(err => {
            alert('Could not get Lifecycle Summary results. ' + err.message.toString())
          })
          .then((response) => {
            this.incomingJson = response.data
            console.log(this.incomingJson)
          })
      }
    }
  }

</script>

<style>

</style>
