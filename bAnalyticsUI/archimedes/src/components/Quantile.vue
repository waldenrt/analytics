<template>
  <!--main container-->
  <main class="pt-5">
    <v-container fluid>
      <v-layout row-sm column child-flex-sm>
        <v-flex xs12>
          <v-card flat>
            <!--<p>first line</p>
            <p>we can put notices here and they will be always visible from everywhere in the app..</p>-->
            <QuantileTabs></QuantileTabs>
            <router-view></router-view>
          </v-card>
        </v-flex>
      </v-layout>
    </v-container>
  </main>
  <!--//main container-->
</template>

<script>
  import QuantileTabs from './QuantileTabs.vue'

  export default {
    name: 'quantile',
    components: {
      QuantileTabs
    }
  }

</script>
