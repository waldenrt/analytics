<template>
<!--main container-->
<main>
  <v-container fluid class="ma-0 pt-0 pl-0 pr-0 pb-0" style="height:100%;">
    <v-layout class="ml-0 mr-0" style="height:100% !important;">
      <v-flex xs12 class="pa-0" style="height:100%;">
        <v-card flat style="height:100%;">
            <!--<p>first line</p>
            <p>we can put notices here and they will be always visible from everywhere in the app..</p>-->
          <router-view></router-view>

        </v-card>
      </v-flex>
    </v-layout>
  </v-container>
</main>
<!--//main container-->
</template>

<script>
  export default {
    name: 'balor',
    mounted () {
      this.$store.commit('switchApp', {module: 'BALOR'})
    }
  }

</script>
