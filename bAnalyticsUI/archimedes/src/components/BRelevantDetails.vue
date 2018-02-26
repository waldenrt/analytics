<template>
  <v-container fluid class="bRelevantDetails pl-3 pr-3">
    <!-- =====ROW1===== -->
    <HelpNavBRelevant ref="helpNav"></HelpNavBRelevant>

    <!-- =====ROW2===== -->
    <v-layout row wrap class="mb-3">
      <!-- Title of this Row -->
      <v-flex xs12 class="mb-0">
        <v-layout row wrap>
          <v-flex xs12>
          <v-card class="white" style="width:100%;">
            <v-card-title primary-title class="primary">
              <h6 class="white--text text-xs-left mb-0">[Time Period] Product Bundles Detail View (at [Product Hierarchy] Level)</h6>
              <v-spacer></v-spacer>
              <v-btn
                  @click.native="getHelpSection()"
                  icon slot="activator"
                  class="success--text"
                  style="height:auto !important;">
                    <v-icon class="pa-0 ma-0 white--text icon_help">help_outline</v-icon>
              </v-btn>
            </v-card-title>
            <v-layout row wrap class="pl-3 pr-3 pb-2">
              <v-flex xs12 sm6 md4 class="pt-3">
                <div class="title primary--text text-xs-center">Quantile Pareto Analysis</div>
                <p class="text-xs-center">A display of the number of rules associated <br />with a specific item (at the class level).</p>
                <img src="http://via.placeholder.com/350x150?text=Chart1" width="100%" height="350px">
              </v-flex>
              <v-flex xs12 sm6 md4 class="pt-3">
                <div class="title primary--text text-xs-center">Association Metrics Scatterplot:</div>
                <p class="text-xs-center">Identify the Support, Confidence, and Life levels <br />for each rule. Color legends and value filters at left.</p>
                <img src="http://via.placeholder.com/350x150?text=Chart2" width="100%" height="350px">
              </v-flex>
              <v-flex xs12 sm12 md4 class="pt-3">
                <div class="title primary--text text-xs-center">Distribution of Lift across Product Bundles:</div>
                <p class="text-xs-center">Analyze the central tendency and spread of <br />Lift in order find acceptance thresholds.</p>
                <img src="http://via.placeholder.com/350x150?text=Chart3" width="100%" height="350px">
              </v-flex>
            </v-layout>
            <v-layout wrap row class="grey lighten-2 ml-0 mr-0">
              <!--Dropdown1-->
              <v-flex xs12 sm4 md2 lg1>
                <v-card flat class="pt-0 pb-0 grey lighten-2">
                  <v-layout row wrap>
                    <v-flex xs12>
                      <div class="info--text text-xs-left pl-0 pr-0 pb-0 pt-2">
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
                      <div class="info--text text-xs-left pl-0 pr-0 pb-0 pt-2">
                        Select Association Metric or Interestingness Measure:
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
              <v-flex xs12 xs12 sm4 md3 lg2>
                <v-card flat class="grey lighten-2">
                  <v-layout row wrap>
                    <v-flex xs12>
                      <div class="info--text text-xs-left pl-0 pr-0 pb-0 pt-2">
                        Filter product by item in basket:
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
              <!--Dropdown4-->
              <v-flex xs12 xs12 sm4 md3 lg2>
                <v-card flat class="grey lighten-2">
                  <v-layout row wrap>
                    <v-flex xs12>
                      <div class="info--text text-xs-left pl-0 pr-0 pb-0 pt-2">
                        Filter by number of items in rule:
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
              <!--//Dropdown4-->
              <!--Dropdown5-->
              <v-flex xs12 xs12 sm4 md3 lg2>
                <v-card flat class="grey lighten-2">
                  <v-layout row wrap>
                    <v-flex xs12>
                      <div class="info--text text-xs-left pl-0 pr-0 pb-0 pt-2">
                        Lift Color Legend:
                      </div>
                    </v-flex>
                    <v-flex xs12>
                        LEGEND GOES HERE
                    </v-flex>
                  </v-layout>
                </v-card>
              </v-flex>
              <!--//Dropdown5-->
              <!--Dropdown6-->
              <v-flex xs12 xs12 sm4 md3 lg2>
                <v-card flat class="grey lighten-2">
                  <v-layout row wrap>
                    <v-flex xs12>
                      <div class="info--text text-xs-left pl-0 pr-0 pb-0 pt-2">
                        Life (should be > 1):
                      </div>
                    </v-flex>
                    <v-flex xs12>
                        UI SLIDER
                    </v-flex>
                  </v-layout>
                </v-card>
              </v-flex>
              <!--//Dropdown6-->
            </v-layout>
          </v-card>
          </v-flex>
        </v-layout>
      </v-flex>
      <!--// Title of this Row -->
    </v-layout>
    <!-- //=====ROW2===== -->

    <!-- =====ROW3===== -->
    <v-layout row wrap class="mb-5">
      <!-- Title of this Row -->
      <v-flex xs12 class="mb-3 pl-4 pr-4">
        <v-layout row wrap>
          <v-data-table
              v-bind:headers="headers"
              :items="items"
              hide-actions
              class="elevation-1 grey lighten-2"
            >
            <template slot="items" scope="props">
              <td class="white">{{ props.item.assc_rule }}</td>
              <td class="text-xs-right white">{{ props.item.set_length }}</td>
              <td class="text-xs-right white">{{ props.item.support }}%</td>
              <td class="text-xs-right white">{{ props.item.confidence }}%</td>
              <td class="text-xs-right white">{{ props.item.lift }}</td>
            </template>
          </v-data-table>
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
        metricSelect: 'Support',
        metrics: [
          'Support',
          'Confidence',
          'Lift',
          'Added Value',
          'Chi-Squared',
          'Odds Ratio'
        ],
        prodPurchSelect: 'All',
        prodPurchArray: [
          'Item2',
          'Item3',
          'Item4'
        ],
        headers: [
          {
            text: 'Association Rule',
            left: true,
            sortable: false,
            value: 'assc_rule'
          },
          { text: 'Item Set Length', value: 'set_length' },
          { text: 'Support', value: 'support' },
          { text: 'Confidence', value: 'confidence' },
          { text: 'Lift', value: 'lift' }
        ],
        items: [
          {
            value: false,
            assc_rule: 'M COTTON BLEND PANTS => M COTTON BLEND JACKETS',
            set_length: 2.0,
            support: 0.52,
            confidence: 26.09,
            lift: 942.75
          },
          {
            value: false,
            assc_rule: 'M FLAT FRONT => M SS PATTERN SHIRTS',
            set_length: 2.0,
            support: 0.11,
            confidence: 2.82,
            lift: 30.97
          },
          {
            value: false,
            assc_rule: 'M LS PATTERN CASUAL SHIRTS => M LS SOLID CASUAL SHIRTS',
            set_length: 2.0,
            support: 0.12,
            confidence: 5.26,
            lift: 130.01
          },
          {
            value: false,
            assc_rule: 'M LS PATTERN DRESS SHIRTS => M LS PATTERN CASUAL SHIRTS',
            set_length: 2.0,
            support: 0.12,
            confidence: 1.85,
            lift: 12.77
          },
          {
            value: false,
            assc_rule: 'M LS PATTERN DRESS SHIRTS => M SLIM',
            set_length: 2.0,
            support: 0.17,
            confidence: 2.73,
            lift: 10.44
          },
          {
            value: false,
            assc_rule: 'M LS PATTERN DRESS SHIRTS => M SOCKS',
            set_length: 2.0,
            support: 0.11,
            confidence: 1.75,
            lift: 8.39
          },
          {
            value: false,
            assc_rule: 'M LS PATTERN DRESS SHIRTS => M TIES',
            set_length: 2.0,
            support: 0.23,
            confidence: 3.60,
            lift: 13.58
          },
          {
            value: false,
            assc_rule: 'M LS SOLID DRESS SHIRTS => M BELTS',
            set_length: 2.0,
            support: 0.12,
            confidence: 1.62,
            lift: 8.28
          },
          {
            value: false,
            assc_rule: 'M LS SOLID DRESS SHIRTS => M EXTRA SLIM',
            set_length: 2.0,
            support: 0.14,
            confidence: 1.84,
            lift: 18.12
          },
          {
            value: false,
            assc_rule: 'M LS SOLID DRESS SHIRTS => M LS PATTERN DRESS SHIRTS',
            set_length: 2.0,
            support: 0.87,
            confidence: 11.49,
            lift: 23.70
          }
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

</style>
