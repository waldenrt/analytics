<template>
  <v-container fluid class="quantileProducts pl-3 pr-3 mb-3">
    <!-- ASHA STUFF HERE -->

    <!--// ASHA STUFF HERE -->

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
                      <!--SWAP OUT DROPDOWN-->
                      <v-select v-bind:items="topBottom"
                                v-model="topBottomSelect"
                                label="Select Top or Bottom"
                                single-line
                                bottom
                                hide-details
                                v-on:input="selectTopBottom()"
                                class="pl-1 pr-1 mt-1 mb-2 white">
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
                      Select<br/>Measure:
                    </div>
                  </v-flex>
                  <v-flex xs12>
                    <v-card class="white">
                      <!--SWAP OUT DROPDOWN-->
                      <v-select
                          v-bind:items="measure"
                          v-model="measureSelect"
                          label="Select Measure"
                          single-line
                          bottom
                          hide-details
                          v-on:input="selectMeasure()"
                          class="pl-1 pr-1 mt-1 mb-2 white elevation-1">
                      </v-select>
                      <!--//SWAP OUT DROPDOWN-->
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
                      <!--SWAP OUT DROPDOWN-->
                      <v-select
                          v-bind:items="tpArray"
                          v-model="tpSelect"
                          label="Select Time Period"
                          single-line
                          bottom
                          hide-details
                          v-on:input="selectTP()"
                          class="pl-1 pr-1 mt-1 mb-2 white elevation-1">
                      </v-select>
                      <!--//SWAP OUT DROPDOWN-->
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
                      <!--SWAP OUT DROPDOWN-->
                      <v-select
                          v-bind:items="quantiles"
                          v-model="quantSelect"
                          label="Select Quantiles"
                          single-line
                          multiple
                          bottom
                          hide-details
                          v-on:input="selectQuantiles()"
                          class="pl-1 pr-1 mt-1 mb-2 white elevation-1">
                          <!--//SWAP OUT DROPDOWN-->
                      </v-select>
                    </v-card>
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
          <div class="title primary--text text-xs-center pa-1"><em><span class="grey--text darken-2">Top</span> Products by <span class="grey--text darken-2">Spend</span> during Period <span class="grey--text darken-2">2</span>, with Customer Quantiles <span class="grey--text darken-2">All</span></em></div>
        </v-card>
      </v-flex>
    </v-layout>
    <!-- //=====ROW2===== -->
    <!-- =====ROW3===== -->
    <v-layout wrap row class="mt-3">
      <v-flex xs4>
        <v-card class="white pl-3 pr-3 pt-1 pb-1">
          <!--****THIS IS JUST A PLACEHOLDER TABLE****-->
          <table cellpadding="0" cellspacing="0" width="100%" style="height:21vh !important;">
            <tr>
              <th class="pa-2 primary--text">Quantiles</th>
              <th class="pa-2 primary--text">Top Products by<br />Spend</th>
              <th class="pa-2 primary--text">Top Products<br />Percent of Total</th>
            </tr>
            <tr v-for="item in custItems" v-bind:key="item.name">
              <td class="pa-2"><div class="text-xs-center" v-text="item.name"></div></td>
              <td class="pa-2"><div class="text-xs-center" v-text="item.vals"></div></td>
              <td class="pa-2"><div class="text-xs-center" v-text="item.percent"></div></td>
            </tr>
          </table>
          <!--//****THIS IS JUST A PLACEHOLDER TABLE****//-->
        </v-card>
      </v-flex>
      <v-flex xs8>
        <v-card class="white pl-3 pr-3 pt-1 pb-1">
          <div class="primary--text text-xs-center pa-1 subhead">Products Treemap<br />by Spend</div>
          <img src="http://via.placeholder.com/1050x480?text=Chart" width="100%" height="100%" style="height:475px;">
        </v-card>
      </v-flex>
    </v-layout>
    <!-- //=====ROW3===== -->
  </v-container>
</template>

<script>
  import {quantProd} from './javascript/quantile.service'

  // [JF] initiates numeral.js library in this vue component. must use together with numeral() or numeral().format()
  var numeral = require('numeral')

  export default {
    name: 'quantProducts',
    data () {
      return {
        topBottom: ['Top', 'Bottom'],
        topBottomSelect: 'Top',
        measure: ['Spend', 'Count'],
        measureSelect: 'Spend',
        tpArray: [],
        tpSelect: '1',
        quantiles: [],
        quantSelect: ['All'],
        custItems: [
            {name: '1', vals: numeral('427700').format('$0,0'), percent: numeral('.4637').format('0.00%')},
            {name: '2', vals: numeral('221234').format('$0,0'), percent: numeral('.4744').format('0.00%')},
            {name: '3', vals: numeral('155746').format('$0,0'), percent: numeral('.4537').format('0.00%')},
            {name: '4', vals: numeral('126114').format('$0,0'), percent: numeral('.4970').format('0.00%')},
            {name: '5', vals: numeral('84081').format('$0,0'), percent: numeral('.4006').format('0.00%')},
            {name: '6', vals: numeral('59079').format('$0,0'), percent: numeral('.3594').format('0.00%')},
            {name: '7', vals: numeral('35986').format('$0,0'), percent: numeral('.2899').format('0.00%')},
            {name: '8', vals: numeral('30481').format('$0,0'), percent: numeral('.2960').format('0.00%')},
            {name: '9', vals: numeral('15496').format('$0,0'), percent: numeral('.2969').format('0.00%')},
            {name: '10', vals: numeral('13111').format('$0,0'), percent: numeral('.3776').format('0.00%')}
        ],
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
          })
      }
    }
  }
</script>

<style scoped>
  .progress_bar{
    width: 100%;
    height:30px;
    display:inline-block;
    background-color:red;
    margin-top:68px;
  }
  .subhead{
    line-height:21px;
    font-weight:bold;
  }
</style>
