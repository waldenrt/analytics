<template>
  <v-container>
    <v-select v-bind:items="tpArray"
              v-model="tpSelect"
              label="Select Time Period"
              single-line
              bottom></v-select>
    <v-select v-bind:items="quantAray"
              v-model="piorPeriod"
              label="Select Prior Period Quantiles"
              multiple
              single-line
              bottom></v-select>
    <v-select v-bind:items="quantAray"
              v-model="postPeriod"
              label="Select Post Period Quantiles"
              multiple
              single-line
              bottom></v-select>
    <v-select v-bind:items="views"
              v-model="viewType"
              label="View Table As"
              single-line
              bottom></v-select>
  </v-container>
</template>

<script>
  export default {
    name: 'quantMigration',
    components: {
    },
    data () {
      return {
        incomingJson: {},
        tpSelect: 1,
        priorPeriod: [],
        postPeriod: [],
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
