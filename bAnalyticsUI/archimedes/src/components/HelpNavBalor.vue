<template>
  <!-- +++ right_drawer +++-->
    <v-navigation-drawer ref="helpNav" right temporary class="white" v-model="showmenuRight">
      <v-card-title primary-title class="primary pa-2">
        <h6 class="white--text text-xs-left mb-0">BALOR - Help</h6>
      </v-card-title>

      <!-- cadence_help -->
      <p class="body-2 grey lighten-2 info--text pa-2 ma-0">Cadence</p>
      <v-container class="pa-2">
        <v-divider></v-divider>
        <details v-for="(item,i) in helpCadenceItems" :key="i">
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

      <!-- balor_trends_help -->
      <p class="body-2 grey lighten-2 info--text pa-2 ma-0">BALOR Trends</p>
      <v-container class="pa-2">
        <v-divider></v-divider>
        <details v-for="(item,i) in helpTrendItems" :key="i">
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

      <!-- segment_trends_help -->
      <p class="body-2 grey lighten-2 info--text pa-2 ma-0">Segment Trends</p>
      <v-container class="pa-2">
        <v-divider></v-divider>
        <details v-for="(item,i) in helpSegTrendsItems" :key="i">
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
      helpCadenceItems: [
        {
          title: 'Purchase Cadence Distribution',
          text: 'This histogram or bar-chart represents the time between purchases among those customers who shopped two or more times in the dataset.',
          link: '#/Help/#cadence1'
        }
      ],
      helpTrendItems: [
        {
          title: 'BALOR Trendline',
          text: 'The BALOR Period Trendline shows how the three BALOR ratios move through each of the time periods.',
          link: '#/Help/#balor1'
        },
        {
          title: 'BALOR Composition and Metrics',
          text: 'The pie charts show how the New, Reactivated, and Returning BALOR customer segments contribute to each of the core metrics.',
          link: '#/Help/#balor2'
        },
        {
          title: 'Retention Summary',
          text: 'The Retention Summary provides several different metrics for assessing the health of customer retention and its impact on purchase frequency and spend.',
          link: '#/Help/#balor3'
        }
      ],
      helpSegTrendsItems: [
        {
          title: 'Segment Trends',
          text: 'This dual-axis chart reports the three core metrics (plus Discount, if applicable) per BALOR segment for each of the time periods on both an aggregate and averaged basis.',
          link: '#/Help/#balor4'
        },
        {
          title: 'Segment Profiles',
          text: 'This visualization is an alternative presentation of the averaged metrics presented as a line-chart in the Segment Trends view.',
          link: '#/Help/#balor5'
        }
      ]
    })
  }
</script>

<style scoped>

</style>
