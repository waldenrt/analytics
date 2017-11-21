<template>
  <v-container fluid class="NewBalor pl-3 pr-3" style="height:100%;">

    <v-layout row wrap style="height:100%;">
      <v-flex xs6 offset-xs3 class="pl-0 pr-0 mt-0">
        <div class="centereddiv">

        <!-- FORM STARTS -->
        <form name="balForm" @submit.prevent="validateBeforeSubmit()">
          <v-card class="white" style="margin-top:50px;">
            <v-card-title primary-title class="primary">
              <div><h6 class="white--text text-xs-left mb-0">BALOR INPUT</h6></div>
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
                      class="ma-0 input-group--focused"
                      single-line
                      hide-details
                      data-vv-name="job_balor"
                      v-validate="'required'"
                      id="job_balor">
                  </v-text-field>
                </v-layout>
                <v-layout class="xs12 ma-0">
                  <small v-show="errors.has('job_balor')" class="error--text">* {{ errors.first('job_balor') }}</small>
                </v-layout>
              </div>
              <!--//FIELD-->
              <!--FILE-LOADER-->
              <div class="xs12 pb-3">
                <label class="body-2">Select file for analysis</label>
                <v-layout xs12 style="padding-left:12px;padding-right:12px;">
                  <form enctype="multipart/form-data" style="width:100%;">
                    <input
                      type="file"
                      :name="uploadFieldName"
                      @change="fileUpload($event.target.name, $event.target.files)"
                      class="ma-0 input-group--focused"
                      style="width:100%;"
                      data-vv-name="file_balor"
                      v-validate="'required|ext:txt,csv,dsv'"
                      id="input_balor">
                  </form>
                </v-layout>
                <v-layout class="xs12 ma-0">
                  <small v-show="errors.has('file_balor')" class="error--text">* {{ errors.first('file_balor') }}</small>
                </v-layout>
              </div>
              <!--//FILE-LOADER-->
              <!--SELECT-->
              <div class="xs12 pb-3">
                <label class="body-2">Select file type</label>
                <v-layout xs12  style="padding-left:12px;padding-right:12px;">
                  <v-select
                      v-bind:items="items"
                      v-model="select_balor"
                      label="Select file type"
                      class="ma-0 input-group--focused"
                      single-line
                      hide-details
                      data-vv-name="select_balor"
                      v-validate="'required'"
                      id="select_balor">
                  </v-select>
                </v-layout>
                <v-layout class="xs12 ma-0">
                  <small v-show="errors.has('select_balor')" class="error--text">* {{ errors.first('select_balor') }}</small>
                </v-layout>
              </div>
              <!--//SELECT-->
              <!--BUTTON-->
              <v-card-row xs12>
                <v-btn
                  :disabled="disabledBtn"
                  @click.native="validateBeforeSubmit()"
                  :class="{ green: valid, red: !valid }"
                  class="primary white--text ma-0">submit</v-btn>
              </v-card-row>
              <!--//BUTTON-->
            </v-flex>
            <!--//+++++col1+++++-->
            </v-layout>
          </v-card>
        </form>
        <!-- //FORM ENDS -->
        </div>
      </v-flex>
    </v-layout>

  </v-container>
</template>

<script>
  import {upload} from './javascript/file-upload.service'

  export default {
    data () {
      return {
        job_balor: '',
        select_balor: '',
        input_balor: '',
        disabledBtn: '',
        items: [
          {text: '.txt (tab separated)'},
          {text: '.CSV ("," delimeter)'},
          {text: '.DSV ("|" delimeter)'},
          {text: '.DSV (";" delimeter)'}
        ],
        dialog: false,
        valid: true,
        uploadedFiles: [],
        uploadError: null,
        currentStatus: null,
        uploadFieldName: ''
      }
    },
    computed: {
      fileIsValid () {
        var inp = document.getElementById('input_balor')
        if (inp.file.length === 0) {
          alert('Attachment Required')
          inp.focus()
          return false
        }
      },
      formIsValid () {
        // var inputField = document.getElementById('input_balor')
        // return this.job_balor !== '' && this.select_balor !== '' && inputField.value !== ''
      }
    },
    methods: {
      fileUpload (fieldName, fileNames) {
        console.log('Entering fileUpload')
        const formData = new FormData()
        if (!fileNames.length) return

        formData.append(fieldName, fileNames[0])

        this.save(formData)
      },
      save (formData) {
        upload(formData)
          .catch(err => {
            alert('There was an error uploading the file.  Please try again.' + err.message.toString())
          })
      },
      submit () {
        console.log('submit job now')
        alert('Job has been submitted. Please check the Job History page for status update.')
      },
      validateBeforeSubmit () {
        var vm = this
        this.$validator.validateAll().then((result) => {
          if (result) {
            alert('Form Submitted!')
            vm.disabledBtn = false
            return
          } else {
            alert('Correct them errors!')
            vm.disabledBtn = true
          }
        })
      }
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.v_card_width {max-width:450px;}
.file {cursor:pointer;}
.file_sample {padding:10px; position:relative; top:3px;}
.centereddiv{
  position:relative;
  width: 100%;
  top: 50%;
  left: 50%;
  transform: translate(-52%, -50%);
  min-width: 450px;
  max-width: 1000px;
}
</style>
