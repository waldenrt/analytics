<template>
  <v-container fluid class="NewBalor pl-3 pr-3" style="height:100%;">

    <v-layout row wrap style="height:100%;">
      <v-flex xs6 offset-xs3 class="pl-0 pr-0 mt-0">
        <div class="centereddiv">
        <form name="balForm" @submit.prevent="submit()" novalidate>
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
            <v-layout row wrap style="">
            <!--+++++col1+++++-->
            <v-flex xs12 class="pa-3 pl-4 pr-4">
              <!--FIELD-->
              <div class="body-2">Enter Job Name</div>
              <v-card-row xs12 class="mt-0 mb-3">
                <v-text-field
                    name="job_balor"
                    label="Enter Job Name"
                    v-model="job_balor"
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
                    v-model="select_balor"
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
                  @click.native="submit()"
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
  import {upload} from './javascript/file-upload.service'

  export default {
    data () {
      return {
        job_balor: '',
        select_balor: '',
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
        uploadFieldName: 'file'
      }
    },
    computed: {
      formIsValid () {
        return this.job_balor !== '' && this.select_balor !== ''
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
            alert('There was an error uploading the file.  Please try again.  ' + err.message.toString())
          })
      },
      submit () {
        console.log('submit job now')
        alert('Job has been submitted. Please check the Job History page for status update.')
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
