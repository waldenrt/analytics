<template>
  <v-container fluid class="fileBrowser pa-0" style="height:100%;">
    <v-layout row wrap class="mt-0 mb-3 pa-0" style="height:100%;">
      <v-flex xs12>
        <div class="marT_img pb-5">
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
                            <h6 class="info--text text-xs-left mb-0">Selected: <span class="success--text">{{ selected_file }}</span></h6>
                          </v-card-title>
                          <v-subheader class="grey lighten-5">Select a File</v-subheader>
                          <v-card-text>
                            <v-radio-group name="file_browser">
                              <v-radio
                                  v-for="item in file_items"
                                  name="item"
                                  :label="item"
                                  :value="item"
                                  v-model="selected_file"
                                  hide-details
                                  @click.native="browser_file_selected()"></v-radio>
                            </v-radio-group>
                          </v-card-text>
                          <v-divider class="grey lighten-2"></v-divider>
                          <v-card-row xs12 class="pa-3">
                              <v-spacer></v-spacer>
                              <v-btn :disabled="valid_select" class="grey white--text mt-0 mb-0 ml-0 mr-2">
                                <v-icon class="white--text">close</v-icon>
                              </v-btn>
                              <v-btn :disabled="valid_select" class="success white--text ma-0">Save</v-btn>
                          </v-card-row>
                        </v-card>
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
        file_items: []
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
            this.incomingJson = response
            this.parseJson()
          })
          .catch(err => {
            alert('Could not get file browser results. ' + err.message.toString())
          })
      },
      parseJson () {
        console.log('incomingJson: ')
        console.log(this.jsonMsg)
        this.file_items = this.jsonMsg.fileNames
      },
      browser_file_selected () {
        console.log(this.selected_file)
      }
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.marT_img {margin-top:59px;width:100%;}
</style>
