<template>
  <v-container fluid class="bRelevantSummary pl-3 pr-3">
    <!-- =====ROW1===== -->
    <HelpNavBRelevant ref="helpNav"></HelpNavBRelevant>
    <!-- =====ROW1===== -->
    <v-layout row wrap class="mb-3 ml-0 mr-0">
      <v-flex xs12 class="elevation-1">
        <v-card class="grey lighten-2 elevation-0">
          <!--<v-card-title primary-title class="primary">
            <h6 class="text-xs-left mb-0 white--text">Summary Card</h6>
            <v-spacer></v-spacer>
          </v-card-title>-->
          <v-layout row wrap class="grey lighten-2">
            <v-flex xs6 sm4 md2>
              <!-- data_row -->
              <div class="panel_body mt-2 mb-2 pl-1 pr-1 pt-2 pb-2 elevation-1">
                <p class="body-1 pa-0 mb-0 text-xs-center">Min. Date</p>
                <h6 class="mb-0 text-xs-center">2017-01-01</h6>
              </div>
            </v-flex>
            <v-flex xs6 sm4 md2>
              <!-- data_row -->
              <div class="panel_body mt-2 mb-2 pl-1 pr-1 pt-2 pb-2 elevation-1">
                <p class="body-1 pa-0 mb-0 text-xs-center">Max. Date</p>
                <h6 class="mb-0 text-xs-center">2017-12-31</h6>
              </div>
            </v-flex>
            <v-flex xs6 sm4 md2>
              <!-- data_row -->
              <div class="panel_body mt-2 mb-2 pl-1 pr-1 pt-2 pb-2 elevation-1">
                <p class="body-1 pa-0 mb-0 text-xs-center">Total Spend:</p>
                <h6 class="mb-0 text-xs-center">$2,069,887,267.69</h6>
              </div>
            </v-flex>
            <v-flex xs6 sm4 md2>
              <!-- data_row -->
              <div class="panel_body mt-2 mb-2 pl-1 pr-1 pt-2 pb-2 elevation-1">
                <p class="body-1 pa-0 mb-0 text-xs-center">Total Trxn:</p>
                <h6 class="mb-0 text-xs-center">71,105,836</h6>
              </div>
            </v-flex>
            <v-flex xs6 sm4 md2>
              <!-- data_row -->
              <div class="panel_body mt-2 mb-2 pl-1 pr-1 pt-2 pb-2 elevation-1">
                <p class="body-1 pa-0 mb-0 text-xs-center">Unit Quantity Sold:</p>
                <h6 class="mb-0 text-xs-center">96,808,065</h6>
              </div>
            </v-flex>
            <v-flex xs6 sm4 md2>
              <!-- data_row -->
              <div class="panel_body mt-2 mb-2 pl-1 pr-1 pt-2 pb-2 elevation-1">
                <p class="body-1 pa-0 mb-0 text-xs-center"># of Association Rules:</p>
                <h6 class="mb-0 text-xs-center">88</h6>
              </div>
            </v-flex>
          </v-layout>
        </v-card>
      </v-flex>
    </v-layout>
    <!-- //=====ROW1===== -->

    <!-- =====ROW2===== -->
    <v-layout row wrap class="mb-2">
      <!-- Title of this Row -->
      <v-flex xs12 class="mb-3">
        <v-layout row wrap>
          <v-flex xs12>
          <v-card class="white" style="width:100%;">
            <v-card-title primary-title class="primary">
              <h6 class="white--text text-xs-left mb-0">[Time Period] Spend per Product (at [Product Hierarchy] Level)</h6>
              <v-spacer></v-spacer>
              <v-btn
                  @click.native="getHelpSection()"
                  icon slot="activator"
                  class="success--text"
                  style="height:auto !important;">
                    <v-icon class="pa-0 ma-0 white--text icon_help">help_outline</v-icon>
              </v-btn>
            </v-card-title>
            <v-layout wrap row class="grey lighten-2 ml-0 mr-0">
              <!--Dropdown1-->
              <v-flex xs12 sm4 md2>
                <v-card flat class="pt-0 pb-0 grey lighten-2">
                  <v-layout row wrap>
                    <v-flex xs12>
                      <div class="primary--text text-xs-left pl-0 pr-0 pb-0 pt-2">
                        Time Period:
                      </div>
                    </v-flex>
                    <v-flex xs12>
                      <v-select v-model="tp"
                                v-bind:items="tpArray"
                                label="Select Time Period"
                                single-line
                                v-on:input="setTP()"
                                class="pl-1 pt-1 mt-1 mb-2 white elevation-1"
                                hide-details>
                      </v-select>
                    </v-flex>
                  </v-layout>
                </v-card>
              </v-flex>
              <!--//Dropdown1-->
              <!--Dropdown2-->
              <v-flex xs12 sm4 md3>
                <v-card flat class="grey lighten-2">
                  <v-layout row wrap>
                    <v-flex xs12>
                      <div class="primary--text text-xs-left pl-0 pr-0 pb-0 pt-2">
                        Metrics for Pareto Analysis:
                      </div>
                    </v-flex>
                    <v-flex xs12>
                        <v-select
                            v-bind:items="metrics"
                            v-model="metricSelect"
                            label="Select Metric"
                            single-line
                            bottom
                            hide-details
                            v-on:input="selectMetric()"
                            class="pl-1 pr-1 mt-1 mb-2 white elevation-1">
                        </v-select>
                    </v-flex>
                  </v-layout>
                </v-card>
              </v-flex>
              <!--//Dropdown2-->
              <!--Dropdown3-->
              <v-flex xs12 xs12 sm4 md3>
                <v-card flat class="grey lighten-2">
                  <v-layout row wrap>
                    <v-flex xs12>
                      <div class="primary--text text-xs-left pl-0 pr-0 pb-0 pt-2">
                        Filter for desired products:
                      </div>
                    </v-flex>
                    <v-flex xs12>
                        <v-select
                                v-bind:items="segments"
                                 v-model="segSelect"
                                 label="Select Segment"
                                 multiple
                                 single-line
                                 bottom
                                 hide-details
                                 v-on:input="selectAvg()"
                                 class="pl-1 pr-1 mt-1 mb-2 white elevation-1">
                        </v-select>
                    </v-flex>
                  </v-layout>
                </v-card>
              </v-flex>
              <!--//Dropdown3-->
            </v-layout>
            <div class="white pa-2">
              <img src="http://via.placeholder.com/350x150?text=Chart1" width="100%" height="400px">
            </div>
          </v-card>
          </v-flex>
        </v-layout>
        <v-layout row wrap>
        </v-layout>
      </v-flex>
      <!--// Title of this Row -->
    </v-layout>
    <!-- //=====ROW2===== -->

    <!-- =====ROW3===== -->
    <v-layout row wrap class="mb-5">
      <!-- Title of this Row -->
      <v-flex xs12 class="mb-3">
        <v-layout row wrap>
          <v-flex xs12>
          <v-card class="white" style="width:100%;">
            <v-card-title primary-title class="primary">
              <h6 class="white--text text-xs-left mb-0">b-Relevant Product Bundles (at [Product Hierarchy] Level)</h6>
              <v-spacer></v-spacer>
              <v-btn
                  @click.native="getHelpSection()"
                  icon slot="activator"
                  class="success--text"
                  style="height:auto !important;">
                    <v-icon class="pa-0 ma-0 white--text icon_help">help_outline</v-icon>
              </v-btn>
            </v-card-title>
            <v-layout wrap row class="grey lighten-2 ml-0 mr-0">
              <!--Dropdown1-->
              <v-flex xs12 sm6 md2>
                <v-card flat class="pt-0 pb-0 grey lighten-2">
                  <v-layout row wrap>
                    <v-flex xs12>
                      <div class="primary--text text-xs-left pl-0 pr-0 pb-0 pt-2">
                        Time Period:
                      </div>
                    </v-flex>
                    <v-flex xs12>
                      <v-select v-model="tp"
                                v-bind:items="tpArray"
                                label="Select Time Period"
                                single-line
                                v-on:input="setTP()"
                                class="pl-1 pt-1 mt-1 mb-2 white elevation-1"
                                hide-details>
                      </v-select>
                    </v-flex>
                  </v-layout>
                </v-card>
              </v-flex>
              <!--//Dropdown1-->
              <!--Dropdown2-->
              <v-flex xs12 sm6 md3>
                <v-card flat class="grey lighten-2">
                  <v-layout row wrap>
                    <v-flex xs12>
                      <div class="primary--text text-xs-left pl-0 pr-0 pb-0 pt-2">
                        Products Purchased (LHS):
                      </div>
                    </v-flex>
                    <v-flex xs12>
                        <v-select
                            v-bind:items="prodPurchArray"
                            v-model="prodPurchSelect"
                            label="Select The Item"
                            single-line
                            bottom
                            hide-details
                            v-on:input="selectProdPurch()"
                            class="pl-1 pr-1 mt-1 mb-2 white elevation-1">
                        </v-select>
                    </v-flex>
                  </v-layout>
                </v-card>
              </v-flex>
              <!--//Dropdown2-->
              <!--Dropdown3-->
              <v-flex xs12 xs12 sm6 md3>
                <v-card flat class="grey lighten-2">
                  <v-layout row wrap>
                    <v-flex xs12>
                      <div class="primary--text text-xs-left pl-0 pr-0 pb-0 pt-2">
                        Products Recommended (RHS):
                      </div>
                    </v-flex>
                    <v-flex xs12>
                        <v-select
                                v-bind:items="prodRecArray"
                                 v-model="prodRecSelect"
                                 label="Select Segment"
                                 multiple
                                 single-line
                                 bottom
                                 hide-details
                                 v-on:input="selectProdRec()"
                                 class="pl-1 pr-1 mt-1 mb-2 white elevation-1">
                        </v-select>
                    </v-flex>
                  </v-layout>
                </v-card>
              </v-flex>
              <!--//Dropdown3-->
            </v-layout>
            <div class="white pa-2">
              <img src="http://via.placeholder.com/350x150?text=Chart2" width="100%" height="400px">
            </div>
          </v-card>
          </v-flex>
        </v-layout>
        <v-layout row wrap>
        </v-layout>
      </v-flex>
      <!--// Title of this Row -->
    </v-layout>
    <!-- //=====ROW3===== -->


  </v-container>
</template>

<script>
  import HelpNavBRelevant from './HelpNavBRelevant.vue'
  export default {
    name: 'bRelevantSummary',
    components: {
      HelpNavBRelevant
    },
    data () {
      return {
        tp: 1,
        tpArray: [1, 2, 3, 4],
        segSelect: ['All'],
        segments: [
          'All',
          'Lapsed',
          'New',
          'Returning',
          'Reactivated'
        ],
        metricSelect: 'Spend',
        metrics: [
          'Spend',
          'Units',
          'Visits'
        ],
        prodPurchSelect: 'All',
        prodPurchArray: [
          'Item2',
          'Item3',
          'Item4'
        ],
        prodRecSelect: ['All'],
        prodRecArray: [
          'Item2',
          'Item3',
          'Item4'
        ]
      }
    },
    methods: {
      getHelpSection: function () {
        var hpNav = this.$refs.helpNav.$refs.helpNav
        hpNav.value = !hpNav.value
      }
    }
  }

</script>

<style scoped>

  .panel_body {
    border: 1px solid #E6EAEE;
    background-color: #FFFFFF;
    border-left: 3px solid #8EAC1D;
    /*border-left: 3px solid #0087aa;*/
    border-top-left-radius: 2px;
    border-bottom-left-radius: 2px;
  }

  .panel_body p, .panel_body h6 {
    color:#354052;
  }
</style>
