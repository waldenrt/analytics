<template>
  <v-container fluid class="NewLifecycle pl-3 pr-3">
    <v-layout row wrap style="height:100%;">
      <v-flex xs6 offset-xs3 class="pl-0 pr-0 mt-0 mb-4">
        <div class="centereddiv">
        <form name="lifeForm" @submit.prevent="validateBeforeSubmit()">
          <v-card class="white v_card">
            <v-card-title primary-title class="primary">
              <div><h6 class="white--text text-xs-left mb-0">Core Lifecycle Input</h6></div>
              <v-spacer></v-spacer>
              <div class="file">
                <v-dialog v-model="dialog" width="550px">
                  <div slot="activator">
                    <div class="white--text subheading" style="display:inline-block;">Sample File Image</div>
                    <div style="display:inline-block;"><v-icon primary light slot="activator">image</v-icon></div>
                  </div>
                  <v-card>
                    <img src="../assets/images/lifecycle_file_img.png" width="100%" height="100%" class="file_sample">
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
                    class="ma-0 input-group--focused custom_input"
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
                    class="ma-0 input-group--focused custom_input"
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
                    class="ma-0 input-group--focused custom_input"
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
                    class="ma-0 input-group--focused custom_input"
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
              <!--FIELD-->
              <div class="xs12">
                <label class="body-2">Enter Product Column Name</label>
                <v-layout class="xs12 ma-0">
                  <v-text-field
                      label="Enter Product Column Name"
                      v-model="prodCol"
                      class="ma-0 input-group--focused custom_input"
                      single-line
                      hide-details
                      data-vv-name="prodCol"
                      v-validate="'required'"
                      id="prodCol">
                  </v-text-field>
                </v-layout>
                <v-layout class="xs12 ma-0">
                  <small v-show="vErrors.has('prodCol')" class="error--text">* {{ vErrors.first('prodCol') }}</small>
                </v-layout>
              </div>
              <!--//FIELD-->
            </v-flex>
            <!--//+++++col1+++++-->
            </v-layout>
            <v-divider class="grey lighten-2"></v-divider>
            <v-layout wrap row>
            <v-flex xs12 class="pt-3 pl-4 pr-4 pb-3">
              <!--BUTTONS-->
              <v-card-row xs12 style="float:right;">
                <div v-if="uploadInProgress">Uploading file
                  <v-btn disabled class="success white--text ma-0">Submit</v-btn></div>
                <v-btn v-else
                  @click.native="validateBeforeSubmit()"
                  class="success white--text ma-0">submit</v-btn>
              </v-card-row>
              <!--//BUTTONS-->
            </v-flex>
            </v-layout>
          </v-card>
        </form>
        </div>
      </v-flex>
    </v-layout>
  </v-container>
</template>

<script>
  import {upload} from './javascript/file-upload.service'
  import {submitJob} from './javascript/job.service'

  export default {
    data () {
      return {
        job_lifecycle: '',
        select_lifecycle1: '',
        select_lifecycle2: '',
        items1: [
          {text: '.txt (tab separated)', value: '/t'},
          {text: '.CSV ("," delimeter)', value: ','},
          {text: '.DSV ("|" delimeter)', value: '|'},
          {text: '.DSV (";" delimeter)', value: ';'}
        ],
        items2: [
          {text: 'All Data', value: '0'},
          {text: '3 months', value: '3'},
          {text: '6 months', value: '6'},
          {text: '12 months', value: '12'}
        ],
        dialog: false,
        valid: true,
        uploadedFile: '',
        uploadFieldName: '',
        prodCol: '',
        uploadInProgress: false
      }
    },
    computed: {
      client: function () {
        return this.$store.state.client
      },
      user: function () {
        return this.$store.state.user
      },
      powerUser: function () {
        return this.$store.state.powerUser
      }
    },
    methods: {
      fileUpload (fieldName, fileNames) {
        this.uploadInProgress = true
        const formData = new FormData()
        if (!fileNames.length) return

        formData.append('file', fileNames[0])

        this.save(formData)
        this.uploadedFile = fileNames[0].name
      },
      save (formData) {
        upload(formData)
          .catch(err => {
            alert('There was an error uploading the file.  Please try again.' + err.message.toString())
          })
          .then((response) => {
            console.log('successfully uploaded file to HDFS')
            console.log(response)
            this.uploadInProgress = false
          })
      },
      validateBeforeSubmit () {
        // create JSON object for submittal
        var jobObj = {
          'client': this.client,
          'user': this.user,
          'powerUser': false,
          'app': 'lifecycle',
          'jobName': this.job_lifecycle,
          'jobId': '',
          'fileName': 'hdfs:///user/admin/' + this.uploadedFile,
          'delimiter': this.select_lifecycle1,
          'args': [
            {'name': 'timePeriod', 'value': this.select_lifecycle2},
            {'name': 'productColumn', 'value': this.prodCol}
          ]
        }

        console.log(jobObj)

        var vm = this
        this.$validator.validateAll().then((result) => {
          if (result) {
            submitJob(jobObj)
              .catch(err => {
                alert('Problem submitting job to server.  ' + err.message.toString())
              })
              .then((response) => {
                alert('Form Submitted!')
                console.log(response)
                vm.disabledBtn = false
                this.$router.push('/JobHistory')
              })
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
.v_card {border-radius:4px;}
.file {cursor:pointer;}
.file_sample {padding:10px; position:relative; top:3px;}
.pad_LR12 { padding-left:12px; padding-right: 12px;}
.input-group {margin-top:0; margin-bottom:0;}
.mar_field1 {margin-top:19px;}
.mar_field2 {margin-top:34px;}
.mar_field3 {margin-top:34px;}
</style>
