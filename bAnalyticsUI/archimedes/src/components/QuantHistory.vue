/**
* Created by amerrill on 1/5/18.
*/

<template>
  <v-container fluid class="QuantJobHistory">
    <v-layout row wrap class="mt-0 pa-0">
      <v-flex xs12>
        <v-card class="white">
          <v-card-title primary-title class="primary pt-2 pb-2">
            <h6 class="white--text text-xs-left mb-0">Job History</h6>
            <v-spacer></v-spacer>
            <v-btn success router to="/Pareto/NewPareto" class="white--text text-xs-left">+ NEW PARETO INPUT</v-btn>
          </v-card-title>
        </v-card>
      </v-flex>
    </v-layout>
    <v-layout row wrap class="mt-3 mb-3 pa-0">
      <v-flex xs12>
        <v-card flat class="white">
          <v-data-table
              v-bind:headers="headers"
              v-bind:items="jsonMsg"
              v-bind:search="search"
              v-bind:pagination.sync="pagination"
              class="elevation-1 pa-0 ma-0"
          >
            <template slot="headers" scope="props">
              <span v-tooltip:bottom="{ 'html': props.item.text }">
                {{ props.item.text }}
              </span>
            </template>
            <template slot="items" scope="props">
              <td class="text-xs-center pl-0 pr-0">
                <v-btn
                  icon="icon"
                  slot="activator"
                  :disabled="props.item.resultsInProgress"
                  v-on:click.native="updateStore(props.item.jobId, props.item.app, props.item.routeLink)"
                ><v-icon class="text-xs-center" :class="props.item.enableDisable">{{props.item.jobIcon}}</v-icon></v-btn>
              </td>
              <td class="text-xs-left pl-2 pr-2">{{ props.item.jobName }}</td>
              <td class="text-xs-left pl-2 pr-2">{{ props.item.jobId }}</td>
              <td class="text-xs-left pl-2 pr-2">{{ props.item.app }}</td>
              <td class="text-xs-left pl-2 pr-2 status_cell">
                <div>
                  <div class="status_block" :style="props.item.bgColors"></div>
                  <div>{{ props.item.jobStatus }}</div>
                </div>
              </td>
              <td class="text-xs-left pl-2 pr-2">{{ props.item.lastDate }}</td>
              <td class="text-xs-left pl-2 pr-2">{{ props.item.recordCount }}</td>
              <!--<td class="text-xs-left pl-1 pr-1">
                <div class="inliner">
                  <v-btn icon="icon" light class="pa-0 ma-0">
                    <v-icon class="success--text text-xs-right">get_app</v-icon>
                  </v-btn>
                </div>
                <div class="inliner">
                  <v-btn :click.native="deleteJob" icon="icon" light class="pa-0 ma-0">
                    <v-icon class="error--text text-xs-right">delete</v-icon>
                  </v-btn>
                </div>
              </td>-->
            </template>
          </v-data-table>
        </v-card>
      </v-flex>
    </v-layout>
  </v-container>
</template>

<script>
  import {userAppJobs} from './javascript/job.service'

  export default {
    name: 'QuantHistory',
    data () {
      return {
        pagination: { page: 1, rowsPerPage: 10, descending: false, totalItems: 0 },
        search: '',
        selected: [],
        headers: [ // Data that populates the Data Table Header Row
          { text: '', left: true, value: 'routeLink' },
          { text: 'Name', left: true, value: 'jobName' },
          { text: 'ID', left: true, sortable: true, value: 'jobId' },
          { text: 'Job Type', left: true, value: 'app' },
          { text: 'Status', left: true, value: 'jobStatus' },
          { text: 'Last Modified Date', left: true, value: 'lastDate' },
          { text: 'Record Count', left: true, value: 'recordCount' }
          // { text: 'Actions', value: 'action' }
        ],
        clientName: 'BPDemo',
        incomingJson: {},
        resultsInProgress: false
      }
    },
    computed: {
      jsonMsg: function () {
        return this.incomingJson.data
      },
      user: function () {
        return this.$store.state.user
      }
    },
    mounted () {
      this.$store.commit('switchApp', {module: 'Pareto Job History'})
      this.getResults()
    },
    methods: {
      getResults () {
        userAppJobs(this.user, 'pareto')
          .catch(err => {
            alert('Could not get User Pareto Job History results. ' + err.message.toString())
          })
          .then((response) => {
            this.incomingJson = response.data
            this.addToJson()
          })
      },
      addToJson () {
        let links = []
        for (let i = 0; i < this.jsonMsg.length; i++) {
          links.push(this.jsonMsg[i])
          links[i].routeLink = '/Pareto/Summary'
          // Adding status colors to the status column
          // Enabling or Disabling view icon on results ready
          if (this.jsonMsg[i].jobStatus.includes('Finished') || this.jsonMsg[i].jobStatus.includes('finished')) {
            links[i].bgColors = 'backgroundColor: #8EAC1D'
            links[i].resultsInProgress = false // creates property for results in progress
            links[i].jobIcon = 'visibility' // changes visibility icon, clickable when results, disables when no results
            links[i].enableDisable = 'success--text' // class to bind to jobIcon
          } else if (this.jsonMsg[i].jobStatus.includes('Running') || this.jsonMsg[i].jobStatus.includes('running')) {
            links[i].bgColors = 'backgroundColor: #F7970E'
            links[i].resultsInProgress = true
            links[i].jobIcon = 'visibility_off'
            links[i].enableDisable = 'grey--text'
          } else if (this.jsonMsg[i].jobStatus.includes('Awaiting') || this.jsonMsg[i].jobStatus.includes('awaiting')) {
            links[i].bgColors = 'backgroundColor: #006984'
            links[i].resultsInProgress = true
            links[i].jobIcon = 'visibility_off'
            links[i].enableDisable = 'grey--text'
          } else if (this.jsonMsg[i].jobStatus.includes('Error') || this.jsonMsg[i].jobStatus.includes('error')) {
            links[i].bgColors = 'backgroundColor: #D63A09'
            links[i].resultsInProgress = true
            links[i].jobIcon = 'visibility_off'
            links[i].enableDisable = 'grey--text'
          } else if (this.jsonMsg[i].jobStatus.includes('Complete') || this.jsonMsg[i].jobStatus.includes('complete')) {
            links[i].bgColors = 'backgroundColor: #8EAC1D'
            links[i].resultsInProgress = false
            links[i].jobIcon = 'visibility'
            links[i].enableDisable = 'success--text'
          }
        }
      },
      updateStore (jobId, app, routeLink) {
        this.$store.dispatch('setJobKey', {'jobid': jobId, 'app': app}).then(() => {
          this.$router.push(routeLink)
        })
      }
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.status_block {
    display:inline-block;
    width:16px;
    height:16px;
    border-radius:3px;
    margin-right:5px;
  }
.status_cell div {
  display:inline-block;
  vertical-align: middle;
}
</style>
