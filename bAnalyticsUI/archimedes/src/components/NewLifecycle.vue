<template>
  <v-container fluid class="NewLifecycle pl-3 pr-3">
    <v-layout row wrap style="height:100%;">
      <v-flex xs6 offset-xs3 class="pl-0 pr-0 mt-0">
        <div class="centereddiv">
        <form name="lifeForm" @submit.prevent="validateBeforeSubmit()">
          <v-card class="white" style="margin-top:50px;">
            <v-card-title primary-title class="primary">
              <div><h6 class="white--text text-xs-left mb-0">CORE LIFECYCLE INPUT</h6></div>
              <v-spacer></v-spacer>
              <div class="file">
                <v-dialog v-model="dialog" width="550px">
                  <div slot="activator">
                    <div class="white--text subheading" style="display:inline-block;">Sample File Image</div>
                    <div style="display:inline-block;"><v-icon primary light slot="activator">image</v-icon></div>
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
              <div class="xs12 pb-3">
                <label class="body-2">Enter Job Name</label>
                <v-layout class="xs12 ma-0">
                <v-text-field
                    label="Enter Job Name"
                    v-model="job_lifecycle"
                    class="ma-0 input-group--focused"
                    single-line
                    hide-details
                    data-vv-name="job_lifecycle"
                    v-validate="'required'"
                    id="job_lifecycle">
                </v-text-field>
              </v-layout>
              <v-layout class="xs12 ma-0">
                <small v-show="vErrors.has('job_lifecycle')" class="error--text">* {{ vErrors.first('job_lifecycle') }}</small>
              </v-layout>
            </div>
              <!--//FIELD-->
              <!--FILE-LOADER-->
              <div class="xs12 pb-3">
                <label class="body-2">Select file for analysis</label>
                <v-layout xs12 class="pad_LR12">
                <form enctype="multipart/form-data" style="width:100%;">
                  <input
                    type="file"
                    :name="uploadFieldName"
                    @change="fileUpload($event.target.name, $event.target.files)"
                    class="white elevation-1"
                    style="width:100%;"
                    data-vv-name="file_lifecycle"
                    v-validate="'required|ext:txt,csv,dsv'"
                    id="input_lifecycle">
                </form>
                </v-layout>
                <v-layout class="xs12 ma-0">
                  <small v-show="vErrors.has('file_lifecycle')" class="error--text">* {{ vErrors.first('file_lifecycle') }}</small>
                </v-layout>
              </div>
              <!--//FILE-LOADER-->
              <!--SELECT-->
              <div class="xs12 pb-3">
                <label class="body-2">Select file type</label>
                <v-layout xs12 class="pad_LR12">
                <v-select
                    v-bind:items="items1"
                    v-model="select_lifecycle1"
                    label="Select file type"
                    class="ma-0 input-group--focused"
                    single-line
                    hide-details
                    data-vv-name="select_lifecycle1"
                    v-validate="'required'"
                    id="select_lifecycle1">
                </v-select>
              </v-layout>
              <v-layout class="xs12 ma-0">
                <small v-show="vErrors.has('select_lifecycle1')" class="error--text">* {{ vErrors.first('select_lifecycle1') }}</small>
              </v-layout>
            </div>
              <!--//SELECT-->
              <!--SELECT-->
              <div class="xs12 pb-3">
              <label class="body-2">Select time period for segmentation</label>
              <v-layout xs12 class="pad_LR12">
                <v-select
                    v-bind:items="items2"
                    v-model="select_lifecycle2"
                    label="Select file type"
                    class="ma-0 input-group--focused"
                    single-line
                    hide-details
                    data-vv-name="select_lifecycle2"
                    v-validate="'required'"
                    id="select_lifecycle2">
                </v-select>
                </v-layout>
                <v-layout class="xs12 ma-0">
                  <small v-show="vErrors.has('select_lifecycle2')" class="error--text">* {{ vErrors.first('select_lifecycle2') }}</small>
                </v-layout>
              </div>
              <!--//SELECT-->
              <!--BUTTONS-->
              <v-card-row xs12>
                <v-btn
                  @click.native="validateBeforeSubmit()"
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
        job_lifecycle: '',
        select_lifecycle1: '',
        select_lifecycle2: '',
        items1: [
          {text: '.txt (tab separated)'},
          {text: '.CSV ("," delimeter)'},
          {text: '.DSV ("|" delimeter)'},
          {text: '.DSV (";" delimeter)'}
        ],
        items2: [
          {text: '3 months'},
          {text: '6 months'},
          {text: '9 months'},
          {text: '12 months'}
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
      validateBeforeSubmit () {
        var vm = this
        this.$validator.validateAll().then((result) => {
          if (result) {
            alert('Form Submitted!')
            vm.disabledBtn = false
            return
          } else {
            alert('Correct them vErrors!')
            vm.disabledBtn = true
          }
        })
      }
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.v_card_width {max-width:300px;}
.file {cursor:pointer;}
.file_sample {padding:10px; position:relative; top:3px;}
.pad_LR12 { padding-left:12px; padding-right: 12px;}
.input-group {margin-top:0; margin-bottom:0;}
.mar_field1 {margin-top:19px;}
.mar_field2 {margin-top:34px;}
.mar_field3 {margin-top:34px;}
</style>
