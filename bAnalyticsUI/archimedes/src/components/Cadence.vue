<template>
  <v-container fluid class="cadence">
    <v-layout row wrap class="mb-2">
      <v-flex xs12 sm12 md9 style="min-height:39vh;">
        <v-card class="white">
          <v-card-title primary-title class="primary">
            <h6 class="white--text text-xs-left mb-0">Annotated Bar Chart</h6>
          </v-card-title>
          <!--trendlines-->
          <v-layout row wrap class="mb-3 pa-3">
            <v-flex xs12 fill-height class="line_chart">
              <annotated-bar-chart :chart-data="cadenceBars"></annotated-bar-chart>
            </v-flex>
          </v-layout>
          <!--//trendlines-->
        </v-card>
      </v-flex>
      <v-flex xs12 sm12 md3 fill-height>
        <v-card class="white">
          <v-card-title primary-title class="primary">
            <h6 class="white--text text-xs-left mb-0">Cadence Summary</h6>
          </v-card-title>
          <v-flex xs12 fill-height>
            <v-layout row>
              <table width="100%" cellpadding="0" cellspacing="0">
                <tr v-for="item in sumItems" v-bind:key="item.name">
                  <td class="pa-3">
                    <div class="primary--text" v-text="item.name"></div>
                  </td>
                  <td class="pa-3">
                    <div v-text="item.vals"></div>
                  </td>
                </tr>
              </table>
            </v-layout>
          </v-flex>
        </v-card>
      </v-flex>
    </v-layout>
  </v-container>
</template>

<script>
  import AnnotatedBarChart from './balorCharts/AnnotatedBarChart'

  export default {
    name: 'cadence',
    components: {
      AnnotatedBarChart
    },
    data () {
      return {
        sumItems: [],
        msg: 'Cadence Charts and approval will go here!',
        cadenceBars: null,
        cadArray: [],
        countArray: [],
        perArray: [],
        incomingJson: {
          'responseCode': 0,
          'isError': 'false',
          'httpStatusCode': 200,
          'data': {
            'normalizedCadence': '2 weeks',
            'singleVist': 24,
            'percentile': 0.8,
            'numRecords': 1000,
            'maxDateCadence': '04/23/2017',
            'numTimePeriods': 99,
            'minDateCadence': '01/01/2017',
            'freqTable': [{
              'cadence': 1,
              'frequency': 2,
              'cumFreq': 3
            }, {
              'cadence': 2,
              'frequency': 5,
              'cumFreq': 6
            }, {
              'cadence': 3,
              'frequency': 8,
              'cumFreq': 9
            }],
            'rawCadence': 23.3
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
    mounted () {
      this.parseJson()
      this.createBar()
      this.createSummary()
    },
    methods: {
      parseJson () {
        var tempCad = []
        var tempCount = []
        var tempPer = []

        for (var i = 0; i < this.jsonMsg.freqTable.length; i++) {
          tempCad.push(this.jsonMsg.freqTable[i].cadence)
          tempCount.push(this.jsonMsg.freqTable[i].frequency)
          tempPer.push(this.jsonMsg.freqTable[i].cumFreq / this.jsonMsg.numRecords)
        }
        this.cadArray = tempCad
        this.countArray = tempCount
        this.perArray = tempPer
      },

      createBar () {
        this.cadenceBars = {
          labels: this.cadArray,
          datasets: [
            {
              data: this.countArray,
              label: 'Cadence',
              backgroundColor: '#F7970E'
            }
          ]
        }
      },
      createSummary () {
        this.sumItems.push({name: 'Min. Date', vals: this.jsonMsg.minDateCadence})
        this.sumItems.push({name: 'Max. Date', vals: this.jsonMsg.maxDateCadence})
        this.sumItems.push({name: '1 Purchase Customers', vals: this.jsonMsg.singleVist})
        this.sumItems.push({name: 'Transactions', vals: this.jsonMsg.numRecords})
        this.sumItems.push({name: 'Raw Cadence - 80% - Days', vals: this.jsonMsg.rawCadence})
        this.sumItems.push({name: 'Normalized Cadence', vals: this.jsonMsg.normalizedCadence})
        this.sumItems.push({name: 'Time Period', vals: this.jsonMsg.numTimePeriods})
      }
      // todo more methods fit in here.....
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
