<template style="height:100%;">
  <v-container fluid class="login pa-0" style="height:100%;">
    <v-layout row wrap class="ma-0 pa-0" style="height:100%;">
      <!-- COL1 -->
      <v-flex xs6 class="ma-0 pa-0" style="background-color:#006984;">
          <div class="grid-container1">
            <div class="grid-item"></div>
            <div class="grid-item">
              <!-- =====row1===== -->
              <div class="row1"></div>
              <!-- =====row2===== -->
              <div class="row2">
                <div class="inner_row1 text-xs-left pl-4">
                  <a href="http://www.brierley.com/" target="_blank"><img src="/static/bp_logo_reverse_color2.png" class="logo" /></a>
                </div>
                <div class="inner_row2 pt-5 pb-5 pl-4 pr-4 card1">
                  <h5 class="white--text text-xs-left mb-2">Welcome to Brierley Archimedes<sup>&reg;</sup></h5>
                  <p class="subheading text-xs-left white--text mb-0">Subheading</p>
                </div>
                <div class="inner_row3 text-xs-left pl-4"></div>
              </div>
              <!-- =====row3===== -->
              <div class="row3"></div>
            </div>
          </div>
      </v-flex>
      <!-- //COL1 -->
      <!-- COL2 -->
      <v-flex xs6 class="info ma-0 pa-0">
          <div class="grid-container2">
            <div class="grid-item">
              <!-- =====row1===== -->
              <div class="row1"></div>
              <!-- =====row2===== -->
              <div class="row2">
                <div class="inner_row1 text-xs-left pl-4">
                  <h5 class="white--text text-xs-left mb-0 pt-4">Sign in to your account</h5>
                </div>
                <div class="inner_row2 pl-4 pr-4 pt-4 card2 white">
                  <v-layout row wrap class="pl-3 pr-5">
                    <form name="inputForm" @submit.prevent="validateBeforeSubmit()">
                  <div class="pa-0">
                    <!--USERNAME-->
                    <div class="xs12 mt-3 mb-3">
                      <label class="body-2">Username</label>
                      <v-layout class="xs12 ma-0">
                        <v-text-field
                            label="Username"
                            v-model="username"
                            class="white mt-0 mb-0 ml-0 input-group--focused custom_input"
                            single-line
                            hide-details
                            data-vv-name="username"
                            v-validate="'required'"
                            id="username">
                        </v-text-field>
                      </v-layout>
                      <v-layout class="xs12 ma-0">
                        <small v-show="vErrors.has('username')" class="error--text">* {{ vErrors.first('username') }}</small>
                      </v-layout>
                    </div>
                    <!--//USERNAME-->

                    <!--PASSWORD-->
                    <div class="xs12 mt-3 mb-3">
                      <label class="body-2">Password</label>
                      <v-layout class="xs12 ma-0">
                        <v-text-field
                            label="Password"
                            v-model="password"
                            class="white mt-0 mb-0 ml-0 input-group--focused custom_input"
                            single-line
                            hide-details
                            data-vv-name="password"
                            v-validate="'required'"
                            id="username">
                        </v-text-field>
                      </v-layout>
                      <v-layout class="xs12 ma-0">
                        <small v-show="vErrors.has('password')" class="error--text">* {{ vErrors.first('password') }}</small>
                      </v-layout>
                    </div>
                    <!--//PASSWORD-->
                  </div>
                  </form>
                  </v-layout>
                </div>
                <div class="inner_row3 text-xs-left pl-4">
                  <!-- LOGIN -->
                  <v-layout class="xs12 ma-0 pt-4">
                    <div class="inliner">
                      <v-btn
                        @click.native="validateBeforeSubmit()"
                        class="white--text pa-0 ma-0 log_btn">Log In</v-btn>
                    </div>
                    <div class="inliner">
                      <p class="caption white--text mb-0 pt-3 pl-3">
                        <a href="#/PasswordReset" class="white--text">Forgot Username or Password?</a>
                      </p>
                    </div>
                  </v-layout>
                  <!-- //LOGIN -->
                </div>
              </div>
              <!-- =====row3===== -->
              <div class="row3"></div>
            </div>
            <div class="grid-item"></div>
          </div>
      </v-flex>
      <!-- //COL2 -->
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
.centereddiv{
  position:relative;
  width: 100%;
  top: 50%;
  left: 50%;
  transform: translate(-52%, -50%);
  max-width: 450px;
}
.inliner {display:inline-block;}
.grid-container1{
  display: grid;
  grid-template-columns: 1fr 2fr;
  grid-template-rows: 100%;
  height: 100%;
}
.grid-container1 .rows {grid-row:1 / 4;}
.logo {width:auto; height:75px;}
.card1 {
  border-top-left-radius:4px;
  border-bottom-left-radius:4px;
  background-color:#005e76;
}

.grid-container2{
  display: grid;
  grid-template-columns: 2fr 1fr;
  grid-template-rows: 100%;
  height: 100%;
}

.grid-container2 .rows {grid-row:1 / 4;}

.row1, .row3 {height:20%;}

.row2 {height:60%;}

.inner_row1 {min-height:90px;}

.inner_row2 {min-height:230px;}

.inner_row2 form {width:100%;}

.card2 {
  border-top-right-radius:4px;
  border-bottom-right-radius:4px;
}
.log_btn {background-color:#005e76;}
</style>
