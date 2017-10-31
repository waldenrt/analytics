<template>
  <v-container fluid class="quantileSummary pl-3 pr-3 mb-3">
    <!-- =====ROW1===== -->
    <v-layout row wrap class="pt-0 mt-0 mb-3">
      <v-flex xs12>
        <v-card class="pa-0 ma-0 grey lighten-2">
          <v-card-title primary-title class="primary">
            <h6 class="white--text text-xs-left mb-0">Segment Summary</h6>
          </v-card-title>
          <v-layout row wrap>
            <!--Dropdown1-->
            <v-flex xs12 sm3>
              <v-card flat class="pl-2 pr-2 pt-0 pb-0 grey lighten-2">
                <v-layout row wrap>
                  <v-flex xs12>
                    <div class="primary--text text-xs-left pl-0 pr-0 pb-0 pt-2">
                      Time Period:
                    </div>
                  </v-flex>
                  <v-flex xs12>
                    <v-card class="white pa-0">
                      <v-select v-bind:items="tpArray"
                                v-model="tpSelect"
                                label="Select Time Period"
                                single-line
                                bottom
                                v-on:input="selectTP()"
                                class="pl-1 pr-1 m-0">
                      </v-select>
                    </v-card>
                  </v-flex>
                </v-layout>
              </v-card>
            </v-flex>
            <!--//Dropdown1-->
          </v-layout>
        </v-card>
      </v-flex>
    </v-layout>
    <!-- //=====ROW1===== -->
    <!-- =====ROW2===== -->
    <v-layout wrap row class="mb-3">
      <!--+++++col1+++++-->
      <v-flex xs5 class="pt-0 mt-0">
        <v-card class="white pl-3 pr-3 pt-1 pb-1 card_height">
          <div class="title primary--text text-xs-center pa-1 mb-2">Aggregate Metrics for Time Period {{ this.tpSelect }}
          </div>
          <v-card class="green mb-2 height_bars1">
            <v-card-text class="green darken-2 white--text height_bars2">
              <div class="subheading">Total Customers <span style="float:right;">{{ this.jsonMsg.timePeriods[this.tpSelect - 1].totalCustCount }}</span></div>
            </v-card-text>
          </v-card>
          <v-card class="red mb-2 height_bars1">
            <v-card-text class="red white--text height_bars2">
              <div class="subheading">Total Visits <span style="float:right;">{{ this.jsonMsg.timePeriods[this.tpSelect - 1].totalTxnCount }}</span></div>
            </v-card-text>
          </v-card>
          <v-card class="blue mb-2 height_bars1">
            <v-card-text class="cyan white--text height_bars2">
              <div class="subheading">Total Spend <span style="float:right;">${{ this.jsonMsg.timePeriods[this.tpSelect - 1].totalSales.toFixed(2) }}</span></div>
            </v-card-text>
          </v-card>
          <v-card class="orange mb-2 height_bars1">
            <v-card-text class="orange white--text height_bars2">
              <div class="subheading">Total Units <span style="float:right;">{{ this.jsonMsg.timePeriods[this.tpSelect - 1].totalItems }}</span></div>
            </v-card-text>
          </v-card>
        </v-card>
      </v-flex>
      <!--//+++++col1+++++-->
      <!--+++++col2+++++-->
      <v-flex xs7 class="pt-0 mt-0">
        <v-card class="white pl-3 pr-3 pt-1 pb-1">
          <div class="title primary--text text-xs-center pa-1 mb-2">Segment Percent of Total for Time Period {{ this.tpSelect
            }}
          </div>
          <v-layout row class="doughnuts">
            <div><doughnut-chart :chart-data="bestCust" :width="100" :height="100"></doughnut-chart></div>
            <div><doughnut-chart :chart-data="risingCust" :width="100" :height="100"></doughnut-chart></div>
            <div><doughnut-chart :chart-data="middleCust" :width="100" :height="100"></doughnut-chart></div>
            <div><doughnut-chart :chart-data="lapsingCust" :width="100" :height="100"></doughnut-chart></div>
            <div><doughnut-chart :chart-data="deeplyCust" :width="100" :height="100"></doughnut-chart></div>
          </v-layout>
          <v-layout row class="doughnuts">
            <div><doughnut-chart :chart-data="bestVisits" :width="100" :height="100"></doughnut-chart></div>
            <div><doughnut-chart :chart-data="risingVisits" :width="100" :height="100"></doughnut-chart></div>
            <div><doughnut-chart :chart-data="middleVisits" :width="100" :height="100"></doughnut-chart></div>
            <div><doughnut-chart :chart-data="lapsingVisits" :width="100" :height="100"></doughnut-chart></div>
            <div><doughnut-chart :chart-data="deeplyVisits" :width="100" :height="100"></doughnut-chart></div>
          </v-layout>
          <v-layout row class="doughnuts">
            <div><doughnut-chart :chart-data="bestSpend" :width="100" :height="100"></doughnut-chart></div>
            <div><doughnut-chart :chart-data="risingSpend" :width="100" :height="100"></doughnut-chart></div>
            <div><doughnut-chart :chart-data="middleSpend" :width="100" :height="100"></doughnut-chart></div>
            <div><doughnut-chart :chart-data="lapsingSpend" :width="100" :height="100"></doughnut-chart></div>
            <div><doughnut-chart :chart-data="deeplySpend" :width="100" :height="100"></doughnut-chart></div>
          </v-layout>
          <v-layout row class="doughnuts">
            <div><doughnut-chart :chart-data="bestUnits" :width="100" :height="100"></doughnut-chart></div>
            <div><doughnut-chart :chart-data="risingUnits" :width="100" :height="100"></doughnut-chart></div>
            <div><doughnut-chart :chart-data="middleUnits" :width="100" :height="100"></doughnut-chart></div>
            <div><doughnut-chart :chart-data="lapsingUnits" :width="100" :height="100"></doughnut-chart></div>
            <div><doughnut-chart :chart-data="deeplyUnits" :width="100" :height="100"></doughnut-chart></div>
          </v-layout>
        </v-card>
      </v-flex>
      <!--//+++++col2+++++-->
    </v-layout>
    <!-- //=====ROW2===== -->
    <!-- =====ROW3===== -->
    <v-layout wrap row>
      <!--+++++col1+++++-->
      <v-flex xs4 class="pt-0 mt-0">
        <v-card class="pl-3 pr-3 pt-1 pb-1 elevation-0">
          <div class="title primary--text text-xs-center pa-1">Overall Metrics Time Period {{ this.tpSelect }}</div>
        </v-card>
      </v-flex>
      <!--//+++++col1+++++-->
      <!--+++++col2+++++-->
      <v-flex xs8 class="pt-0 mt-0">
        <v-card class="pl-3 pr-3 pt-1 pb-1 elevation-0">
          <div class="title primary--text text-xs-center pa-1">Segment Metrics for Time Period {{ this.tpSelect }}</div>
        </v-card>
      </v-flex>
      <!--//+++++col2+++++-->
    </v-layout>
    <!-- //=====ROW3===== -->
    <!-- //=====ROW4===== -->
    <v-layout wrap row>
      <v-flex xs12>
        <v-card class="white">
        <v-data-table
            v-bind:headers="tableHeaders"
            :items="tableData"
            hide-actions>
          <template slot="items" scope="props">
            <td>{{ props.item.avgName }}</td>
            <td class="text-xs-right">{{ props.item.overallAvg }}</td>
            <td class="text-xs-right">{{ props.item.bestAvg }}</td>
            <td class="text-xs-right">{{ props.item.risingAvg }}</td>
            <td class="text-xs-right">{{ props.item.middleAvg }}</td>
            <td class="text-xs-right">{{ props.item.lapsingAvg }}</td>
            <td class="text-xs-right">{{ props.item.deeplyAvg }}</td>
          </template>
        </v-data-table>
        </v-card>
      </v-flex>
    </v-layout>
  </v-container>
</template>

<script>
  import {summary} from './javascript/lifecycle.service'
  import DoughnutChart from './lifecycleCharts/doughnutChart'

  export default {
    name: 'lifecycleSummary',
    components: {
      DoughnutChart
    },
    data () {
      return {
        incomingJson: {},
        tpSelect: 1,
        tpArray: [],
        tableHeaders: [
          {text: '', value: 'avgName'},
          {text: 'Overall', value: 'overallAvg'},
          {text: 'Best in Class', value: 'bestAvg'},
          {text: 'Rising Stars', value: 'risingAvg'},
          {text: 'Middle of the Road', value: 'middleAvg'},
          {text: 'Lapsing', value: 'lapsingAvg'},
          {text: 'Deeply Lapsed', value: 'deeplyAvg'}],
        tableData: [],
        tpDataArray: [],
        jobId: 'testLifecycle',
        bestCust: {},
        bestVisits: {},
        bestSpend: {},
        bestUnits: {},
        risingCust: {},
        risingVisits: {},
        risingSpend: {},
        risingUnits: {},
        middleCust: {},
        middleVisits: {},
        middleSpend: {},
        middleUnits: {},
        lapsingCust: {},
        lapsingVisits: {},
        lapsingSpend: {},
        lapsingUnits: {},
        deeplyCust: {},
        deeplyVisits: {},
        deeplySpend: {},
        deeplyUnits: {}
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
        summary(this.jobId)
          .catch(err => {
            alert('Could not get Lifecycle Summary results. ' + err.message.toString())
          })
          .then((response) => {
            this.incomingJson = response.data
            console.log(this.incomingJson)
            this.parseJson()
            this.createDoughnuts()
          })
      },

      parseJson () {
        console.log('jsonMsg')
        console.log(this.jsonMsg)

        var tempTpArray = []
        var tempTableData = []
        var tableRows = ['AvgRecencyCust', 'AvgFrequencyCust', 'AvgSpendCust', 'AvgItemsCust', 'AvgSpendVisit']

        for (let i = 0; i < this.jsonMsg.timePeriods.length; i++) {
          tempTpArray.push(this.jsonMsg.timePeriods[i].timePeriod)
          tempTableData.push([{
            'avgName': tableRows[0],
            'overallAvg': ((this.jsonMsg.timePeriods[i].bestRecencyTotal + this.jsonMsg.timePeriods[i].risingRecencyTotal +
            this.jsonMsg.timePeriods[i].middleRecencyTotal + this.jsonMsg.timePeriods[i].lapsingRecencyTotal +
            this.jsonMsg.timePeriods[i].deeplyRecencyTotal) / this.jsonMsg.timePeriods[i].totalCustCount).toFixed(2),
            'bestAvg': this.jsonMsg.timePeriods[i].bestAvgRecency.toFixed(2),
            'risingAvg': this.jsonMsg.timePeriods[i].risingAvgRecency.toFixed(2),
            'middleAvg': this.jsonMsg.timePeriods[i].middleAvgRecency.toFixed(2),
            'lapsingAvg': this.jsonMsg.timePeriods[i].lapsingAvgRecency.toFixed(2),
            'deeplyAvg': this.jsonMsg.timePeriods[i].deeplyAvgRecency.toFixed(2)
          }, {
            'avgName': tableRows[1],
            'overallAvg': ((this.jsonMsg.timePeriods[i].bestTxnTotal + this.jsonMsg.timePeriods[i].risingTxnTotal +
            this.jsonMsg.timePeriods[i].middleTxnTotal + this.jsonMsg.timePeriods[i].lapsingTxnTotal +
            this.jsonMsg.timePeriods[i].deeplyTxnTotal) / this.jsonMsg.timePeriods[i].totalCustCount).toFixed(2),
            'bestAvg': this.jsonMsg.timePeriods[i].bestAvgFreq.toFixed(2),
            'risingAvg': this.jsonMsg.timePeriods[i].risingAvgFreq.toFixed(2),
            'middleAvg': this.jsonMsg.timePeriods[i].middleAvgFreq.toFixed(2),
            'lapsingAvg': this.jsonMsg.timePeriods[i].lapsingAvgFreq.toFixed(2),
            'deeplyAvg': this.jsonMsg.timePeriods[i].deeplyAvgFreq.toFixed(2)
          }, {
            'avgName': tableRows[2],
            'overallAvg': '$' + ((this.jsonMsg.timePeriods[i].bestSalesTotal + this.jsonMsg.timePeriods[i].risingSalesTotal +
            this.jsonMsg.timePeriods[i].middleSalesTotal + this.jsonMsg.timePeriods[i].lapsingSalesTotal +
            this.jsonMsg.timePeriods[i].deeplySalesTotal) / this.jsonMsg.timePeriods[i].totalCustCount).toFixed(2),
            'bestAvg': '$' + this.jsonMsg.timePeriods[i].bestAvgSales.toFixed(2),
            'risingAvg': '$' + this.jsonMsg.timePeriods[i].risingAvgSales.toFixed(2),
            'middleAvg': '$' + this.jsonMsg.timePeriods[i].middleAvgSales.toFixed(2),
            'lapsingAvg': '$' + this.jsonMsg.timePeriods[i].lapsingAvgSales.toFixed(2),
            'deeplyAvg': '$' + this.jsonMsg.timePeriods[i].deeplyAvgSales.toFixed(2)
          }, {
            'avgName': tableRows[3],
            'overallAvg': ((this.jsonMsg.timePeriods[i].bestItemTotal + this.jsonMsg.timePeriods[i].risingItemTotal +
            this.jsonMsg.timePeriods[i].middleItemTotal + this.jsonMsg.timePeriods[i].lapsingItemTotal +
            this.jsonMsg.timePeriods[i].deeplyItemTotal) / this.jsonMsg.timePeriods[i].totalCustCount).toFixed(2),
            'bestAvg': this.jsonMsg.timePeriods[i].bestAvgItems.toFixed(2),
            'risingAvg': this.jsonMsg.timePeriods[i].risingAvgItems.toFixed(2),
            'middleAvg': this.jsonMsg.timePeriods[i].middleAvgItems.toFixed(2),
            'lapsingAvg': this.jsonMsg.timePeriods[i].lapsingAvgItems.toFixed(2),
            'deeplyAvg': this.jsonMsg.timePeriods[i].deeplyAvgItems.toFixed(2)
          }, {
            'avgName': tableRows[4],
            'overallAvg': '$' + ((this.jsonMsg.timePeriods[i].bestSalesTotal + this.jsonMsg.timePeriods[i].risingSalesTotal +
            this.jsonMsg.timePeriods[i].middleSalesTotal + this.jsonMsg.timePeriods[i].lapsingSalesTotal +
            this.jsonMsg.timePeriods[i].deeplySalesTotal) / this.jsonMsg.timePeriods[i].totalTxnCount).toFixed(2),
            'bestAvg': '$' + (this.jsonMsg.timePeriods[i].bestSalesTotal / this.jsonMsg.timePeriods[i].bestTxnTotal).toFixed(2),
            'risingAvg': '$' + (this.jsonMsg.timePeriods[i].risingSalesTotal / this.jsonMsg.timePeriods[i].risingTxnTotal).toFixed(2),
            'middleAvg': '$' + (this.jsonMsg.timePeriods[i].middleSalesTotal / this.jsonMsg.timePeriods[i].middleTxnTotal).toFixed(2),
            'lapsingAvg': '$' + (this.jsonMsg.timePeriods[i].lapsingSalesTotal / this.jsonMsg.timePeriods[i].lapsingTxnTotal).toFixed(2),
            'deeplyAvg': '$' + (this.jsonMsg.timePeriods[i].deeplySalesTotal / this.jsonMsg.timePeriods[i].deeplyTxnTotal).toFixed(2)
          }
          ])
        }

        this.tpArray = tempTpArray
        this.tpDataArray = tempTableData
        this.tableData = tempTableData[0]

        console.log('tempTableData')
        console.log(tempTableData)
      },

      createDoughnuts () {
        this.bestCust = {
          datasets: [{
            data: [this.jsonMsg.timePeriods[this.tpSelect - 1].bestPercentCustBase * 100,
              100 - (this.jsonMsg.timePeriods[this.tpSelect - 1].bestPercentCustBase * 100)],
            backgroundColor: ['#003947', '#848C98']
          }]
        }

        this.bestVisits = {
          datasets: [{
            data: [this.jsonMsg.timePeriods[this.tpSelect - 1].bestPercentTxnBase * 100,
              100 - (this.jsonMsg.timePeriods[this.tpSelect - 1].bestPercentTxnBase * 100)],
            backgroundColor: ['#003947', '#848C98']
          }]
        }

        this.bestSpend = {
          datasets: [{
            data: [this.jsonMsg.timePeriods[this.tpSelect - 1].bestPercentSalesBase * 100,
              100 - (this.jsonMsg.timePeriods[this.tpSelect - 1].bestPercentSalesBase * 100)],
            backgroundColor: ['#003947', '#848C98']
          }]
        }

        var tempBestUnits = (this.jsonMsg.timePeriods[this.tpSelect - 1].bestItemTotal / this.jsonMsg.timePeriods[this.tpSelect - 1].totalItems) * 100

        this.bestUnits = {
          datasets: [{
            data: [tempBestUnits,
              100 - tempBestUnits],
            backgroundColor: ['#003947', '#848C98']
          }]
        }

        this.risingCust = {
          datasets: [{
            data: [this.jsonMsg.timePeriods[this.tpSelect - 1].risingPercentCustBase * 100,
              100 - (this.jsonMsg.timePeriods[this.tpSelect - 1].risingPercentCustBase * 100)],
            backgroundColor: ['#8EAC1D', '#848C98']
          }]
        }

        this.risingVisits = {
          datasets: [{
            data: [this.jsonMsg.timePeriods[this.tpSelect - 1].risingPercentTxnBase * 100,
              100 - (this.jsonMsg.timePeriods[this.tpSelect - 1].risingPercentTxnBase * 100)],
            backgroundColor: ['#8EAC1D', '#848C98']
          }]
        }

        this.risingSpend = {
          datasets: [{
            data: [this.jsonMsg.timePeriods[this.tpSelect - 1].risingPercentSalesBase * 100,
              100 - (this.jsonMsg.timePeriods[this.tpSelect - 1].risingPercentSalesBase * 100)],
            backgroundColor: ['#8EAC1D', '#848C98']
          }]
        }

        var temprisingUnits = (this.jsonMsg.timePeriods[this.tpSelect - 1].risingItemTotal / this.jsonMsg.timePeriods[this.tpSelect - 1].totalItems) * 100

        this.risingUnits = {
          datasets: [{
            data: [temprisingUnits,
              100 - temprisingUnits],
            backgroundColor: ['#8EAC1D', '#848C98']
          }]
        }

        this.middleCust = {
          datasets: [{
            data: [this.jsonMsg.timePeriods[this.tpSelect - 1].middlePercentCustBase * 100,
              100 - (this.jsonMsg.timePeriods[this.tpSelect - 1].middlePercentCustBase * 100)],
            backgroundColor: ['#0087AA', '#848C98']
          }]
        }

        this.middleVisits = {
          datasets: [{
            data: [this.jsonMsg.timePeriods[this.tpSelect - 1].middlePercentTxnBase * 100,
              100 - (this.jsonMsg.timePeriods[this.tpSelect - 1].middlePercentTxnBase * 100)],
            backgroundColor: ['#0087AA', '#848C98']
          }]
        }

        this.middleSpend = {
          datasets: [{
            data: [this.jsonMsg.timePeriods[this.tpSelect - 1].middlePercentSalesBase * 100,
              100 - (this.jsonMsg.timePeriods[this.tpSelect - 1].middlePercentSalesBase * 100)],
            backgroundColor: ['#0087AA', '#848C98']
          }]
        }

        var tempmiddleUnits = (this.jsonMsg.timePeriods[this.tpSelect - 1].middleItemTotal / this.jsonMsg.timePeriods[this.tpSelect - 1].totalItems) * 100

        this.middleUnits = {
          datasets: [{
            data: [tempmiddleUnits,
              100 - tempmiddleUnits],
            backgroundColor: ['#0087AA', '#848C98']
          }]
        }

        this.lapsingCust = {
          datasets: [{
            data: [this.jsonMsg.timePeriods[this.tpSelect - 1].lapsingPercentCustBase * 100,
              100 - (this.jsonMsg.timePeriods[this.tpSelect - 1].lapsingPercentCustBase * 100)],
            backgroundColor: ['#F7970E', '#848C98']
          }]
        }

        this.lapsingVisits = {
          datasets: [{
            data: [this.jsonMsg.timePeriods[this.tpSelect - 1].lapsingPercentTxnBase * 100,
              100 - (this.jsonMsg.timePeriods[this.tpSelect - 1].lapsingPercentTxnBase * 100)],
            backgroundColor: ['#F7970E', '#848C98']
          }]
        }

        this.lapsingSpend = {
          datasets: [{
            data: [this.jsonMsg.timePeriods[this.tpSelect - 1].lapsingPercentSalesBase * 100,
              100 - (this.jsonMsg.timePeriods[this.tpSelect - 1].lapsingPercentSalesBase * 100)],
            backgroundColor: ['#F7970E', '#848C98']
          }]
        }

        var templapsingUnits = (this.jsonMsg.timePeriods[this.tpSelect - 1].lapsingItemTotal / this.jsonMsg.timePeriods[this.tpSelect - 1].totalItems) * 100

        this.lapsingUnits = {
          datasets: [{
            data: [templapsingUnits,
              100 - templapsingUnits],
            backgroundColor: ['#F7970E', '#848C98']
          }]
        }

        this.deeplyCust = {
          datasets: [{
            data: [this.jsonMsg.timePeriods[this.tpSelect - 1].deeplyPercentCustBase * 100,
              100 - (this.jsonMsg.timePeriods[this.tpSelect - 1].deeplyPercentCustBase * 100)],
            backgroundColor: ['#D63809', '#848C98']
          }]
        }

        this.deeplyVisits = {
          datasets: [{
            data: [this.jsonMsg.timePeriods[this.tpSelect - 1].deeplyPercentTxnBase * 100,
              100 - (this.jsonMsg.timePeriods[this.tpSelect - 1].deeplyPercentTxnBase * 100)],
            backgroundColor: ['#D63809', '#848C98']
          }]
        }

        this.deeplySpend = {
          datasets: [{
            data: [this.jsonMsg.timePeriods[this.tpSelect - 1].deeplyPercentSalesBase * 100,
              100 - (this.jsonMsg.timePeriods[this.tpSelect - 1].deeplyPercentSalesBase * 100)],
            backgroundColor: ['#D63809', '#848C98']
          }]
        }

        var tempdeeplyUnits = (this.jsonMsg.timePeriods[this.tpSelect - 1].deeplyItemTotal / this.jsonMsg.timePeriods[this.tpSelect - 1].totalItems) * 100

        this.deeplyUnits = {
          datasets: [{
            data: [tempdeeplyUnits,
              100 - tempdeeplyUnits],
            backgroundColor: ['#D63809', '#848C98']
          }]
        }
      },

      selectTP () {
        this.tableData = this.tpDataArray[this.tpSelect - 1]
        this.createDoughnuts()
      }
    }
  }

</script>

<style scoped>
.doughnuts div {width:20%;}
.card_height {height:100% !important;}
.height_bars1 {height:20% !important;}
.height_bars2 {height:100% !important; display:table;}
.height_bars2 div {display:table-cell; vertical-align: middle !important;font-weight: bold;}
</style>
