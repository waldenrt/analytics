<template>
<v-container fluid class="mt-2 mb-0 pt-0 pb-0">
<v-card class="white">
    <v-layout row wrap class="pa-2">
      <v-flex xs12 s12 md2 class="text-xs-center">
          <v-btn primary large router to="/Balor/NewBalor" class="white--text">+ NEW BALOR INPUT</v-btn>
      </v-flex>
      <v-flex xs12 s12 md10 class="pa-2">
        <v-breadcrumbs icons divider="chevron_right" class="pl-0 ml-0">
        <v-breadcrumbs-item
          v-for="item in items"
          :key="item.text"
          :disabled="item.disabled"
          :to="item.link"
          class="bread_font"
        >
          {{ item.text }}
        </v-breadcrumbs-item>
      </v-breadcrumbs>
      </v-flex>
    </v-layout>
</v-card>
</v-container>
</template>

<script>

  export default{
    name: 'balorBreadcrumbs',
    data () {
      return {
        items: [
          { text: 'Input', disabled: false, link: '#/Balor/NewBalor' },
          { text: 'Cadence', disabled: false, link: '#/Balor/Cadence' },
          { text: 'Balor Trend', disabled: false, link: '#/Balor/Trend' },
          { text: 'Segment Trends', disabled: false, link: '#/Balor/SegmentTrend' }
        ]
      }
    }
  }

</script>

<style>
.bread_font, .bread_font a{
font-size:17px;
}
</style>
