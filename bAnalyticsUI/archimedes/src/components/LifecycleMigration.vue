<template>
  <v-container fluid class="quantileProducts pl-3 pr-3 mb-3">
    <!-- =====ROW1===== -->
    <v-layout row wrap class="pt-0 mt-0">
      <v-flex xs12>
        <v-card class="pa-0 ma-0 grey lighten-2">
          <v-card-title primary-title class="primary">
            <h6 class="white--text text-xs-left mb-0">Period-over-Period Segment Migration</h6>
          </v-card-title>
          <v-layout row wrap>
            <!--Dropdown1-->
            <v-flex xs12 sm3 md2>
              <v-card flat class="pl-2 pr-2 pt-0 pb-0 grey lighten-2">
                <v-layout row wrap>
                  <v-flex xs12>
                    <div class="primary--text text-xs-left pl-0 pr-0 pb-0 pt-2">
                      Prior Period<br/> for Analysis:
                    </div>
                  </v-flex>
                  <v-flex xs12>
                      <v-select v-bind:items="tpArray"
                                v-model="tpSelect"
                                label="Select Time Period"
                                single-line
                                bottom
                                hide-details
                                v-on:input="selectTP()"
                                class="pl-1 pr-1 mt-1 mb-2 white elevation-1">
                      </v-select>
                  </v-flex>
                </v-layout>
              </v-card>
            </v-flex>
            <!--//Dropdown1-->
            <!-- don't filter by segment currently
            Dropdown2
            <v-flex xs12 sm2>
              <v-card flat class="pl-2 pr-2 grey lighten-2">
                <v-layout row wrap>
                  <v-flex xs12>
                    <div class="primary--text text-xs-left pl-0 pr-0 pb-0 pt-2">
                      Prior<br/>Segment:
                    </div>
                  </v-flex>
                  <v-flex xs12>
                      SWAP OUT DROPDOWN
                      <v-select
                          v-bind:items="segments"
                          v-model="priorSegSel"
                          label="Select Segment"
                          mulitple
                          single-line
                          bottom
                          hide-details
                          v-on:input="selectPriorSegment()"
                          class="pl-1 pr-1 mt-1 mb-2 white elevation-1">
                      </v-select>
                      //SWAP OUT DROPDOWN
                  </v-flex>
                </v-layout>
              </v-card>
            </v-flex>
            //Dropdown2
            Dropdown3
            <v-flex xs12 sm2>
              <v-card flat class="pl-2 pr-2 grey lighten-2">
                <v-layout row wrap>
                  <v-flex xs12>
                    <div class="primary--text text-xs-left pl-0 pr-0 pb-0 pt-2">
                      Post<br/>Segment:
                    </div>
                  </v-flex>
                  <v-flex xs12>
                      SWAP OUT DROPDOWN
                      <v-select
                          v-bind:items="segments"
                          v-model="postSegSel"
                          label="Select Segment"
                          single-line
                          mulitple
                          bottom
                          hide-details
                          v-on:input="selectPostSegment()"
                          class="pl-1 pr-1 mt-1 mb-2 white elevation-1">
                      </v-select>
                      //SWAP OUT DROPDOWN
                  </v-flex>
                </v-layout>
              </v-card>
            </v-flex>
            //Dropdown3
            maybe readd the drop downs if its a deal breaker later -->
            <!--Legend-->
            <v-flex xs12 sm9 md7>
              <v-card flat class="pl-2 pr-2 grey lighten-2">
                <v-layout row wrap>
                  <!-- LEGEND -->
                  <v-flex xs12>
                    <div class="primary--text text-xs-left pl-0 pr-0 pb-1 pt-2">
                      Legend:<br/><br/>
                    </div>
                    <div class="legend_contain white elevation-1">
                      <div class="inliner">
                        <div class="legend accent"></div>
                        <div class="caption inliner padR5">Best in Class</div>
                      </div>
                      <div class="inliner">
                        <div class="legend success"></div>
                        <div class="caption inliner padR5">Rising Stars</div>
                      </div>
                      <div class="inliner">
                        <div class="legend info"></div>
                        <div class="caption inliner padR5">Middle of the road</div>
                      </div>
                      <div class="inliner">
                        <div class="legend warning"></div>
                        <div class="caption inliner padR5">Lapsing</div>
                      </div>
                      <div class="inliner">
                        <div class="legend error"></div>
                        <div class="caption inliner padR5">Deeply Lapsed</div>
                      </div>
                    </div>
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
          <div class="title primary--text text-xs-center pa-1"><em>Period-over-Period Segment Migration from Prior
            Segment(s)<span class="grey--text darken-2">All</span> to Post Segment(s)<span class="grey--text darken-2">All</span>
            during Period <span class="grey--text darken-2">1</span></em></div>
        </v-card>
      </v-flex>
    </v-layout>
    <!-- //=====ROW2===== -->
    <!-- =====ROW3===== -->
    <v-layout wrap row>
      <v-flex xs12 class="pt-0 mt-0">
        <v-card class="white pa-3">
          <v-layout wrap row>

            <!--+++++col1+++++-->
            <v-flex xs12 sm12 md8>
              <!-- SANKEY CHART -->
              <!--<div v-show="showSankey" class="y_axis1 text-xs-center caption">
                y-axis1
              </div>-->
              <div
                v-show="showSankey"
                class="scaling-svg-container"
                style="padding-bottom: 92% /* 100% * 55/60 */">
                  <svg
                    width="1060"
                    height="1000"
                    id="sankeySvg"
                    class="scaling-svg"
                    viewBox="0 10 1050 1000">
                    <text class="y_axis1 subheading" x="-56%" y="-7" fill="rgba(53,64,82,0.87)">Prior Period Segment</text>
                    <text class="y_axis2 subheading" x="45%" y="-1057" fill="rgba(53,64,82,0.87)">Post Period Segment</text>
                    <text class="x_axis subheading" x="33%" y="1000" fill="rgba(53,64,82,0.87)">Period-over-Period Customer Migration across Segments</text>
                  </svg>
              </div>
              <div v-show="showError">
                I need something to go here that explains that the last timePeriod does not have migration data due to it being the baseline for all migrations.
              </div>
              <!-- //SANKEY CHART -->
            </v-flex>
            <!--//+++++col1+++++-->

            <!--+++++col2+++++-->
            <v-flex xs12 sm12 md4 class="pr-4">
              <!--table-row-->
              <v-layout wrap row>
                <v-card class="white w_100">
                  <v-card-title primary-title class="white">
                    <h6 class="primary--text text-xs-center mb-0">Prior Period Segment Retention Rate</h6>
                  </v-card-title>
                  <v-divider class="primary pb-0"></v-divider>
                  <v-flex xs12 fill-height>
                    <v-layout row wrap>
                      <v-flex xs12 class="pt-0 mt-0">
                        <v-card class="accent mt-2 mb-2 height_bars1">
                          <v-card-text class="accent white--text height_bars2">
                            <div class="subheading">Best in Class <span>{{ bestRet }}</span>
                            </div>
                          </v-card-text>
                        </v-card>
                        <v-card class="success mb-2 height_bars1">
                          <v-card-text class="success white--text height_bars2">
                            <div class="subheading">Rising Stars <span>{{ risingRet }}</span>
                            </div>
                          </v-card-text>
                        </v-card>
                        <v-card class="info mb-2 height_bars1">
                          <v-card-text class="info white--text height_bars2">
                            <div class="subheading">Middle of the Road <span>{{ middleRet }}</span>
                            </div>
                          </v-card-text>
                        </v-card>
                        <v-card class="warning mb-2 height_bars1">
                          <v-card-text class="warning white--text height_bars2">
                            <div class="subheading">Lapsing <span>{{ lapsingRet }}</span></div>
                          </v-card-text>
                        </v-card>
                        <v-card class="error mb-2 height_bars1">
                          <v-card-text class="error white--text height_bars2">
                            <div class="subheading">Deeply Lapsed <span>{{ deeplyRet }}</span>
                            </div>
                          </v-card-text>
                        </v-card>
                      </v-flex>
                    </v-layout>
                  </v-flex>
                </v-card>
              </v-layout>
              <!--//table-row-->
              <!--chart-row-->
              <v-layout wrap row>
                <v-card class="white mt-3 pa-0 w_100">
                  <v-flex xs12 fill-height>
                    <!-- =====ROW1===== -->
                    <v-layout row wrap class="pt-0 mt-0">
                      <v-flex xs12 class="pa-0 ma-0">
                        <v-card class="pa-0 ma-0 grey lighten-2">
                          <v-card-title primary-title class="white">
                            <h6 class="primary--text text-xs-left mb-0">Post Period Segment Composition</h6>
                          </v-card-title>
                          <v-layout row wrap>
                            <!--Dropdown1-->
                            <v-flex xs12>
                              <v-card flat class="pl-2 pr-2 pt-0 pb-0 grey lighten-2">
                                <v-layout row wrap>
                                  <v-flex xs12>
                                    <div class="primary--text text-xs-left pl-0 pr-0 pb-0 pt-2">
                                      Select Post Period Segment:
                                    </div>
                                  </v-flex>
                                  <v-flex xs12 sm7>
                                      <v-select v-bind:items="segments"
                                                v-model="postSegComp"
                                                label="Select Segments"
                                                single-line
                                                bottom
                                                hide-details
                                                v-on:input="selPostPeriodComp()"
                                                class="pl-1 pr-1 mt-1 mb-2 white elevation-1">
                                      </v-select>
                                  </v-flex>
                                  <v-flex xs12 sm5>
                                    <v-checkbox
                                      label="Show new"
                                      v-model="showNew"
                                      class="primary--text pt-2 pb-1 pl-0 pr-0 ma-0"
                                      hide-details
                                      @change="selPostPeriodComp()"></v-checkbox>
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
                    <v-layout row wrap class="pt-0 mt-0">
                      <v-flex xs12 class="pa-0 ma-0">
                        <div class="mt-3 w_100">
                          <bar-chart :chart-data="barData"></bar-chart>
                        </div>
                      </v-flex>
                    </v-layout>
                    <!-- //=====ROW2===== -->
                  </v-flex>
                </v-card>
              </v-layout>
              <!--//chart-row-->
            </v-flex>
            <!--//+++++col2+++++-->
          </v-layout>
        </v-card>
      </v-flex>
    </v-layout>
    <!-- //=====ROW3===== -->
  </v-container>
</template>

<script>
  import {migration} from './javascript/lifecycle.service'
  import * as d3 from 'd3'
  import {sankey, sankeyLinkHorizontal} from 'd3-sankey'
  import BarChart from './balorCharts/BarChart'

  // [JF] initiates numeral.js library in this vue component. must use together with numeral() or numeral().format()
  var numeral = require('numeral')

  export default {
    name: 'lifecycleMigration',
    components: {
      BarChart
    },
    data () {
      return {
        incomingJson: {},
        tpSelect: 1,
        tpArray: [],
        priorSegSel: ['All'],
        postSegSel: ['All'],
        postSegComp: 'Best in Class',
        segments: ['All', 'Best in Class', 'Rising Stars', 'Middle of the Road', 'Lapsing', 'Deeply Lapsed'],
        quantArray: [],
        nodes: [],
        tpLinks: [],
        links: [],
        sumItems: [],
        sankeyData: [],
        barData: {},
        allBestPost: [],
        allRisingPost: [],
        allMiddlePost: [],
        allLapsingPost: [],
        allDeeplyPost: [],
        selBestPost: [],
        selRisingPost: [],
        selMiddlePost: [],
        selLapsingPost: [],
        selDeeplyPost: [],
        barLabels: ['Best in Class', 'Rising Stars', 'Middle of the Road', 'Lapsing', 'Deeply Lapsed', 'New'],
        bestMigArray: [],
        risingMigArray: [],
        middleMigArray: [],
        lapsingMigArray: [],
        deeplyMigArray: [],
        bestRet: 0,
        risingRet: 0,
        middleRet: 0,
        lapsingRet: 0,
        deeplyRet: 0,
        showSankey: true,
        showError: false,
        stankey: null,
        showNew: true
      }
    },
    computed: {
      jsonMsg: function () {
        return this.incomingJson.data
      },
      jobId: function () {
        return this.$store.state.jobKey
      },
      jobApp: function () {
        return this.$store.state.jobApp
      }
    },
    mounted () {
      if (this.jobApp === 'Lifecycle' || this.jobApp === 'lifecycle') {
        this.$store.commit('switchApp', {module: 'Lifecycle'})
        this.getResults()
      } else {
        alert('Please select a Core Lifecycle job from Job History')
        this.$router.push('/Lifecycle/')
      }
    },
    methods: {
      getResults () {
        migration(this.jobId)
          .catch(err => {
            alert('Could not get Lifecycle Migration results. ' + err.message.toString())
          })
          .then((response) => {
            this.incomingJson = response.data
            console.log(this.incomingJson)
            this.createSankeyJson()
            this.parseBarData()
            this.buildSankey()
          })
      },

      createSankeyJson () {
        console.log(this.jsonMsg)
        this.nodes = [
          {'node': 0, 'name': 'Best in Class'},
          {'node': 1, 'name': 'Rising Stars'},
          {'node': 2, 'name': 'Middle of the Road'},
          {'node': 3, 'name': 'Lapsing'},
          {'node': 4, 'name': 'Deeply Lapsed'},
          {'node': 5, 'name': 'Best in Class'},
          {'node': 6, 'name': 'Rising Stars'},
          {'node': 7, 'name': 'Middle of the Road'},
          {'node': 8, 'name': 'Lapsing'},
          {'node': 9, 'name': 'Deeply Lapsed'}
        ]

        var tempTpLink = []

        for (let i = 0; i < this.jsonMsg.timePeriods.length; i++) {
          var tempLinks = []
          for (let j = 0; j < this.jsonMsg.timePeriods[i].migrationData.length; j++) {
            let from = 0
            let to = 0
            switch (this.jsonMsg.timePeriods[i].migrationData[j].fromSegment) {
              case 'Best in Class':
                from = 0
                break
              case 'Rising Stars':
                from = 1
                break
              case 'Middle of the Road':
                from = 2
                break
              case 'Lapsing':
                from = 3
                break
              case 'Deeply Lapsed':
                from = 4
                break
            }
            switch (this.jsonMsg.timePeriods[i].migrationData[j].currentSegment) {
              case 'Best in Class':
                to = 5
                break
              case 'Rising Stars':
                to = 6
                break
              case 'Middle of the Road':
                to = 7
                break
              case 'Lapsing':
                to = 8
                break
              case 'Deeply Lapsed':
                to = 9
                break
            }
            tempLinks.push(
              {
                'source': from,
                'target': to,
                'value': this.jsonMsg.timePeriods[i].migrationData[j].migrationCount
              }
            )
          }
          tempTpLink.push({
            'nodes': this.nodes,
            'links': tempLinks
          })
        }
        this.tpLinks = tempTpLink
      },

      buildSankey () {
        console.log('starting sankey build')

        const colorScheme = {
          'Best in Class': '#003947',
          'Rising Stars': '#8EAC1D',
          'Middle of the Road': '#0087AA',
          'Lapsing': '#F7970E',
          'Deeply Lapsed': '#D63809'
        }

        var units = 'Customers'

        var svg = d3.select('#sankeySvg')
          .append('svg')
          .attr('id', 'stankey')
        var width = 1055
        var height = 990

        var formatNumber = d3.format(',.0f')    // decimal places
        var format = function (d) {
          return formatNumber(d) + ' ' + units
        }

        const data = this.tpLinks[this.tpSelect - 1]
        this.stankey = sankey()
          .nodeWidth(45)
          .nodePadding(5)
          .extent([[1, 1], [width - 1, height - 6]])
          .iterations(0)

        var link = svg
          .append('g')
          .attr('class', 'links')
          .attr('fill', 'none')
          .attr('stroke-opacity', 0.5)
          .selectAll('path')

        var node = svg.append('g')
          .attr('class', 'nodes')
          .attr('fill', 'none')
          .attr('font-family', 'sans-serif')
          .attr('font-size', 10)
          .selectAll('g')

        // this is instead of any var path = sankey.link, same with sankeyLinkHorizontal
        this.stankey(data)

        link = link.data(data.links)
          .enter().append('path')
          .attr('d', sankeyLinkHorizontal())
          .style('stroke-width', function (d) {
            return Math.max(1, d.width)
          })
          .attr('stroke', function (d) {
            return colorScheme[d.source.name]
          })

        link.append('title')
          .text(function (d) {
            return d.source.name + ' → ' +
              d.target.name + '\n' + format(d.value)
          })

        node = node
          .data(data.nodes)
          .enter().append('g')

        node.append('rect')
          .attr('x', function (d) {
            return d.x0
          })
          .attr('y', function (d) {
            return d.y0
          })
          .attr('height', function (d) {
            return d.y1 - d.y0
          })
          .attr('width', function (d) {
            return d.x1 - d.x0
          })
          .attr('fill', function (d) {
            return colorScheme[d.name]
          })
          .attr('stroke', function (d) {
            return colorScheme[d.name]
          })

        node.append('text')
          .attr('x', function (d) {
            return d.x0 - 6
          })
          .attr('y', function (d) {
            return (d.y1 + d.y0) / 2
          })
          .attr('dy', '0.35em')
          .attr('text-anchor', 'end')
          .text(function (d) {
            return d.name
          })
          .filter(function (d) {
            return d.x0 < width / 2
          })
          .attr('x', function (d) {
            return d.x1 + 6
          })
          .attr('text-anchor', 'start')

        node.append('title')
          .text(function (d) {
            return d.name + '\n' + format(d.value)
          })

        console.log('finished building sankey')
      },

      selPostPeriodComp () {
        if (this.showNew) {
          if (this.postSegComp === 'Best in Class') {
            this.barData = {
              labels: this.barLabels,
              datasets: [
                {
                  data: this.allBestPost[this.tpSelect - 1],
                  label: 'Prior Segment',
                  backgroundColor: '#003947'
                }
              ]
            }
          } else if (this.postSegComp === 'Rising Stars') {
            this.barData = {
              labels: this.barLabels,
              datasets: [
                {
                  data: this.allRisingPost[this.tpSelect - 1],
                  label: 'Prior Segment',
                  backgroundColor: '#8EAC1D'
                }
              ]
            }
          } else if (this.postSegComp === 'Middle of the Road') {
            this.barData = {
              labels: this.barLabels,
              datasets: [
                {
                  data: this.allMiddlePost[this.tpSelect - 1],
                  label: 'Prior Segment',
                  backgroundColor: '#0087AA'
                }
              ]
            }
          } else if (this.postSegComp === 'Lapsing') {
            this.barData = {
              labels: this.barLabels,
              datasets: [
                {
                  data: this.allLapsingPost[this.tpSelect - 1],
                  label: 'Prior Segment',
                  backgroundColor: '#F7970E'
                }
              ]
            }
          } else if (this.postSegComp === 'Deeply Lapsed') {
            this.barData = {
              labels: this.barLabels,
              datasets: [
                {
                  data: this.allDeeplyPost[this.tpSelect - 1],
                  label: 'Prior Segment',
                  backgroundColor: '#D63809'
                }
              ]
            }
          }
        } else {
          if (this.postSegComp === 'Best in Class') {
            this.barData = {
              labels: this.barLabels.slice(0, 5),
              datasets: [
                {
                  data: this.allBestPost[this.tpSelect - 1].slice(0, 5),
                  label: 'Prior Segment',
                  backgroundColor: '#003947'
                }
              ]
            }
          } else if (this.postSegComp === 'Rising Stars') {
            this.barData = {
              labels: this.barLabels.slice(0, 5),
              datasets: [
                {
                  data: this.allRisingPost[this.tpSelect - 1].slice(0, 5),
                  label: 'Prior Segment',
                  backgroundColor: '#8EAC1D'
                }
              ]
            }
          } else if (this.postSegComp === 'Middle of the Road') {
            this.barData = {
              labels: this.barLabels.slice(0, 5),
              datasets: [
                {
                  data: this.allMiddlePost[this.tpSelect - 1].slice(0, 5),
                  label: 'Prior Segment',
                  backgroundColor: '#0087AA'
                }
              ]
            }
          } else if (this.postSegComp === 'Lapsing') {
            this.barData = {
              labels: this.barLabels.slice(0, 5),
              datasets: [
                {
                  data: this.allLapsingPost[this.tpSelect - 1].slice(0, 5),
                  label: 'Prior Segment',
                  backgroundColor: '#F7970E'
                }
              ]
            }
          } else if (this.postSegComp === 'Deeply Lapsed') {
            this.barData = {
              labels: this.barLabels.slice(0, 5),
              datasets: [
                {
                  data: this.allDeeplyPost[this.tpSelect - 1].slice(0, 5),
                  label: 'Prior Segment',
                  backgroundColor: '#D63809'
                }
              ]
            }
          }
        }
      },

      selectTP () {
        // update bars
        this.selPostPeriodComp()

        // update retention cards
        if (this.tpSelect <= this.bestMigArray.length - 1) {
          this.bestRet = numeral(this.bestMigArray[this.tpSelect - 1] / this.jsonMsg.timePeriods[this.tpSelect].segmentTotal[0].bestTotalCount).format('0.00%')
          this.risingRet = numeral(this.risingMigArray[this.tpSelect - 1] / this.jsonMsg.timePeriods[this.tpSelect].segmentTotal[0].risingTotalCount).format('0.00%')
          this.middleRet = numeral(this.middleMigArray[this.tpSelect - 1] / this.jsonMsg.timePeriods[this.tpSelect].segmentTotal[0].middleTotalCount).format('0.00%')
          this.lapsingRet = numeral(this.lapsingMigArray[this.tpSelect - 1] / this.jsonMsg.timePeriods[this.tpSelect].segmentTotal[0].lapsingTotalCount).format('0.00%')
          this.deeplyRet = numeral(this.deeplyMigArray[this.tpSelect - 1] / this.jsonMsg.timePeriods[this.tpSelect].segmentTotal[0].deeplyTotalCount).format('0.00%')

          this.showSankey = true
          this.showError = false

          // remove current sankey and rebuild with new data
          d3.select('#stankey').remove()
          this.buildSankey()
        } else {
          this.bestRet = 0
          this.risingRet = 0
          this.middleRet = 0
          this.lapsingRet = 0
          this.deeplyRet = 0
          this.showSankey = false
          this.showError = true
        }
      },

      parseBarData () {
        let tempTpArray = []
        let tempAllBest = []
        let tempAllRising = []
        let tempAllMiddle = []
        let tempAllLapsing = []
        let tempAllDeeply = []

        let tempBestMigArray = []
        let tempRisingMigArray = []
        let tempMiddleMigArray = []
        let tempLapsingMigArray = []
        let tempDeeplyMigArray = []

        for (let i = 0; i < this.jsonMsg.timePeriods.length; i++) {
          let tempBest = [0, 0, 0, 0, 0, 0]
          let tempRising = [0, 0, 0, 0, 0, 0]
          let tempMiddle = [0, 0, 0, 0, 0, 0]
          let tempLapsing = [0, 0, 0, 0, 0, 0]
          let tempDeeply = [0, 0, 0, 0, 0, 0]

          let tempBestMig = 0
          let tempRisingMig = 0
          let tempMiddleMig = 0
          let tempLapsingMig = 0
          let tempDeeplyMig = 0

          tempTpArray.push(this.jsonMsg.timePeriods[i].timePeriod)

          for (let j = 0; j < this.jsonMsg.timePeriods[i].migrationData.length; j++) {
            switch (this.jsonMsg.timePeriods[i].migrationData[j].currentSegment) {
              case 'Best in Class':
                if (this.jsonMsg.timePeriods[i].migrationData[j].fromSegment === 'Best in Class') {
                  tempBest[0] = this.jsonMsg.timePeriods[i].migrationData[j].migrationCount
                } else if (this.jsonMsg.timePeriods[i].migrationData[j].fromSegment === 'Rising Stars') {
                  tempBest[1] = this.jsonMsg.timePeriods[i].migrationData[j].migrationCount
                } else if (this.jsonMsg.timePeriods[i].migrationData[j].fromSegment === 'Middle of the Road') {
                  tempBest[2] = this.jsonMsg.timePeriods[i].migrationData[j].migrationCount
                } else if (this.jsonMsg.timePeriods[i].migrationData[j].fromSegment === 'Lapsing') {
                  tempBest[3] = this.jsonMsg.timePeriods[i].migrationData[j].migrationCount
                } else if (this.jsonMsg.timePeriods[i].migrationData[j].fromSegment === 'Deeply Lapsed') {
                  tempBest[4] = this.jsonMsg.timePeriods[i].migrationData[j].migrationCount
                }
                tempBestMig = tempBestMig + this.jsonMsg.timePeriods[i].migrationData[j].migrationCount
                break
              case 'Rising Stars':
                if (this.jsonMsg.timePeriods[i].migrationData[j].fromSegment === 'Best in Class') {
                  tempRising[0] = this.jsonMsg.timePeriods[i].migrationData[j].migrationCount
                } else if (this.jsonMsg.timePeriods[i].migrationData[j].fromSegment === 'Rising Stars') {
                  tempRising[1] = this.jsonMsg.timePeriods[i].migrationData[j].migrationCount
                } else if (this.jsonMsg.timePeriods[i].migrationData[j].fromSegment === 'Middle of the Road') {
                  tempRising[2] = this.jsonMsg.timePeriods[i].migrationData[j].migrationCount
                } else if (this.jsonMsg.timePeriods[i].migrationData[j].fromSegment === 'Lapsing') {
                  tempRising[3] = this.jsonMsg.timePeriods[i].migrationData[j].migrationCount
                } else if (this.jsonMsg.timePeriods[i].migrationData[j].fromSegment === 'Deeply Lapsed') {
                  tempRising[4] = this.jsonMsg.timePeriods[i].migrationData[j].migrationCount
                }
                tempRisingMig = tempRisingMig + this.jsonMsg.timePeriods[i].migrationData[j].migrationCount
                break
              case 'Middle of the Road':
                if (this.jsonMsg.timePeriods[i].migrationData[j].fromSegment === 'Best in Class') {
                  tempMiddle[0] = this.jsonMsg.timePeriods[i].migrationData[j].migrationCount
                } else if (this.jsonMsg.timePeriods[i].migrationData[j].fromSegment === 'Rising Stars') {
                  tempMiddle[1] = this.jsonMsg.timePeriods[i].migrationData[j].migrationCount
                } else if (this.jsonMsg.timePeriods[i].migrationData[j].fromSegment === 'Middle of the Road') {
                  tempMiddle[2] = this.jsonMsg.timePeriods[i].migrationData[j].migrationCount
                } else if (this.jsonMsg.timePeriods[i].migrationData[j].fromSegment === 'Lapsing') {
                  tempMiddle[3] = this.jsonMsg.timePeriods[i].migrationData[j].migrationCount
                } else if (this.jsonMsg.timePeriods[i].migrationData[j].fromSegment === 'Deeply Lapsed') {
                  tempMiddle[4] = this.jsonMsg.timePeriods[i].migrationData[j].migrationCount
                }
                tempMiddleMig = tempMiddleMig + this.jsonMsg.timePeriods[i].migrationData[j].migrationCount
                break
              case 'Lapsing':
                if (this.jsonMsg.timePeriods[i].migrationData[j].fromSegment === 'Best in Class') {
                  tempLapsing[0] = this.jsonMsg.timePeriods[i].migrationData[j].migrationCount
                } else if (this.jsonMsg.timePeriods[i].migrationData[j].fromSegment === 'Rising Stars') {
                  tempLapsing[1] = this.jsonMsg.timePeriods[i].migrationData[j].migrationCount
                } else if (this.jsonMsg.timePeriods[i].migrationData[j].fromSegment === 'Middle of the Road') {
                  tempLapsing[2] = this.jsonMsg.timePeriods[i].migrationData[j].migrationCount
                } else if (this.jsonMsg.timePeriods[i].migrationData[j].fromSegment === 'Lapsing') {
                  tempLapsing[3] = this.jsonMsg.timePeriods[i].migrationData[j].migrationCount
                } else if (this.jsonMsg.timePeriods[i].migrationData[j].fromSegment === 'Deeply Lapsed') {
                  tempLapsing[4] = this.jsonMsg.timePeriods[i].migrationData[j].migrationCount
                }
                tempLapsingMig = tempLapsingMig + this.jsonMsg.timePeriods[i].migrationData[j].migrationCount
                break
              case 'Deeply Lapsed':
                if (this.jsonMsg.timePeriods[i].migrationData[j].fromSegment === 'Best in Class') {
                  tempDeeply[0] = this.jsonMsg.timePeriods[i].migrationData[j].migrationCount
                } else if (this.jsonMsg.timePeriods[i].migrationData[j].fromSegment === 'Rising Stars') {
                  tempDeeply[1] = this.jsonMsg.timePeriods[i].migrationData[j].migrationCount
                } else if (this.jsonMsg.timePeriods[i].migrationData[j].fromSegment === 'Middle of the Road') {
                  tempDeeply[2] = this.jsonMsg.timePeriods[i].migrationData[j].migrationCount
                } else if (this.jsonMsg.timePeriods[i].migrationData[j].fromSegment === 'Lapsing') {
                  tempDeeply[3] = this.jsonMsg.timePeriods[i].migrationData[j].migrationCount
                } else if (this.jsonMsg.timePeriods[i].migrationData[j].fromSegment === 'Deeply Lapsed') {
                  tempDeeply[4] = this.jsonMsg.timePeriods[i].migrationData[j].migrationCount
                }
                tempDeeplyMig = tempDeeplyMig + this.jsonMsg.timePeriods[i].migrationData[j].migrationCount
                break
            }
          }
          tempBest[5] = this.jsonMsg.timePeriods[i].segmentTotal[0].bestNewCount
          tempRising[5] = this.jsonMsg.timePeriods[i].segmentTotal[0].risingNewCount
          tempMiddle[5] = this.jsonMsg.timePeriods[i].segmentTotal[0].middleNewCount
          tempLapsing[5] = this.jsonMsg.timePeriods[i].segmentTotal[0].lapsingNewCount
          tempDeeply[5] = this.jsonMsg.timePeriods[i].segmentTotal[0].deeplyNewCount

          tempAllBest.push(tempBest)
          tempAllRising.push(tempRising)
          tempAllMiddle.push(tempMiddle)
          tempAllLapsing.push(tempLapsing)
          tempAllDeeply.push(tempDeeply)

          tempBestMigArray.push(tempBestMig)
          tempRisingMigArray.push(tempRisingMig)
          tempMiddleMigArray.push(tempMiddleMig)
          tempLapsingMigArray.push(tempLapsingMig)
          tempDeeplyMigArray.push(tempDeeplyMig)
        }

        this.allBestPost = tempAllBest
        this.allRisingPost = tempAllRising
        this.allMiddlePost = tempAllMiddle
        this.allLapsingPost = tempAllLapsing
        this.allDeeplyPost = tempAllDeeply

        this.selBestPost = tempAllBest[this.tpSelect - 1]
        this.selRisingPost = tempAllRising[this.tpSelect - 1]
        this.selMiddlePost = tempAllMiddle[this.tpSelect - 1]
        this.selLapsingPost = tempAllLapsing[this.tpSelect - 1]
        this.selDeeplyPost = tempAllDeeply[this.tpSelect - 1]

        this.barData = {
          labels: this.barLabels,
          datasets: [
            {
              data: this.selBestPost,
              label: 'Prior Segment',
              backgroundColor: '#003947'
            }
          ]
        }

        this.tpArray = tempTpArray

        this.bestMigArray = tempBestMigArray
        this.risingMigArray = tempRisingMigArray
        this.middleMigArray = tempMiddleMigArray
        this.lapsingMigArray = tempLapsingMigArray
        this.deeplyMigArray = tempDeeplyMigArray

        if (this.tpSelect <= this.bestMigArray.length - 1) {
          this.bestRet = numeral(this.bestMigArray[this.tpSelect - 1] / this.jsonMsg.timePeriods[this.tpSelect].segmentTotal[0].bestTotalCount).format('0.00%')
          this.risingRet = numeral(this.risingMigArray[this.tpSelect - 1] / this.jsonMsg.timePeriods[this.tpSelect].segmentTotal[0].risingTotalCount).format('0.00%')
          this.middleRet = numeral(this.middleMigArray[this.tpSelect - 1] / this.jsonMsg.timePeriods[this.tpSelect].segmentTotal[0].middleTotalCount).format('0.00%')
          this.lapsingRet = numeral(this.lapsingMigArray[this.tpSelect - 1] / this.jsonMsg.timePeriods[this.tpSelect].segmentTotal[0].lapsingTotalCount).format('0.00%')
          this.deeplyRet = numeral(this.deeplyMigArray[this.tpSelect - 1] / this.jsonMsg.timePeriods[this.tpSelect].segmentTotal[0].deeplyTotalCount).format('0.00%')
        } else {
          this.bestRet = 0
          this.risingRet = 0
          this.middleRet = 0
          this.lapsingRet = 0
          this.deeplyRet = 0
        }
      }
    }
  }

</script>

<style>
  .node rect {
    cursor: move;
    fill-opacity: .9;
    shape-rendering: crispEdges;
  }

  .node text {
    pointer-events: none;
    text-shadow: 0 1px 0 #fff;
  }

  .link {
    fill: none;
    stroke: #000;
    stroke-opacity: .2;
  }

  .link:hover {
    stroke-opacity: .5;
  }

  .scaling-svg-container {
    position: relative;
    height: 0;
    width: 100%;
    padding: 0;
    padding-bottom: 100%;
    /* override this inline for aspect ratio other than square */
  }

  .scaling-svg {
    position: absolute;
    height: 100%;
    width: 100%;
    left: 0;
    top: 0;
  }

  .inliner {
    display: inline-block;
  }

  .legend {
    display: inline-block;
    width: 12px;
    height: 12px;
    margin-right: 5px;
  }

  .legend_contain {
    display: inline-block;
    line-height: 26px;
    min-height: 34px;
    padding: 4px 8px 4px 8px;
    margin:0 0 8px 0;
  }

  .height_bars2 span { float: right; }

  .w_100 { width: 100%; }

  .y_axis1 {
    /* Rotate div */
    -ms-transform: rotate(270deg); /* IE 9 */
    -webkit-transform: rotate(270deg); /* Safari 3-8 */
    transform: rotate(270deg);
    }

  .y_axis2 {
    /* Rotate div */
    -ms-transform: rotate(90deg); /* IE 9 */
    -webkit-transform: rotate(90deg); /* Safari 3-8 */
    transform: rotate(90deg);
  }
</style>
