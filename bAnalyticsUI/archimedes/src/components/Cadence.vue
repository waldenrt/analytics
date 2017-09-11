<template>
  <div class="cadence">
    <annotated-bar-chart :chart-data="cadenceBars"></annotated-bar-chart>
  </div>
</template>

<script>
  import AnnotatedBarChart from './balorCharts/AnnotatedBarChart'

  export default {
    name: 'cadence',
    components: {
      AnnotatedBarChart
    },
    data () {
      return {
        msg: 'Cadence Charts and approval will go here!',
        cadenceBars: null,
        cadArray: [],
        countArray: [],
        perArray: [],
        incomingJson: {
          'responseCode': 0,
          'isError': 'false',
          'httpStatusCode': 200,
          'data': {
            'normalizedCadence': '2 weeks',
            'singleVist': 24,
            'percentile': 0.8,
            'numRecords': 1000,
            'maxDateCadence': '04/23/2017',
            'numTimePeriods': 99,
            'minDateCadence': '01/01/2017',
            'freqTable': [{
              'cadence': 1,
              'frequency': 2,
              'cumFreq': 3
            }, {
              'cadence': 2,
              'frequency': 5,
              'cumFreq': 6
            }, {
              'cadence': 3,
              'frequency': 8,
              'cumFreq': 9
            }],
            'rawCadence': 23.3
          },
          'errors': '',
          'moreInfo': '',
          'userMessage': 'Success',
          'developerMessage': ''
        }
      }
    },
    computed: {
      jsonMsg: function () {
        return this.incomingJson.data
      }
    },
    mounted () {
      this.parseJson()
      this.createBar()
    },
    methods: {
      parseJson () {
        var tempCad = []
        var tempCount = []
        var tempPer = []

        for (var i = 0; i < this.jsonMsg.freqTable.length; i++) {
          tempCad.push(this.jsonMsg.freqTable[i].cadence)
          tempCount.push(this.jsonMsg.freqTable[i].frequency)
          tempPer.push(this.jsonMsg.freqTable[i].cumFreq / this.jsonMsg.numRecords)
        }
        this.cadArray = tempCad
        this.countArray = tempCount
        this.perArray = tempPer
      },

      createBar () {
        this.cadenceBars = {
          labels: this.cadArray,
          datasets: [
            {
              data: this.countArray,
              label: 'Cadence',
              backgroundColor: '#0087AA'
            }
          ]
        }
      }
      // todo more methods fit in here.....
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
