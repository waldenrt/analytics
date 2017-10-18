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
              <annotated-bar-chart
                :chart-data="cadenceBars"
                :options="cadOptions"
                style="height:65vh !important;"
                class="bar_chart"
                id="cadChart"></annotated-bar-chart>
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
    <!-- =====ROW2===== -->
        <v-btn floating class="float_btn"
        v-on:click="openOver = !openOver">
          <v-icon light>add</v-icon>
        </v-btn>
    <!-- //=====ROW2===== -->
    <!-- =====ROW3===== -->
    <v-layout row wrap>
      <div
      class="demo"
      :class="{'change_sumthn': openOver}"></div>
    </v-layout>
    <!--<v-layout row wrap class="mb-2">
      <v-flex xs4 class="mb-3">
        <v-card class="white">
          <div id="raw_data_tbl">
            <v-card-title primary-title class="white">
              <h6 class="primary--text text-xs-left mb-0">Cadence Raw Data</h6>
            </v-card-title>
            <v-divider class="primary pb-0"></v-divider>
            <v-flex xs12 fill-height>
              <v-layout row wrap>
                <v-data-table
                  v-bind:headers="tableHeaders"
                  :items="tableData"
                  v-bind:search="search"
                  v-bind:pagination.sync="pagination"
                  hide-actions>
                <template slot="items" scope="props">
                  <td>{{ props.item.cadence }}</td>
                  <td>{{ props.item.frequency }}</td>
                  <td>{{ props.item.cumFreq }}</td>
                </template>
              </v-data-table>
              <div class="text-xs-center pt-2">
                <v-pagination v-model="pagination.page" :length="Math.ceil(pagination.totalItems / pagination.rowsPerPage)"></v-pagination>
              </div>
              </v-layout>
            </v-flex>
          </div>

        </v-card>
      </v-flex>
    </v-layout>-->
    <!-- //=====ROW3===== -->
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
      },
      openOverlay () {
        document.getElementById('myDIV').style.overflow = 'scroll'
      }
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.float_btn{
  background-color:#D63A09;
  position: fixed;
  bottom:70px;
  right:30px;
}
#raw_data_tbl{
  overflow: hidden;
  height:0;
  background:rbga(0,0,0,.2);
}
.demo{
  width: 100%;
  height: 100px;
  background-color: grey;
  display: inline-block;
  margin: 10px;
}
.change_sumthn{
  width: 50px;
  height: 50px;
  background-color: blue;
}
</style>
