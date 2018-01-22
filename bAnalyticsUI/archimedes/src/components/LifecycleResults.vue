<template>
  <!--main container-->
  <main>
    <v-container fluid class="ma-0 pt-0 pl-0 pr-0">
      <v-layout row-sm column child-flex-sm class="ml-0 mr-0">
        <v-flex xs12 class="pa-0">
          <v-card flat>
            <!--<p>first line</p>
            <p>we can put notices here and they will be always visible from everywhere in the app..</p>-->
            <LifecycleTabs></LifecycleTabs>
            <router-view></router-view>
          </v-card>
        </v-flex>
      </v-layout>
    </v-container>
  </main>
  <!--//main container-->
</template>

<script>
  import LifecycleTabs from './LifecycleTabs.vue'
  // TODO update to lifecycle tabs when they are completed....

  export default {
    name: 'lifecycle',
    components: {
      LifecycleTabs
    },
    mounted () {
      this.$store.commit('switchApp', {module: 'Lifecycle'})
    }
  }

</script>
