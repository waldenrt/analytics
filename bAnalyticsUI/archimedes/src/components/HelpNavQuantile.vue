<template>
  <!-- +++ right_drawer +++-->
    <v-navigation-drawer ref="helpNav" right temporary class="white" v-model="showmenuRight">
      <v-card-title primary-title class="primary pa-2">
        <h6 class="white--text text-xs-left mb-0">Pareto - Help</h6>
      </v-card-title>

      <!-- profiles_help -->
      <p class="body-2 grey lighten-2 info--text pa-2 ma-0">Profiles</p>
      <v-container class="pa-2">
        <v-divider></v-divider>
        <details v-for="(item,i) in helpProfileItems" :key="i">
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
      <p class="body-2 grey lighten-2 info--text pa-2 ma-0">Migration</p>
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
      <p class="body-2 grey lighten-2 info--text pa-2 ma-0">Top/Bottom Products</p>
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
      helpProfileItems: [
        {
          title: 'Customer Pareto Analysis',
          text: 'The Pareto chart shows the both the individual and cumulative contribution of customer/store value-groups across core performance metrics (i.e. spend, units, visits).',
          link: '#/Help/#pareto1'
        }
      ],
      helpMigItems: [
        {
          title: 'Customer Pareto Migration',
          text: 'The table shows how customers/stores migrate from one value-group to another during subsequent time periods.',
          link: '#/Help/#pareto2'
        },
        {
          title: 'Period-over-Period Retention Rate',
          text: 'The table shows a breakdown of retention for each value-group so a user can understand the relationship between value-group and loyalty.',
          link: '#/Help/#pareto3'
        },
        {
          title: 'Post Period Quantile Migration',
          text: 'This bar-chart dives deeper into the retention aspect of migration analysis.',
          link: '#/Help/#pareto4'
        }
      ],
      helpProductsItems: [
        {
          title: 'Purchased Products Analysis',
          text: 'This view reports the top-N (and bottom-N) products and the percentage of total spend allocated towards the top-N (or bottom-N) products, per value-group and time period.',
          link: '#/Help/#pareto5'
        }
      ]
    })
  }
</script>

<style scoped>

</style>
