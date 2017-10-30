<template>
  <v-container fluid class="NewQuantile pl-3 pr-3">
    <v-layout row wrap style="height:100%;">
      <v-flex xs6 offset-xs3 class="pl-0 pr-0 mt-0">
        <div class="centereddiv">
          <v-card class="white" style="margin-top:50px;">
            <v-card-title primary-title class="primary">
              <div><h6 class="white--text text-xs-left mb-0">BALOR INPUT</h6></div>
              <v-spacer></v-spacer>
              <div>
                <v-dialog v-model="dialog" width="550px">
                  <div slot="activator">
                    <div class="white--text subheading" style="display:inline-block;cursor:pointer;">Sample File Image</div>
                    <div style="display:inline-block;"><v-icon primary light slot="activator" style="cursor:pointer;">image</v-icon></div>
                  </div>
                  <v-card>
                    <img src="../assets/images/balor_file_img.png" width="100%" height="100%" class="file_sample">
                  </v-card>
                </v-dialog>
              </div>
            </v-card-title>
            <v-layout row wrap style="">
            <!--+++++col1+++++-->
            <v-flex xs12 class="pa-3 pl-4 pr-4">
            <form>
              <!--FIELD-->
              <b2>Enter Job Name</b2>
              <v-card-row xs12 class="mt-0 mb-3">
                <v-text-field
                    name="input-1"
                    label="Enter Job Name"
                    class="mt-1 mb-0 input-group--focused elevation-1"
                    single-line
                    required
                    id="job_balor">
                </v-text-field>
              </v-card-row>
              <!--//FIELD-->
              <!--FILE-LOADER-->
              <b2>Select file for analysis</b2>
              <v-card-row xs12 class="mb-3">
                <form enctype="multipart/form-data" style="width:100%;">
                  <input
                    type="file"
                    :name="uploadFieldName"
                    @change="fileUpload($event.target.name, $event.target.files)"
                    class="white elevation-1"
                    style="width:100%;"
                    id="input_balor">
                </form>
              </v-card-row>
              <!--//FILE-LOADER-->
              <!--SELECT-->
              <b2>Select file type</b2>
              <v-card-row xs12 class="mb-3">
                <v-select
                    v-bind:items="items"
                    v-model="e1"
                    label="Select file type"
                    class="mt-1 mb-0 input-group--focused elevation-1"
                    single-line
                    bottom
                    v-bind:error-messages="['Please select an option']"
                    required
                    id="select_balor">
                </v-select>
              </v-card-row>
              <!--//SELECT-->
              <!--BUTTONS-->
              <v-card-row xs12>
                <v-btn
                  :disabled="!formIsValid"
                  @click="submit"
                  :class="{ green: valid, red: !valid }"
                  class="primary white--text ma-0">submit</v-btn>
              </v-card-row>
              <!--//BUTTONS-->
            </form>
            </v-flex>
            <!--//+++++col1+++++-->
            </v-layout>
            <v-layout wrap row>
              <v-flex xs6>
                col1
              </v-flex>
              <v-flex xs6>
                col2
              </v-flex>
            </v-layout>
          </v-card>
        </div>
      </v-flex>
    </v-layout>
    <v-layout row wrap>





      <!-- =====COLUMN1===== -->
      <v-flex d-flex xs12 sm12 md6>
        <v-card class="grey lighten-2 pa-3 mb-3" style="height:371px !important;">
          <v-layout row wrap>
            <v-flex xs12>
              <!-- CARD1 -->
              <v-card class="white mt-3 mb-3">
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
              <v-card class="white mb-3">
                <form enctype="multipart/form-data">
                  <input type="file" id="fileUploader" @change="fileUpload()">
                </form>
              </v-card>
              <!-- //CARD2 -->
              <!-- CARD3 -->
              <div class="caption primary--text mb-0 pb-0">Select file type</div>
              <v-card class="white mb-2">
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
            </v-flex>
          </v-layout>
        </v-card>
        <v-card class="grey lighten-2 pa-3 mb-3" style="height:371px !important;">
          <v-layout row wrap>
            <!-- COL1 -->
            <v-flex xs12 sm6>
              <!-- CARD1 -->
              <div class="caption primary--text mb-0 pb-0">Select time period for quantile</div>
              <v-card class="white mb-3">
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
              <!-- //CARD1 -->
              <!-- CARD2 -->
              <div class="caption primary--text mb-0 pb-0">Select quantile value</div>
              <v-card class="white mb-3">
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
              <!-- //CARD2 -->
              <!-- CARD3 -->
              <div class="caption primary--text mb-0 pb-0">Select dimension to decile</div>
              <v-card flat class="grey lighten-2 mb-3">
                <v-radio label="Customer Level" v-model="ex8" value="radio-1" dark></v-radio>
                <v-radio label="Store Level" v-model="ex8" value="radio-2" dark></v-radio>
              </v-card>
              <!-- //CARD3 -->
            </v-flex>
            <!-- //COL1 -->
            <!-- COL2 -->
            <v-flex xs12 sm6>
              <!-- CARD1 -->
              <v-card class="white mar_field1">
                <v-text-field
                    name="input-1"
                    label="Enter product hierarchy level I"
                    class="input-group--focused"
                    required
                ></v-text-field>
              </v-card>
              <!-- //CARD1 -->
              <!-- CARD2 -->
              <v-card class="white mar_field2">
                <v-text-field
                    name="input-1"
                    label="Enter product hierarchy level II"
                    class="input-group--focused"
                    required
                ></v-text-field>
              </v-card>
              <!-- //CARD2 -->
              <!-- CARD3 -->
              <v-card class="white mar_field3">
                <v-text-field
                    name="input-1"
                    label="Enter product hierarchy level III"
                    class="input-group--focused"
                    required
                ></v-text-field>
              </v-card>
              <!-- //CARD3 -->
            </v-flex>
            <!-- COL2 -->
          </v-layout>
          <v-layout row wrap>
            <v-flex xs12 class="pl-2 pr-2">
              <v-btn @click="submit" :class="{ green: valid, red: !valid }" class="primary white--text">submit</v-btn>
              <v-btn @click="clear" class="white">clear</v-btn>
            </v-flex>
          </v-layout>
        </v-card>
      </v-flex>
      <!-- //=====COLUMN1===== -->




      <!-- =====COLUMN2===== -->
      <v-flex d-flex xs12 sm12 md6>
        <v-card class="white">
          <v-card-title primary-title class="primary">
            <h6 class="white--text text-xs-left mb-0">Sample File Image</h6>
          </v-card-title>
          <img src="../assets/images/balor_file_img.png" width="100%" height="100%" class="file_sample">
        </v-card>
        <!--<v-card class="white mt-3">
          <img src="http://via.placeholder.com/525x150/EDEDED/ffffff?text=User+File+Preview" width="100%" height="100%" class="file_sample">
        </v-card>-->
      </v-flex>
      <!-- //=====COLUMN2===== -->



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
.v_card_width {max-width:300px;}
.file_sample {padding:10px; position:relative; top:3px;}
.input-group {margin-top:0; margin-bottom:0;}
.mar_field1 {margin-top:19px;}
.mar_field2 {margin-top:34px;}
.mar_field3 {margin-top:34px;}
</style>
