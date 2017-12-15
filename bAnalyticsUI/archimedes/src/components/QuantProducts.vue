<template>
  <v-container fluid class="quantileProducts pl-3 pr-3 mb-3">
    <!-- =====ROW1===== -->
    <v-layout row wrap class="pt-0 mt-0">
      <v-flex xs12>
        <v-card class="pa-0 ma-0 grey lighten-2">
          <v-card-title primary-title class="primary">
            <h6 class="white--text text-xs-left mb-0">Purchased Products Analysis per Quantile</h6>
          </v-card-title>
          <v-layout row wrap>
            <!--Dropdown1-->
            <v-flex xs12 sm2>
              <v-card flat class="pl-2 pr-2 pt-0 pb-0 grey lighten-2">
                <v-layout row wrap>
                  <v-flex xs12>
                    <div class="primary--text text-xs-left pl-0 pr-0 pb-0 pt-2">
                      Select Top/Bottom<br/>View:
                    </div>
                  </v-flex>
                  <v-flex xs12>
                    <v-card class="white pa-0">
                      <v-select v-bind:items="topBottom"
                                v-model="topBottomSelect"
                                label="Select Top or Bottom"
                                single-line
                                bottom
                                hide-details
                                v-on:input="updateData()"
                                class="pl-1 pr-1 mt-1 mb-2 white">
                      </v-select>
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
                      Select<br/>Measure:
                    </div>
                  </v-flex>
                  <v-flex xs12>
                    <v-card class="white">
                      <v-select
                          v-bind:items="measure"
                          v-model="measureSelect"
                          label="Select Measure"
                          single-line
                          bottom
                          hide-details
                          v-on:input="updateData()"
                          class="pl-1 pr-1 mt-1 mb-2 white elevation-1">
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
                      Choose A<br/>Time Period:
                    </div>
                  </v-flex>
                  <v-flex xs12>
                    <v-card class="white">
                      <v-select
                          v-bind:items="tpArray"
                          v-model="tpSelect"
                          label="Select Time Period"
                          single-line
                          bottom
                          hide-details
                          v-on:input="updateData()"
                          class="pl-1 pr-1 mt-1 mb-2 white elevation-1">
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
                      Select Customer<br/>Quantiles:
                    </div>
                  </v-flex>
                  <v-flex xs12>
                    <v-card class="white">
                      <v-select
                          v-bind:items="quantiles"
                          v-model="quantSelect"
                          label="Select Quantiles"
                          single-line
                          multiple
                          bottom
                          hide-details
                          v-on:input="updateData()"
                          class="pl-1 pr-1 mt-1 mb-2 white elevation-1">
                      </v-select>
                    </v-card>
                  </v-flex>
                </v-layout>
              </v-card>
            </v-flex>
            <!--//Dropdown4-->
            <!--Dropdown5-->
            <v-flex xs12 sm2>
              <v-card flat class="pl-2 pr-2 grey lighten-2">
                <v-layout row wrap>
                  <v-flex xs12>
                    <div class="primary--text text-xs-left pl-0 pr-0 pb-0 pt-2">
                      Choose A<br/>Product Level:
                    </div>
                  </v-flex>
                  <v-flex xs12>
                    <v-card class="white">
                      <v-select
                          v-bind:items="prodLevels"
                          v-model="levelSelect"
                          label="Select Product Level"
                          single-line
                          bottom
                          hide-details
                          v-on:input="updateData()"
                          class="pl-1 pr-1 mt-1 mb-2 white elevation-1">
                      </v-select>
                    </v-card>
                  </v-flex>
                </v-layout>
              </v-card>
            </v-flex>
            <!--//Dropdown5-->
          </v-layout>
        </v-card>
      </v-flex>
    </v-layout>
    <!-- //=====ROW1===== -->
    <!-- =====ROW2===== -->
    <v-layout wrap row>
      <v-flex xs12 class="pt-0 mt-0">
        <v-card class="pl-3 pr-3 pt-1 pb-1">
          <div class="title primary--text text-xs-center pa-1"><em><span class="grey--text darken-2">{{ topBottomSelect }}</span>
            Products by <span class="grey--text darken-2">{{ measureSelect }}</span> during Period
            <span class="grey--text darken-2">{{ tpSelect }}</span>, with Customer Quantiles
            <span class="grey--text darken-2">{{ quantSelect }} </span></em></div>
        </v-card>
      </v-flex>
    </v-layout>
    <!-- //=====ROW2===== -->
    <!-- =====ROW3===== -->
    <v-layout wrap row class="mt-3">
      <v-flex xs3 fill-height>
        <v-card class="white pl-3 pr-3 pt-1 pb-1">
          <!--****THIS IS JUST A PLACEHOLDER TABLE****-->
          <table cellpadding="0" cellspacing="0" width="100%" style="height:21vh !important;">
            <tr>
              <th class="pa-2 primary--text">Quantiles</th>
              <th class="pa-2 primary--text">Top Products<br/>Total Spend</th>
            </tr>
            <tr v-for="item in custItems" v-bind:key="item.Quantile">
              <td class="pa-2">
                <div class="text-xs-center" v-text="item.Quantile"></div>
              </td>
              <td class="pa-2">
                <div class="text-xs-center" v-text="item.total"></div>
              </td>
            </tr>
          </table>
          <!--//****THIS IS JUST A PLACEHOLDER TABLE****//-->
        </v-card>
      </v-flex>
      <v-flex xs9 fill-height>
        <v-card fill-height class="white pl-3 pr-3 pt-1 pb-1" style="height:100%">
          <div class="primary--text text-xs-center pa-1 subhead">Products</div>
          <stacked-bar :chart-data="barData" :options="barOptions" class="prod_chart"></stacked-bar>
        </v-card>
      </v-flex>
    </v-layout>
    <!-- //=====ROW3===== -->
  </v-container>
</template>

<script>
  import {quantProd} from './javascript/quantile.service'
  import StackedBar from './quantileCharts/StackedBar'

  // [JF] initiates numeral.js library in this vue component. must use together with numeral() or numeral().format()
  var numeral = require('numeral')

  export default {
    name: 'quantProducts',
    components: {
      StackedBar
    },
    data () {
      return {
        topBottom: ['Top', 'Bottom'],
        topBottomSelect: 'Top',
        measure: ['Spend', 'Count'],
        measureSelect: 'Spend',
        tpArray: [],
        tpSelect: 1,
        quantiles: [],
        quantSelect: ['All'],
        prodLevels: ['Level 1'],
        levelSelect: 'Level 1',
        barData: {},
        barOptions: {},
        custItems: [],
        topCountLvl1: [],
        botCountLvl1: [],
        topSpendLvl1: [],
        botSpendLvl1: [],
        topProdLvl1: [],
        botProdLvl1: [],
        topTtlSpend1: [],
        topTtlCount1: [],
        botTtlSpend1: [],
        botTtlCount1: [],
        topCountLvl2: [],
        botCountLvl2: [],
        topSpendLvl2: [],
        botSpendLvl2: [],
        topProdLvl2: [],
        botProdLvl2: [],
        topTtlSpend2: [],
        topTtlCount2: [],
        botTtlSpend2: [],
        botTtlCount2: [],
        topCountLvl3: [],
        botCountLvl3: [],
        topSpendLvl3: [],
        botSpendLvl3: [],
        topProdLvl3: [],
        botProdLvl3: [],
        topTtlSpend3: [],
        topTtlCount3: [],
        botTtlSpend3: [],
        botTtlCount3: [],
        jobId: 'QATestRun'
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
        quantProd(this.jobId)
          .catch(err => {
            alert('Could not get Quantile Summary results. ' + err.message.toString())
          })
          .then((response) => {
            this.incomingJson = response.data
            console.log(this.incomingJson)
            this.parseJson()
            this.createBarChart()
          })
      },

      parseJson () {
        var tempLevel1 = []
        var tempLevel2 = []
        var tempLevel3 = []
        var tempTPArray = []

        var tempTPlvl1 = []
        var tempTPlvl2 = []
        var tempTPlvl3 = []

        // separate into product level arrays
        for (let i = 0; i < this.jsonMsg.quantileProducts.length; i++) {
          if (this.jsonMsg.quantileProducts[i].columnName === 'Level1') {
            tempLevel1.push(this.jsonMsg.quantileProducts[i])
          } else if (this.jsonMsg.quantileProducts[i].columnName === 'Level2') {
            tempLevel2.push(this.jsonMsg.quantileProducts[i])
          } else if (this.jsonMsg.quantileProducts[i].columnName === 'Level3') {
            tempLevel3.push(this.jsonMsg.quantileProducts[i])
          }

          if (!tempTPArray.includes(this.jsonMsg.quantileProducts[i].timePeriod)) {
            tempTPArray.push(this.jsonMsg.quantileProducts[i].timePeriod)
          }
        }

        this.tpArray = tempTPArray

        // sort product level arrays into time periods with sorted quantiles in each TP
        for (let i = 0; i < this.tpArray.length; i++) {
          let lvl1 = []
          let lvl2 = []
          let lvl3 = []

          for (let j = 0; j < tempLevel1.length; j++) {
            if (tempLevel1[j].timePeriod === this.tpArray[i]) {
              lvl1.push(tempLevel1[j])
            }
          }

          for (let k = 0; k < tempLevel2.length; k++) {
            if (tempLevel2[k].timePeriod === this.tpArray[i]) {
              lvl2.push(tempLevel2[k])
            }
          }

          for (let l = 0; l < tempLevel3.length; l++) {
            if (tempLevel3[l].timePeriod === this.tpArray[i]) {
              lvl3.push(tempLevel3[l])
            }
          }

          lvl1.sort(function (a, b) {
            return a.quantile - b.quantile
          })

          lvl2.sort(function (a, b) {
            return a.quantile - b.quantile
          })

          lvl3.sort(function (a, b) {
            return a.quantile - b.quantile
          })

          tempTPlvl1.push(lvl1)
          tempTPlvl2.push(lvl2)
          tempTPlvl3.push(lvl3)
        }

        var itemCount = tempTPlvl1[0][0].ProductCount.length / 2

        var lvl1TPCountTop = []
        var lvl2TPCountTop = []
        var lvl3TPCountTop = []
        var lvl1TPCountBot = []
        var lvl2TPCountBot = []
        var lvl3TPCountBot = []

        var lvl1TPSpendTop = []
        var lvl2TPSpendTop = []
        var lvl3TPSpendTop = []
        var lvl1TPSpendBot = []
        var lvl2TPSpendBot = []
        var lvl3TPSpendBot = []

        var ttl1countTopObjTP = []
        var ttl1countBotObjTP = []
        var ttl1spendTopObjTP = []
        var ttl1spendBotObjTP = []

        var ttl2countTopObjTP = []
        var ttl2countBotObjTP = []
        var ttl2spendTopObjTP = []
        var ttl2spendBotObjTP = []

        var ttl3countTopObjTP = []
        var ttl3countBotObjTP = []
        var ttl3spendTopObjTP = []
        var ttl3spendBotObjTP = []

        // separate into top/bottom
        for (let i = 0; i < tempTPlvl1.length; i++) {
          let templvl1TPCountTop = []
          let templvl1TPCountBot = []
          let templvl1TPSpendTop = []
          let templvl1TPSpendBot = []

          let templvl2TPCountTop = []
          let templvl2TPCountBot = []
          let templvl2TPSpendTop = []
          let templvl2TPSpendBot = []

          let templvl3TPCountTop = []
          let templvl3TPCountBot = []
          let templvl3TPSpendTop = []
          let templvl3TPSpendBot = []

          let ttl1countTopObj = []
          let ttl1countBotObj = []
          let ttl1spendTopObj = []
          let ttl1spendBotObj = []

          let ttl2countTopObj = []
          let ttl2countBotObj = []
          let ttl2spendTopObj = []
          let ttl2spendBotObj = []

          let ttl3countTopObj = []
          let ttl3countBotObj = []
          let ttl3spendTopObj = []
          let ttl3spendBotObj = []

          // lvl 1 counts and spend array
          for (let j = 0; j < tempTPlvl1[i].length; j++) {
            let templvl1CountTop = []
            let templvl1SpendTop = []
            let templvl1CountBot = []
            let templvl1SpendBot = []

            let lvl1ttlcounttop = 0
            let lvl1ttlspendtop = 0
            let lvl1ttlcountbot = 0
            let lvl1ttlspendbot = 0

            for (let k = 0; k < itemCount; k++) {
              templvl1CountTop.push(tempTPlvl1[i][j].ProductCount[k])
              lvl1ttlcounttop += tempTPlvl1[i][j].ProductCount[k].count
              templvl1SpendTop.push(tempTPlvl1[i][j].ProductSpend[k])
              lvl1ttlspendtop += tempTPlvl1[i][j].ProductSpend[k].spend
            }
            for (let l = itemCount; l < tempTPlvl1[i][j].ProductCount.length; l++) {
              templvl1CountBot.push(tempTPlvl1[i][j].ProductCount[l])
              lvl1ttlcountbot += tempTPlvl1[i][j].ProductCount[l].count
              templvl1SpendBot.push(tempTPlvl1[i][j].ProductSpend[l])
              lvl1ttlspendbot += tempTPlvl1[i][j].ProductSpend[l].spend
            }

            templvl1TPCountTop.push(templvl1CountTop)
            templvl1TPCountBot.push(templvl1CountBot)
            templvl1TPSpendTop.push(templvl1SpendTop)
            templvl1TPSpendBot.push(templvl1SpendBot)

            ttl1countTopObj.push({'Quantile': j + 1, 'total': numeral(lvl1ttlcounttop).format('0,0')})
            ttl1countBotObj.push({'Quantile': j + 1, 'total': numeral(lvl1ttlcountbot).format('0,0')})
            ttl1spendTopObj.push({'Quantile': j + 1, 'total': numeral(lvl1ttlspendtop).format('$0,0.00')})
            ttl1spendBotObj.push({'Quantile': j + 1, 'total': numeral(lvl1ttlspendbot).format('$0,0.00')})
          }

          // lvl 2 counts and spend arrays
          for (let j = 0; j < tempTPlvl2[i].length; j++) {
            let templvl2CountTop = []
            let templvl2SpendTop = []
            let templvl2CountBot = []
            let templvl2SpendBot = []

            let lvl2ttlcounttop = 0
            let lvl2ttlspendtop = 0
            let lvl2ttlcountbot = 0
            let lvl2ttlspendbot = 0

            for (let k = 0; k < itemCount; k++) {
              templvl2CountTop.push(tempTPlvl2[i][j].ProductCount[k])
              lvl2ttlcounttop += tempTPlvl2[i][j].ProductCount[k].count
              templvl2SpendTop.push(tempTPlvl2[i][j].ProductSpend[k])
              lvl2ttlspendtop += tempTPlvl2[i][j].ProductSpend[k].spend
            }
            for (let l = itemCount; l < tempTPlvl2[i][j].ProductCount.length; l++) {
              templvl2CountBot.push(tempTPlvl2[i][j].ProductCount[l])
              lvl2ttlcountbot += tempTPlvl2[i][j].ProductCount[l].count
              templvl2SpendBot.push(tempTPlvl2[i][j].ProductSpend[l])
              lvl2ttlspendbot += tempTPlvl2[i][j].ProductSpend[l].spend
            }

            templvl2TPCountTop.push(templvl2CountTop)
            templvl2TPCountBot.push(templvl2CountBot)
            templvl2TPSpendTop.push(templvl2SpendTop)
            templvl2TPSpendBot.push(templvl2SpendBot)

            ttl2countTopObj.push({'Quantile': j + 1, 'total': numeral(lvl2ttlcounttop).format('0,0')})
            ttl2countBotObj.push({'Quantile': j + 1, 'total': numeral(lvl2ttlcountbot).format('0,0')})
            ttl2spendTopObj.push({'Quantile': j + 1, 'total': numeral(lvl2ttlspendtop).format('$0,0.00')})
            ttl2spendBotObj.push({'Quantile': j + 1, 'total': numeral(lvl2ttlspendbot).format('$0,0.00')})
          }

          // lvl 3 counts and spend arrays (do them separately in case any of them are empty)
          for (let j = 0; j < tempTPlvl3[i].length; j++) {
            let templvl3CountTop = []
            let templvl3SpendTop = []
            let templvl3CountBot = []
            let templvl3SpendBot = []

            let lvl3ttlcounttop = 0
            let lvl3ttlspendtop = 0
            let lvl3ttlcountbot = 0
            let lvl3ttlspendbot = 0

            for (let k = 0; k < itemCount; k++) {
              templvl3CountTop.push(tempTPlvl3[i][j].ProductCount[k])
              lvl3ttlcounttop += tempTPlvl3[i][j].ProductCount[k].count
              templvl3SpendTop.push(tempTPlvl3[i][j].ProductSpend[k])
              lvl3ttlspendtop += tempTPlvl3[i][j].ProductSpend[k].spend
            }
            for (let l = itemCount; l < tempTPlvl3[i][j].ProductCount.length; l++) {
              templvl3CountBot.push(tempTPlvl3[i][j].ProductCount[l])
              lvl3ttlcountbot += tempTPlvl3[i][j].ProductCount[l].count
              templvl3SpendBot.push(tempTPlvl3[i][j].ProductSpend[l])
              lvl3ttlspendbot += tempTPlvl3[i][j].ProductSpend[l].spend
            }

            templvl3TPCountTop.push(templvl3CountTop)
            templvl3TPCountBot.push(templvl3CountBot)
            templvl3TPSpendTop.push(templvl3SpendTop)
            templvl3TPSpendBot.push(templvl3SpendBot)

            ttl3countTopObj.push({'Quantile': j + 1, 'total': numeral(lvl3ttlcounttop).format('0,0')})
            ttl3countBotObj.push({'Quantile': j + 1, 'total': numeral(lvl3ttlcountbot).format('0,0')})
            ttl3spendTopObj.push({'Quantile': j + 1, 'total': numeral(lvl3ttlspendtop).format('$0,0.00')})
            ttl3spendBotObj.push({'Quantile': j + 1, 'total': numeral(lvl3ttlspendbot).format('$0,0.00')})
          }

          lvl1TPCountTop.push(templvl1TPCountTop)
          lvl1TPCountBot.push(templvl1TPCountBot)
          lvl1TPSpendTop.push(templvl1TPSpendTop)
          lvl1TPSpendBot.push(templvl1TPSpendBot)

          lvl2TPCountTop.push(templvl2TPCountTop)
          lvl2TPCountBot.push(templvl2TPCountBot)
          lvl2TPSpendTop.push(templvl2TPSpendTop)
          lvl2TPSpendBot.push(templvl2TPSpendBot)

          lvl3TPCountTop.push(templvl3TPCountTop)
          lvl3TPCountBot.push(templvl3TPCountBot)
          lvl3TPSpendTop.push(templvl3TPSpendTop)
          lvl3TPSpendBot.push(templvl3TPSpendBot)

          ttl1countTopObjTP.push(ttl1countTopObj)
          ttl1spendTopObjTP.push(ttl1spendTopObj)
          ttl1countBotObjTP.push(ttl1countBotObj)
          ttl1spendBotObjTP.push(ttl1spendBotObj)
          ttl2countTopObjTP.push(ttl2countTopObj)
          ttl2spendTopObjTP.push(ttl2spendTopObj)
          ttl2countBotObjTP.push(ttl2countBotObj)
          ttl2spendBotObjTP.push(ttl2spendBotObj)
          ttl3countTopObjTP.push(ttl3countTopObj)
          ttl3spendTopObjTP.push(ttl3spendTopObj)
          ttl3countBotObjTP.push(ttl3countBotObj)
          ttl3spendBotObjTP.push(ttl3spendBotObj)
        }

        this.topTtlSpend1 = ttl1spendTopObjTP
        this.topTtlCount1 = ttl1countTopObjTP
        this.botTtlSpend1 = ttl1spendBotObjTP
        this.botTtlCount1 = ttl1countBotObjTP

        this.topTtlSpend2 = ttl2spendTopObjTP
        this.topTtlCount2 = ttl2countTopObjTP
        this.botTtlSpend2 = ttl2spendBotObjTP
        this.botTtlCount2 = ttl2countBotObjTP

        this.topTtlSpend3 = ttl3spendTopObjTP
        this.topTtlCount3 = ttl3countTopObjTP
        this.botTtlSpend3 = ttl3spendBotObjTP
        this.botTtlCount3 = ttl3countBotObjTP

        var countTop1TP = []
        var countBot1TP = []
        var spendTop1TP = []
        var spendBot1TP = []
        var prodTop1TP = []
        var prodBot1TP = []

        var countTop2TP = []
        var countBot2TP = []
        var spendTop2TP = []
        var spendBot2TP = []
        var prodTop2TP = []
        var prodBot2TP = []

        var countTop3TP = []
        var countBot3TP = []
        var spendTop3TP = []
        var spendBot3TP = []
        var prodTop3TP = []
        var prodBot3TP = []

        // put in chartjs friendly arrays - 1 entry in each array per quantile, each array represents a single product rank
        for (let i = 0; i < lvl1TPCountTop.length; i++) {
          let countTop1 = []
          let countBot1 = []
          let spendTop1 = []
          let spendBot1 = []
          let prodTop1 = []
          let prodBot1 = []
          for (let l = 0; l < lvl1TPCountTop[i][0].length; l++) {
            countTop1.push([])
            countBot1.push([])
            spendTop1.push([])
            spendBot1.push([])
            prodTop1.push([])
            prodBot1.push([])
          }
          for (let j = 0; j < lvl1TPCountTop[i].length; j++) {
            for (let k = 0; k < lvl1TPCountTop[i][j].length; k++) {
              countTop1[k].push(lvl1TPCountTop[i][j][k].count)
              countBot1[k].push(lvl1TPCountBot[i][j][k].count)
              spendTop1[k].push(lvl1TPSpendTop[i][j][k].spend)
              spendBot1[k].push(lvl1TPSpendBot[i][j][k].spend)
              prodTop1[k].push(lvl1TPCountTop[i][j][k].product)
              prodBot1[k].push(lvl1TPCountBot[i][j][k].product)
            }
          }
          countTop1TP.push(countTop1)
          countBot1TP.push(countBot1)
          spendTop1TP.push(spendTop1)
          spendBot1TP.push(spendBot1)
          prodTop1TP.push(prodTop1)
          prodBot1TP.push(prodBot1)
        }

        for (let i = 0; i < lvl2TPCountTop.length; i++) {
          let countTop2 = []
          let countBot2 = []
          let spendTop2 = []
          let spendBot2 = []
          let prodTop2 = []
          let prodBot2 = []
          for (let l = 0; l < lvl2TPCountTop[i][0].length; l++) {
            countTop2.push([])
            countBot2.push([])
            spendTop2.push([])
            spendBot2.push([])
            prodTop2.push([])
            prodBot2.push([])
          }
          for (let j = 0; j < lvl2TPCountTop[i].length; j++) {
            for (let k = 0; k < lvl2TPCountTop[i][j].length; k++) {
              countTop2[k].push(lvl2TPCountTop[i][j][k].count)
              countBot2[k].push(lvl2TPCountBot[i][j][k].count)
              spendTop2[k].push(lvl2TPSpendTop[i][j][k].spend)
              spendBot2[k].push(lvl2TPSpendBot[i][j][k].spend)
              prodTop2[k].push(lvl2TPCountTop[i][j][k].product)
              prodBot2[k].push(lvl2TPCountBot[i][j][k].product)
            }
          }
          countTop2TP.push(countTop2)
          countBot2TP.push(countBot2)
          spendTop2TP.push(spendTop2)
          spendBot2TP.push(spendBot2)
          prodTop2TP.push(prodTop2)
          prodBot2TP.push(prodBot2)
        }

        for (let i = 0; i < lvl3TPCountTop.length; i++) {
          let countTop3 = []
          let countBot3 = []
          let spendTop3 = []
          let spendBot3 = []
          let prodTop3 = []
          let prodBot3 = []
          for (let l = 0; l < lvl3TPCountTop[i][0].length; l++) {
            countTop3.push([])
            countBot3.push([])
            spendTop3.push([])
            spendBot3.push([])
            prodTop3.push([])
            prodBot3.push([])
          }
          for (let j = 0; j < lvl3TPCountTop[i].length; j++) {
            for (let k = 0; k < lvl3TPCountTop[i][j].length; k++) {
              countTop3[k].push(lvl3TPCountTop[i][j][k].count)
              countBot3[k].push(lvl3TPCountBot[i][j][k].count)
              spendTop3[k].push(lvl3TPSpendTop[i][j][k].spend)
              spendBot3[k].push(lvl3TPSpendBot[i][j][k].spend)
              prodTop3[k].push(lvl3TPCountTop[i][j][k].product)
              prodBot3[k].push(lvl3TPCountBot[i][j][k].product)
            }
          }
          countTop3TP.push(countTop3)
          countBot3TP.push(countBot3)
          spendTop3TP.push(spendTop3)
          spendBot3TP.push(spendBot3)
          prodTop3TP.push(prodTop3)
          prodBot3TP.push(prodBot3)
        }

        this.topCountLvl1 = countTop1TP
        this.botCountLvl1 = countBot1TP
        this.topSpendLvl1 = spendTop1TP
        this.botSpendLvl1 = spendBot1TP
        this.topProdLvl1 = prodTop1TP
        this.botProdLvl1 = prodBot1TP

        this.topCountLvl2 = countTop2TP
        this.botCountLvl2 = countBot2TP
        this.topSpendLvl2 = spendTop2TP
        this.botSpendLvl2 = spendBot2TP
        this.topProdLvl2 = prodTop2TP
        this.botProdLvl2 = prodBot2TP

        this.topCountLvl3 = countTop3TP
        this.botCountLvl3 = countBot3TP
        this.topSpendLvl3 = spendTop3TP
        this.botSpendLvl3 = spendBot3TP
        this.topProdLvl3 = prodTop3TP
        this.botProdLvl3 = prodBot3TP

        if (!this.topCountLvl2.isEmpty) {
          this.prodLevels.push('Level 2')
        }

        if (!this.topCountLvl3.isEmpty) {
          this.prodLevels.push('Level 3')
        }

        var tempQuants = Array.from(new Array(this.topCountLvl1[0][0].length), (x, i) => i + 1)
        var tempAll = ['All'].concat(tempQuants)
        this.quantiles = tempAll
      },

      createBarChart () {
        this.barOptions = {
          responsive: true,
          maintainAspectRatio: false,
          scales: {
            xAxes: [{
              stacked: true,
              scaleLabel: {
                display: true,
                labelString: 'Percent of Total ' + this.measureSelect + ' on ' + this.topBottomSelect + ' ' +
                this.topSpendLvl1[this.tpSelect - 1].length + ' Products'
              }
            }],
            yAxes: [{
              stacked: true,
              scaleLabel: {
                display: true,
                labelString: 'Customer Quantile Group'
              }
            }]
          },
          legend: {
            display: false
          },
          animation: {
            onComplete: function () {
              // TODO fill out to populate inner text
            }
          }
        }

        this.custItems = this.topTtlSpend1[this.tpSelect - 1]

        var barDataset = []

        for (let i = 0; i < this.topSpendLvl1[this.tpSelect - 1].length; i++) {
          let green = 135 + i * 15
          let blue = 170 + 1 * 15
          barDataset.push({
            data: this.topSpendLvl1[this.tpSelect - 1][i],
            backgroundColor: 'rgb(0, ' + green + ', ' + blue + ')'
          })
        }

        this.barData = {
          labels: this.quantiles.slice(1, this.quantiles.length),
          datasets: barDataset
        }
      },

      updateData () {
        this.barOptions = {
          responsive: true,
          maintainAspectRatio: false,
          scales: {
            xAxes: [{
              stacked: true,
              scaleLabel: {
                display: true,
                labelString: 'Percent of Total ' + this.measureSelect + ' on ' + this.topBottomSelect + ' ' +
                this.topSpendLvl1[this.tpSelect - 1].length + ' Products'
              }
            }],
            yAxes: [{
              stacked: true,
              scaleLabel: {
                display: true,
                labelString: 'Customer Quantile Group'
              }
            }]
          },
          legend: {
            display: false
          },
          animation: {
            onComplete: function () {
              // TODO fill out to populate inner text
            }
          }
        }
        // using to update no matter which drop down is changed
        var barDataset = []
        var quants = []

        if (this.quantSelect.includes('All')) {
          quants = this.quantiles.slice(1, this.quantiles.length)
        } else {
          quants = JSON.parse(JSON.stringify(this.quantSelect)).sort(function (a, b) {
            return a - b
          })
        }

        if (this.levelSelect === 'Level 1') {
          if (this.topBottomSelect === 'Top') {
            if (this.measureSelect === 'Spend') {
              for (let i = 0; i < this.topSpendLvl1[this.tpSelect - 1].length; i++) {
                let green = 135 + i * 15
                let blue = 170 + 1 * 15
                if (quants.includes(i + 1)) {
                  barDataset.push({
                    data: this.topSpendLvl1[this.tpSelect - 1][i],
                    backgroundColor: 'rgb(0, ' + green + ', ' + blue + ')'
                  })
                }
              }
              this.custItems = this.topTtlSpend1[this.tpSelect - 1]
            } else if (this.measureSelect === 'Count') {
              this.custItems = this.topTtlCount1[this.tpSelect - 1]
              for (let i = 0; i < this.topCountLvl1[this.tpSelect - 1].length; i++) {
                let green = 135 + i * 15
                let blue = 170 + 1 * 15
                if (quants.includes(i + 1)) {
                  barDataset.push({
                    data: this.topCountLvl1[this.tpSelect - 1][i],
                    backgroundColor: 'rgb(0, ' + green + ', ' + blue + ')'
                  })
                }
              }
            }
          } else if (this.topBottomSelect === 'Bottom') {
            if (this.measureSelect === 'Spend') {
              this.custItems = this.botTtlSpend1[this.tpSelect - 1]
              for (let i = 0; i < this.botSpendLvl1[this.tpSelect - 1].length; i++) {
                let green = 135 + i * 15
                let blue = 170 + 1 * 15
                if (quants.includes(i + 1)) {
                  barDataset.push({
                    data: this.botSpendLvl1[this.tpSelect - 1][i],
                    backgroundColor: 'rgb(0, ' + green + ', ' + blue + ')'
                  })
                }
              }
            } else if (this.measureSelect === 'Count') {
              this.custItems = this.botTtlCount1[this.tpSelect - 1]
              for (let i = 0; i < this.botCountLvl1[this.tpSelect - 1].length; i++) {
                let green = 135 + i * 15
                let blue = 170 + 1 * 15
                if (quants.includes(i + 1)) {
                  barDataset.push({
                    data: this.botCountLvl1[this.tpSelect - 1][i],
                    backgroundColor: 'rgb(0, ' + green + ', ' + blue + ')'
                  })
                }
              }
            }
          }
        } else if (this.levelSelect === 'Level 2') {
          if (this.topBottomSelect === 'Top') {
            if (this.measureSelect === 'Spend') {
              this.custItems = this.topTtlSpend2[this.tpSelect - 1]
              for (let i = 0; i < this.topSpendLvl2[this.tpSelect - 1].length; i++) {
                let green = 135 + i * 15
                let blue = 170 + 1 * 15
                if (quants.includes(i + 1)) {
                  barDataset.push({
                    data: this.topSpendLvl2[this.tpSelect - 1][i],
                    backgroundColor: 'rgb(0, ' + green + ', ' + blue + ')'
                  })
                }
              }
            } else if (this.measureSelect === 'Count') {
              this.custItems = this.topTtlCount2[this.tpSelect - 1]
              for (let i = 0; i < this.topCountLvl2[this.tpSelect - 1].length; i++) {
                let green = 135 + i * 15
                let blue = 170 + 1 * 15
                if (quants.includes(i + 1)) {
                  barDataset.push({
                    data: this.topCountLvl1[this.tpSelect - 1][i],
                    backgroundColor: 'rgb(0, ' + green + ', ' + blue + ')'
                  })
                }
              }
            }
          } else if (this.topBottomSelect === 'Bottom') {
            if (this.measureSelect === 'Spend') {
              this.custItems = this.botTtlSpend2[this.tpSelect - 1]
              for (let i = 0; i < this.botSpendLvl2[this.tpSelect - 1].length; i++) {
                let green = 135 + i * 15
                let blue = 170 + 1 * 15
                if (quants.includes(i + 1)) {
                  barDataset.push({
                    data: this.botSpendLvl2[this.tpSelect - 1][i],
                    backgroundColor: 'rgb(0, ' + green + ', ' + blue + ')'
                  })
                }
              }
            } else if (this.measureSelect === 'Count') {
              this.custItems = this.botTtlCount2[this.tpSelect - 1]
              for (let i = 0; i < this.botCountLvl2[this.tpSelect - 1].length; i++) {
                let green = 135 + i * 15
                let blue = 170 + 1 * 15
                if (quants.includes(i + 1)) {
                  barDataset.push({
                    data: this.botCountLvl2[this.tpSelect - 1][i],
                    backgroundColor: 'rgb(0, ' + green + ', ' + blue + ')'
                  })
                }
              }
            }
          }
        } else if (this.levelSelect === 'Level 3') {
          if (this.topBottomSelect === 'Top') {
            if (this.measureSelect === 'Spend') {
              this.custItems = this.topTtlSpend3[this.tpSelect - 1]
              for (let i = 0; i < this.topSpendLvl3[this.tpSelect - 1].length; i++) {
                let green = 135 + i * 15
                let blue = 170 + 1 * 15
                if (quants.includes(i + 1)) {
                  barDataset.push({
                    data: this.topSpendLvl3[this.tpSelect - 1][i],
                    backgroundColor: 'rgb(0, ' + green + ', ' + blue + ')'
                  })
                }
              }
            } else if (this.measureSelect === 'Count') {
              this.custItems = this.topTtlCount3[this.tpSelect - 1]
              for (let i = 0; i < this.topCountLvl3[this.tpSelect - 1].length; i++) {
                let green = 135 + i * 15
                let blue = 170 + 1 * 15
                if (quants.includes(i + 1)) {
                  barDataset.push({
                    data: this.topCountLvl3[this.tpSelect - 1][i],
                    backgroundColor: 'rgb(0, ' + green + ', ' + blue + ')'
                  })
                }
              }
            }
          } else if (this.topBottomSelect === 'Bottom') {
            if (this.measureSelect === 'Spend') {
              this.custItems = this.botTtlSpend3[this.tpSelect - 1]
              for (let i = 0; i < this.botSpendLvl3[this.tpSelect - 1].length; i++) {
                let green = 135 + i * 15
                let blue = 170 + 1 * 15

                let tempQuant = []
                for (let j = 0; j < this.botSpendLvl3[this.tpSelect - 1][i].length; j++) {
                  if (quants.includes(j + 1)) {
                    tempQuant.push(this.botSpendLvl3[this.tpSelect - 1][i][j])
                  }
                }
                barDataset.push({
                  data: tempQuant,
                  backgroundColor: 'rgb(0, ' + green + ', ' + blue + ')'
                })
              }
            } else if (this.measureSelect === 'Count') {
              this.custItems = this.botTtlCount3[this.tpSelect - 1]
              for (let i = 0; i < this.botCountLvl3[this.tpSelect - 1].length; i++) {
                let green = 135 + i * 15
                let blue = 170 + 1 * 15
                if (quants.includes(i + 1)) {
                  barDataset.push({
                    data: this.botCountLvl3[this.tpSelect - 1][i],
                    backgroundColor: 'rgb(0, ' + green + ', ' + blue + ')'
                  })
                }
              }
            }
          }
        }

        this.barData = {
          labels: quants.sort(function (a, b) {
            return a - b
          }),
          datasets: barDataset
        }
      }
    }
  }
</script>

<style scoped>
  .progress_bar {
    width: 100%;
    height: 30px;
    display: inline-block;
    background-color: red;
    margin-top: 68px;
  }

  .subhead {
    line-height: 21px;
    font-weight: bold;
  }
  .prod_container {
    width:100% !important;
    height: 400px;
  }
  .prod_chart {
    position: relative;
    margin: 0 auto;
    /* height: 385px !important; */
  }
</style>
