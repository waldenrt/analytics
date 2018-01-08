<template>
  <v-container fluid class="quantileMigration pl-3 pr-3 mb-3">
    <!-- =====ROW1===== -->
    <v-layout row wrap class="pt-0 mt-0">
      <v-flex xs12>
        <v-card class="pa-0 ma-0 grey lighten-2">
          <v-card-title primary-title class="primary">
            <h6 class="white--text text-xs-left mb-0">Customer Pareto Migration</h6>
          </v-card-title>
          <v-layout row wrap>
            <!--Dropdown1-->
            <v-flex xs12 sm3>
              <v-card flat class="pl-2 pr-2 pt-0 pb-0 grey lighten-2">
                <v-layout row wrap>
                  <v-flex xs12>
                    <div class="primary--text text-xs-left pl-0 pr-0 pb-0 pt-2">
                      Select Prior Period for Analysis:
                    </div>
                  </v-flex>
                  <v-flex xs12>
                      <v-select v-bind:items="tpArray"
                                v-model="tpSelect"
                                v-on:input="selectTP()"
                                label="Select Time Period"
                                single-line
                                bottom
                                hide-details
                                class="pl-1 pr-1 mt-1 mb-2 white elevation-1"></v-select>
                      </v-select>
                  </v-flex>
                </v-layout>
              </v-card>
            </v-flex>
            <!--//Dropdown1-->
            <!--Dropdown2-->
            <!--add in at later date
            <v-flex xs12 sm3>
              <v-card flat class="pl-2 pr-2 grey lighten-2">
                <v-layout row wrap>
                  <v-flex xs12>
                    <div class="primary--text text-xs-left pl-0 pr-0 pb-0 pt-2">
                      Select Prior Period Quantiles:
                    </div>
                  </v-flex>
                  <v-flex xs12>
                      <v-select v-bind:items="quantArray"
                                v-model="priorPeriod"
                                label="Select Prior Period Quantiles"
                                multiple
                                single-line
                                bottom
                                hide-details
                                class="pl-1 pr-1 mt-1 mb-2 white elevation-1"></v-select>
                  </v-flex>
                </v-layout>
              </v-card>
            </v-flex> -->
            <!--//Dropdown2-->
            <!--Dropdown3-->
            <!--add in at later date
            <v-flex xs12 sm3>
              <v-card flat class="pl-2 pr-2 grey lighten-2">
                <v-layout row wrap>
                  <v-flex xs12>
                    <div class="primary--text text-xs-left pl-0 pr-0 pb-0 pt-2">
                      Select Post Period Quantiles:
                    </div>
                  </v-flex>
                  <v-flex xs12>
                      <v-select v-bind:items="quantArray"
                                v-model="postPeriod"
                                label="Select Post Period Quantiles"
                                multiple
                                single-line
                                bottom
                                hide-details
                                class="pl-1 pr-1 m-0 white elevation-1"></v-select>
                      </v-select>
                  </v-flex>
                </v-layout>
              </v-card>
            </v-flex> -->
            <!--//Dropdown3-->
            <!--Dropdown4-->
            <v-flex xs12 sm3>
              <v-card flat class="pl-2 pr-2 grey lighten-2">
                <v-layout row wrap>
                  <v-flex xs12>
                    <div class="primary--text text-xs-left pl-0 pr-0 pb-0 pt-2">
                      View Table As:
                    </div>
                  </v-flex>
                  <v-flex xs12>
                      <v-select v-bind:items="views"
                                v-model="viewType"
                                v-on:input="selectView()"
                                label="View Table As"
                                single-line
                                bottom
                                hide-details
                                class="pl-1 pr-1 mt-1 mb-2 white elevation-1"></v-select>
                      </v-select>
                  </v-flex>
                </v-layout>
              </v-card>
            </v-flex>
            <!--//Dropdown4-->
          </v-layout>
        </v-card>
      </v-flex>
    </v-layout>
    <!-- //=====ROW1===== -->
    <!-- =====ROW2===== -->
    <v-layout wrap row>
      <v-flex xs12 class="pt-0 mt-0">
        <v-card class="pl-3 pr-3 pt-1 pb-1">
          <div class="title primary--text text-xs-center pa-1 emphasis"><em>Period <span class="grey--text darken-2">{{ tpSelect }}</span>
            [Quantile] Migration from Prior Period [Quantiles] <span class="grey--text darken-2">{{ priorPeriod }}</span> to
            Post Period [Quantiles] <span class="grey--text darken-2">{{ postPeriod }}</span></em></div>
        </v-card>
      </v-flex>
    </v-layout>
    <!-- //=====ROW2===== -->
    <!-- =====ROW3===== -->
    <v-layout wrap row class="pt-0 mt-0">
      <v-flex xs12>
        <div class="y_axis caption text-xs-center">Customer Prior Period Quantile</div>
        <v-card class="white w_100 pt-3 pb-3">
          <div class="x_axis caption text-xs-center">Customer Post Period Quantile</div>
          <!--<table style="width:100%; margin:0 auto;">
            <tbody>
              <tr v-for="item in tableMigItems" v-bind:key="item.text" style="width:100%;">
                <td class="tbl_cells text-xs-center" :style="" v-text="item.from"></td>
                <td class="tbl_cells text-xs-center" v-text="item.key1"></td>
                <td class="tbl_cells text-xs-center" v-text="item.key2"></td>
                <td class="tbl_cells text-xs-center" v-text="item.key3"></td>
                <td class="tbl_cells text-xs-center" v-text="item.key4"></td>
                <td class="tbl_cells text-xs-center" v-text="item.key5"></td>
                <td class="tbl_cells text-xs-center" v-text="item.key6"></td>
                <td class="tbl_cells text-xs-center" v-text="item.key7"></td>
                <td class="tbl_cells text-xs-center" v-text="item.key8"></td>
                <td class="tbl_cells text-xs-center" v-text="item.key9"></td>
                <td class="tbl_cells text-xs-center" v-text="item.key10"></td>
              </tr>
            </tbody>
          </table>-->
        <v-data-table
            :headers="quantHeaders"
            :items="tableMigItems"
            class="pl-5 pr-3"
            :style=""
            hide-actions>
            <template slot="items" scope="props">
              <td>{{ props.item.from }}</td>
              <td>{{ props.item.key1 }}</td>
              <td>{{ props.item.key2 }}</td>
              <td>{{ props.item.key3 }}</td>
              <td>{{ props.item.key4 }}</td>
              <td>{{ props.item.key5 }}</td>
              <td>{{ props.item.key6 }}</td>
              <td>{{ props.item.key7 }}</td>
              <td>{{ props.item.key8 }}</td>
              <td>{{ props.item.key9 }}</td>
              <td>{{ props.item.key10 }}</td>
            </template>
        </v-data-table>
        </v-card>
      </v-flex>
    </v-layout>
    <!-- //=====ROW3===== -->
    <!-- =====ROW4===== -->
    <v-layout wrap row class="pt-0 mt-3 mb-3">
      <!--+++++col1+++++-->
      <v-flex xs12 md8 class="mb-3">
      <!--table-->
        <v-card class="white w_100">
          <v-card-title primary-title class="white">
            <h6 class="primary--text text-xs-center mb-0">Period-over-Period Retention Rate</h6>
          </v-card-title>
          <v-divider class="primary pb-0"></v-divider>
            <v-layout row wrap class="pa-3">
              <table cellpadding="0" cellspacing="0" width="100%" class="ret_table">
                <tr>
                  <td>Quantile</td>
                  <!--<td>Prior Customers</td>-->
                  <td>Retained<br />Customers</td>
                  <td>New<br />Customers</td>
                  <td>Post<br />Customer<br />Total</td>
                  <!--<td>Retention Rate</td>-->
                </tr>
                <tr v-for="item in sumItems" v-bind:key="item.name">
                  <td class="pl-2 pr-2 pt-2 pb-0">
                    <div class="primary--text" v-text="item.name"></div>
                  </td>
                  <!--<td class="pl-2 pr-2 pt-2 pb-0">
                    <div v-text="item.priorCustCount"></div>
                  </td>-->
                  <td class="pl-2 pr-2 pt-2 pb-0">
                    <div v-text="item.retained"></div>
                  </td>
                  <td class="pl-2 pr-2 pt-2 pb-0">
                    <div v-text="item.new"></div>
                  </td>
                  <td class="pl-2 pr-2 pt-2 pb-0">
                    <div v-text="item.postCustCount"></div>
                  </td>
                  <!--<td class="pl-2 pr-2 pt-2 pb-0">
                    <div v-text="item.retRate"></div>
                  </td>-->
                </tr>
              </table>
            </v-layout>
        </v-card>
      <!--//table-->
      </v-flex>
      <!--//+++++col1+++++-->
      <!--+++++col2+++++-->
      <v-flex xs12 md4 class="mb-3">
      <!--chart-row-->
        <v-card class="white pa-0 w_100">
          <v-flex xs12 fill-height>
            <!--row1-->
            <v-layout row wrap class="pt-0 mt-0">
              <v-flex xs12 class="pa-0 ma-0">
                <v-card class="pa-0 ma-0 grey lighten-2">
                  <v-card-title primary-title class="white">
                    <h6 class="primary--text text-xs-left mb-0">Post Period [Quantile] Composition</h6>
                  </v-card-title>
                  <v-layout row wrap>
                    <!--Dropdown1-->
                    <v-flex xs12>
                      <v-card flat class="pl-2 pr-2 pt-0 pb-0 grey lighten-2">
                        <v-layout row wrap>
                          <v-flex xs12>
                            <div class="primary--text text-xs-left pl-0 pr-0 pb-0 pt-2">
                              Select Post Period Segment for Analysis:
                            </div>
                          </v-flex>
                          <v-flex xs12>
                              <v-select v-bind:items="quantArray"
                                        v-model="quantileSelect"
                                        label="Select Quantile\"
                                        single-line
                                        bottom
                                        hide-details
                                        v-on:input="selectQuantile()"
                                        class="pl-1 pr-1 mt-1 mb-2 white elevation-1">
                              </v-select>
                          </v-flex>
                        </v-layout>
                      </v-card>
                    </v-flex>
                    <!--//Dropdown1-->
                  </v-layout>
                </v-card>
              </v-flex>
            </v-layout>
            <!--//row1-->
            <!--row2-->
            <v-layout row wrap class="pt-0 mt-0">
              <v-flex xs12>
                <div class="pl-1 pr-1 pt-3 pb-2"><bar-chart :chart-data="quantbars" class="bar_height"></bar-chart></div>
              </v-flex>
            </v-layout>
            <!-- //row2-->
          </v-flex>
        </v-card>
      </v-flex>
      <!--//+++++col2+++++-->
    </v-layout>
    <!-- //=====ROW4===== -->
  </v-container>
</template>

<script>
  import BarChart from './balorCharts/BarChart'
  import {quantMig} from './javascript/quantile.service'

  // [JF] initiates numeral.js library in this vue component. must use together with numeral() or numeral().format()
  var numeral = require('numeral')

  export default {
    name: 'quantMigration',
    components: {
      BarChart
    },
    data () {
      return {
        tpSelect: 1,
        priorPeriod: ['All'],
        postPeriod: ['All'],
        viewType: 'Counts',
        views: ['Counts', 'Percentages'],
        tpArray: [],
        quantArray: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10],
        quantileSelect: 1,
        quantHeaders: [
          {text: '', value: 'from'},
          {text: '1', value: 'key1'},
          {text: '2', value: 'key2'},
          {text: '3', value: 'key3'},
          {text: '4', value: 'key4'},
          {text: '5', value: 'key5'},
          {text: '6', value: 'key6'},
          {text: '7', value: 'key7'},
          {text: '8', value: 'key8'},
          {text: '9', value: 'key9'},
          {text: '10', value: 'key10'}
        ],
        quantMigItems: [],
        tableMigItems: [],
        tableCounts: [],
        tablePercents: [],
        sumItems: [],
        sumItemsArray: [],
        quantbars: {}
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
      if (this.jobApp === 'Pareto' || this.jobApp === 'pareto') {
        this.$store.commit('switchApp', {module: 'Pareto'})
        this.getResults()
      } else {
        alert('Please select a Pareto job from Job History')
        this.$router.push('/Pareto/')
      }
      this.createTblColor()
    },
    methods: {
      getResults () {
        quantMig(this.jobId)
          .catch(err => {
            alert('Could not get Quantile Migration results. ' + err.message.toString())
          })
          .then((response) => {
            this.incomingJson = response.data
            console.log(this.incomingJson)
            this.createArrays()
            this.selectQuantile()
          })
      },

      createArrays () {
        var initArray = this.jsonMsg.quantileMigration
        var tpConverted = []
        var quants = []
        var tempTP = []
        var tempTpSums = []

        var numQuants = Math.sqrt(initArray[0].migrationData.length)

        for (let i = 1; i <= numQuants; i++) {
          quants.push({'from': i})
        }

        // REFORMAT DATA INTO SINLGE TP ARRAY OF KEY VALUE PAIRS
        for (let j = 0; j < initArray.length; j++) {
          tempTP.push(initArray[j].timePeriod)
          var tempConvert = JSON.parse(JSON.stringify(quants))
          for (let x = 0; x < initArray[j].migrationData.length; x++) {
            for (let k = 0; k < tempConvert.length; k++) {
              let key = 'key' + initArray[j].migrationData[x].currentQuantile
              if (tempConvert[k].from === initArray[j].migrationData[x].fromQuantile) {
                tempConvert[k][key] = initArray[j].migrationData[x].migrationCount
              }
            }
          }
          tpConverted.push(tempConvert)
        }

        // this.tableMigItems = JSON.parse(JSON.stringify(tpConverted))[this.tpSelect - 1]
        this.quantMigItems = JSON.parse(JSON.stringify(tpConverted))
        var tempMig = []
        for (let i = 0; i < this.quantMigItems[this.tpSelect - 1].length; i++) {
          tempMig.push({
            'from': this.quantMigItems[this.tpSelect - 1][i].from,
            'key1': numeral(this.quantMigItems[this.tpSelect - 1][i].key1).format('0,0'),
            'key2': numeral(this.quantMigItems[this.tpSelect - 1][i].key2).format('0,0'),
            'key3': numeral(this.quantMigItems[this.tpSelect - 1][i].key3).format('0,0'),
            'key4': numeral(this.quantMigItems[this.tpSelect - 1][i].key4).format('0,0'),
            'key5': numeral(this.quantMigItems[this.tpSelect - 1][i].key5).format('0,0'),
            'key6': numeral(this.quantMigItems[this.tpSelect - 1][i].key6).format('0,0'),
            'key7': numeral(this.quantMigItems[this.tpSelect - 1][i].key7).format('0,0'),
            'key8': numeral(this.quantMigItems[this.tpSelect - 1][i].key8).format('0,0'),
            'key9': numeral(this.quantMigItems[this.tpSelect - 1][i].key9).format('0,0'),
            'key10': numeral(this.quantMigItems[this.tpSelect - 1][i].key10).format('0,0')
          })
        }
        this.tableMigItems = tempMig
        this.tableCounts = JSON.parse(JSON.stringify(tpConverted))
        this.tpArray = tempTP

        // CALCULATE THE TOTAL RETAINED CUSTOMERS IN POST PERIOD
        for (let y = 0; y < this.quantMigItems.length; y++) {
          var tempSums = []
          for (let z = 0; z < this.quantMigItems[y].length; z++) {
            var vals = Object.values(this.quantMigItems[y][z])
            vals.shift()
            var sum2 = vals.reduce(function (a, b) {
              return a + b
            })
            tempSums.push(sum2)
          }
          tempTpSums.push(tempSums)
        }

        var tempPercentTable = []

        // CALCULATE PERCENATAGE TABLE DATA
        for (let i = 0; i < this.quantMigItems.length; i++) {
          var newPercents = []
          for (let j = 0; j < this.quantMigItems[i].length; j++) {
            let val = Object.values(this.quantMigItems[i][j])
            let keys = Object.keys(this.quantMigItems[i][j])
            let obj = {'from': val[0]}
            for (let k = 1; k < val.length; k++) {
              obj[keys[k]] = (val[k] / tempTpSums[i][j] * 100).toFixed(2) + '%'
            }
            newPercents.push(obj)
          }
          tempPercentTable.push(newPercents)
        }

        this.tablePercents = tempPercentTable

        // REFORMAT NEW CUST COUNT PER QUANTILE DATA
        var tpNewCust = []

        for (let i = 0; i < this.jsonMsg.quantileMigration.length; i++) {
          var newCust = []
          for (let j = 0; j < this.jsonMsg.quantileMigration[i].quantileTotals.length; j++) {
            newCust.push(this.jsonMsg.quantileMigration[i].quantileTotals[j].newCount)
          }
          tpNewCust.push(newCust)
        }

        // CALCULATE THE POST CUSTOMER COUNT ARRAY (POST NEW + POST RETAINED)
        var tempTpPostCustCount = []
        var tempCustCountZero = []

        for (let i = 0; i < tempTpSums.length; i++) {
          var postCount = []
          tempCustCountZero = []
          for (let j = 0; j < tempTpSums[i].length; j++) {
            postCount.push(tempTpSums[i][j] + tpNewCust[i][j])
            tempCustCountZero.push(0)
          }
          tempTpPostCustCount.push(postCount)
        }

        tempTpPostCustCount.push(tempCustCountZero)

        // CREATE ARRAY OF OBJECTS TO POPULATE THE RETENTION RATE TABLE...
        var tempRetObj = []

        for (let i = 0; i < tempTpSums.length; i++) {
          var retObj = []
          for (let j = 0; j < tempTpSums[i].length; j++) {
            retObj.push({
              name: j + 1,
              // priorCustCount: tempTpPostCustCount[i + 1][j],
              retained: numeral(tempTpSums[i][j]).format('0,0'),
              new: numeral(tpNewCust[i][j]).format('0,0'),
              postCustCount: numeral(tempTpPostCustCount[i][j]).format('0,0')
              // retRate: tempTpSums[i][j] / tempTpPostCustCount[i + 1][j] * 100
            })
          }
          tempRetObj.push(retObj)
        }

        this.sumItems = tempRetObj[this.tpSelect - 1]
        this.sumItemsArray = tempRetObj
      },

      selectTP () {
        this.sumItems = this.sumItemsArray[this.tpSelect - 1]
        this.selectView()
        this.selectQuantile()
      },

      selectView () {
        if (this.viewType === 'Counts') {
          var tempFormatted = []
          for (let i = 0; i < this.quantMigItems[this.tpSelect - 1].length; i++) {
            tempFormatted.push({
              'from': this.quantMigItems[this.tpSelect - 1][i].from,
              'key1': numeral(this.quantMigItems[this.tpSelect - 1][i].key1).format('0,0'),
              'key2': numeral(this.quantMigItems[this.tpSelect - 1][i].key2).format('0,0'),
              'key3': numeral(this.quantMigItems[this.tpSelect - 1][i].key3).format('0,0'),
              'key4': numeral(this.quantMigItems[this.tpSelect - 1][i].key4).format('0,0'),
              'key5': numeral(this.quantMigItems[this.tpSelect - 1][i].key5).format('0,0'),
              'key6': numeral(this.quantMigItems[this.tpSelect - 1][i].key6).format('0,0'),
              'key7': numeral(this.quantMigItems[this.tpSelect - 1][i].key7).format('0,0'),
              'key8': numeral(this.quantMigItems[this.tpSelect - 1][i].key8).format('0,0'),
              'key9': numeral(this.quantMigItems[this.tpSelect - 1][i].key9).format('0,0'),
              'key10': numeral(this.quantMigItems[this.tpSelect - 1][i].key10).format('0,0')
            })
          }
          this.tableMigItems = tempFormatted
        } else if (this.viewType === 'Percentages') {
          this.tableMigItems = this.tablePercents[this.tpSelect - 1]
        }
      },

      selectQuantile () {
        var keyArray = ['key1', 'key2', 'key3', 'key4', 'key5', 'key6', 'key7', 'key8', 'key9', 'key10']
        this.quantbars = {
          labels: this.quantArray,
          datasets: [{
            data: [
              this.quantMigItems[this.tpSelect - 1][0][keyArray[this.quantileSelect - 1]],
              this.quantMigItems[this.tpSelect - 1][1][keyArray[this.quantileSelect - 1]],
              this.quantMigItems[this.tpSelect - 1][2][keyArray[this.quantileSelect - 1]],
              this.quantMigItems[this.tpSelect - 1][3][keyArray[this.quantileSelect - 1]],
              this.quantMigItems[this.tpSelect - 1][4][keyArray[this.quantileSelect - 1]],
              this.quantMigItems[this.tpSelect - 1][5][keyArray[this.quantileSelect - 1]],
              this.quantMigItems[this.tpSelect - 1][6][keyArray[this.quantileSelect - 1]],
              this.quantMigItems[this.tpSelect - 1][7][keyArray[this.quantileSelect - 1]],
              this.quantMigItems[this.tpSelect - 1][8][keyArray[this.quantileSelect - 1]],
              this.quantMigItems[this.tpSelect - 1][9][keyArray[this.quantileSelect - 1]]
            ],
            backgroundColor: '#F7970E'
          }]
        }
      },
      createTblColor () {
        var count = 0
        // dividing 60 since we are only using values between 40 and 100
        var decrement = 60 / (this.tableMigItems.length - 1)

        var colorChart = []

        for (var i = 0; i < this.tableMigItems.length; i++) {
          // reset variables for eah new row of colors
          var tempColorChart = []
          var red = 30
          var green = 40 + (decrement * 1)
          for (var j = 0; j < this.tableMigItems.length; j++) {
            if (j === count) {
              tempColorChart.push('hsl(120, 15% 75%)')
            } else if (j < count) {
              green = green - decrement * (j + 1)
              tempColorChart.push('hsl(130,' + green + '%, 50%)')
            } else if (j > count) {
              red = red + decrement
              tempColorChart.push('hsl(0,' + red + '%, 50%)')
            }
          }
        }
        // add row of color to master colorChart
        colorChart.push(tempColorChart)
        count++
      }
    }
  }

</script>

<style scoped>
  .card_width { width: 100% !important; }
  .ret_table {
    height:21vh !important;
    text-align: center;
  }
  .bar_height { height: 23vh !important; }
  .w_100 { width: 100%; }
  .y_axis {
    display: inline-block;
    position: relative;
    left: -60px;
    top: 300px;
    /*background-color: red;*/
    /* Rotate div */
    -ms-transform: rotate(270deg); /* IE 9 */
    -webkit-transform: rotate(270deg); /* Safari 3-8 */
    transform: rotate(270deg);
    z-index: 1;
  }
</style>
