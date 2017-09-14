<template>
  <div>
    <div>
      <v-select v-bind:items="quantiles"
                v-model="quantileSelect"
                label="Select Quantiles"
                single-line
                bottom
                v-on:input="selectQuantile()">
      </v-select>
      <v-select
          v-bind:items="TPArray"
          v-model="TPSelect"
          label="Select Average"
          single-line
          bottom
          v-on:input="selectAvg()">
      </v-select>
      <v-select
          v-bind:items="metrics"
          v-model="metricSelect"
          label="Select Metric"
          single-line
          bottom
          v-on:input="selectMetric()">
      </v-select>
    </div>
    <div>
      <bar-chart></bar-chart>
    </div>
  </div>
</template>

<script>
  import BarChart from './balorCharts/BarChart'

  export default {
    name: 'quantProfile',
    data () {
      return {
        TPArray: [],
        TPSelect: null,
        quantiles: [],
        quantileSelect: null,
        metrics: [
          'Spend',
          'Units',
          'Visits'
        ],
        metricSelect: null,
        spendArray: [],
        unitArray: [],
        visitArray: [],
        paretoData: {},
        incomingJson: {
          'responseCode': 0,
          'isError': '',
          'httpStatusCode': 200,
          'data': {
            'quantileDimension': {
              'numRecords': 1000,
              'minDate': '01/01/2017',
              'maxDate': '12/31/2017',
              'dimension': 'Products'
            },
            'quantileResult': [{
              'avgVisitUnits': 8.0,
              'totalVisits': 2000,
              'avgCustSales': 14.0,
              'avgVisitSpend': 7.0,
              'avgRecency': 10.0,
              'avgSpend': 4.0,
              'totalUnits': 3000,
              'quantile': 1,
              'totalSpend': 1.0,
              'avgCustUnits': 16.0,
              'avgUnits': 5.0,
              'totalCount': 2000,
              'timePeriod': 1,
              'avgItemDisc': 11.0,
              'avgDisc': 6.0,
              'avgVisitDisc': 9.0,
              'avgCustVisits': 15.0,
              'avgVisits': 3.0,
              'totalDisc': 2.0,
              'avgStoreCust': 13.0
            }, {
              'avgVisitUnits': 8.0,
              'totalVisits': 2000,
              'avgCustSales': 14.0,
              'avgVisitSpend': 7.0,
              'avgRecency': 10.0,
              'avgSpend': 4.0,
              'totalUnits': 3000,
              'quantile': 1,
              'totalSpend': 1.0,
              'avgCustUnits': 16.0,
              'avgUnits': 5.0,
              'totalCount': 2000,
              'timePeriod': 2,
              'avgItemDisc': 11.0,
              'avgDisc': 6.0,
              'avgVisitDisc': 9.0,
              'avgCustVisits': 15.0,
              'avgVisits': 3.0,
              'totalDisc': 2.0,
              'avgStoreCust': 13.0
            }]
          },
          'errors': '',
          'moreInfo': '',
          'userMessage': 'Success',
          'developerMessage': ''
        }
      }
    },
    computed: {
      jsonMsg: function () {
        return this.incomingJson.data
      }
    },
    components: {
      BarChart
    },
    methods: {
      selectQuantile () {

      },
      selectMetric () {

      },
      createPareto () {
        var tempVisit = []
        var tempUnit = []
        var tempSpend = []

        for (var i = 0; i < this.jsonMsg.timePeriods.length; i++) {
          tempVisit.push(this.jsonMsg.timePeriods[i].totalVisits)
          tempUnit.push(this.jsonMsg.timePeriods[i].totalUnits)
          tempSpend.push(this.jsonMsg.timePeriods[i].totalSpend)
        }

        this.spendArray = tempSpend
        this.unitArray = tempUnit
        this.visitArray = tempVisit
      }
    }
  }
</script>

<style>

</style>
