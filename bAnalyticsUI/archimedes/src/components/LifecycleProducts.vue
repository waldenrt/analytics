<template>
  <v-container>
    <v-select v-bind:items="tpArray"
              v-model="tpSelect"
              label="Select Time Period"
              single-line
              bottom></v-select>
    <v-select v-bind:items="prodArray"
              v-model="prods"
              label="Select Product Description"
              multiple
              single-line
              bottom></v-select>
    <v-select v-bind:items="segArray"
              v-model="segments"
              label="Select Segment(s)"
              multiple
              single-line
              bottom></v-select>
    <v-select v-bind:items="views"
              v-model="viewType"
              label="No idea how this is supposed to work...."
              single-line
              bottom></v-select>
    <prod-index-chart
  </v-container>
</template>

<script>
  import prodIndexChart from './lifecycleCharts/prodIndexChart'

  export default {
    name: 'lifecycleProducts',
    components: {
      prodIndexChart
    },
    data () {
      return {
        incomingJson: {},
        tpSelect: 1,
        prods: [],
        segments: [],
        viewType: 'Counts',
        views: ['Counts', 'Percentages'],
        tpArray: [],
        quantArray: []
      }
    },
    computed: {
      jsonMsg: function () {
        return this.incomingJson.data
      }
    },
    mounted () {

    },
    methods: {}
  }

</script>

<style>

</style>
