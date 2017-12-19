<template>
  <v-container fluid class="home pa-0" style="height:100%;">
    <v-layout row wrap class="mt-0 mb-3 pa-0" style="height:100%;">

      <v-flex xs12 >
        <div class="marT_img">
          <!-- Image Row -->
          <v-layout wrap row class="accent elevation-1 pa-3">
              <v-flex xs10>
                <div>
                  <h3 class="primary--text text-xs-left pb-0 mb-0">Welcome to [Application Name]</h3>
                  <h5 class="white--text text-xs-left pb-0 mb-0">UserName</h5>
                </div>
                <div style="margin-top:50px;">
                  <v-btn outline class="white--text ma-0">Power User</v-btn>
                </div>
              </v-flex>
              <v-flex xs2>
                <v-layout wrap row>
                  <!--date-->
                  <v-flex xs6>
                    <div class="white--text">Date</div>
                  </v-flex>
                  <!--//date-->
                  <!--time-->
                  <v-flex xs6>
                    <div class="white--text">Time</div>
                  </v-flex>
                  <!--//time-->
                </v-layout>
              </v-flex>
          </v-layout>
          <!-- //Image Row -->
          <!-- Input Pages Row -->
          <v-layout wrap row class="pl-3 pr-3 pt-3 pb-0">
              <v-flex xs12 class="pl-1 pr-1">
                <h4 class="grey--text">Input <span class="primary--text"><b>Pages</b></span></h4>
              </v-flex>
              <v-flex xs12 sm6 md3 class="pl-1 pr-1 pb-3" v-for="item in cardItems">
                <v-card class="white input_card">
                  <v-card-row :class="item.titleColor">
                    <v-card-title class="pa-0 ma-0">
                      <div class="subheading pa-3 white--text">
                        <b>{{ item.title }}</b>
                      </div>
                    </v-card-title>
                  </v-card-row>
                  <v-card-text>
                    {{ item.description }}
                  </v-card-text>
                  <v-card-row class="pl-3 pr-3 pt-0 pb-3">
                    <v-btn router :to="item.inputLink" :class="item.titleColor" class="white--text ma-0">+ NEW INPUT</v-btn>
                  </v-card-row>
                </v-card>
              </v-flex>
          </v-layout>
          <!-- //Input Pages Row -->
          <!-- Input Pages Row -->
          <v-layout wrap row class="pa-3">
              <v-flex xs12 class="pl-1 pr-1">
                <h4 class="grey--text">Misc <span class="primary--text"><b>Info</b></span></h4>
              </v-flex>
              <v-flex xs4 class="pl-1 pr-1">
                <v-card class="white">
                  <v-card-row>
                    <v-card-title class="pa-0 ma-0">
                      <div class="subheading pa-3 primary--text">
                        <b>Links</b>
                      </div>
                    </v-card-title>
                  </v-card-row>
                  <v-card-text class="primary">
                    <div class="body-2" v-for="item in linkItems">
                      <v-icon light class="pr-2">label</v-icon>
                      <a :href="item.site" target="_blank" class="white--text">{{item.name}}</a>
                    </div>
                  </v-card-text>
                </v-card>
              </v-flex>
              <v-flex xs4 class="pl-1 pr-1">
                <v-card class="white">
                  <v-card-row>
                    <v-card-title class="pa-0 ma-0">
                      <div class="subheading pa-3 error--text"><b>Job History</b></div>
                    </v-card-title>
                  </v-card-row>
                  <v-card-text class="error">
                    <div class="body-2" v-for="item in historyItems">
                      <v-icon class="accent--text pr-2">label</v-icon>
                      <a router :to="item.histLink" target="_blank" class="white--text">{{item.name}}</a>
                    </div>
                  </v-card-text>
                </v-card>
              </v-flex>
          </v-layout>
          <!-- //Input Pages Row -->

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
      cardItems: [
        {
          title: 'Balor',
          titleColor: 'primary',
          description: 'Product Description Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed varius nunc tempor tellus efficitur accumsan. Suspendisse accumsan neque.',
          inputLink: '/Balor/NewBalor'
        },
        {
          title: 'Pareto',
          titleColor: 'success',
          description: 'Product Description Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed varius nunc tempor tellus efficitur accumsan. Suspendisse accumsan neque.',
          inputLink: '/Pareto/NewPareto'
        },
        {
          title: 'Core Lifecycle',
          titleColor: 'error',
          description: 'Product Description Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed varius nunc tempor tellus efficitur accumsan. Suspendisse accumsan neque.',
          inputLink: '/Lifecycle/NewLifecycle'
        },
        {
          title: 'bRelevant',
          titleColor: 'accent',
          description: 'Product Description Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed varius nunc tempor tellus efficitur accumsan. Suspendisse accumsan neque.',
          inputLink: '/bRelevant/NewBRelevant'
        }
      ],
      linkItems: [
        {
          name: 'LoyaltyOnDemand',
          site: 'http://www.brierley.com/technology-products/brierley-loyaltyondemand'
        },
        {
          name: 'Campaign Module',
          site: 'http://www.brierley.com/technology-products/brierley-campaign'
        },
        {
          name: 'Survey Module',
          site: 'http://www.brierley.com/technology-products/brierley-survey'
        },
        {
          name: 'Digital Messaging Suite',
          site: 'http://www.brierley.com/technology-products/brierley-digital-messaging-suite'
        },
        {
          name: 'Data360',
          site: 'http://www.brierley.com/technology-products/brierley-data360'
        }
      ],
      historyItems: [
        {
          name: 'Balor Job History',
          histLink: '/bRelevant/NewBRelevant'
        },
        {
          name: 'Pareto Job History',
          histLink: '/bRelevant/NewBRelevant'
        },
        {
          name: 'Core Lifecycle Job History',
          histLink: '/bRelevant/NewBRelevant'
        },
        {
          name: 'bRelevant Job History',
          histLink: '/bRelevant/NewBRelevant'
        }
      ]
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.marT_img {margin-top:59px;width:100%;}
.input_card {border-top-right-radius: 20px;border-bottom-left-radius: 20px;}
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
</style>
