<template>
  <v-container>
    <v-select v-bind:items="tpArray"
              v-model="tpSelect"
              label="Select Time Period"
              single-line
              bottom></v-select>
    <v-select v-bind:items="quantArray"
              v-model="priorPeriod"
              label="Select Prior Period Quantiles"
              multiple
              single-line
              bottom></v-select>
    <v-select v-bind:items="quantArray"
              v-model="postPeriod"
              label="Select Post Period Quantiles"
              multiple
              single-line
              bottom></v-select>
    <v-select v-bind:items="views"
              v-model="viewType"
              label="View Table As"
              single-line
              bottom></v-select>
    <v-data-table
      v-bind:headers="quantHeaders"
      :items="quantMigItems"
      hide-actions>
      <template slot="items" scope="props">
        <td>{{ props.item.from }}</td>
        <td>{{ props.item.key1 }}</td>
        <td>{{ props.item.key2 }}</td>
        <td>{{ props.item.key3 }}</td>
        <td>{{ props.item.key4 }}</td>
        <td>{{ props.item.key5 }}</td>
        <td>{{ props.item.key6 }}</td>
        <td>{{ props.item.key7 }}</td>
        <td>{{ props.item.key8 }}</td>
        <td>{{ props.item.key9 }}</td>
        <td>{{ props.item.key10 }}</td>
      </template>
    </v-data-table>
  </v-container>
</template>

<script>
  export default {
    name: 'quantMigration',
    components: {},
    data () {
      return {
        incomingJson: {
          'responseCode': 0,
          'isError': '',
          'httpStatusCode': 200,
          'data': {
            'quantileDimension': {
              'numRecords': 1000,
              'minDate': '01/01/2017',
              'maxDate': '12/31/2017',
              'dimension': 'Products'
            },
            'quantileMigration': [{
              'timePeriod': 1,
              'migrationData': [{
                'fromQuantile': 1,
                'currentQuantile': 2,
                'migrationCount': 1000
              }, {
                'fromQuantile': 2,
                'currentQuantile': 3,
                'migrationCount': 2000
              }, {
                'fromQuantile': 1,
                'currentQuantile': 1,
                'migrationCount': 500
              }, {
                'fromQuantile': 2,
                'currentQuantile': 4,
                'migrationCount': 5000
              }],
              'quantileTotals': [{
                'quantile': 1,
                'newCount': 3000
              }, {
                'quantile': 2,
                'newCount': 2500
              }]
            }, {
              'timePeriod': 2,
              'migrationData': [{
                'fromQuantile': 1,
                'currentQuantile': 2,
                'migrationCount': 100
              }, {
                'fromQuantile': 2,
                'currentQuantile': 3,
                'migrationCount': 220
              }, {
                'fromQuantile': 1,
                'currentQuantile': 1,
                'migrationCount': 300
              }, {
                'fromQuantile': 2,
                'currentQuantile': 4,
                'migrationCount': 400
              }],
              'quantileTotals': [{
                'quantile': 3,
                'newCount': 1500
              }, {
                'quantile': 4,
                'newCount': 7000
              }]
            }]
          },
          'errors': '',
          'moreInfo': '',
          'userMessage': 'Success',
          'developerMessage': ''
        },
        tpSelect: 1,
        priorPeriod: [],
        postPeriod: [],
        viewType: 'Counts',
        views: ['Counts', 'Percentages'],
        tpArray: [],
        quantArray: [],
        quantHeaders: [
          {text: '', value: 'from'},
          {text: '1', value: 'key1'},
          {text: '2', value: 'key2'},
          {text: '3', value: 'key3'},
          {text: '4', value: 'key4'},
          {text: '5', value: 'key5'},
          {text: '6', value: 'key6'},
          {text: '7', value: 'key7'},
          {text: '8', value: 'key8'},
          {text: '9', value: 'key9'},
          {text: '10', value: 'key10'}
        ],
        quantMigItems: []
      }
    },
    computed: {
      jsonMsg: function () {
        return this.incomingJson.data
      }
    },
    mounted () {
      this.createArrays()
    },
    methods: {
      createArrays () {
        var initArray = this.jsonMsg.quantileMigration
        var tpConverted = []
        var quants = []
        var tempTP = []

        var numQuants = Math.sqrt(initArray[1].migrationData.length)

        for (var i = 1; i <= numQuants; i++) {
          quants.push({'from': i})
        }

        for (var j = 0; j < initArray.length; j++) {
          tempTP.push(initArray[j].timePeriod)
          var tempConvert = JSON.parse(JSON.stringify(quants))
          for (var x = 0; x < initArray[j].migrationData.length; x++) {
            for (var k = 0; k < tempConvert.length; k++) {
              let key = 'key' + initArray[j].migrationData[x].currentQuantile
              if (tempConvert[k].from === initArray[j].migrationData[x].fromQuantile) {
                tempConvert[k][key] = initArray[j].migrationData[x].migrationCount
              } else {
                tempConvert[k][key] = 0
              }
            }
          }
          tpConverted.push(tempConvert)
        }
        // tpConverted contains timePeriod arrays
        this.quantMigItems = JSON.parse(JSON.stringify(tpConverted[0]))
        console.log(this.quantMigItems)
        this.tpArray = tempTP
      }
    }
  }

</script>

<style>

</style>
