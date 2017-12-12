<template>
  <v-container fluid class="NewQuantile pl-3 pr-3">
    <v-layout row wrap style="height:100%;">
      <v-flex xs6 offset-xs3 class="pl-0 pr-0 mt-0">
        <div class="centereddiv">

          <!-- FORM STARTS -->
          <form name="quantForm" @submit.prevent="validateBeforeSubmit()">
          <v-card class="white v_card">
            <v-card-title primary-title class="primary">
              <div><h6 class="white--text text-xs-left mb-0">Pareto Input</h6></div>
              <v-spacer></v-spacer>
              <div class="file">
                <v-dialog v-model="dialog" width="550px">
                  <div slot="activator">
                    <div class="white--text subheading inliner">Sample File Image</div>
                    <div class="inliner"><v-icon primary light slot="activator">image</v-icon></div>
                  </div>
                  <v-card>
                    <img src="../assets/images/balor_file_img.png" width="100%" height="100%" class="file_sample">
                  </v-card>
                </v-dialog>
              </div>
            </v-card-title>
            <v-layout row wrap>
            <!--+++++col1+++++-->
            <v-flex xs12 class="pa-3 pl-4 pr-4">
              <!--FIELD-->
              <div class="xs12 pb-3">
                <label class="body-2">Enter Job Name</label>
                <v-layout class="xs12 ma-0">
                  <v-text-field
                      label="Enter Job Name"
                      v-model="job_pareto"
                      class="ma-0 input-group--focused"
                      single-line
                      hide-details
                      data-vv-name="job_pareto"
                      v-validate="'required'"
                      id="job_pareto">
                  </v-text-field>
                </v-layout>
                <v-layout class="xs12 ma-0">
                  <small v-show="vErrors.has('job_pareto')" class="error--text">* {{ vErrors.first('job_pareto') }}</small>
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
                    class="ma-0 input-group--focused"
                    style="width:100%;"
                    data-vv-name="file_pareto"
                    v-validate="'required|ext:txt,csv,dsv'"
                    id="input_pareto1">
                </form>
                </v-layout>
                <v-layout class="xs12 ma-0">
                  <small v-show="vErrors.has('file_pareto')" class="error--text">* {{ vErrors.first('file_pareto') }}</small>
                </v-layout>
              </div>
              <!--//FILE-LOADER-->
              <!--SELECT-->
              <div class="xs12">
                <label class="body-2">Select file type</label>
                <v-layout xs12 class="pad_LR12">
                  <v-select
                      v-bind:items="items1"
                      v-model="select_balor"
                      label="Select file type"
                      class="ma-0 input-group--focused"
                      single-line
                      hide-details
                      data-vv-name="select_pareto1"
                      v-validate="'required'"
                      id="select_pareto1">
                  </v-select>
                </v-layout>
                <v-layout class="xs12 ma-0">
                  <small v-show="vErrors.has('select_pareto1')" class="error--text">* {{ vErrors.first('select_pareto1') }}</small>
                </v-layout>
              </div>
              <!--//SELECT-->
            </v-flex>
            <!--//+++++col1+++++-->
            </v-layout>
            <v-divider class="grey lighten-2"></v-divider>
            <v-layout wrap row>
              <!--+++++col1+++++-->
              <v-flex xs6 class="pt-3 pb-0 pl-4 pr-4">
                <!--SELECT-->
                <div class="xs12 pb-3">
                <label class="body-2">Select time period for pareto</label>
                  <v-layout xs12 class="pad_LR12">
                    <v-select
                        v-bind:items="items2"
                        v-model="select_pareto2"
                        label="Select"
                        class="ma-0 input-group--focused"
                        single-line
                        hide-details
                        data-vv-name="select_pareto2"
                        v-validate="'required'"
                        id="select_pareto2">
                    </v-select>
                  </v-layout>
                  <v-layout class="xs12 ma-0">
                    <small v-show="vErrors.has('select_pareto2')" class="error--text">* {{ vErrors.first('select_pareto2') }}</small>
                  </v-layout>
                </div>
                <!--//SELECT-->
                <!--SELECT-->
                <div class="xs12 pb-3">
                  <label class="body-2">Select pareto value</label>
                  <v-layout xs12 class="pad_LR12">
                    <v-select
                      v-bind:items="items3"
                      v-model="select_pareto3"
                      label="Select pareto value"
                      class="ma-0 input-group--focused"
                      single-line
                      hide-details
                      data-vv-name="select_pareto3"
                      v-validate="'required'"
                      id="select_pareto3">
                    </v-select>
                  </v-layout>
                  <v-layout class="xs12 ma-0">
                    <small v-show="vErrors.has('select_pareto3')" class="error--text">* {{ vErrors.first('select_pareto3') }}</small>
                  </v-layout>
                </div>
                <!--//SELECT-->
                <!--SELECT-->
                <div class="xs12 pb-3">
                  <label class="body-2">Select dimension to decile</label>
                  <p class="control mb-0">
                    <v-layout xs12 class="pad_LR12">
                    <v-radio
                        label="Customer Level"
                        v-model="radio_group1"
                        value="radio1"
                        hide-details
                        data-vv-name="radio_group1"
                        v-validate="'required|in:radio1,radio2'"
                        dark></v-radio>
                    </v-layout>
                    <v-layout xs12 class="pad_LR12">
                    <v-radio
                        label="Store Level"
                        v-model="radio_group1"
                        value="radio2"
                        hide-details
                        data-vv-name="radio_group1"
                        v-validate="'required'"
                        dark></v-radio>
                      </v-layout>
                    </p>
                    <v-layout class="xs12 ma-0">
                      <small v-show="vErrors.has('radio_group1')" class="error--text">* {{ vErrors.first('radio_group1') }}</small>
                    </v-layout>
                </div>
                <!--//SELECT-->
              </v-flex>
              <!--//+++++col1+++++-->
              <!--+++++col2+++++-->
              <v-flex xs6 class="pt-3 pb-0 pl-4 pr-4">
                <!--FIELD-->
                <div class="xs12 pb-3">
                  <label class="body-2">Enter product hierarchy level I</label>
                  <v-layout xs12 class="pad_LR12">
                  <v-text-field
                      label="Enter hierarchy"
                      v-model="product1"
                      class="ma-0 input-group--focused"
                      single-line
                      hide-details
                      data-vv-name="product1"
                      v-validate="'required'"
                      id="product1">
                  </v-text-field>
                  </v-layout>
                  <v-layout class="xs12 ma-0">
                    <small v-show="vErrors.has('product1')" class="error--text">* {{ vErrors.first('product1') }}</small>
                  </v-layout>
                </div>
                <!--//FIELD-->
                <!--FIELD-->
                <div class="xs12 pb-3">
                  <label class="body-2">Enter product hierarchy level II</label>
                  <v-layout xs12 class="pad_LR12">
                  <v-text-field
                      label="Enter hierarchy"
                      v-model="product2"
                      class="ma-0 input-group--focused"
                      single-line
                      hide-details
                      data-vv-name="product2"
                      v-validate="'required'"
                      id="product2">
                  </v-text-field>
                  </v-layout>
                  <v-layout class="xs12 ma-0">
                    <small v-show="vErrors.has('product2')" class="error--text">* {{ vErrors.first('product2') }}</small>
                  </v-layout>
                </div>
                <!--//FIELD-->
                <!--FIELD-->
                <div class="xs12 pb-3">
                  <label class="body-2">Enter product hierarchy level III</label>
                  <v-layout xs12 class="pad_LR12">
                  <v-text-field
                      label="Enter hierarchy"
                      v-model="product3"
                      class="ma-0 input-group--focused"
                      single-line
                      hide-details
                      data-vv-name="product3"
                      v-validate="'required'"
                      id="product3">
                  </v-text-field>
                  </v-layout>
                  <v-layout class="xs12 ma-0">
                    <small v-show="vErrors.has('product3')" class="error--text">* {{ vErrors.first('product3') }}</small>
                  </v-layout>
                </div>
                <!--//FIELD-->
              </v-flex>
              <!--//+++++col2+++++-->
            </v-layout>
            <v-divider class="grey lighten-2"></v-divider>
            <v-layout wrap row>
            <v-flex xs12 class="pt-3 pl-4 pr-4 pb-3">
              <!--BUTTONS-->
              <v-card-row xs12 style="float:right;">
                <v-btn
                  @click.native="validateBeforeSubmit()"
                  class="success white--text ma-0">submit</v-btn>
              </v-card-row>
              <!--//BUTTONS-->
            </v-flex>
            </v-layout>
          </v-card>
        </form>
        <!-- FORM ENDS -->
        </div>
      </v-flex>
    </v-layout>
  </v-container>
</template>

<script>
  export default {
    data () {
      return {
        job_pareto: '',
        select_balor: '',
        select_pareto2: '',
        select_pareto3: '',
        product1: '',
        product2: '',
        product3: '',
        radio_group1: '',
        disabledBtn: '',
        items1: [
          {text: '.txt (tab separated)'},
          {text: '.CSV ("," delimeter)'},
          {text: '.DSV ("|" delimeter)'},
          {text: '.DSV (";" delimeter)'}
        ],
        items2: [
          {text: 'month'},
          {text: 'quarter'},
          {text: '6 months'},
          {text: '12 months'}
        ],
        items3: [
          {text: 'Ventile (groups of 5%)'},
          {text: 'Decile (groups of 10%)'},
          {text: 'Quintiles (groups of 20%)'},
          {text: 'Quartiles (groups of 25%)'},
          {text: 'Median (groups of 50%)'}
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
.inliner {display:inline-block;}
.v_card {border-radius:4px;}
.file {cursor:pointer;}
.file_sample {padding:10px; position:relative; top:3px;}
.pad_LR12 { padding-left:12px; padding-right:12px;}
.input-group {margin-top:0; margin-bottom:0;}
.mar_field1 {margin-top:19px;}
.mar_field2 {margin-top:34px;}
.mar_field3 {margin-top:34px;}
</style>
