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
              <h5 class="white--text text-xs-center mb-1 pa-3">Reset Your Password</h5>
              <form name="inputForm" @submit.prevent="validateBeforeSubmit()">
              <v-card class="white">
                <div class="pa-3">
                  <!--USERNAME-->
                  <div class="xs12 mt-3 mb-3">
                    <label class="body-2">Username</label>
                    <v-layout class="xs12 ma-0">
                      <v-text-field
                          label="Username"
                          v-model="username"
                          class="white mt-0 mb-0 ml-0 custom_input"
                          single-line
                          hide-details
                          append-icon="person"
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
                    <label class="body-2">Email</label>
                    <v-layout class="xs12 ma-0">
                      <v-text-field
                          label="Email"
                          v-model="email"
                          class="white mt-0 mb-0 ml-0 custom_input"
                          single-line
                          hide-details
                          append-icon="email"
                          data-vv-name="email"
                          v-validate="'required|email'"
                          id="email">
                      </v-text-field>
                    </v-layout>
                    <v-layout class="xs12 ma-0">
                      <small v-show="vErrors.has('email')" class="error--text">* {{ vErrors.first('email') }}</small>
                    </v-layout>
                  </div>
                  <!--//PASSWORD-->
                </div>
              </v-card>

              <!-- RESET BUTTON -->
              <v-layout row wrap class="xs12 ml-0 mr-0 mb-0 mt-4 text-xs-center">
                  <v-btn
                    @click.native="validateBeforeSubmit()"
                    primary
                    class="success white--text mb-0"
                    style="margin:0 auto;">Request Password Reset Link</v-btn>
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
    name: 'passwordReset',
    data () {
      return {
        username: '',
        email: '',
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
.contain_div{max-width: 600px;}
.logo{width:150px;margin:0 auto;}
.centereddiv{
  position:relative;
  width: 100%;
  top: 47%;
  left: 50%;
  transform: translate(-52%, -50%);
  max-width: 450px;
}
.input-group--text-field label {padding-left:8px !important;}
</style>
