<template>
  <body>
  <div class="balorSegmentTrend">
    <h1>{{ msg }}</h1>
    <br>
    <div>
      <v-select v-bind:items="dimensions"
                v-model="dimensionSelect"
                label="Select Dimension"
                single-line
                bottom
                v-on:input="selectDimension()">
      </v-select>
      <v-select
          v-if="dimensionSelect === 'Customer'"
          v-bind:items="custDim"
          v-model="avgSelect"
          label="Select Average"
          single-line
          bottom
          v-on:input="selectAvg()">
      </v-select>
      <v-select
          v-else-if="dimensionSelect === 'Visits'"
          v-bind:items="visitDim"
          v-model="avgSelect"
          label="Select Average"
          single-line
          bottom
          v-on:input="selectAvg()">
      </v-select>
      <v-select
          v-else-if="dimensionSelect === 'Spend'"
          v-bind:items="spendDim"
          v-model="avgSelect"
          label="Select Average"
          single-line
          bottom
          v-on:input="selectAvg()">
      </v-select>
      <v-select
          v-else-if="dimensionSelect === 'Discount'"
          v-bind:items="discDim"
          v-model="avgSelect"
          label="Select Average"
          single-line
          bottom
          v-on:input="selectAvg()">
      </v-select>
      <bar-chart :chart-data="totalBars"></bar-chart>
      <v-select
        v-bind:items="tpArray"
        v-model="tpSelect"
        label="Select Balor Time Period"
        single-line
        bottom
        v-on:input="selectTP">
      </v-select>
      <v-select
        v-bind:items="avgList"
        v-model="tpAvgSelect"
        label="Select Balor Metric"
        single-line
        bottom
        v-on:input="selectMetric">
      </v-select>
      <horizontal-chart :chart-data="avgBars"></horizontal-chart>
    </div>
  </div>
  </body>
</template>

<script>
  import HorizontalChart from './balorCharts/HorizontalChart'
  import BarChart from './balorCharts/BarChart'

  export default {
    name: 'balorSegmentTrend',
    components: {
      BarChart,
      HorizontalChart
    },
    data () {
      return {
        msg: 'Balor Segment Trend Charts will go here!',
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
        dimensionSelect: 'Customer',
        avgSelect: 'Spend per Customer',
        tpSelect: 1,
        tpAvgSelect: 'Spend per Customer',
        avgList: [
          {text: 'Spend per Customer'},
          {text: 'Items per Customer'},
          {text: 'Discount per Customer'},
          {text: 'Transactions per Customer'},
          {text: 'Spend per Basket'},
          {text: 'Items per Basket'},
          {text: 'Discount per Basket'}
        ],
        incomingJson: {
          'responseCode': 0,
          'isError': 'false',
          'httpStatusCode': 200,
          'data': {
            'normalizedCadence': 1.0,
            'singleVisit': 1,
            'totalCust': 1,
            'maxDateBalor': '2/1/2017',
            'numRecords': 1,
            'timePeriods': [
              {
                'txnBalor': 1.0,
                'returnCustInfo': {
                  'returnCustSpendAvg': 100.0,
                  'returnVisitDiscAvg': 4.3,
                  'returnTxnAmount': 1000.0,
                  'returnVisitItemAvg': 5.1,
                  'returnCustDiscAvg': 2.0,
                  'returnVisitSpendAvg': 1.2,
                  'returnCustVistAvg': 2.0,
                  'returnCustItemAvg': 5.0,
                  'returnCustCount': 75,
                  'returnTxnCount': 200,
                  'returnItemCount': 23,
                  'returnDiscAmt': 12.00
                },
                'spendBalor': 1.0,
                'newCustInfo': {
                  'newCustDiscAvg': 2.0,
                  'newCustCount': 100,
                  'newCustVistAvg': 2.0,
                  'newTxnCount': 200,
                  'newVisitSpendAvg': 1.2,
                  'newCustSpendAvg': 100.0,
                  'newCustItemAvg': 5.0,
                  'newVisitItemAvg': 5.1,
                  'newVisitDiscAvg': 4.3,
                  'newTxnAmt': 1000.0,
                  'newItemCount': 12,
                  'newDiscAmt': 34.00
                },
                'timePeriod': 1,
                'custBalor': 1.0,
                'reactCustInfo': {
                  'reactTxnCount': 200,
                  'reactCustSpendAvg': 100.0,
                  'reactCustCount': 120,
                  'reactCustDiscAvg': 2.0,
                  'reactCustItemAvg': 5.0,
                  'reactTxnAmount': 1000.0,
                  'reactVisitDiscAvg': 4.3,
                  'reactVisitItemAvg': 5.1,
                  'reactCustVistAvg': 2.0,
                  'reactVisitSpendAvg': 1.2,
                  'reactItemCount': 43,
                  'reactDiscAmt': 23.00
                },
                'lapsedCustInfo': {
                  'lapsedTxnCount': 200,
                  'lapsedCustVistAvg': 2.0,
                  'lapsedCustDiscAvg': 2.0,
                  'lapsedCustSpendAvg': 100.0,
                  'lapsedCustItemAvg': 5.0,
                  'lapsedVisitItemAvg': 5.1,
                  'lapsedVisitSpendAvg': 1.2,
                  'lapsedVisitDiscAvg': 4.3,
                  'lapsedCustCount': 45,
                  'lapsedTxnAmount': 1000.0,
                  'lapsedItemCount': 12,
                  'lapsedDiscAmt': 8.00
                }
              }, {
                'txnBalor': 1.0,
                'returnCustInfo': {
                  'returnCustSpendAvg': 100.0,
                  'returnVisitDiscAvg': 4.3,
                  'returnTxnAmount': 1000.0,
                  'returnVisitItemAvg': 5.1,
                  'returnCustDiscAvg': 2.0,
                  'returnVisitSpendAvg': 1.2,
                  'returnCustVistAvg': 2.0,
                  'returnCustItemAvg': 5.0,
                  'returnCustCount': 100,
                  'returnTxnCount': 200,
                  'returnItemCount': 12,
                  'returnDiscAmt': 12.00
                },
                'spendBalor': 1.0,
                'newCustInfo': {
                  'newCustDiscAvg': 2.0,
                  'newCustCount': 100,
                  'newCustVistAvg': 2.0,
                  'newTxnCount': 200,
                  'newVisitSpendAvg': 1.2,
                  'newCustSpendAvg': 100.0,
                  'newCustItemAvg': 5.0,
                  'newVisitItemAvg': 5.1,
                  'newVisitDiscAvg': 4.3,
                  'newTxnAmt': 1000.0,
                  'newItemCount': 10,
                  'newDiscAmt': 11.00
                },
                'timePeriod': 2,
                'custBalor': 1.0,
                'reactCustInfo': {
                  'reactTxnCount': 200,
                  'reactCustSpendAvg': 100.0,
                  'reactCustCount': 100,
                  'reactCustDiscAvg': 2.0,
                  'reactCustItemAvg': 5.0,
                  'reactTxnAmount': 1000.0,
                  'reactVisitDiscAvg': 4.3,
                  'reactVisitItemAvg': 5.1,
                  'reactCustVistAvg': 2.0,
                  'reactVisitSpendAvg': 1.2,
                  'reactItemCount': 21,
                  'reactDiscAmt': 23.00
                },
                'lapsedCustInfo': {
                  'lapsedTxnCount': 200,
                  'lapsedCustVistAvg': 2.0,
                  'lapsedCustDiscAvg': 2.0,
                  'lapsedCustSpendAvg': 100.0,
                  'lapsedCustItemAvg': 5.0,
                  'lapsedVisitItemAvg': 5.1,
                  'lapsedVisitSpendAvg': 1.2,
                  'lapsedVisitDiscAvg': 4.3,
                  'lapsedCustCount': 100,
                  'lapsedTxnAmount': 1000.0,
                  'lapsedItemCount': 43,
                  'lapsedDiscAmt': 22.00
                }
              }, {
                'txnBalor': 1.0,
                'returnCustInfo': {
                  'returnCustSpendAvg': 100.0,
                  'returnVisitDiscAvg': 4.3,
                  'returnTxnAmount': 1000.0,
                  'returnVisitItemAvg': 5.1,
                  'returnCustDiscAvg': 2.0,
                  'returnVisitSpendAvg': 1.2,
                  'returnCustVistAvg': 2.0,
                  'returnCustItemAvg': 5.0,
                  'returnCustCount': 100,
                  'returnTxnCount': 200,
                  'returnItemCount': 34,
                  'returnDiscAmt': 1.25
                },
                'spendBalor': 1.0,
                'newCustInfo': {
                  'newCustDiscAvg': 2.0,
                  'newCustCount': 100,
                  'newCustVistAvg': 2.0,
                  'newTxnCount': 200,
                  'newVisitSpendAvg': 1.2,
                  'newCustSpendAvg': 100.0,
                  'newCustItemAvg': 5.0,
                  'newVisitItemAvg': 5.1,
                  'newVisitDiscAvg': 4.3,
                  'newTxnAmt': 1000.0,
                  'newItemCount': 32,
                  'newDiscAmt': 10.50
                },
                'timePeriod': 3,
                'custBalor': 1.0,
                'reactCustInfo': {
                  'reactTxnCount': 200,
                  'reactCustSpendAvg': 100.0,
                  'reactCustCount': 100,
                  'reactCustDiscAvg': 2.0,
                  'reactCustItemAvg': 5.0,
                  'reactTxnAmount': 1000.0,
                  'reactVisitDiscAvg': 4.3,
                  'reactVisitItemAvg': 5.1,
                  'reactCustVistAvg': 2.0,
                  'reactVisitSpendAvg': 1.2,
                  'reactItemCount': 21,
                  'reactDiscAmt': 21.00
                },
                'lapsedCustInfo': {
                  'lapsedTxnCount': 200,
                  'lapsedCustVistAvg': 2.0,
                  'lapsedCustDiscAvg': 2.0,
                  'lapsedCustSpendAvg': 100.0,
                  'lapsedCustItemAvg': 5.0,
                  'lapsedVisitItemAvg': 5.1,
                  'lapsedVisitSpendAvg': 1.2,
                  'lapsedVisitDiscAvg': 4.3,
                  'lapsedCustCount': 100,
                  'lapsedTxnAmount': 1000.0,
                  'lapsedItemCount': 9,
                  'lapsedDiscAmt': 2.50
                }
              }],
            'retention': 0.8,
            'minDateBalor': '1/1/2017'
          },
          'errors': '',
          'moreInfo': '',
          'userMessage': 'Success',
          'developerMessage': ''
        },
        totalBars: {},
        avgBars: {},
        tpArray: [],
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
      }
    },
    mounted () {
      this.parseJson()
      this.createBars()
      // put initial chart creation methods here
    },
    methods: {
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

        // Bar chart arrays
        for (var i = 0; i < this.jsonMsg.timePeriods.length; i++) {
          tempTp.push(this.jsonMsg.timePeriods[i].timePeriod)

          tempCustRet.push(this.jsonMsg.timePeriods[i].returnCustInfo.returnCustCount)
          tempCustLap.push(this.jsonMsg.timePeriods[i].lapsedCustInfo.lapsedCustCount)
          tempCustNew.push(this.jsonMsg.timePeriods[i].newCustInfo.newCustCount)
          tempCustReact.push(this.jsonMsg.timePeriods[i].reactCustInfo.reactCustCount)

          tempTxnRet.push(this.jsonMsg.timePeriods[i].returnCustInfo.returnTxnCount)
          tempTxnLap.push(this.jsonMsg.timePeriods[i].lapsedCustInfo.lapsedTxnCount)
          tempTxnNew.push(this.jsonMsg.timePeriods[i].newCustInfo.newTxnCount)
          tempTxnReact.push(this.jsonMsg.timePeriods[i].reactCustInfo.reactTxnCount)

          tempItemRet.push(this.jsonMsg.timePeriods[i].returnCustInfo.returnItemCount)
          tempItemLap.push(this.jsonMsg.timePeriods[i].lapsedCustInfo.lapsedItemCount)
          tempItemNew.push(this.jsonMsg.timePeriods[i].newCustInfo.newItemCount)
          tempItemReact.push(this.jsonMsg.timePeriods[i].reactCustInfo.reactItemCount)

          tempSpendRet.push(this.jsonMsg.timePeriods[i].returnCustInfo.returnTxnAmount)
          tempSpendLap.push(this.jsonMsg.timePeriods[i].lapsedCustInfo.lapsedTxnAmount)
          tempSpendNew.push(this.jsonMsg.timePeriods[i].newCustInfo.newTxnAmt)
          tempSpendReact.push(this.jsonMsg.timePeriods[i].reactCustInfo.reactTxnAmount)

          tempDiscRet.push(this.jsonMsg.timePeriods[i].returnCustInfo.returnDiscAmt)
          tempDiscLap.push(this.jsonMsg.timePeriods[i].lapsedCustInfo.lapsedDiscAmt)
          tempDiscNew.push(this.jsonMsg.timePeriods[i].newCustInfo.newDiscAmt)
          tempDiscReact.push(this.jsonMsg.timePeriods[i].reactCustInfo.reactDiscAmt)

          tempNewCustSpend.push(this.jsonMsg.timePeriods[i].newCustInfo.newCustSpendAvg)
          tempNewCustItems.push(this.jsonMsg.timePeriods[i].newCustInfo.newCustItemAvg)
          tempNewCustDisc.push(this.jsonMsg.timePeriods[i].newCustInfo.newCustDiscAvg)
          tempNewCustTxn.push(this.jsonMsg.timePeriods[i].newCustInfo.newCustVistAvg)
          tempNewVisitSpend.push(this.jsonMsg.timePeriods[i].newCustInfo.newVisitSpendAvg)
          tempNewVisitItems.push(this.jsonMsg.timePeriods[i].newCustInfo.newVisitItemAvg)
          tempNewVisitDisc.push(this.jsonMsg.timePeriods[i].newCustInfo.newVisitDiscAvg)

          tempRetCustSpend.push(this.jsonMsg.timePeriods[i].returnCustInfo.returnCustSpendAvg)
          tempRetCustItems.push(this.jsonMsg.timePeriods[i].returnCustInfo.returnCustItemAvg)
          tempRetCustDisc.push(this.jsonMsg.timePeriods[i].returnCustInfo.returnCustDiscAvg)
          tempRetCustTxn.push(this.jsonMsg.timePeriods[i].returnCustInfo.returnCustVistAvg)
          tempRetVisitSpend.push(this.jsonMsg.timePeriods[i].returnCustInfo.returnVisitSpendAvg)
          tempRetVisitItems.push(this.jsonMsg.timePeriods[i].returnCustInfo.returnVisitItemAvg)
          tempRetVisitDisc.push(this.jsonMsg.timePeriods[i].returnCustInfo.returnVisitDiscAvg)

          tempReactCustSpend.push(this.jsonMsg.timePeriods[i].reactCustInfo.reactCustSpendAvg)
          tempReactCustItems.push(this.jsonMsg.timePeriods[i].reactCustInfo.reactCustItemAvg)
          tempReactCustDisc.push(this.jsonMsg.timePeriods[i].reactCustInfo.reactCustDiscAvg)
          tempReactCustTxn.push(this.jsonMsg.timePeriods[i].reactCustInfo.reactCustVistAvg)
          tempReactVisitSpend.push(this.jsonMsg.timePeriods[i].reactCustInfo.reactVisitSpendAvg)
          tempReactVisitItems.push(this.jsonMsg.timePeriods[i].reactCustInfo.reactVisitItemAvg)
          tempReactVisitDisc.push(this.jsonMsg.timePeriods[i].reactCustInfo.reactVisitDiscAvg)

          tempLapCustSpend.push(this.jsonMsg.timePeriods[i].lapsedCustInfo.lapsedCustSpendAvg)
          tempLapCustItems.push(this.jsonMsg.timePeriods[i].lapsedCustInfo.lapsedCustItemAvg)
          tempLapCustDisc.push(this.jsonMsg.timePeriods[i].lapsedCustInfo.lapsedCustDiscAvg)
          tempLapCustTxn.push(this.jsonMsg.timePeriods[i].lapsedCustInfo.lapsedCustVistAvg)
          tempLapVisitSpend.push(this.jsonMsg.timePeriods[i].lapsedCustInfo.lapsedVisitSpendAvg)
          tempLapVisitItems.push(this.jsonMsg.timePeriods[i].lapsedCustInfo.lapsedVisitItemAvg)
          tempLapVisitDisc.push(this.jsonMsg.timePeriods[i].lapsedCustInfo.lapsedVisitDiscAvg)
        }

        this.tpArray = tempTp
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
          labels: this.tpArray,
          datasets: [
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
            }
          ]
        }
      },

      selectDimension () {
        if (this.dimensionSelect === 'Customer') {
          console.log('Customer selected')
          this.totalBars = {
            labels: this.tpArray,
            datasets: [
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
              }
            ]
          }
        } else if (this.dimensionSelect === 'Visits') {
          console.log('Visits selected')
          this.totalBars = {
            labels: this.tpArray,
            datasets: [
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
              }
            ]
          }
        } else if (this.dimensionSelect === 'Spend') {
          console.log('Spend selected')
          this.totalBars = {
            labels: this.tpArray,
            datasets: [
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
              }
            ]
          }
        } else if (this.dimensionSelect === 'Discount') {
          console.log('Discount selected')
          this.totalBars = {
            labels: this.tpArray,
            datasets: [
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
              }
            ]
          }
        }
      },

      selectAvg () {
        if (this.dimensionSelect === 'Customer' && this.avgSelect === 'Avg Spend per Customer') {
          this.totalBars = {
            labels: this.tpArray,
            datasets: [
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
                backgroundColor: '#F7970E',
                fill: false
              }, {
                data: this.avgLapCustSpend,
                label: 'Spend per Lapsed Customer',
                type: 'line',
                backgroundColor: '#D63A09',
                fill: false
              }, {
                data: this.avgNewCustSpend,
                label: 'Spend per New Customer',
                type: 'line',
                backgroundColor: '#8EAC1D',
                fill: false
              }, {
                data: this.avgReactCustSpend,
                label: 'Spend per Reactivated Customer',
                type: 'line',
                backgroundColor: '#0087AA',
                fill: false
              }
            ]
          }
        } else if (this.dimensionSelect === 'Customer' && this.avgSelect === 'Avg Items per Customer') {
          this.totalBars = {
            labels: this.tpArray,
            datasets: [
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
                backgroundColor: '#F7970E',
                fill: false
              }, {
                data: this.avgLapCustItems,
                label: 'Items per Lapsed Customer',
                type: 'line',
                backgroundColor: '#D63A09',
                fill: false
              }, {
                data: this.avgNewCustItems,
                label: 'Items per New Customer',
                type: 'line',
                backgroundColor: '#8EAC1D',
                fill: false
              }, {
                data: this.avgReactCustItems,
                label: 'Items per Reactivated Customer',
                type: 'line',
                backgroundColor: '#0087AA',
                fill: false
              }
            ]
          }
        } else if (this.dimensionSelect === 'Customer' && this.avgSelect === 'Avg Discount per Customer') {
          this.totalBars = {
            labels: this.tpArray,
            datasets: [
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
                backgroundColor: '#F7970E',
                fill: false
              }, {
                data: this.avgLapCustDisc,
                label: 'Discount per Lapsed Customer',
                type: 'line',
                backgroundColor: '#D63A09',
                fill: false
              }, {
                data: this.avgNewCustDisc,
                label: 'Discount per New Customer',
                type: 'line',
                backgroundColor: '#8EAC1D',
                fill: false
              }, {
                data: this.avgReactCustDisc,
                label: 'Discount per Reactivated Customer',
                type: 'line',
                backgroundColor: '#0087AA',
                fill: false
              }
            ]
          }
        } else if (this.dimensionSelect === 'Customer' && this.avgSelect === 'Avg Transactions per Customer') {
          this.totalBars = {
            labels: this.tpArray,
            datasets: [
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
                backgroundColor: '#F7970E',
                fill: false
              }, {
                data: this.avgLapCustTxn,
                label: 'Visits per Lapsed Customer',
                type: 'line',
                backgroundColor: '#D63A09',
                fill: false
              }, {
                data: this.avgNewCustTxn,
                label: 'Visits per New Customer',
                type: 'line',
                backgroundColor: '#8EAC1D',
                fill: false
              }, {
                data: this.avgReactCustTxn,
                label: 'Visits per Reactivated Customer',
                type: 'line',
                backgroundColor: '#0087AA',
                fill: false
              }
            ]
          }
        } else if (this.dimensionSelect === 'Visits' && this.avgSelect === 'Avg Basket Amount') {
          this.totalBars = {
            labels: this.tpArray,
            datasets: [
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
                backgroundColor: '#F7970E',
                fill: false
              }, {
                data: this.avgLapVisitSpend,
                label: 'Spend per Visit - Lapsed',
                type: 'line',
                backgroundColor: '#D63A09',
                fill: false
              }, {
                data: this.avgNewVisitSpend,
                label: 'Spend per Visit - New',
                type: 'line',
                backgroundColor: '#8EAC1D',
                fill: false
              }, {
                data: this.avgReactVisitSpend,
                label: 'Spend per Visit - Reactivated',
                type: 'line',
                backgroundColor: '#0087AA',
                fill: false
              }
            ]
          }
        } else if (this.dimensionSelect === 'Visits' && this.avgSelect === 'Avg Items per Basket') {
          this.totalBars = {
            labels: this.tpArray,
            datasets: [
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
                backgroundColor: '#F7970E',
                fill: false
              }, {
                data: this.avgLapVisitItems,
                label: 'Items per Visit - Lapsed',
                type: 'line',
                backgroundColor: '#D63A09',
                fill: false
              }, {
                data: this.avgNewVisitItems,
                label: 'Items per Visit - New',
                type: 'line',
                backgroundColor: '#8EAC1D',
                fill: false
              }, {
                data: this.avgReactVisitItems,
                label: 'Items per Visit - Reactivated',
                type: 'line',
                backgroundColor: '#0087AA',
                fill: false
              }
            ]
          }
        } else if (this.dimensionSelect === 'Visits' && this.avgSelect === 'Avg Discount per Basket') {
          this.totalBars = {
            labels: this.tpArray,
            datasets: [
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
                backgroundColor: '#F7970E',
                fill: false
              }, {
                data: this.avgLapVisitDisc,
                label: 'Discount per Visit - Lapsed',
                type: 'line',
                backgroundColor: '#D63A09',
                fill: false
              }, {
                data: this.avgNewVisitDisc,
                label: 'Discount per Visit - New',
                type: 'line',
                backgroundColor: '#8EAC1D',
                fill: false
              }, {
                data: this.avgReactVisitDisc,
                label: 'Discount per Visit - Reactivated',
                type: 'line',
                backgroundColor: '#0087AA',
                fill: false
              }
            ]
          }
        } else if (this.dimensionSelect === 'Spend' && this.avgSelect === 'Avg Spend per Customer') {
          this.totalBars = {
            labels: this.tpArray,
            datasets: [
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
                backgroundColor: '#F7970E',
                fill: false
              }, {
                data: this.avgLapCustSpend,
                label: 'Spend per Customer - Lapsed',
                type: 'line',
                backgroundColor: '#D63A09',
                fill: false
              }, {
                data: this.avgNewCustSpend,
                label: 'Spend per Customer - New',
                type: 'line',
                backgroundColor: '#8EAC1D',
                fill: false
              }, {
                data: this.avgReactCustSpend,
                label: 'Spend per Customer - Reactivated',
                type: 'line',
                backgroundColor: '#0087AA',
                fill: false
              }
            ]
          }
        } else if (this.dimensionSelect === 'Spend' && this.avgSelect === 'Avg Spend per Basket') {
          this.totalBars = {
            labels: this.tpArray,
            datasets: [
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
                backgroundColor: '#F7970E',
                fill: false
              }, {
                data: this.avgLapVisitSpend,
                label: 'Spend per Visit - Lapsed',
                type: 'line',
                backgroundColor: '#D63A09',
                fill: false
              }, {
                data: this.avgNewVisitSpend,
                label: 'Spend per Visit - New',
                type: 'line',
                backgroundColor: '#8EAC1D',
                fill: false
              }, {
                data: this.avgReactVisitSpend,
                label: 'Spend per Visit - Reactivated',
                type: 'line',
                backgroundColor: '#0087AA',
                fill: false
              }
            ]
          }
        } else if (this.dimensionSelect === 'Discount' && this.avgSelect === 'Avg Discount per Customer') {
          this.totalBars = {
            labels: this.tpArray,
            datasets: [
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
                backgroundColor: '#F7970E',
                fill: false
              }, {
                data: this.avgLapCustDisc,
                label: 'Discount per Customer - Lapsed',
                type: 'line',
                backgroundColor: '#D63A09',
                fill: false
              }, {
                data: this.avgNewCustDisc,
                label: 'Discount per Customer - New',
                type: 'line',
                backgroundColor: '#8EAC1D',
                fill: false
              }, {
                data: this.avgReactCustDisc,
                label: 'Discount per Customer - Reactivated',
                type: 'line',
                backgroundColor: '#0087AA',
                fill: false
              }
            ]
          }
        } else if (this.dimensionSelect === 'Discount' && this.avgSelect === 'Avg Discount per Basket') {
          this.totalBars = {
            labels: this.tpArray,
            datasets: [
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
                backgroundColor: '#F7970E',
                fill: false
              }, {
                data: this.avgLapVisitDisc,
                label: 'Discount per Visit - Lapsed',
                type: 'line',
                backgroundColor: '#D63A09',
                fill: false
              }, {
                data: this.avgNewVisitDisc,
                label: 'Discount per Visit - New',
                type: 'line',
                backgroundColor: '#8EAC1D',
                fill: false
              }, {
                data: this.avgReactVisitDisc,
                label: 'Discount per Visit - Reactivated',
                type: 'line',
                backgroundColor: '#0087AA',
                fill: false
              }
            ]
          }
        }
      },

      selectTP () {

      },

      selectMetric () {

      }
      // todo fill in more methods here
    }
  }

</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
