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
              <v-flex xs12 sm4 md4>
                <!-- data_row -->
                <div class="panel_body mt-2 mb-2 pl-1 pr-1 pt-2 pb-2 elevation-1">
                  <p class="body-1 pa-0 mb-0 text-xs-center">{{ tpData.year }}</p>
                  <h6 class="mb-0 text-xs-center">{{ tpData.num }}</h6>
                </div>
              </v-flex>
              <v-flex xs12 sm4 md2
                  v-for="item in tpData.quarters">
                <!-- data_row -->
                <div class="panel_body mt-2 mb-2 pl-1 pr-1 pt-2 pb-2 elevation-1">
                  <p class="body-1 pa-0 mb-0 text-xs-center">{{ item.name }}</p>
                  <h6 class="mb-0 text-xs-center">{{ item.num }}</h6>
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
              <v-flex xs12 sm4 md2>
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
      <v-flex xs12 class="mb-3 pl-4 pr-4">
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

  .panel_body p, .panel_body h6 {
    color:#354052;
  }
</style>
