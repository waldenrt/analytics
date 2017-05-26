<template>
  <div  v-if="$store.state.appmodule === 'Balor'">
    <v-list>
      <v-list-item>
        <v-list-tile>
          <v-list-tile-title>
            <router-link to="/Balor/NewBalor">New Balor</router-link>
          </v-list-tile-title>
        </v-list-tile>
      </v-list-item>
      <v-list-item>
        <v-list-tile>
          <v-list-tile-title>
            <router-link to="/Balor/Cadence">Cadence</router-link>
          </v-list-tile-title>
        </v-list-tile>
      </v-list-item>
      <v-list-item>
        <v-list-tile>
          <v-list-tile-title>
            <router-link to="/Balor/Trend">Balor Trend</router-link>
          </v-list-tile-title>
        </v-list-tile>
      </v-list-item>
      <v-list-item>
        <v-list-tile>
          <v-list-tile-title>
            <router-link to="/Balor/SegmentTrend">Segment Trend</router-link>
          </v-list-tile-title>
        </v-list-tile>
      </v-list-item>
      <v-list-item>
        <v-list-tile>
          <v-list-tile-title>
            <router-link to="/Balor/SegmentProfile">Segment Profile</router-link>
          </v-list-tile-title>
        </v-list-tile>
      </v-list-item>
      <v-list-item>
        <v-list-tile>
          <v-list-tile-title>
            <router-link to="/JobHistory">Balor History</router-link>
          </v-list-tile-title>
        </v-list-tile>
      </v-list-item>
    </v-list>
  </div>

  <div v-else-if="$store.state.appmodule === 'Decile'">
    <v-list>
      <v-list-item>
        <v-list-tile>
          <v-list-tile-title>
            <router-link to="/Decile/NewDecile">New Decile</router-link>
          </v-list-tile-title>
        </v-list-tile>
      </v-list-item>
    </v-list>
  </div>

  <div v-else>
    <p>Leave empty until module is selected? </p>
  </div>
</template>

<script>

  export default{
    name: 'sideMenu'
  }

</script>

<style scoped>
  .nav-link{
    color: #354052;
  }

</style>
