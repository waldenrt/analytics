<template>
  <v-container fluid class="quantileMigration pl-3 pr-3 mb-3">
    <!-- ASHA STUFF HERE -->

    <!--// ASHA STUFF HERE -->

    <!-- =====ROW1===== -->
    <v-layout row wrap class="pt-0 mt-0">
      <v-flex xs12>
        <v-card class="pa-0 ma-0 grey lighten-2">
          <v-card-title primary-title class="primary">
            <h6 class="white--text text-xs-left mb-0">Customer Quantile Migration</h6>
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
            Quantile Migration from Prior Period Quantiles <span class="grey--text darken-2">{{ priorPeriod }}</span> to
            Post Period Quantiles <span class="grey--text darken-2">{{ postPeriod }}</span></em></div>
        </v-card>
      </v-flex>
    </v-layout>
    <!-- //=====ROW2===== -->
    <!-- =====ROW3===== -->
    <v-layout wrap row>
      <v-flex xs12 class="pt-0 mt-0">
        <v-card class="white pa-3" style="width:100%;">
          <v-layout wrap row>


            <!--+++++col1+++++-->
            <v-flex xs8>
              <v-data-table
                  v-bind:headers="quantHeaders"
                  :items="tableMigItems"
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
            </v-flex>
            <!--//+++++col1+++++-->


            <!--+++++col2+++++-->
            <v-flex xs4 class="pr-4">
              <!--table-row-->
              <v-layout wrap row>
                <v-card class="white" style="width:100%;">
                  <v-card-title primary-title class="white">
                    <h6 class="primary--text text-xs-center mb-0">Period-over-Period Retention Rate</h6>
                  </v-card-title>
                  <v-divider class="primary pb-0"></v-divider>
                  <v-flex xs12 fill-height>
                    <v-layout row wrap>
                      <table cellpadding="0" cellspacing="0" width="100%" style="height:21vh !important;">
                        <tr>
                          <td>Quantile</td>
                          <td>Prior Customers</td>
                          <td>Retained Customers</td>
                          <td>New Customers</td>
                          <td>Post Customer Total</td>
                          <td>Retention Rate</td>
                        </tr>
                        <tr v-for="item in sumItems" v-bind:key="item.name">
                          <td class="pl-2 pr-2 pt-2 pb-0">
                            <div class="primary--text" v-text="item.name"></div>
                          </td>
                          <td class="pl-2 pr-2 pt-2 pb-0">
                            <div v-text="item.priorCustCount"></div>
                          </td>
                          <td class="pl-2 pr-2 pt-2 pb-0">
                            <div v-text="item.retained"></div>
                          </td>
                          <td class="pl-2 pr-2 pt-2 pb-0">
                            <div v-text="item.new"></div>
                          </td>
                          <td class="pl-2 pr-2 pt-2 pb-0">
                            <div v-text="item.postCustCount"></div>
                          </td>
                          <td class="pl-2 pr-2 pt-2 pb-0">
                            <div v-text="item.retRate"></div>
                          </td>
                        </tr>
                      </table>
                    </v-layout>
                  </v-flex>
                </v-card>
              </v-layout>
              <!--//table-row-->
              <!--chart-row-->
              <v-layout wrap row>
                <v-card class="white mt-3 pa-0" style="width:100%;">
                  <v-flex xs12 fill-height>
                    <!-- =====ROW1===== -->
                    <v-layout row wrap class="pt-0 mt-0">
                      <v-flex xs12 class="pa-0 ma-0">
                        <v-card class="pa-0 ma-0 grey lighten-2">
                          <v-card-title primary-title class="white">
                            <h6 class="primary--text text-xs-left mb-0">Post Period Quantile Composition</h6>
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
                    <!-- //=====ROW1===== -->
                    <!-- =====ROW2===== -->
                    <v-layout row wrap class="pt-0 mt-0">
                      <v-flex xs12>
                        <div class="pl-1 pr-1 pt-3 pb-2 bar_height"><bar-chart :chart-data="quantbars"></bar-chart></div>
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
  import BarChart from './balorCharts/BarChart'
  import {quantMig} from './javascript/quantile.service'

  // [JF] initiates numeral.js library in this vue component. must use together with numeral() or numeral().format()
  // var numeral = require('numeral')

  export default {
    name: 'quantMigration',
    components: {
      BarChart
    },
    data () {
      return {
        jobId: 'QATestRun',
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
      }
    },
    mounted () {
      this.getResults()
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

        this.tableMigItems = JSON.parse(JSON.stringify(tpConverted))[this.tpSelect - 1]
        this.quantMigItems = JSON.parse(JSON.stringify(tpConverted))
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
              priorCustCount: tempTpPostCustCount[i + 1][j],
              retained: tempTpSums[i][j],
              new: tpNewCust[i][j],
              postCustCount: tempTpPostCustCount[i][j],
              retRate: tempTpSums[i][j] / tempTpPostCustCount[i + 1][j] * 100
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
          this.tableMigItems = this.quantMigItems[this.tpSelect - 1]
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
  .card_width { width: 100% !important; }
  .bar_height { height: 23vh; }
</style>
