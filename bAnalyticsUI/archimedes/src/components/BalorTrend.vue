<template>
  <v-container fluid class="balorTrend pl-3 pr-3">
    <!-- =====ROW1===== -->
    <SideNav ref="helpNav"></SideNav>
    <v-layout row wrap class="pa-0 mb-0">
      <!-- BALOR Trendline -->
      <v-flex xs12 sm8 md9 lg10 class="mb-3">
        <v-card class="white">
          <v-card-title primary-title class="primary">
            <h6 class="white--text text-xs-left mb-0">BALOR Period Trendline</h6>
            <v-spacer></v-spacer>
            <v-btn
                @click.native="getHelpSection()"
                icon slot="activator"
                class="success--text" style="height:auto !important;">
                <v-icon class="pa-0 ma-0 white--text">help_outline</v-icon>
            </v-btn>
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
          <v-layout row wrap class="mb-3 pa-3">
            <v-flex xs12 fill-height>
              <balor-trend-line :chart-data="trendLine" class="line_chart"></balor-trend-line>
            </v-flex>
          </v-layout>
          <!--//trendlines-->
        </v-card>
      </v-flex>
      <!--// BALOR Trendline -->
      <!-- BALOR Summary -->
      <v-flex xs12 sm4 md3 lg2 class="mb-3">
        <v-layout row wrap>
          <v-flex xs12>
            <div class="white elevation-1">
            <v-card class="white elevation-0">
              <v-card-title primary-title class="primary">
                <h6 class="text-xs-left mb-0 white--text ">BALOR Summary</h6>
                <v-spacer></v-spacer>
                <v-dialog v-model="dialog2" style="height:auto !important;line-height:24px;">
                  <v-btn
                      v-on:click.native=""
                      icon slot="activator"
                      class="success--text" style="height:auto !important;">
                      <v-icon class="pa-0 ma-0 white--text">help_outline</v-icon>
                  </v-btn>
                  <v-card>
                    <v-card-row class="pa-2 info">
                      <div class="body-2 mb-0 white--text">Help - BALOR Summary</div>
                    </v-card-row>
                    <v-card-row class="pa-2">
                      <div class="body-1 accent--text">Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla a risus ut nulla tincidunt sodales.</div>
                    </v-card-row>
                  </v-card>
                </v-dialog>
              </v-card-title>
              <div class="pl-2 pr-2 pb-2 pt-1 elevation-0 panel">
                  <!-- data_row -->
                  <div class="panel_body mt-1 pl-1 pr-1 pt-2 pb-2">
                    <p class="body-1 pa-0 mb-0 text-xs-center">Min. Date</p>
                    <h6 class="mb-0 text-xs-center">{{ this.sumItems.minDateBalor }}</h6>
                  </div>
                  <!-- data_row -->
                  <div class="panel_body mt-1 pl-1 pr-1 pt-2 pb-2">
                    <p class="body-1 pa-0 mb-0 text-xs-center">Max. Date</p>
                    <h6 class="mb-0 text-xs-center">{{ this.sumItems.maxDateBalor }}</h6>
                  </div>
                  <!-- data_row -->
                  <div class="panel_body mt-1 pl-1 pr-1 pt-2 pb-2">
                    <p class="body-1 pa-0 mb-0 text-xs-center">Customer Base:</p>
                    <h6 class="mb-0 text-xs-center">{{ this.sumItems.totalCusts }}</h6>
                  </div>
                  <!-- data_row -->
                  <div class="panel_body mt-1 pl-1 pr-1 pt-2 pb-2">
                    <p class="body-1 pa-0 mb-0 text-xs-center">% Customer - 1 Purchase:</p>
                    <h6 class="mb-0 text-xs-center">{{ this.sumItems.percentCust }}</h6>
                  </div>
                  <!-- data_row -->
                  <div class="panel_body mt-1 pl-1 pr-1 pt-2 pb-2">
                    <p class="body-1 pa-0 mb-0 text-xs-center">Transactions:</p>
                    <h6 class="mb-0 text-xs-center">{{ this.sumItems.numRecords }}</h6>
                  </div>
                  <!-- data_row -->
                  <div class="panel_body mt-1 pl-1 pr-1 pt-2 pb-2">
                    <p class="body-1 pa-0 mb-0 text-xs-center">Purchase Cadence - <br />80th Percentile:</p>
                    <h6 class="mb-0 text-xs-center">{{ this.sumItems.normalizedCadence }}</h6>
                  </div>
                  <!-- data_row -->
                  <div class="panel_body mt-1 pl-1 pr-1 pt-2 pb-2">
                    <p class="body-1 pa-0 mb-0 text-xs-center">Time Periods:</p>
                    <h6 class="mb-0 text-xs-center">{{ this.sumItems.timePeriods }}</h6>
                  </div>
              </div>
            </v-card>
            </div>
          </v-flex>
        </v-layout>
      </v-flex>
      <!--// BALOR Summary -->
    </v-layout>
    <!-- //=====ROW1===== -->

    <!-- =====ROW2===== -->
    <v-layout row wrap>
      <!-- PieCharts & Ratio Lines -->
      <v-flex xs12 sm8 md9 lg10 class="mb-3">
        <v-card class="white">

          <!-- SELECTION ROW -->
          <v-layout row wrap>
            <v-flex xs12>
              <v-card class="pa-0 ma-0 grey lighten-2">
                <v-card-title primary-title class="primary">
                  <h6 class="white--text text-xs-left mb-0">BALOR Composition and Metrics for Period:</h6>
                  <v-spacer></v-spacer>
                  <v-btn
                      v-on:click.native="getHelpSection()"
                      icon slot="activator"
                      class="success--text" style="height:auto !important;">
                      <v-icon class="pa-0 ma-0 white--text">help_outline</v-icon>
                  </v-btn>
                </v-card-title>
                <v-layout row wrap>
                  <!--Dropdown1-->
                  <v-flex xs12>
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
          <v-layout row>
            <v-flex xs12 class="pie_ratio_height">

              <!-- LEGEND -->
              <v-layout row wrap>
                <v-flex xs12>
                  <v-card flat class="white pa-3">
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
              <!--PIE CHARTS ROW-->
              <v-layout row wrap>
              <!--Pie Charts-->
                <v-flex xs12 sm6 md3 lg3>
                  <v-card flat class="white pa-3">
                    <pie-charts class="white pie_chart1" :chart-data='custData' :width="250" :height="250" ></pie-charts>
                    <div primary-title class="primary--text text-xs-center pa-2">Customers</div>
                  </v-card>
                </v-flex>
                <v-flex xs12 sm6 md3 lg3>
                  <v-card flat class="white pa-3">
                    <pie-charts class="white pie_chart1" :chart-data='txnData' :width="250" :height="250"></pie-charts>
                    <div primary-title class="primary--text text-xs-center pa-2">Transactions</div>
                  </v-card>
                </v-flex>
                <v-flex xs12 sm6 md3 lg3>
                  <v-card flat class="white pa-3">
                    <pie-charts class="white pie_chart1" :chart-data='spendData' :width="250" :height="250"></pie-charts>
                    <div primary-title class="primary--text text-xs-center pa-2">Sales</div>
                  </v-card>
                </v-flex>
                <!--//Pie Charts-->
                <!--Ratio Lines-->
                <v-flex xs12 sm6 md3 lg3>
                  <v-card flat class="white pt-4 pl-2 pr-4 pb-3">
                    <balor-trend-line class="white ratio_line" :chart-data='ratioLine' :width="250" :height="250"></balor-trend-line>
                  </v-card>
                </v-flex>
                <!--//Ratio Lines-->
              </v-layout>
              <!--//PIE CHARTS ROW-->
            </v-flex>
          </v-layout>
        </v-card>
      </v-flex>
      <!-- //PieCharts & Ratio Lines -->
      <!-- Retention Summary -->
      <v-flex xs12 sm4 md3 lg2 class="mb-3">
        <v-layout row wrap>
          <v-flex xs12>
            <v-card class="white ret_sum_height">
              <v-card-title primary-title class="primary white--text">
                <h6 class="text-xs-left mb-0 white--text truncate1">Rentention Summary</h6>
                <v-spacer></v-spacer>
                <v-icon class="pa-0 ma-0 white--text">help_outline</v-icon>
              </v-card-title>
              <div class="pl-2 pr-2 pb-2 pt-1 elevation-0 panel">
                  <!-- data_row -->
                  <div class="panel_body mt-1 pl-3 pt-2 pb-2" v-for="item in retentionItems" v-bind:key="item.name">
                    <p class="body-1 pa-0 mb-0 text-xs-center">{{ item.name }}:</p>
                    <h6 class="mb-0 text-xs-center">{{ item.vals }}</h6>
                  </div>
              </div>
            </v-card>
          </v-flex>
        </v-layout>
      </v-flex>
      <!--// Retention Summary -->
    </v-layout>
    <!-- //=====ROW2===== -->
    <!-- =====ROW3===== -->
    <v-layout row wrap class="mb-5">
      <v-flex xs12 fill-height class="mb-3">
        <v-card class="white">
          <v-layout row wrap>
            <v-flex xs12>
              <v-card-title primary-title class="info">
                <h6 class="white--text text-xs-left mb-0">BALOR Trend Raw Data</h6>
                <v-spacer></v-spacer>
                <v-icon class="pa-0 ma-0 white--text">help_outline</v-icon>
              </v-card-title>
              <v-data-table
                  v-bind:headers="headers"
                  :items="items1"
                  hide-actions
                  class="elevation-1 grey lighten-2"
              >
                <template slot="items" scope="props">
                  <td class="white">{{ props.item.timePeriod }}</td>
                  <td class="white text-xs-right">{{ props.item.custBalor }}</td>
                  <td class="white text-xs-right">{{ props.item.spendBalor }}</td>
                  <td class="white text-xs-right">{{ props.item.txnBalor }}</td>
                  <td class="white text-xs-right">{{ props.item.newCustCount }}</td>
                  <td class="white text-xs-right">{{ props.item.newTxnAmt }}</td>
                  <td class="white text-xs-right">{{ props.item.newTxnCount }}</td>
                  <td class="white text-xs-right">{{ props.item.reactCustCount }}</td>
                  <td class="white text-xs-right">{{ props.item.reactTxnAmt }}</td>
                  <td class="white text-xs-right">{{ props.item.reactTxnCount }}</td>
                  <td class="white text-xs-right">{{ props.item.returnCustCount }}</td>
                  <td class="white text-xs-right">{{ props.item.returnTxnAmt }}</td>
                  <td class="white text-xs-right">{{ props.item.returnTxnCount }}</td>
                  <td class="white text-xs-right">{{ props.item.lapsedCustCount }}</td>
                  <td class="white text-xs-right">{{ props.item.lapsedTxnAmt }}</td>
                  <td class="white text-xs-right">{{ props.item.lapsedTxnCount }}</td>
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
  import SideNav from './SideNav.vue'

  var numeral = require('numeral')

  export default {
    name: 'balorTrend',
    components: {
      BalorTrendLine,
      PieCharts,
      SideNav
    },
    data () {
      return {
        sideNavData: SideNav.data,
        headers: [
          {
            text: 'Time Period',
            left: true,
            sortable: true,
            value: 'timePeriod'
          },
          {text: 'Customer BALOR Ratio', value: 'custBalor'},
          {text: 'Spend BALOR Ratio', value: 'spendBalor'},
          {text: 'Txn BALOR Ratio', value: 'txnBalor'},
          {text: 'New Cust Count', value: 'newCustCount'},
          {text: 'New Total Sales', value: 'newTxnAmt'},
          {text: 'New Txn Count', value: 'newTxnCount'},
          {text: 'Reactivated Cust Count', value: 'reactCustCount'},
          {text: 'Reactivated Total Sales', value: 'reactTxnAmt'},
          {text: 'Reactivated Txn Count', value: 'reactTxnCount'},
          {text: 'Returning Cust Count', value: 'returnCustCount'},
          {text: 'Returning Total Sales', value: 'returnTxnAmt'},
          {text: 'Returning Txn Count', value: 'returnTxnCount'},
          {text: 'Lapsed Cust Count', value: 'lapsedCustCount'},
          {text: 'Lapsed Total Sales', value: 'lapsedTxnAmt'},
          {text: 'Lapsed Txn Count', value: 'lapsedTxnCount'}
        ],
        retentionItems: [],
        sumItems: {},
        msg: 'BALOR Trend Charts will go here!',
        custData: null,
        txnData: null,
        spendData: null,
        ratioLine: null,
        incomingJson: {},
        custBalorArray: [],
        txnBalorArray: [],
        spendBalorArray: [],
        dateArray: [],
        tpArray: [],
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
        trendLine: this.allTrendData,
        dialog2: false
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
      if (this.jobApp === 'Balor' || this.jobApp === 'balor') {
        this.$store.commit('switchApp', {module: 'BALOR'})
        this.getResults()
      } else {
        alert('Please select a BALOR job from Job History')
        this.$router.push('/Balor/')
      }
    },
    methods: {
      getHelpSection: function () {
        console.log(this.$refs)
        var hpNv = this.$refs.helpNav.$refs.helpNav
        hpNv.value = !hpNv.value
      },
      getResults () {
        summary(this.jobId)
          .catch(err => {
            alert('Could not get Trend results. ' + err.message.toString())
          })
          .then((response) => {
            this.incomingJson = response.data
            console.log(this.incomingJson)
            this.createPies()
            this.createRetentionItems()
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
        var tempDate = []

        for (var i = 0; i < this.jsonMsg.timePeriods.length; i++) {
          tempCust.push(this.jsonMsg.timePeriods[i].custBalor.toFixed(2))
          tempTxn.push(this.jsonMsg.timePeriods[i].txnBalor.toFixed(2))
          tempSpend.push(this.jsonMsg.timePeriods[i].spendBalor.toFixed(2))
          tempTP.push(this.jsonMsg.timePeriods[i].timePeriod)
          tempDate.push(this.jsonMsg.timePeriods[i].anchorDate.substring(1, this.jsonMsg.timePeriods[i].anchorDate.length - 1))
        }

        this.custBalorArray = tempCust
        this.txnBalorArray = tempTxn
        this.spendBalorArray = tempSpend
        this.tpArray = tempTP
        this.dateArray = tempDate

        this.ratioLine = {
          labels: ['Customer', 'Transaction', 'Sales'],
          datasets: [
            {
              label: 'BALOR Ratios',
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
          labels: this.dateArray,
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

        var newData = {}
        newData.labels = this.tpArray
        newData.datasets = []

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
      },
      createRetentionItems () {
        var ttlSales = this.jsonMsg.timePeriods[this.tp - 1].retention.returnNewSales + this.jsonMsg.timePeriods[this.tp - 1].retention.returnReactSales +
          this.jsonMsg.timePeriods[this.tp - 1].retention.returnReturnSales
        var ttlTxns = this.jsonMsg.timePeriods[this.tp - 1].retention.returnNewTxn + this.jsonMsg.timePeriods[this.tp - 1].retention.returnReactTxn +
          this.jsonMsg.timePeriods[this.tp - 1].retention.returnReturnTxn
        this.retentionItems = [
          {name: 'Retention', vals: numeral(this.jsonMsg.timePeriods[this.tp - 1].retention.retention).format('0.00') + '%'},
          {name: 'Retention Growth', vals: numeral(this.jsonMsg.timePeriods[this.tp - 1].retention.retentionGrowth).format('0.00') + '%'},
          {name: 'Retention Sales Lift', vals: numeral((this.jsonMsg.timePeriods[this.tp - 1].retention.ttlSalesLift) / 100).format('0.00')},
          {name: 'Retained Customer Sales from Prior Period', vals: numeral(ttlSales).format('$0,0.00')},
          {name: 'Retention Transaction Lift', vals: numeral((this.jsonMsg.timePeriods[this.tp - 1].retention.ttlTxnLift) / 100).format('0.00')},
          {name: 'Retained Customer Txns from Prior Period', vals: numeral(ttlTxns).format('0,0')}
        ]
      },
      createPies () {
        var tp = 0

        this.custData = {
          datasets: [{
            data: [
              this.jsonMsg.timePeriods[tp].newCustCount,
              this.jsonMsg.timePeriods[tp].returnCustCount,
              this.jsonMsg.timePeriods[tp].reactCustCount],
            backgroundColor: [
              '#8EAC1D',
              '#F7970E',
              '#0087AA'
            ],
            label: 'Customers'
          }],
          labels: [
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
              this.jsonMsg.timePeriods[tp].newTxnAmt.toFixed(2),
              this.jsonMsg.timePeriods[tp].returnTxnAmt.toFixed(2),
              this.jsonMsg.timePeriods[tp].reactTxnAmt.toFixed(2)],
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
        this.createRetentionItems()

        this.custData = {
          datasets: [{
            data: [
              this.jsonMsg.timePeriods[this.tp - 1].newCustCount,
              this.jsonMsg.timePeriods[this.tp - 1].returnCustCount,
              this.jsonMsg.timePeriods[this.tp - 1].reactCustCount],
            backgroundColor: [
              '#8EAC1D',
              '#F7970E',
              '#0087AA'
            ],
            label: 'Customers'
          }],
          labels: [
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
              this.jsonMsg.timePeriods[this.tp - 1].newTxnAmt.toFixed(2),
              this.jsonMsg.timePeriods[this.tp - 1].returnTxnAmt.toFixed(2),
              this.jsonMsg.timePeriods[this.tp - 1].reactTxnAmt.toFixed(2)],
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
              label: 'BALOR Ratios',
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
      }
    }
  }

</script>

<!-- Add 'scoped' attribute to limit CSS to this component only -->
<style scoped>
  .inliner {
    display: inline-block;
  }

  .sum_brd1 {
    border-right: 7px solid #D63809;
  }

  .sum_brd2 {
    border-right: 7px solid #f7970e;
  }

  .summary td:last-child {
    margin-left: 5px;
    text-align: right;
  }

  .line_chart {
    position: relative;
    margin: 0 auto;
    height: 396px !important;
  }

  .pie_ratio_height {
    height:372px !important;
  }

  .ret_sum_height {
    height:500px !important;
  }

  .pie_chart1 {
    position: relative;
    margin: 0 auto;
    /*height: 100px !important;*/
  }

  .ratio_line {
    position: relative;
    margin: 0 auto;
    /*height: 20vw !important;*/
  }

  .left_float {
    float: left;
  }

  .right_float {
    float: right;
  }

  .legend {
    display: inline-block;
    width: 38px;
    height: 12px;
    margin-right: 5px;
  }

  .legend_color2 {
    background-color: #8EAC1D;
  }

  .legend_color3 {
    background-color: #F7970E;
  }

  .legend_color4 {
    background-color: #0087AA;
  }

  .padR5 {
    padding-right: 5px;
  }

  .tabs__item {
    padding: 0 !important;
  }

  .tabs__container a {
    padding: 0;
  }

  .panel {
    overflow: auto;
  }

  .panel .panel_body {
    border: 1px solid #E6EAEE;
    background-color: #FFFFFF;
    border-left: 3px solid #8EAC1D;
    /*border-left: 3px solid #0087aa;*/
    border-top-left-radius: 2px;
    border-bottom-left-radius: 2px;
  }

  .panel .panel_body p, .panel .panel_body h6 {
    color:#354052;
  }

  .truncate1 {
    width: 207px;
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
  }
  /* Smartphones (portrait and landscape) ----------- */
  @media only screen and (min-width: 481px) and (max-width: 960px) {
    /* Styles */
    .pie_ratio_height {
      height:auto !important;
    }
    .ret_sum_height {
      height:820px !important;
    }
  }

  /* Smartphones (portrait and landscape) ----------- */
  @media only screen and (min-width: 320px) and (max-width: 480px) {
    /* Styles */
    .pie_ratio_height {
      height:auto !important;
    }
    .ret_sum_height {
      height:auto !important;
    }
    .padT15_mob {
      padding-top: 15px;
    }
  }
</style>
