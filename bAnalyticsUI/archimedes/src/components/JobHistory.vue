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
            v-bind:items="items"
            v-bind:search="search"
            v-model="selected"
            selected-key="id"
            select-all
            class="elevation-1"
          >
            <template slot="headers" scope="props">
              <span v-tooltip:bottom="{ 'html': props.item.text }">
                {{ props.item.text }}
              </span>
            </template>
            <template slot="items" scope="props">
              <td>
                <v-checkbox
                  primary
                  hide-details
                  v-model="props.selected"
                ></v-checkbox>
              </td>
              <td>{{ props.item.id }}</td>
              <td class="text-xs-right">{{ props.item.name }}</td>
              <td class="text-xs-right">{{ props.item.job }}</td>
              <td class="text-xs-right">
                  <div class="status_block">{{ props.item.status }}</div>
              </td>
              <td class="text-xs-right">{{ props.item.date_mod }}</td>
              <td class="text-xs-right">{{ props.item.record }}</td>
              <td class="text-xs-right pl-1" style="width:10px !important;">
                <v-menu bottom left offset-y>
                  <v-btn icon="icon" slot="activator" light>
                    <v-icon class="primary--text text-xs-right pl-4">more_vert</v-icon>
                  </v-btn>
                  <v-list>
                    <v-list-item v-for="drop in drops" :key="drop.slot">
                      <v-list-tile>
                        <v-list-tile-title>{{ drop.title }}</v-list-tile-title>
                      </v-list-tile>
                    </v-list-item>
                  </v-list>
                </v-menu>
              </td>
            </template>
          </v-data-table>
        </v-card>
      </v-flex>
    </v-layout>
  </v-container>
</template>

<script>
  export default {
    name: 'jobHistory',
    data () {
      return {
        search: '',
        selected: [],
        headers: [ // Data that populates the Data Table Header Row
          {
            text: 'ID',
            left: true,
            sortable: true,
            value: 'id'
          },
          { text: 'Name', value: 'name' },
          { text: 'Job Type', value: 'job' },
          { text: 'Status', value: 'status' },
          { text: 'Last Modified Date', value: 'date_mod' },
          { text: 'Record Count', value: 'record' },
          { text: 'Actions', value: 'action' }
        ],
        items: [ // Data that populates the Data Table
          {
            value: false,
            id: '0000-0001',
            name: 'Name01',
            job: 'Balor',
            status: 'File Uploaded',
            date_mod: '2017-01-01',
            record: 100,
            action: ''
          },
          {
            value: false,
            id: '0000-0002',
            name: 'Name02',
            job: 'Balor',
            status: 'Cadence Complete',
            date_mod: '2017-05-01',
            record: 90,
            action: ''
          },
          {
            value: false,
            id: '0000-0003',
            name: 'Name03',
            job: 'Core Lifecycle',
            status: 'Running',
            date_mod: '2017-08-01',
            record: 80,
            action: ''
          },
          {
            value: false,
            id: '0000-0004',
            name: 'Name04',
            job: 'bRelevant',
            status: 'Running',
            date_mod: '2017-10-01',
            record: 70,
            action: ''
          },
          {
            value: false,
            id: '0000-0005',
            name: 'Name05',
            job: 'Balor',
            status: 'Balor Complete',
            date_mod: '2017-06-01',
            record: 40,
            action: ''
          },
          {
            value: false,
            id: '0000-0006',
            name: 'Name06',
            job: 'Balor',
            status: 'Cadence Complete',
            date_mod: '2017-02-01',
            record: 17,
            action: ''
          },
          {
            value: false,
            id: '0000-0007',
            name: 'Name07',
            job: 'Balor',
            status: 'Balor Complete',
            date_mod: '2017-09-01',
            record: 1,
            action: ''
          },
          {
            value: false,
            id: '0000-0008',
            name: 'Name08',
            job: 'Balor',
            status: 'Balor Complete',
            date_mod: '2017-07-01',
            record: 6,
            action: ''
          },
          {
            value: false,
            id: '0000-0009',
            name: 'Name09',
            job: 'Balor',
            status: 'Balor Complete',
            date_mod: '2017-04-01',
            record: 5,
            action: ''
          },
          {
            value: false,
            id: '0000-0010',
            name: 'Name10',
            job: 'Balor',
            status: 'Balor Complete',
            date_mod: '2017-03-01',
            record: 7,
            action: ''
          }
        ],
        drops: [ // Dropdown for each job
          { title: 'View', slot: 1 },
          { title: 'Delete', slot: 2 },
          { title: 'Export', slot: 3 }
        ]
      }
    },
    methods: {
      statusColor () {
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
    },
    mounted () {
      this.statusColor()
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
</style>
