<template style="height:100%;">
  <v-container fluid class="login pa-0 marT60" style="height:100%;">
    <v-layout row wrap class="mt-0 mb-3 pa-0" style="height:100%;">
      <!--COLUMN1-->
      <v-flex xs12 sm6 class="ma-0 pt-0 pl-5 pr-5 bg">
        <div class="contain_div pt-5">
          <div class="mar1">
            <img src="/static/bp_logo_horz_color1.png" class="logo" />
          </div>
          <div class="mar2">
            <h5 class="white--text">Global Loyalty + CRM Innovators</h5>
          </div>
          <div class="mar3">
            <h5 class="white--text">The Leader in Loyalty brings a new self-service analytics suite designed specifically with marketers in mind.</h5>
          </div>
        </div>
      </v-flex>
      <!--//COLUMN1-->
      <!--COLUMN2-->
      <v-flex xs12 sm6 class="ma-0 pa-0">
        <v-layout row wrap class="pl-0 pr-0" style="height:100%;">
          <v-flex xs10 offset-xs1 class="pl-0 pr-0 mt-0">
            <div class="centereddiv">
              <v-card>
                <v-card-title primary-title class="primary">
                  <h6 class="white--text text-xs-left mb-0">Login</h6>
                </v-card-title>
                <form>
                  <v-card-row xs12 class="mt-3 mb-3">
                    <v-icon class="primary white--text pa-2 ml-3 mt-0">person</v-icon>
                    <v-text-field
                        name="input-1"
                        label="Username"
                        class="white mt-0 mb-0 mr-3 pr-4 pa-2"
                        required
                        id="username">
                    </v-text-field>
                  </v-card-row>
                  <v-card-row xs12 class="mb-3">
                    <v-icon class="primary white--text pa-2 ml-3 mt-0">lock</v-icon>
                    <v-text-field
                        name="input-1"
                        label="Password"
                        class="white mt-0 mb-0 mr-3 pr-4 pa-2"
                        required
                        id="password">
                    </v-text-field>
                  </v-card-row>
                  <v-card-row xs12>
                    <v-btn primary block class="white--text ml-3 mr-3 mb-0">Log In</v-btn>
                  </v-card-row>
                  <v-card-row xs12>
                    <v-card-text class="ma-0 pl-3 pr-3 pt-0 pb-0">
                      <v-checkbox v-bind:label="`Remember Me`" v-model="ex1"></v-checkbox>
                    </v-card-text>
                  </v-card-row>
                  <div class="pl-3 pr-3"><v-divider class="primary"></v-divider></div>
                  <v-card-row xs12 class="link_row">
                    <v-flex xs6 class="pl-3 pr-3 text-xs-left">
                      <a href="#">Forgot Username</a>
                    </v-flex>
                    <v-flex xs6 class="pl-3 pr-3 text-xs-right">
                      <a href="#">Reset Password</a>
                    </v-flex>
                  </v-card-row>
                </form>
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
        msg: 'LOGIN PAGE GOES HERE',
        ex1: false
      }
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.bg{
  background-image: url("http://res.cloudinary.com/govimg/image/upload/v1481125533/5820287194e85008730d0d6d/digital_messaging_hero.jpg");
  background-color: #ffffff;
  background-repeat: no-repeat;
  background-size: cover;
  background-position: center top;
  width: 100%;
  height: 100%;
}
.contain_div{
  max-width: 500px;
}
.logo{width:250px;}
.mar1{margin-top:40px;}
.mar2{margin-top:100px;}
.mar3{margin-top:80px;}
.centereddiv{
  position:relative;
  width: 100%;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  max-width: 450px;
}
.link_row a{
  text-decoration: none;
  color:#354052;
}
</style>
