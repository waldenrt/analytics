<template>
  <v-container fluid class="quantileProducts pl-3 pr-3 mb-3">
    <!-- ASHA STUFF HERE -->

    <!--// ASHA STUFF HERE -->

    <!-- =====ROW1===== -->
    <v-layout row wrap class="pt-0 mt-0">
      <v-flex xs12>
        <v-card class="pa-0 ma-0 grey lighten-2">
          <v-card-title primary-title class="primary">
            <h6 class="white--text text-xs-left mb-0">Product Index</h6>
          </v-card-title>
          <v-layout row wrap>
            <!--Dropdown1-->
            <v-flex xs12 sm2>
              <v-card flat class="pl-2 pr-2 pt-0 pb-0 grey lighten-2">
                <v-layout row wrap>
                  <v-flex xs12>
                    <div class="primary--text text-xs-left pl-0 pr-0 pb-0 pt-2">
                      Select Time<br/>Period:
                    </div>
                  </v-flex>
                  <v-flex xs12>
                    <v-card class="white pa-0">
                      <!--SWAP OUT DROPDOWN-->
                      <v-select v-bind:items="tpArray"
                                v-model="tpSelect"
                                label="Select Time Period"
                                single-line
                                bottom
                                v-on:input="selectTP()"
                                class="pl-1 pr-1 mt-1 mb-2">
                      </v-select>
                      <!--//SWAP OUT DROPDOWN-->
                    </v-card>
                  </v-flex>
                </v-layout>
              </v-card>
            </v-flex>
            <!--//Dropdown1-->
            <!--Dropdown2-->
            <v-flex xs12 sm2>
              <v-card flat class="pl-2 pr-2 grey lighten-2">
                <v-layout row wrap>
                  <v-flex xs12>
                    <div class="primary--text text-xs-left pl-0 pr-0 pb-0 pt-2">
                      Select Product<br/> Department Description:
                    </div>
                  </v-flex>
                  <v-flex xs12>
                    <v-card class="white">
                      <!--SWAP OUT DROPDOWN-->
                      <v-select
                          v-bind:items="prodArray"
                          v-model="prodSelect"
                          label="Select Product Category"
                          single-line
                          bottom
                          multiple
                          v-on:input="selectProds()"
                          class="pl-1 pr-1 mt-1 mb-2">
                      </v-select>
                      <!--//SWAP OUT DROPDOWN-->
                    </v-card>
                  </v-flex>
                </v-layout>
              </v-card>
            </v-flex>
            <!--//Dropdown2-->
            <!--Dropdown3-->
            <v-flex xs12 sm2>
              <v-card flat class="pl-2 pr-2 grey lighten-2">
                <v-layout row wrap>
                  <v-flex xs12>
                    <div class="primary--text text-xs-left pl-0 pr-0 pb-0 pt-2">
                      Select<br/>Segment(s):
                    </div>
                  </v-flex>
                  <v-flex xs12>
                    <v-card class="white">
                      <!--SWAP OUT DROPDOWN-->
                      <v-select
                          v-bind:items="segmentArray"
                          v-model="segSelect"
                          label="Select Segments"
                          multiple
                          single-line
                          bottom
                          v-on:input="selectSegment()"
                          class="pl-1 pr-1 mt-1 mb-2">
                      </v-select>
                      <!--//SWAP OUT DROPDOWN-->
                    </v-card>
                  </v-flex>
                </v-layout>
              </v-card>
            </v-flex>
            <!--//Dropdown3-->
            <!--Dropdown4-->
            <v-flex xs12 sm2>
              <v-card flat class="pl-2 pr-2 grey lighten-2">
                <v-layout row wrap>
                  <v-flex xs12>
                    <div class="primary--text text-xs-left pl-0 pr-0 pb-0 pt-2">
                      Select Top<br/>N Products:
                    </div>
                  </v-flex>
                  <v-flex xs10>
                    <v-card class="white">
                      <!--SWAP OUT DROPDOWN-->
                      <v-text-field
                          name="topProducts"
                          label="How many products"
                          id="topN"
                          v-model="topN"
                          v-on:keyup.enter.native="topProds()"
                          class="pl-1 pr-1 mt-1 mb-2">
                      </v-text-field>
                      <!--//SWAP OUT DROPDOWN-->
                    </v-card>
                  </v-flex>
                  <v-flex xs2>
                    <v-btn light primary default v-on:click.native="topProds()" class="ma-0">Filter</v-btn>
                  </v-flex>
                </v-layout>
              </v-card>
            </v-flex>
            <!--//Dropdown4-->
            <!--Legend-->
            <v-flex xs12 sm4>
              <v-card flat class="pl-2 pr-2 grey lighten-2">
                <v-layout row wrap>
                  <v-flex xs12>
                    <div class="primary--text text-xs-center pl-0 pr-0 pb-0 pt-2">
                      Segment Legend:
                    </div>
                  </v-flex>
                </v-layout>
              </v-card>
            </v-flex>
            <!--//Legend-->
          </v-layout>
        </v-card>
      </v-flex>
    </v-layout>
    <!-- //=====ROW1===== -->
    <!-- =====ROW2===== -->
    <v-layout wrap row>
      <v-flex xs12 class="pt-0 mt-0">
        <v-card class="pl-3 pr-3 pt-1 pb-1">
          <div class="title primary--text text-xs-center pa-1"><em>Product Product Product Product Time Period <span
              class="grey--text darken-2">1</span> for Segments <span class="grey--text darken-2">All</span></span></em>
          </div>
        </v-card>
      </v-flex>
    </v-layout>
    <!-- //=====ROW2===== -->
    <!-- =====ROW3===== -->
    <v-layout wrap row class="mt-3">
      <v-flex xs4>
        <v-card class="white pl-3 pr-3 pt-1 pb-1">
          <h6 class="primary--text text-xs-center pa-1 mb-0 subhead">Title 1</h6>
          <v-layout wrap row>
            some text here
          </v-layout>
        </v-card>
      </v-flex>
      <v-flex xs8>
        <v-card class="white pl-3 pr-3 pt-1 pb-1">
          <h6 class="primary--text text-xs-center pa-1 mb-0">Title 2</h6>
          <v-layout wrap row>
            some text here
          </v-layout>
        </v-card>
      </v-flex>
    </v-layout>
    <!-- //=====ROW3===== -->
  </v-container>
</template>

<script>

  export default {
    name: 'bRelevantProducts'
  }

</script>

<style scoped>

</style>
