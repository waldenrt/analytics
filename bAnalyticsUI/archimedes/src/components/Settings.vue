<template>
  <v-container fluid class="settings pa-0 mb-3">
    <v-layout row wrap class="mt-0 mb-5 pa-0" style="height:100%;">

      <v-flex xs12>
        <div class="marT_img">
          <!-- Hero Row -->
          <v-layout wrap row class="accent elevation-1 pa-3 ma-0 bg_hero">
              <v-flex xs12>
                <div class="contain">
                  <div>
                    <h3 class="white--text text-xs-center pb-0 mb-0">Settings</h3>
                    <!--<h5 class="grey--text text-xs-center pb-0 mb-0">subhead</h5>-->
                  </div>
                </div>
              </v-flex>
          </v-layout>
          <!-- //Hero Row -->
          <!-- Info Row 1 -->
          <v-card class="white ma-3">
          <form name="settingsForm" @submit.prevent="validateBeforeSubmit()">
          <v-layout wrap row class="pl-4 pr-4 pt-3 pb-0">
              <v-flex xs12>
                <p class="grey--text body-2">Personal Settings</p>
              </v-flex>
              <v-flex xs12 class="pb-3">
                <!--FIELD-->
                <div class="xs12 pb-3">
                  <!-- first_name -->
                  <v-layout row>
                    <v-flex xs5 sm2>
                      <label class="body-2">First Name</label>
                    </v-flex>
                    <v-flex xs7 sm6>
                      <v-text-field
                          label="First Name"
                          v-model="first_name"
                          class="ma-0 input-group--focused custom_input"
                          single-line
                          hide-details
                          data-vv-name="first_name"
                          v-validate="'required'">
                      </v-text-field>
                    </v-flex>
                  </v-layout>

                  <!-- last_name -->
                  <v-layout row class="pt-2">
                    <v-flex xs5 sm2>
                      <label class="body-2">Last Name</label>
                    </v-flex>
                    <v-flex xs7 sm6>
                      <v-text-field
                          label="Last Name"
                          v-model="last_name"
                          class="ma-0 input-group--focused custom_input"
                          single-line
                          hide-details
                          data-vv-name="last_name"
                          v-validate="'required'">
                      </v-text-field>
                    </v-flex>
                  </v-layout>

                  <!-- email -->
                  <v-layout row class="pt-2">
                    <v-flex xs5 sm2>
                      <label class="body-2">Email</label>
                    </v-flex>
                    <v-flex xs7 sm6>
                      <v-text-field
                          label="Email"
                          v-model="email"
                          class="ma-0 input-group--focused custom_input"
                          single-line
                          hide-details
                          data-vv-name="email"
                          v-validate="'required'">
                      </v-text-field>
                    </v-flex>
                  </v-layout>

                  <!-- password -->
                  <v-layout row class="pt-2">
                    <v-flex xs5 sm2>
                      <label class="body-2">Password</label>
                    </v-flex>
                    <v-flex xs7 sm6>
                      <v-text-field
                          label="Password"
                          v-model="password"
                          class="ma-0 input-group--focused custom_input"
                          single-line
                          hide-details
                          data-vv-name="password"
                          v-validate="'required'">
                      </v-text-field>
                    </v-flex>
                  </v-layout>

                  <!-- confirm -->
                  <v-layout row class="pt-2">
                    <v-flex xs5 sm2>
                      <label class="body-2">Confirm</label>
                    </v-flex>
                    <v-flex xs7 sm6>
                      <v-text-field
                          label="Confirm"
                          v-model="confirm"
                          class="ma-0 input-group--focused custom_input"
                          single-line
                          hide-details
                          data-vv-name="confirm"
                          v-validate="'required'">
                      </v-text-field>
                    </v-flex>
                  </v-layout>

                  <!-- phone -->
                  <v-layout row class="pt-2">
                    <v-flex xs5 sm2>
                      <label class="body-2">Phone</label>
                    </v-flex>
                    <v-flex xs7 sm6>
                      <v-text-field
                          label="Phone"
                          v-model="phone"
                          class="ma-0 input-group--focused custom_input"
                          single-line
                          hide-details
                          data-vv-name="phone"
                          v-validate="'required'">
                      </v-text-field>
                    </v-flex>
                  </v-layout>

                </div>
                <!--//FIELD-->
              </v-flex>
          </v-layout>
          <v-divider></v-divider>
          <v-layout row wrap class="pl-4 pr-4 pt-3 pb-0">
            <v-flex xs12>
              <p class="grey--text body-2">Account Settings</p>
            </v-flex>
            <v-flex xs12 class="pb-3">
              <!--FIELD-->
              <div class="xs12 pb-3">
                <!-- email notify -->
                <v-layout row>
                  <v-flex xs12 sm8>
                    <v-checkbox
                      v-bind:label="`Email Notification`"
                      v-model="ex1"
                      hide-details
                      data-vv-name="email_check"
                      v-validate="'required'"
                      class="info--text ma-0 custom_input"></v-checkbox>
                  </v-flex>
                </v-layout>

                <!-- SMS notify -->
                <v-layout row class="pt-2">
                  <v-flex xs12 sm8>
                    <v-checkbox
                    v-bind:label="`SMS Notification`"
                    v-model="ex2"
                    hide-details
                    data-vv-name="sms_check"
                    v-validate="'required'"
                    class="info--text ma-0 custom_input"></v-checkbox>
                  </v-flex>
                </v-layout>

              </div>
              <!--//FIELD-->
            </v-flex>
          </v-layout>
          <v-divider></v-divider>
          <!-- LOGIN -->
          <v-layout class="xs12 ma-0 pl-3 pr-3 pt-3 pb-3">
            <v-flex xs12 sm6>
              <v-spacer></v-spacer>
              <div class="inliner pr-3">
                <v-btn
                  @click.native="validateBeforeSubmit()"
                  class="white--text pa-0 ma-0">Cancel</v-btn>
              </div>
              <div class="inliner">
                <v-btn
                  @click.native="validateBeforeSubmit()"
                  class="success white--text pa-0 ma-0">Save</v-btn>
              </div>
            </v-flex>
          </v-layout>
          <!-- //LOGIN -->
          </form>
          </v-card>
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
      ex1: true,
      ex2: false,
      ex3: true,
      ex4: false
    }
  },
  mounted () {
    this.$store.commit('switchApp', {module: 'Settings'})
  },
  methods: {
    validateBeforeSubmit () {
      var vm = this
      this.$validator.validateAll().then((result) => {
        if (result) {
          alert('Form Submitted!')
          vm.disabledBtn = false
          return
        } else {
          alert('Correct the errors!')
          vm.disabledBtn = true
        }
      })
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.inliner {display:inline-block;}
.marT_img {margin-top:59px;width:100%;}
.card_crnr {border-top-right-radius: 20px;border-bottom-left-radius: 20px;}
.wrap1 { display: table; width: 100%; height: 100%; }
.wrap2 { display: table-cell; vertical-align: middle; margin:0 auto; }
.bg_hero { min-height: 200px; }
.bg_hero .contain {
  display:table;
  margin:0 auto;
  height:100%;
}
.bg_hero .contain div {
  display:table-cell;
  vertical-align: middle;
}
</style>
