<template>
  <v-container fluid class="JobHistory">
    <v-layout row wrap class="mt-5 pa-0">
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
  import {userJobs, addHistory} from './javascript/job.service'

  export default {
    name: 'jobHistory',
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
      this.$store.commit('switchApp', {module: 'Global Job History'})
    //  this.addHistoryItem()
      this.getResults()
     // this.statusColor()
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
            this.addRouteLinks()
          })
      },
      addRouteLinks () {
        let links = []
        for (let i = 0; i < this.jsonMsg.length; i++) {
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
      },
      statusColor () { // leaving this method. removing it from the data table until I can figure out how to activate method when the pagination activates.
        var x = document.getElementsByClassName('status_block')
        for (var i = 0; i < this.items.length; i++) {
          if (this.items[i].status === 'File Uploaded') {
            x[i].style.backgroundColor = '#005E76'
          } else if (this.items[i].status === 'Cadence Complete') {
            x[i].style.backgroundColor = '#8EAC1D'
          } else if (this.items[i].status === 'Balor Complete') {
            x[i].style.backgroundColor = '#8EAC1D'
          } else if (this.items[i].status === 'Running') {
            x[i].style.backgroundColor = '#F7970E'
          }
        }
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
.menu__content {
  z-index: 1;
}
.inliner { display: inline-block; }
</style>
