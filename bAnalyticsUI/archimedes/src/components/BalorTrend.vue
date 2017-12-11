<template>
  <v-container fluid class="balorTrend pl-3 pr-3">
    <!-- =====TABS-ROW===== -->
    <v-layout row wrap class="mt-0 mb-3 pa-0">
      <v-flex xs12>
        <v-card flat height="50px">
          <v-bottom-nav absolute value="true" class="blue-grey lighten-1 elevation-2">
            <v-btn flat class="white--text" router to="/Balor/Trend">
              <span>Balor Trends</span>
              <v-icon>timeline</v-icon>
            </v-btn>
            <v-btn flat class="white--text" router to="/Balor/SegmentTrend">
              <span>Segment Trends</span>
              <v-icon>bar_chart</v-icon>
            </v-btn>
          </v-bottom-nav>
        </v-card>
      </v-flex>
    </v-layout>
    <!-- //=====TABS-ROW===== -->
    <!-- =====ROW1===== -->
    <v-layout row wrap class="pa-0 mb-0">
      <!--+++++col1+++++-->
      <v-flex xs8 lg9>
        <v-card class="white">
          <v-card-title primary-title class="primary">
            <h6 class="white--text text-xs-left mb-0">BALOR Period Trendline</h6>
          </v-card-title>
          <v-layout row wrap>
            <!--slider-->
            <v-flex xs12>
              <v-layout row wrap class="mt-2 mb-3 pl-4 pr-4">
                <v-flex xs12>
                  <div id="ratioSlide" class="noUiSlider" ref="mySlider"></div>
                </v-flex>
              </v-layout>
            </v-flex>
            <!--//slider-->
          </v-layout>
          <!--trendlines-->
          <v-layout row wrap class="mb-3 pa-3 prod_container">
            <v-flex xs12 fill-height>
              <balor-trend-line :chart-data="trendLine" class="line_chart"></balor-trend-line>
            </v-flex>
          </v-layout>
          <!--//trendlines-->
        </v-card>
      </v-flex>
      <!--//+++++col1+++++-->
      <!--+++++col2+++++-->
      <v-flex xs4 lg3>
        <!-- BALOR Summary -->
        <v-layout row wrap>
          <v-flex xs12 class="pb-3">
            <div class="sum_brd1">
            <v-card horizontal class="white card_height">
              <v-card-text class="white error--text card_pad">
                <v-card-title primary-title class="pa-0 pb-3">
                  <h6 class="error--text text-xs-left mb-0 pt-0 pb-0">BALOR Summary</h6>
                </v-card-title>
                <v-divider class="error"></v-divider>
                <table width="100%" class="summary">
                  <tr>
                    <td class="grey lighten-4">Min. Date:</td>
                    <td class="grey lighten-4">{{ this.sumItems.minDateBalor }}</td>
                  </tr>
                  <tr>
                    <td class="grey lighten-4">Max. Date:</td>
                    <td class="grey lighten-4">{{ this.sumItems.maxDateBalor }}</td>
                  </tr>
                  <tr>
                    <td class="grey lighten-4">Customer Base:</td>
                    <td class="grey lighten-4">{{ this.sumItems.totalCusts }}</td>
                  </tr>
                  <tr>
                    <td class="grey lighten-4">% Customer - 1 Purchase:</td>
                    <td class="grey lighten-4">{{ this.sumItems.percentCust }}</td>
                  </tr>
                  <tr>
                    <td class="grey lighten-4">Transactions:</td>
                    <td class="grey lighten-4">{{ this.sumItems.numRecords }}</td>
                  </tr>
                  <tr>
                    <td class="grey lighten-4">Purchase Cadence - 80th Percentile:</td>
                    <td class="grey lighten-4">{{ this.sumItems.normalizedCadence }}</td>
                  </tr>
                  <tr>
                    <td class="grey lighten-4">Time Periods:</td>
                    <td class="grey lighten-4">{{ this.sumItems.timePeriods }}</td>
                  </tr>
                </table>
              </v-card-text>
            </v-card>
            </div>
          </v-flex>
        </v-layout>
        <!--// BALOR Summary -->
        <!-- Retention Summary -->
        <v-layout row wrap class="ret_sum">
          <v-flex xs12 class="pb-3">
            <div class="sum_brd2">
            <v-card horizontal class="white card_height">
              <v-card-text class="white warning--text card_pad">
                <v-card-title primary-title class="pa-0 pb-3">
                  <h6 class="warning--text text-xs-left mb-0 pt-0 pb-0">Retention Summary</h6>
                </v-card-title>
                <v-divider class="warning"></v-divider>
                <table width="100%" class="summary">
                  <tr v-for="item in retentionItems" v-bind:key="item.name">
                    <td class="grey lighten-4">{{ item.name }}:</td>
                    <td class="grey lighten-4">{{ item.vals }}</td>
                  </tr>
                </table>
              </v-card-text>
            </v-card>
            </div>
          </v-flex>
        </v-layout>
        <!--// Retention Summary -->
      </v-flex>
      <!--//+++++col2+++++-->
    </v-layout>
    <!-- //=====ROW1===== -->

    <!-- =====ROW2===== -->
    <v-layout row wrap class="mb-3">
      <v-flex xs12 fill-height>
        <v-card class="white">

          <!-- SELECTION ROW -->
          <v-layout row wrap>
            <v-flex xs12>
              <v-card class="pa-0 ma-0 grey lighten-2">
                <v-card-title primary-title class="primary">
                  <h6 class="white--text text-xs-left mb-0">BALOR Composition and Metrics for Period:</h6>
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
                            <v-select v-model="tp"
                                    v-bind:items="tpArray"
                                    label="Select Time Period"
                                    single-line
                                    v-on:input="setTP()"
                                    style="width:175px;"
                                    class="pl-1 pt-1 mt-1 mb-2 white elevation-1"
                                    hide-details
                                    id="selection">
                            </v-select>
                        </v-flex>
                      </v-layout>
                    </v-card>
                  </v-flex>
                  <!--//Dropdown1-->
                </v-layout>
              </v-card>
            </v-flex>
            <!--selection-->
            <!--//selection-->
          </v-layout>
          <!-- //SELECTION ROW -->
          <!-- LEGEND -->
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
          <!-- //LEGEND -->
          <!--Pie Charts-->
          <v-layout row wrap>
            <v-flex xs12 sm6 md3 lg3>
              <v-card flat class="white pa-3">
                <pie-charts class="white pie_chart1" :chart-data='custData'></pie-charts>
                <div primary-title class="primary--text text-xs-center pa-2">Customers</div>
              </v-card>
            </v-flex>
            <v-flex xs12 sm6 md3 lg3>
              <v-card flat class="white pa-3">
                <pie-charts class="white pie_chart1" :chart-data='txnData'></pie-charts>
                <div primary-title class="primary--text text-xs-center pa-2">Transactions</div>
              </v-card>
            </v-flex>
            <v-flex xs12 sm6 md3 lg3>
              <v-card flat class="white pa-3">
                <pie-charts class="white pie_chart1" :chart-data='spendData'></pie-charts>
                <div primary-title class="primary--text text-xs-center pa-2">Sales</div>
              </v-card>
            </v-flex>
            <v-flex xs12 sm6 md3 lg3>
              <v-card flat class="white pt-4 pl-2 pr-2 pb-3">
                <balor-trend-line class="white ratio_line" :chart-data='ratioLine'></balor-trend-line>
              </v-card>
            </v-flex>
          </v-layout>
          <!--/Pie Charts-->
        </v-card>
      </v-flex>
    </v-layout>
    <!-- //=====ROW2===== -->
    <!-- =====ROW3===== -->
    <v-layout row wrap class="mb-3">
      <v-flex xs12 fill-height class="mb-3">
        <v-card class="white">
          <v-layout row wrap>
            <v-flex xs12>
              <v-card-title primary-title class="white">
                <h6 class="primary--text text-xs-left mb-0">BALOR Trend Raw Data</h6>
              </v-card-title>
              <v-data-table
                v-bind:headers="headers"
                :items="items1"
                hide-actions
                class="elevation-1"
              >
              <template slot="items" scope="props">
                <td>{{ props.item.timePeriod }}</td>
                <td class="text-xs-right">{{ props.item.custBalor }}</td>
                <td class="text-xs-right">{{ props.item.spendBalor }}</td>
                <td class="text-xs-right">{{ props.item.txnBalor }}</td>
                <td class="text-xs-right">{{ props.item.newCustCount }}</td>
                <td class="text-xs-right">{{ props.item.newTxnAmt }}</td>
                <td class="text-xs-right">{{ props.item.newTxnCount }}</td>
                <td class="text-xs-right">{{ props.item.reactCustCount }}</td>
                <td class="text-xs-right">{{ props.item.reactTxnAmt }}</td>
                <td class="text-xs-right">{{ props.item.reactTxnCount }}</td>
                <td class="text-xs-right">{{ props.item.returnCustCount }}</td>
                <td class="text-xs-right">{{ props.item.returnTxnAmt }}</td>
                <td class="text-xs-right">{{ props.item.returnTxnCount }}</td>
                <td class="text-xs-right">{{ props.item.lapsedCustCount }}</td>
                <td class="text-xs-right">{{ props.item.lapsedTxnAmt }}</td>
                <td class="text-xs-right">{{ props.item.lapsedTxnCount }}</td>
              </template>
            </v-data-table>
            </v-flex>
          </v-layout>
        </v-card>
      </v-flex>
    </v-layout>
    <!-- //=====ROW3===== -->

  </v-container>


</template>

<script>
  import BalorTrendLine from './balorCharts/BalorTrendLine.js'
  import PieCharts from './balorCharts/PieCharts.js'
  import noUiSlider from 'noUiSlider'
  import {summary} from './javascript/balor.service'

  var numeral = require('numeral')

  export default {
    name: 'balorTrend',
    components: {
      BalorTrendLine,
      PieCharts
    },
    data () {
      return {
        headers: [
          {
            text: 'Time Period',
            left: true,
            sortable: true,
            value: 'timePeriod'
          },
          { text: 'Customer Balor Ratio', value: 'custBalor' },
          { text: 'Spend Balor Ratio', value: 'spendBalor' },
          { text: 'Txn Balor Ratio', value: 'txnBalor' },
          { text: 'New Cust Count', value: 'newCustCount' },
          { text: 'New Total Sales', value: 'newTxnAmt' },
          { text: 'New Txn Count', value: 'newTxnCount' },
          { text: 'Reactivated Cust Count', value: 'reactCustCount' },
          { text: 'Reactivated Total Sales', value: 'reactTxnAmt' },
          { text: 'Reactivated Txn Count', value: 'reactTxnCount' },
          { text: 'Returning Cust Count', value: 'returnCustCount' },
          { text: 'Returning Total Sales', value: 'returnTxnAmt' },
          { text: 'Returning Txn Count', value: 'returnTxnCount' },
          { text: 'Lapsed Cust Count', value: 'lapsedCustCount' },
          { text: 'Lapsed Total Sales', value: 'lapsedTxnAmt' },
          { text: 'Lapsed Txn Count', value: 'lapsedTxnCount' }
        ],
        retentionItems: [],
        sumItems: {},
        msg: 'Balor Trend Charts will go here!',
        custData: null,
        txnData: null,
        spendData: null,
        ratioLine: null,
        incomingJson: {},
        custBalorArray: [],
        txnBalorArray: [],
        spendBalorArray: [],
        tpArray: [],
        jobId: 'mgiBrandt2',
        custTrendData: {
          labels: this.tpArray,
          datasets: [
            {
              label: 'Customer',
              data: this.custBalorArray,
              fill: false,
              lineTension: 0,
              backgroundColor: '#8EAC1D',
              borderColor: '#8EAC1D'
            }]
        },
        txnTrendData: {
          labels: this.tpArray,
          datasets: [{
            label: 'Transaction',
            fill: false,
            data: this.txnBalorArray,
            lineTension: 0,
            backgroundColor: '#F7970E',
            borderColor: '#F7970E'
          }]
        },
        spendTrendData: {
          labels: this.tpArray,
          datasets: [
            {
              label: 'Spend',
              fill: false,
              data: this.spendBalorArray,
              lineTension: 0,
              backgroundColor: '#0087AA',
              borderColor: '#0087AA'
            }]
        },
        allTrendData: {},
        slider: {
          start: [0, 10], // this.tpArray.length],
          connect: true,
          step: 1,
          min: 0,
          max: 10 // this.tpArray.length}
        },
        Slider: null,
        mySlider: {},
        tp: 1,
        trendLine: this.allTrendData
      }
    },
    computed: {
      jsonMsg: function () {
        return this.incomingJson.data
      },
      items1: function () {
        var formatArray = []
        for (let i = 0; i < this.incomingJson.data.timePeriods.length; i++) {
          formatArray.push({
            'timePeriod': numeral(this.incomingJson.data.timePeriods[i].timePeriod).format(),
            'custBalor': numeral(this.incomingJson.data.timePeriods[i].custBalor).format('0.00'),
            'spendBalor': numeral(this.incomingJson.data.timePeriods[i].spendBalor).format('0.00'),
            'txnBalor': numeral(this.incomingJson.data.timePeriods[i].txnBalor).format('0.00'),
            'newCustCount': numeral(this.incomingJson.data.timePeriods[i].newCustCount).format(),
            'newTxnAmt': numeral(this.incomingJson.data.timePeriods[i].newTxnAmt).format('$0,0.00'),
            'newTxnCount': numeral(this.incomingJson.data.timePeriods[i].newTxnCount).format(),
            'reactCustCount': numeral(this.incomingJson.data.timePeriods[i].reactCustCount).format(),
            'reactTxnAmt': numeral(this.incomingJson.data.timePeriods[i].reactTxnAmt).format('$0,0.00'),
            'reactTxnCount': numeral(this.incomingJson.data.timePeriods[i].reactTxnCount).format(),
            'returnCustCount': numeral(this.incomingJson.data.timePeriods[i].returnCustCount).format(),
            'returnTxnAmt': numeral(this.incomingJson.data.timePeriods[i].returnTxnAmt).format('$0,0.00'),
            'returnTxnCount': numeral(this.incomingJson.data.timePeriods[i].returnTxnCount).format(),
            'lapsedCustCount': numeral(this.incomingJson.data.timePeriods[i].lapsedCustCount).format(),
            'lapsedTxnAmt': numeral(this.incomingJson.data.timePeriods[i].lapsedTxnAmt).format('$0,0.00'),
            'lapsedTxnCount': numeral(this.incomingJson.data.timePeriods[i].lapsedTxnCount).format()
          })
        }
        return formatArray
      }
    },
    updated () {
      if (document.getElementsByClassName('noUi-target').length === 0) {
        this.createSlider()
      }
    },
    mounted () {
      this.getResults()
    },
    methods: {
      getResults () {
        summary(this.jobId)
          .catch(err => {
            alert('Could not get Trend results. ' + err.message.toString())
          })
          .then((response) => {
            this.incomingJson = response.data
            console.log(this.incomingJson)
            this.createPies()
            this.createLines()
            this.createBalSumItems()
          })
      },
      createBalSumItems () {
        this.sumItems = {
          'minDateBalor': this.jsonMsg.minDateBalor,
          'maxDateBalor': this.jsonMsg.maxDateBalor,
          'totalCusts': numeral(this.jsonMsg.totalCusts).format('0,0'),
          'singleVisit': this.jsonMsg.singleVisit,
          'percentCust': numeral(this.jsonMsg.singleVisit / this.jsonMsg.totalCusts).format('0.00%'),
          'numRecords': numeral(this.jsonMsg.numRecords).format('0,0'),
          'normalizedCadence': this.jsonMsg.normalizedCadence,
          'timePeriods': this.jsonMsg.timePeriods.length
        }
      },
      createLines () {
        console.log('this.items1')
        console.log(this.items1)

        var tempCust = []
        var tempTxn = []
        var tempSpend = []
        var tempTP = []

        for (var i = 0; i < this.jsonMsg.timePeriods.length; i++) {
          tempCust.push(this.jsonMsg.timePeriods[i].custBalor)
          tempTxn.push(this.jsonMsg.timePeriods[i].txnBalor)
          tempSpend.push(this.jsonMsg.timePeriods[i].spendBalor)
          tempTP.push(this.jsonMsg.timePeriods[i].timePeriod)
        }

        this.custBalorArray = tempCust
        this.txnBalorArray = tempTxn
        this.spendBalorArray = tempSpend
        this.tpArray = tempTP

        this.ratioLine = {
          labels: ['Customer', 'Transaction', 'Sales'],
          datasets: [
            {
              label: 'Balor Ratios',
              data: [
                this.custBalorArray[this.tp],
                this.txnBalorArray[this.tp],
                this.spendBalorArray[this.tp]],
              fill: false,
              lineTension: 0,
              backgroundColor: '#005E76',
              borderColor: '#005E76'
            }
          ]
        }

        this.trendLine = {
          labels: this.tpArray,
          datasets: [
            {
              label: 'Customer',
              data: this.custBalorArray,
              fill: false,
              lineTension: 0,
              backgroundColor: '#8EAC1D',
              borderColor: '#8EAC1D'
            }, {
              label: 'Transaction',
              fill: false,
              data: this.txnBalorArray,
              lineTension: 0,
              backgroundColor: '#F7970E',
              borderColor: '#F7970E'
            }, {
              label: 'Spend',
              fill: false,
              data: this.spendBalorArray,
              lineTension: 0,
              backgroundColor: '#0087AA',
              borderColor: '#0087AA'
            }]
        }
      },
      slideUpdateTrends () {
        var vals = this.Slider.noUiSlider.get()
        var min = parseInt(vals[0]) - 1
        var max = parseInt(vals[1])
        var allChbx = document.getElementById('allTrends')
        var custChbx = document.getElementById('custTrend')
        var txnChbx = document.getElementById('txnTrend')
        var spendChbx = document.getElementById('spendTrend')

        var newData = {}
        newData.labels = this.tpArray
        newData.datasets = []

        if (allChbx.checked) {
          this.trendLine = {
            labels: this.tpArray.slice(min, max),
            datasets: [
              {
                label: 'Customer',
                data: this.custBalorArray.slice(min, max),
                fill: false,
                lineTension: 0,
                backgroundColor: '#8EAC1D',
                borderColor: '#8EAC1D'
              }, {
                label: 'Transaction',
                fill: false,
                data: this.txnBalorArray.slice(min, max),
                lineTension: 0,
                backgroundColor: '#F7970E',
                borderColor: '#F7970E'
              }, {
                label: 'Spend',
                fill: false,
                data: this.spendBalorArray.slice(min, max),
                lineTension: 0,
                backgroundColor: '#0087AA',
                borderColor: '#0087AA'
              }]
          }
        } else if (custChbx.checked && txnChbx.checked) {
          this.trendLine = {
            labels: this.tpArray.slice(min, max),
            datasets: [
              {
                label: 'Customer',
                data: this.custBalorArray.slice(min, max),
                fill: false,
                lineTension: 0,
                backgroundColor: '#8EAC1D',
                borderColor: '#8EAC1D'
              }, {
                label: 'Transaction',
                fill: false,
                data: this.txnBalorArray.slice(min, max),
                lineTension: 0,
                backgroundColor: '#F7970E',
                borderColor: '#F7970E'
              }]
          }
        } else if (custChbx.checked && spendChbx.checked) {
          this.trendLine = {
            labels: this.tpArray.slice(min, max),
            datasets: [
              {
                label: 'Customer',
                data: this.custBalorArray.slice(min, max),
                fill: false,
                lineTension: 0,
                backgroundColor: '#8EAC1D',
                borderColor: '#8EAC1D'
              }, {
                label: 'Spend',
                fill: false,
                data: this.spendBalorArray.slice(min, max),
                lineTension: 0,
                backgroundColor: '#0087AA',
                borderColor: '#0087AA'
              }]
          }
        } else if (txnChbx.checked && spendChbx.checked) {
          this.trendLine = {
            labels: this.tpArray.slice(min, max),
            datasets: [
              {
                label: 'Transaction',
                fill: false,
                data: this.txnBalorArray.slice(min, max),
                lineTension: 0,
                backgroundColor: '#F7970E',
                borderColor: '#F7970E'
              }, {
                label: 'Spend',
                fill: false,
                data: this.spendBalorArray.slice(min, max),
                lineTension: 0,
                backgroundColor: '#0087AA',
                borderColor: '#0087AA'
              }]
          }
        } else if (custChbx.checked) {
          this.trendLine = {
            labels: this.tpArray.slice(min, max),
            datasets: [
              {
                label: 'Customer',
                data: this.custBalorArray.slice(min, max),
                fill: false,
                lineTension: 0,
                backgroundColor: '#8EAC1D',
                borderColor: '#8EAC1D'
              }]
          }
        } else if (txnChbx.checked) {
          this.trendLine = {
            labels: this.tpArray.slice(min, max),
            datasets: [
              {
                label: 'Transaction',
                fill: false,
                data: this.txnBalorArray.slice(min, max),
                lineTension: 0,
                backgroundColor: '#F7970E',
                borderColor: '#F7970E'
              }]
          }
        } else if (spendChbx.checked) {
          this.trendLine = {
            labels: this.tpArray.slice(min, max),
            datasets: [
              {
                label: 'Spend',
                fill: false,
                data: this.spendBalorArray.slice(min, max),
                lineTension: 0,
                backgroundColor: '#0087AA',
                borderColor: '#0087AA'
              }]
          }
        }
      },
      createPies () {
        var tp = 0
        this.retentionItems = [{name: 'Retention', vals: numeral(this.jsonMsg.timePeriods[tp].returnTxnCount).format()}]

        this.custData = {
          datasets: [{
            data: [
              this.jsonMsg.timePeriods[tp].lapsedCustCount,
              this.jsonMsg.timePeriods[tp].newCustCount,
              this.jsonMsg.timePeriods[tp].returnCustCount,
              this.jsonMsg.timePeriods[tp].reactCustCount],
            backgroundColor: [
              '#D63A09',
              '#8EAC1D',
              '#F7970E',
              '#0087AA'
            ],
            label: 'Customers'
          }],
          labels: [
            'Lapsed',
            'New',
            'Returning',
            'Reactivated'
          ]
        }

        this.txnData = {
          datasets: [{
            data: [
              this.jsonMsg.timePeriods[tp].newTxnCount,
              this.jsonMsg.timePeriods[tp].returnTxnCount,
              this.jsonMsg.timePeriods[tp].reactTxnCount],
            backgroundColor: [
              '#8EAC1D',
              '#F7970E',
              '#0087AA'
            ],
            label: 'Transactions'
          }],
          labels: [
            'New',
            'Returning',
            'Reactivated'
          ]
        }

        this.spendData = {
          datasets: [{
            data: [
              this.jsonMsg.timePeriods[tp].newTxnAmt,
              this.jsonMsg.timePeriods[tp].returnTxnAmt,
              this.jsonMsg.timePeriods[tp].reactTxnAmt],
            backgroundColor: [
              '#8EAC1D',
              '#F7970E',
              '#0087AA'
            ],
            label: 'Spend'
          }],
          labels: [
            'New',
            'Returning',
            'Reactivated'
          ]
        }
      },
      createSlider () {
        this.Slider = document.getElementById('ratioSlide')
        this.Slider.style.height = '12px'
        this.Slider.style.margin = '5px'
        noUiSlider.create(this.Slider, {
          start: [1, this.tpArray.length],
          margin: 1, // Handles must be at least 1 apart
          tooltips: false,
          connect: true,
          step: 1,
          range: {'min': 1, 'max': this.tpArray.length},
          pips: { // Show a scale with the slider
            mode: 'steps',
            stepped: true,
            density: 10
          }
        })
        this.Slider.noUiSlider.on('change', this.slideUpdateTrends)
      },
      setTP () {
        this.custData = {
          datasets: [{
            data: [
              this.jsonMsg.timePeriods[this.tp - 1].lapsedCustCount,
              this.jsonMsg.timePeriods[this.tp - 1].newCustCount,
              this.jsonMsg.timePeriods[this.tp - 1].returnCustCount,
              this.jsonMsg.timePeriods[this.tp - 1].reactCustCount],
            backgroundColor: [
              '#D63A09',
              '#8EAC1D',
              '#F7970E',
              '#0087AA'
            ],
            label: 'Customers'
          }],
          labels: [
            'Lapsed',
            'New',
            'Returning',
            'Reactivated'
          ]
        }

        this.txnData = {
          datasets: [{
            data: [
              this.jsonMsg.timePeriods[this.tp - 1].newTxnCount,
              this.jsonMsg.timePeriods[this.tp - 1].returnTxnCount,
              this.jsonMsg.timePeriods[this.tp - 1].reactTxnCount],
            backgroundColor: [
              '#8EAC1D',
              '#F7970E',
              '#0087AA'
            ],
            label: 'Transactions'
          }],
          labels: [
            'New',
            'Returning',
            'Reactivated'
          ]
        }

        this.spendData = {
          datasets: [{
            data: [
              this.jsonMsg.timePeriods[this.tp - 1].newTxnAmt,
              this.jsonMsg.timePeriods[this.tp - 1].returnTxnAmt,
              this.jsonMsg.timePeriods[this.tp - 1].reactTxnAmt],
            backgroundColor: [
              '#8EAC1D',
              '#F7970E',
              '#0087AA'
            ],
            label: 'Spend'
          }],
          labels: [
            'New',
            'Returning',
            'Reactivated'
          ]
        }

        this.ratioLine = {
          labels: ['Customer', 'Transaction', 'Sales'],
          datasets: [
            {
              label: 'Balor Ratios',
              data: [
                this.custBalorArray[this.tp - 1],
                this.txnBalorArray[this.tp - 1],
                this.spendBalorArray[this.tp - 1]],
              fill: false,
              lineTension: 0,
              backgroundColor: '#005E76',
              borderColor: '#005E76'
            }
          ]
        }

        this.retentionItems = [{name: 'Retention', vals: this.jsonMsg.timePeriods[this.tp].returnTxnCount}]
      }
    }
  }

</script>

<!-- Add 'scoped' attribute to limit CSS to this component only -->
<style scoped>
  .inliner { display: inline-block; }

  .sum_brd1 { border-right: 7px solid #D63809; }

  .sum_brd2 { border-right: 7px solid #f7970e; }

  .summary td:last-child {
    margin-left:5px;
    text-align:right;
  }

  .line_chart {
    position: relative;
    margin: 0 auto;
    height: 385px !important;
  }

  .pie_chart1 {
    position: relative;
    margin: 0 auto;
    height: 20vw !important;
  }

  .ratio_line {
    position: relative;
    margin: 0 auto;
    height: 20vw !important;
  }

  .left_float { float: left; }

  .right_float { float: right; }

  .legend {
    display: inline-block;
    width: 38px;
    height: 12px;
    margin-right: 5px;
  }

  .legend_color1 { background-color: #D63809; }
  .legend_color2 { background-color: #8EAC1D; }
  .legend_color3 { background-color: #F7970E; }
  .legend_color4 { background-color: #0087AA; }
  .padR5{padding-right:5px;}

  .tabs__item{padding:0 !important;}
  .tabs__container a{padding:0;}

  /* Smartphones (portrait and landscape) ----------- */
  @media only screen and (min-device-width : 320px) and (max-device-width : 480px) {
  /* Styles */
    .pie_chart1 {
      height: 30vh !important;
    }
    .padT15_mob{ padding-top:15px;}
  }

</style>
