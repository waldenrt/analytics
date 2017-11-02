<template>
  <v-container fluid class="quantileProducts pl-3 pr-3 mb-3">
    <!-- ASHA STUFF HERE -->

    <!--// ASHA STUFF HERE -->

    <!-- =====ROW1===== -->
    <v-layout row wrap class="pt-0 mt-0">
      <v-flex xs12>
        <v-card class="pa-0 ma-0 grey lighten-2">
          <v-card-title primary-title class="primary">
            <h6 class="white--text text-xs-left mb-0">Period-over-Period Segment Migration</h6>
          </v-card-title>
          <v-layout row wrap>
            <!--Dropdown1-->
            <v-flex xs12 sm2>
              <v-card flat class="pl-2 pr-2 pt-0 pb-0 grey lighten-2">
                <v-layout row wrap>
                  <v-flex xs12>
                    <div class="primary--text text-xs-left pl-0 pr-0 pb-0 pt-2">
                      Select Prior Period<br/> for Analysis:
                    </div>
                  </v-flex>
                  <v-flex xs12>
                    <v-card class="white pa-0">
                      <!--SWAP OUT DROPDOWN-->
                      <v-select v-bind:items="quantiles"
                                v-model="quantileSelect"
                                label="Select Quantiles"
                                multiple
                                single-line
                                bottom
                                v-on:input="selectQuantile()"
                                class="pl-1 pr-1 mt-1 mb-2">
                      </v-select>
                      <!--//SWAP OUT DROPDOWN-->
                    </v-card>
                  </v-flex>
                </v-layout>
              </v-card>
            </v-flex>
            <!--//Dropdown1-->
            <!--Dropdown2-->
            <v-flex xs12 sm2>
              <v-card flat class="pl-2 pr-2 grey lighten-2">
                <v-layout row wrap>
                  <v-flex xs12>
                    <div class="primary--text text-xs-left pl-0 pr-0 pb-0 pt-2">
                      Prior<br/>Segment:
                    </div>
                  </v-flex>
                  <v-flex xs12>
                    <v-card class="white">
                      <!--SWAP OUT DROPDOWN-->
                      <v-select
                          v-bind:items="TPArray"
                          v-model="TPSelect"
                          label="Select Time Period"
                          single-line
                          bottom
                          v-on:input="selectTP()"
                          class="pl-1 pr-1 mt-1 mb-2">
                      </v-select>
                      <!--//SWAP OUT DROPDOWN-->
                    </v-card>
                  </v-flex>
                </v-layout>
              </v-card>
            </v-flex>
            <!--//Dropdown2-->
            <!--Dropdown3-->
            <v-flex xs12 sm2>
              <v-card flat class="pl-2 pr-2 grey lighten-2">
                <v-layout row wrap>
                  <v-flex xs12>
                    <div class="primary--text text-xs-left pl-0 pr-0 pb-0 pt-2">
                      Post<br/>Segment:
                    </div>
                  </v-flex>
                  <v-flex xs12>
                    <v-card class="white">
                      <!--SWAP OUT DROPDOWN-->
                      <v-select
                          v-bind:items="metrics"
                          v-model="metricSelect"
                          label="Select Metric"
                          single-line
                          bottom
                          v-on:input="selectMetric()"
                          class="pl-1 pr-1 mt-1 mb-2">
                      </v-select>
                      <!--//SWAP OUT DROPDOWN-->
                    </v-card>
                  </v-flex>
                </v-layout>
              </v-card>
            </v-flex>
            <!--//Dropdown3-->
            <!--Legend-->
            <v-flex xs12 sm6>
              <v-card flat class="pl-2 pr-2 grey lighten-2">
                <v-layout row wrap>
                  <v-flex xs12>
                    <div class="primary--text text-xs-center pl-0 pr-0 pb-0 pt-2">
                      Segment Legend:
                    </div>
                  </v-flex>
                </v-layout>
              </v-card>
            </v-flex>
            <!--//Legend-->
          </v-layout>
        </v-card>
      </v-flex>
    </v-layout>
    <!-- //=====ROW1===== -->
    <!-- =====ROW2===== -->
    <v-layout wrap row>
      <v-flex xs12 class="pt-0 mt-0">
        <v-card class="pl-3 pr-3 pt-1 pb-1">
          <div class="title primary--text text-xs-center pa-1"><em>Period-over-Period Segment Migration from Prior Segment(s)<span class="grey--text darken-2">All</span> to Post Segment(s)<span class="grey--text darken-2">All</span> during Period <span class="grey--text darken-2">1</span></em></div>
        </v-card>
      </v-flex>
    </v-layout>
    <!-- //=====ROW2===== -->
    <!-- =====ROW3===== -->
    <v-layout wrap row>
      <v-flex xs12 class="pt-0 mt-0">
        <v-card class="white pa-3">
          <v-layout wrap row>


            <!--+++++col1+++++-->
            <v-flex xs8>
              <!-- CHART GOES HERE -->
              <img src="http://via.placeholder.com/450x200?text=Chart" width="100%" height="100%">
            </v-flex>
            <!--//+++++col1+++++-->


            <!--+++++col2+++++-->
            <v-flex xs4 class="pr-4">
              <!--table-row-->
              <v-layout wrap row>
                <v-card class="white" style="width:100%;">
                  <v-card-title primary-title class="white">
                    <h6 class="primary--text text-xs-center mb-0">Prior Period Segment Retention Rate</h6>
                  </v-card-title>
                  <v-divider class="primary pb-0"></v-divider>
                  <v-flex xs12 fill-height>
                    <v-layout row wrap>
                      <table cellpadding="0" cellspacing="0" width="100%" style="height:21vh !important;">
                        <tr v-for="item in sumItems" v-bind:key="item.name">
                          <td class="pl-2 pr-2 pt-2 pb-0">
                            <div class="primary--text" v-text="item.name"></div>
                          </td>
                          <td class="pl-2 pr-2 pt-2 pb-0">
                            <div v-text="item.priorCustCount"></div>
                          </td>
                          <td class="pl-2 pr-2 pt-2 pb-0">
                            <div v-text="item.retained"></div>
                          </td>
                          <td class="pl-2 pr-2 pt-2 pb-0">
                            <div v-text="item.new"></div>
                          </td>
                          <td class="pl-2 pr-2 pt-2 pb-0">
                            <div v-text="item.postCustCount"></div>
                          </td>
                          <td class="pl-2 pr-2 pt-2 pb-0">
                            <div v-text="item.retRate"></div>
                          </td>
                        </tr>
                      </table>
                    </v-layout>
                  </v-flex>
                </v-card>
              </v-layout>
              <!--//table-row-->
              <!--chart-row-->
              <v-layout wrap row>
                <v-card class="white mt-3 pa-0" style="width:100%;">
                  <v-flex xs12 fill-height>
                    <!-- =====ROW1===== -->
                    <v-layout row wrap class="pt-0 mt-0">
                      <v-flex xs12 class="pa-0 ma-0">
                        <v-card class="pa-0 ma-0 grey lighten-2">
                          <v-card-title primary-title class="white">
                            <h6 class="primary--text text-xs-left mb-0">Post Period Segment Composition</h6>
                          </v-card-title>
                          <v-layout row wrap>
                            <!--Dropdown1-->
                            <v-flex xs12>
                              <v-card flat class="pl-2 pr-2 pt-0 pb-0 grey lighten-2">
                                <v-layout row wrap>
                                  <v-flex xs12>
                                    <div class="primary--text text-xs-left pl-0 pr-0 pb-0 pt-2">
                                      Select Post Period Segment:
                                    </div>
                                  </v-flex>
                                  <v-flex xs12>
                                    <v-card class="white pa-0">
                                      <!--****THIS IS JUST A PLACEHOLDER DROPDOWN****-->
                                      <v-select v-bind:items="quantArray"
                                                v-model="quantileSelect"
                                                label="Select Quantiles"
                                                multiple
                                                single-line
                                                bottom
                                                v-on:input="selectQuantile()"
                                                class="pl-1 pr-1 m-0">
                                      </v-select>
                                      <!--//****THIS IS JUST A PLACEHOLDER DROPDOWN****//-->
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
                    <v-layout row wrap class="pt-0 mt-0">
                      <v-flex xs12>
                        <img src="http://via.placeholder.com/450x200?text=Chart" width="100%" height="100%">
                      </v-flex>
                    </v-layout>
                    <!-- //=====ROW2===== -->
                  </v-flex>
                </v-card>
              </v-layout>
              <!--//chart-row-->
            </v-flex>
            <!--//+++++col2+++++-->


          </v-layout>
        </v-card>
      </v-flex>
    </v-layout>
    <!-- //=====ROW3===== -->
  </v-container>
</template>

<script>
  import { migration } from './javascript/lifecycle.service'

  export default {
    name: 'lifecycleMigration',
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
        migration(this.jobId)
          .catch(err => {
            alert('Could not get Lifecycle Migration results. ' + err.message.toString())
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
