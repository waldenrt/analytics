<template>
  <v-container fluid class="cadence pl-3 pr-3">
    <!-- =====ROW2===== -->
    <v-layout row wrap class="mb-2">
      <!--+++++col1+++++-->
      <v-flex xs8 lg9>
        <v-card class="white card_height2">
          <v-card-title primary-title class="primary">
            <h6 class="white--text text-xs-left mb-0">Purchase Cadence Distribution</h6>
          </v-card-title>
          <div row wrap class="pt-2 pb-0">
            <!--Chart-->
            <v-flex xs12>
              <annotated-bar-chart
                  :chart-data="cadenceBars"
                  :options="cadOptions"
                  class="bar_chart"
                  id="cadChart"></annotated-bar-chart>
            </v-flex>
            <!--//Chart-->
          </div>
        </v-card>
      </v-flex>
      <!--//+++++col1+++++-->
      <!--+++++col2+++++-->
      <v-flex xs4 lg3>
        <v-layout row wrap class="cad_sum">
          <v-flex xs12 class="pb-3">
            <div class="sum_brd1">
              <v-card horizontal class="white card_height">
                <v-card-text class="white red--text card_pad">
                  <v-card-title primary-title class="pt-0 pb-3">
                    <h6 class="red--text text-xs-left mb-0 pt-0 pb-0">Cadence Summary</h6>
                  </v-card-title>
                  <v-divider class="red"></v-divider>
                  <table width="100%" class="summary">
                    <tr v-for="item in sumItems">
                      <td class="grey lighten-4">{{ item.name }}</td>
                      <td class="grey lighten-4">{{ item.vals }}</td>
                    </tr>
                  </table>
                </v-card-text>
              </v-card>
            </div>
          </v-flex>
        </v-layout>
        <v-card class="white card_height3">
          <v-card-title primary-title class="white">
            <h6 v-if="cadWeeks" class="primary--text text-xs-left mb-0">Cadence Raw Data (Weeks)</h6>
            <h6 v-else class="primary--text text-xs-left mb-0">Cadence Raw Data (Days)</h6>
          </v-card-title>
          <v-divider class="primary pb-0 pl-3 pr-3"></v-divider>
          <v-flex xs12 fill-height>
            <v-layout row wrap>
              <div id="raw_data_tbl">
                <v-data-table
                    v-bind:headers="tableHeaders"
                    :items="tableData"
                    v-bind:search="search"
                    hide-actions>
                  <template slot="items" scope="props">
                    <td class="pa-3">{{ props.item.cadence }}</td>
                    <td>{{ props.item.frequency }}</td>
                    <td>{{ props.item.cumFreq }}</td>
                  </template>
                </v-data-table>
              </div>
            </v-layout>
          </v-flex>
        </v-card>
      </v-flex>
      <!--//+++++col2+++++-->
    </v-layout>
    <!-- //=====ROW2===== -->
  </v-container>
</template>

<script>
  import AnnotatedBarChart from './balorCharts/AnnotatedBarChart'
  import {cadence} from './javascript/balor.service'

  var numeral = require('numeral')

  export default {
    name: 'cadence',
    components: {
      AnnotatedBarChart
    },
    data () {
      return {
        openOver: false,
        search: '',
        sumItems: [],
        msg: 'Cadence Charts and approval will go here!',
        cadenceBars: null,
        cadArray: [],
        countArray: [],
        perArray: [],
        jobId: 'aeoOnlineMayank',
        incomingJson: {},
        tableHeaders: [
          {text: 'Cadence', value: 'cadence'},
          {text: 'Frequency', value: 'frequency'},
          {text: 'Cumlative Frequency', value: 'cumFreq'}],
        tableData: [],
        pagination: {},
        percentages: [],
        cadOptions: {},
        cadWeeks: true
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
            'frequency': numeral(this.jsonMsg.freqTable[i].frequency).format('0,0'),
            'cumFreq': numeral(this.jsonMsg.freqTable[i].cumFreq).format('0,0')
          })
        }

        if (this.jsonMsg.rawCadence > 30) {
          this.cadWeeks === true
        } else {
          this.cadWeeks === false
        }
        this.cadArray = tempCad
        this.countArray = tempCount
        this.perArray = tempPer
        this.tableData = tempTable
      },
      createSummary () {
        this.sumItems.push({name: 'Min. Date', vals: this.jsonMsg.minDateCadence})
        this.sumItems.push({name: 'Max. Date', vals: this.jsonMsg.maxDateCadence})
        this.sumItems.push({name: '# Customer - 1 Purchase', vals: numeral(this.jsonMsg.singleVisit).format()})
        this.sumItems.push({name: 'Transactions', vals: numeral(this.jsonMsg.numRecords).format()})
        this.sumItems.push({name: 'Raw Cadence - 80th Percentile', vals: this.jsonMsg.rawCadence})
        this.sumItems.push({name: 'Normalized Cadence Value', vals: this.jsonMsg.normalizedCadence})
        this.sumItems.push({name: 'Time Period', vals: this.jsonMsg.numTimePeriods})
      },
      createBar () {
        if (this.jsonMsg.rawCadence > 30) {
          let cadLine = Math.trunc(this.jsonMsg.rawCadence / 7)
          console.log('cadLine is: ' + cadLine)
          this.cadOptions = {
            responsive: true,
            maintainAspectRatio: false,
            scales: {
              xAxes: [{
                categoryPercentage: 1.0,
                barPercentage: 1.0,
                id: 'x-axis-0',
                position: 'bottom'
              }]
            },
            annotation: {
              annotations: [
                {
                  type: 'line',
                  mode: 'vertical',
                  scaleID: 'x-axis-0',
                  value: cadLine,
                  borderColor: '#D63A09',
                  label: {
                    content: '80%',
                    enabled: true,
                    position: 'top'
                  }
                }
              ]
            },
            legend: {
              display: false
            }
          }
        } else {
          this.cadOptions = {
            responsive: true,
            maintainAspectRatio: false,
            scales: {
              xAxes: [{
                categoryPercentage: 1.0,
                barPercentage: 1.0,
                id: 'x-axis-0',
                position: 'bottom'
              }]
            },
            annotation: {
              annotations: [
                {
                  type: 'line',
                  mode: 'vertical',
                  scaleID: 'x-axis-0',
                  value: this.jsonMsg.rawCadence,
                  borderColor: '#D63A09',
                  label: {
                    content: '80%',
                    enabled: true,
                    position: 'top'
                  }
                }
              ]
            },
            legend: {
              display: false
            }
          }
        }

        this.cadenceBars = {
          labels: this.cadArray,
          datasets: [
            {
              data: this.countArray,
              label: 'Cadence',
              backgroundColor: '#D63A09',
              xAxisId: 'x-axis-0'
            }
          ]
        }
      }
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style>
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

  .cad_sum div {
    margin: 0 auto;
  }

  .card_height {
    min-height: 165px;
  }

  .card_pad {
    padding-top: 20px;
    padding-bottom: 20px;
  }

  .bar_chart {
    height: 70vh !important;
  }

  .card_height2 {
    height: 77vh !important;
  }

  .card_height3 {
    height: 48vh !important;
  }

  #raw_data_tbl {
    overflow-x: hidden; /* Hide horizontal scrollbar */
    overflow-y: scroll; /* Add vertical scrollbar */
    height: 42vh;
  }
</style>
