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
            <v-flex xs6 sm4>
              <v-card flat class="pl-2 pr-2 pt-0 pb-0 grey lighten-2">
                <v-layout row wrap>
                  <v-flex xs12>
                    <div class="primary--text text-xs-left pl-0 pr-0 pb-0 pt-2">
                      Prior Period <br class="mob_break2" />for Analysis:
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
            <v-flex xs6 sm4>
              <v-card flat class="pl-2 pr-2 grey lighten-2">
                <v-layout row wrap>
                  <v-flex xs12>
                    <div class="primary--text text-xs-left pl-0 pr-0 pb-0 pt-2">
                      View <br class="mob_break2" />Table As:
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
            <!--//Dropdown2-->
            <!--Dropdown3-->
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
            <!--//Dropdown3-->
            <!--Dropdown4-->
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
            <!--//Dropdown4-->
          </v-layout>
        </v-card>
      </v-flex>
    </v-layout>
    <!-- //=====ROW1===== -->
    <!-- =====ROW2===== -->
    <v-layout wrap row>
      <v-flex xs12 class="pt-0 mt-0">
        <v-card class="pl-1 pr-1 pt-2 pb-2">
          <div class="title primary--text text-xs-center">
            <em>Period <span class="grey--text darken-2">{{ tpSelect }}</span>
            [Quantile] Migration from Prior Period [Quantiles] <br class="mob_break1" /><span class="grey--text darken-2">{{ priorPeriod }}</span> to
            Post Period [Quantiles] <span class="grey--text darken-2">{{ postPeriod }}</span></em>
          </div>
        </v-card>
      </v-flex>
    </v-layout>
    <!-- //=====ROW2===== -->
    <!-- =====ROW3===== -->
    <v-layout wrap row class="pt-0 mt-0">
      <v-flex xs12 class="scrolltable">
        <div class="y_axis caption text-xs-center">Customer Prior Period Quantile</div>
        <v-card class="white w_100 pt-3 pb-3 scrolltable">
          <div class="x_axis caption text-xs-center">Customer Post Period Quantile</div>
          <table cellpadding="0" cellspacing="0" class="quantTbl subheading pt-2 pl-5 pr-3">
            <thead>
              <td class="text-xs-center"></td>
              <td class="text-xs-center">1</td>
              <td class="text-xs-center">2</td>
              <td class="text-xs-center">3</td>
              <td class="text-xs-center">4</td>
              <td class="text-xs-center">5</td>
              <td class="text-xs-center">6</td>
              <td class="text-xs-center">7</td>
              <td class="text-xs-center">8</td>
              <td class="text-xs-center">9</td>
              <td class="text-xs-center">10</td>
            </thead>
            <tbody>
              <tr v-for="(item, index) in tableMigItems" v-bind:key="item.text" class="tRow">
                <td class="text-xs-center" v-text="item.from"></td>
                <td class="text-xs-center" :style="createTblColor[index][0]" v-text="item.key1"></td>
                <td class="text-xs-center" :style="createTblColor[index][1]" v-text="item.key2"></td>
                <td class="text-xs-center" :style="createTblColor[index][2]" v-text="item.key3"></td>
                <td class="text-xs-center" :style="createTblColor[index][3]" v-text="item.key4"></td>
                <td class="text-xs-center" :style="createTblColor[index][4]" v-text="item.key5"></td>
                <td class="text-xs-center" :style="createTblColor[index][5]" v-text="item.key6"></td>
                <td class="text-xs-center" :style="createTblColor[index][6]" v-text="item.key7"></td>
                <td class="text-xs-center" :style="createTblColor[index][7]" v-text="item.key8"></td>
                <td class="text-xs-center" :style="createTblColor[index][8]" v-text="item.key9"></td>
                <td class="text-xs-center" :style="createTblColor[index][9]" v-text="item.key10"></td>
              </tr>
            </tbody>
          </table>
<!--        <v-data-table
            :headers="quantHeaders"
            :items="tableMigItems"
            class="pl-5 pr-3 text-xs-center"
            :style="createTblColor"
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
        </v-data-table>-->
        </v-card>
      </v-flex>
    </v-layout>
    <!-- //=====ROW3===== -->
    <!-- =====ROW4===== -->
    <v-layout wrap row class="pt-0 mt-3 mb-3">
      <!--+++++col1+++++-->
      <v-flex xs12 md6 class="mb-3">
      <!--table-->
        <v-card class="white w_100">
          <v-card-title primary-title class="white">
            <h6 class="text-xs-center mb-0 primary--text">Period-over-Period Retention Rate</h6>
          </v-card-title>
          <v-divider class="primary pb-0"></v-divider>
              <div style="width:100%;" class="pb-3">
                <table cellpadding="0" cellspacing="0" width="100%" class="ret_table">
                  <tr class="grey lighten-2 elevation-1">
                    <td class="pt-1 pb-1 grey lighten-2 primary--text">Quantile</td>
                    <!--<td class="pt-1 pb-1">Prior Customers</td>-->
                    <td class="pt-1 pb-1 grey lighten-2 primary--text">Retained<br />Customers</td>
                    <td class="pt-1 pb-1 grey lighten-2 primary--text">New<br />Customers</td>
                    <td class="pt-1 pb-1 grey lighten-2 primary--text">Post<br />Customer<br />Total</td>
                    <!--<td class="pt-1 pb-1">Retention Rate</td>-->
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
              </div>
        </v-card>
      <!--//table-->
      </v-flex>
      <!--//+++++col1+++++-->
      <!--+++++col2+++++-->
      <v-flex xs12 md6 class="mb-3">
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
            <v-layout row wrap class="pl-1 pr-1 pt-3 pb-2">
              <v-flex xs12 fill-height>
                  <bar-chart :chart-data="quantbars" class="pareto_chart2"></bar-chart>
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
        viewType: 'Percentages',
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
        quantbars: {},
        tableBGcolor: {}
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
      },
      createTblColor: function () {
        var count = 0
        // dividing 60 since we are only using values between 40 and 100
        var decrementRed = 50 / (this.tableMigItems.length - 1)
        var decrementGreen = 40 / (this.tableMigItems.length - 1)

        var colorChart = []

        for (var i = 0; i < this.tableMigItems.length; i++) {
          // reset variables for eah new row of colors
          var tempColorChart = []
          var red = 85
          var green = 70 - (decrementGreen * i)
          for (var j = 0; j < this.tableMigItems.length; j++) {
            if (j === count) {
              tempColorChart.push('background-color: hsl(120, 15%, 75%)')
            } else if (j < count) {
              var newGreen = green + decrementGreen * (j + 1)
              tempColorChart.push('background-color: hsl(110, ' + '65%, ' + newGreen + '%')
            } else if (j > count) {
              red = red - decrementRed
              tempColorChart.push('background-color: hsl(0, ' + '100%, ' + red + '%')
            }
          }
          // add row of color to master colorChart
          colorChart.push(tempColorChart)
          count++
        }
        return colorChart
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
    //  this.createTblColor()
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
        this.tableMigItems = this.tablePercents[this.tpSelect - 1]

        console.log(this.tableMigItems)
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
      }
    }
  }

</script>

<style scoped>
  .mob_break {display: none;}
  .card_width { width: 100% !important; }
  .ret_table {
    height: 360px !important;
    text-align: center;
  }
  .pareto_chart2 {
    position: relative !important;
    margin: 0 auto !important;
    height: 279px !important;
  }
  .w_100 { width: 100%; }
  .y_axis {
    display: inline-block;
    position: relative;
    left: -60px;
    top: 400px;
    /*background-color: red;*/
    /* Rotate div */
    -ms-transform: rotate(270deg); /* IE 9 */
    -webkit-transform: rotate(270deg); /* Safari 3-8 */
    transform: rotate(270deg);
    z-index: 1;
  }
  .quantTbl {
    width:100%;
    margin:0 auto;
  }
  .quantTbl td {
    padding:20px;
    font-weight: bold;
  }
  .quantTbl thead td {
    background-color:#ffeaeb;
  }
  .quantTbl thead td:first-child {
    background-color:inherit;
  }
  .quantTbl .tRow td:first-child {
    background-color:#e7eeda;
  }

  .mob_break2 {
    display:none;
  }


  @media (max-width: 768px) {
    .quantTbl .tRow td:first-child {
      width:30px;
      padding: 5px;
    }
    .quantTbl td {
      padding:10px;
      font-size:13px;
    }
    .quantTbl thead td {
      padding: 5px 10px;
    }
  }
  /* Smartphones (portrait and landscape) ----------- */
  @media only screen and (min-device-width: 481px) and (max-device-width: 960px) {
    /* Styles */
    .mob_break1 {
      display:block;
    }
    .mob_break2 {
      display:none;
    }
  }

  /* Smartphones (portrait and landscape) ----------- */
  @media only screen and (min-device-width: 320px) and (max-device-width: 480px) {
    /* Styles */
    .mob_break1 {
      display:none;
    }
    .mob_break2 {
      display:block;
    }
  }
  /* Smartphones (portrait and landscape) ----------- */
  @media only screen and (min-device-width: 100px) and (max-device-width: 320px) {
    /* Styles */
    .scrolltable {
      width: 320px !important;
      overflow-x: scroll;
    }
    .mob_break2 {
      display:block;
    }
  }
  /* Smartphones (portrait and landscape) ----------- */
  @media only screen and (min-device-width: 321px) and (max-device-width: 375px) {
    /* Styles */
    .scrolltable {
      width: 375px !important;
      overflow-x: scroll;
    }
  }
  /* Smartphones (portrait and landscape) ----------- */
  @media only screen and (min-device-width: 376px) and (max-device-width: 425px) {
    /* Styles */
    .scrolltable {
      width: 425px !important;
      overflow-x: scroll;
    }
  }
</style>
