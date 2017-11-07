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
            <!--checkboxes-->
            <v-flex xs12>
              <v-card flat>
                <v-layout row wrap class="checkboxes pl-3 pr-3">
                  <v-flex xs12 sm6 md2 grey--text subheading>
                    <input class="mr-1" type='checkbox' id='allTrends'
                           checked='true' @change="allUpdate()">All
                  </v-flex>
                  <v-flex xs12 sm6 md3 grey--text subheading>
                    <input class="mr-1" type='checkbox' id='custTrend'
                           @change="singleUpdate()">Customer
                  </v-flex>
                  <v-flex xs12 sm6 md3 grey--text subheading>
                    <input class="mr-1" type='checkbox' id='txnTrend'
                           @change="singleUpdate()">Transaction
                  </v-flex>
                  <v-flex xs12 sm6 md3 grey--text subheading>
                    <input class="mr-1" type='checkbox' id='spendTrend'
                           @change="singleUpdate()">Spend
                  </v-flex>
                </v-layout>
              </v-card>
            </v-flex>
            <!--//checkboxes-->

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
          <v-layout row wrap class="mb-3 pa-3">
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
        <v-layout row wrap class="cad_sum">
          <v-flex xs12 class="pb-3">
            <div style="border-right: 7px solid red;">
            <v-card horizontal class="white card_height">
              <v-card-text class="white red--text card_pad">
                <v-card-title primary-title class="pt-0 pb-3">
                  <h6 class="red--text text-xs-left mb-0 pt-0 pb-0">BALOR Summary</h6>
                </v-card-title>
                <v-divider class="red"></v-divider>
                <table width="100%">
                <tr>
                  <td class="grey lighten-4" style="border-bottom:1px solid grey !important;">Min. Date:</td>
                  <td class="grey lighten-4" style="border-bottom:1px solid grey !important; margin-left:5px; text-align:right;">{{ jsonMsg.minDateBalor }}</td>
                </tr>
                <tr>
                  <td class="grey lighten-4" style="border-bottom:1px solid grey !important;">Max. Date:</td>
                  <td class="grey lighten-4" style="border-bottom:1px solid grey !important; margin-left:5px; text-align:right;">{{ jsonMsg.maxDateBalor }}</td>
                </tr>
                <tr>
                  <td class="grey lighten-4" style="border-bottom:1px solid grey !important;">Customer Base:</td>
                  <td class="grey lighten-4" style="border-bottom:1px solid grey !important; margin-left:5px; text-align:right;">{{ jsonMsg.totalCusts }}</td>
                </tr>
                <tr>
                  <td class="grey lighten-4" style="border-bottom:1px solid grey !important;">% Customer - 1 Purchase:</td>
                  <td class="grey lighten-4" style="border-bottom:1px solid grey !important; margin-left:5px; text-align:right;">{{ (jsonMsg.singleVisit / jsonMsg.totalCusts * 100).toFixed(2) + '%' }}</td>
                </tr>
                <tr>
                  <td class="grey lighten-4" style="border-bottom:1px solid grey !important;">Transactions:</td>
                  <td class="grey lighten-4" style="border-bottom:1px solid grey !important; margin-left:5px; text-align:right;">{{ jsonMsg.numRecords }}</td>
                </tr>
                <tr>
                  <td class="grey lighten-4" style="border-bottom:1px solid grey !important;">Purchase Cadence - 80th Percentile:</td>
                  <td class="grey lighten-4" style="border-bottom:1px solid grey !important; margin-left:5px; text-align:right;">{{ jsonMsg.normalizedCadence }}</td>
                </tr>
                <tr>
                  <td class="grey lighten-4" style="border-bottom:1px solid grey !important;">Time Periods:</td>
                  <td class="grey lighten-4" style="border-bottom:1px solid grey !important; margin-left:5px; text-align:right;">{{ jsonMsg.timePeriods.length }}</td>
                </tr>
                </table>
              </v-card-text>
            </v-card>
            </div>
          </v-flex>
        </v-layout>
      </v-flex>
      <!--//+++++col2+++++-->
    </v-layout>
    <!-- //=====ROW1===== -->

    <!-- =====ROW2===== -->
    <v-layout row wrap class="mb-3">
      <v-flex xs12 fill-height>
        <v-card class="white">
          <v-layout row wrap>
            <v-flex xs12>
              <v-card-title primary-title class="primary">
                <h6 class="white--text text-xs-left mb-0">BALOR Composition and Metrics for Period:</h6>
              </v-card-title>
            </v-flex>
          </v-layout>

          <!-- SELECTION ROW -->
          <v-layout row wrap>
            <!--selection-->
            <v-flex xs12>
              <v-card flat class="pa-3">
                <v-layout row wrap>
                  <v-flex xs12 sm3 md3>
                      <v-card id="selection" class="white inliner">
                        <v-select v-model="tp"
                                  v-bind:items="tpArray"
                                  label="Select Time Period"
                                  v-on:input="setTP()"
                                  style="width:175px;margin-bottom:0;"
                                  class="pl-1 pt-1">
                        </v-select>
                      </v-card>
                  </v-flex>
                  <v-flex xs12 sm3 offset-sm6 md3 offset-md6 class="text-sm-right padT15_mob">
                      <v-card class="white primary--text inliner pt-3 pb-3 pl-2 pr-2" v-for="item in retentionItems"
                           v-bind:key="item.name">
                        {{ item.name }}: {{ item.vals }}
                      </v-card>
                  </v-flex>
                </v-layout>
              </v-card>
            </v-flex>
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
                <td class="text-xs-right">{{ props.item.custBalor.toFixed(2) }}</td>
                <td class="text-xs-right">{{ props.item.spendBalor.toFixed(2) }}</td>
                <td class="text-xs-right">{{ props.item.txnBalor.toFixed(2) }}</td>
                <td class="text-xs-right">{{ props.item.newCustCount }}</td>
                <td class="text-xs-right">${{ props.item.newTxnAmt.toFixed(2) }}</td>
                <td class="text-xs-right">{{ props.item.newTxnCount }}</td>
                <td class="text-xs-right">{{ props.item.reactCustCount }}</td>
                <td class="text-xs-right">${{ props.item.reactTxnAmt.toFixed(2) }}</td>
                <td class="text-xs-right">{{ props.item.reactTxnCount }}</td>
                <td class="text-xs-right">{{ props.item.returnCustCount }}</td>
                <td class="text-xs-right">${{ props.item.returnTxnAmt.toFixed(2) }}</td>
                <td class="text-xs-right">{{ props.item.returnTxnCount }}</td>
                <td class="text-xs-right">{{ props.item.lapsedCustCount }}</td>
                <td class="text-xs-right">${{ props.item.lapsedTxnAmt.toFixed(2) }}</td>
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
        sumItems: [],
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
        jobId: 'macysDemo',
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
        tp: 1,
        trendLine: this.allTrendData
      }
    },
    computed: {
      jsonMsg: function () {
        return this.incomingJson.data
      },
      items1: function () {
        return this.incomingJson.data.timePeriods
      }
    },
    mounted () {
      this.getResults()

      // this.createTrend()
    },
    methods: {
      getResults () {
        summary(this.jobId)
          .catch(err => {
            alert('Could not get Trend results. ' + err.message.toString())
          })
          .then((response) => {
            this.incomingJson = response.data
            this.items1 = response.data.data.timePeriods
            console.log(this.incomingJson)
            this.createPies()
            this.createLines()
            this.createSlider()
          })
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
      singleUpdate () {
        if (document.getElementById('custTrend').checked && document.getElementById('txnTrend').checked && document.getElementById('spendTrend').checked) {
          document.getElementById('custTrend').checked = false
          document.getElementById('txnTrend').checked = false
          document.getElementById('spendTrend').checked = false
          document.getElementById('allTrends').checked = true
          this.slideUpdateTrends()
        } else {
          document.getElementById('allTrends').checked = false
          this.slideUpdateTrends()
        }
      },
      allUpdate () {
        document.getElementById('custTrend').checked = false
        document.getElementById('txnTrend').checked = false
        document.getElementById('spendTrend').checked = false
        this.slideUpdateTrends()
      },
      createPies () {
        var tp = 0
        this.retentionItems = [{name: 'Retention', vals: this.jsonMsg.timePeriods[tp].returnTxnCount}]

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
