<template>
  <v-container fluid class="JobHistory">
    <v-layout row wrap class="mt-5 pa-0">
      <v-flex xs12>
        <v-card class="white">
          <v-card-title primary-title class="primary pt-2 pb-2">
            <h6 class="white--text text-xs-left mb-0">Job History</h6>
            <v-spacer></v-spacer>
            <!--NEW INPUT DROPDOWN-->
            <v-menu bottom left offset-y>
              <v-btn success slot="activator" class="white--text">+ NEW INPUT<v-icon class="white--text">arrow_drop_down</v-icon></v-btn>
              <v-list>
                <v-list-item v-for="module in modules" :key="module.name" class="pl-3 drop_item">
                  <a :href="module.link" class="body-2">{{ module.name }}</a>
                </v-list-item>
              </v-list>
            </v-menu>
            <!--//NEW INPUT DROPDOWN-->
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
                  round
                  success
                  icon="icon"
                  slot="activator"
                  v-on:click.native="updateStore(props.item.jobId, props.item.app, props.item.routeLink)"
                ><v-icon class="white--text text-xs-center">visibility</v-icon></v-btn>
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
                    <v-icon class="error--text text-xs-left">delete</v-icon>
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
  import {userJobs, addHistory} from './javascript/job.service'

  export default {
    name: 'jobHistory',
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
          // { text: 'Actions', left: true, value: 'action' }
        ],
        modules: [
          {name: 'BALOR', icon: 'label', link: '#/Balor/NewBalor'},
          {name: 'Pareto', icon: 'label', link: '#/Pareto/NewPareto'},
          {name: 'Core Lifecycle', icon: 'label', link: '#/Lifecycle/NewLifecycle'},
          {name: 'b-Relevant', icon: 'label', link: '#/bRelevant/NewBRelevant'}
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
      this.$store.commit('switchApp', {module: 'Global Job History'})
    //  this.addHistoryItem()
      this.getResults()
    },
    methods: {
      getResults () {
        userJobs(this.user)
          .catch(err => {
            alert('Could not get Client Job History results. ' + err.message.toString())
          })
          .then((response) => {
            this.incomingJson = response.data
            console.log('this is the incoming response.data')
            console.log(this.incomingJson)
            this.addToJson()
          })
      },
      addToJson () {
        let links = []
        for (let i = 0; i < this.jsonMsg.length; i++) {
          // Adding Router Links to the jsonMsg Array
          if (this.jsonMsg[i].app === 'Balor' || this.jsonMsg[i].app === 'balor') {
            links.push(this.jsonMsg[i])
            links[i].routeLink = '/Balor/Cadence'
          } else if (this.jsonMsg[i].app === 'Pareto' || this.jsonMsg[i].app === 'pareto') {
            links.push(this.jsonMsg[i])
            links[i].routeLink = '/Pareto/Summary'
          } else if (this.jsonMsg[i].app === 'Lifecycle' || this.jsonMsg[i].app === 'lifecycle') {
            links.push(this.jsonMsg[i])
            links[i].routeLink = '/Lifecycle/Summary'
          }
          // Adding status colors to the status column
          if (this.jsonMsg[i].jobStatus.includes('Finished') || this.jsonMsg[i].jobStatus.includes('finished')) {
            links[i].bgColors = 'backgroundColor: #8EAC1D'
          } else if (this.jsonMsg[i].jobStatus.includes('Running') || this.jsonMsg[i].jobStatus.includes('running')) {
            links[i].bgColors = 'backgroundColor: #F7970E'
          } else if (this.jsonMsg[i].jobStatus.includes('Awaiting') || this.jsonMsg[i].jobStatus.includes('awaiting')) {
            links[i].bgColors = 'backgroundColor: #006984'
          } else if (this.jsonMsg[i].jobStatus.includes('Error') || this.jsonMsg[i].jobStatus.includes('error')) {
            links[i].bgColors = 'backgroundColor: #D63A09'
          } else if (this.jsonMsg[i].jobStatus.includes('Complete') || this.jsonMsg[i].jobStatus.includes('complete')) {
            links[i].bgColors = 'backgroundColor: #8EAC1D'
          }
        }
      },
      addHistoryItem () {
        let histObj = {
          'client': 'BPDemo',
          'user': 'Admin',
          'jobId': 'aeoInstoreMayank',
          'jobName': 'aeoInstoreMayank',
          'app': 'balor',
          'powerUser': false,
          'fileName': 'hdfs:///test/test.csv',
          'delimiter': ',',
          'jobStatus': 'Complete',
          'lastDate': '12/21/2017',
          'recordCount': 10000000,
          'args': '',
          'livyId': '',
          'errorMsg': ''
        }
        addHistory(histObj)
          .catch(err => {
            alert('Could not add History Item. ' + err.message.toString())
          })
          .then((response) => {
            console.log(this.response)
          })
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
.drop_item a {
  color: #354052;
  text-decoration: none;
  display: block;
  padding: 10px 0;
}
.drop_item a:hover {
  color: #FFFFFF;
  font-weight: bold;
}
.drop_item:hover {
  color: #FFFFFF;
  background-color: #8EAC1D;
}
</style>
