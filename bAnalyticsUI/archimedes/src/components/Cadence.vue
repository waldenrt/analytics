<template>
  <v-container fluid class="cadence">
    <v-layout row wrap class="mb-2">
      <v-flex xs12 sm12 md8 lg9>
        <v-card class="white">
        <v-card-title primary-title class="primary">
          <h6 class="white--text text-xs-left mb-0">Annotated Bar Chart</h6>
        </v-card-title>
        <!--trendlines-->
        <v-layout row wrap class="mb-3 pa-3">
            <v-flex xs12>
            <annotated-bar-chart :chart-data="cadenceBars" style="height:21vh !important;" class="bar_chart"></annotated-bar-chart>
            </v-flex>
        </v-layout>
        <!--//trendlines-->
        </v-card>
      </v-flex>
      <v-flex xs12 sm12 md4 lg3 class="mb-3">
        <v-card class="white">
          <v-card-title primary-title class="white">
            <h6 class="primary--text text-xs-left mb-0">Cadence Summary</h6>
          </v-card-title>
          <v-divider class="primary pb-0"></v-divider>
          <v-flex xs12 fill-height>
                <v-layout row wrap>
                    <table cellpadding="0" cellspacing="0" style="height:21vh !important;">
                      <tr v-for="item in sumItems" v-bind:key="item.name">
                        <td class="pa-2">
                          <div class="primary--text" v-text="item.name"></div>
                        </td>
                        <td class="pa-2">
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
            'singleVisit': 24,
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

      createSummary () {
        this.sumItems.push({name: 'Min. Date', vals: this.jsonMsg.minDateCadence})
        this.sumItems.push({name: 'Max. Date', vals: this.jsonMsg.maxDateCadence})
        this.sumItems.push({name: '# Customer - 1 Purchase', vals: this.jsonMsg.singleVisit})
        this.sumItems.push({name: 'Transactions', vals: this.jsonMsg.numRecords})
        this.sumItems.push({name: 'Raw Cadence - 80th Percentile', vals: this.jsonMsg.rawCadence})
        this.sumItems.push({name: 'Normalized Cadence Value', vals: this.jsonMsg.normalizedCadence})
        this.sumItems.push({name: 'Time Period', vals: this.jsonMsg.numTimePeriods})
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
      }
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
  .bar_chart{}
  .table_h{}
</style>
