<template>
  <v-container fluid class="NewBalor">
    <v-layout row wrap>
      <!-- COLUMN1 -->
      <v-flex d-flex xs12 sm6>
        <!-- CARD1 -->
        <v-card class="white v_card_width">
          <v-card-title primary-title class="primary">
            <h6 class="white--text text-xs-left mb-0">Enter Job Name</h6>
          </v-card-title>
          <v-text-field
              name="input-1"
              label="Enter job name"
              class="ma-3 pr-4"
              required
          ></v-text-field>
        </v-card>
        <!-- //CARD1 -->
        <!-- CARD2 -->
        <v-card class="white v_card_width">
          <v-card-title primary-title class="primary">
            <h6 class="white--text text-xs-left mb-0">Select file for analysis</h6>
          </v-card-title>
          <!-- Shouldn't be a plain select....
          <v-select
              v-model="dialog"
              persistent
              label="Select file for analysis"
              single-line
              auto
              prepend-icon="search"
              slot="activator"
              class="ma-3 pr-4"
              required
            ></v-select>

        -->
          <form enctype="multipart/form-data">
            <input type="file" id="fileUploader" @change="fileUpload()">
          </form>
        </v-card>
        <!-- //CARD2 -->
        <!-- CARD3 -->
        <v-card class="white v_card_width">
          <v-card-title primary-title class="primary">
            <h6 class="white--text text-xs-left mb-0">Select file type</h6>
          </v-card-title>
          <v-select
              v-bind:items="items"
              v-model="e1"
              label="Select file type"
              class="ma-3 pr-4"
              single-line
              bottom
              v-bind:error-messages="['Please select an option']"
              required
          ></v-select>
        </v-card>
        <v-btn @click="submit" :class="{ green: valid, red: !valid }" class="primary white--text">submit</v-btn>
        <v-btn @click="clear">clear</v-btn>
        <!-- CARD2 -->
      </v-flex>
      <!-- //COLUMN1 -->
      <!-- COLUMN2 -->
      <v-flex d-flex xs12 sm6>
        <v-card class="white">
          <img src="http://via.placeholder.com/525x150/00313D/ffffff?text=[Image+of+Sample+file+preview]" width="100%"
               height="100%" style="padding:10px;">
        </v-card>
        <v-card class="white mt-3">
          <img src="http://via.placeholder.com/525x150/8EAC1D/ffffff?text=User+File+Preview" width="100%" height="100%"
               style="padding:10px;">
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
  .v_card_width {
    max-width: 450px;
  }
</style>
