<template>
  <v-container fluid class="quantileSummary pl-3 pr-3 mb-3">
    <!-- =====ROW1===== -->
    <HelpNavQuantile ref="helpNav"></HelpNavQuantile>
    <v-layout row wrap class="pt-0 mt-0">
      <v-flex xs12>
        <v-card class="pa-0 ma-0 grey lighten-2">
          <v-card-title primary-title class="primary">
            <h6 class="white--text text-xs-left mb-0">Customer Pareto Analysis</h6>
            <v-spacer></v-spacer>
            <v-btn
                @click.native="getHelpSection()"
                icon slot="activator"
                class="success--text"
                style="height:auto !important;">
                  <v-icon class="pa-0 ma-0 white--text icon_help">help_outline</v-icon>
            </v-btn>
          </v-card-title>
          <v-layout row wrap>
            <!--Dropdown1-->
            <v-flex xs6 sm4 md3>
              <v-card flat class="pl-2 pr-2 pt-0 pb-0 grey lighten-2">
                <v-layout row wrap>
                  <v-flex xs12>
                    <div class="primary--text text-xs-left pl-0 pr-0 pb-0 pt-2">
                      Customer Quantiles:
                    </div>
                  </v-flex>
                  <v-flex xs12>
                      <v-select v-bind:items="quantiles"
                                v-model="quantileSelect"
                                label="Select Quantiles"
                                multiple
                                single-line
                                bottom
                                hide-details
                                v-on:input="selectQuantile()"
                                class="pl-1 pr-1 mt-1 mb-2 white elevation-1">
                      </v-select>
                  </v-flex>
                </v-layout>
              </v-card>
            </v-flex>
            <!--//Dropdown1-->
            <!--Dropdown2-->
            <v-flex xs6 sm4 md3>
              <v-card flat class="pl-2 pr-2 grey lighten-2">
                <v-layout row wrap>
                  <v-flex xs12>
                    <div class="primary--text text-xs-left pl-0 pr-0 pb-0 pt-2">
                      Time Period:
                    </div>
                  </v-flex>
                  <v-flex xs12>
                      <v-select
                          v-bind:items="TPArray"
                          v-model="TPSelect"
                          label="Select Time Period"
                          single-line
                          bottom
                          hide-details
                          v-on:input="selectTP()"
                          class="pl-1 pr-1 mt-1 mb-2 white elevation-1">
                      </v-select>
                  </v-flex>
                </v-layout>
              </v-card>
            </v-flex>
            <!--//Dropdown2-->
            <!--Dropdown3-->
            <v-flex xs12 sm4 md3>
              <v-card flat class="pl-2 pr-2 grey lighten-2">
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
            <!--//Dropdown3-->
          </v-layout>
        </v-card>
      </v-flex>
    </v-layout>
    <!-- //=====ROW1===== -->
    <!-- =====ROW2===== -->
    <v-layout wrap row>
      <v-flex xs12 class="pt-0 mt-0">
        <v-card class="pl-1 pr-1 pt-2 pb-2">
          <div class="title primary--text text-xs-center">
            <em><span class="grey--text darken-2">Visits</span> Analysis for Quantiles <span class="grey--text darken-2">All</span> during Period <span class="grey--text darken-2">1</span>, with reference line at <span class="grey--text darken-2">70.00</span> percent of total</em>
          </div>
        </v-card>
      </v-flex>
    </v-layout>
    <!-- //=====ROW2===== -->
    <!-- =====ROW3===== -->
    <v-layout wrap row>
      <v-flex xs12 class="pt-0 mt-3">
        <v-card class="white mob_padLR_0">
          <v-card-title primary-title class="primary">
            <h6 class="white--text text-xs-left mb-0">Quantile Pareto Analysis</h6>
          </v-card-title>
          <v-layout row wrap class="mt-2 mb-3 mob_marB_0">
            <v-flex xs12 fill-height>
              <pareto-chart :chart-data="paretoData" :options="paretoOptions" class="pareto_chart1"></pareto-chart>
            </v-flex>
          </v-layout>
        </v-card>
      </v-flex>
    </v-layout>
    <!-- //=====ROW3===== -->
    <!-- =====ROW4===== -->
    <v-layout wrap row>
      <v-flex xs12>
        <v-card class="white">
          <v-card-title primary-title class="primary">
            <h6 class="white--text text-xs-left mb-0">Quantile Purchase Profiles</h6>
          </v-card-title>
          <v-data-table v-if="dimension === 'customer'"
              v-bind:headers="custHeaders"
              :items="custItems"
              hide-actions>
            <template slot="items" scope="props">
              <td>{{ props.item.quantile }}</td>
              <td class="text-xs-right">{{ props.item.avgCustSales }}</td>
              <td class="text-xs-right">{{ props.item.avgVisitSpend }}</td>
              <td class="text-xs-right">{{ props.item.avgItemSales }}</td>
              <td class="text-xs-right">{{ props.item.avgCustUnits }}</td>
              <td class="text-xs-right">{{ props.item.avgVisitUnits }}</td>
              <td class="text-xs-right">{{ props.item.avgCustVisits }}</td>
            </template>
          </v-data-table>

          <v-data-table v-if="dimension === 'store'"
              v-bind:headers="storeHeaders"
              :items="custItems"
              hide-actions>
            <template slot="items" scope="props">
              <td>{{ props.item.quantile }}</td>
              <td class="text-xs-right">{{ props.item.avgCustSales }}</td>
              <td class="text-xs-right">{{ props.item.avgCustUnits }}</td>
              <td class="text-xs-right">{{ props.item.avgCustVisits }}</td>
              <td class="text-xs-right">{{ props.item.avgStoreVisitSpend }}</td>
              <td class="text-xs-right">{{ props.item.avgStoreVisitUnits }}</td>
              <td class="text-xs-right">{{ props.item.avgStoreSales }}</td>
              <td class="text-xs-right">{{ props.item.avgStoreCust }}</td>
              <td class="text-xs-right">{{ props.item.avgStoreUnits }}</td>
            </template>
          </v-data-table>
        </v-card>
      </v-flex>
    </v-layout>
    <!-- //=====ROW4===== -->
  </v-container>
</template>

<script>
  import ParetoChart from './balorCharts/ParetoChart'
  import {quantSum} from './javascript/quantile.service'
  import HelpNavQuantile from './HelpNavQuantile.vue'

  // [JF] initiates numeral.js library in this vue component. must use together with numeral() or numeral().format()
  var numeral = require('numeral')

  export default {
    name: 'quantProfile',
    data () {
      return {
        TPArray: [],
        TPSelect: 1,
        quantiles: [],
        quantileSelect: ['All'],
        metrics: [
          'Spend',
          'Units',
          'Visits'
        ],
        metricSelect: 'Spend',
        spendArrayFull: [],
        unitArrayFull: [],
        visitArrayFull: [],
        spendArrayCurr: [],
        unitArrayCurr: [],
        visitArrayCurr: [],
        quantileArray: [],
        quantArrayCurr: [],
        spendPerArray: [],
        spendPerArrayCurr: [],
        unitPerArray: [],
        unitPerArrayCurr: [],
        visitPerArray: [],
        visitPerArrayCurr: [],
        paretoData: {},
        custHeaders: [
          {text: 'Quantiles', value: 'quantile', left: true, sortable: true},
          {text: 'Spend per Member($)', value: 'avgCustSales'},
          {text: 'Spend per Visit($)', value: 'avgVisitSpend'},
          {text: 'Spend per Item($)', value: 'avgItemSales'},
          {text: 'Units per Member', value: 'avgCustUnits'},
          {text: 'Units per Visit', value: 'avgVisitUnits'},
          {text: 'Visit per Member', value: 'avgCustVisits'}
        ],
        storeHeaders: [
          {text: 'Quantiles', value: 'quantile', left: true, sortable: true},
          {text: 'Spend per Member($)', value: 'avgCustSales'},
          {text: 'Units per Member', value: 'avgCustUnits'},
          {text: 'Visit per Member', value: 'avgCustVisits'},
          {text: 'Spend per Visit($)', value: 'avgStoreVisitSpend'},
          {text: 'Units per Visit($)', value: 'avgStoreVisitUnits'},
          {text: 'Spend per Store($)', value: 'avgStoreSales'},
          {text: 'Cust per Store', value: 'avgStoreCust'},
          {text: 'Units per Store', value: 'avgStoreUnits'}
        ],
        custItems: [],
        groupName: 'Quantile'
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
      dimension: function () {
        return this.incomingJson.data.quantileDimension.dimension
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
                labelString: this.metricSelect
              }
            }, {
              id: 'right-y-axis',
              type: 'linear',
              position: 'right',
              scaleLabel: {
                display: true,
                labelString: 'Percent of Total ' + this.metricSelect
              }
            }],
            xAxes: [{
              barPercentage: 0.8,
              scaleLabel: {
                display: true,
                labelString: 'Customer ' + this.groupName + ' Groups'
              }
            }]
          },
          legend: {
            display: false
          }
        }
        return opts
      }
    },
    components: {
      ParetoChart,
      HelpNavQuantile
    },
    mounted () {
      if (this.jobApp === 'Pareto' || this.jobApp === 'pareto') {
        this.$store.commit('switchApp', {module: 'Pareto'})
        this.getResults()
      } else {
        alert('Please select a Pareto job from Job History')
        this.$router.push('/Pareto/')
      }
    },
    methods: {
      getHelpSection: function () {
        var hpNav = this.$refs.helpNav.$refs.helpNav
        hpNav.value = !hpNav.value
      },
      getResults () {
        quantSum(this.jobId)
          .catch(err => {
            alert('Could not get Quantile Summary results. ' + err.message.toString())
          })
          .then((response) => {
            this.incomingJson = response.data
            console.log(this.incomingJson)
            this.createPareto()
            this.createTable()
          })
      },

      createTable () {
        var tempObj = []
        if (this.jsonMsg.quantileDimension.dimension === 'customer') {
          this.tableHeaders = this.custHeaders
          for (let i = 0; i < this.jsonMsg.quantileResult.length; i++) {
            if (this.jsonMsg.quantileResult[i].timePeriod === this.TPSelect) {
              tempObj.push({
                'quantile': this.jsonMsg.quantileResult[i].quantile,
                'avgCustSales': numeral(this.jsonMsg.quantileResult[i].summaryAverages.avgSpend).format('$0.00'),
                'avgVisitSpend': numeral(this.jsonMsg.quantileResult[i].summaryAverages.avgVisitSpend).format('$0.00'),
                'avgItemSales': numeral(this.jsonMsg.quantileResult[i].summaryAverages.avgItemSales).format('$0.00'),
                'avgCustUnits': numeral(this.jsonMsg.quantileResult[i].summaryAverages.avgUnits).format('0.00'),
                'avgVisitUnits': numeral(this.jsonMsg.quantileResult[i].summaryAverages.avgVisitUnits).format('0.00'),
                'avgCustVisits': numeral(this.jsonMsg.quantileResult[i].summaryAverages.avgVisits).format('0.00')
              })
            }
          }
        } else if (this.jsonMsg.quantileDimension.dimension === 'store') {
          this.tableHeaders = this.storeHeaders
          for (let i = 0; i < this.jsonMsg.quantileResult.length; i++) {
            if (this.jsonMsg.quantileResult[i].timePeriod === this.TPSelect) {
              tempObj.push({
                'quantile': this.jsonMsg.quantileResult[i].quantile,
                'avgCustSales': numeral(this.jsonMsg.quantileResult[i].summaryAverages.avgCustSales).format('$0.00'),
                'avgCustUnits': numeral(this.jsonMsg.quantileResult[i].summaryAverages.avgCustUnits).format('0.00'),
                'avgCustVisits': numeral(this.jsonMsg.quantileResult[i].summaryAverages.avgCustVisits).format('0.00'),
                'avgStoreVisitSpend': numeral(this.jsonMsg.quantileResult[i].summaryAverages.avgVisitSpend).format('$0.00'),
                'avgStoreVisitUnits': numeral(this.jsonMsg.quantileResult[i].summaryAverages.avgVisitUnits).format('0.00'),
                'avgStoreSales': numeral(this.jsonMsg.quantileResult[i].summaryAverages.avgSpend).format('$0,0.00'),
                'avgStoreCust': numeral(this.jsonMsg.quantileResult[i].summaryAverages.avgStoreCust).format('0,0.00'),
                'avgStoreUnits': numeral(this.jsonMsg.quantileResult[i].summaryAverages.avgUnits).format('0,0.00')
              })
            }
          }
        }

        this.custItems = tempObj
        console.log(tempObj)

        if (tempObj.length === 2) {
          this.groupName = 'Median'
        } else if (tempObj.length === 4) {
          this.groupName = 'Quartile'
        } else if (tempObj.length === 5) {
          this.groupName = 'Quintile'
        } else if (tempObj.length === 10) {
          this.groupName = 'Decile'
        } else if (tempObj.length === 20) {
          this.groupName = 'Ventile'
        } else {
          this.groupName = 'Quantile'
        }
      },
      selectQuantile () {
        if (this.quantileSelect.indexOf('All') !== -1) {
          console.log('contains all')
          this.spendArrayCurr = this.spendArrayFull
          this.unitArrayCurr = this.unitArrayFull
          this.visitArrayCurr = this.visitArrayFull
          this.quantArrayCurr = this.quantileArray
          this.selectMetric()
        } else {
          var sorted = this.quantileSelect.slice().sort((a, b) => (a - b))
          sorted.slice().sort()
          console.log(sorted)
          console.log('no all here')
          var unitArray = []
          var spendArray = []
          var visitArray = []
          var quantArray = []
          var unitPer = []
          var spendPer = []
          var visitPer = []

          for (var i = 0; i < sorted.length; i++) {
            unitArray.push(this.unitArrayFull[sorted[i] - 1])
            spendArray.push(this.spendArrayFull[sorted[i] - 1])
            visitArray.push(this.visitArrayFull[sorted[i] - 1])
            quantArray.push(this.quantileArray[sorted[i] - 1])
            unitPer.push(this.unitPerArray[sorted[i] - 1])
            spendPer.push(this.spendPerArray[sorted[i] - 1])
            visitPer.push(this.visitPerArray[sorted[i] - 1])
          }

          this.spendArrayCurr = spendArray
          this.unitArrayCurr = unitArray
          this.visitArrayCurr = visitArray
          this.quantArrayCurr = quantArray
          this.spendPerArrayCurr = spendPer
          this.visitPerArrayCurr = visitPer
          this.unitPerArrayCurr = unitPer

          this.selectMetric()
        }
      },
      selectMetric () {
        console.log(this.spendPerArrayCurr)
        if (this.metricSelect === 'Spend') {
          this.paretoData = {
            labels: this.quantArrayCurr,
            datasets: [
              {
                data: this.spendArrayCurr,
                label: 'Total Spend',
                backgroundColor: '#0087AA'
              }, {
                data: this.spendPerArrayCurr,
                label: 'Cumlative Percent Spend',
                type: 'line',
                yAxisID: 'right-y-axis',
                backgroundColor: '#F7970E',
                borderColor: '#F7970E',
                fill: false
              }
            ]
          }
        } else if (this.metricSelect === 'Units') {
          this.paretoData = {
            labels: this.quantArrayCurr,
            datasets: [
              {
                data: this.unitArrayCurr,
                label: 'Total Units',
                backgroundColor: '#0087AA'
              }, {
                data: this.unitPerArrayCurr,
                label: 'Cumlative Percent Units',
                type: 'line',
                yAxisID: 'right-y-axis',
                backgroundColor: '#F7970E',
                borderColor: '#F7970E',
                fill: false
              }
            ]
          }
        } else if (this.metricSelect === 'Visits') {
          this.paretoData = {
            labels: this.quantArrayCurr,
            datasets: [
              {
                data: this.visitArrayCurr,
                label: 'Total Visits',
                backgroundColor: '#0087AA'
              }, {
                data: this.visitPerArrayCurr,
                label: 'Cumlative Percent Visits',
                type: 'line',
                yAxisID: 'right-y-axis',
                backgroundColor: '#F7970E',
                borderColor: '#F7970E',
                fill: false
              }
            ]
          }
        }
      },
      createPareto () {
        var tempVisit = []
        var tempUnit = []
        var tempSpend = []
        var tempObj = []
        var tempQuant = []
        var tempTP = []

        for (var i = 0; i < this.jsonMsg.quantileResult.length; i++) {
          if (this.jsonMsg.quantileResult[i].timePeriod === this.TPSelect) {
            tempObj.push({
              'quantile': this.jsonMsg.quantileResult[i].quantile,
              'totalSpend': this.jsonMsg.quantileResult[i].summaryTotals.totalSpend,
              'totalVisits': this.jsonMsg.quantileResult[i].summaryTotals.totalVisits,
              'totalUnits': this.jsonMsg.quantileResult[i].summaryTotals.totalUnits
            })
          }

          if (tempTP.indexOf(this.jsonMsg.quantileResult[i].timePeriod) === -1) {
            tempTP.push(this.jsonMsg.quantileResult[i].timePeriod)
          }
        }

        var sortArray = tempObj.sort(function (a, b) {
          var x = a['quantile']
          var y = b['quantile']
          return ((x < y) ? -1 : ((x > y) ? 1 : 0))
        })

        var ttlSpend = 0
        var ttlUnit = 0
        var ttlVisit = 0

        for (var j = 0; j < sortArray.length; j++) {
          tempVisit.push(sortArray[j].totalVisits)
          ttlVisit += sortArray[j].totalVisits
          tempUnit.push(sortArray[j].totalUnits)
          ttlUnit += sortArray[j].totalUnits
          tempSpend.push(sortArray[j].totalSpend)
          ttlSpend += sortArray[j].totalSpend
          tempQuant.push(sortArray[j].quantile)
        }

        this.TPArray = tempTP
        this.spendArrayFull = tempSpend
        this.unitArrayFull = tempUnit
        this.visitArrayFull = tempVisit
        this.quantileArray = tempQuant
        this.spendArrayCurr = tempSpend
        this.unitArrayCurr = tempUnit
        this.visitArrayCurr = tempVisit
        var allArray = ['All']
        this.quantiles = allArray.concat(tempQuant)

        var tempUnitPer = []
        var tempTtlUnit = []
        var tempSpendPer = []
        var tempTtlSpend = []
        var tempVisitPer = []
        var tempTtlVisit = []

        for (var k = 0; k < this.spendArrayFull.length; k++) {
          if (k === 0) {
            tempSpendPer.push(this.spendArrayFull[k] / ttlSpend)
            tempTtlSpend.push(this.spendArrayFull[k])
          } else {
            tempSpendPer.push((tempTtlSpend[k - 1] + this.spendArrayFull[k]) / ttlSpend)
            tempTtlSpend.push(this.spendArrayFull[k] + tempTtlSpend[k - 1])
          }
        }
        for (var l = 0; l < this.unitArrayFull.length; l++) {
          if (l === 0) {
            tempUnitPer.push(this.unitArrayFull[l] / ttlUnit)
            tempTtlUnit.push(this.unitArrayFull[l])
          } else {
            tempUnitPer.push((tempTtlUnit[l - 1] + this.unitArrayFull[l]) / ttlUnit)
            tempTtlUnit.push(this.unitArrayFull[l] + tempTtlUnit[l - 1])
          }
        }
        for (var x = 0; x < this.visitArrayFull.length; x++) {
          if (x === 0) {
            tempVisitPer.push(this.visitArrayFull[x] / ttlVisit)
            tempTtlVisit.push(this.visitArrayFull[x])
          } else {
            tempVisitPer.push((tempTtlVisit[x - 1] + this.visitArrayFull[x]) / ttlVisit)
            tempTtlVisit.push(this.visitArrayFull[x] + tempTtlVisit[x - 1])
          }
        }

        this.spendPerArray = tempSpendPer
        this.unitPerArray = tempUnitPer
        this.visitPerArray = tempVisitPer

        this.spendPerArrayCurr = tempSpendPer
        this.unitPerArrayCurr = tempUnitPer
        this.visitPerArrayCurr = tempVisitPer

        this.quantArrayCurr = this.quantileArray

        this.paretoData = {
          labels: this.quantileArray,
          datasets: [
            {
              data: this.spendArrayFull,
              label: 'Total Spend',
              backgroundColor: '#0087AA'
            }, {
              data: this.spendPerArray,
              label: 'Cumlative Percent Spend',
              type: 'line',
              yAxisID: 'right-y-axis',
              backgroundColor: '#F7970E',
              borderColor: '#F7970E',
              fill: false
            }
          ]
        }
      },
      selectTP () {
        this.createTable()

        var tempVisit = []
        var tempUnit = []
        var tempSpend = []
        var tempObj = []

        for (var i = 0; i < this.jsonMsg.quantileResult.length; i++) {
          if (this.jsonMsg.quantileResult[i].timePeriod === this.TPSelect) {
            tempObj.push({
              'quantile': this.jsonMsg.quantileResult[i].quantile,
              'totalSpend': this.jsonMsg.quantileResult[i].summaryTotals.totalSpend,
              'totalVisits': this.jsonMsg.quantileResult[i].summaryTotals.totalVisits,
              'totalUnits': this.jsonMsg.quantileResult[i].summaryTotals.totalUnits
            })
          }
        }

        var sortArray = tempObj.sort(function (a, b) {
          var x = a['quantile']
          var y = b['quantile']
          return ((x < y) ? -1 : ((x > y) ? 1 : 0))
        })

        var ttlSpend = 0
        var ttlUnit = 0
        var ttlVisit = 0

        for (var j = 0; j < sortArray.length; j++) {
          tempVisit.push(sortArray[j].totalVisits)
          ttlVisit += sortArray[j].totalVisits
          tempUnit.push(sortArray[j].totalUnits)
          ttlUnit += sortArray[j].totalUnits
          tempSpend.push(sortArray[j].totalSpend)
          ttlSpend += sortArray[j].totalSpend
        }

        this.spendArrayFull = tempSpend
        this.unitArrayFull = tempUnit
        this.visitArrayFull = tempVisit
        this.spendArrayCurr = tempSpend
        this.unitArrayCurr = tempUnit
        this.visitArrayCurr = tempVisit

        var tempUnitPer = []
        var tempTtlUnit = []
        var tempSpendPer = []
        var tempTtlSpend = []
        var tempVisitPer = []
        var tempTtlVisit = []

        for (var k = 0; k < this.spendArrayFull.length; k++) {
          if (k === 0) {
            tempSpendPer.push(this.spendArrayFull[k] / ttlSpend)
            tempTtlSpend.push(this.spendArrayFull[k])
          } else {
            tempSpendPer.push((tempTtlSpend[k - 1] + this.spendArrayFull[k]) / ttlSpend)
            tempTtlSpend.push(this.spendArrayFull[k] + tempTtlSpend[k - 1])
          }
        }
        for (var l = 0; l < this.unitArrayFull.length; l++) {
          if (l === 0) {
            tempUnitPer.push(this.unitArrayFull[l] / ttlUnit)
            tempTtlUnit.push(this.unitArrayFull[l])
          } else {
            tempUnitPer.push((tempTtlUnit[l - 1] + this.unitArrayFull[l]) / ttlUnit)
            tempTtlUnit.push(this.unitArrayFull[l] + tempTtlUnit[l - 1])
          }
        }
        for (var x = 0; x < this.visitArrayFull.length; x++) {
          if (x === 0) {
            tempVisitPer.push(this.visitArrayFull[x] / ttlVisit)
            tempTtlVisit.push(this.visitArrayFull[x])
          } else {
            tempVisitPer.push((tempTtlVisit[x - 1] + this.visitArrayFull[x]) / ttlVisit)
            tempTtlVisit.push(this.visitArrayFull[x] + tempTtlVisit[x - 1])
          }
        }

        this.spendPerArray = tempSpendPer
        this.unitPerArray = tempUnitPer
        this.visitPerArray = tempVisitPer
        this.spendPerArrayCurr = tempSpendPer
        this.unitPerArrayCurr = tempUnitPer
        this.visitPerArrayCurr = tempVisitPer

        this.paretoData = {
          labels: this.quantileArray,
          datasets: [
            {
              data: this.spendArrayFull,
              label: 'Total Spend',
              backgroundColor: '#0087AA'
            }, {
              data: this.spendPerArray,
              label: 'Cumlative Percent Spend',
              type: 'line',
              yAxisID: 'right-y-axis',
              backgroundColor: '#F7970E',
              borderColor: '#F7970E',
              fill: false
            }
          ]
        }
      }
    }
  }
</script>

<style scoped>
.pareto_chart1 {
  position:relative !important;
  margin:0 auto !important;
  height:200px !important;
  width:100% !important;
}
.mob_break1 {display:none;}
.mob_break2 {display:none;}
/* Tablets (portrait and landscape) ----------- */
@media only screen and (min-device-width: 481px) and (max-device-width: 960px) {
  /* Styles */
  .mob_break1 {
    display:block;
  }
  .mob_break2 {
    display:none;
  }
  .mob_padLR_0 {
    padding-left:0px !important;
    padding-right:0px !important;
  }
  .mob_padLR_1 {
    padding-left:4px !important;
    padding-right:4px !important;
  }
  .mob_padLR_2 {
    padding-left:8px !important;
    padding-right:8px !important;
  }
  .mob_padTB_0 {
    padding-top:0px !important;
    padding-bottom:0px !important;
  }
  .mob_padTB_1 {
    padding-top:4px !important;
    padding-bottom:4px !important;
  }
  .mob_padTB_2 {
    padding-top:8px !important;
    padding-bottom:8px !important;
  }
  .mob_marB_0 {
    margin-bottom:0 !important;
  }
  .mob_padB_1 {
    padding-bottom:4px !important;
  }
}

/* Smartphones (portrait and landscape) ----------- */
@media only screen and (min-device-width: 320px) and (max-device-width: 480px) {
  /* Styles */
  .mob_break1 {
    display:none;
  }
  .mob_break2 {
    display:none;
  }
  .mob_padLR_0 {
    padding-left:0px !important;
    padding-right:0px !important;
  }
  .mob_padLR_1 {
    padding-left:4px !important;
    padding-right:4px !important;
  }
  .mob_padLR_2 {
    padding-left:8px !important;
    padding-right:8px !important;
  }
  .mob_padTB_0 {
    padding-top:0px !important;
    padding-bottom:0px !important;
  }
  .mob_padTB_1 {
    padding-top:4px !important;
    padding-bottom:4px !important;
  }
  .mob_padTB_2 {
    padding-top:8px !important;
    padding-bottom:8px !important;
  }
  .mob_marB_0 {
    margin-bottom:0 !important;
  }
  .mob_padB_1 {
    padding-bottom:4px !important;
  }
}
</style>
