<template>
  <v-container fluid class="quantileProducts pl-3 pr-3 mb-3">
    <!-- =====ROW1===== -->
    <v-layout row wrap class="pt-0 mt-0">
      <v-flex xs12>
        <v-card class="pa-0 ma-0 grey lighten-2">
          <v-card-title primary-title class="primary">
            <h6 class="white--text text-xs-left mb-0">Product Index</h6>
          </v-card-title>
          <v-layout row wrap>
            <!--Dropdown1-->
            <v-flex xs12 sm2>
              <v-card flat class="pl-2 pr-2 pt-0 pb-0 grey lighten-2">
                <v-layout row wrap>
                  <v-flex xs12>
                    <div class="primary--text text-xs-left pl-0 pr-0 pb-0 pt-2">
                      Time Period:
                    </div>
                  </v-flex>
                  <v-flex xs12>
                      <!--SWAP OUT DROPDOWN-->
                      <v-select v-bind:items="tpArray"
                                v-model="tpSelect"
                                label="Select Time Period"
                                single-line
                                bottom
                                hide-details
                                v-on:input="selectTP()"
                                class="pl-1 pr-1 mt-1 mb-2 white elevation-1">
                      </v-select>
                      <!--//SWAP OUT DROPDOWN-->
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
                      Product Dept Desc:
                    </div>
                  </v-flex>
                  <v-flex xs12>
                      <v-select
                          v-bind:items="prodArray"
                          v-model="prodSelect"
                          label="Select Product Category"
                          single-line
                          bottom
                          multiple
                          hide-details
                          v-on:input="selectProds()"
                          class="pl-1 pr-1 mt-1 mb-2 white elevation-1">
                      </v-select>
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
                      Segment(s):
                    </div>
                  </v-flex>
                  <v-flex xs12>
                      <v-select
                          v-bind:items="segmentArray"
                          v-model="segSelect"
                          label="Select Segments"
                          multiple
                          single-line
                          bottom
                          hide-details
                          v-on:input="selectSegment()"
                          class="pl-1 pr-1 mt-1 mb-2 white elevation-1">
                      </v-select>
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
                      Top N Products:
                    </div>
                  </v-flex>
                  <v-flex xs8>
                      <v-text-field
                          name="topProducts"
                          label="How many products"
                          hide-details
                          id="topN"
                          v-model="topN"
                          v-on:keyup.enter.native="topProds()"
                          class="pl-1 pr-1 mt-1 mb-2 white elevation-1"></v-text-field>
                  </v-flex>
                  <v-flex xs4>
                    <v-btn light primary default v-on:click.native="topProds()" class="ma-0">Filter</v-btn>
                  </v-flex>
                </v-layout>
              </v-card>
            </v-flex>
            <!--//Dropdown4-->
            <!--Legend-->
            <v-flex xs12 sm4>
              <v-card flat class="pl-2 pr-2 grey lighten-2">
                <v-layout row wrap>
                  <!-- LEGEND -->
                  <v-flex xs12>
                    <div class="primary--text text-xs-left pl-0 pr-0 pb-1 pt-2">
                      Legend:<br />
                    </div>
                    <v-card class="white pt-1 pb-1 pl-2 pr-2 mr-2 mb-2">
                      <div class="legend accent"></div>
                      <div class="caption inliner padR5">Best in Class</div>
                      <div class="legend success"></div>
                      <div class="caption inliner padR5">Rising Stars</div>
                      <div class="legend info"></div>
                      <div class="caption inliner padR5">Middle of the road</div>
                      <div class="legend warning"></div>
                      <div class="caption inliner padR5">Lapsing</div>
                      <div class="legend error"></div>
                      <div class="caption inliner padR5">Deeply Lapsed</div>
                    </v-card>
                  </v-flex>
                  <!-- //LEGEND -->
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
      <!--+++++col1+++++-->
      <v-flex xs12 sm4>
        <v-card class="white pl-3 pr-3 pt-1 pb-1">
          <h6 class="primary--text text-xs-center pa-1 mb-0 subhead">Overall Product Share</h6>
          <div class="prod_share_container">
            <horizontal-chart :chart-data="overallBars" class="chart_height1"></horizontal-chart>
          </div>
        </v-card>
      </v-flex>
      <!--//+++++col1+++++-->
      <!--+++++col2+++++-->
      <v-flex xs12 sm8>
        <v-card class="white pl-3 pr-3 pt-1 pb-1">
          <h6 class="primary--text text-xs-center pa-1 mb-0">Product Index by Segment</h6>
          <v-layout wrap row>
            <div v-show="showBest" class="seg_sect" :style="segCount">
              <prod-index-chart :width="segChart" :chart-data="bestBars" class="chart_height1"></prod-index-chart>
            </div>
            <div v-show="showRising" class="seg_sect" :style="segCount">
              <prod-index-chart :width="segChart" :chart-data="risingBars" class="chart_height1"></prod-index-chart>
            </div>
            <div v-show="showMiddle" class="seg_sect" :style="segCount">
              <prod-index-chart :width="segChart" :chart-data="middleBars" class="chart_height1"></prod-index-chart>
            </div>
            <div v-show="showLapsing" class="seg_sect" :style="segCount">
              <prod-index-chart :width="segChart" :chart-data="lapsingBars" class="chart_height1"></prod-index-chart>
            </div>
            <div v-show="showDeeply" class="seg_sect" :style="segCount">
              <prod-index-chart :width="segChart" :chart-data="deeplyBars" class="chart_height1"></prod-index-chart>
            </div>
          </v-layout>
        </v-card>
      </v-flex>
      <!--//+++++col2+++++-->
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
        topN: null,
        jobId: 'invertedLifecycle',
        labels: [],
        spendPer: [],
        bestIndex: [],
        risingIndex: [],
        middleIndex: [],
        lapsingIndex: [],
        deeplyIndex: [],
        labelsSelected: [],
        spendPerSelected: [],
        bestIndexSelected: [],
        risingIndexSelected: [],
        middleIndexSelected: [],
        lapsingIndexSelected: [],
        deeplyIndexSelected: [],
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
        showDeeply: true,
        styleObject: { width: '20%' },
        segChart: '100%'
      }
    },
    computed: {
      jsonMsg: function () {
        return this.incomingJson.data
      },
      segCount: function () {
        if (this.segSelect.includes('All') || this.segSelect.length === 5) {
          this.styleObject.width = '20%'
        } else if (this.segSelect.length === 4) {
          this.styleObject.width = '25%'
        } else if (this.segSelect.length === 3) {
          this.styleObject.width = '33%'
        } else if (this.segSelect.length === 2) {
          this.styleObject.width = '50%'
        } else if (this.segSelect.length === 1) {
          this.styleObject.width = '100%'
        }
        return this.styleObject
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
            this.parseJson()
            this.createOverallBars()
            this.createSegmentBars()
          })
      },

      parseJson () {
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

        // set selected to all for initial load
        this.labelsSelected = tempLabels
        this.spendPerSelected = tempSpendPer
        this.bestIndexSelected = tempBestIndex
        this.risingIndexSelected = tempRisingIndex
        this.middleIndexSelected = tempMiddleIndex
        this.lapsingIndexSelected = tempLapsingIndex
        this.deeplyIndexSelected = tempDeeplyIndex

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
          labels: this.labelsSelected,
          datasets: [
            {
              data: this.spendPerSelected,
              label: 'Spend Percent',
              backgroundColor: '#8EAC1D'
            }
          ]
        }
      },

      createSegmentBars () {
        this.bestBars = {
          type: 'horizontalBar',
          labels: this.labelsSelected,
          datasets: [
            {
              data: this.bestIndexSelected,
              label: 'Best Spend Index',
              backgroundColor: '#003947'
            }
          ]
        }

        this.risingBars = {
          type: 'horizontalBar',
          labels: this.labelsSelected,
          datasets: [
            {
              data: this.risingIndexSelected,
              label: 'Rising Spend Index',
              backgroundColor: '#8EAC1D'
            }
          ]
        }

        this.middleBars = {
          type: 'horizontalBar',
          labels: this.labelsSelected,
          datasets: [
            {
              data: this.middleIndexSelected,
              label: 'Middle Spend Index',
              backgroundColor: '#0087AA'
            }
          ]
        }

        this.lapsingBars = {
          type: 'horizontalBar',
          labels: this.labelsSelected,
          datasets: [
            {
              data: this.lapsingIndexSelected,
              label: 'Lapsing Spend Index',
              backgroundColor: '#F7970E'
            }
          ]
        }

        this.deeplyBars = {
          type: 'horizontalBar',
          labels: this.labelsSelected,
          datasets: [
            {
              data: this.deeplyIndexSelected,
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

        this.selectProds()
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
      },

      selectProds () {
        var tempLabels = []
        var tempSpendPer = []
        var tempBestIndex = []
        var tempRisingIndex = []
        var tempMiddleIndex = []
        var tempLapsingIndex = []
        var tempDeeplyIndex = []

        if (this.prodSelect.includes('All')) {
          this.labelsSelected = this.labels
          this.spendPerSelected = this.spendPer
          this.bestIndexSelected = this.bestIndex
          this.risingIndexSelected = this.risingIndex
          this.middleIndexSelected = this.middleIndex
          this.lapsingIndexSelected = this.lapsingIndex
          this.deeplyIndexSelected = this.deeplyIndex
        } else {
          console.log(this.prodSelect)
          for (let i = 0; i < this.labels.length; i++) {
            if (this.prodSelect.includes(this.labels[i])) {
              tempLabels.push(this.labels[i])
              tempSpendPer.push(this.spendPer[i])
              tempBestIndex.push(this.bestIndex[i])
              tempRisingIndex.push(this.risingIndex[i])
              tempMiddleIndex.push(this.middleIndex[i])
              tempLapsingIndex.push(this.lapsingIndex[i])
              tempDeeplyIndex.push(this.deeplyIndex[i])
            }
          }
          this.labelsSelected = tempLabels
          this.spendPerSelected = tempSpendPer
          this.bestIndexSelected = tempBestIndex
          this.risingIndexSelected = tempRisingIndex
          this.middleIndexSelected = tempMiddleIndex
          this.lapsingIndexSelected = tempLapsingIndex
          this.deeplyIndexSelected = tempDeeplyIndex
        }

        this.createOverallBars()
        this.createSegmentBars()
      },

      topProds () {
        console.log('button clicked!')

        this.labelsSelected = this.labels.slice(0, this.topN)
        this.spendPerSelected = this.spendPer.slice(0, this.topN)
        this.bestIndexSelected = this.bestIndex.slice(0, this.topN)
        this.risingIndexSelected = this.risingIndex.slice(0, this.topN)
        this.middleIndexSelected = this.middleIndex.slice(0, this.topN)
        this.lapsingIndexSelected = this.lapsingIndex.slice(0, this.topN)
        this.deeplyIndexSelected = this.deeplyIndex.slice(0, this.topN)

        this.prodSelect = this.labelsSelected

        this.createOverallBars()
        this.createSegmentBars()
      }
    }
  }

</script>

<style scoped>
  .seg_sect {
    width: 20%;
    height: 100%;
    display: table-cell;
    position: relative;
  }

  .chart_height1 {
    width: 100% !important;
    height: 74vh !important;
  }

  canvas, .prod_share_container { width:100% !important; }

</style>
