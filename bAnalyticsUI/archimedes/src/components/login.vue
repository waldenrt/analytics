<template style="height:100%;">
  <v-container fluid class="login pa-0 marT60" style="height:100%;">
    <v-layout row wrap class="mt-0 mb-3 pa-0" style="height:100%;">
      <!--COLUMN1-->
      <v-flex xs6 class="ma-0 pt-0 pl-5 pr-5 bg">
        <div class="mar1">
          <img src="/static/bp_logo_horz_color1.png" class="logo" />
        </div>
        <div class="mar2">
          <h5>Global Loyalty + CRM Innovators</h5>
        </div>
        <div class="mar3">
          <h5>The Leader in Loyalty brings a new self-service analytics suite designed specifically with marketers in mind.</h5>
        </div>
      </v-flex>
      <!--//COLUMN1-->
      <!--COLUMN2-->
      <v-flex xs6 class="ma-0 pa-0">
        <v-layout row wrap class="pl-0 pr-0" style="height:100%;">
          <v-flex xs10 offset-xs1 class="pl-0 pr-0 mt-0 white">
            <div class="center-table">
              <div style="height:150px;">I'm vertically centered multiple lines of text in a CSS-created table layout.</div>
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
        msg: 'LOGIN PAGE GOES HERE'
      }
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.marT60{
  margin-top:60px;
}
.bg{
  /*background-image: url("/static/airport.jpg");*/
  background-color: #ffffff;
  background-repeat: no-repeat;
  background-size: cover;
  background-position: center top;
  width: 100%;
  height: 100%;
  min-height: 1200px;
}
.logo{
  width:250px;
}
.mar1{margin-top:40px;}
.mar2{margin-top:100px;}
.mar3{margin-top:80px;}

.center-table {
  display: table;
  height: 100%;
  background: white;
  margin:0 auto;
  vertical-align: middle;
}
.center-table div {
  display: table-cell;
  margin: 0;
  background: black;
  color: white;
  padding: 20px;
  border: 10px solid white;
  vertical-align: middle;
}
</style>
