<template>
  <v-container fluid class="cadence pl-3 pr-3">
    <!-- =====ROW1===== -->
    <!--Cadence Summary Cards-->
    <v-flex xs12 class="pl-0 pr-0 pb-3 white--text">
      <v-layout row wrap class="cad_sum">
        <v-flex xs4>
          <v-card class="white card_height">
            <v-card-row class="green darken-2">
              <div><v-icon large light>web</v-icon></div>
            </v-card-row>
            <v-card-text class="white green--text card_pad">
              Min. Date: {{ jsonMsg.minDateCadence }}
              <v-divider class="green mt-2 mb-2"></v-divider>
              Max. Date': {{ jsonMsg.maxDateCadence }}
            </v-card-text>
          </v-card>
        </v-flex>
        <v-flex xs4>
          <v-card class="white card_height">
            <v-card-row class="cyan darken-2">
              <div><v-icon large light>web</v-icon></div>
            </v-card-row>
            <v-card-text class="white cyan--text">
              # Customer - 1 Purchase: {{ this.jsonMsg.singleVisit }}
              <v-divider class="cyan mt-2 mb-2"></v-divider>
              Transactions: {{ this.jsonMsg.numRecords }}
              <v-divider class="cyan mt-2 mb-2"></v-divider>
              Raw Cadence - 80th Percentile: {{ this.jsonMsg.rawCadence }}
            </v-card-text>
          </v-card>
        </v-flex>
        <v-flex xs4>
          <v-card class="white card_height">
            <v-card-row class="red darken-2">
              <div><v-icon large light>web</v-icon></div>
            </v-card-row>
            <v-card-text class="white red--text card_pad">
              Normalized Cadence Value: {{ this.jsonMsg.normalizedCadence }}
              <v-divider class="red mt-2 mb-2"></v-divider>
              Time Period: {{ this.jsonMsg.numTimePeriods }}
            </v-card-text>
          </v-card>
        </v-flex>
      </v-layout>
    </v-flex>
    <!--//Cadence Summary Cards-->
    <!-- //=====ROW1===== -->
    <!-- =====ROW2===== -->
    <v-layout row wrap class="mb-2">
      <!--+++++col1+++++-->
      <v-flex xs8>
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
      <v-flex xs4>
        <v-card class="white card_height2">
            <v-card-title primary-title class="white">
              <h6 class="primary--text text-xs-left mb-0">Cadence Raw Data</h6>
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
        jobId: 'macysDemo',
        incomingJson: {},
        tableHeaders: [
          {text: 'Cadence', value: 'cadence'},
          {text: 'Frequency', value: 'frequency'},
          {text: 'Cumlative Frequency', value: 'cumFreq'}],
        tableData: [],
        pagination: {},
        percentages: [],
        cadOptions: {}
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
        this.tableData = tempTable
        console.log(this.perArray)
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
              backgroundColor: '#D63A09',
              xAxisId: 'x-axis-0'
            }
          ]
        }
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
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.cad_sum div {margin:0 auto;}
.card_height{min-height:165px;}
.card_pad{padding-top:35px;padding-bottom:35px;}
.bar_chart{height:40vh !important;}
.card_height2{height: 46vh !important;}

#raw_data_tbl{
  overflow: scroll;
  height:40vh;
}
</style>
