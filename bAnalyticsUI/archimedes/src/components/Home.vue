<template style="height:100%;">
  <v-container fluid class="home pa-0" style="height:100%;">
    <v-layout row wrap class="mt-0 mb-3 pa-0" style="height:100%;">

      <v-flex xs12 >
        <div class="marT_img">
          <!-- Image Row -->
          <v-layout wrap row class="accent elevation-1 pa-3 ma-0 bg_hero">
              <v-flex xs8 sm9>
                <div>
                  <h4 class="white--text text-xs-left pb-0 mb-0">Welcome to Archimedes</h4>
                  <h5 class="white--text text-xs-left pb-0 mb-0 grey--text lighten-2">UserName</h5>
                </div>
                <div style="margin-top:50px;">
                  <v-btn outline class="white--text ma-0">Power User</v-btn>
                </div>
              </v-flex>
              <v-flex xs4 sm3 class="date_border1">
                <v-layout wrap row fill-height class="date_border2">
                  <!--date-->
                  <v-flex xs12 class="primary card_crnr">
                    <div class="wrap1 text-xs-center">
                      <div class="wrap2 text-xs-center">
                        <v-icon x-large class="white--text pb-2">date_range</v-icon>
                        <h6 class="white--text mb-0">{{ this.monthArray[new Date().getMonth()] }} {{ new Date().getDate() }}, {{ new Date().getFullYear() }}</h6>
                      </div>
                    </div>
                  </v-flex>
                  <!--//date-->
                  <!--time
                  <v-flex xs6 class="success card_crnr">
                    <div class="wrap1 text-xs-center">
                      <div class="wrap2">
                        <v-icon x-large class="white--text">timer</v-icon>
                        <div class="white--text">time</div>
                      </div>
                    </div>
                  </v-flex>-->
                </v-layout>
              </v-flex>
          </v-layout>
          <!-- //Image Row -->
          <!-- App Modules Row -->
          <v-layout wrap row class="pl-4 pr-4 pt-3 pb-0">
              <v-flex xs12 class="pl-1 pr-1">
                <h5 class="grey--text">App <span class="primary--text">Modules</span></h5>
              </v-flex>
              <v-flex xs12 sm6 md3 class="pl-1 pr-1 pb-3" v-for="(item, index) in cardItems" :key="item.title">
                <v-card class="white mod_cards">
                  <v-card-row :class="item.titleColor">
                    <v-card-title class="pa-0 ma-0">
                      <div class="subheading pa-3 white--text">
                        <b>{{ item.title }}</b>
                      </div>
                    </v-card-title>
                  </v-card-row>
                  <v-card-text class="module_card_txt pl-3 pr-3 pt-3">
                    <p class="mb-0 subheading" v-html="item.description"></p>
                  </v-card-text>
                  <v-divider></v-divider>
                  <v-card-row class="pa-3">
                    <v-btn icon router :to="item.histLink" :class="item.titleColor" class="white--text ma-0">
                      <v-icon>history</v-icon>
                    </v-btn>
                    <v-spacer></v-spacer>
                    <v-btn router :to="item.inputLink" :class="item.titleColor" class="white--text ma-0">+ NEW INPUT</v-btn>
                  </v-card-row>
                </v-card>
              </v-flex>
          </v-layout>
          <!-- //App Modules Row -->
          <!-- Misc Info Row -->
          <v-layout wrap row class="pl-4 pr-4 pt-3 pb-3 mb-5">
              <v-flex xs12 class="pl-1 pr-1">
                <h5 class="grey--text">Misc <span class="primary--text">Info</span></h5>
              </v-flex>
              <v-flex xs12 sm6 md4 class="pl-1 pr-1 mb-2">
                <v-card class="white">
                  <v-card-row>
                    <v-card-title class="pa-0 ma-0">
                      <div class="subheading pa-3 primary--text">
                        <b>Links</b>
                      </div>
                    </v-card-title>
                  </v-card-row>
                  <v-card-text class="primary misc_card_txt">
                    <div class="body-2" v-for="item in linkItems">
                      <v-icon light class="pr-2">label</v-icon>
                      <a :href="item.site" target="_blank" class="white--text">{{item.name}}</a>
                    </div>
                  </v-card-text>
                </v-card>
              </v-flex>
          </v-layout>
          <!-- //Misc Info Row -->

      </div>
      </v-flex>

    </v-layout>
  </v-container>
</template>

<script>
export default {
  name: 'home',
  data () {
    return {
      monthArray: [ 'January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December' ],
      cardItems: [
        {
          title: 'Core Lifecycle',
          titleColor: 'error',
          description: 'Brierley+Partner’s propriety customer lifecycle segmentation provides you with visibility into the similarities and differences across customers at different lifecycle phases. Compare core Loyalty KPIs across segments, and view trends in lifecycle migration by defining time period subsets within your data. A product index analysis then profiles purchase behavior across segments and time, identifying which products resonate the most within segments and which segments to target for promotional activities.',
          inputLink: '/Lifecycle/NewLifecycle',
          histLink: '/Lifecycle'
        },
        {
          title: 'BALOR',
          titleColor: 'primary',
          description: 'Visualize the purchase cadence of your customer base and identify the net effects of customer acquisition or retention activities against attrition. With this strategic analysis of customer purchase behavior, you can analyze trends in core Loyalty KPIs and quantify the incremental value of customer retention to your bottom line.',
          inputLink: '/Balor/NewBalor',
          histLink: '/Balor'
        },
        {
          title: 'Pareto',
          titleColor: 'success',
          description: 'Define and analyze value-groups of customers (or stores) across operational metrics and compare the contribution per group relative to the whole. Select time period subsets for analysis in order to see how your customers (or stores) migrate through value-groups over time. And with product profiling, get the data you need to compare the top and bottom products across each value-group and construct strategies for converting low-value customers into high-value customers.',
          inputLink: '/Pareto/NewPareto',
          histLink: '/Pareto'
        },
        {
          title: 'b-Relevant',
          titleColor: 'accent',
          description: 'Bring an empirical approach to product bundling and recommendations through Brierley+Partner’s b-Relevant engine. Identify which products are expected to yield the best results for cross-sell and up-sell campaigns throughout the year or during a specific season. For a more targeted approach, mine your historic data to issue personalized recommendations for all customers based upon a product recently purchased or currently in the cart. Realize a lift in sales by curating the product discovery process for your customers.',
          inputLink: '/bRelevant/NewBRelevant',
          histLink: '/bRelevant/NewBRelevant'
        }
      ],
      linkItems: [
        { name: 'LoyaltyOnDemand', site: 'http://www.brierley.com/technology-products/brierley-loyaltyondemand' },
        { name: 'Campaign Module', site: 'http://www.brierley.com/technology-products/brierley-campaign' },
        { name: 'Survey Module', site: 'http://www.brierley.com/technology-products/brierley-survey' },
        { name: 'Digital Messaging Suite', site: 'http://www.brierley.com/technology-products/brierley-digital-messaging-suite' },
        { name: 'Data360', site: 'http://www.brierley.com/technology-products/brierley-data360' }
      ]
    }
  },
  mounted () {
    this.$store.commit('switchApp', {module: 'Dashboard'})
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.marT_img {margin-top:59px; width:100%;}
.wrap1 {display:table; width:100%; height:100%;}
.wrap2 {display:table-cell; vertical-align:middle; margin:0 auto;}
.module_card_txt {height:300px;overflow-y: scroll;}
.module_card_txt a {cursor:pointer;}
.mod_contain1 {height:240px; overflow:hidden;}
.mod_contain2 {height:auto; overflow:visible;}
.date_border1 {border:5px solid #ffffff;}
.date_border2 {border:3px solid #8eac1d;}
.misc_card_txt {min-height:150px;}
/*.bg_hero {
  background-image: url("/static/bp_site_contact_us_hero1.jpg");
  background-repeat: no-repeat;
  background-size: cover;
  -webkit-background-size: cover;
  -moz-background-size: cover;
  -o-background-size: cover;
  background-position: center 60%;
  width: 100%;
  height: 100%;
}*/
/*.bg{
  background-image: url("/static/bp_logo_reverse_color2.png");
  background-color: #eff3f6;
  background-repeat: no-repeat;
  background-size: cover;
  -webkit-background-size: cover;
  -moz-background-size: cover;
  -o-background-size: cover;
  background-position: center top;
  width: 100%;
  height: 100%;
}*/
/* Tablets (portrait and landscape) ----------- */
@media only screen and (min-width: 481px) and (max-width: 960px) {
  /* Styles */
  .module_card_txt {min-height:260px !important;}
  .misc_card_txt {min-height:155px !important;}
}
/* Smartphones (portrait and landscape) ----------- */
@media only screen and (min-width: 320px) and (max-width: 480px) {
  /* Styles */
  .mob_break1 {display:none;}
  .mob_break2 {display:none;}
  .module_card_txt {min-height:200px !important;}
}
</style>
