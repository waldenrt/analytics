<template>
  <v-container fluid class="cadence pl-3 pr-3">
    <!-- =====ROW1===== -->
    <v-layout row wrap class="mb-2">
      <!--Chart-->
      <v-flex xs12 sm12 md8 lg9>
        <v-card class="white">
          <v-card-title primary-title class="primary">
            <h6 class="white--text text-xs-left mb-0">Purchase Cadence Distribution</h6>
          </v-card-title>
          <!--trendlines-->
          <v-layout row wrap class="mb-3 pa-3">
            <v-flex xs12>
              <annotated-bar-chart :chart-data="cadenceBars" style="height:65vh !important;"
                                   class="bar_chart"></annotated-bar-chart>
            </v-flex>
          </v-layout>
          <!--//trendlines-->
        </v-card>
      </v-flex>
      <!--//Chart-->
      <!--Table -->
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
      <!--//Table-->
    </v-layout>
    <!-- //=====ROW1===== -->

    <v-flex xs4 class="mb-3">
      <v-card class="white">
        <v-card-title primary-title class="white">
          <h6 class="primary--text text-xs-left mb-0">Cadence Raw Data</h6>
        </v-card-title>
        <v-divider class="primary pb-0"></v-divider>
        <v-flex xs12 fill-height>
          <v-layout row wrap>
            <table cellpadding="0" cellspacing="0" style="height:21vh !important;">
              <tr v-for="item in tableData" v-bind:key="item.cadence">
                <td class="pa-2">
                  <div class="primary--text" v-text="item.cadence"></div>
                </td>
                <td class="pa-2">
                  <div v-text="item.frequency"></div>
                </td>
                <td class="pa-2">
                  <div v-text="item.cumFreq"></div>
                </td>
              </tr>
            </table>
          </v-layout>
        </v-flex>
      </v-card>
    </v-flex>
    <!--
    <v-layout wrap row>
      <v-flex xs12>
        <v-card class="white pl-3 pr-3 pt-1 pb-1">
          <div class="title primary--text text-xs-center pa-1">Cadence Raw Data</div>
          <v-data-table
              v-bind:headers="tableHeaders"
              v-bind:pagination.sync="pagination"
              :items="tableData"
              hide-actions>
            <template slot="items" scope="props">
              <td>{{ props.item.cadence }}</td>
              <td class="text-xs-right">{{ props.item.frequency }}</td>
              <td class="text-xs-right">{{ props.item.cumFreq }}</td>
            </template>
          </v-data-table>
        </v-card>
      </v-flex>
    </v-layout>
    -->
  </v-container>
</template>

<script>
  import AnnotatedBarChart from './balorCharts/AnnotatedBarChart'
  import {cadence} from './javascript/balor.service'

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
        jobId: 'macysDemo',
        incomingJson: {},
        tableHeaders: ['Cadence', 'Frequency', 'Cumlative Frequency'],
        tableData: [],
        pagination: {}
      }
    },
    computed: {
      jsonMsg: function () {
        return this.incomingJson.data
      }
    },
    mounted () {
      this.getResults()
    },
    methods: {
      getResults () {
        cadence(this.jobId)
          .then((response) => {
            this.incomingJson = response.data
            this.parseJson()
            this.createBar()
            this.createSummary()
          })
          .catch(err => {
            alert('Could not get cadence results. ' + err.message.toString())
          })
      },
      parseJson () {
        console.log('incomingJson: ')
        console.log(this.jsonMsg)
        var tempCad = []
        var tempCount = []
        var tempPer = []
        var tempTable = []

        for (var i = 0; i < this.jsonMsg.freqTable.length; i++) {
          tempCad.push(this.jsonMsg.freqTable[i].cadence)
          tempCount.push(this.jsonMsg.freqTable[i].frequency)
          tempPer.push(this.jsonMsg.freqTable[i].cumFreq / this.jsonMsg.numRecords)

          tempTable.push({
            'cadence': this.jsonMsg.freqTable[i].cadence,
            'frequency': this.jsonMsg.freqTable[i].frequency,
            'cumFreq': this.jsonMsg.freqTable[i].cumFreq
          })
        }
        this.cadArray = tempCad
        this.countArray = tempCount
        this.perArray = tempPer
        console.log('new table data from for loop')
        this.tableData = tempTable
        console.log(this.tableData)
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
              backgroundColor: '#D63A09'
            }
          ]
        }
      }
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
