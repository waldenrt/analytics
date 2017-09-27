<template>
  <v-container fluid class="NewBalor pl-3 pr-3">
    <v-layout row wrap>
      <!-- COLUMN1 -->
      <v-flex d-flex xs12 sm12 md6>
        <!-- CARD1 -->
        <v-card class="white v_card_width">
          <v-card-title primary-title class="primary">
            <h6 class="white--text text-xs-left mb-0">Enter Job Name</h6>
          </v-card-title>
          <v-text-field
              name="input-1"
              label="Enter job name"
              class="ma-3 pr-4 pa-2"
              required
          ></v-text-field>
        </v-card>
        <!-- //CARD1 -->
        <!-- CARD2 -->
        <v-card class="white v_card_width mb-3">
          <v-card-title primary-title class="primary">
            <h6 class="white--text text-xs-left mb-0">Select file for analysis</h6>
          </v-card-title>
          <form enctype="multipart/form-data">
            <input type="file" :name="uploadFieldName"  @change="fileUpload($event.target.name, $event.target.files)" class="pa-3">
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
              class="ma-3 pr-4 pa-2"
              single-line
              bottom
              v-bind:error-messages="['Please select an option']"
              required
          ></v-select>
        </v-card>
        <!-- //CARD3 -->
        <v-btn @click="submit" :class="{ green: valid, red: !valid }" class="primary white--text">submit</v-btn>
        <v-btn @click="clear">clear</v-btn>
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
        <!--<v-card class="white mt-3">
          <img src="http://via.placeholder.com/525x150/EDEDED/ffffff?text=User+File+Preview" width="100%" height="100%" class="file_sample">
        </v-card>-->
      </v-flex>
      <!-- //COLUMN2 -->
    </v-layout>
  </v-container>
</template>

<script>
  import {upload} from './javascript/file-upload.service'

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
        valid: true,
        uploadedFiles: [],
        uploadError: null,
        currentStatus: null,
        uploadFieldName: 'file'
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
      },
      clear () {
        console.log('clear all fields')
      }
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.v_card_width {max-width:450px;}
.file_sample {padding:10px; position:relative; top:3px;}
</style>
