<template>
<!--main container-->
<main class="pt-5">
  <v-container fluid>
    <v-layout row-sm column child-flex-sm>
      <v-flex xs12>
        <div>
          <!--<p>first line</p>
          <p>we can put notices here and they will be always visible from everywhere in the app..</p>-->
          <balorTabs></balorTabs>
          <router-view></router-view>
        </div>
      </v-flex>
    </v-layout>
  </v-container>
</main>
<!--//main container-->
</template>

<script>
  import balorTabs from './balorTabs.vue'

  export default {
    name: 'balor',
    components: {
      balorTabs
    }
  }

</script>
