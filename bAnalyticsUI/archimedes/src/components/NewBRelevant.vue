<template>
  <v-container fluid class="NewBRelevant pl-3 pr-3">
    <v-layout row wrap style="height:100%;">
      <v-flex xs6 offset-xs3 class="pl-0 pr-0 mt-0">
        <div class="centereddiv">
        <form>
          <v-card class="white" style="margin-top:50px;">
            <v-card-title primary-title class="primary">
              <div><h6 class="white--text text-xs-left mb-0">BRELEVANT INPUT</h6></div>
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
              <!--FIELD-->
              <div class="body-2">Enter Job Name</div>
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
              <div class="body-2">Select file for analysis</div>
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
              <div class="body-2">Select file type</div>
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
            </v-flex>
            <!--//+++++col1+++++-->
            </v-layout>
          </v-card>
        </form>
        </div>
      </v-flex>
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
          {text: '.CSV ("," delimeter)'},
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
