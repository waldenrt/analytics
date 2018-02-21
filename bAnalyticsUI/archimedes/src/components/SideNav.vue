<template>


    <!-- +++ right_drawer +++-->
    <v-navigation-drawer ref="helpNav" right temporary class="white" v-model="showmenuRight">
      <v-card-title primary-title class="primary pa-2">
        <h5 class="white--text text-xs-left mb-0">BALOR - Help</h5>
      </v-card-title>
      <p class="body-2 grey lighten-2 info--text pa-2 ma-0">BALOR Trends</p>
      <v-container class="pa-2">
        <v-divider></v-divider>
        <details v-for="(item,i) in items" :key="i">
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
      items: [
        {
          title: 'BALOR Trendline',
          text: 'The BALOR Period Trendline shows how the three BALOR ratios move through each of the time periods.',
          link: '#/Help/#balor1'
        },
        {
          title: 'BALOR Summary',
          text: 'Some text some text some text some text.',
          link: '#/Help/#balor2'
        },
        {
          title: 'BALOR Composition and Metrics',
          text: 'The pie charts show how the New, Reactivated, and Returning BALOR customer segments contribute to each of the core metrics.',
          link: '#/Help/#balor3'
        },
        {
          title: 'Retention Summary',
          text: 'The Retention Summary provides several different metrics for assessing the health of customer retention and its impact on purchase frequency and spend.',
          link: '#/Help/#balor4'
        },
        {
          title: 'BALOR Trend Raw Data',
          text: 'Some text some text some text some text.',
          link: '#/Help/#balor5'
        }
      ]
    })
  }
</script>

<style scoped>

</style>
