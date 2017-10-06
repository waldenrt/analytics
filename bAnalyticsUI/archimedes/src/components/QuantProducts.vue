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
                      <v-select v-bind:items="quantiles"
                                v-model="quantileSelect"
                                label="Select Quantiles"
                                multiple
                                single-line
                                bottom
                                v-on:input="selectQuantile()"
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
                      Select<br/>Measure:
                    </div>
                  </v-flex>
                  <v-flex xs12>
                    <v-card class="white">
                      <!--SWAP OUT DROPDOWN-->
                      <v-select
                          v-bind:items="TPArray"
                          v-model="TPSelect"
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
                          v-bind:items="metrics"
                          v-model="metricSelect"
                          label="Select Metric"
                          single-line
                          bottom
                          v-on:input="selectMetric()"
                          class="pl-1 pr-1 m-0">
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
                          v-bind:items="metrics"
                          v-model="metricSelect"
                          label="Select Metric"
                          single-line
                          bottom
                          v-on:input="selectMetric()"
                          class="pl-1 pr-1 m-0">
                          <!--//SWAP OUT DROPDOWN-->
                      </v-select>
                    </v-card>
                  </v-flex>
                </v-layout>
              </v-card>
            </v-flex>
            <!--//Dropdown4-->
            <!--ProgressBar-->
            <v-flex xs12 sm4>
              <v-card flat class="pl-2 pr-2 grey lighten-2">
                <v-layout row wrap>
                  <v-flex xs12 class="pt-0">
                    <div class="progress_bar"></div>
                  </v-flex>
                </v-layout>
              </v-card>
            </v-flex>
            <!--//ProgressBar-->
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

  export default {
    name: 'quantProducts',
    data () {
      return {
        custItems: [
            {name: '1', vals: '$42,7700', percent: '46.37%'},
            {name: '2', vals: '$22,1234', percent: '47.44%'},
            {name: '3', vals: '$15,5746', percent: '45.37%'},
            {name: '4', vals: '$12,6114', percent: '49.70%'},
            {name: '5', vals: '$84,081', percent: '40.06%'},
            {name: '6', vals: '$59,079', percent: '35.94%'},
            {name: '7', vals: '$35,986', percent: '28.99%'},
            {name: '8', vals: '$30,481', percent: '29.60%'},
            {name: '9', vals: '$15,496', percent: '29.69%'},
            {name: '10', vals: '$13,111', percent: '37.76%'}
        ]
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
