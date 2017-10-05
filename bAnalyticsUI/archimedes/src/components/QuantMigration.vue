<template>
  <v-container fluid class="quantileMigration pl-3 pr-3 mb-3">
    <!-- ASHA STUFF HERE -->

    <!--// ASHA STUFF HERE -->

    <!-- =====ROW1===== -->
    <v-layout row wrap class="pt-0 mt-0">
      <v-flex xs12>
        <v-card class="pa-0 ma-0 grey lighten-2">
          <v-card-title primary-title class="primary">
            <h6 class="white--text text-xs-left mb-0">Customer Quantile Migration</h6>
          </v-card-title>
          <v-layout row wrap>
            <!--Dropdown1-->
            <v-flex xs12 sm3>
              <v-card flat class="pl-2 pr-2 pt-0 pb-0 grey lighten-2">
                <v-layout row wrap>
                  <v-flex xs12>
                    <div class="primary--text text-xs-left pl-0 pr-0 pb-0 pt-2">
                      Select Prior Period for Analysis:
                    </div>
                  </v-flex>
                  <v-flex xs12>
                    <v-card class="white pa-0">
                      <v-select v-bind:items="tpArray"
                                v-model="tpSelect"
                                label="Select Time Period"
                                single-line
                                bottom
                                class="pl-1 pr-1 m-0"></v-select>
                      </v-select>
                    </v-card>
                  </v-flex>
                </v-layout>
              </v-card>
            </v-flex>
            <!--//Dropdown1-->
            <!--Dropdown2-->
            <v-flex xs12 sm3>
              <v-card flat class="pl-2 pr-2 grey lighten-2">
                <v-layout row wrap>
                  <v-flex xs12>
                    <div class="primary--text text-xs-left pl-0 pr-0 pb-0 pt-2">
                      Select Prior Period Quantiles:
                    </div>
                  </v-flex>
                  <v-flex xs12>
                    <v-card class="white">
                      <v-select v-bind:items="quantAray"
                                v-model="piorPeriod"
                                label="Select Prior Period Quantiles"
                                multiple
                                single-line
                                bottom
                                class="pl-1 pr-1 m-0"></v-select>
                    </v-card>
                  </v-flex>
                </v-layout>
              </v-card>
            </v-flex>
            <!--//Dropdown2-->
            <!--Dropdown3-->
            <v-flex xs12 sm3>
              <v-card flat class="pl-2 pr-2 grey lighten-2">
                <v-layout row wrap>
                  <v-flex xs12>
                    <div class="primary--text text-xs-left pl-0 pr-0 pb-0 pt-2">
                      Select Post Period Quantiles:
                    </div>
                  </v-flex>
                  <v-flex xs12>
                    <v-card class="white">
                      <v-select v-bind:items="quantAray"
                                v-model="postPeriod"
                                label="Select Post Period Quantiles"
                                multiple
                                single-line
                                bottom
                                class="pl-1 pr-1 m-0"></v-select>
                      </v-select>
                    </v-card>
                  </v-flex>
                </v-layout>
              </v-card>
            </v-flex>
            <!--//Dropdown3-->
            <!--Dropdown4-->
            <v-flex xs12 sm3>
              <v-card flat class="pl-2 pr-2 grey lighten-2">
                <v-layout row wrap>
                  <v-flex xs12>
                    <div class="primary--text text-xs-left pl-0 pr-0 pb-0 pt-2">
                      View Table As:
                    </div>
                  </v-flex>
                  <v-flex xs12>
                    <v-card class="white">
                      <v-select v-bind:items="views"
                                v-model="viewType"
                                label="View Table As"
                                single-line
                                bottom
                                class="pl-1 pr-1 m-0"></v-select>
                      </v-select>
                    </v-card>
                  </v-flex>
                </v-layout>
              </v-card>
            </v-flex>
            <!--//Dropdown4-->
          </v-layout>
        </v-card>
      </v-flex>
    </v-layout>
    <!-- //=====ROW1===== -->
    <!-- =====ROW2===== -->
    <v-layout wrap row>
      <v-flex xs12 class="pt-0 mt-0">
        <v-card class="pl-3 pr-3 pt-1 pb-1">
          <div class="title primary--text text-xs-center pa-1 emphasis"><em>Period <span class="grey--text darken-2">1</span> Quantile Migration from Prior Period Quantiles <span class="grey--text darken-2">All</span> to Post Period Quantiles <span class="grey--text darken-2">All</span></em></div>
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
              <img src="http://via.placeholder.com/1050x710?text=Chart" width="100%" height="100%">
            </v-flex>
            <!--//+++++col1+++++-->


            <!--+++++col2+++++-->
            <v-flex xs4 class="pr-4">
              <!--table-row-->
              <v-layout wrap row>
                <v-card class="white card_width">
                  <v-card-title primary-title class="white">
                    <h6 class="primary--text text-xs-center mb-0">Period-over-Period Retention Rate</h6>
                  </v-card-title>
                  <v-divider class="primary pb-0"></v-divider>
                  <v-flex xs12 fill-height>
                    <v-layout row wrap>
                      <!--****THIS IS JUST A PLACEHOLDER TABLE****-->
                      <table cellpadding="0" cellspacing="0" width="100%" style="height:21vh !important;">
                        <tr v-for="item in sumItems" v-bind:key="item.name">
                          <td class="pl-2 pr-2 pt-2 pb-0"><div class="primary--text" v-text="item.name"></div></td>
                          <td class="pl-2 pr-2 pt-2 pb-0"><div v-text="item.vals"></div></td>
                          <td class="pl-2 pr-2 pt-2 pb-0"><div><v-icon>{{item.icon}}</v-icon></div></td>
                        </tr>
                      </table>
                      <!--//****THIS IS JUST A PLACEHOLDER TABLE****//-->
                    </v-layout>
                  </v-flex>
                </v-card>
              </v-layout>
              <!--//table-row-->
              <!--chart-row-->
              <v-layout wrap row>
                <v-card class="white mt-3 pa-0">
                  <v-flex xs12 fill-height>
                    <!-- =====ROW1===== -->
                    <v-layout row wrap class="pt-0 mt-0">
                      <v-flex xs12 class="pa-0 ma-0">
                        <v-card class="pa-0 ma-0 grey lighten-2">
                          <v-card-title primary-title class="white">
                            <h6 class="primary--text text-xs-left mb-0">Post Period Quantile Composition</h6>
                          </v-card-title>
                          <v-layout row wrap>
                            <!--Dropdown1-->
                            <v-flex xs12>
                              <v-card flat class="pl-2 pr-2 pt-0 pb-0 grey lighten-2">
                                <v-layout row wrap>
                                  <v-flex xs12>
                                    <div class="primary--text text-xs-left pl-0 pr-0 pb-0 pt-2">
                                      Select Post Period Segment for Analysis:
                                    </div>
                                  </v-flex>
                                  <v-flex xs12>
                                    <v-card class="white pa-0">
                                      <!--****THIS IS JUST A PLACEHOLDER DROPDOWN****-->
                                      <v-select v-bind:items="quantiles"
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
  export default {
    name: 'quantMigration',
    components: {
    },
    data () {
      return {
        incomingJson: {},
        tpSelect: 1,
        priorPeriod: [],
        postPeriod: [],
        viewType: 'Counts',
        views: ['Counts', 'Percentages'],
        tpArray: [],
        quantArray: [],
        sumItems: [
          {name: 'name1', vals: '100%', icon: 'trending_up'},
          {name: 'name2', vals: '90%', icon: 'trending_up'},
          {name: 'name3', vals: '80%', icon: 'trending_up'},
          {name: 'name4', vals: '70%', icon: 'trending_up'},
          {name: 'name5', vals: '60%', icon: 'trending_flat'},
          {name: 'name6', vals: '50%', icon: 'trending_flat'},
          {name: 'name7', vals: '40%', icon: 'trending_down'},
          {name: 'name8', vals: '30%', icon: 'trending_down'},
          {name: 'name9', vals: '20%', icon: 'trending_down'},
          {name: 'name10', vals: '10%', icon: 'trending_down'}
        ]
      }
    },
    computed: {
      jsonMsg: function () {
        return this.incomingJson.data
      }
    },
    mounted () {

    },
    methods: {}
  }

</script>

<style scoped>
.card_width{width:100% !important;}
</style>
