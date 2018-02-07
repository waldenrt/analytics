<template>
  <v-parallax src="/assets/images/bp_logo_reverse_color.png" height="1000">
    <v-layout column align-center justify-center>
      <h1 class="white--text text-xs-center">Welcome to the Application!</h1>
      <h4 class="white--text">Build your application today!</h4>
    </v-layout>
  </v-parallax>
</template>

<script>
export default {
  name: 'home',
  data () {
    return {
      items: [
        {
          text: 'Dashboard',
          disabled: false
        },
        {
          text: 'Link 1',
          disabled: false
        },
        {
          text: 'Link 2',
          disabled: true
        }
      ]
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.home{
  display: block;
  min-width: 100%;
  min-height: 650px;
  background-image:url(../assets/images/bp_logo_reverse_color.png);
  background-position: 25% 0%;
  background-repeat: no-repeat;
  background-attachment: fixed;
  opacity:0.3;
}
.icon_bdr{
  border:2px solid rgba(53,64,82,0.54);
  border-radius:4px;
}
.title{
  vertical-align: middle;
}
.inliner{display:inline;}
.wtrmrk{
  background: url(https://governor-media.s3.amazonaws.com/5820287194e85008730d0d6d%2Fassets%2Fimages%2Flogo-white.png) no-repeat center;
  background-size: cover;
}
</style>
