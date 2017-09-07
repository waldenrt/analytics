<template>
<v-container fluid>
    <v-layout row wrap v-if="$store.state.appmodule === 'Balor'" white class="mb-5 pa-3">
      <v-flex xs12 s12 md2 class="text-xs-center">
          <v-btn primary class="white--text">NEW {{$store.state.appmodule}} INPUT</v-btn>
      </v-flex>
      <v-flex xs12 s12 md10>
        <v-breadcrumbs icons divider="chevron_right">
        <v-breadcrumbs-item
          v-for="item in items" :key="item.text"
          :disabled="item.disabled"
        >
          {{ item.text }}
        </v-breadcrumbs-item>
      </v-breadcrumbs>
      </v-flex>
    </v-layout>

    <v-layout row wrap v-else-if="$store.state.appmodule === 'Decile'" white class="mb-5 pa-3">
      <v-flex xs12 s12 md2 class="text-xs-center">
        <v-btn primary class="white--text">NEW {{$store.state.appmodule}} INPUT</v-btn>
      </v-flex>
      <v-flex xs12 s12 md10>
        <v-breadcrumbs icons divider="chevron_right">
        <v-breadcrumbs-item
          v-for="item in items" :key="item.text"
          :disabled="item.disabled"
        >
          {{ item.text }}
        </v-breadcrumbs-item>
      </v-breadcrumbs>
      </v-flex>
    </v-layout>

  <v-layout row wrap v-else>
    <p>Leave empty until module is selected? </p>
  </v-layout>
</v-container>
</template>

<script>

  export default{
    name: 'breadcrumbs',
    data () {
      return {
        items: [
          {
            text: 'Dashboard',
            disabled: false
          },
          {
            text: 'Cadence',
            disabled: false
          },
          {
            text: 'Balor Trend',
            disabled: false
          },
          {
            text: 'Segment Trend',
            disabled: false
          },
          {
            text: 'Balor History',
            disabled: false
          }
        ]
      }
    }
  }

</script>

<style scoped>

</style>
