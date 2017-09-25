<template>
  <!--main container-->
  <main class="pt-5">
    <v-container fluid>
      <v-layout row-sm column child-flex-sm>
        <v-flex xs12>
          <div>
            <balor-breadcrumbs></balor-breadcrumbs>
            <router-view></router-view>
          </div>
        </v-flex>
      </v-layout>
    </v-container>
  </main>
  <!--//main container-->
</template>

<script>
  import BalorBreadcrumbs from './BalorBreadcrumbs.vue'
  // TODO update to lifecycle tabs when they are completed....

  export default {
    name: 'lifecycle',
    components: {
      BalorBreadcrumbs
    }
  }

</script>
