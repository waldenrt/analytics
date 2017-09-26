<template>
  <v-container fluid class="NewQuantile">
    <v-layout row wrap>
      <!-- COLUMN1 -->
      <v-flex d-flex xs12 sm12 md6>
        <v-card class="grey lighten-2 pa-3 mb-3">


        <!-- CARD1 -->
        <v-card class="white mb-3">
          <v-text-field
              name="input-1"
              label="Enter job name"
              class="input-group--focused"
              required
          ></v-text-field>
        </v-card>
        <!-- //CARD1 -->
        <!-- CARD2 -->
        <div class="caption primary--text">Select file for analysis</div>
        <v-card class="white v_card_width mb-4">
          <form enctype="multipart/form-data">
            <input type="file" id="fileUploader" @change="fileUpload()">
          </form>
        </v-card>
        <!-- //CARD2 -->
        <!-- CARD3 -->
        <div class="caption primary--text mb-0 pb-0">Select file type</div>
        <v-card class="white v_card_width mb-3">
          <v-select
              v-bind:items="items"
              v-model="e1"
              label="Select file type"
              class="input-group--focused ml-1 pr-1 mt-0"
              single-line
              bottom
              v-bind:error-messages="['Please select an option']"
              required
          ></v-select>
        </v-card>
        <!-- //CARD3 -->
        <v-btn @click="submit" :class="{ green: valid, red: !valid }" class="primary white--text">submit</v-btn>
        <v-btn @click="clear">clear</v-btn>


        </v-card>
      </v-flex>
      <!-- //COLUMN1 -->
      <!-- COLUMN2 -->
      <v-flex d-flex xs12 sm12 md6>
        <v-card class="white">

          <v-card-title primary-title class="primary">
            <h6 class="white--text text-xs-left mb-0">Sample File Image</h6>
          </v-card-title>
          <img src="../assets/images/balor_file_img.png" width="100%" height="100%" class="file_sample">
        </v-card>
        <v-card class="white mt-3">
          <img src="http://via.placeholder.com/525x150/EDEDED/ffffff?text=User+File+Preview" width="100%" height="100%" class="file_sample">
        </v-card>
      </v-flex>
      <!-- //COLUMN2 -->
    </v-layout>
  </v-container>
</template>

<script>
  export default {
    data () {
      return {
        e1: null,
        items: [
          {text: '.txt (tab separated)'},
          {text: '.CSV ("|" delimeter)'},
          {text: '.DSV ("|" delimeter)'},
          {text: '.DSV (";" delimeter)'}
        ],
        dialog: false,
        valid: true
      }
    },
    methods: {
      fileUpload () {
        var path = document.getElementById('fileUploader').value
        console.log('path...' + path)
        var WebHDFS = require('webhdfs')

        var hdfs = WebHDFS.createClient({
          user: 'admin',
          host: '10.4.3.26',
          port: 14000,
          path: '/webhdfs/v1'
        })
        var fs = require('fs')
        var localFileStream = fs.createReadStream(path)
        // e.target.files || e.dataTransfer.files
        var remoteFileStream = hdfs.createWriteStream('/user/archimedes/brierley/demo/test')

        localFileStream.pipe(remoteFileStream)

        remoteFileStream.on('error', function onError (err) {
          console.log('ERROR occured: ' + err)
          // Do something with the error
        })

        remoteFileStream.on('finish', function onFinish () {
          console.log('Upload succesful')
          // Upload is done
        })
      },
      submit () {
        console.log('submit job now')
      },
      clear () {
        console.log('clear all fields')
      }
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.file_sample {padding:10px; position:relative; top:3px;}
</style>
