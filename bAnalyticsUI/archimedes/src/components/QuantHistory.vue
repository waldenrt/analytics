/**
* Created by amerrill on 1/5/18.
*/

<template>
  <v-container fluid class="QuantJobHistory">
    <v-layout row wrap class="mt-0 pa-0">
      <v-flex xs12>
        <v-card class="white">
          <v-card-title primary-title class="primary">
            <h6 class="white--text text-xs-left mb-0">Job History</h6>
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
              class="elevation-1"
          >
            <template slot="headers" scope="props">
              <span v-tooltip:bottom="{ 'html': props.item.text }">
                {{ props.item.text }}
              </span>
            </template>
            <template slot="items" scope="props">
              <td class="text-xs-center">
                <v-btn
                  icon="icon"
                  slot="activator"
                  v-on:click.native="updateStore(props.item.jobId, props.item.app, props.item.routeLink)"
                ><v-icon class="warning--text text-xs-center">visibility</v-icon></v-btn>
              </td>
              <td>{{ props.item.jobId }}</td>
              <td class="text-xs-right">{{ props.item.jobName }}</td>
              <td class="text-xs-right">{{ props.item.app }}</td>
              <td class="text-xs-right">{{ props.item.jobStatus }}</td>
              <td class="text-xs-right">{{ props.item.lastDate }}</td>
              <td class="text-xs-right">{{ props.item.recordCount }}</td>
              <!--<td class="text-xs-right pl-1 pr-1">
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
        search: '',
        selected: [],
        headers: [ // Data that populates the Data Table Header Row
          {
            text: '',
            value: 'routeLink'
          },
          {
            text: 'ID',
            left: true,
            sortable: true,
            value: 'jobId'
          },
          { text: 'Name', value: 'jobName' },
          { text: 'Job Type', value: 'app' },
          { text: 'Status', value: 'jobStatus' },
          { text: 'Last Modified Date', value: 'lastDate' },
          { text: 'Record Count', value: 'recordCount' }
          // { text: 'Actions', value: 'action' }
        ],
        clientName: 'BPDemo',
        incomingJson: {}
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
            this.addRouteLinks()
          })
      },
      addRouteLinks () {
        let links = []
        for (let i = 0; i < this.jsonMsg.length; i++) {
          links.push(this.jsonMsg[i])
          links[i].routeLink = '/Pareto/Summary'
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
    width:100%;
    border-radius:2px;
    padding:5px;
    color:##eff3f6;
  }
  .menu .menu__content .card .list {
    padding-top:0;
    padding-bottom:0;
  }
  .list__tile{height:38px !important;}
  .inliner { display: inline-block; }
</style>
