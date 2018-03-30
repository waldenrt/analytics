<template>
  <v-container fluid class="bRelevantComparison pl-3 pr-3">
    <!-- =====ROW1===== -->
    <HelpNavBRelevant ref="helpNav"></HelpNavBRelevant>

    <!-- =====ROW2===== -->
    <v-layout row wrap class="mb-3">
      <!-- Title of this Row -->
      <v-flex xs12 class="mb-0">
          <v-card class="white" style="width:100%;">
            <v-card-title primary-title class="primary">
              <h6 class="white--text text-xs-left mb-0">Product Bundles Per Time Period</h6>
              <v-spacer></v-spacer>
              <v-btn
                  @click.native="getHelpSection()"
                  icon slot="activator"
                  class="success--text"
                  style="height:auto !important;">
                    <v-icon class="pa-0 ma-0 white--text icon_help">help_outline</v-icon>
              </v-btn>
            </v-card-title>
            <v-layout row wrap class="grey lighten-2 ml-0 mr-0">
              <v-flex xs12 sm4 md2>
                <!-- data_row -->
                <div class="panel_body mt-2 mb-2 pl-1 pr-1 pt-2 pb-2 elevation-1">
                  <p class="body-1 pa-0 mb-0 text-xs-center">{{ tpData.year }}</p>
                  <h6 class="mb-0 text-xs-center info--text">{{ tpData.num }}</h6>
                </div>
              </v-flex>
              <v-flex xs12 sm4 md2 v-for="item in tpData.quarters">
                <!-- data_row -->
                <div class="panel_body mt-2 mb-2 pl-1 pr-1 pt-2 pb-2 elevation-1">
                  <p class="body-1 pa-0 mb-0 text-xs-center">{{ item.name }}'{{ tpData.year }}</p>
                  <h6 class="mb-0 text-xs-center info--text">{{ item.num }}</h6>
                </div>
              </v-flex>
            </v-layout>

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
                        Product Purchased (LHS):
                      </div>
                    </v-flex>
                    <v-flex xs12>
                        <v-select
                            v-bind:items="prodsPurch"
                            v-model="prodsSelect"
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
            </v-layout>
          </v-card>
      </v-flex>
      <!--// Title of this Row -->
    </v-layout>
    <!-- //=====ROW2===== -->

    <!-- =====ROW3===== -->
    <v-layout row wrap class="mb-5">
      <!-- Title of this Row -->
      <v-flex xs12>
        <v-layout row wrap class="pb-2">

          <!-- col1 -->
          <v-flex xs12 sm6 class="pt-3">
            <v-card-title primary-title class="primary">
              <h6 class="white--text text-xs-center mb-0 title_row">Product Bundles per Product Purchased & Time Periods</h6>
            </v-card-title>
            <v-layout wrap row class="grey lighten-2 ml-0 mr-0">
              <!--Dropdown1-->
              <v-flex xs12>
                <v-card flat class="pt-0 pb-0 grey lighten-2">
                  <v-layout row wrap>
                    <v-flex xs12>
                      <div class="primary--text text-xs-left pl-0 pr-0 pb-0 pt-2">
                        Time Period Legend:
                      </div>
                    </v-flex>
                    <v-flex xs12 class="pb-2">
                      <v-card class="white pa-2">
                        <div class="legend accent"></div>
                        <div class="inliner padR5">2017</div>
                        <div class="legend warning"></div>
                        <div class="inliner padR5">Q1'2017</div>
                        <div class="legend error"></div>
                        <div class="inliner padR5">Q2'2017</div>
                        <div class="legend info"></div>
                        <div class="inliner padR5">Q3'2017</div>
                        <div class="legend success"></div>
                        <div class="inliner padR5">Q4'2017</div>
                      </v-card>
                    </v-flex>
                  </v-layout>
                </v-card>
              </v-flex>
              <!--//Dropdown1-->
            </v-layout>
            <v-layout row wrap>
              <v-flex xs12>
                  <v-card class="white pa-3">
                    <img src="http://via.placeholder.com/350x150?text=Chart1" width="100%" height="600px">
                  </v-card>
              </v-flex>
            </v-layout>
          </v-flex>
          <!-- //col1 -->

          <!-- col2 -->
          <v-flex xs12 sm6 class="pt-3">
            <v-card-title primary-title class="primary">
              <h6 class="white--text text-xs-center mb-0 title_row">Product Bundles Appearances Across Time Periods</h6>
            </v-card-title>
            <v-layout wrap row class="grey lighten-2 ml-0 mr-0">
              <!--Dropdown1-->
              <v-flex xs6>
                <v-card flat class="pt-0 pb-0 grey lighten-2">
                  <v-layout row wrap>
                    <v-flex xs12>
                      <div class="primary--text text-xs-left pl-0 pr-0 pb-0 pt-2">
                        Occurs in ALL Time Periods:
                      </div>
                    </v-flex>
                    <v-flex xs12 class="pb-2">
                      <v-card class="white pa-2">
                        <div class="inliner error--text pr-1">X</div>
                        <div class="inliner padR5">FALSE</div>
                        <div class="inliner success--text pr-1">&#10003;</div>
                        <div class="inliner">TRUE</div>
                      </v-card>
                    </v-flex>
                  </v-layout>
                </v-card>
              </v-flex>
              <!--//Dropdown1-->
              <!--Dropdown2-->
              <v-flex xs6>
                <v-card flat class="pt-0 pb-0 grey lighten-2">
                  <v-layout row wrap>
                    <v-flex xs12>
                      <div class="primary--text text-xs-left pl-0 pr-0 pb-0 pt-2">
                        Rules in ALL Time Periods:
                      </div>
                    </v-flex>
                    <v-flex xs12>
                      <v-select v-model="tp"
                                v-bind:items="tpArray"
                                label="Select Time Period"
                                single-line
                                v-on:input="setTP()"
                                class="pl-1 pt-1 mt-0 mb-2 white elevation-1"
                                hide-details>
                      </v-select>
                    </v-flex>
                  </v-layout>
                </v-card>
              </v-flex>
              <!--//Dropdown2-->
            </v-layout>
            <v-layout row wrap>
              <v-flex xs12>
                  <v-card class="white pa-3">
                    <img src="http://via.placeholder.com/350x150?text=Chart2" width="100%" height="600px">
                  </v-card>
              </v-flex>
            </v-layout>
          </v-flex>
          <!-- //col2 -->

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
    assc_rule: 'bRelevantDetails',
    components: {
      HelpNavBRelevant
    },
    data () {
      return {
        prodsSelect: [],
        tp: [],
        tpArray: [],
        prodsPurch: [],
        tpData: {
          year: '2017',
          num: 95,
          quarters: [
            { name: 'Q1', num: 100 },
            { name: 'Q2', num: 105 },
            { name: 'Q3', num: 104 },
            { name: 'Q4', num: 98 }
          ]
        }
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

  .panel_body p, .panel_body h6 { color:#354052; }

  .title_row { margin:0 auto; }
</style>
