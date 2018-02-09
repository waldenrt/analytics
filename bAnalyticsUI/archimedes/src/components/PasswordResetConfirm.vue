<template style="height:100%;">
  <v-container fluid class="passwordReset pa-0 info" style="height:100%;">
    <v-layout row wrap class="ma-0 pa-0" style="height:100%;">
      <!--COLUMN-->
      <v-flex xs12 class="ma-0 pa-0">
        <v-layout row wrap class="pl-0 pr-0" style="height:100%;">
          <v-flex xs12 class="pl-0 pr-0 mt-0">
            <div class="centereddiv">
              <div class="text-xs-center" style="margin:0 auto;">
                <a href="http://www.brierley.com/" target="_blank">
                  <img src="/static/bp_logo_reverse_color2.png" class="logo" />
                </a>
              </div>
              <h5 class="white--text text-xs-center mb-1 pa-3">We sent you a reset password link to your email.</h5>
              <form name="inputForm" @submit.prevent="validateBeforeSubmit()">
              <!-- RESET BUTTON -->
              <v-layout row wrap class="xs12 ml-0 mr-0 mb-0 mt-4 text-xs-center">
                  <v-btn
                    router
                    to="/Login"
                    primary
                    class="success
                    white--text mb-0"
                    style="margin:0 auto;"><v-icon class="white--text">keyboard_arrow_left</v-icon>Back to Login</v-btn>
              </v-layout>
              <!-- //RESET BUTTON -->
              </form>
              <div class="text-xs-center mt-4">
                <v-layout row wrap class="text-xs-center">
                  <b1 class="text-xs-center white--text" style="margin:0 auto;">All Rights Reserved &copy; {{ new Date().getFullYear() }}</b1>
                </v-layout>
              </div>
            </div><!--//centereddiv-->
          </v-flex>
        </v-layout>
      </v-flex>
      <!--//COLUMN-->
    </v-layout>
  </v-container>
</template>

<script>
  export default {
    name: 'passwordResetConfirmation',
    data () {
      return {
      }
    },
    methods: {
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.contain_div{max-width: 600px;}
.logo{width:150px;margin:0 auto;}
.centereddiv{
  position:relative;
  width: 100%;
  top: 47%;
  left: 50%;
  transform: translate(-52%, -50%);
  max-width: 550px;
}
</style>
