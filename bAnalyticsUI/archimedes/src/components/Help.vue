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


          <!-- Core Lifecycle Helpful Docs Row -->
          <v-layout wrap row class="pl-4 pr-4 pt-3 pb-0">
              <v-flex xs12 class="pl-1 pr-1">
                <h5 class="grey--text">Core Lifecycle Helpful Documentation</h5>
                <v-divider class="mb-3"></v-divider>
                <!-- lifecycle1 -->
                <h6 id="lifecycle1">Segment Summary</h6>
                <p>This summary view reports the aggregate performance metrics per time period (i.e. customers, visits, spend, and units) as well as a breakdown of the contribution towards this total by each Core Lifecycle Segment, per time period. The doughnut charts should be read row-wise, showing the percentage of the total metric belonging to a particular Segment. A user can understand how the value of a customer changes as they progress through the lifecycle.</p>

                <!-- lifecycle2 -->
                <h6 id="lifecycle2">Period-over-Period Segment Migration</h6>
                <h6 id="lifecycle2">Prior Period Segment Retention Rate</h6>
                <p>This visualization is known as a Sankey Diagram and describes the migration of customers between Core Lifecycle Segments period-over-period, and is complemented by the period-over-period retention rate per segment. This information provides a user with a deeper understanding of customer progression through the lifecycle and how lifecycle stage impacts retention. Customers must have at least one transaction record in both periods in order to be assigned a segment in both pre-and-post periods and thus be reflected in the migration calculations.</p>

                <!-- lifecycle3 -->
                <h6 id="lifecycle3">Post Period Segment Composition</h6>
                <p>The bar-chart in this view is another representation of the migration data, focusing on one Core Lifecycle Segment in the post period. A user can see which segments in the prior-period contribute most to a segment in the post-period, as well as how many “New” customers are represented in the post-period. These “New” customers are those who did not have at least one transaction record in the prior-period.</p>

                <!-- lifecycle4 -->
                <h6 id="lifecycle4">Product Index</h6>
                <p>There are two different, but inherently related, sections in this view. On the left is a bar-chart which shows the overall percent of total spend per product and per period in the dataset. On the right is an index of each Core Lifecycle Segment’s percent of total spends per product against the Overall Product Share. This index represents the difference of a segment’s product share from the Overall Product Share. Across all segments, if a bar moves towards the left it indicates that the Segment spends less (as a percentage of total segment spend) on that particular product than Overall; similarly, if a bar moves towards the right it indicates that the Segment spends more (as a percentage of total segment spend) on that particular product than Overall. When taken together, these views allow a user to see the top and bottom products overall, how a Segment compares against the Overall calculations, and how preferences or tastes for particular products change as customers progress through the Core Lifecycle.</p>

                <!-- lifecycle5 -->
                <h6 id="lifecycle5">lifecycle5</h6>
                <p></p>

                <p></p>
              </v-flex>
          </v-layout>
          <!-- //Pareto Helpful Docs Row -->


          <!-- BALOR Helpful Docs Row -->
          <v-layout wrap row class="pl-4 pr-4 pt-3 pb-0">
              <v-flex xs12 class="pl-1 pr-1">
                <h5 class="grey--text">BALOR Helpful Documentation</h5>
                <v-divider class="mb-3"></v-divider>
                <!-- Purchase Cadence Distribution -->
                <h6 id="cadence1">Purchase Cadence Distribution</h6>
                <p>This histogram or bar-chart represents the time between purchases among those customers who shopped two or more times in the dataset. Analyzing the time between purchases informs a user as to how frequently customers are coming back to the brand and serves to define the time horizon for BALOR analysis.</p>

                <p>The actual time horizon for applying the BALOR is defined by the 80th percentile of Purchase Cadence. This 80th percentile value is then generalized, according to the following rules, so as to bring the calculated value into more meaningful time horizon for the business:</p>

                <table cellpadding="0" cellspacing="0" border="1" class="cad_tbl mb-4">
                  <thead>
                    <tr class="body-2">
                      <th>Calculated Cadence</th>
                      <th>BALOR Time Period</th>
                      <th>Histogram Bin Width</th>
                    </tr>
                  </thead>
                  <tbody>
                    <!-- first_group -->
                    <tr>
                      <td>0 – 7 days</td>
                      <td>1 week</td>
                      <td rowspan="3" class="text-xs-center">1 Day</td>
                    </tr>
                    <tr>
                      <td>8 – 14 days</td>
                      <td>2 weeks</td>
                    </tr>
                    <tr>
                      <td>15 – 30 days</td>
                      <td>1 month</td>
                    </tr>
                    <!-- second_group -->
                    <tr>
                      <td>31 – 61 days</td>
                      <td>2 months</td>
                      <td rowspan="4" class="text-xs-center">1 Week (7 days)</td>
                    </tr>
                    <tr>
                      <td>62 – 92 days</td>
                      <td>3 months</td>
                    </tr>
                    <tr>
                      <td>83 – 183 days</td>
                      <td>6 months</td>
                    </tr>
                    <tr>
                      <td>184+ days</td>
                      <td>1 year</td>
                    </tr>
                  </tbody>
                </table>

                <!-- Balor Trendlines -->
                <h6 id="balor1">BALOR Period Trend-line</h6>
                <p>The BALOR Period Trend-line shows how the three BALOR ratios move through each of the time periods. The BALOR ratios compare the contribution of New, Reactivated, and Returning customers against the loss in contribution from Lapsed customers. There is a BALOR ratio for each of the three core metrics (i.e. customers, transactions, spend).</p>

                <p>The parity level for the BALOR ratio is 1.0, indicating that the contribution from New, Reactivated, and Returning customers equally offsets the loss in contribution from Lapsed customers; a ratio greater than 1.0 indicates that the loss in contribution from Lapsed customers is being exceeded; and a ratio less than 1.0 indicates that the loss in contribution from Lapsed customers is not being offset.</p>

                <!-- Balor Composition -->
                <h6 id="balor2">BALOR Composition and Metrics</h6>
                <p>The pie charts show how the New, Reactivated, and Returning BALOR customer segments contribute to each of the core metrics. Lapsed customers are not represented in any of the charts, due to the fact that these customers have not shopped and therefore do not contribute to these metrics, by definition. Thus, in calculating the BALOR ratios, the contribution of Lapsed customers is taken from the prior period (before the customers Lapsed) and represents an all-things-held-constant opportunity cost of not retaining these customers.</p>

                <p>The line chart on the right of this section shows how the three BALOR ratios compare to each other during any given period. Ideally, this chart should show a line that moves upwards and towards the right- demonstrating that New, Reactivated, and Returning customers contribute enough towards the business to directly offset or exceed the loss in contribution from Lapsed customer across all metrics.</p>

                <!-- Balor Retention Summary -->
                <h6 id="balor3">BALOR Retention Summary</h6>
                <p>The Retention Summary provides several different metrics for assessing the health of customer retention and its impact on purchase frequency and spend.</p>

                <p>The first metric is <b>Retention</b>, which measures the percentage of customers who shopped in one period, and returned to shop again in the period immediately following.</p>

                <p><b>Retention Growth</b>, then, is the percentage change in retention rate period over period. Because of the way these measures are calculated, Retention will not have a populated value until the second period and Retention Growth will not have a populated value until the third period.</p>

                <p><b>Retention Sales Lift</b> is the ratio of spend from Returning customers in the current period to Retained Customer Spend from Prior Period (i.e. spend in the prior period attributed to those customers who are Returning in the current period).</p>

                <p><b>Retention Transaction Lift</b> is the ratio of transaction count from Returning customers in the current period and Retained Customer Transactions from Prior Period (i.e. transaction count in prior period attribute to those customers who are Returning in the current period).</p>

                <!-- Segmant Trends -->
                <h6 id="balor4">Segment Trends</h6>
                <p>This dual-axis chart reports the three core metrics (plus Discount, if applicable) per BALOR segment for each of the time periods on both an aggregate and averaged basis. The bar-charts (left axis) represent the aggregate metrics per BALOR segment and per time period, whereas the line-chart (right axis) represents the average metrics per BALOR segment and per time period. Through this view, a user can see how the core metrics trend through time across each of the BALOR segments.</p>

                <p>Lapsed customers are defined as those who shopped in the previous time period but not in the current time period. Thus, they do not have transaction history in the current time period and so the results presented for Lapsed customers in this view are taken from the prior period (before the customers Lapsed). They represent an all-things-held-constant opportunity cost of not retaining these customers.</p>

                <!-- Segmant Profiles -->
                <h6 id="balor5">Segment Profiles</h6>
                <p>This visualization is an alternative presentation of the averaged metrics presented as a line-chart in the Segment Trends view. The bar-chart presentation enables a user to compare the averaged metrics per BALOR segment during one specific time period. It contains the same per-period information as the trend lines in the Segment Trends view, but rendered in a graphical format that makes it easier to see similarities/differences among the BALOR segments.</p>
              </v-flex>
          </v-layout>
          <!-- //BALOR Helpful Docs Row -->


          <!-- Pareto Helpful Docs Row -->
          <v-layout wrap row class="pl-4 pr-4 pt-3 pb-0">
              <v-flex xs12 class="pl-1 pr-1">
                <h5 class="grey--text">Pareto Helpful Documentation</h5>
                <v-divider class="mb-3"></v-divider>
                <!-- Purchase Cadence Distribution -->
                <h6 id="pareto1">Quantile Pareto Analysis</h6>
                <p>The Pareto chart shows the both the individual and cumulative contribution of customer/store value-groups across core performance metrics (i.e. spend, units, visits). This dual-axis chart shows the total contribution per value group with bar-charts (left axis), and the cumulative contribution across value-groups as a line-chart (right axis). The view allows a user to see how the Pareto Effect (or 80/20 rule) materializes across customers and core metrics.</p>

                <p>The process of assigning customers/stores to value-groups is formally known as quantiling. In this context, quantiling refers to the process of dividing customers/stores into (near) equal groups based upon individual total spend per period. The value-group of “1” is the best performing according to spend, and the value-group of “10” is the worst performing according to spend.</p>

                <!-- Customer Quantile Migration -->
                <h6 id="pareto2">Customer Pareto Migration</h6>
                <p>The table shows how customers/stores migrate from one value-group to another during subsequent time periods. In order to be included in a value-group for any given period, a customer/store must have at least one transaction record during that period. This is especially notable amongst customers, who might not shop in consecutive periods; amongst stores, it’s likely to occur only with store openings or closings.</p>

                <!-- Period-over-Period Retention Rate -->
                <h6 id="pareto3">Period-over-Period Retention Rate</h6>
                <p>The table shows a breakdown of retention for each value-group so a user can understand the relationship between value-group and loyalty. From left-to-right the table contains for each value-group the number of customers/stores during the prior time period, the number of customers/stores retained in the post period (from the prior period), the number of new customers/stores in the post period, and the number of customers/stores in the post period.</p>

                <!-- Post Period Quantile Migration -->
                <h6 id="pareto4">Post Period Quantile Migration</h6>
                <p>This bar-chart dives deeper into the retention aspect of migration analysis. For each value-group in the post period, this chart helps a user understand from where (i.e. which value-groups in the prior-period) customers/stores originate.</p>

                <!-- Purchased Products Analysis per Quantile -->
                <h6 id="pareto5">Purchased Products Analysis per Quantile</h6>
                <p>This view reports the top-N (and bottom-N) products and the percentage of total spend allocated towards the top-N (or bottom-N) products, per value-group and time period. The number of products returned (N) is defined on the Input Page prior to running the engine, and has the flexibility of returning data across three different levels of the product hierarchy.</p>

                <p>The user can see which products do (or do not) resonate well within a particular value-group, analyze the similarities and differences amongst  value-groups in terms of products purchased, and get a better understanding for the diversity of products purchased per value-group via the top (or bottom) products spend as percent of total spend.</p>
              </v-flex>
          </v-layout>
          <!-- //Pareto Helpful Docs Row -->


          <!-- FAQs Row -->
          <v-layout wrap row class="pl-4 pr-4 pt-3 pb-0">
              <v-flex xs12 class="pl-1 pr-1">
                <h5 class="grey--text">Frequent Asked Questions</h5>
                <v-divider></v-divider>
              </v-flex>
          </v-layout>
          <!-- //FAQs Row -->
      </div>
      </v-flex>

    </v-layout>
  </v-container>
</template>

<script>
export default {
  name: 'help',
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
.cad_tbl th, .cad_tbl td {
  padding:4px;
}
</style>
