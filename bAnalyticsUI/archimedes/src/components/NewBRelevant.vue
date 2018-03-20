<template>
  <v-container fluid class="NewBRelevant pl-3 pr-3">
    <v-layout row wrap style="height:100%;">
      <v-flex xs12  sm8 offset-sm2  md6 offset-md3  class="pl-0 pr-0 mt-0 mb-5">

          <!-- FORM STARTS -->
          <form name="bRelevantForm" @submit.prevent="validateBeforeSubmit()">
          <v-card class="white v_card">
            <v-card-title primary-title class="primary">
              <div><h6 class="white--text text-xs-left mb-0">b-Relevant Recommender System Input</h6></div>
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
                      v-model="job_brelevant"
                      class="ma-0 input-group--focused custom_input"
                      single-line
                      hide-details
                      data-vv-name="job_brelevant"
                      v-validate="'required'"
                      id="job_pareto">
                  </v-text-field>
                </v-layout>
                <v-layout class="xs12 ma-0">
                  <small v-show="vErrors.has('job_brelevant')" class="error--text">* {{ vErrors.first('job_brelevant') }}</small>
                </v-layout>
              </div>
              <!--//FIELD-->
              <!--FILE-LOADER-->
              <div class="xs12 pb-3">
                <label class="body-2">Select file for analysis</label>
                <div class="file">
                  <v-dialog v-model="dialog" width="1000px">
                    <div slot="activator">
                      <div class="inliner"><v-icon class="warning--text" slot="activator">note</v-icon></div>
                    </div>
                    <v-card style="width:1000px;">
                      <img src="../assets/images/pareto_file_img.png" width="100%" height="100%" class="file_sample">
                    </v-card>
                  </v-dialog>
                </div>
                <v-layout xs12 class="pad_LR12">
                <form enctype="multipart/form-data" style="width:100%;">
                  <input
                    type="file"
                    :name="uploadFieldName"
                    @change="fileUpload($event.target.name, $event.target.files)"
                    class="ma-0 input-group--focused custom_input"
                    style="width:100%;"
                    data-vv-name="file_brelevant"
                    v-validate="'required|ext:txt,csv,dsv'"
                    id="input_pareto1">
                </form>
                </v-layout>
                <v-layout class="xs12 ma-0">
                  <small v-show="vErrors.has('file_brelevant')" class="error--text">* {{ vErrors.first('file_brelevant') }}</small>
                </v-layout>
              </div>
              <!--//FILE-LOADER-->
              <!--SELECT-->
              <div class="xs12">
                <label class="body-2">Select file type</label>
                <v-layout xs12 class="pad_LR12">
                  <v-select
                      v-bind:items="items1"
                      v-model="select_delimiter"
                      label="Select file type"
                      class="ma-0 input-group--focused custom_input"
                      single-line
                      hide-details
                      data-vv-name="select_delimiter"
                      v-validate="'required'"
                      id="select_delimiter">
                  </v-select>
                </v-layout>
                <v-layout class="xs12 ma-0">
                  <small v-show="vErrors.has('select_delimiter')" class="error--text">* {{ vErrors.first('select_delimiter') }}</small>
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
                  <label class="body-2">Select analysis to be performed:</label>
                  <p class="control mb-0 custom_input">
                    <v-layout xs12 class="pad_LR12">
                    <v-radio
                        label="Market Basket Analysis"
                        v-model="dim_radio"
                        value="market"
                        hide-details
                        data-vv-name="dim_radio"
                        v-validate="'required|in:market,personalized,both'"
                        dark></v-radio>
                    </v-layout>
                    <v-layout xs12 class="pad_LR12">
                    <v-radio
                        label="Personalized Recommender"
                        v-model="dim_radio"
                        value="personalized"
                        hide-details
                        data-vv-name="dim_radio"
                        v-validate="'required'"
                        dark></v-radio>
                      </v-layout>
                      <v-layout xs12 class="pad_LR12">
                      <v-radio
                        label="Both"
                        v-model="dim_radio"
                        value="both"
                        hide-details
                        data-vv-name="dim_radio"
                        v-validate="'required'"
                        dark></v-radio>
                      </v-layout>
                    </p>
                    <v-layout class="xs12 ma-0">
                      <small v-show="vErrors.has('dim_radio')" class="error--text">* {{ vErrors.first('dim_radio') }}</small>
                    </v-layout>
                </div>
                <!--//SELECT-->
                <!--FIELD-->
                <div class="xs12 pb-3">
                  <label class="body-2">Enter product hierarchy level for market basket analysis:</label>
                  <v-layout xs12 class="pad_LR12">
                  <v-text-field
                      label="Text"
                      v-model="product1"
                      class="ma-0 input-group--focused custom_input"
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
              </v-flex>
              <!--//+++++col1+++++-->
              <!--+++++col2+++++-->
              <v-flex xs6 class="pt-3 pb-0 pl-4 pr-4">
                <!--FIELD-->
                <div class="xs12 pb-3">
                  <label class="body-2">Desired minimum transaction frequency of product bundles:</label>
                  <v-layout xs12 class="pad_LR12">
                    <v-text-field
                        label="Enter number of products"
                        v-model="minTrnx"
                        class="ma-0 input-group--focused custom_input"
                        single-line
                        hide-details
                        data-vv-name="minTrnx"
                        v-validate="'required'"
                        id="minTrnx">
                    </v-text-field>
                  </v-layout>
                  <v-layout class="xs12 ma-0">
                    <small v-show="vErrors.has('minTrnx')" class="error--text">* {{ vErrors.first('minTrnx') }}</small>
                  </v-layout>
                </div>
                <!--//FIELD-->
                <!--SELECT-->
                <div class="xs12">
                  <label class="body-2">Select time period for analysis</label>
                  <v-layout xs12 class="pad_LR12">
                    <v-select
                        v-bind:items="items2"
                        v-model="select_tp"
                        label="Select time period"
                        class="ma-0 input-group--focused custom_input"
                        single-line
                        hide-details
                        data-vv-name="select_tp"
                        v-validate="'required'"
                        id="select_tp">
                    </v-select>
                  </v-layout>
                  <v-layout class="xs12 ma-0">
                    <small v-show="vErrors.has('select_tp')" class="error--text">* {{ vErrors.first('select_tp') }}</small>
                  </v-layout>
                </div>
                <!--//SELECT-->
              </v-flex>
              <!--//+++++col2+++++-->
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
        <!-- FORM ENDS -->
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
        job_brelevant: '',
        select_delimiter: '',
        select_tp: '',
        minTrnx: '',
        product1: '',
        dim_radio: '',
        disabledBtn: '',
        items1: [
          {text: '.txt (tab separated)', value: '/t'},
          {text: '.CSV ("," delimiter)', value: ','},
          {text: '.DSV ("|" delimiter)', value: '|'},
          {text: '.DSV (";" delimiter)', value: ';'}
        ],
        items2: [
          {text: 'Bi-monthly (calendar)', value: '1'},
          {text: 'Quarterly (calendar)', value: '3'},
          {text: 'Half yearly (calendar)', value: '6'},
          {text: 'Yearly (calendar)', value: '12'},
          {text: '1 month (from end of file)', value: '3'},
          {text: '2 months (from end of file)', value: '6'},
          {text: '3 months (from end of file)', value: '12'},
          {text: '6 months (from end of file)', value: '3'},
          {text: '12 months (from end of file)', value: '6'}
        ],
        dialog: false,
        valid: true,
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
        var jobObj = {
          'client': this.client,
          'user': this.user,
          'powerUser': false,
          'app': 'pareto',
          'jobName': this.job_brelevant,
          'jobId': '',
          'fileName': 'hdfs:///user/admin/' + this.uploadedFile,
          'delimiter': this.select_delimiter,
          'args': [
            {'name': 'timePeriod', 'value': this.select_tp},
            {'name': 'minTrnx', 'value': this.minTrnx},
            {'name': 'dimension', 'value': this.dim_radio}
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
.file {cursor:pointer;display: inline-block;}
.file_sample {padding:10px; position:relative; top:3px;}
.pad_LR12 {padding-left:12px; padding-right:12px;}
.input-group {margin-top:0; margin-bottom:0;}
.mar_field1 {margin-top:19px;}
.mar_field2 {margin-top:34px;}
.mar_field3 {margin-top:34px;}
</style>
