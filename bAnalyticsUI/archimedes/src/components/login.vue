<template style="height:100%;">
  <v-container fluid class="login pa-0 marT60" style="height:100%;">
    <v-layout row wrap class="mt-0 mb-3 pa-0" style="height:100%;">
      <!--COLUMN1-->
      <v-flex xs12 sm6 class="ma-0 pt-0 pl-5 pr-5 bg">
        <div class="contain_div pt-5">
          <div class="mar1">
            <img src="/static/bp_logo_horz_color.png" class="logo" alt="B+P logo" />
          </div>
          <div class="mar2">
            <h5>Global Loyalty + CRM Innovators</h5>
          </div>
          <div class="mar3">
            <h5>The Leader in Loyalty brings a new analytics suite designed specifically with marketers in&nbsp;mind.</h5>
          </div>
        </div>
      </v-flex>
      <!--//COLUMN1-->
      <!--COLUMN2-->
      <v-flex xs12 sm6 class="white ma-0 pa-0">
        <v-layout row wrap class="pl-0 pr-0" style="height:100%;">
          <v-flex xs10 offset-xs1 class="pl-0 pr-0 mt-0">
            <div class="centereddiv">
                  <h3 class="primary--text text-xs-center mb-3 pa-3">Archimedes</h3>
              <v-card>
                <v-card-title primary-title class="primary">
                  <h6 class="white--text text-xs-left mb-0">Login</h6>
                </v-card-title>
                <form name="inputForm" @submit.prevent="validateBeforeSubmit()">
                <div class="pa-3">
                  <!--USERNAME-->
                  <div class="xs12 mt-3 mb-3">
                    <v-layout class="xs12 ma-0">
                      <v-text-field
                          label="Username"
                          class="white mt-0 mb-0 ml-0"
                          single-line
                          hide-details
                          data-vv-name="username"
                          v-validate="'required'"
                          id="username">
                      </v-text-field>
                      <v-icon dark class="primary white--text mt-0" style="padding:3px;">person</v-icon>
                    </v-layout>
                    <v-layout class="xs12 ma-0">
                      <small v-show="vErrors.has('username')" class="error--text">* {{ vErrors.first('username') }}</small>
                    </v-layout>
                  </div>
                  <!--//USERNAME-->

                  <!--PASSWORD-->
                  <div class="xs12 mt-3 mb-3">
                    <v-layout class="xs12 ma-0">
                      <v-text-field
                          label="Password"
                          class="white mt-0 mb-0 ml-0"
                          single-line
                          hide-details
                          data-vv-name="password"
                          v-validate="'required'"
                          id="username">
                      </v-text-field>
                      <v-icon dark class="primary white--text mt-0" style="padding:3px;">lock</v-icon>
                    </v-layout>
                    <v-layout class="xs12 ma-0">
                      <small v-show="vErrors.has('password')" class="error--text">* {{ vErrors.first('password') }}</small>
                    </v-layout>
                  </div>
                  <!--//PASSWORD-->

                  <!-- LOGIN -->
                  <v-layout class="xs12 ma-0">
                    <v-btn
                      @click.native="validateBeforeSubmit()"
                      primary
                      block
                      class="success white--text mb-0">Log In</v-btn>
                  </v-layout>
                  <!-- //LOGIN -->

                  <v-card-row xs12>
                    <v-flex xs12 class="pa-0">
                      <v-card-text class="ma-0 pa-0">
                        <v-checkbox
                          v-bind:label="`Remember Me`"
                          v-model="checkbox1"
                          hide-details
                          data-vv-name="checkbox1"
                          v-validate=""></v-checkbox>
                      </v-card-text>
                    </v-flex>
                  </v-card-row>
                  <div class="pa-0"><v-divider class="primary"></v-divider></div>
                  <v-card-row xs12 class="link_row pb-3 pt-3">
                    <v-flex xs6 class="pa-0 text-xs-left">
                      <a href="#">Forgot Username</a>
                    </v-flex>
                    <v-flex xs6 class="pa-0 text-xs-right">
                      <a href="#">Reset Password</a>
                    </v-flex>
                  </v-card-row>
                </div>
                </form>
              </v-card>
              <v-card class="mt-5 primary--text">
                <v-card-row xs12>
                  <a href="mailto:name@email.com?subject=Demo%20Please&amp;body=The%20body%20of%20the%20email" target="_top" style="width:100%; text-decoration:none;">
                    <div style="display:inline-block;">
                      <v-icon dark class="primary white--text ml-3 mt-0" style="padding:3px;">email</v-icon>
                    </div>
                    <div style="display:inline-block;">
                      <v-card-text style="display:inline-block;">Request a demo</v-card-text>
                    </div>
                  </a>
                </v-card-row>
              </v-card>
            </div>
          </v-flex>
        </v-layout>
      </v-flex>
      <!--//COLUMN2-->
    </v-layout>
  </v-container>
</template>

<script>
  export default {
    name: 'login',
    data () {
      return {
        checkbox1: false,
        username: '',
        password: '',
        disabledBtn: ''
      }
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
.bg{
  background-image: url("http://res.cloudinary.com/govimg/image/upload/v1481924007/5820287194e85008730d0d6d/CRM-Strategy-GetTheMost.jpg");
  background-color: #ffffff;
  background-repeat: no-repeat;
  background-size: cover;
  -webkit-background-size: cover;
  -moz-background-size: cover;
  -o-background-size: cover;
  background-position: center top;
  width: 100%;
  height: 100%;
}
.contain_div{max-width: 600px;}
.logo{width:250px;}
.mar1{margin-top:40px;}
.mar2{margin-top:100px;}
.mar3{margin-top:80px;}
.centereddiv{
  position:relative;
  width: 100%;
  top: 50%;
  left: 50%;
  transform: translate(-52%, -50%);
  max-width: 450px;
}
.link_row a{
  text-decoration: none;
  color:#354052;
}
</style>
