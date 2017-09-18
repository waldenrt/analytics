<template>
<v-container fluid class="mt-2 mb-0 pt-0 pb-0">
<v-card flat>
    <v-layout row wrap class="">
      <v-flex xs12 s12 md3 class="text-xs-center">
          <v-btn primary large router to="/Balor/NewBalor" class="white--text text-xs-left marTB20">+ NEW BALOR INPUT</v-btn>
      </v-flex>
      <v-flex xs12 s12 md9 class="pa-2">
        <v-tabs id="mobile-tabs-3" grow light>
          <v-tabs-bar slot="activators">
            <v-tabs-slider></v-tabs-slider>
            <v-tabs-item
              v-for="i in 3"
              :key="i"
              :href="'#mobile-tabs-3-' + i"
            >
              Item {{ i }}
            </v-tabs-item>
          </v-tabs-bar>
        </v-tabs>
      </v-flex>
    </v-layout>
</v-card>
</v-container>
</template>

<script>

  export default{
    name: 'balorTabs',
    data () {
      return {
        items: ['Item One', 'Item Seventeen', 'Item Five'],
        text: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit.'
      }
    },
    methods: {
      next () {
        this.active = this.tabs[(this.tabs.indexOf(this.active) + 1) % this.tabs.length]
      }
    }
  }

</script>

<style>
.bread_font, .bread_font a{
  font-size:17px;
}
.marTB20{margin-top:20px; margin-bottom:20px;}
</style>
