<template>
  <v-container fluid class="quantileProducts pl-3 pr-3 mb-3">
    <!-- ASHA STUFF HERE -->

    <!--// ASHA STUFF HERE -->

    <!-- =====ROW1===== -->
    <v-layout row wrap class="pt-0 mt-0">
      <v-flex xs12>
        <v-card class="pa-0 ma-0 grey lighten-2">
          <v-card-title primary-title class="primary">
            <h6 class="white--text text-xs-left mb-0">Title Goes Here</h6>
          </v-card-title>
          <v-layout row wrap>
            <!--Dropdown1-->
            <v-flex xs12 sm2>
              <v-card flat class="pl-2 pr-2 pt-0 pb-0 grey lighten-2">
                <v-layout row wrap>
                  <v-flex xs12>
                    <div class="primary--text text-xs-left pl-0 pr-0 pb-0 pt-2">
                      Select Time<br/>Period:
                    </div>
                  </v-flex>
                  <v-flex xs12>
                    <v-card class="white pa-0">
                      <!--SWAP OUT DROPDOWN-->
                      <v-select v-bind:items="tpArray"
                                v-model="tpSelect"
                                label="Select Time Period"
                                single-line
                                bottom
                                v-on:input="selectTP()"
                                class="pl-1 pr-1 m-0">
                      </v-select>
                      <!--//SWAP OUT DROPDOWN-->
                    </v-card>
                  </v-flex>
                </v-layout>
              </v-card>
            </v-flex>
            <!--//Dropdown1-->
            <!--Dropdown2-->
            <v-flex xs12 sm2>
              <v-card flat class="pl-2 pr-2 grey lighten-2">
                <v-layout row wrap>
                  <v-flex xs12>
                    <div class="primary--text text-xs-left pl-0 pr-0 pb-0 pt-2">
                      Select Product<br/> Department Description:
                    </div>
                  </v-flex>
                  <v-flex xs12>
                    <v-card class="white">
                      <v-select
                          v-bind:items="prodArray"
                          v-model="prodSelect"
                          label="Select Product Category"
                          single-line
                          bottom
                          multiple
                          v-on:input="selectProd()"
                          class="pl-1 pr-1 m-0">
                      </v-select>
                    </v-card>
                  </v-flex>
                </v-layout>
              </v-card>
            </v-flex>
            <!--//Dropdown2-->
            <!--Dropdown3-->
            <v-flex xs12 sm2>
              <v-card flat class="pl-2 pr-2 grey lighten-2">
                <v-layout row wrap>
                  <v-flex xs12>
                    <div class="primary--text text-xs-left pl-0 pr-0 pb-0 pt-2">
                      Select<br/>Segment(s):
                    </div>
                  </v-flex>
                  <v-flex xs12>
                    <v-card class="white">
                      <v-select
                          v-bind:items="segmentArray"
                          v-model="segSelect"
                          label="Select Segments"
                          multiple
                          single-line
                          bottom
                          v-on:input="selectSegment()"
                          class="pl-1 pr-1 m-0">
                      </v-select>
                    </v-card>
                  </v-flex>
                </v-layout>
              </v-card>
            </v-flex>
            <!--//Dropdown3-->
            <!--Dropdown4-->
            <v-flex xs12 sm2>
              <v-card flat class="pl-2 pr-2 grey lighten-2">
                <v-layout row wrap>
                  <v-flex xs12>
                    <div class="primary--text text-xs-left pl-0 pr-0 pb-0 pt-2">
                      Select Top<br/>N Products:
                    </div>
                  </v-flex>
                  <v-flex xs12>
                    <v-card class="white">
                      <v-text-field
                          name="topProducts"
                          label="How many products"
                          id="topN"></v-text-field>
                    </v-card>
                  </v-flex>
                </v-layout>
              </v-card>
            </v-flex>
            <!--//Dropdown4-->
            <!--Legend-->
            <v-flex xs12 sm4>
              <v-card flat class="pl-2 pr-2 grey lighten-2">
                <v-layout row wrap>
                  <v-flex xs12>
                    <div class="primary--text text-xs-center pl-0 pr-0 pb-0 pt-2">
                      Segment Legend:
                    </div>
                  </v-flex>
                </v-layout>
              </v-card>
            </v-flex>
            <!--//Legend-->
          </v-layout>
        </v-card>
      </v-flex>
    </v-layout>
    <!-- //=====ROW1===== -->
    <!-- =====ROW2===== -->
    <v-layout wrap row>
      <v-flex xs12 class="pt-0 mt-0">
        <v-card class="pl-3 pr-3 pt-1 pb-1">
          <div class="title primary--text text-xs-center pa-1"><em>Product Share Index during Time Period <span
              class="grey--text darken-2">1</span> for Segments <span class="grey--text darken-2">All</span></span></em>
          </div>
        </v-card>
      </v-flex>
    </v-layout>
    <!-- //=====ROW2===== -->
    <!-- =====ROW3===== -->
    <v-layout wrap row class="mt-3">
      <v-flex xs4>
        <v-card class="white pl-3 pr-3 pt-1 pb-1">
          <h6 class="primary--text text-xs-center pa-1 mb-0 subhead">Overall Product Share</h6>
          <!--<img src="http://via.placeholder.com/1050x480?text=Chart" width="100%" height="100%" style="height:475px;">-->
          <horizontal-chart :chart-data="overallBars" class="chart_height1"></horizontal-chart>
        </v-card>
      </v-flex>
      <v-flex xs8>
        <v-card class="white pl-3 pr-3 pt-1 pb-1">
          <h6 class="primary--text text-xs-center pa-1 mb-0">Product Index by Segment</h6>
          <v-layout wrap row>
            <div v-if="showBest" class="seg_sect">
              <prod-index-chart :chart-data="bestBars" class="chart_height1"></prod-index-chart>
            </div>
            <div v-if="showRising" class="seg_sect">
              <prod-index-chart :chart-data="risingBars" class="chart_height1"></prod-index-chart>
            </div>
            <div v-if="showMiddle" class="seg_sect">
              <prod-index-chart :chart-data="middleBars" class="chart_height1"></prod-index-chart>
            </div>
            <div v-if="showLapsing" class="seg_sect">
              <prod-index-chart :chart-data="lapsingBars" class="chart_height1"></prod-index-chart>
            </div>
            <div v-if="showDeeply" class="seg_sect">
              <prod-index-chart :chart-data="deeplyBars" class="chart_height1"></prod-index-chart>
            </div>
          </v-layout>
          <!--<img src="http://via.placeholder.com/1050x480?text=Chart" width="100%" height="100%" style="height:475px;">-->
        </v-card>
      </v-flex>
    </v-layout>
    <!-- //=====ROW3===== -->
  </v-container>
</template>

<script>
  import {product} from './javascript/lifecycle.service'
  import HorizontalChart from './balorCharts/HorizontalChart'
  import prodIndexChart from './lifecycleCharts/prodIndexChart'

  export default {
    name: 'lifecycleProducts',
    components: {
      HorizontalChart,
      prodIndexChart
    },
    data () {
      return {
        incomingJson: {},
        tpSelect: 1,
        tpArray: [],
        prods: [],
        segmentArray: ['All', 'Best in Class', 'Rising Stars', 'Middle of the Road', 'Lapsing', 'Deeply Lapsed'],
        segSelect: ['All'],
        prodArray: [],
        prodSelect: ['All'],
        jobId: 'testLifecycle',
        labels: [],
        spendPer: [],
        bestIndex: [],
        risingIndex: [],
        middleIndex: [],
        lapsingIndex: [],
        deeplyIndex: [],
        overallBars: {},
        bestBars: {},
        middleBars: {},
        risingBars: {},
        lapsingBars: {},
        deeplyBars: {},
        sortedProds: [],
        showBest: true,
        showRising: true,
        showMiddle: true,
        showLapsing: true,
        showDeeply: true
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
        product(this.jobId)
          .catch(err => {
            alert('Could not get Lifecycle Product results. ' + err.message.toString())
          })
          .then((response) => {
            this.incomingJson = response.data
            console.log(this.incomingJson)
            this.parseJson()
            this.createOverallBars()
            this.createSegmentBars()
          })
      },

      parseJson () {
        console.log(this.jsonMsg)

        var tps = this.jsonMsg.timePeriods

        var tempLabels = []
        var tempSpendPer = []
        var tempBestIndex = []
        var tempRisingIndex = []
        var tempMiddleIndex = []
        var tempLapsingIndex = []
        var tempDeeplyIndex = []
        var tempTpArray = []
        var tempProdArray = []

        tps.sort(function (a, b) {
          return b.prodTotalSales - a.prodTotalSales
        })

        this.sortedProds = tps
        for (let i = 0; i < tps.length; i++) {
          if (tps[i].timePeriod === this.tpSelect) {
            tempLabels.push(tps[i].productCat)
            tempSpendPer.push(tps[i].prodPercentSales)
            tempBestIndex.push(tps[i].bestIndex)
            tempRisingIndex.push(tps[i].risingIndex)
            tempMiddleIndex.push(tps[i].middleIndex)
            tempLapsingIndex.push(tps[i].lapsingIndex)
            tempDeeplyIndex.push(tps[i].deeplyIndex)
          }
        }

        tempProdArray = JSON.parse(JSON.stringify(tempLabels))
        tempProdArray.sort()
        tempProdArray.unshift('All')
        this.labels = tempLabels
        this.prodArray = tempProdArray
        this.spendPer = tempSpendPer
        this.bestIndex = tempBestIndex
        this.risingIndex = tempRisingIndex
        this.middleIndex = tempMiddleIndex
        this.lapsingIndex = tempLapsingIndex
        this.deeplyIndex = tempDeeplyIndex

        for (let i = 0; i < tps.length; i++) {
          if (tempTpArray.includes(tps[i].timePeriod)) {

          } else {
            tempTpArray.push(tps[i].timePeriod)
          }
        }

        this.tpArray = tempTpArray
      },

      createOverallBars () {
        this.overallBars = {
          type: 'horizontalBar',
          labels: this.labels,
          datasets: [
            {
              data: this.spendPer,
              label: 'Spend Percent',
              backgroundColor: '#8EAC1D'
            }
          ]
        }
      },

      createSegmentBars () {
        this.bestBars = {
          type: 'horizontalBar',
          labels: this.labels,
          datasets: [
            {
              data: this.bestIndex,
              label: 'Best Spend Index',
              backgroundColor: '#003947'
            }
          ]
        }

        this.risingBars = {
          type: 'horizontalBar',
          labels: this.labels,
          datasets: [
            {
              data: this.risingIndex,
              label: 'Rising Spend Index',
              backgroundColor: '#8EAC1D'
            }
          ]
        }

        this.middleBars = {
          type: 'horizontalBar',
          labels: this.labels,
          datasets: [
            {
              data: this.middleIndex,
              label: 'Middle Spend Index',
              backgroundColor: '#0087AA'
            }
          ]
        }

        this.lapsingBars = {
          type: 'horizontalBar',
          labels: this.labels,
          datasets: [
            {
              data: this.lapsingIndex,
              label: 'Lapsing Spend Index',
              backgroundColor: '#F7970E'
            }
          ]
        }

        this.deeplyBars = {
          type: 'horizontalBar',
          labels: this.labels,
          datasets: [
            {
              data: this.deeplyIndex,
              label: 'Deeply Spend Index',
              backgroundColor: '#D63809'
            }
          ]
        }
      },

      selectTP () {
        var tempLabels = []
        var tempSpendPer = []
        var tempBestIndex = []
        var tempRisingIndex = []
        var tempMiddleIndex = []
        var tempLapsingIndex = []
        var tempDeeplyIndex = []

        for (let i = 0; i < this.sortedProds.length; i++) {
          if (this.sortedProds[i].timePeriod === this.tpSelect) {
            tempLabels.push(this.sortedProds[i].productCat)
            tempSpendPer.push(this.sortedProds[i].prodPercentSales)
            tempBestIndex.push(this.sortedProds[i].bestIndex)
            tempRisingIndex.push(this.sortedProds[i].risingIndex)
            tempMiddleIndex.push(this.sortedProds[i].middleIndex)
            tempLapsingIndex.push(this.sortedProds[i].lapsingIndex)
            tempDeeplyIndex.push(this.sortedProds[i].deeplyIndex)
          }
        }

        this.labels = tempLabels
        this.spendPer = tempSpendPer
        this.bestIndex = tempBestIndex
        this.risingIndex = tempRisingIndex
        this.middleIndex = tempMiddleIndex
        this.lapsingIndex = tempLapsingIndex
        this.deeplyIndex = tempDeeplyIndex

        this.createOverallBars()
        this.createSegmentBars()
      },

      selectSegment () {
        this.showBest = false
        this.showRising = false
        this.showMiddle = false
        this.showLapsing = false
        this.showDeeply = false

        if (this.segSelect.includes('All')) {
          this.showBest = true
          this.showRising = true
          this.showMiddle = true
          this.showLapsing = true
          this.showDeeply = true
        } else {
          if (this.segSelect.includes('Best in Class')) {
            this.showBest = true
          }
          if (this.segSelect.includes('Rising Stars')) {
            this.showRising = true
          }
          if (this.segSelect.includes('Middle of the Road')) {
            this.showMiddle = true
          }
          if (this.segSelect.includes('Lapsing')) {
            this.showLapsing = true
          }
          if (this.segSelect.includes('Deeply Lapsed')) {
            this.showDeeply = true
          }
        }
      }
    }
  }

</script>

<style scoped>
  .seg_sect {
    width: 20%;
    display: inline-block;
  }

  .chart_height1 {
    height: 74vh !important;
  }

</style>
