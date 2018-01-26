<template>
  <v-container fluid class="quantileSummary pl-3 pr-3 mb-3">
    <!-- =====ROW1===== -->
    <v-layout row wrap class="pt-0 mt-0 mb-3">
      <v-flex xs12>
        <v-card class="pa-0 ma-0 grey lighten-2">
          <v-card-title primary-title class="primary">
            <h6 class="white--text text-xs-left mb-0">Segment Summary</h6>
          </v-card-title>
          <v-layout row wrap>
            <!--Dropdown1-->
            <v-flex xs12 sm2>
              <v-card flat class="pl-2 pr-2 pt-0 pb-0 grey lighten-2">
                <v-layout row wrap>
                  <v-flex xs12>
                    <div class="primary--text text-xs-left pl-0 pr-0 pb-0 pt-2">
                      Time Period:
                    </div>
                  </v-flex>
                  <v-flex xs12>
                    <v-card class="white pa-0">
                      <v-select v-bind:items="tpArray"
                                v-model="tpSelect"
                                label="Select Time Period"
                                single-line
                                bottom
                                hide-details
                                v-on:input="selectTP()"
                                class="pl-1 pr-1 mt-1 mb-2">
                      </v-select>
                    </v-card>
                  </v-flex>
                </v-layout>
              </v-card>
            </v-flex>
            <!--//Dropdown1-->
            <!-- LEGEND -->
            <v-flex xs12 sm6>
              <v-card flat class="pl-2 pr-2 grey lighten-2">
                <v-layout row wrap>
                  <!-- LEGEND -->
                  <v-flex xs12>
                    <div class="primary--text text-xs-left pl-0 pr-0 pb-1 pt-2">
                      Legend:
                    </div>
                    <div class="legend_contain white elevation-1">
                      <div class="inliner">
                        <div class="legend accent"></div>
                        <div class="caption inliner padR5">Best in Class</div>
                      </div>
                      <div class="inliner">
                        <div class="legend success"></div>
                        <div class="caption inliner padR5">Rising Stars</div>
                      </div>
                      <div class="inliner">
                        <div class="legend info"></div>
                        <div class="caption inliner padR5">Middle of the Road</div>
                      </div>
                      <div class="inliner">
                        <div class="legend warning"></div>
                        <div class="caption inliner padR5">Lapsing</div>
                      </div>
                      <div class="inliner">
                        <div class="legend error"></div>
                        <div class="caption inliner padR5">Deeply Lapsed</div>
                      </div>
                    </div>
                  </v-flex>
                  <!-- //LEGEND -->
                </v-layout>
              </v-card>
            </v-flex>
            <!-- //LEGEND -->
          </v-layout>
        </v-card>
      </v-flex>
    </v-layout>
    <!-- //=====ROW1===== -->
    <!-- =====ROW2===== -->
    <v-layout wrap row class="mb-3">
      <!--+++++col1+++++-->
      <v-flex xs12 md5 class="pt-0 mt-0">
        <v-card class="white pl-3 pr-3 pt-1 pb-1 card_height">
          <div class="title primary--text text-xs-center pa-1 mb-2">Aggregate Metrics for Time Period {{ this.tpSelect }}
          </div>
          <v-card class="mb-2 height_bars1" style="background-color:#848C98;">
            <v-card-text class="white--text height_bars2">
              <div class="subheading">Total Customers <span>{{ this.metricsItems.totalCusts }}</span></div>
            </v-card-text>
          </v-card>
          <v-card class="mb-2 height_bars1" style="background-color:#005E76;">
            <v-card-text class="white--text height_bars2">
              <div class="subheading">Total Visits <span>{{ this.metricsItems.totalVisits }}</span></div>
            </v-card-text>
          </v-card>
          <v-card class="mb-2 height_bars1" style="background-color:#848C98;">
            <v-card-text class="white--text height_bars2">
              <div class="subheading">Total Spend <span>{{ this.metricsItems.totalSpend }}</span></div>
            </v-card-text>
          </v-card>
          <v-card class="mb-2 height_bars1" style="background-color:#005E76;">
            <v-card-text class="white--text height_bars2">
              <div class="subheading">Total Units <span>{{ this.metricsItems.totalUnits }}</span></div>
            </v-card-text>
          </v-card>
        </v-card>
      </v-flex>
      <!--//+++++col1+++++-->
      <!--+++++col2+++++-->
      <v-flex xs12 md7 class="pt-0 mt-0">
        <v-card class="white pl-3 pr-3 pt-1 pb-3">
          <div class="title primary--text text-xs-center pa-1 mb-2">Segment Percent of Total Aggregate Metrics for Time Period {{ this.tpSelect
            }}
          </div>
          <v-layout row>
            <div class="doughnuts"><doughnut-chart :chart-data="bestCust" :width="100" :height="100"></doughnut-chart></div>
            <div class="doughnuts"><doughnut-chart :chart-data="risingCust" :width="100" :height="100"></doughnut-chart></div>
            <div class="doughnuts"><doughnut-chart :chart-data="middleCust" :width="100" :height="100"></doughnut-chart></div>
            <div class="doughnuts"><doughnut-chart :chart-data="lapsingCust" :width="100" :height="100"></doughnut-chart></div>
            <div class="doughnuts"><doughnut-chart :chart-data="deeplyCust" :width="100" :height="100"></doughnut-chart></div>
          </v-layout>
          <v-layout row>
            <div class="doughnuts"><doughnut-chart :chart-data="bestVisits" :width="100" :height="100"></doughnut-chart></div>
            <div class="doughnuts"><doughnut-chart :chart-data="risingVisits" :width="100" :height="100"></doughnut-chart></div>
            <div class="doughnuts"><doughnut-chart :chart-data="middleVisits" :width="100" :height="100"></doughnut-chart></div>
            <div class="doughnuts"><doughnut-chart :chart-data="lapsingVisits" :width="100" :height="100"></doughnut-chart></div>
            <div class="doughnuts"><doughnut-chart :chart-data="deeplyVisits" :width="100" :height="100"></doughnut-chart></div>
          </v-layout>
          <v-layout row>
            <div class="doughnuts"><doughnut-chart :chart-data="bestSpend" :width="100" :height="100"></doughnut-chart></div>
            <div class="doughnuts"><doughnut-chart :chart-data="risingSpend" :width="100" :height="100"></doughnut-chart></div>
            <div class="doughnuts"><doughnut-chart :chart-data="middleSpend" :width="100" :height="100"></doughnut-chart></div>
            <div class="doughnuts"><doughnut-chart :chart-data="lapsingSpend" :width="100" :height="100"></doughnut-chart></div>
            <div class="doughnuts"><doughnut-chart :chart-data="deeplySpend" :width="100" :height="100"></doughnut-chart></div>
          </v-layout>
          <v-layout row>
            <div class="doughnuts"><doughnut-chart :chart-data="bestUnits" :width="100" :height="100"></doughnut-chart></div>
            <div class="doughnuts"><doughnut-chart :chart-data="risingUnits" :width="100" :height="100"></doughnut-chart></div>
            <div class="doughnuts"><doughnut-chart :chart-data="middleUnits" :width="100" :height="100"></doughnut-chart></div>
            <div class="doughnuts"><doughnut-chart :chart-data="lapsingUnits" :width="100" :height="100"></doughnut-chart></div>
            <div class="doughnuts"><doughnut-chart :chart-data="deeplyUnits" :width="100" :height="100"></doughnut-chart></div>
          </v-layout>
        </v-card>
      </v-flex>
      <!--//+++++col2+++++-->
    </v-layout>
    <!-- //=====ROW2===== -->
    <!-- =====ROW3===== -->
    <v-layout wrap row>
      <!--+++++col1+++++-->
      <v-flex xs4 class="pt-0 mt-0">
        <v-card class="pl-3 pr-3 pt-1 pb-1 elevation-0">
          <div class="title primary--text text-xs-center pa-1">Overall Metrics Time Period {{ this.tpSelect }}</div>
        </v-card>
      </v-flex>
      <!--//+++++col1+++++-->
      <!--+++++col2+++++-->
      <v-flex xs8 class="pt-0 mt-0">
        <v-card class="pl-3 pr-3 pt-1 pb-1 elevation-0">
          <div class="title primary--text text-xs-center pa-1">Segment Metrics for Time Period {{ this.tpSelect }}</div>
        </v-card>
      </v-flex>
      <!--//+++++col2+++++-->
    </v-layout>
    <!-- //=====ROW3===== -->
    <!-- //=====ROW4===== -->
    <v-layout wrap row>
      <v-flex xs12>
        <v-card class="white">
        <v-data-table
            v-bind:headers="tableHeaders"
            :items="tableData"
            hide-actions>
          <template slot="items" scope="props">
            <td>{{ props.item.avgName }}</td>
            <td class="text-xs-right">{{ props.item.overallAvg }}</td>
            <td class="text-xs-right">{{ props.item.bestAvg }}</td>
            <td class="text-xs-right">{{ props.item.risingAvg }}</td>
            <td class="text-xs-right">{{ props.item.middleAvg }}</td>
            <td class="text-xs-right">{{ props.item.lapsingAvg }}</td>
            <td class="text-xs-right">{{ props.item.deeplyAvg }}</td>
          </template>
        </v-data-table>
        </v-card>
      </v-flex>
    </v-layout>
  </v-container>
</template>

<script>
  import {summary} from './javascript/lifecycle.service'
  import DoughnutChart from './lifecycleCharts/doughnutChart'

  // [JF] initiates numeral.js library in this vue component. must use together with numeral() or numeral().format()
  var numeral = require('numeral')

  export default {
    name: 'lifecycleSummary',
    components: {
      DoughnutChart
    },
    data () {
      return {
        incomingJson: {},
        tpSelect: 1,
        tpArray: [],
        tableHeaders: [
          {text: '', value: 'avgName'},
          {text: 'Overall', value: 'overallAvg'},
          {text: 'Best in Class', value: 'bestAvg'},
          {text: 'Rising Stars', value: 'risingAvg'},
          {text: 'Middle of the Road', value: 'middleAvg'},
          {text: 'Lapsing', value: 'lapsingAvg'},
          {text: 'Deeply Lapsed', value: 'deeplyAvg'}],
        tableData: [],
        tpDataArray: [],
        bestCust: {},
        bestVisits: {},
        bestSpend: {},
        bestUnits: {},
        risingCust: {},
        risingVisits: {},
        risingSpend: {},
        risingUnits: {},
        middleCust: {},
        middleVisits: {},
        middleSpend: {},
        middleUnits: {},
        lapsingCust: {},
        lapsingVisits: {},
        lapsingSpend: {},
        lapsingUnits: {},
        deeplyCust: {},
        deeplyVisits: {},
        deeplySpend: {},
        deeplyUnits: {}
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
      metricsItems: function () {
        let compItems = {
          'totalCusts': numeral(this.jsonMsg.timePeriods[this.tpSelect - 1].totalCustCount).format('0,0'),
          'totalVisits': numeral(this.jsonMsg.timePeriods[this.tpSelect - 1].totalTxnCount).format('0,0'),
          'totalSpend': numeral(this.jsonMsg.timePeriods[this.tpSelect - 1].totalSales).format('$0,0.00'),
          'totalUnits': numeral(this.jsonMsg.timePeriods[this.tpSelect - 1].totalItems).format('0,0')
        }
        return compItems
      }
    },
    mounted () {
      if (this.jobApp === 'Lifecycle' || this.jobApp === 'lifecycle') {
        this.$store.commit('switchApp', {module: 'Lifecycle'})
        this.getResults()
      } else {
        alert('Please select a Core Lifecycle job from Job History')
        this.$router.push('/Lifecycle/')
      }
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
            this.parseJson()
            this.createDoughnuts()
           // this.createAggMetrics()
          })
      },

      parseJson () {
        console.log('jsonMsg')
        console.log(this.jsonMsg)

        var tempTpArray = []
        var tempTableData = []
        var tableRows = ['AvgRecencyCust', 'AvgFrequencyCust', 'AvgSpendCust', 'AvgItemsCust', 'AvgSpendVisit']

        for (let i = 0; i < this.jsonMsg.timePeriods.length; i++) {
          tempTpArray.push(this.jsonMsg.timePeriods[i].timePeriod)
          tempTableData.push([{
            'avgName': tableRows[0],
            'overallAvg': numeral((this.jsonMsg.timePeriods[i].bestRecencyTotal + this.jsonMsg.timePeriods[i].risingRecencyTotal +
            this.jsonMsg.timePeriods[i].middleRecencyTotal + this.jsonMsg.timePeriods[i].lapsingRecencyTotal +
            this.jsonMsg.timePeriods[i].deeplyRecencyTotal) / this.jsonMsg.timePeriods[i].totalCustCount).format('0.00'),
            'bestAvg': numeral(this.jsonMsg.timePeriods[i].bestAvgRecency).format('0.00'),
            'risingAvg': numeral(this.jsonMsg.timePeriods[i].risingAvgRecency).format('0.00'),
            'middleAvg': numeral(this.jsonMsg.timePeriods[i].middleAvgRecency).format('0.00'),
            'lapsingAvg': numeral(this.jsonMsg.timePeriods[i].lapsingAvgRecency).format('0.00'),
            'deeplyAvg': numeral(this.jsonMsg.timePeriods[i].deeplyAvgRecency).format('0.00')
          }, {
            'avgName': tableRows[1],
            'overallAvg': numeral((this.jsonMsg.timePeriods[i].bestTxnTotal + this.jsonMsg.timePeriods[i].risingTxnTotal +
            this.jsonMsg.timePeriods[i].middleTxnTotal + this.jsonMsg.timePeriods[i].lapsingTxnTotal +
            this.jsonMsg.timePeriods[i].deeplyTxnTotal) / this.jsonMsg.timePeriods[i].totalCustCount).format('0.00'),
            'bestAvg': numeral(this.jsonMsg.timePeriods[i].bestAvgFreq).format('0.00'),
            'risingAvg': numeral(this.jsonMsg.timePeriods[i].risingAvgFreq).format('0.00'),
            'middleAvg': numeral(this.jsonMsg.timePeriods[i].middleAvgFreq).format('0.00'),
            'lapsingAvg': numeral(this.jsonMsg.timePeriods[i].lapsingAvgFreq).format('0.00'),
            'deeplyAvg': numeral(this.jsonMsg.timePeriods[i].deeplyAvgFreq).format('0.00')
          }, {
            'avgName': tableRows[2],
            'overallAvg': numeral((this.jsonMsg.timePeriods[i].bestSalesTotal + this.jsonMsg.timePeriods[i].risingSalesTotal +
            this.jsonMsg.timePeriods[i].middleSalesTotal + this.jsonMsg.timePeriods[i].lapsingSalesTotal +
            this.jsonMsg.timePeriods[i].deeplySalesTotal) / this.jsonMsg.timePeriods[i].totalCustCount).format('$0.00'),
            'bestAvg': numeral(this.jsonMsg.timePeriods[i].bestAvgSales).format('$0.00'),
            'risingAvg': numeral(this.jsonMsg.timePeriods[i].risingAvgSales).format('$0.00'),
            'middleAvg': numeral(this.jsonMsg.timePeriods[i].middleAvgSales).format('$0.00'),
            'lapsingAvg': numeral(this.jsonMsg.timePeriods[i].lapsingAvgSales).format('$0.00'),
            'deeplyAvg': numeral(this.jsonMsg.timePeriods[i].deeplyAvgSales).format('$0.00')
          }, {
            'avgName': tableRows[3],
            'overallAvg': numeral((this.jsonMsg.timePeriods[i].bestItemTotal + this.jsonMsg.timePeriods[i].risingItemTotal +
            this.jsonMsg.timePeriods[i].middleItemTotal + this.jsonMsg.timePeriods[i].lapsingItemTotal +
            this.jsonMsg.timePeriods[i].deeplyItemTotal) / this.jsonMsg.timePeriods[i].totalCustCount).format('0.00'),
            'bestAvg': numeral(this.jsonMsg.timePeriods[i].bestAvgItems).format('0.00'),
            'risingAvg': numeral(this.jsonMsg.timePeriods[i].risingAvgItems).format('0.00'),
            'middleAvg': numeral(this.jsonMsg.timePeriods[i].middleAvgItems).format('0.00'),
            'lapsingAvg': numeral(this.jsonMsg.timePeriods[i].lapsingAvgItems).format('0.00'),
            'deeplyAvg': numeral(this.jsonMsg.timePeriods[i].deeplyAvgItems).format('0.00')
          }, {
            'avgName': tableRows[4],
            'overallAvg': numeral((this.jsonMsg.timePeriods[i].bestSalesTotal + this.jsonMsg.timePeriods[i].risingSalesTotal +
            this.jsonMsg.timePeriods[i].middleSalesTotal + this.jsonMsg.timePeriods[i].lapsingSalesTotal +
            this.jsonMsg.timePeriods[i].deeplySalesTotal) / this.jsonMsg.timePeriods[i].totalTxnCount).format('$0.00'),
            'bestAvg': numeral(this.jsonMsg.timePeriods[i].bestSalesTotal / this.jsonMsg.timePeriods[i].bestTxnTotal).format('$0.00'),
            'risingAvg': numeral(this.jsonMsg.timePeriods[i].risingSalesTotal / this.jsonMsg.timePeriods[i].risingTxnTotal).format('$0.00'),
            'middleAvg': numeral(this.jsonMsg.timePeriods[i].middleSalesTotal / this.jsonMsg.timePeriods[i].middleTxnTotal).format('$0.00'),
            'lapsingAvg': numeral(this.jsonMsg.timePeriods[i].lapsingSalesTotal / this.jsonMsg.timePeriods[i].lapsingTxnTotal).format('$0.00'),
            'deeplyAvg': numeral(this.jsonMsg.timePeriods[i].deeplySalesTotal / this.jsonMsg.timePeriods[i].deeplyTxnTotal).format('$0.00')
          }
          ])
        }

        this.tpArray = tempTpArray
        this.tpDataArray = tempTableData
        this.tableData = tempTableData[0]

        console.log('tempTableData')
        console.log(tempTableData)
      },

      createDoughnuts () {
        this.bestCust = {
          datasets: [{
            data: [+(this.jsonMsg.timePeriods[this.tpSelect - 1].bestPercentCustBase * 100).toFixed(2),
              (100 - (this.jsonMsg.timePeriods[this.tpSelect - 1].bestPercentCustBase * 100)).toFixed(2)],
            backgroundColor: ['#003947', '#848C98']
          }],
          labels: [
            'Best Customers',
            'Other Customers'
          ]
        }

        this.bestVisits = {
          datasets: [{
            data: [+(this.jsonMsg.timePeriods[this.tpSelect - 1].bestPercentTxnBase * 100).toFixed(2),
              (100 - (this.jsonMsg.timePeriods[this.tpSelect - 1].bestPercentTxnBase * 100)).toFixed(2)],
            backgroundColor: ['#003947', '#848C98']
          }],
          labels: [
            'Best Visits',
            'Other Visits'
          ]
        }

        this.bestSpend = {
          datasets: [{
            data: [+(this.jsonMsg.timePeriods[this.tpSelect - 1].bestPercentSalesBase * 100).toFixed(2),
              (100 - (this.jsonMsg.timePeriods[this.tpSelect - 1].bestPercentSalesBase * 100)).toFixed(2)],
            backgroundColor: ['#003947', '#848C98']
          }],
          labels: [
            'Best Spend',
            'Other Spend'
          ]
        }

        var tempBestUnits = (this.jsonMsg.timePeriods[this.tpSelect - 1].bestItemTotal / this.jsonMsg.timePeriods[this.tpSelect - 1].totalItems) * 100

        this.bestUnits = {
          datasets: [{
            data: [+(tempBestUnits).toFixed(2),
              +(100 - tempBestUnits).toFixed(2)],
            backgroundColor: ['#003947', '#848C98']
          }],
          labels: [
            'Best Units',
            'Other Units'
          ]
        }

        this.risingCust = {
          datasets: [{
            data: [+(this.jsonMsg.timePeriods[this.tpSelect - 1].risingPercentCustBase * 100).toFixed(2),
              (100 - (this.jsonMsg.timePeriods[this.tpSelect - 1].risingPercentCustBase * 100)).toFixed(2)],
            backgroundColor: ['#8EAC1D', '#848C98']
          }],
          labels: [
            'Rising Customers',
            'Other Customers'
          ]
        }

        this.risingVisits = {
          datasets: [{
            data: [+(this.jsonMsg.timePeriods[this.tpSelect - 1].risingPercentTxnBase * 100).toFixed(2),
              (100 - (this.jsonMsg.timePeriods[this.tpSelect - 1].risingPercentTxnBase * 100)).toFixed(2)],
            backgroundColor: ['#8EAC1D', '#848C98']
          }],
          labels: [
            'Rising Visits',
            'Other Visits'
          ]
        }

        this.risingSpend = {
          datasets: [{
            data: [+(this.jsonMsg.timePeriods[this.tpSelect - 1].risingPercentSalesBase * 100).toFixed(2),
              (100 - (this.jsonMsg.timePeriods[this.tpSelect - 1].risingPercentSalesBase * 100)).toFixed(2)],
            backgroundColor: ['#8EAC1D', '#848C98']
          }],
          labels: [
            'Rising Spend',
            'Other Spend'
          ]
        }

        var temprisingUnits = (this.jsonMsg.timePeriods[this.tpSelect - 1].risingItemTotal / this.jsonMsg.timePeriods[this.tpSelect - 1].totalItems) * 100

        this.risingUnits = {
          datasets: [{
            data: [+(temprisingUnits).toFixed(2),
              +(100 - temprisingUnits).toFixed(2)],
            backgroundColor: ['#8EAC1D', '#848C98']
          }],
          labels: [
            'Rising Units',
            'Other Units'
          ]
        }

        this.middleCust = {
          datasets: [{
            data: [+(this.jsonMsg.timePeriods[this.tpSelect - 1].middlePercentCustBase * 100).toFixed(2),
              (100 - (this.jsonMsg.timePeriods[this.tpSelect - 1].middlePercentCustBase * 100)).toFixed(2)],
            backgroundColor: ['#0087AA', '#848C98']
          }],
          labels: [
            'Middle Customers',
            'Other Customers'
          ]
        }

        this.middleVisits = {
          datasets: [{
            data: [+(this.jsonMsg.timePeriods[this.tpSelect - 1].middlePercentTxnBase * 100).toFixed(2),
              (100 - (this.jsonMsg.timePeriods[this.tpSelect - 1].middlePercentTxnBase * 100)).toFixed(2)],
            backgroundColor: ['#0087AA', '#848C98']
          }],
          labels: [
            'Middle Visits',
            'Other Visits'
          ]
        }

        this.middleSpend = {
          datasets: [{
            data: [+(this.jsonMsg.timePeriods[this.tpSelect - 1].middlePercentSalesBase * 100).toFixed(2),
              (100 - (this.jsonMsg.timePeriods[this.tpSelect - 1].middlePercentSalesBase * 100)).toFixed(2)],
            backgroundColor: ['#0087AA', '#848C98']
          }],
          labels: [
            'Middle Spend',
            'Other Spend'
          ]
        }

        var tempmiddleUnits = (this.jsonMsg.timePeriods[this.tpSelect - 1].middleItemTotal / this.jsonMsg.timePeriods[this.tpSelect - 1].totalItems) * 100

        this.middleUnits = {
          datasets: [{
            data: [+(tempmiddleUnits).toFixed(2),
              +(100 - tempmiddleUnits).toFixed(2)],
            backgroundColor: ['#0087AA', '#848C98']
          }],
          labels: [
            'Middle Units',
            'Other Units'
          ]
        }

        this.lapsingCust = {
          datasets: [{
            data: [+(this.jsonMsg.timePeriods[this.tpSelect - 1].lapsingPercentCustBase * 100).toFixed(2),
              (100 - (this.jsonMsg.timePeriods[this.tpSelect - 1].lapsingPercentCustBase * 100)).toFixed(2)],
            backgroundColor: ['#F7970E', '#848C98']
          }],
          labels: [
            'Lapsing Customers',
            'Other Customers'
          ]
        }

        this.lapsingVisits = {
          datasets: [{
            data: [+(this.jsonMsg.timePeriods[this.tpSelect - 1].lapsingPercentTxnBase * 100).toFixed(2),
              (100 - (this.jsonMsg.timePeriods[this.tpSelect - 1].lapsingPercentTxnBase * 100)).toFixed(2)],
            backgroundColor: ['#F7970E', '#848C98']
          }],
          labels: [
            'Lapsing Visits',
            'Other Visits'
          ]
        }

        this.lapsingSpend = {
          datasets: [{
            data: [+(this.jsonMsg.timePeriods[this.tpSelect - 1].lapsingPercentSalesBase * 100).toFixed(2),
              (100 - (this.jsonMsg.timePeriods[this.tpSelect - 1].lapsingPercentSalesBase * 100)).toFixed(2)],
            backgroundColor: ['#F7970E', '#848C98']
          }],
          labels: [
            'Lapsing Spend',
            'Other Spend'
          ]
        }

        var templapsingUnits = (this.jsonMsg.timePeriods[this.tpSelect - 1].lapsingItemTotal / this.jsonMsg.timePeriods[this.tpSelect - 1].totalItems) * 100

        this.lapsingUnits = {
          datasets: [{
            data: [+(templapsingUnits).toFixed(2),
              +(100 - templapsingUnits).toFixed(2)],
            backgroundColor: ['#F7970E', '#848C98']
          }],
          labels: [
            'Lapsing Units',
            'Other Units'
          ]
        }

        this.deeplyCust = {
          datasets: [{
            data: [+(this.jsonMsg.timePeriods[this.tpSelect - 1].deeplyPercentCustBase * 100).toFixed(2),
              (100 - (this.jsonMsg.timePeriods[this.tpSelect - 1].deeplyPercentCustBase * 100)).toFixed(2)],
            backgroundColor: ['#D63809', '#848C98']
          }],
          labels: [
            'Deeply Customers',
            'Other Customers'
          ]
        }

        this.deeplyVisits = {
          datasets: [{
            data: [+(this.jsonMsg.timePeriods[this.tpSelect - 1].deeplyPercentTxnBase * 100).toFixed(2),
              (100 - (this.jsonMsg.timePeriods[this.tpSelect - 1].deeplyPercentTxnBase * 100)).toFixed(2)],
            backgroundColor: ['#D63809', '#848C98']
          }],
          labels: [
            'Deeply Visits',
            'Other Visits'
          ]
        }

        this.deeplySpend = {
          datasets: [{
            data: [+(this.jsonMsg.timePeriods[this.tpSelect - 1].deeplyPercentSalesBase * 100).toFixed(2),
              (100 - (this.jsonMsg.timePeriods[this.tpSelect - 1].deeplyPercentSalesBase * 100)).toFixed(2)],
            backgroundColor: ['#D63809', '#848C98']
          }],
          labels: [
            'Deeply Spend',
            'Other Spend'
          ]
        }

        var tempdeeplyUnits = (this.jsonMsg.timePeriods[this.tpSelect - 1].deeplyItemTotal / this.jsonMsg.timePeriods[this.tpSelect - 1].totalItems) * 100

        this.deeplyUnits = {
          datasets: [{
            data: [+(tempdeeplyUnits).toFixed(2),
              +(100 - tempdeeplyUnits).toFixed(2)],
            backgroundColor: ['#D63809', '#848C98']
          }],
          labels: [
            'Deeply Units',
            'Other Units'
          ]
        }
      },

      selectTP () {
        this.tableData = this.tpDataArray[this.tpSelect - 1]
        this.createDoughnuts()
      }
    }
  }

</script>

<style scoped>
.inliner {
  display: inline-block;
}

.legend {
  display: inline-block;
  width: 12px;
  height: 12px;
  margin-right: 5px;
}

.legend_contain {
  display: inline-block;
  line-height: 26px;
  min-height: 34px;
  padding: 4px 8px 4px 8px;
  margin:0 0 8px 0;
}

.doughnuts {
  width:20%;
  position:relative;
  margin:0 auto;
}
.card_height {height:100% !important;}
.height_bars1 {height:21% !important;}
.height_bars2 {height:100% !important; display:table;}
.height_bars2 div {display:table-cell; vertical-align: middle !important;font-weight: bold;}
.height_bars2 span {float:right;}
</style>
