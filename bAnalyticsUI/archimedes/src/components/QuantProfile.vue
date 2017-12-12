<template>
  <v-container fluid class="quantileSummary pl-3 pr-3 mb-3">
    <!-- =====ROW1===== -->
    <v-layout row wrap class="pt-0 mt-0">
      <v-flex xs12>
        <v-card class="pa-0 ma-0 grey lighten-2">
          <v-card-title primary-title class="primary">
            <h6 class="white--text text-xs-left mb-0">Customer Pareto Analysis</h6>
          </v-card-title>
          <v-layout row wrap>
            <!--Dropdown1-->
            <v-flex xs12 sm3>
              <v-card flat class="pl-2 pr-2 pt-0 pb-0 grey lighten-2">
                <v-layout row wrap>
                  <v-flex xs12>
                    <div class="primary--text text-xs-left pl-0 pr-0 pb-0 pt-2">
                      Select Customer<br/>Quantiles:
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
            <v-flex xs12 sm3>
              <v-card flat class="pl-2 pr-2 grey lighten-2">
                <v-layout row wrap>
                  <v-flex xs12>
                    <div class="primary--text text-xs-left pl-0 pr-0 pb-0 pt-2">
                      Choose A Time<br/>Period:
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
            <v-flex xs12 sm3>
              <v-card flat class="pl-2 pr-2 grey lighten-2">
                <v-layout row wrap>
                  <v-flex xs12>
                    <div class="primary--text text-xs-left pl-0 pr-0 pb-0 pt-2">
                      Select Metrics for<br/>Pareto Analysis:
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
        <v-card class="pl-3 pr-3 pt-1 pb-1">
          <div class="title primary--text text-xs-center pa-1"><em><span class="grey--text darken-2">Visits</span> Analysis for Quantiles <span class="grey--text darken-2">All</span> during Period <span class="grey--text darken-2">1</span>, with reference line at <span class="grey--text darken-2">70.00</span> percent of total</em></div>
        </v-card>
      </v-flex>
    </v-layout>
    <!-- //=====ROW2===== -->
    <!-- =====ROW3===== -->
    <v-layout wrap row>
      <v-flex xs12 class="pt-0 mt-0">
        <v-card class="white pl-3 pr-3 pt-1 pb-1">
          <div class="title primary--text text-xs-center pa-1">Quantile Pareto Analysis</div>
          <pareto-chart :chart-data="paretoData" style="height:23vh;"></pareto-chart>
        </v-card>
      </v-flex>
    </v-layout>
    <!-- //=====ROW3===== -->
    <!-- =====ROW4===== -->
    <v-layout wrap row>
      <v-flex xs12>
        <v-card class="white pl-3 pr-3 pt-1 pb-1">
          <div class="title primary--text text-xs-center pa-1">Quantile Purchase Profiles</div>
          <v-data-table
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
        </v-card>
      </v-flex>
    </v-layout>
    <!-- //=====ROW4===== -->
  </v-container>
</template>

<script>
  import ParetoChart from './balorCharts/ParetoChart'
  import {quantSum} from './javascript/quantile.service'

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
          {
            text: 'Quantiles',
            align: 'left',
            value: 'quantile'
          },
          {text: 'avgSpendMember($)', value: 'avgCustSales'},
          {text: 'avgSpendVisit($)', value: 'avgVisitSpend'},
          {text: 'avgSpendItem($)', value: 'avgItemSales'},
          {text: 'avgUnitsMember', value: 'avgCustUnits'},
          {text: 'avgUnitsVisit', value: 'avgVisitUnits'},
          {text: 'avgVisitMember', value: 'avgCustVisits'}
        ],
        custItems: [],
        jobId: 'QATestRun'
      }
    },
    computed: {
      jsonMsg: function () {
        return this.incomingJson.data
      }
    },
    components: {
      ParetoChart
    },
    mounted () {
      this.getResults()
    },
    methods: {
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
        for (var i = 0; i < this.jsonMsg.quantileResult.length; i++) {
          if (this.jsonMsg.quantileResult[i].timePeriod === this.TPSelect) {
            tempObj.push({
              'quantile': this.jsonMsg.quantileResult[i].quantile,
              'avgCustSales': numeral(this.jsonMsg.quantileResult[i].avgSpend).format('$0.00'),
              'avgVisitSpend': numeral(this.jsonMsg.quantileResult[i].avgVisitSpend).format('$0.00'),
              'avgItemSales': numeral(this.jsonMsg.quantileResult[i].avgItemSales).format('$0.00'),
              'avgCustUnits': this.jsonMsg.quantileResult[i].avgCustUnits,
              'avgVisitUnits': numeral(this.jsonMsg.quantileResult[i].avgVisitUnits).format('0.00'),
              'avgCustVisits': numeral(this.jsonMsg.quantileResult[i].avgVisits).format('0.00')
            })
          }
        }
        this.custItems = tempObj
        console.log(tempObj)
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
              'totalSpend': this.jsonMsg.quantileResult[i].totalSpend,
              'totalVisits': this.jsonMsg.quantileResult[i].totalVisits,
              'totalUnits': this.jsonMsg.quantileResult[i].totalUnits
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
        var tempVisit = []
        var tempUnit = []
        var tempSpend = []
        var tempObj = []

        for (var i = 0; i < this.jsonMsg.quantileResult.length; i++) {
          if (this.jsonMsg.quantileResult[i].timePeriod === this.TPSelect) {
            tempObj.push({
              'quantile': this.jsonMsg.quantileResult[i].quantile,
              'totalSpend': this.jsonMsg.quantileResult[i].totalSpend,
              'totalVisits': this.jsonMsg.quantileResult[i].totalVisits,
              'totalUnits': this.jsonMsg.quantileResult[i].totalUnits
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
</style>
