<template>
  <v-container fluid class="NewBalor pl-3 pr-3" style="height:100%;">

    <v-layout row wrap style="height:100%;">
      <v-flex xs12  sm8 offset-sm2  md6 offset-md3  class="pl-0 pr-0 mt-0 mb-5">
        <!-- FORM STARTS -->
        <form name="balForm" @submit.prevent="validateBeforeSubmit()">
          <v-card class="white v_card">
            <v-card-title primary-title class="primary">
              <div><h6 class="white--text text-xs-left mb-0">BALOR Input</h6></div>
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
                        v-model="job_balor"
                        class="ma-0 input-group--focused custom_input"
                        single-line
                        hide-details
                        data-vv-name="job_balor"
                        v-validate="'required'"
                        id="job_balor">
                    </v-text-field>
                  </v-layout>
                  <v-layout class="xs12 ma-0">
                    <small v-show="vErrors.has('job_balor')" class="error--text">* {{ vErrors.first('job_balor') }}</small>
                  </v-layout>
                </div>
                <!--//FIELD-->
                <!--FILE-LOADER-->
                <div class="xs12 pb-3">
                  <label class="body-2">Select file for analysis</label>
                  <!-- sample_file_image_dialog -->
                  <v-dialog v-model="dialog1" width="550px">
                    <div slot="activator">
                      <div class="file">
                        <div style="display:inline-block;"
                        v-tooltip:right="{ html: 'Sample File Image' }">
                          <v-icon class="warning--text" slot="activator">note</v-icon></div>
                      </div>
                    </div>
                    <v-card>
                      <img src="../assets/images/balor_file_img.png" width="100%" height="100%" class="file_sample">
                    </v-card>
                  </v-dialog>

                  <v-layout xs12 class="pad_LR12">
                    <form enctype="multipart/form-data" style="width:100%;">
                      <input
                        type="file"
                        :name="uploadFieldName"
                        @change="fileUpload($event.target.name, $event.target.files)"
                        class="ma-0 input-group--focused custom_input"
                        style="width:100%;"
                        data-vv-name="file_balor"
                        v-validate="'required|ext:txt,csv,dsv'"
                        id="input_balor">
                    </form>
                  </v-layout>
                  <v-layout class="xs12 ma-0">
                    <small v-show="vErrors.has('file_balor')" class="error--text">* {{ vErrors.first('file_balor') }}</small>
                  </v-layout>
                </div>
                <!--//FILE-LOADER-->
                <!--SELECT-->
                <div class="xs12">
                  <label class="body-2">Select file type</label>
                  <v-layout xs12 class="pad_LR12">
                    <v-select
                        v-bind:items="items"
                        v-model="select_balor"
                        label="Select file type"
                        class="ma-0 input-group--focused custom_input"
                        single-line
                        hide-details
                        data-vv-name="select_balor"
                        v-validate="'required'"
                        id="select_balor">
                    </v-select>
                  </v-layout>
                  <v-layout class="xs12 ma-0">
                    <small v-show="vErrors.has('select_balor')" class="error--text">* {{ vErrors.first('select_balor') }}</small>
                  </v-layout>
                </div>
                <!--//SELECT-->
              </v-flex>
              <!--//+++++col1+++++-->
            </v-layout>
            <v-divider class="grey lighten-2"></v-divider>
            <v-layout wrap row>
            <v-flex xs12 class="pt-3 pl-4 pr-4 pb-3">
              <!--BUTTONS-->
              <v-card-row xs12>
                <v-spacer></v-spacer>
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
        <!-- //FORM ENDS -->
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
        job_balor: '',
        select_balor: '',
        input_balor: '',
        disabledBtn: '',
        items: [
          {text: '.txt (tab separated)', value: '/t'},
          {text: '.CSV ("," delimiter)', value: ','},
          {text: '.DSV ("|" delimiter)', value: '|'},
          {text: '.DSV (";" delimiter)', value: ';'}
        ],
        dialog1: false,
        valid: true,
        uploadedFile: '',
        uploadError: null,
        currentStatus: null,
        uploadFieldName: '',
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

        console.log(fieldName)
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
        // create json object for job submittal
        var jobObj = {
          'client': this.client,
          'user': this.user,
          'powerUser': this.powerUser,
          'app': 'cadence',
          'jobName': this.job_balor,
          'jobId': '',
          'fileName': 'hdfs:///user/admin/' + this.uploadedFile,
          'delimiter': this.select_balor,
          'args': [
            {'name': 'percentile', 'value': '.8'}
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
.file {cursor:pointer;display:inline-block;}
.file_sample {padding:10px; position:relative; top:3px;}
.pad_LR12 {padding-left:12px; padding-right: 12px;}
</style>
