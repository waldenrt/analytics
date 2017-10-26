<template>
  <v-container fluid class="balorTrend pl-3 pr-3">
    <!-- =====TABS-ROW===== -->
    <v-layout row wrap class="mt-0 mb-3 pa-0">
      <v-flex xs12>
        <v-card flat height="50px">
          <v-bottom-nav absolute value="true" class="blue-grey lighten-1 elevation-2">
            <v-btn flat class="white--text" router to="/Balor/Trend">
              <span>Balor Trends</span>
              <v-icon>timeline</v-icon>
            </v-btn>
            <v-btn flat class="white--text" router to="/Balor/SegmentTrend">
              <span>Segment Trends</span>
              <v-icon>bar_chart</v-icon>
            </v-btn>
          </v-bottom-nav>
        </v-card>
      </v-flex>
    </v-layout>
    <!-- //=====TABS-ROW===== -->
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
              Min. Date: {{ jsonMsg.minDateBalor }}
              <v-divider class="green mt-2 mb-2"></v-divider>
              Max. Date': {{ jsonMsg.maxDateBalor }}
            </v-card-text>
          </v-card>
        </v-flex>
        <v-flex xs4>
          <v-card class="white card_height">
            <v-card-row class="cyan darken-2">
              <div><v-icon large light>web</v-icon></div>
            </v-card-row>
            <v-card-text class="white cyan--text">
              Customer Base: {{ jsonMsg.totalCusts }}
              <v-divider class="cyan mt-2 mb-2"></v-divider>
              % Customer - 1 Purchase:
              <v-divider class="cyan mt-2 mb-2"></v-divider>
              Transactions: {{ jsonMsg.numRecords }}
            </v-card-text>
          </v-card>
        </v-flex>
        <v-flex xs4>
          <v-card class="white card_height">
            <v-card-row class="red darken-2">
              <div><v-icon large light>web</v-icon></div>
            </v-card-row>
            <v-card-text class="white red--text card_pad">
              Purchase Cadence - 80th Percentile: {{ jsonMsg.normalizedCadence }}
              <v-divider class="red mt-2 mb-2"></v-divider>
              Time Periods: {{ jsonMsg.numTimePeriods }}
            </v-card-text>
          </v-card>
        </v-flex>
      </v-layout>
    </v-flex>
    <!--//Cadence Summary Cards-->
    <!-- //=====ROW1===== -->
    <!-- =====ROW2===== -->
    <v-layout row wrap class="pa-0 mb-0">
      <!--+++++col1+++++-->
      <v-flex xs12>
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
      <!--//+++++col1+++++-->
    </v-layout>
    <!-- //=====ROW2===== -->

    <!-- =====ROW3===== -->
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

          <!-- SELECTION ROW -->
          <v-layout row wrap>
            <!--selection-->
            <v-flex xs12>
              <v-card flat class="pa-3">
                <v-layout row wrap>
                  <v-flex xs12 sm3 md3>
                      <v-card id="selection" class="white inliner">
                        <v-select v-model="tp"
                                  v-bind:items="tpArray"
                                  label="Select Time Period"
                                  v-on:input="setTP()"
                                  style="width:175px;margin-bottom:0;"
                                  class="pl-1 pt-1">
                        </v-select>
                      </v-card>
                  </v-flex>
                  <v-flex xs12 sm3 offset-sm6 md3 offset-md6 class="text-sm-right padT15_mob">
                      <v-card class="white primary--text inliner pt-3 pb-3 pl-2 pr-2" v-for="item in retentionItems"
                           v-bind:key="item.name">
                        {{ item.name }}: {{ item.vals }}
                      </v-card>
                  </v-flex>
                </v-layout>
              </v-card>
            </v-flex>
            <!--//selection-->
          </v-layout>
          <!-- //SELECTION ROW -->
          <!-- LEGEND -->
          <v-layout row wrap>
            <v-flex xs12>
              <v-card flat class="white pa-3">
                <div class="legend legend_color1"></div>
                <div class="inliner padR5">Lapsed</div>
                <div class="legend legend_color2"></div>
                <div class="inliner padR5">New</div>
                <div class="legend legend_color3"></div>
                <div class="inliner padR5">Returning</div>
                <div class="legend legend_color4"></div>
                <div class="inliner padR5">Reactivated</div>
              </v-card>
            </v-flex>
          </v-layout>
          <!-- //LEGEND -->
          <!--Pie Charts-->
          <v-layout row wrap>
            <v-flex xs12 sm6 md3 lg3>
              <v-card flat class="white pa-3">
                <pie-charts class="white pie_chart1" :chart-data='custData'></pie-charts>
                <div primary-title class="primary--text text-xs-center pa-2">Customers</div>
              </v-card>
            </v-flex>
            <v-flex xs12 sm6 md3 lg3>
              <v-card flat class="white pa-3">
                <pie-charts class="white pie_chart1" :chart-data='txnData'></pie-charts>
                <div primary-title class="primary--text text-xs-center pa-2">Transactions</div>
              </v-card>
            </v-flex>
            <v-flex xs12 sm6 md3 lg3>
              <v-card flat class="white pa-3">
                <pie-charts class="white pie_chart1" :chart-data='spendData'></pie-charts>
                <div primary-title class="primary--text text-xs-center pa-2">Sales</div>
              </v-card>
            </v-flex>
            <v-flex xs12 sm6 md3 lg3>
              <v-card flat class="white pt-4 pl-2 pr-2 pb-3">
                <balor-trend-line class="white ratio_line" :chart-data='ratioLine'></balor-trend-line>
              </v-card>
            </v-flex>
          </v-layout>
          <!--/Pie Charts-->
        </v-card>
      </v-flex>
    </v-layout>
    <!-- //=====ROW3===== -->
    <!-- =====ROW4===== -->
    <v-layout row wrap>
      <v-flex xs12 sm6 md6 fill-height class="mb-2">
        <v-card class="white">
          <v-layout row wrap>
            <v-flex xs12>
              <v-card-title primary-title class="white">
                <h6 class="primary--text text-xs-left mb-0">BALOR Trend Raw Data</h6>
              </v-card-title>
              <v-data-table
                v-bind:headers="headers1"
                :items="items1"
                hide-actions
                class="elevation-1"
              >
              <template slot="items" scope="props">
                <td>{{ props.item.name }}</td>
                <td class="text-xs-right">{{ props.item.calories }}</td>
                <td class="text-xs-right">{{ props.item.fat }}</td>
                <td class="text-xs-right">{{ props.item.carbs }}</td>
                <td class="text-xs-right">{{ props.item.protein }}</td>
                <td class="text-xs-right">{{ props.item.sodium }}</td>
                <td class="text-xs-right">{{ props.item.calcium }}</td>
                <td class="text-xs-right">{{ props.item.iron }}</td>
              </template>
            </v-data-table>
            </v-flex>
          </v-layout>
        </v-card>
      </v-flex>
      <v-flex xs12 sm6 md6 fill-height class="mb-2">
        <v-card class="white">
          <v-layout row wrap>
            <v-flex xs12>
              <v-card-title primary-title class="white">
                <h6 class="primary--text text-xs-left mb-0">BALOR Composition and Metrics Raw Data</h6>
              </v-card-title>
              <v-data-table
                v-bind:headers="headers2"
                :items="items2"
                hide-actions
                class="elevation-1"
              >
              <template slot="items" scope="props">
                <td>{{ props.item.name }}</td>
                <td class="text-xs-right">{{ props.item.calories }}</td>
                <td class="text-xs-right">{{ props.item.fat }}</td>
                <td class="text-xs-right">{{ props.item.carbs }}</td>
                <td class="text-xs-right">{{ props.item.protein }}</td>
                <td class="text-xs-right">{{ props.item.sodium }}</td>
                <td class="text-xs-right">{{ props.item.calcium }}</td>
                <td class="text-xs-right">{{ props.item.iron }}</td>
              </template>
            </v-data-table>
            </v-flex>
          </v-layout>
        </v-card>
      </v-flex>
    </v-layout>
    <!-- //=====ROW4===== -->

  </v-container>


</template>

<script>
  import BalorTrendLine from './balorCharts/BalorTrendLine.js'
  import PieCharts from './balorCharts/PieCharts.js'
  import noUiSlider from 'noUiSlider'
  import {summary} from './javascript/balor.service'

  export default {
    name: 'balorTrend',
    components: {
      BalorTrendLine,
      PieCharts
    },
    data () {
      return {
        headers1: [
          {
            text: 'Dessert (100g serving)',
            left: true,
            sortable: false,
            value: 'name'
          },
          { text: 'Calories', value: 'calories' },
          { text: 'Fat (g)', value: 'fat' },
          { text: 'Carbs (g)', value: 'carbs' },
          { text: 'Protein (g)', value: 'protein' },
          { text: 'Sodium (mg)', value: 'sodium' },
          { text: 'Calcium (%)', value: 'calcium' },
          { text: 'Iron (%)', value: 'iron' }
        ],
        items1: [
          {
            value: false,
            name: 'Frozen Yogurt',
            calories: 159,
            fat: 6.0,
            carbs: 24,
            protein: 4.0,
            sodium: 87,
            calcium: '14%',
            iron: '1%'
          },
          {
            value: false,
            name: 'Ice cream sandwich',
            calories: 237,
            fat: 9.0,
            carbs: 37,
            protein: 4.3,
            sodium: 129,
            calcium: '8%',
            iron: '1%'
          },
          {
            value: false,
            name: 'Eclair',
            calories: 262,
            fat: 16.0,
            carbs: 23,
            protein: 6.0,
            sodium: 337,
            calcium: '6%',
            iron: '7%'
          },
          {
            value: false,
            name: 'Cupcake',
            calories: 305,
            fat: 3.7,
            carbs: 67,
            protein: 4.3,
            sodium: 413,
            calcium: '3%',
            iron: '8%'
          },
          {
            value: false,
            name: 'Gingerbread',
            calories: 356,
            fat: 16.0,
            carbs: 49,
            protein: 3.9,
            sodium: 327,
            calcium: '7%',
            iron: '16%'
          },
          {
            value: false,
            name: 'Jelly bean',
            calories: 375,
            fat: 0.0,
            carbs: 94,
            protein: 0.0,
            sodium: 50,
            calcium: '0%',
            iron: '0%'
          },
          {
            value: false,
            name: 'Lollipop',
            calories: 392,
            fat: 0.2,
            carbs: 98,
            protein: 0,
            sodium: 38,
            calcium: '0%',
            iron: '2%'
          },
          {
            value: false,
            name: 'Honeycomb',
            calories: 408,
            fat: 3.2,
            carbs: 87,
            protein: 6.5,
            sodium: 562,
            calcium: '0%',
            iron: '45%'
          },
          {
            value: false,
            name: 'Donut',
            calories: 452,
            fat: 25.0,
            carbs: 51,
            protein: 4.9,
            sodium: 326,
            calcium: '2%',
            iron: '22%'
          },
          {
            value: false,
            name: 'KitKat',
            calories: 518,
            fat: 26.0,
            carbs: 65,
            protein: 7,
            sodium: 54,
            calcium: '12%',
            iron: '6%'
          }
        ],
        headers2: [
          {
            text: 'Dessert (100g serving)',
            left: true,
            sortable: false,
            value: 'name'
          },
          { text: 'Calories', value: 'calories' },
          { text: 'Fat (g)', value: 'fat' },
          { text: 'Carbs (g)', value: 'carbs' },
          { text: 'Protein (g)', value: 'protein' },
          { text: 'Sodium (mg)', value: 'sodium' },
          { text: 'Calcium (%)', value: 'calcium' },
          { text: 'Iron (%)', value: 'iron' }
        ],
        items2: [
          {
            value: false,
            name: 'Frozen Yogurt',
            calories: 159,
            fat: 6.0,
            carbs: 24,
            protein: 4.0,
            sodium: 87,
            calcium: '14%',
            iron: '1%'
          },
          {
            value: false,
            name: 'Ice cream sandwich',
            calories: 237,
            fat: 9.0,
            carbs: 37,
            protein: 4.3,
            sodium: 129,
            calcium: '8%',
            iron: '1%'
          },
          {
            value: false,
            name: 'Eclair',
            calories: 262,
            fat: 16.0,
            carbs: 23,
            protein: 6.0,
            sodium: 337,
            calcium: '6%',
            iron: '7%'
          },
          {
            value: false,
            name: 'Cupcake',
            calories: 305,
            fat: 3.7,
            carbs: 67,
            protein: 4.3,
            sodium: 413,
            calcium: '3%',
            iron: '8%'
          },
          {
            value: false,
            name: 'Gingerbread',
            calories: 356,
            fat: 16.0,
            carbs: 49,
            protein: 3.9,
            sodium: 327,
            calcium: '7%',
            iron: '16%'
          },
          {
            value: false,
            name: 'Jelly bean',
            calories: 375,
            fat: 0.0,
            carbs: 94,
            protein: 0.0,
            sodium: 50,
            calcium: '0%',
            iron: '0%'
          },
          {
            value: false,
            name: 'Lollipop',
            calories: 392,
            fat: 0.2,
            carbs: 98,
            protein: 0,
            sodium: 38,
            calcium: '0%',
            iron: '2%'
          },
          {
            value: false,
            name: 'Honeycomb',
            calories: 408,
            fat: 3.2,
            carbs: 87,
            protein: 6.5,
            sodium: 562,
            calcium: '0%',
            iron: '45%'
          },
          {
            value: false,
            name: 'Donut',
            calories: 452,
            fat: 25.0,
            carbs: 51,
            protein: 4.9,
            sodium: 326,
            calcium: '2%',
            iron: '22%'
          },
          {
            value: false,
            name: 'KitKat',
            calories: 518,
            fat: 26.0,
            carbs: 65,
            protein: 7,
            sodium: 54,
            calcium: '12%',
            iron: '6%'
          }
        ],
        retentionItems: [],
        sumItems: [],
        msg: 'Balor Trend Charts will go here!',
        custData: null,
        txnData: null,
        spendData: null,
        ratioLine: null,
        incomingJson: {},
        custBalorArray: [],
        txnBalorArray: [],
        spendBalorArray: [],
        tpArray: [],
        jobId: 'macysDemo',
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
      this.getResults()

      // this.createTrend()
    },
    methods: {
      getResults () {
        summary(this.jobId)
          .catch(err => {
            alert('Could not get Trend results. ' + err.message.toString())
          })
          .then((response) => {
            this.incomingJson = response.data
            console.log(this.incomingJson)
            this.createPies()
            this.createLines()
            this.createSlider()
            this.createSummary()
          })
      },
      createSummary () {
        this.sumItems.push({name: 'Min. Date', vals: this.jsonMsg.minDateBalor})
        this.sumItems.push({name: 'Max. Date', vals: this.jsonMsg.maxDateBalor})
        this.sumItems.push({name: 'Customer Base', vals: this.jsonMsg.totalCusts})
        this.sumItems.push({
          name: '% Customer - 1 Purchase',
          vals: (this.jsonMsg.singleVisit / this.jsonMsg.totalCusts * 100).toFixed(2) + '%'
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
              this.jsonMsg.timePeriods[tp].returnTxnAmt,
              this.jsonMsg.timePeriods[tp].reactTxnAmt],
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
              this.jsonMsg.timePeriods[this.tp - 1].returnTxnAmt,
              this.jsonMsg.timePeriods[this.tp - 1].reactTxnAmt],
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

  .inliner { display: inline-block; }

  .line_chart {
    position: relative;
    margin: 0 auto;
    height: 385px !important;
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

  .left_float { float: left; }

  .right_float { float: right; }

  .legend {
    display: inline-block;
    width: 38px;
    height: 12px;
    margin-right: 5px;
  }

  .legend_color1 { background-color: #D63809; }
  .legend_color2 { background-color: #8EAC1D; }
  .legend_color3 { background-color: #F7970E; }
  .legend_color4 { background-color: #0087AA; }
  .padR5{padding-right:5px;}

  .tabs__item{padding:0 !important;}
  .tabs__container a{padding:0;}

  /* Smartphones (portrait and landscape) ----------- */
  @media only screen and (min-device-width : 320px) and (max-device-width : 480px) {
  /* Styles */
    .pie_chart1 {
      height: 30vh !important;
    }
    .padT15_mob{ padding-top:15px;}
  }

</style>
