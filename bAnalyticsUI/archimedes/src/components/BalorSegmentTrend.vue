<template>
  <v-container fluid class="balorSegmentTrend pl-3 pr-3">

    <!-- =====ROW1===== -->
    <HelpNavBalor ref="helpNav"></HelpNavBalor>
    <v-layout row wrap class="pt-0 mb-3">
      <v-flex xs12>
        <v-card class="white">
          <v-card-title primary-title class="primary">
            <h6 class="white--text text-xs-left mb-0">Segment Trends</h6>
            <v-spacer></v-spacer>
            <v-btn
                @click.native="getHelpSection()"
                icon slot="activator"
                class="success--text"
                style="height:auto !important;">
                  <v-icon class="pa-0 ma-0 white--text icon_help">help_outline</v-icon>
            </v-btn>
          </v-card-title>
          <!-- dropdown_row [a] -->
          <v-card-row class="grey lighten-2">
            <!--Dropdown1-->
            <v-flex xs12 sm3 class="pl-0">
              <v-card flat class="pl-2 pr-2 pt-0 pb-0 grey lighten-2">
                <v-layout row wrap>
                  <v-flex xs12>
                    <div class="primary--text text-xs-left pl-0 pr-0 pb-0 pt-2">
                      BALOR dimension:
                    </div>
                  </v-flex>
                  <v-flex xs12>
                      <v-select v-bind:items="dimensions"
                                v-model="dimensionSelect"
                                label="Select Dimension"
                                single-line
                                bottom
                                v-on:input="selectDimension()"
                                class="pl-1 pr-1 mt-1 mb-2 white elevation-1"
                                hide-details>
                      </v-select>
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
                      BALOR average:
                    </div>
                  </v-flex>
                  <v-flex xs12>
                      <v-select
                          v-if="dimensionSelect === 'Customer'"
                          v-bind:items="custDim"
                          v-model="avgSelect"
                          label="Select Average"
                          single-line
                          bottom
                          hide-details
                          v-on:input="selectAvg()"
                          class="pl-1 pr-1 mt-1 mb-2 white elevation-1">
                      </v-select>
                      <v-select
                          v-else-if="dimensionSelect === 'Visits'"
                          v-bind:items="visitDim"
                          v-model="avgSelect"
                          label="Select Average"
                          single-line
                          bottom
                          hide-details
                          v-on:input="selectAvg()"
                          class="pl-1 pr-1 mt-1 mb-2 white elevation-1">
                      </v-select>
                      <v-select
                          v-else-if="dimensionSelect === 'Spend'"
                          v-bind:items="spendDim"
                          v-model="avgSelect"
                          label="Select Average"
                          single-line
                          bottom
                          hide-details
                          v-on:input="selectAvg()"
                          class="pl-1 pr-1 mt-1 mb-2 white elevation-1">
                      </v-select>
                      <v-select
                          v-else-if="dimensionSelect === 'Discount'"
                          v-bind:items="discDim"
                          v-model="avgSelect"
                          label="Select Average"
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
            <!--//Dropdown2-->
            <!--Dropdown3-->
            <v-flex xs12 sm3>
              <v-card flat class="pl-2 pr-2 grey lighten-2">
                <v-layout row wrap>
                  <v-flex xs12>
                    <div class="primary--text text-xs-left pl-0 pr-0 pb-0 pt-2">
                      BALOR segment:
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
          </v-card-row>
          <!-- //dropdown_row [a] -->
          <!-- legend_row [a] -->
          <v-layout row wrap>
            <v-flex xs12>
              <v-card flat class="white pa-3">
                <div class="legend legend_color1"></div>
                <div class="inliner padR5">Lapsed</div>
                <div class="legend legend_color2"></div>
                <div class="inliner padR5">New</div>
                <div class="legend legend_color3"></div>
                <div class="inliner padR5">Returning</div>
                <div class="legend legend_color4"></div>
                <div class="inliner padR5">Reactivated</div>
              </v-card>
            </v-flex>
          </v-layout>
          <!-- //legend_row [a] -->
          <!--chart-->
          <v-layout row wrap class="pa-2">
            <v-flex xs12 fill-height>
              <pareto-chart :chart-data="totalBars" :options="paretoOptions" class="bar_chart1"></pareto-chart>
            </v-flex>
          </v-layout>
          <!--//chart-->
        </v-card>
      </v-flex>
    </v-layout>
    <!-- //=====ROW1===== -->

    <!-- =====ROW2===== -->
    <v-layout row wrap class="mt-4 mb-5">
      <v-flex xs12>
        <v-card class="white">
        <v-card-title primary-title class="primary">
          <h6 class="white--text text-xs-left mb-0">Segment Profiles</h6>
          <v-spacer></v-spacer>
          <v-btn
              @click.native="getHelpSection()"
              icon slot="activator"
              class="success--text"
              style="height:auto !important;">
                <v-icon class="pa-0 ma-0 white--text icon_help">help_outline</v-icon>
          </v-btn>
        </v-card-title>
        <!-- dropdown_row [a] -->
        <v-card-row class="grey lighten-2">
          <!--Dropdown1-->
          <v-flex xs12 sm3 class="pl-0">
            <v-card flat class="pl-2 pr-2 pt-0 pb-0 grey lighten-2">
              <v-layout row wrap>
                <v-flex xs12>
                  <div class="primary--text text-xs-left pl-0 pr-0 pb-0 pt-2">
                    BALOR time period:
                  </div>
                </v-flex>
                <v-flex xs12>
                    <v-select
                    v-bind:items="tpArray"
                    v-model="tpSelect"
                    label="Select BALOR Time Period"
                    single-line
                    bottom
                    hide-details
                    v-on:input="updateTPMetric"
                    class="pl-1 pr-1 mt-1 mb-2 white elevation-1">
                    </v-select>
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
                    BALOR metric:
                  </div>
                </v-flex>
                <v-flex xs12>
                    <v-select
                    v-bind:items="avgList"
                    v-model="tpAvgSelect"
                    label="Select BALOR Metric"
                    single-line
                    bottom
                    hide-details
                    v-on:input="updateTPMetric"
                        class="pl-1 pr-1 mt-1 mb-2 white elevation-1">
                    </v-select>
                </v-flex>
              </v-layout>
            </v-card>
          </v-flex>
          <!--//Dropdown2-->
        </v-card-row>
        <!-- //dropdown_row [a] -->
        <!--chart-->
        <v-layout row wrap class="mb-3 pa-2">
          <v-flex xs12 fill-height>
            <horizontal-chart :chart-data="avgBars" :options="avgOptions" class="bar_chart1"></horizontal-chart>
          </v-flex>
        </v-layout>
        <!--//chart-->
      </v-card>
      </v-flex>
    </v-layout>
    <!-- //=====ROW2===== -->

  </v-container>
</template>

<script>
  import HorizontalChart from './balorCharts/HorizontalChart'
  import BarChart from './balorCharts/BarChart'
  import ParetoChart from './balorCharts/ParetoChart'
  import {segment} from './javascript/balor.service'
  import HelpNavBalor from './HelpNavBalor.vue'

  export default {
    name: 'balorSegmentTrend',
    components: {
      BarChart,
      HorizontalChart,
      ParetoChart,
      HelpNavBalor
    },
    data () {
      return {
        msg: 'BALOR Segment Trend Charts will go here!',
        dimensions: [
          'Customer', 'Visits', 'Spend', 'Discount'
        ],
        custDim: [
          'Avg Spend per Customer',
          'Avg Items per Customer',
          'Avg Discount per Customer',
          'Avg Transactions per Customer'
        ],
        visitDim: [
          'Avg Spend per Basket',
          'Avg Items per Basket',
          'Avg Discount per Basket'
        ],
        spendDim: [
          'Avg Spend per Customer',
          'Avg Spend per Basket'
        ],
        discDim: [
          'Avg Discount per Customer',
          'Avg Discount per Basket'
        ],
        segments: [
          'All',
          'Lapsed',
          'New',
          'Returning',
          'Reactivated'
        ],
        dimensionSelect: 'Customer',
        avgSelect: 'Avg Spend per Customer',
        tpSelect: 1,
        tpAvgSelect: 'Spend per Customer',
        segSelect: ['All'],
        avgList: [
          'Spend per Customer',
          'Items per Customer',
          'Discount per Customer',
          'Transactions per Customer',
          'Spend per Basket',
          'Items per Basket',
          'Discount per Basket'
        ],
        incomingJson: {},
        totalBars: {},
        avgBars: {},
        tpArray: [],
        dateArray: [],
        ttlReturnCust: [],
        ttlLapsedCust: [],
        ttlNewCust: [],
        ttlReactCust: [],
        ttlReturnTxn: [],
        ttlLapsedTxn: [],
        ttlNewTxn: [],
        ttlReactTxn: [],
        ttlReturnItem: [],
        ttlLapsedItem: [],
        ttlNewItem: [],
        ttlReactItem: [],
        ttlReturnSpend: [],
        ttlLapsedSpend: [],
        ttlNewSpend: [],
        ttlReactSpend: [],
        ttlReturnDisc: [],
        ttlLapsedDisc: [],
        ttlNewDisc: [],
        ttlReactDisc: [],
        avgNewCustSpend: [],
        avgNewCustItems: [],
        avgNewCustDisc: [],
        avgNewCustTxn: [],
        avgNewVisitSpend: [],
        avgNewVisitItems: [],
        avgNewVisitDisc: [],
        avgRetCustSpend: [],
        avgRetCustItems: [],
        avgRetCustDisc: [],
        avgRetCustTxn: [],
        avgRetVisitSpend: [],
        avgRetVisitItems: [],
        avgRetVisitDisc: [],
        avgReactCustSpend: [],
        avgReactCustItems: [],
        avgReactCustDisc: [],
        avgReactCustTxn: [],
        avgReactVisitSpend: [],
        avgReactVisitItems: [],
        avgReactVisitDisc: [],
        avgLapCustSpend: [],
        avgLapCustItems: [],
        avgLapCustDisc: [],
        avgLapCustTxn: [],
        avgLapVisitSpend: [],
        avgLapVisitItems: [],
        avgLapVisitDisc: []
      }
    },
    computed: {
      jsonMsg: function () {
        return this.incomingJson.data
      },
      jobId: function () {
        return this.$store.state.jobKey
      },
      jobApp: function () {
        return this.$store.state.jobApp
      },
      paretoOptions: function () {
        let opts = {
          responsive: true,
          maintainAspectRatio: false,
          scales: {
            yAxes: [{
              id: 'left-y-axis',
              type: 'linear',
              position: 'left',
              scaleLabel: {
                display: true,
                labelString: 'Total ' + this.dimensionSelect
              }
            }, {
              id: 'right-y-axis',
              type: 'linear',
              position: 'right',
              scaleLabel: {
                display: true,
                labelString: this.avgSelect
              }
            }],
            xAxes: [{
              barPercentage: 0.8,
              scaleLabel: {
                display: true,
                labelString: 'BALOR Period'
              }
            }]
          },
          legend: {
            display: false
          }
        }
        return opts
      },
      avgOptions: function () {
        let options = {
          responsive: true,
          maintainAspectRatio: false,
          legend: {
            display: false
          },
          scales: {
            xAxes: [{
              gridLines: {
                display: true,
                color: '#848C98'
              },
              scaleLabel: {
                display: true,
                labelString: this.tpAvgSelect
              }
            }],
            yAxes: [{
              scaleLabel: {
                display: true,
                labelString: 'BALOR Segment'
              }
            }]
          }
        }
        return options
      }
    },
    mounted () {
      if (this.jobApp === 'Balor' || this.jobApp === 'balor') {
        this.$store.commit('switchApp', {module: 'BALOR'})
        this.getResults()
      } else {
        alert('Please select a Balor job from Job History')
        this.$router.push('/Balor/')
      }
    },
    methods: {
      getHelpSection: function () {
        var hpNav = this.$refs.helpNav.$refs.helpNav
        hpNav.value = !hpNav.value
      },
      getResults () {
        segment(this.jobId)
          .catch(err => {
            alert('Could not get Segment results. ' + err.message.toString())
          })
          .then((response) => {
            this.incomingJson = response.data
            console.log(this.incomingJson)
            this.parseJson()
            this.createBars()
          })
      },
      parseJson () {
        var tempCustRet = []
        var tempCustLap = []
        var tempCustNew = []
        var tempCustReact = []
        var tempTxnRet = []
        var tempTxnLap = []
        var tempTxnNew = []
        var tempTxnReact = []
        var tempItemRet = []
        var tempItemLap = []
        var tempItemNew = []
        var tempItemReact = []
        var tempSpendRet = []
        var tempSpendLap = []
        var tempSpendNew = []
        var tempSpendReact = []
        var tempDiscRet = []
        var tempDiscLap = []
        var tempDiscNew = []
        var tempDiscReact = []

        var tempNewCustSpend = []
        var tempNewCustItems = []
        var tempNewCustDisc = []
        var tempNewCustTxn = []
        var tempNewVisitSpend = []
        var tempNewVisitItems = []
        var tempNewVisitDisc = []
        var tempRetCustSpend = []
        var tempRetCustItems = []
        var tempRetCustDisc = []
        var tempRetCustTxn = []
        var tempRetVisitSpend = []
        var tempRetVisitItems = []
        var tempRetVisitDisc = []
        var tempReactCustSpend = []
        var tempReactCustItems = []
        var tempReactCustDisc = []
        var tempReactCustTxn = []
        var tempReactVisitSpend = []
        var tempReactVisitItems = []
        var tempReactVisitDisc = []
        var tempLapCustSpend = []
        var tempLapCustItems = []
        var tempLapCustDisc = []
        var tempLapCustTxn = []
        var tempLapVisitSpend = []
        var tempLapVisitItems = []
        var tempLapVisitDisc = []

        var tempTp = []
        var tempDate = []

        // Bar chart arrays
        for (var i = 0; i < this.jsonMsg.timePeriods.length; i++) {
          tempTp.push(this.jsonMsg.timePeriods[i].timePeriod)
          tempDate.push(this.jsonMsg.timePeriods[i].anchorDate.substring(1, this.jsonMsg.timePeriods[i].anchorDate.length - 1))

          tempCustRet.push(this.jsonMsg.timePeriods[i].returnCustInfo.returnCustCount.toFixed(2))
          tempCustLap.push(this.jsonMsg.timePeriods[i].lapsedCustInfo.lapsedCustCount.toFixed(2))
          tempCustNew.push(this.jsonMsg.timePeriods[i].newCustInfo.newCustCount.toFixed(2))
          tempCustReact.push(this.jsonMsg.timePeriods[i].reactCustInfo.reactCustCount.toFixed(2))

          tempTxnRet.push(this.jsonMsg.timePeriods[i].returnCustInfo.returnTxnCount.toFixed(2))
          tempTxnLap.push(this.jsonMsg.timePeriods[i].lapsedCustInfo.lapsedTxnCount.toFixed(2))
          tempTxnNew.push(this.jsonMsg.timePeriods[i].newCustInfo.newTxnCount.toFixed(2))
          tempTxnReact.push(this.jsonMsg.timePeriods[i].reactCustInfo.reactTxnCount.toFixed(2))

          tempItemRet.push(this.jsonMsg.timePeriods[i].returnCustInfo.returnItemQty.toFixed(2))
          tempItemLap.push(this.jsonMsg.timePeriods[i].lapsedCustInfo.lapsedItemQty.toFixed(2))
          tempItemNew.push(this.jsonMsg.timePeriods[i].newCustInfo.newItemQty.toFixed(2))
          tempItemReact.push(this.jsonMsg.timePeriods[i].reactCustInfo.reactItemQty.toFixed(2))

          tempSpendRet.push(this.jsonMsg.timePeriods[i].returnCustInfo.returnTxnAmt.toFixed(2))
          tempSpendLap.push(this.jsonMsg.timePeriods[i].lapsedCustInfo.lapsedTxnAmt.toFixed(2))
          tempSpendNew.push(this.jsonMsg.timePeriods[i].newCustInfo.newTxnAmt.toFixed(2))
          tempSpendReact.push(this.jsonMsg.timePeriods[i].reactCustInfo.reactTxnAmt.toFixed(2))

          tempDiscRet.push(this.jsonMsg.timePeriods[i].returnCustInfo.returnDiscAmt.toFixed(2))
          tempDiscLap.push(this.jsonMsg.timePeriods[i].lapsedCustInfo.lapsedDiscAmt.toFixed(2))
          tempDiscNew.push(this.jsonMsg.timePeriods[i].newCustInfo.newDiscAmt.toFixed(2))
          tempDiscReact.push(this.jsonMsg.timePeriods[i].reactCustInfo.reactDiscAmt.toFixed(2))

          tempNewCustSpend.push(this.jsonMsg.timePeriods[i].newCustInfo.newCustSpendAvg.toFixed(2))
          tempNewCustItems.push(this.jsonMsg.timePeriods[i].newCustInfo.newCustItemAvg.toFixed(2))
          tempNewCustDisc.push(this.jsonMsg.timePeriods[i].newCustInfo.newCustDiscAvg.toFixed(2))
          tempNewCustTxn.push(this.jsonMsg.timePeriods[i].newCustInfo.newCustVisitAvg.toFixed(2))
          tempNewVisitSpend.push(this.jsonMsg.timePeriods[i].newCustInfo.newVisitSpendAvg.toFixed(2))
          tempNewVisitItems.push(this.jsonMsg.timePeriods[i].newCustInfo.newVisitItemAvg.toFixed(2))
          tempNewVisitDisc.push(this.jsonMsg.timePeriods[i].newCustInfo.newVisitDiscAvg.toFixed(2))

          tempRetCustSpend.push(this.jsonMsg.timePeriods[i].returnCustInfo.returnCustSpendAvg.toFixed(2))
          tempRetCustItems.push(this.jsonMsg.timePeriods[i].returnCustInfo.returnCustItemAvg.toFixed(2))
          tempRetCustDisc.push(this.jsonMsg.timePeriods[i].returnCustInfo.returnCustDiscAvg.toFixed(2))
          tempRetCustTxn.push(this.jsonMsg.timePeriods[i].returnCustInfo.returnCustVisitAvg.toFixed(2))
          tempRetVisitSpend.push(this.jsonMsg.timePeriods[i].returnCustInfo.returnVisitSpendAvg.toFixed(2))
          tempRetVisitItems.push(this.jsonMsg.timePeriods[i].returnCustInfo.returnVisitItemAvg.toFixed(2))
          tempRetVisitDisc.push(this.jsonMsg.timePeriods[i].returnCustInfo.returnVisitDiscAvg.toFixed(2))

          tempReactCustSpend.push(this.jsonMsg.timePeriods[i].reactCustInfo.reactCustSpendAvg.toFixed(2))
          tempReactCustItems.push(this.jsonMsg.timePeriods[i].reactCustInfo.reactCustItemAvg.toFixed(2))
          tempReactCustDisc.push(this.jsonMsg.timePeriods[i].reactCustInfo.reactCustDiscAvg.toFixed(2))
          tempReactCustTxn.push(this.jsonMsg.timePeriods[i].reactCustInfo.reactCustVisitAvg.toFixed(2))
          tempReactVisitSpend.push(this.jsonMsg.timePeriods[i].reactCustInfo.reactVisitSpendAvg.toFixed(2))
          tempReactVisitItems.push(this.jsonMsg.timePeriods[i].reactCustInfo.reactVisitItemAvg.toFixed(2))
          tempReactVisitDisc.push(this.jsonMsg.timePeriods[i].reactCustInfo.reactVisitDiscAvg.toFixed(2))

          tempLapCustSpend.push(this.jsonMsg.timePeriods[i].lapsedCustInfo.lapsedCustSpendAvg.toFixed(2))
          tempLapCustItems.push(this.jsonMsg.timePeriods[i].lapsedCustInfo.lapsedCustItemAvg.toFixed(2))
          tempLapCustDisc.push(this.jsonMsg.timePeriods[i].lapsedCustInfo.lapsedCustDiscAvg.toFixed(2))
          tempLapCustTxn.push(this.jsonMsg.timePeriods[i].lapsedCustInfo.lapsedCustVisitAvg.toFixed(2))
          tempLapVisitSpend.push(this.jsonMsg.timePeriods[i].lapsedCustInfo.lapsedVisitSpendAvg.toFixed(2))
          tempLapVisitItems.push(this.jsonMsg.timePeriods[i].lapsedCustInfo.lapsedVisitItemAvg.toFixed(2))
          tempLapVisitDisc.push(this.jsonMsg.timePeriods[i].lapsedCustInfo.lapsedVisitDiscAvg.toFixed(2))
        }

        this.tpArray = tempTp
        this.dateArray = tempDate
        this.ttlReturnCust = tempCustRet
        this.ttlLapsedCust = tempCustLap
        this.ttlNewCust = tempCustNew
        this.ttlReactCust = tempCustReact
        this.ttlReturnTxn = tempTxnRet
        this.ttlLapsedTxn = tempTxnLap
        this.ttlNewTxn = tempTxnNew
        this.ttlReactTxn = tempTxnReact
        this.ttlReturnItem = tempItemRet
        this.ttlLapsedItem = tempItemLap
        this.ttlNewItem = tempItemNew
        this.ttlReactItem = tempItemReact
        this.ttlReturnSpend = tempSpendRet
        this.ttlLapsedSpend = tempSpendLap
        this.ttlNewSpend = tempSpendNew
        this.ttlReactSpend = tempSpendReact
        this.ttlReturnDisc = tempDiscRet
        this.ttlLapsedDisc = tempDiscLap
        this.ttlNewDisc = tempDiscNew
        this.ttlReactDisc = tempDiscReact

        this.avgNewCustSpend = tempNewCustSpend
        this.avgNewCustItems = tempNewCustItems
        this.avgNewCustDisc = tempNewCustDisc
        this.avgNewCustTxn = tempNewCustTxn
        this.avgNewVisitSpend = tempNewVisitSpend
        this.avgNewVisitItems = tempNewVisitItems
        this.avgNewVisitDisc = tempNewVisitDisc
        this.avgRetCustSpend = tempRetCustSpend
        this.avgRetCustItems = tempRetCustItems
        this.avgRetCustDisc = tempRetCustDisc
        this.avgRetCustTxn = tempRetCustTxn
        this.avgRetVisitSpend = tempRetVisitSpend
        this.avgRetVisitItems = tempRetVisitItems
        this.avgRetVisitDisc = tempRetVisitDisc
        this.avgReactCustSpend = tempReactCustSpend
        this.avgReactCustItems = tempReactCustItems
        this.avgReactCustDisc = tempReactCustDisc
        this.avgReactCustTxn = tempReactCustTxn
        this.avgReactVisitSpend = tempReactVisitSpend
        this.avgReactVisitItems = tempReactVisitItems
        this.avgReactVisitDisc = tempReactVisitDisc
        this.avgLapCustSpend = tempLapCustSpend
        this.avgLapCustItems = tempLapCustItems
        this.avgLapCustDisc = tempLapCustDisc
        this.avgLapCustTxn = tempLapCustTxn
        this.avgLapVisitSpend = tempLapVisitSpend
        this.avgLapVisitItems = tempLapVisitItems
        this.avgLapVisitDisc = tempLapVisitDisc
      },

      createBars () {
        this.totalBars = {
          labels: this.dateArray,
          datasets: []
        }

        if (this.segSelect.includes('All')) {
          this.totalBars.datasets.push({
            data: this.ttlReturnCust,
            label: 'Returning',
            backgroundColor: '#F7970E'
          }, {
            data: this.ttlLapsedCust,
            label: 'Lapsed',
            backgroundColor: '#D63A09'
          }, {
            data: this.ttlNewCust,
            label: 'New',
            backgroundColor: '#8EAC1D'
          }, {
            data: this.ttlReactCust,
            label: 'Reactivated',
            backgroundColor: '#0087AA'
          }, {
            data: this.avgRetCustSpend,
            label: 'Spend per Returning Customer',
            type: 'line',
            yAxisID: 'right-y-axis',
            backgroundColor: '#F7970E',
            fill: false
          }, {
            data: this.avgLapCustSpend,
            label: 'Spend per Lapsed Customer',
            type: 'line',
            yAxisID: 'right-y-axis',
            backgroundColor: '#D63A09',
            fill: false
          }, {
            data: this.avgNewCustSpend,
            label: 'Spend per New Customer',
            type: 'line',
            yAxisID: 'right-y-axis',
            backgroundColor: '#8EAC1D',
            fill: false
          }, {
            data: this.avgReactCustSpend,
            label: 'Spend per Reactivated Customer',
            type: 'line',
            yAxisID: 'right-y-axis',
            backgroundColor: '#0087AA',
            fill: false
          })
        }
        this.avgBars = {
          type: 'horizontalBar',
          labels: ['Lapsed', 'New', 'Reactivated', 'Returning'],
          datasets: [
            {
              data: [this.avgLapCustSpend[this.tpSelect - 1], this.avgNewCustSpend[this.tpSelect - 1], this.avgReactCustSpend[this.tpSelect - 1], this.avgRetCustSpend[this.tpSelect - 1]],
              label: 'Avg Spend Per Customer',
              backgroundColor: ['#D63A09', '#8EAC1D', '#0087AA', '#F7970E']
            }
          ]
        }
      },

      selectDimension () {
        if (this.dimensionSelect === 'Customer') {
          this.avgSelect = 'Avg Spend per Customer'
          this.totalBars = {
            labels: this.dateArray,
            datasets: []
          }

          if (this.segSelect.includes('All')) {
            this.totalBars.datasets.push(
              {
                data: this.ttlReturnCust,
                label: 'Returning',
                backgroundColor: '#F7970E'
              }, {
                data: this.ttlLapsedCust,
                label: 'Lapsed',
                backgroundColor: '#D63A09'
              }, {
                data: this.ttlNewCust,
                label: 'New',
                backgroundColor: '#8EAC1D'
              }, {
                data: this.ttlReactCust,
                label: 'Reactivated',
                backgroundColor: '#0087AA'
              }, {
                data: this.avgRetCustSpend,
                label: 'Spend per Returning Customer',
                type: 'line',
                yAxisID: 'right-y-axis',
                backgroundColor: '#F7970E',
                fill: false
              }, {
                data: this.avgLapCustSpend,
                label: 'Spend per Lapsed Customer',
                type: 'line',
                yAxisID: 'right-y-axis',
                backgroundColor: '#D63A09',
                fill: false
              }, {
                data: this.avgNewCustSpend,
                label: 'Spend per New Customer',
                type: 'line',
                yAxisID: 'right-y-axis',
                backgroundColor: '#8EAC1D',
                fill: false
              }, {
                data: this.avgReactCustSpend,
                label: 'Spend per Reactivated Customer',
                type: 'line',
                yAxisID: 'right-y-axis',
                backgroundColor: '#0087AA',
                fill: false
              }
            )
          } else {
            if (this.segSelect.includes('Lapsed')) {
              this.totalBars.datasets.push(
                {
                  data: this.ttlLapsedCust,
                  label: 'Lapsed',
                  backgroundColor: '#D63A09'
                }, {
                  data: this.avgLapCustSpend,
                  label: 'Spend per Lapsed Customer',
                  type: 'line',
                  yAxisID: 'right-y-axis',
                  backgroundColor: '#D63A09',
                  fill: false
                }
              )
            }
            if (this.segSelect.includes('New')) {
              this.totalBars.datasets.push(
                {
                  data: this.ttlNewCust,
                  label: 'New',
                  backgroundColor: '#8EAC1D'
                }, {
                  data: this.avgNewCustSpend,
                  label: 'Spend per New Customer',
                  type: 'line',
                  yAxisID: 'right-y-axis',
                  backgroundColor: '#8EAC1D',
                  fill: false
                }
              )
            }
            if (this.segSelect.includes('Returning')) {
              this.totalBars.datasets.push(
                {
                  data: this.ttlReturnCust,
                  label: 'Returning',
                  backgroundColor: '#F7970E'
                }, {
                  data: this.avgRetCustSpend,
                  label: 'Spend per Returning Customer',
                  type: 'line',
                  yAxisID: 'right-y-axis',
                  backgroundColor: '#F7970E',
                  fill: false
                }
              )
            }
            if (this.segSelect.includes('Reactivated')) {
              this.totalBars.datasets.push(
                {
                  data: this.ttlReactCust,
                  label: 'Reactivated',
                  backgroundColor: '#0087AA'
                }, {
                  data: this.avgReactCustSpend,
                  label: 'Spend per Reactivated Customer',
                  type: 'line',
                  yAxisID: 'right-y-axis',
                  backgroundColor: '#0087AA',
                  fill: false
                }
              )
            }
          }
        } else if (this.dimensionSelect === 'Visits') {
          this.avgSelect = 'Avg Spend per Basket'
          this.totalBars = {
            labels: this.dateArray,
            datasets: []
          }

          if (this.segSelect.includes('All')) {
            this.totalBars.datasets.push(
              {
                data: this.ttlReturnTxn,
                label: 'Returning',
                backgroundColor: '#F7970E'
              }, {
                data: this.ttlLapsedTxn,
                label: 'Lapsed',
                backgroundColor: '#D63A09'
              }, {
                data: this.ttlNewTxn,
                label: 'New',
                backgroundColor: '#8EAC1D'
              }, {
                data: this.ttlReactTxn,
                label: 'Reactivated',
                backgroundColor: '#0087AA'
              }, {
                data: this.avgRetVisitSpend,
                label: 'Spend per Visit - Returning',
                type: 'line',
                yAxisID: 'right-y-axis',
                backgroundColor: '#F7970E',
                fill: false
              }, {
                data: this.avgLapVisitSpend,
                label: 'Spend per Visit - Lapsed',
                type: 'line',
                yAxisID: 'right-y-axis',
                backgroundColor: '#D63A09',
                fill: false
              }, {
                data: this.avgNewVisitSpend,
                label: 'Spend per Visit - New',
                type: 'line',
                yAxisID: 'right-y-axis',
                backgroundColor: '#8EAC1D',
                fill: false
              }, {
                data: this.avgReactVisitSpend,
                label: 'Spend per Visit - Reactivated',
                type: 'line',
                yAxisID: 'right-y-axis',
                backgroundColor: '#0087AA',
                fill: false
              }
            )
          } else {
            if (this.segSelect.includes('Lapsed')) {
              this.totalBars.datasets.push(
                {
                  data: this.ttlLapsedTxn,
                  label: 'Lapsed',
                  backgroundColor: '#D63A09'
                }, {
                  data: this.avgLapVisitSpend,
                  label: 'Spend per Visit - Lapsed',
                  type: 'line',
                  yAxisID: 'right-y-axis',
                  backgroundColor: '#D63A09',
                  fill: false
                }
              )
            }
            if (this.segSelect.includes('New')) {
              this.totalBars.datasets.push(
                {
                  data: this.ttlNewTxn,
                  label: 'New',
                  backgroundColor: '#8EAC1D'
                }, {
                  data: this.avgNewVisitSpend,
                  label: 'Spend per Visit - New',
                  type: 'line',
                  yAxisID: 'right-y-axis',
                  backgroundColor: '#8EAC1D',
                  fill: false
                }
              )
            }
            if (this.segSelect.includes('Returning')) {
              this.totalBars.datasets.push(
                {
                  data: this.ttlReturnTxn,
                  label: 'Returning',
                  backgroundColor: '#F7970E'
                }, {
                  data: this.avgRetVisitSpend,
                  label: 'Spend per Visit - Returning',
                  type: 'line',
                  yAxisID: 'right-y-axis',
                  backgroundColor: '#F7970E',
                  fill: false
                }
              )
            }
            if (this.segSelect.includes('Reactivated')) {
              this.totalBars.datasets.push(
                {
                  data: this.ttlReactTxn,
                  label: 'Reactivated',
                  backgroundColor: '#0087AA'
                }, {
                  data: this.avgReactVisitSpend,
                  label: 'Spend per Visit - Reactivated',
                  type: 'line',
                  yAxisID: 'right-y-axis',
                  backgroundColor: '#0087AA',
                  fill: false
                }
              )
            }
          }
        } else if (this.dimensionSelect === 'Spend') {
          this.avgSelect = 'Avg Spend per Customer'
          this.totalBars = {labels: this.dateArray, datasets: []}
          if (this.segSelect.includes('All')) {
            this.totalBars.datasets.push(
              {
                data: this.ttlReturnSpend,
                label: 'Returning',
                backgroundColor: '#F7970E'
              }, {
                data: this.ttlLapsedSpend,
                label: 'Lapsed',
                backgroundColor: '#D63A09'
              }, {
                data: this.ttlNewSpend,
                label: 'New',
                backgroundColor: '#8EAC1D'
              }, {
                data: this.ttlReactSpend,
                label: 'Reactivated',
                backgroundColor: '#0087AA'
              }, {
                data: this.avgRetCustSpend,
                label: 'Spend per Returning Customer',
                type: 'line',
                yAxisID: 'right-y-axis',
                backgroundColor: '#F7970E',
                fill: false
              }, {
                data: this.avgLapCustSpend,
                label: 'Spend per Lapsed Customer',
                type: 'line',
                yAxisID: 'right-y-axis',
                backgroundColor: '#D63A09',
                fill: false
              }, {
                data: this.avgNewCustSpend,
                label: 'Spend per New Customer',
                type: 'line',
                yAxisID: 'right-y-axis',
                backgroundColor: '#8EAC1D',
                fill: false
              }, {
                data: this.avgReactCustSpend,
                label: 'Spend per Reactivated Customer',
                type: 'line',
                yAxisID: 'right-y-axis',
                backgroundColor: '#0087AA',
                fill: false
              }
            )
          } else {
            if (this.segSelect.includes('Lapsed')) {
              this.totalBars.datasets.push(
                {
                  data: this.ttlLapsedSpend,
                  label: 'Lapsed',
                  backgroundColor: '#D63A09'
                }, {
                  data: this.avgLapCustSpend,
                  label: 'Spend per Lapsed Customer',
                  type: 'line',
                  yAxisID: 'right-y-axis',
                  backgroundColor: '#D63A09',
                  fill: false
                }
              )
            }
            if (this.segSelect.includes('New')) {
              this.totalBars.datasets.push(
                {
                  data: this.ttlNewSpend,
                  label: 'New',
                  backgroundColor: '#8EAC1D'
                }, {
                  data: this.avgNewCustSpend,
                  label: 'Spend per New Customer',
                  type: 'line',
                  yAxisID: 'right-y-axis',
                  backgroundColor: '#8EAC1D',
                  fill: false
                }
              )
            }
            if (this.segSelect.includes('Returning')) {
              this.totalBars.datasets.push(
                {
                  data: this.ttlReturnSpend,
                  label: 'Returning',
                  backgroundColor: '#F7970E'
                }, {
                  data: this.avgRetCustSpend,
                  label: 'Spend per Returning Customer',
                  type: 'line',
                  yAxisID: 'right-y-axis',
                  backgroundColor: '#F7970E',
                  fill: false
                }
              )
            }
            if (this.segSelect.includes('Reactivated')) {
              this.totalBars.datasets.push(
                {
                  data: this.ttlReactSpend,
                  label: 'Reactivated',
                  backgroundColor: '#0087AA'
                }, {
                  data: this.avgReactCustSpend,
                  label: 'Spend per Reactivated Customer',
                  type: 'line',
                  yAxisID: 'right-y-axis',
                  backgroundColor: '#0087AA',
                  fill: false
                }
              )
            }
          }
        } else if (this.dimensionSelect === 'Discount') {
          this.avgSelect = 'Avg Discount per Customer'
          this.totalBars = {labels: this.dateArray, datasets: []}
          if (this.segSelect.includes('All')) {
            this.totalBars.datasets.push(
              {
                data: this.ttlReturnDisc,
                label: 'Returning',
                backgroundColor: '#F7970E'
              }, {
                data: this.ttlLapsedDisc,
                label: 'Lapsed',
                backgroundColor: '#D63A09'
              }, {
                data: this.ttlNewDisc,
                label: 'New',
                backgroundColor: '#8EAC1D'
              }, {
                data: this.ttlReactDisc,
                label: 'Reactivated',
                backgroundColor: '#0087AA'
              }, {
                data: this.avgRetCustDisc,
                label: 'Discount per Customer - Returning',
                type: 'line',
                yAxisID: 'right-y-axis',
                backgroundColor: '#F7970E',
                fill: false
              }, {
                data: this.avgLapCustDisc,
                label: 'Discount per Customer - Lapsed',
                type: 'line',
                yAxisID: 'right-y-axis',
                backgroundColor: '#D63A09',
                fill: false
              }, {
                data: this.avgNewCustDisc,
                label: 'Discount per Customer - New',
                type: 'line',
                yAxisID: 'right-y-axis',
                backgroundColor: '#8EAC1D',
                fill: false
              }, {
                data: this.avgReactCustDisc,
                label: 'Discount per Customer - Reactivated',
                type: 'line',
                yAxisID: 'right-y-axis',
                backgroundColor: '#0087AA',
                fill: false
              }
            )
          } else {
            if (this.segSelect.includes('Lapsed')) {
              this.totalBars.datasets.push(
                {
                  data: this.ttlLapsedDisc,
                  label: 'Lapsed',
                  backgroundColor: '#D63A09'
                }, {
                  data: this.avgLapCustDisc,
                  label: 'Discount per Customer - Lapsed',
                  type: 'line',
                  yAxisID: 'right-y-axis',
                  backgroundColor: '#D63A09',
                  fill: false
                }
              )
            }
            if (this.segSelect.includes('New')) {
              this.totalBars.datasets.push(
                {
                  data: this.ttlNewDisc,
                  label: 'New',
                  backgroundColor: '#8EAC1D'
                }, {
                  data: this.avgNewCustDisc,
                  label: 'Discount per Customer - New',
                  type: 'line',
                  yAxisID: 'right-y-axis',
                  backgroundColor: '#8EAC1D',
                  fill: false
                }
              )
            }
            if (this.segSelect.includes('Returning')) {
              this.totalBars.datasets.push(
                {
                  data: this.ttlReturnDisc,
                  label: 'Returning',
                  backgroundColor: '#F7970E'
                }, {
                  data: this.avgRetCustDisc,
                  label: 'Discount per Customer - Returning',
                  type: 'line',
                  yAxisID: 'right-y-axis',
                  backgroundColor: '#F7970E',
                  fill: false
                }
              )
            }
            if (this.segSelect.includes('Reactivated')) {
              this.totalBars.datasets.push(
                {
                  data: this.ttlReactDisc,
                  label: 'Reactivated',
                  backgroundColor: '#0087AA'
                }, {
                  data: this.avgReactCustDisc,
                  label: 'Discount per Customer - Reactivated',
                  type: 'line',
                  yAxisID: 'right-y-axis',
                  backgroundColor: '#0087AA',
                  fill: false
                }
              )
            }
          }
        }
      },

      selectAvg () {
        if (this.dimensionSelect === 'Customer' && this.avgSelect === 'Avg Spend per Customer') {
          this.totalBars = {
            labels: this.dateArray,
            datasets: []
          }

          if (this.segSelect.includes('All')) {
            this.totalBars.datasets.push(
              {
                data: this.ttlReturnCust,
                label: 'Returning',
                backgroundColor: '#F7970E'
              }, {
                data: this.ttlLapsedCust,
                label: 'Lapsed',
                backgroundColor: '#D63A09'
              }, {
                data: this.ttlNewCust,
                label: 'New',
                backgroundColor: '#8EAC1D'
              }, {
                data: this.ttlReactCust,
                label: 'Reactivated',
                backgroundColor: '#0087AA'
              }, {
                data: this.avgRetCustSpend,
                label: 'Spend per Returning Customer',
                type: 'line',
                yAxisID: 'right-y-axis',
                backgroundColor: '#F7970E',
                fill: false
              }, {
                data: this.avgLapCustSpend,
                label: 'Spend per Lapsed Customer',
                type: 'line',
                yAxisID: 'right-y-axis',
                backgroundColor: '#D63A09',
                fill: false
              }, {
                data: this.avgNewCustSpend,
                label: 'Spend per New Customer',
                type: 'line',
                yAxisID: 'right-y-axis',
                backgroundColor: '#8EAC1D',
                fill: false
              }, {
                data: this.avgReactCustSpend,
                label: 'Spend per Reactivated Customer',
                type: 'line',
                yAxisID: 'right-y-axis',
                backgroundColor: '#0087AA',
                fill: false
              }
            )
          } else {
            if (this.segSelect.includes('Lapsed')) {
              this.totalBars.datasets.push(
                {
                  data: this.ttlLapsedCust,
                  label: 'Lapsed',
                  backgroundColor: '#D63A09'
                }, {
                  data: this.avgLapCustSpend,
                  label: 'Spend per Lapsed Customer',
                  type: 'line',
                  yAxisID: 'right-y-axis',
                  backgroundColor: '#D63A09',
                  fill: false
                }
              )
            }
            if (this.segSelect.includes('New')) {
              this.totalBars.datasets.push(
                {
                  data: this.ttlNewCust,
                  label: 'New',
                  backgroundColor: '#8EAC1D'
                }, {
                  data: this.avgNewCustSpend,
                  label: 'Spend per New Customer',
                  type: 'line',
                  yAxisID: 'right-y-axis',
                  backgroundColor: '#8EAC1D',
                  fill: false
                }
              )
            }
            if (this.segSelect.includes('Returning')) {
              this.totalBars.datasets.push(
                {
                  data: this.ttlReturnCust,
                  label: 'Returning',
                  backgroundColor: '#F7970E'
                }, {
                  data: this.avgRetCustSpend,
                  label: 'Spend per Returning Customer',
                  type: 'line',
                  yAxisID: 'right-y-axis',
                  backgroundColor: '#F7970E',
                  fill: false
                }
              )
            }
            if (this.segSelect.includes('Reactivated')) {
              this.totalBars.datasets.push(
                {
                  data: this.ttlReactCust,
                  label: 'Reactivated',
                  backgroundColor: '#0087AA'
                }, {
                  data: this.avgReactCustSpend,
                  label: 'Spend per Reactivated Customer',
                  type: 'line',
                  yAxisID: 'right-y-axis',
                  backgroundColor: '#0087AA',
                  fill: false
                }
              )
            }
          }
        } else if (this.dimensionSelect === 'Customer' && this.avgSelect === 'Avg Items per Customer') {
          this.totalBars = {
            labels: this.dateArray,
            datasets: []
          }

          if (this.segSelect.includes('All')) {
            this.totalBars.datasets.push(
              {
                data: this.ttlReturnCust,
                label: 'Returning',
                backgroundColor: '#F7970E'
              }, {
                data: this.ttlLapsedCust,
                label: 'Lapsed',
                backgroundColor: '#D63A09'
              }, {
                data: this.ttlNewCust,
                label: 'New',
                backgroundColor: '#8EAC1D'
              }, {
                data: this.ttlReactCust,
                label: 'Reactivated',
                backgroundColor: '#0087AA'
              }, {
                data: this.avgRetCustItems,
                label: 'Items per Returning Customer',
                type: 'line',
                yAxisID: 'right-y-axis',
                backgroundColor: '#F7970E',
                fill: false
              }, {
                data: this.avgLapCustItems,
                label: 'Items per Lapsed Customer',
                type: 'line',
                yAxisID: 'right-y-axis',
                backgroundColor: '#D63A09',
                fill: false
              }, {
                data: this.avgNewCustItems,
                label: 'Items per New Customer',
                type: 'line',
                yAxisID: 'right-y-axis',
                backgroundColor: '#8EAC1D',
                fill: false
              }, {
                data: this.avgReactCustItems,
                label: 'Items per Reactivated Customer',
                type: 'line',
                yAxisID: 'right-y-axis',
                backgroundColor: '#0087AA',
                fill: false
              }
            )
          } else {
            if (this.segSelect.includes('Lapsed')) {
              this.totalBars.datasets.push(
                {
                  data: this.ttlLapsedCust,
                  label: 'Lapsed',
                  backgroundColor: '#D63A09'
                }, {
                  data: this.avgLapCustItems,
                  label: 'Items per Lapsed Customer',
                  type: 'line',
                  yAxisID: 'right-y-axis',
                  backgroundColor: '#D63A09',
                  fill: false
                }
              )
            }
            if (this.segSelect.includes('New')) {
              this.totalBars.datasets.push(
                {
                  data: this.ttlNewCust,
                  label: 'New',
                  backgroundColor: '#8EAC1D'
                }, {
                  data: this.avgNewCustItems,
                  label: 'Items per New Customer',
                  type: 'line',
                  yAxisID: 'right-y-axis',
                  backgroundColor: '#8EAC1D',
                  fill: false
                }
              )
            }
            if (this.segSelect.includes('Returning')) {
              this.totalBars.datasets.push(
                {
                  data: this.ttlReturnCust,
                  label: 'Returning',
                  backgroundColor: '#F7970E'
                }, {
                  data: this.avgRetCustItems,
                  label: 'Items per Returning Customer',
                  type: 'line',
                  yAxisID: 'right-y-axis',
                  backgroundColor: '#F7970E',
                  fill: false
                }
              )
            }
            if (this.segSelect.includes('Reactivated')) {
              this.totalBars.datasets.push(
                {
                  data: this.ttlReactCust,
                  label: 'Reactivated',
                  backgroundColor: '#0087AA'
                }, {
                  data: this.avgReactCustItems,
                  label: 'Items per Reactivated Customer',
                  type: 'line',
                  yAxisID: 'right-y-axis',
                  backgroundColor: '#0087AA',
                  fill: false
                }
              )
            }
          }
        } else if (this.dimensionSelect === 'Customer' && this.avgSelect === 'Avg Discount per Customer') {
          this.totalBars = {
            labels: this.dateArray,
            datasets: []
          }

          if (this.segSelect.includes('All')) {
            this.totalBars.datasets.push(
              {
                data: this.ttlReturnCust,
                label: 'Returning',
                backgroundColor: '#F7970E'
              }, {
                data: this.ttlLapsedCust,
                label: 'Lapsed',
                backgroundColor: '#D63A09'
              }, {
                data: this.ttlNewCust,
                label: 'New',
                backgroundColor: '#8EAC1D'
              }, {
                data: this.ttlReactCust,
                label: 'Reactivated',
                backgroundColor: '#0087AA'
              }, {
                data: this.avgRetCustDisc,
                label: 'Discount per Returning Customer',
                type: 'line',
                yAxisID: 'right-y-axis',
                backgroundColor: '#F7970E',
                fill: false
              }, {
                data: this.avgLapCustDisc,
                label: 'Discount per Lapsed Customer',
                type: 'line',
                yAxisID: 'right-y-axis',
                backgroundColor: '#D63A09',
                fill: false
              }, {
                data: this.avgNewCustDisc,
                label: 'Discount per New Customer',
                type: 'line',
                yAxisID: 'right-y-axis',
                backgroundColor: '#8EAC1D',
                fill: false
              }, {
                data: this.avgReactCustDisc,
                label: 'Discount per Reactivated Customer',
                type: 'line',
                yAxisID: 'right-y-axis',
                backgroundColor: '#0087AA',
                fill: false
              }
            )
          } else {
            if (this.segSelect.includes('Lapsed')) {
              this.totalBars.datasets.push(
                {
                  data: this.ttlLapsedCust,
                  label: 'Lapsed',
                  backgroundColor: '#D63A09'
                }, {
                  data: this.avgLapCustDisc,
                  label: 'Discount per Lapsed Customer',
                  type: 'line',
                  yAxisID: 'right-y-axis',
                  backgroundColor: '#D63A09',
                  fill: false
                }
              )
            }
            if (this.segSelect.includes('New')) {
              this.totalBars.datasets.push(
                {
                  data: this.ttlNewCust,
                  label: 'New',
                  backgroundColor: '#8EAC1D'
                }, {
                  data: this.avgNewCustDisc,
                  label: 'Discount per New Customer',
                  type: 'line',
                  yAxisID: 'right-y-axis',
                  backgroundColor: '#8EAC1D',
                  fill: false
                }
              )
            }
            if (this.segSelect.includes('Returning')) {
              this.totalBars.datasets.push(
                {
                  data: this.ttlReturnCust,
                  label: 'Returning',
                  backgroundColor: '#F7970E'
                }, {
                  data: this.avgRetCustDisc,
                  label: 'Discount per Returning Customer',
                  type: 'line',
                  yAxisID: 'right-y-axis',
                  backgroundColor: '#F7970E',
                  fill: false
                }
              )
            }
            if (this.segSelect.includes('Reactivated')) {
              this.totalBars.datasets.push(
                {
                  data: this.ttlReactCust,
                  label: 'Reactivated',
                  backgroundColor: '#0087AA'
                }, {
                  data: this.avgReactCustDisc,
                  label: 'Discount per Reactivated Customer',
                  type: 'line',
                  yAxisID: 'right-y-axis',
                  backgroundColor: '#0087AA',
                  fill: false
                }
              )
            }
          }
        } else if (this.dimensionSelect === 'Customer' && this.avgSelect === 'Avg Transactions per Customer') {
          this.totalBars = {
            labels: this.dateArray,
            datasets: []
          }

          if (this.segSelect.includes('All')) {
            this.totalBars.datasets.push(
              {
                data: this.ttlReturnCust,
                label: 'Returning',
                backgroundColor: '#F7970E'
              }, {
                data: this.ttlLapsedCust,
                label: 'Lapsed',
                backgroundColor: '#D63A09'
              }, {
                data: this.ttlNewCust,
                label: 'New',
                backgroundColor: '#8EAC1D'
              }, {
                data: this.ttlReactCust,
                label: 'Reactivated',
                backgroundColor: '#0087AA'
              }, {
                data: this.avgRetCustTxn,
                label: 'Visits per Returning Customer',
                type: 'line',
                yAxisID: 'right-y-axis',
                backgroundColor: '#F7970E',
                fill: false
              }, {
                data: this.avgLapCustTxn,
                label: 'Visits per Lapsed Customer',
                type: 'line',
                yAxisID: 'right-y-axis',
                backgroundColor: '#D63A09',
                fill: false
              }, {
                data: this.avgNewCustTxn,
                label: 'Visits per New Customer',
                type: 'line',
                yAxisID: 'right-y-axis',
                backgroundColor: '#8EAC1D',
                fill: false
              }, {
                data: this.avgReactCustTxn,
                label: 'Visits per Reactivated Customer',
                type: 'line',
                yAxisID: 'right-y-axis',
                backgroundColor: '#0087AA',
                fill: false
              }
            )
          } else {
            if (this.segSelect.includes('Lapsed')) {
              this.totalBars.datasets.push(
                {
                  data: this.ttlLapsedCust,
                  label: 'Lapsed',
                  backgroundColor: '#D63A09'
                }, {
                  data: this.avgLapCustTxn,
                  label: 'Visits per Lapsed Customer',
                  type: 'line',
                  yAxisID: 'right-y-axis',
                  backgroundColor: '#D63A09',
                  fill: false
                }
              )
            }
            if (this.segSelect.includes('New')) {
              this.totalBars.datasets.push(
                {
                  data: this.ttlNewCust,
                  label: 'New',
                  backgroundColor: '#8EAC1D'
                }, {
                  data: this.avgNewCustTxn,
                  label: 'Visits per New Customer',
                  type: 'line',
                  yAxisID: 'right-y-axis',
                  backgroundColor: '#8EAC1D',
                  fill: false
                }
              )
            }
            if (this.segSelect.includes('Returning')) {
              this.totalBars.datasets.push(
                {
                  data: this.ttlReturnCust,
                  label: 'Returning',
                  backgroundColor: '#F7970E'
                }, {
                  data: this.avgRetCustTxn,
                  label: 'Visits per Returning Customer',
                  type: 'line',
                  yAxisID: 'right-y-axis',
                  backgroundColor: '#F7970E',
                  fill: false
                }
              )
            }
            if (this.segSelect.includes('Reactivated')) {
              this.totalBars.datasets.push(
                {
                  data: this.ttlReactCust,
                  label: 'Reactivated',
                  backgroundColor: '#0087AA'
                }, {
                  data: this.avgReactCustTxn,
                  label: 'Visits per Reactivated Customer',
                  type: 'line',
                  yAxisID: 'right-y-axis',
                  backgroundColor: '#0087AA',
                  fill: false
                }
              )
            }
          }
        } else if (this.dimensionSelect === 'Visits' && this.avgSelect === 'Avg Spend per Basket') {
          this.totalBars = {
            labels: this.dateArray,
            datasets: []
          }

          if (this.segSelect.includes('All')) {
            this.totalBars.datasets.push(
              {
                data: this.ttlReturnTxn,
                label: 'Returning',
                backgroundColor: '#F7970E'
              }, {
                data: this.ttlLapsedTxn,
                label: 'Lapsed',
                backgroundColor: '#D63A09'
              }, {
                data: this.ttlNewTxn,
                label: 'New',
                backgroundColor: '#8EAC1D'
              }, {
                data: this.ttlReactTxn,
                label: 'Reactivated',
                backgroundColor: '#0087AA'
              }, {
                data: this.avgRetVisitSpend,
                label: 'Spend per Visit - Returning',
                type: 'line',
                yAxisID: 'right-y-axis',
                backgroundColor: '#F7970E',
                fill: false
              }, {
                data: this.avgLapVisitSpend,
                label: 'Spend per Visit - Lapsed',
                type: 'line',
                yAxisID: 'right-y-axis',
                backgroundColor: '#D63A09',
                fill: false
              }, {
                data: this.avgNewVisitSpend,
                label: 'Spend per Visit - New',
                type: 'line',
                yAxisID: 'right-y-axis',
                backgroundColor: '#8EAC1D',
                fill: false
              }, {
                data: this.avgReactVisitSpend,
                label: 'Spend per Visit - Reactivated',
                type: 'line',
                yAxisID: 'right-y-axis',
                backgroundColor: '#0087AA',
                fill: false
              }
            )
          } else {
            if (this.segSelect.includes('Lapsed')) {
              this.totalBars.datasets.push(
                {
                  data: this.ttlLapsedTxn,
                  label: 'Lapsed',
                  backgroundColor: '#D63A09'
                }, {
                  data: this.avgLapVisitSpend,
                  label: 'Spend per Visit - Lapsed',
                  type: 'line',
                  yAxisID: 'right-y-axis',
                  backgroundColor: '#D63A09',
                  fill: false
                }
              )
            }
            if (this.segSelect.includes('New')) {
              this.totalBars.datasets.push(
                {
                  data: this.ttlNewTxn,
                  label: 'New',
                  backgroundColor: '#8EAC1D'
                }, {
                  data: this.avgNewVisitSpend,
                  label: 'Spend per Visit - New',
                  type: 'line',
                  yAxisID: 'right-y-axis',
                  backgroundColor: '#8EAC1D',
                  fill: false
                }
              )
            }
            if (this.segSelect.includes('Returning')) {
              this.totalBars.datasets.push(
                {
                  data: this.ttlReturnTxn,
                  label: 'Returning',
                  backgroundColor: '#F7970E'
                }, {
                  data: this.avgRetVisitSpend,
                  label: 'Spend per Visit - Returning',
                  type: 'line',
                  yAxisID: 'right-y-axis',
                  backgroundColor: '#F7970E',
                  fill: false
                }
              )
            }
            if (this.segSelect.includes('Reactivated')) {
              this.totalBars.datasets.push(
                {
                  data: this.ttlReactTxn,
                  label: 'Reactivated',
                  backgroundColor: '#0087AA'
                }, {
                  data: this.avgReactVisitSpend,
                  label: 'Spend per Visit - Reactivated',
                  type: 'line',
                  yAxisID: 'right-y-axis',
                  backgroundColor: '#0087AA',
                  fill: false
                }
              )
            }
          }
        } else if (this.dimensionSelect === 'Visits' && this.avgSelect === 'Avg Items per Basket') {
          this.totalBars = {
            labels: this.dateArray,
            datasets: []
          }

          if (this.segSelect.includes('All')) {
            this.totalBars.datasets.push(
              {
                data: this.ttlReturnTxn,
                label: 'Returning',
                backgroundColor: '#F7970E'
              }, {
                data: this.ttlLapsedTxn,
                label: 'Lapsed',
                backgroundColor: '#D63A09'
              }, {
                data: this.ttlNewTxn,
                label: 'New',
                backgroundColor: '#8EAC1D'
              }, {
                data: this.ttlReactTxn,
                label: 'Reactivated',
                backgroundColor: '#0087AA'
              }, {
                data: this.avgRetVisitItems,
                label: 'Items per Visit - Returning',
                type: 'line',
                yAxisID: 'right-y-axis',
                backgroundColor: '#F7970E',
                fill: false
              }, {
                data: this.avgLapVisitItems,
                label: 'Items per Visit - Lapsed',
                type: 'line',
                yAxisID: 'right-y-axis',
                backgroundColor: '#D63A09',
                fill: false
              }, {
                data: this.avgNewVisitItems,
                label: 'Items per Visit - New',
                type: 'line',
                yAxisID: 'right-y-axis',
                backgroundColor: '#8EAC1D',
                fill: false
              }, {
                data: this.avgReactVisitItems,
                label: 'Items per Visit - Reactivated',
                type: 'line',
                yAxisID: 'right-y-axis',
                backgroundColor: '#0087AA',
                fill: false
              }
            )
          } else {
            if (this.segSelect.includes('Lapsed')) {
              this.totalBars.datasets.push(
                {
                  data: this.ttlLapsedTxn,
                  label: 'Lapsed',
                  backgroundColor: '#D63A09'
                }, {
                  data: this.avgLapVisitItems,
                  label: 'Items per Visit - Lapsed',
                  type: 'line',
                  yAxisID: 'right-y-axis',
                  backgroundColor: '#D63A09',
                  fill: false
                }
              )
            }
            if (this.segSelect.includes('New')) {
              this.totalBars.datasets.push(
                {
                  data: this.ttlNewTxn,
                  label: 'New',
                  backgroundColor: '#8EAC1D'
                }, {
                  data: this.avgNewVisitItems,
                  label: 'Items per Visit - New',
                  type: 'line',
                  yAxisID: 'right-y-axis',
                  backgroundColor: '#8EAC1D',
                  fill: false
                }
              )
            }
            if (this.segSelect.includes('Returning')) {
              this.totalBars.datasets.push(
                {
                  data: this.ttlReturnTxn,
                  label: 'Returning',
                  backgroundColor: '#F7970E'
                }, {
                  data: this.avgRetVisitItems,
                  label: 'Items per Visit - Returning',
                  type: 'line',
                  yAxisID: 'right-y-axis',
                  backgroundColor: '#F7970E',
                  fill: false
                }
              )
            }
            if (this.segSelect.includes('Reactivated')) {
              this.totalBars.datasets.push(
                {
                  data: this.ttlReactTxn,
                  label: 'Reactivated',
                  backgroundColor: '#0087AA'
                }, {
                  data: this.avgReactVisitItems,
                  label: 'Items per Visit - Reactivated',
                  type: 'line',
                  yAxisID: 'right-y-axis',
                  backgroundColor: '#0087AA',
                  fill: false
                }
              )
            }
          }
        } else if (this.dimensionSelect === 'Visits' && this.avgSelect === 'Avg Discount per Basket') {
          this.totalBars = {
            labels: this.dateArray,
            datasets: []
          }

          if (this.segSelect.includes('All')) {
            this.totalBars.datasets.push(
              {
                data: this.ttlReturnTxn,
                label: 'Returning',
                backgroundColor: '#F7970E'
              }, {
                data: this.ttlLapsedTxn,
                label: 'Lapsed',
                backgroundColor: '#D63A09'
              }, {
                data: this.ttlNewTxn,
                label: 'New',
                backgroundColor: '#8EAC1D'
              }, {
                data: this.ttlReactTxn,
                label: 'Reactivated',
                backgroundColor: '#0087AA'
              }, {
                data: this.avgRetVisitDisc,
                label: 'Discount per Visit - Returning',
                type: 'line',
                yAxisID: 'right-y-axis',
                backgroundColor: '#F7970E',
                fill: false
              }, {
                data: this.avgLapVisitDisc,
                label: 'Discount per Visit - Lapsed',
                type: 'line',
                yAxisID: 'right-y-axis',
                backgroundColor: '#D63A09',
                fill: false
              }, {
                data: this.avgNewVisitDisc,
                label: 'Discount per Visit - New',
                type: 'line',
                yAxisID: 'right-y-axis',
                backgroundColor: '#8EAC1D',
                fill: false
              }, {
                data: this.avgReactVisitDisc,
                label: 'Discount per Visit - Reactivated',
                type: 'line',
                yAxisID: 'right-y-axis',
                backgroundColor: '#0087AA',
                fill: false
              }
            )
          } else {
            if (this.segSelect.includes('Lapsed')) {
              this.totalBars.datasets.push(
                {
                  data: this.ttlLapsedTxn,
                  label: 'Lapsed',
                  backgroundColor: '#D63A09'
                }, {
                  data: this.avgLapVisitDisc,
                  label: 'Discount per Visit - Lapsed',
                  type: 'line',
                  yAxisID: 'right-y-axis',
                  backgroundColor: '#D63A09',
                  fill: false
                }
              )
            }
            if (this.segSelect.includes('New')) {
              this.totalBars.datasets.push(
                {
                  data: this.ttlNewTxn,
                  label: 'New',
                  backgroundColor: '#8EAC1D'
                }, {
                  data: this.avgNewVisitDisc,
                  label: 'Discount per Visit - New',
                  type: 'line',
                  yAxisID: 'right-y-axis',
                  backgroundColor: '#8EAC1D',
                  fill: false
                }
              )
            }
            if (this.segSelect.includes('Returning')) {
              this.totalBars.datasets.push(
                {
                  data: this.ttlReturnTxn,
                  label: 'Returning',
                  backgroundColor: '#F7970E'
                }, {
                  data: this.avgRetVisitDisc,
                  label: 'Discount per Visit - Returning',
                  type: 'line',
                  yAxisID: 'right-y-axis',
                  backgroundColor: '#F7970E',
                  fill: false
                }
              )
            }
            if (this.segSelect.includes('Reactivated')) {
              this.totalBars.datasets.push(
                {
                  data: this.ttlReactTxn,
                  label: 'Reactivated',
                  backgroundColor: '#0087AA'
                }, {
                  data: this.avgReactVisitDisc,
                  label: 'Discount per Visit - Reactivated',
                  type: 'line',
                  yAxisID: 'right-y-axis',
                  backgroundColor: '#0087AA',
                  fill: false
                }
              )
            }
          }
        } else if (this.dimensionSelect === 'Spend' && this.avgSelect === 'Avg Spend per Customer') {
          this.totalBars = {
            labels: this.dateArray,
            datasets: []
          }

          if (this.segSelect.includes('All')) {
            this.totalBars.datasets.push(
              {
                data: this.ttlReturnSpend,
                label: 'Returning',
                backgroundColor: '#F7970E'
              }, {
                data: this.ttlLapsedSpend,
                label: 'Lapsed',
                backgroundColor: '#D63A09'
              }, {
                data: this.ttlNewSpend,
                label: 'New',
                backgroundColor: '#8EAC1D'
              }, {
                data: this.ttlReactSpend,
                label: 'Reactivated',
                backgroundColor: '#0087AA'
              }, {
                data: this.avgRetCustSpend,
                label: 'Spend per Customer - Returning',
                type: 'line',
                yAxisID: 'right-y-axis',
                backgroundColor: '#F7970E',
                fill: false
              }, {
                data: this.avgLapCustSpend,
                label: 'Spend per Customer - Lapsed',
                type: 'line',
                yAxisID: 'right-y-axis',
                backgroundColor: '#D63A09',
                fill: false
              }, {
                data: this.avgNewCustSpend,
                label: 'Spend per Customer - New',
                type: 'line',
                yAxisID: 'right-y-axis',
                backgroundColor: '#8EAC1D',
                fill: false
              }, {
                data: this.avgReactCustSpend,
                label: 'Spend per Customer - Reactivated',
                type: 'line',
                yAxisID: 'right-y-axis',
                backgroundColor: '#0087AA',
                fill: false
              }
            )
          } else {
            if (this.segSelect.includes('Lapsed')) {
              this.totalBars.datasets.push(
                {
                  data: this.ttlLapsedSpend,
                  label: 'Lapsed',
                  backgroundColor: '#D63A09'
                }, {
                  data: this.avgLapCustSpend,
                  label: 'Spend per Customer - Lapsed',
                  type: 'line',
                  yAxisID: 'right-y-axis',
                  backgroundColor: '#D63A09',
                  fill: false
                }
              )
            }
            if (this.segSelect.includes('New')) {
              this.totalBars.datasets.push(
                {
                  data: this.ttlNewSpend,
                  label: 'New',
                  backgroundColor: '#8EAC1D'
                }, {
                  data: this.avgNewCustSpend,
                  label: 'Spend per Customer - New',
                  type: 'line',
                  yAxisID: 'right-y-axis',
                  backgroundColor: '#8EAC1D',
                  fill: false
                }
              )
            }
            if (this.segSelect.includes('Returning')) {
              this.totalBars.datasets.push(
                {
                  data: this.ttlReturnSpend,
                  label: 'Returning',
                  backgroundColor: '#F7970E'
                }, {
                  data: this.avgRetCustSpend,
                  label: 'Spend per Customer - Returning',
                  type: 'line',
                  yAxisID: 'right-y-axis',
                  backgroundColor: '#F7970E',
                  fill: false
                }
              )
            }
            if (this.segSelect.includes('Reactivated')) {
              this.totalBars.datasets.push(
                {
                  data: this.ttlReactSpend,
                  label: 'Reactivated',
                  backgroundColor: '#0087AA'
                }, {
                  data: this.avgReactCustSpend,
                  label: 'Spend per Customer - Reactivated',
                  type: 'line',
                  yAxisID: 'right-y-axis',
                  backgroundColor: '#0087AA',
                  fill: false
                }
              )
            }
          }
        } else if (this.dimensionSelect === 'Spend' && this.avgSelect === 'Avg Spend per Basket') {
          this.totalBars = {
            labels: this.dateArray,
            datasets: []
          }

          if (this.segSelect.includes('All')) {
            this.totalBars.datasets.push(
              {
                data: this.ttlReturnSpend,
                label: 'Returning',
                backgroundColor: '#F7970E'
              }, {
                data: this.ttlLapsedSpend,
                label: 'Lapsed',
                backgroundColor: '#D63A09'
              }, {
                data: this.ttlNewSpend,
                label: 'New',
                backgroundColor: '#8EAC1D'
              }, {
                data: this.ttlReactSpend,
                label: 'Reactivated',
                backgroundColor: '#0087AA'
              }, {
                data: this.avgRetVisitSpend,
                label: 'Spend per Visit - Returning',
                type: 'line',
                yAxisID: 'right-y-axis',
                backgroundColor: '#F7970E',
                fill: false
              }, {
                data: this.avgLapVisitSpend,
                label: 'Spend per Visit - Lapsed',
                type: 'line',
                yAxisID: 'right-y-axis',
                backgroundColor: '#D63A09',
                fill: false
              }, {
                data: this.avgNewVisitSpend,
                label: 'Spend per Visit - New',
                type: 'line',
                yAxisID: 'right-y-axis',
                backgroundColor: '#8EAC1D',
                fill: false
              }, {
                data: this.avgReactVisitSpend,
                label: 'Spend per Visit - Reactivated',
                type: 'line',
                yAxisID: 'right-y-axis',
                backgroundColor: '#0087AA',
                fill: false
              }
            )
          } else {
            if (this.segSelect.includes('Lapsed')) {
              this.totalBars.datasets.push(
                {
                  data: this.ttlLapsedSpend,
                  label: 'Lapsed',
                  backgroundColor: '#D63A09'
                }, {
                  data: this.avgLapVisitSpend,
                  label: 'Spend per Visit - Lapsed',
                  type: 'line',
                  yAxisID: 'right-y-axis',
                  backgroundColor: '#D63A09',
                  fill: false
                }
              )
            }
            if (this.segSelect.includes('New')) {
              this.totalBars.datasets.push(
                {
                  data: this.ttlNewSpend,
                  label: 'New',
                  backgroundColor: '#8EAC1D'
                }, {
                  data: this.avgNewVisitSpend,
                  label: 'Spend per Visit - New',
                  type: 'line',
                  yAxisID: 'right-y-axis',
                  backgroundColor: '#8EAC1D',
                  fill: false
                }
              )
            }
            if (this.segSelect.includes('Returning')) {
              this.totalBars.datasets.push(
                {
                  data: this.ttlReturnSpend,
                  label: 'Returning',
                  backgroundColor: '#F7970E'
                }, {
                  data: this.avgRetVisitSpend,
                  label: 'Spend per Visit - Returning',
                  type: 'line',
                  yAxisID: 'right-y-axis',
                  backgroundColor: '#F7970E',
                  fill: false
                }
              )
            }
            if (this.segSelect.includes('Reactivated')) {
              this.totalBars.datasets.push(
                {
                  data: this.ttlReactSpend,
                  label: 'Reactivated',
                  backgroundColor: '#0087AA'
                }, {
                  data: this.avgReactVisitSpend,
                  label: 'Spend per Visit - Reactivated',
                  type: 'line',
                  yAxisID: 'right-y-axis',
                  backgroundColor: '#0087AA',
                  fill: false
                }
              )
            }
          }
        } else if (this.dimensionSelect === 'Discount' && this.avgSelect === 'Avg Discount per Customer') {
          this.totalBars = {
            labels: this.dateArray,
            datasets: []
          }

          if (this.segSelect.includes('All')) {
            this.totalBars.datasets.push(
              {
                data: this.ttlReturnDisc,
                label: 'Returning',
                backgroundColor: '#F7970E'
              }, {
                data: this.ttlLapsedDisc,
                label: 'Lapsed',
                backgroundColor: '#D63A09'
              }, {
                data: this.ttlNewDisc,
                label: 'New',
                backgroundColor: '#8EAC1D'
              }, {
                data: this.ttlReactDisc,
                label: 'Reactivated',
                backgroundColor: '#0087AA'
              }, {
                data: this.avgRetCustDisc,
                label: 'Discount per Customer - Returning',
                type: 'line',
                yAxisID: 'right-y-axis',
                backgroundColor: '#F7970E',
                fill: false
              }, {
                data: this.avgLapCustDisc,
                label: 'Discount per Customer - Lapsed',
                type: 'line',
                yAxisID: 'right-y-axis',
                backgroundColor: '#D63A09',
                fill: false
              }, {
                data: this.avgNewCustDisc,
                label: 'Discount per Customer - New',
                type: 'line',
                yAxisID: 'right-y-axis',
                backgroundColor: '#8EAC1D',
                fill: false
              }, {
                data: this.avgReactCustDisc,
                label: 'Discount per Customer - Reactivated',
                type: 'line',
                yAxisID: 'right-y-axis',
                backgroundColor: '#0087AA',
                fill: false
              }
            )
          } else {
            if (this.segSelect.includes('Lapsed')) {
              this.totalBars.datasets.push(
                {
                  data: this.ttlLapsedDisc,
                  label: 'Lapsed',
                  backgroundColor: '#D63A09'
                }, {
                  data: this.avgLapCustDisc,
                  label: 'Discount per Customer - Lapsed',
                  type: 'line',
                  yAxisID: 'right-y-axis',
                  backgroundColor: '#D63A09',
                  fill: false
                }
              )
            }
            if (this.segSelect.includes('New')) {
              this.totalBars.datasets.push(
                {
                  data: this.ttlNewDisc,
                  label: 'New',
                  backgroundColor: '#8EAC1D'
                }, {
                  data: this.avgNewCustDisc,
                  label: 'Discount per Customer - New',
                  type: 'line',
                  yAxisID: 'right-y-axis',
                  backgroundColor: '#8EAC1D',
                  fill: false
                }
              )
            }
            if (this.segSelect.includes('Returning')) {
              this.totalBars.datasets.push(
                {
                  data: this.ttlReturnDisc,
                  label: 'Returning',
                  backgroundColor: '#F7970E'
                }, {
                  data: this.avgRetCustDisc,
                  label: 'Discount per Customer - Returning',
                  type: 'line',
                  yAxisID: 'right-y-axis',
                  backgroundColor: '#F7970E',
                  fill: false
                }
              )
            }
            if (this.segSelect.includes('Reactivated')) {
              this.totalBars.datasets.push(
                {
                  data: this.ttlReactDisc,
                  label: 'Reactivated',
                  backgroundColor: '#0087AA'
                }, {
                  data: this.avgReactCustDisc,
                  label: 'Discount per Customer - Reactivated',
                  type: 'line',
                  yAxisID: 'right-y-axis',
                  backgroundColor: '#0087AA',
                  fill: false
                }
              )
            }
          }
        } else if (this.dimensionSelect === 'Discount' && this.avgSelect === 'Avg Discount per Basket') {
          this.totalBars = {
            labels: this.dateArray,
            datasets: []
          }

          if (this.segSelect.includes('All')) {
            this.totalBars.datasets.push(
              {
                data: this.ttlReturnDisc,
                label: 'Returning',
                backgroundColor: '#F7970E'
              }, {
                data: this.ttlLapsedDisc,
                label: 'Lapsed',
                backgroundColor: '#D63A09'
              }, {
                data: this.ttlNewDisc,
                label: 'New',
                backgroundColor: '#8EAC1D'
              }, {
                data: this.ttlReactDisc,
                label: 'Reactivated',
                backgroundColor: '#0087AA'
              }, {
                data: this.avgRetVisitDisc,
                label: 'Discount per Visit - Returning',
                type: 'line',
                yAxisID: 'right-y-axis',
                backgroundColor: '#F7970E',
                fill: false
              }, {
                data: this.avgLapVisitDisc,
                label: 'Discount per Visit - Lapsed',
                type: 'line',
                yAxisID: 'right-y-axis',
                backgroundColor: '#D63A09',
                fill: false
              }, {
                data: this.avgNewVisitDisc,
                label: 'Discount per Visit - New',
                type: 'line',
                yAxisID: 'right-y-axis',
                backgroundColor: '#8EAC1D',
                fill: false
              }, {
                data: this.avgReactVisitDisc,
                label: 'Discount per Visit - Reactivated',
                type: 'line',
                yAxisID: 'right-y-axis',
                backgroundColor: '#0087AA',
                fill: false
              }
            )
          } else {
            if (this.segSelect.includes('Lapsed')) {
              this.totalBars.datasets.push(
                {
                  data: this.ttlLapsedDisc,
                  label: 'Lapsed',
                  backgroundColor: '#D63A09'
                }, {
                  data: this.avgLapVisitDisc,
                  label: 'Discount per Visit - Lapsed',
                  type: 'line',
                  yAxisID: 'right-y-axis',
                  backgroundColor: '#D63A09',
                  fill: false
                }
              )
            }
            if (this.segSelect.includes('New')) {
              this.totalBars.datasets.push(
                {
                  data: this.ttlNewDisc,
                  label: 'New',
                  backgroundColor: '#8EAC1D'
                }, {
                  data: this.avgNewVisitDisc,
                  label: 'Discount per Visit - New',
                  type: 'line',
                  yAxisID: 'right-y-axis',
                  backgroundColor: '#8EAC1D',
                  fill: false
                }
              )
            }
            if (this.segSelect.includes('Returning')) {
              this.totalBars.datasets.push(
                {
                  data: this.ttlReturnDisc,
                  label: 'Returning',
                  backgroundColor: '#F7970E'
                }, {
                  data: this.avgRetVisitDisc,
                  label: 'Discount per Visit - Returning',
                  type: 'line',
                  yAxisID: 'right-y-axis',
                  backgroundColor: '#F7970E',
                  fill: false
                }
              )
            }
            if (this.segSelect.includes('Reactivated')) {
              this.totalBars.datasets.push(
                {
                  data: this.ttlReactDisc,
                  label: 'Reactivated',
                  backgroundColor: '#0087AA'
                }, {
                  data: this.avgReactVisitDisc,
                  label: 'Discount per Visit - Reactivated',
                  type: 'line',
                  yAxisID: 'right-y-axis',
                  backgroundColor: '#0087AA',
                  fill: false
                }
              )
            }
          }
        }
      },

      updateTPMetric () {
        if (this.tpAvgSelect === 'Spend per Customer') {
          this.avgBars = {
            type: 'horizontalBar',
            labels: ['Lapsed', 'New', 'Reactivated', 'Returning'],
            datasets: [
              {
                data: [this.avgLapCustSpend[this.tpSelect - 1], this.avgNewCustSpend[this.tpSelect - 1], this.avgReactCustSpend[this.tpSelect - 1], this.avgRetCustSpend[this.tpSelect - 1]],
                label: 'Avg Spend Per Customer',
                backgroundColor: ['#D63A09', '#8EAC1D', '#0087AA', '#F7970E']
              }
            ]
          }
        } else if (this.tpAvgSelect === 'Items per Customer') {
          this.avgBars = {
            type: 'horizontalBar',
            labels: ['Lapsed', 'New', 'Reactivated', 'Returning'],
            datasets: [
              {
                data: [this.avgLapCustItems[this.tpSelect - 1], this.avgNewCustItems[this.tpSelect - 1], this.avgReactCustItems[this.tpSelect - 1], this.avgRetCustItems[this.tpSelect - 1]],
                label: 'Avg Items Per Customer',
                backgroundColor: ['#D63A09', '#8EAC1D', '#0087AA', '#F7970E']
              }
            ]
          }
        } else if (this.tpAvgSelect === 'Discount per Customer') {
          this.avgBars = {
            type: 'horizontalBar',
            labels: ['Lapsed', 'New', 'Reactivated', 'Returning'],
            datasets: [
              {
                data: [this.avgLapCustDisc[this.tpSelect - 1], this.avgNewCustDisc[this.tpSelect - 1], this.avgReactCustDisc[this.tpSelect - 1], this.avgRetCustDisc[this.tpSelect - 1]],
                label: 'Avg Discount Per Customer',
                backgroundColor: ['#D63A09', '#8EAC1D', '#0087AA', '#F7970E']
              }
            ]
          }
        } else if (this.tpAvgSelect === 'Transactions per Customer') {
          this.avgBars = {
            type: 'horizontalBar',
            labels: ['Lapsed', 'New', 'Reactivated', 'Returning'],
            datasets: [
              {
                data: [this.avgLapCustTxn[this.tpSelect - 1], this.avgNewCustTxn[this.tpSelect - 1], this.avgReactCustTxn[this.tpSelect - 1], this.avgRetCustTxn[this.tpSelect - 1]],
                label: 'Avg Transactions Per Customer',
                backgroundColor: ['#D63A09', '#8EAC1D', '#0087AA', '#F7970E']
              }
            ]
          }
        } else if (this.tpAvgSelect === 'Spend per Basket') {
          this.avgBars = {
            type: 'horizontalBar',
            labels: ['Lapsed', 'New', 'Reactivated', 'Returning'],
            datasets: [
              {
                data: [this.avgLapVisitSpend[this.tpSelect - 1], this.avgNewVisitSpend[this.tpSelect - 1], this.avgReactVisitSpend[this.tpSelect - 1], this.avgRetVisitSpend[this.tpSelect - 1]],
                label: 'Avg Spend Per Visit',
                backgroundColor: ['#D63A09', '#8EAC1D', '#0087AA', '#F7970E']
              }
            ]
          }
        } else if (this.tpAvgSelect === 'Items per Basket') {
          this.avgBars = {
            type: 'horizontalBar',
            labels: ['Lapsed', 'New', 'Reactivated', 'Returning'],
            datasets: [
              {
                data: [this.avgLapVisitItems[this.tpSelect - 1], this.avgNewVisitItems[this.tpSelect - 1], this.avgReactVisitItems[this.tpSelect - 1], this.avgRetVisitItems[this.tpSelect - 1]],
                label: 'Avg Items Per Visit',
                backgroundColor: ['#D63A09', '#8EAC1D', '#0087AA', '#F7970E']
              }
            ]
          }
        } else if (this.tpAvgSelect === 'Discount per Basket') {
          this.avgBars = {
            type: 'horizontalBar',
            labels: ['Lapsed', 'New', 'Reactivated', 'Returning'],
            datasets: [
              {
                data: [this.avgLapVisitDisc[this.tpSelect - 1], this.avgNewVisitDisc[this.tpSelect - 1], this.avgReactVisitDisc[this.tpSelect - 1], this.avgRetVisitDisc[this.tpSelect - 1]],
                label: 'Avg Discount Per Visit',
                backgroundColor: ['#D63A09', '#8EAC1D', '#0087AA', '#F7970E']
              }
            ]
          }
        }
      }
    }
  }

</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style>
  .bar_chart1 {
    position: relative;
    margin: 0 auto;
    height: 300px !important;
    width: 100% !important;
  }

  .padT18 {padding-top: 18px !important;}

  .inliner {display: inline-block;}

  .left_float {float: left;}

  .right_float {float: right;}

  .legend {
    display: inline-block;
    width: 38px;
    height: 12px;
    margin-right: 5px;
  }

  .legend_color1 {background-color: #D63809;}

  .legend_color2 {background-color: #8EAC1D;}

  .legend_color3 {background-color: #F7970E;}

  .legend_color4 {background-color: #0087AA;}

  .padR5 {padding-right: 5px;}
</style>
