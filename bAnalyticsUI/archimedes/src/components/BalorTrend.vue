<template>
  <v-container fluid class="balorSegmentTrend">
    <v-layout row wrap class="mt-0 mb-3 pa-0">
      <v-flex xs12>
        <v-card flat>
          <v-tabs id="mobile-tabs-3" grow class="elevation-1">
            <v-tabs-bar slot="activators" class="grey lighten-2">
              <v-tabs-slider></v-tabs-slider>
              <v-tabs-item router to="/Balor/Trend">
                BALOR Trends
                <v-icon class="pt-1">timeline</v-icon>
              </v-tabs-item>
              <v-tabs-item router to="/Balor/SegmentTrend">
                Segment Trends
                <v-icon class="pt-1">bar_chart</v-icon>
              </v-tabs-item>
            </v-tabs-bar>
          </v-tabs>
        </v-card>
      </v-flex>
    </v-layout>

    <v-layout row wrap class="mb-3">
      <v-flex xs12 sm12 md8 lg9 class="mb-3" style="height:49vh;">
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
      <v-flex xs12 sm12 md4 lg3 fill-height>
        <v-card class="white">
          <v-card-title primary-title class="white">
            <h6 class="primary--text text-xs-left mb-0">BALOR Summary</h6>
          </v-card-title>
          <v-divider class="primary pb-0"></v-divider>
          <v-flex xs12 fill-height>
            <v-layout row>
              <table width="100%" cellpadding="0" cellspacing="0">
                <tr v-for="item in sumItems" v-bind:key="item.name">
                  <td class="pa-2" width="50%">
                    <div class="primary--text" v-text="item.name"></div>
                  </td>
                  <td class="pa-2" width="50%">
                    <div v-text="item.vals"></div>
                  </td>
                </tr>
              </table>
            </v-layout>
          </v-flex>
        </v-card>
      </v-flex>
    </v-layout>

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
          <v-layout row wrap>
            <!--selection-->
            <v-flex xs12>
              <v-card flat>
                <div class="pa-3" style="height:7vh;">
                  <div id="selection" class="inliner left_float">
                    <v-select v-model="tp"
                              v-bind:items="tpArray"
                              label="Select Time Period"
                              v-on:input="setTP()"
                              style="width:175px;margin-bottom:0;">
                    </v-select>
                  </div>
                  <div class="primary--text inliner right_float pt-3" v-for="item in retentionItems"
                       v-bind:key="item.name">
                    {{ item.name }}: {{ item.vals }}
                  </div>
                </div>
              </v-card>
            </v-flex>
            <!--//selection-->
          </v-layout>
          <v-layout row wrap>
            <v-flex xs12>
              <v-card flat class="white pa-3">
                <div class="legend" style="background-color:#D63809;"></div>
                <div style="display:inline;padding-right:5px;">Lapsed</div>
                <div class="legend" style="background-color:#8EAC1D;"></div>
                <div style="display:inline;padding-right:5px;">New</div>
                <div class="legend" style="background-color:#F7970E;"></div>
                <div style="display:inline;padding-right:5px;">Returning</div>
                <div class="legend" style="background-color:#0087AA;"></div>
                <div style="display:inline;padding-right:5px;">Reactivated</div>
              </v-card>
            </v-flex>
          </v-layout>
          <!--Pie Charts-->
          <v-layout row wrap>
            <v-flex xs12 sm6 md4 lg3>
              <v-card flat class="white pa-3">
                <pie-charts class="white pie_chart1" :chart-data='custData'></pie-charts>
                <div primary-title class="primary--text text-xs-center pa-2">Customers</div>
              </v-card>
            </v-flex>
            <v-flex xs12 sm6 md4 lg3>
              <v-card flat class="white pa-3">
                <pie-charts class="white pie_chart1" :chart-data='txnData'></pie-charts>
                <div primary-title class="primary--text text-xs-center pa-2">Transactions</div>
              </v-card>
            </v-flex>
            <v-flex xs12 sm6 md4 lg3>
              <v-card flat class="white pa-3">
                <pie-charts class="white pie_chart1" :chart-data='spendData'></pie-charts>
                <div primary-title class="primary--text text-xs-center pa-2">Sales</div>
              </v-card>
            </v-flex>
            <v-flex xs12 sm6 md7 lg3>
              <v-card flat class="white pt-4 pr-3 pb-3 pl-3">
                <balor-trend-line class="white ratio_line" :chart-data='ratioLine'></balor-trend-line>
              </v-card>
            </v-flex>
          </v-layout>
          <!--/Pie Charts-->
        </v-card>
      </v-flex>
    </v-layout>

  </v-container>


</template>

<script>
  import BalorTrendLine from './balorCharts/BalorTrendLine.js'
  import PieCharts from './balorCharts/PieCharts.js'
  import noUiSlider from 'noUiSlider'

  export default {
    name: 'balorTrend',
    components: {
      BalorTrendLine,
      PieCharts
    },
    data () {
      return {

        retentionItems: [],
        sumItems: [],
        msg: 'Balor Trend Charts will go here!',
        custData: null,
        txnData: null,
        spendData: null,
        ratioLine: null,
        incomingJson: {
          'responseCode': 0,
          'isError': 'false',
          'httpStatusCode': 200,
          'data': {
            'normalizedCadence': 1.0,
            'singleVisit': 1050,
            'totalCust': 100000,
            'maxDateBalor': '2016-12-31',
            'numRecords': 2702629,
            'timePeriods': [
              {
                'lapsedTxnCount': 92230,
                'newCustCount': 69506,
                'reactTxnCount': 39050,
                'newTxnCount': 116307,
                'txnBalor': 1.684451913694026,
                'returnTxnAmount': 4716953.0,
                'reactCustCount': 17966,
                'spendBalor': 2.107847534662702,
                'timePeriod': 1,
                'reactTxnAmount': 1033399.0,
                'returnCustCount': 69462,
                'custBalor': 1.508397999655113,
                'lapsedCustCount': 57990,
                'lapsedTxnAmount': 2175970.0,
                'newTxnAmt': 3553214.0,
                'returnTxnCount': 215602
              }, {
                'lapsedTxnCount': 77938,
                'newCustCount': 61236,
                'reactTxnCount': 36643,
                'newTxnCount': 100160,
                'txnBalor': 1.7552798378198056,
                'returnTxnAmount': 2991339.0,
                'reactCustCount': 17432,
                'spendBalor': 1.5643779808631946,
                'timePeriod': 2,
                'reactTxnAmount': 842324.0,
                'returnCustCount': 48784,
                'custBalor': 1.5794567028730901,
                'lapsedCustCount': 49807,
                'lapsedTxnAmount': 2192947.0,
                'newTxnAmt': 2588274.0,
                'returnTxnCount': 144387
              }, {
                'lapsedTxnCount': 85303,
                'newCustCount': 41723,
                'reactTxnCount': 27621,
                'newTxnCount': 63081,
                'txnBalor': 1.0632920295886428,
                'returnTxnAmount': 2437061.0,
                'reactCustCount': 15016,
                'spendBalor': 1.0520229762587552,
                'timePeriod': 3,
                'reactTxnAmount': 724850.0,
                'returnCustCount': 41852,
                'custBalor': 1.0629859302695919,
                'lapsedCustCount': 53377,
                'lapsedTxnAmount': 2549066.0,
                'newTxnAmt': 1956826.0,
                'returnTxnCount': 109455
              }, {
                'lapsedTxnCount': 88456,
                'newCustCount': 40499,
                'reactTxnCount': 28337,
                'newTxnCount': 61986,
                'txnBalor': 1.0211065388441711,
                'returnTxnAmount': 2623998.0,
                'reactCustCount': 14838,
                'spendBalor': 1.3453899282385546,
                'timePeriod': 4,
                'reactTxnAmount': 845628.0,
                'returnCustCount': 39892,
                'custBalor': 0.9886197162968521,
                'lapsedCustCount': 55974,
                'lapsedTxnAmount': 2241733.0,
                'newTxnAmt': 2170377.0,
                'returnTxnCount': 104411
              }, {
                'lapsedTxnCount': 92734,
                'newCustCount': 39288,
                'reactTxnCount': 28749,
                'newTxnCount': 58170,
                'txnBalor': 0.9372937649621498,
                'returnTxnAmount': 2093211.0,
                'reactCustCount': 15697,
                'spendBalor': 1.1416113309970697,
                'timePeriod': 5,
                'reactTxnAmount': 733655.0,
                'returnCustCount': 40881,
                'custBalor': 0.9591299190622383,
                'lapsedCustCount': 57328,
                'lapsedTxnAmount': 2257736.0,
                'newTxnAmt': 1843802.0,
                'returnTxnCount': 103120
              }, {
                'lapsedTxnCount': 100803,
                'newCustCount': 39702,
                'reactTxnCount': 27053,
                'newTxnCount': 60239,
                'txnBalor': 0.8659662906857931,
                'returnTxnAmount': 2084852.0,
                'reactCustCount': 14945,
                'spendBalor': 1.047200847460415,
                'timePeriod': 6,
                'reactTxnAmount': 683061.0,
                'returnCustCount': 43562,
                'custBalor': 0.9053962257898835,
                'lapsedCustCount': 60357,
                'lapsedTxnAmount': 2431736.0,
                'newTxnAmt': 1863455.0,
                'returnTxnCount': 111003
              }, {
                'lapsedTxnCount': 97086,
                'newCustCount': 41056,
                'reactTxnCount': 26735,
                'newTxnCount': 63154,
                'txnBalor': 0.9258698473518324,
                'returnTxnAmount': 2477227.0,
                'reactCustCount': 14347,
                'spendBalor': 0.9483848897077092,
                'timePeriod': 7,
                'reactTxnAmount': 616520.0,
                'returnCustCount': 48516,
                'custBalor': 0.905322156314852,
                'lapsedCustCount': 61197,
                'lapsedTxnAmount': 2616230.0,
                'newTxnAmt': 1864673.0,
                'returnTxnCount': 131028
              }, {
                'lapsedTxnCount': 88212,
                'newCustCount': 47370,
                'reactTxnCount': 31164,
                'newTxnCount': 72332,
                'txnBalor': 1.1732644084705028,
                'returnTxnAmount': 2646692.0,
                'reactCustCount': 16662,
                'spendBalor': 1.2618917303311943,
                'timePeriod': 8,
                'reactTxnAmount': 854868.0,
                'returnCustCount': 45681,
                'custBalor': 1.1417363550452009,
                'lapsedCustCount': 56083,
                'lapsedTxnAmount': 2497597.0,
                'newTxnAmt': 2296829.0,
                'returnTxnCount': 122000
              }, {
                'lapsedTxnCount': 96699,
                'newCustCount': 44833,
                'reactTxnCount': 26956,
                'newTxnCount': 68335,
                'txnBalor': 0.9854393530439819,
                'returnTxnAmount': 2518094.0,
                'reactCustCount': 14333,
                'spendBalor': 1.0379567379694452,
                'timePeriod': 9,
                'reactTxnAmount': 763353.0,
                'returnCustCount': 42598,
                'custBalor': 0.9843773396556027,
                'lapsedCustCount': 60105,
                'lapsedTxnAmount': 2864498.0,
                'newTxnAmt': 2209872.0,
                'returnTxnCount': 111083
              }, {
                'lapsedTxnCount': 88676,
                'newCustCount': 45153,
                'reactTxnCount': 32013,
                'newTxnCount': 68897,
                'txnBalor': 1.137962921196265,
                'returnTxnAmount': 2600382.0,
                'reactCustCount': 16647,
                'spendBalor': 1.1262522030538704,
                'timePeriod': 10,
                'reactTxnAmount': 838657.0,
                'returnCustCount': 40903,
                'custBalor': 1.0770303241547579,
                'lapsedCustCount': 57380,
                'lapsedTxnAmount': 2780799.0,
                'newTxnAmt': 2293224.0,
                'returnTxnCount': 110152
              }],
            'minDateBalor': '2016-01-01'
          },
          'errors': '',
          'moreInfo': '',
          'userMessage': 'Cowboys',
          'developerMessage': ''
        },
        custBalorArray: [],
        txnBalorArray: [],
        spendBalorArray: [],
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
        tp: 1,
        trendLine: this.allTrendData
      }
    },
    computed: {
      jsonMsg: function () {
        return this.incomingJson.data
      }
    },
    mounted () {
      this.createPies()
      this.createLines()
      this.createSlider()
      this.createSummary()

      // this.createTrend()
    },
    methods: {
      createSummary () {
        this.sumItems.push({name: 'Min. Date', vals: this.jsonMsg.minDateBalor})
        this.sumItems.push({name: 'Max. Date', vals: this.jsonMsg.maxDateBalor})
        this.sumItems.push({name: 'Customer Base', vals: this.jsonMsg.totalCust})
        this.sumItems.push({
          name: '% Customer - 1 Purchase',
          vals: this.jsonMsg.singleVisit / this.jsonMsg.totalCust * 100
        })
        this.sumItems.push({name: 'Transactions', vals: this.jsonMsg.numRecords})
        this.sumItems.push({name: 'Purchase Cadence - 80th Percentile', vals: this.jsonMsg.normalizedCadence})
        this.sumItems.push({name: 'Time Periods', vals: this.jsonMsg.timePeriods.length})
      },
      createLines () {
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
              this.jsonMsg.timePeriods[tp].returnTxnAmount,
              this.jsonMsg.timePeriods[tp].reactTxnAmount],
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
        console.log(this.Slider)
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
              this.jsonMsg.timePeriods[this.tp - 1].returnTxnAmount,
              this.jsonMsg.timePeriods[this.tp - 1].reactTxnAmount],
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

  .inliner {
    display: inline-block;
  }

  .line_chart {
    position: relative;
    margin: 0 auto;
    height: 35vh !important;
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
    border-radius: 2px;
    margin-right: 5px;
  }

  .legend_color {
    background-color: #005E76;
  }

</style>
