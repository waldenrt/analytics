<template>
  <v-container fluid class="fileBrowser pa-0" style="height:100%;">
    <v-layout row wrap class="mt-0 mb-3 pa-0" style="height:100%;">
      <v-flex xs12>
        <div class="marT_img">
            <v-list two-line dense>
                <template>
                  <v-container fluid>
                    <v-layout row wrap>
                      <v-flex xs12>
                        <v-card class="white">
                          <v-card-title primary-title class="info">
                            <h6 class="white--text text-xs-left mb-0">{{ client_name }}</h6>
                          </v-card-title>
                          <v-card-title primary-title class="grey lighten-2">
                            <h6 class="info--text text-xs-left mb-0">Selected: {{ selected_file }}</h6>
                          </v-card-title>
                          <v-subheader>Select a File</v-subheader>
                          <v-card-text>
                            <v-radio-group name="file_browser">
                              <v-radio
                                  v-for="item in file_items"
                                  name="item.filename"
                                  :label="item.filename"
                                  :value="item.value"
                                  v-model="selected_file"
                                  hide-details
                                  @click.native="browser_file_selected()"></v-radio>
                            </v-radio-group>
                          </v-card-text>
                          <v-divider class="grey lighten-2"></v-divider>
                          <v-card-row xs12 class="pa-3">
                            <v-spacer></v-spacer>
                              <v-btn :disabled="valid_select" class="success white--text ma-0">Submit</v-btn>
                          </v-card-row>
                        </v-card>
                      </v-flex>
                    </v-layout>
                    <v-layout row wrap>
                      <v-flex xs12>
                        <v-btn class="info white--text" @click.native="dialog2 = false">
                          <v-icon class="white--text">close</v-icon>
                        </v-btn>
                        <v-btn class="info white--text" @click.native="dialog2 = false">Save</v-btn>
                      </v-flex>
                    </v-layout>
                  </v-container>
                </template>
            </v-list>
        </div>
      </v-flex>
    </v-layout>
  </v-container>
</template>

<script>
  import {fileList} from './javascript/file-upload.service'

  export default {
    name: 'fileBrowser',
    data () {
      return {
        incomingJson: {},
        selected_file: '',
        client_name: 'Client Name Goes Here',
        dialog2: '',
        file_items: [
          {filename: 'File1', value: 'value1'},
          {filename: 'File2', value: 'value2'},
          {filename: 'File3', value: 'value3'},
          {filename: 'File4', value: 'value4'},
          {filename: 'File5', value: 'value5'}
        ]
      }
    },
    computed: {
      valid_select: function () {
        var val = null
        if (this.selected_file === '') {
          val = true
        } else {
          val = false
        }
        return val
      },
      jsonMsg: function () {
        return this.incomingJson.data
      }
    },
    mounted () {
      this.$store.commit('switchApp', {module: 'File Browser'})
      this.getResults()
    },
    methods: {
      getResults () {
        fileList()
          .then((response) => {
            console.log('We have a response: ', response)
            // this.incomingJson = response.data
            // this.parseJson()
          })
          .catch(err => {
            alert('Could not get file browser results. ' + err.message.toString())
          })
      },
      parseJson () {
        // console.log('incomingJson: ')
        // console.log(this.jsonMsg)
      },
      browser_file_selected () { // fileBrowser
        console.log(this.selected_file)
      }
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.marT_img {margin-top:59px;width:100%;}
</style>
