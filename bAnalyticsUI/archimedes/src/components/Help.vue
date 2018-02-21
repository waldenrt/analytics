<template>
  <v-container fluid class="help pa-0 mb-3">
    <v-layout row wrap class="mt-0 mb-5 pa-0">

      <v-flex xs12 class="mb-4">
        <div class="marT_img">
          <!-- Hero Row -->
          <v-layout wrap row class="accent elevation-1 pa-3 ma-0 bg_hero">
              <v-flex xs12>
                <div class="contain">
                  <div>
                    <h3 class="white--text text-xs-center pb-0 mb-0">Help & Support</h3>
                  </div>
                </div>
              </v-flex>
          </v-layout>
          <!-- //Hero Row -->
          <!-- FAQs Row -->
          <v-layout wrap row class="pl-4 pr-4 pt-3 pb-0">
              <v-flex xs12 class="pl-1 pr-1">
                <h5 class="grey--text">Frequent Asked Questions</h5>
                <v-divider></v-divider>
              </v-flex>
          </v-layout>
          <!-- //FAQs Row -->
          <!-- Helpful Docs Row -->
          <v-layout wrap row class="pl-4 pr-4 pt-3 pb-0">
              <v-flex xs12 class="pl-1 pr-1">
                <h5 class="grey--text">Helpful Documentation</h5>
                <v-divider class="mb-3"></v-divider>
                <!-- Balor Trendlines -->
                <h6 id="balor1">BALOR Period Trend-line</h6>
                <p>The BALOR Period Trend-line shows how the three BALOR ratios move through each of the time periods. The BALOR ratios compare the contribution of New, Reactivated, and Returning customers against the loss in contribution from Lapsed customers. There is a BALOR ratio for each of the three core metrics (i.e. customers, transactions, spend).</p>

                <p>The parity level for the BALOR ratio is 1.0, indicating that the contribution from New, Reactivated, and Returning customers equally offsets the loss in contribution from Lapsed customers; a ratio greater than 1.0 indicates that the loss in contribution from Lapsed customers is being exceeded; and a ratio less than 1.0 indicates that the loss in contribution from Lapsed customers is not being offset.</p>

                <!-- Balor Summary -->
                <h6 id="balor2">BALOR Summary</h6>
                <p>Some text some text some text some text.</p>

                <!-- Balor Composition -->
                <h6 id="balor3">BALOR Composition and Metrics</h6>
                <p>The pie charts show how the New, Reactivated, and Returning BALOR customer segments contribute to each of the core metrics. Lapsed customers are not represented in any of the charts, due to the fact that these customers have not shopped and therefore do not contribute to these metrics, by definition. Thus, in calculating the BALOR ratios, the contribution of Lapsed customers is taken from the prior period (before the customers Lapsed) and represents an all-things-held-constant opportunity cost of not retaining these customers.</p>

                <p>The line chart on the right of this section shows how the three BALOR ratios compare to each other during any given period. Ideally, this chart should show a line that moves upwards and towards the right- demonstrating that New, Reactivated, and Returning customers contribute enough towards the business to directly offset or exceed the loss in contribution from Lapsed customer across all metrics.</p>

                <!-- Balor Retention Summary -->
                <h6 id="balor4">BALOR Retention Summary</h6>
                <p>The Retention Summary provides several different metrics for assessing the health of customer retention and its impact on purchase frequency and spend.</p>

                <p>The first metric is <b>Retention</b>, which measures the percentage of customers who shopped in one period, and returned to shop again in the period immediately following.</p>

                <p><b>Retention Growth</b>, then, is the percentage change in retention rate period over period. Because of the way these measures are calculated, Retention will not have a populated value until the second period and Retention Growth will not have a populated value until the third period.</p>

                <p><b>Retention Sales Lift</b> is the ratio of spend from Returning customers in the current period to Retained Customer Spend from Prior Period (i.e. spend in the prior period attributed to those customers who are Returning in the current period).</p>

                <p><b>Retention Transaction Lift</b> is the ratio of transaction count from Returning customers in the current period and Retained Customer Transactions from Prior Period (i.e. transaction count in prior period attribute to those customers who are Returning in the current period).</p>

                <!-- BALOR Raw Data -->
                <h6 id="balor5">BALOR Raw Data</h6>
                <p>Some text some text some text some text.</p>

                <!-- Segmant Trends -->
                <h6 id="balor6">Segment Trends</h6>
                <p>This dual-axis chart reports the three core metrics (plus Discount, if applicable) per BALOR segment for each of the time periods on both an aggregate and averaged basis. The bar-charts (left axis) represent the aggregate metrics per BALOR segment and per time period, whereas the line-chart (right axis) represents the average metrics per BALOR segment and per time period. Through this view, a user can see how the core metrics trend through time across each of the BALOR segments.</p>

                <p>Lapsed customers are defined as those who shopped in the previous time period but not in the current time period. Thus, they do not have transaction history in the current time period and so the results presented for Lapsed customers in this view are taken from the prior period (before the customers Lapsed). They represent an all-things-held-constant opportunity cost of not retaining these customers.</p>

                <!-- Segmant Profiles -->
                <h6 id="balor7">Segment Trends</h6>
                <p>This visualization is an alternative presentation of the averaged metrics presented as a line-chart in the Segment Trends view. The bar-chart presentation enables a user to compare the averaged metrics per BALOR segment during one specific time period. It contains the same per-period information as the trend lines in the Segment Trends view, but rendered in a graphical format that makes it easier to see similarities/differences among the BALOR segments.</p>
              </v-flex>
          </v-layout>
          <!-- //Helpful Docs Row -->

      </div>
      </v-flex>

    </v-layout>
  </v-container>
</template>

<script>
export default {
  name: 'home',
  data () {
    return {

    }
  },
  mounted () {
    this.$store.commit('switchApp', {module: 'Help'})
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.marT_img {margin-top:59px;width:100%;}
.card_crnr {border-top-right-radius:20px; border-bottom-left-radius:20px;}
.wrap1 {display:table; width:100%; height:100%;}
.wrap2 {display:table-cell; vertical-align:middle; margin:0 auto;}
.bg_hero {min-height:200px;}
.bg_hero .contain {
  display:table;
  margin:0 auto;
  height:100%;
}
.bg_hero .contain div {
  display:table-cell;
  vertical-align:middle;
}
</style>
