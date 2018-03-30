<template>
  <!-- +++ right_drawer +++-->
    <v-navigation-drawer ref="helpNav" right temporary class="white" v-model="showmenuRight">
      <v-card-title primary-title class="primary pa-2">
        <h6 class="white--text text-xs-left mb-0">Core Lifecycle - Help</h6>
      </v-card-title>

      <!-- profiles_help -->
      <p class="body-2 grey lighten-2 info--text pa-2 ma-0">Segment Summary</p>
      <v-container class="pa-2">
        <v-divider></v-divider>
        <details v-for="(item,i) in helpSummaryItems" :key="i">
            <summary class="grey--text lighten-2 subheading"><span class="accent--text">{{item.title}}</span></summary>
            <p class="pt-1 pl-3">
              <span>{{item.text}}</span><br />
              <span>
                <a :href="item.link" target="_blank">read more</a>
                <a :href="item.link" target="_blank" style="text-decoration:none;">
                  <v-icon style="font-size:14px;">launch</v-icon>
                </a>
              </span>
            </p>
        </details>
      </v-container>
      <v-divider></v-divider>

      <!-- pareto_migration_help -->
      <p class="body-2 grey lighten-2 info--text pa-2 ma-0">Segment Migration</p>
      <v-container class="pa-2">
        <v-divider></v-divider>
        <details v-for="(item,i) in helpMigItems" :key="i">
            <summary class="grey--text lighten-2 subheading"><span class="accent--text">{{item.title}}</span></summary>
            <p class="pt-1 pl-3">
              <span>{{item.text}}</span><br />
              <span>
                <a :href="item.link" target="_blank">read more</a>
                <a :href="item.link" target="_blank" style="text-decoration:none;">
                  <v-icon style="font-size:14px;">launch</v-icon>
                </a>
              </span>
            </p>
        </details>
      </v-container>
      <v-divider></v-divider>

      <!-- pareto_products_help -->
      <p class="body-2 grey lighten-2 info--text pa-2 ma-0">Product Index</p>
      <v-container class="pa-2">
        <v-divider></v-divider>
        <details v-for="(item,i) in helpProductsItems" :key="i">
            <summary class="grey--text lighten-2 subheading"><span class="accent--text">{{item.title}}</span></summary>
            <p class="pt-1 pl-3">
              <span>{{item.text}}</span><br />
              <span>
                <a :href="item.link" target="_blank">read more</a>
                <a :href="item.link" target="_blank" style="text-decoration:none;">
                  <v-icon style="font-size:14px;">launch</v-icon>
                </a>
              </span>
            </p>
        </details>
      </v-container>
    </v-navigation-drawer>
</template>

<script>
  export default {
    data: () => ({
      showmenuRight: false,
      helpSummaryItems: [
        {
          title: 'Segment Summary',
          text: 'This summary view reports the aggregate performance metrics per time period (i.e. customers, visits, spend, and units) as well as a breakdown of the contribution towards this total by each Core Lifecycle Segment, per time period.',
          link: '#/Help/#lifecycle1'
        }
      ],
      helpMigItems: [
        {
          title: 'Period-over-Period Segment Migration / Prior Period Segment Retention Rate',
          text: 'This visualization is known as a Sankey Diagram and describes the migration of customers between Core Lifecycle Segments period-over-period, and is complemented by the period-over-period retention rate per segment.',
          link: '#/Help/#lifecycle2'
        },
        {
          title: 'Post Period Segment Composition',
          text: 'The bar-chart in this view is another representation of the migration data, focusing on one Core Lifecycle Segment in the post period.',
          link: '#/Help/#lifecycle3'
        }
      ],
      helpProductsItems: [
        {
          title: 'Product Index',
          text: 'On the left is a bar-chart which shows the overall percent of total spend per product and per period in the dataset. On the right is an index of each Core Lifecycle Segment’s percent of total spends per product against the Overall Product Share.',
          link: '#/Help/#lifecycle4'
        }
      ]
    })
  }
</script>

<style scoped>

</style>
