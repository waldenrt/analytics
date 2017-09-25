<template>
  <div>
    <div>
      <v-select v-bind:items="quantiles"
                v-model="quantileSelect"
                label="Select Quantiles"
                multiple
                single-line
                bottom
                v-on:input="selectQuantile()">
      </v-select>
      <v-select
          v-bind:items="TPArray"
          v-model="TPSelect"
          label="Select Time Period"
          single-line
          bottom
          v-on:input="selectTP()">
      </v-select>
      <v-select
          v-bind:items="metrics"
          v-model="metricSelect"
          label="Select Metric"
          single-line
          bottom
          v-on:input="selectMetric()">
      </v-select>
    </div>
    <div>
      <pareto-chart :chart-data="paretoData"></pareto-chart>
    </div>

    <v-data-table
        v-bind:headers="custHeaders"
        :items="custItems"
        hide-actions>
      <template slot="items" scope="props">
        <td>{{ props.item.quantile }}</td>
        <td class="text-xs-right">{{ props.item.avgCustSales }}</td>
        <td class="text-xs-right">{{ props.item.avgVisitSpend }}</td>
        <td class="text-xs-right">{{ props.item.avgItemSales }}</td>
        <td class="text-xs-right">{{ props.item.avgCustUnits }}</td>
        <td class="text-xs-right">{{ props.item.avgVisitUnits }}</td>
        <td class="text-xs-right">{{ props.item.avgCustVisits }}</td>
      </template>
    </v-data-table>
  </div>
</template>

<script>
  import ParetoChart from './balorCharts/ParetoChart'

  export default {
    name: 'quantProfile',
    data () {
      return {
        TPArray: [],
        TPSelect: 1,
        quantiles: [],
        quantileSelect: ['All'],
        metrics: [
          'Spend',
          'Units',
          'Visits'
        ],
        metricSelect: 'Spend',
        spendArrayFull: [],
        unitArrayFull: [],
        visitArrayFull: [],
        spendArrayCurr: [],
        unitArrayCurr: [],
        visitArrayCurr: [],
        quantileArray: [],
        quantArrayCurr: [],
        spendPerArray: [],
        spendPerArrayCurr: [],
        unitPerArray: [],
        unitPerArrayCurr: [],
        visitPerArray: [],
        visitPerArrayCurr: [],
        paretoData: {},
        custHeaders: [
          {
            text: 'Quantiles',
            align: 'left',
            value: 'quantile'
          },
          {text: 'avgSpendMember($)', value: 'avgCustSales'},
          {text: 'avgSpendVisit($)', value: 'avgVisitSpend'},
          {text: 'avgSpendItem($)', value: 'avgItemSales'},
          {text: 'avgUnitsMember', value: 'avgCustUnits'},
          {text: 'avgUnitsVisit', value: 'avgVisitUnits'},
          {text: 'avgVisitMember', value: 'avgCustVisits'}
        ],
        custItems: [],
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
            'quantileResult': [
              {
                'timePeriod': 1,
                'quantile': 1,
                'totalCount': 1909,
                'totalVisits': 3940,
                'totalSpend': 421859.79000000184,
                'totalUnits': 16231,
                'totalDisc': 48517.810000000034,
                'avgVisits': 2.063907805133578,
                'avgSpend': 220.98469879518169,
                'avgUnits': 8.502357255107386,
                'avgDisc': 25.415301204819293,
                'avgVisitSpend': 107.0710126903558,
                'avgVisitUnits': 4.119543147208121,
                'avgVisitDisc': 12.314164974619297,
                'avgItemSales': 25.99099192902482,
                'avgItemDisc': 2.9892064567802374,
                'avgStoreCust': null,
                'avgCustSales': null,
                'avgCustVisits': null,
                'avgCustUnits': null
              }, {
                'timePeriod': 1,
                'quantile': 2,
                'totalCount': 1900,
                'totalVisits': 2787,
                'totalSpend': 219851.63000000047,
                'totalUnits': 8936,
                'totalDisc': 26920.210000000014,
                'avgVisits': 1.466842105263158,
                'avgSpend': 115.71138421052656,
                'avgUnits': 4.703157894736842,
                'avgDisc': 14.168531578947375,
                'avgVisitSpend': 78.88468963042715,
                'avgVisitUnits': 3.2063150340868316,
                'avgVisitDisc': 9.6592070326516,
                'avgItemSales': 24.602912936436937,
                'avgItemDisc': 3.0125570725156683,
                'avgStoreCust': null,
                'avgCustSales': null,
                'avgCustVisits': null,
                'avgCustUnits': null
              }, {
                'timePeriod': 1,
                'quantile': 3,
                'totalCount': 1911,
                'totalVisits': 2423,
                'totalSpend': 164002.8400000009,
                'totalUnits': 6639,
                'totalDisc': 19826.510000000024,
                'avgVisits': 1.2679225536368393,
                'avgSpend': 85.82042909471528,
                'avgUnits': 3.4740973312401886,
                'avgDisc': 10.374939822082691,
                'avgVisitSpend': 67.68586050350842,
                'avgVisitUnits': 2.739991745769707,
                'avgVisitDisc': 8.182628972348338,
                'avgItemSales': 24.702943214339644,
                'avgItemDisc': 2.986369935231213,
                'avgStoreCust': null,
                'avgCustSales': null,
                'avgCustVisits': null,
                'avgCustUnits': null
              }, {
                'timePeriod': 1,
                'quantile': 4,
                'totalCount': 1914,
                'totalVisits': 2237,
                'totalSpend': 130350.46999999831,
                'totalUnits': 5456,
                'totalDisc': 16417.630000000016,
                'avgVisits': 1.1687565308254964,
                'avgSpend': 68.10369383489984,
                'avgUnits': 2.8505747126436782,
                'avgDisc': 8.577654127481722,
                'avgVisitSpend': 58.270214573088204,
                'avgVisitUnits': 2.4389807778274473,
                'avgVisitDisc': 7.339128296826114,
                'avgItemSales': 23.89121517595277,
                'avgItemDisc': 3.0090964076246363,
                'avgStoreCust': null,
                'avgCustSales': null,
                'avgCustVisits': null,
                'avgCustUnits': null
              }, {
                'timePeriod': 1,
                'quantile': 5,
                'totalCount': 1887,
                'totalVisits': 2161,
                'totalSpend': 99929.5699999984,
                'totalUnits': 4947,
                'totalDisc': 14800.54,
                'avgVisits': 1.1452040275569688,
                'avgSpend': 52.956846846846005,
                'avgUnits': 2.6216216216216215,
                'avgDisc': 7.843423423423424,
                'avgVisitSpend': 46.24228135122555,
                'avgVisitUnits': 2.2892179546506246,
                'avgVisitDisc': 6.848931050439612,
                'avgItemSales': 20.200034364260848,
                'avgItemDisc': 2.9918213058419245,
                'avgStoreCust': null,
                'avgCustSales': null,
                'avgCustVisits': null,
                'avgCustUnits': null
              }, {
                'timePeriod': 1,
                'quantile': 6,
                'totalCount': 1928,
                'totalVisits': 2151,
                'totalSpend': 78631.1100000011,
                'totalUnits': 4177,
                'totalDisc': 12523.500000000005,
                'avgVisits': 1.1156639004149378,
                'avgSpend': 40.78377074688854,
                'avgUnits': 2.16649377593361,
                'avgDisc': 6.495591286307056,
                'avgVisitSpend': 36.555606694561185,
                'avgVisitUnits': 1.9418874941887494,
                'avgVisitDisc': 5.822175732217576,
                'avgItemSales': 18.82478094326098,
                'avgItemDisc': 2.998204452956669,
                'avgStoreCust': null,
                'avgCustSales': null,
                'avgCustVisits': null,
                'avgCustUnits': null
              }, {
                'timePeriod': 1,
                'quantile': 7,
                'totalCount': 1880,
                'totalVisits': 2024,
                'totalSpend': 57360.62999999971,
                'totalUnits': 4008,
                'totalDisc': 11907.460000000015,
                'avgVisits': 1.0765957446808512,
                'avgSpend': 30.510973404255168,
                'avgUnits': 2.1319148936170214,
                'avgDisc': 6.333755319148945,
                'avgVisitSpend': 28.340232213438593,
                'avgVisitUnits': 1.9802371541501975,
                'avgVisitDisc': 5.883132411067201,
                'avgItemSales': 14.311534431137654,
                'avgItemDisc': 2.9709231536926186,
                'avgStoreCust': null,
                'avgCustSales': null,
                'avgCustVisits': null,
                'avgCustUnits': null
              }, {
                'timePeriod': 1,
                'quantile': 8,
                'totalCount': 2042,
                'totalVisits': 2086,
                'totalSpend': 44092.35000000092,
                'totalUnits': 2946,
                'totalDisc': 8901.390000000001,
                'avgVisits': 1.0215475024485798,
                'avgSpend': 21.592727717924056,
                'avgUnits': 1.4427032321253672,
                'avgDisc': 4.359152791381,
                'avgVisitSpend': 21.137272291467365,
                'avgVisitUnits': 1.4122722914669223,
                'avgVisitDisc': 4.267205177372963,
                'avgItemSales': 14.966853360489111,
                'avgItemDisc': 3.0215173116089615,
                'avgStoreCust': null,
                'avgCustSales': null,
                'avgCustVisits': null,
                'avgCustUnits': null
              }, {
                'timePeriod': 1,
                'quantile': 9,
                'totalCount': 1953,
                'totalVisits': 2002,
                'totalSpend': 30643.699999999815,
                'totalUnits': 2563,
                'totalDisc': 7726.45,
                'avgVisits': 1.025089605734767,
                'avgSpend': 15.690578597030115,
                'avgUnits': 1.3123399897593446,
                'avgDisc': 3.956195596518177,
                'avgVisitSpend': 15.306543456543364,
                'avgVisitUnits': 1.2802197802197801,
                'avgVisitDisc': 3.859365634365634,
                'avgItemSales': 11.956184159188378,
                'avgItemDisc': 3.0146117830667185,
                'avgStoreCust': null,
                'avgCustSales': null,
                'avgCustVisits': null,
                'avgCustUnits': null
              }, {
                'timePeriod': 1,
                'quantile': 10,
                'totalCount': 1718,
                'totalVisits': 1732,
                'totalSpend': 14235.059999999712,
                'totalUnits': 1876,
                'totalDisc': 5545.549999999997,
                'avgVisits': 1.0081490104772992,
                'avgSpend': 8.28583236321287,
                'avgUnits': 1.0919674039580909,
                'avgDisc': 3.2279103608847484,
                'avgVisitSpend': 8.21885681293286,
                'avgVisitUnits': 1.0831408775981524,
                'avgVisitDisc': 3.201818706697458,
                'avgItemSales': 7.587985074626713,
                'avgItemDisc': 2.9560501066098066,
                'avgStoreCust': null,
                'avgCustSales': null,
                'avgCustVisits': null,
                'avgCustUnits': null
              }, {
                'timePeriod': 2,
                'quantile': 1,
                'totalCount': 3312,
                'totalVisits': 8764,
                'totalSpend': 922236.9600000052,
                'totalUnits': 35845,
                'totalDisc': 107500.43999999983,
                'avgVisits': 2.646135265700483,
                'avgSpend': 278.4531884057987,
                'avgUnits': 10.822765700483092,
                'avgDisc': 32.457862318840526,
                'avgVisitSpend': 105.23014148790566,
                'avgVisitUnits': 4.090027384755819,
                'avgVisitDisc': 12.266138749429464,
                'avgItemSales': 25.728468684614455,
                'avgItemDisc': 2.9990358487934112,
                'avgStoreCust': null,
                'avgCustSales': null,
                'avgCustVisits': null,
                'avgCustUnits': null
              }, {
                'timePeriod': 2,
                'quantile': 2,
                'totalCount': 3316,
                'totalVisits': 5411,
                'totalSpend': 466310.38999999914,
                'totalUnits': 18611,
                'totalDisc': 55701.920000000006,
                'avgVisits': 1.6317852834740652,
                'avgSpend': 140.62436369119396,
                'avgUnits': 5.61248492159228,
                'avgDisc': 16.79792521109771,
                'avgVisitSpend': 86.17822768434654,
                'avgVisitUnits': 3.4394751432267605,
                'avgVisitDisc': 10.294200702273148,
                'avgItemSales': 25.055633227661016,
                'avgItemDisc': 2.9929568534737525,
                'avgStoreCust': null,
                'avgCustSales': null,
                'avgCustVisits': null,
                'avgCustUnits': null
              }, {
                'timePeriod': 2,
                'quantile': 3,
                'totalCount': 3401,
                'totalVisits': 4736,
                'totalSpend': 343432.6199999994,
                'totalUnits': 14001,
                'totalDisc': 42170.11999999991,
                'avgVisits': 1.392531608350485,
                'avgSpend': 100.9798941487796,
                'avgUnits': 4.116730373419583,
                'avgDisc': 12.39932960893852,
                'avgVisitSpend': 72.51533361486474,
                'avgVisitUnits': 2.95629222972973,
                'avgVisitDisc': 8.904163851351331,
                'avgItemSales': 24.52914934647521,
                'avgItemDisc': 3.0119362902649747,
                'avgStoreCust': null,
                'avgCustSales': null,
                'avgCustVisits': null,
                'avgCustUnits': null
              }, {
                'timePeriod': 2,
                'quantile': 4,
                'totalCount': 3221,
                'totalVisits': 3868,
                'totalSpend': 253818.53000000856,
                'totalUnits': 9925,
                'totalDisc': 29557.050000000017,
                'avgVisits': 1.2008692952499225,
                'avgSpend': 78.8011580254606,
                'avgUnits': 3.0813411983855947,
                'avgDisc': 9.17635827382801,
                'avgVisitSpend': 65.62009565667232,
                'avgVisitUnits': 2.565925542916236,
                'avgVisitDisc': 7.641429679420894,
                'avgItemSales': 25.573655415617992,
                'avgItemDisc': 2.9780403022670043,
                'avgStoreCust': null,
                'avgCustSales': null,
                'avgCustVisits': null,
                'avgCustUnits': null
              }, {
                'timePeriod': 2,
                'quantile': 5,
                'totalCount': 3302,
                'totalVisits': 3879,
                'totalSpend': 209891.36000000357,
                'totalUnits': 9851,
                'totalDisc': 29333.120000000014,
                'avgVisits': 1.1747425802543914,
                'avgSpend': 63.564918231376005,
                'avgUnits': 2.983343428225318,
                'avgDisc': 8.883440339188375,
                'avgVisitSpend': 54.10965712812673,
                'avgVisitUnits': 2.539572054653261,
                'avgVisitDisc': 7.562031451405005,
                'avgItemSales': 21.30660440564446,
                'avgItemDisc': 2.977679423408792,
                'avgStoreCust': null,
                'avgCustSales': null,
                'avgCustVisits': null,
                'avgCustUnits': null
              }, {
                'timePeriod': 2,
                'quantile': 6,
                'totalCount': 3310,
                'totalVisits': 3761,
                'totalSpend': 164372.43000000255,
                'totalUnits': 7749,
                'totalDisc': 23136.76000000001,
                'avgVisits': 1.1362537764350453,
                'avgSpend': 49.659344410876905,
                'avgUnits': 2.3410876132930514,
                'avgDisc': 6.989957703927495,
                'avgVisitSpend': 43.704448285031255,
                'avgVisitUnits': 2.0603562882212176,
                'avgVisitDisc': 6.151757511300189,
                'avgItemSales': 21.212082849400254,
                'avgItemDisc': 2.9857736482126738,
                'avgStoreCust': null,
                'avgCustSales': null,
                'avgCustVisits': null,
                'avgCustUnits': null
              }, {
                'timePeriod': 2,
                'quantile': 7,
                'totalCount': 3312,
                'totalVisits': 3613,
                'totalSpend': 124146.82000000076,
                'totalUnits': 6931,
                'totalDisc': 20863.26000000005,
                'avgVisits': 1.0908816425120773,
                'avgSpend': 37.483943236715206,
                'avgUnits': 2.0926932367149758,
                'avgDisc': 6.299293478260885,
                'avgVisitSpend': 34.36114586216462,
                'avgVisitUnits': 1.9183504013285357,
                'avgVisitDisc': 5.774497647384459,
                'avgItemSales': 17.911819362285495,
                'avgItemDisc': 3.010137065358541,
                'avgStoreCust': null,
                'avgCustSales': null,
                'avgCustVisits': null,
                'avgCustUnits': null
              }, {
                'timePeriod': 2,
                'quantile': 8,
                'totalCount': 3752,
                'totalVisits': 3853,
                'totalSpend': 102972.38000000156,
                'totalUnits': 5536,
                'totalDisc': 16948.109999999935,
                'avgVisits': 1.0269189765458422,
                'avgSpend': 27.444664179104894,
                'avgUnits': 1.4754797441364607,
                'avgDisc': 4.517086886993586,
                'avgVisitSpend': 26.72524785881172,
                'avgVisitUnits': 1.4368024915650144,
                'avgVisitDisc': 4.398678951466373,
                'avgItemSales': 18.60050216763034,
                'avgItemDisc': 3.061436054913283,
                'avgStoreCust': null,
                'avgCustSales': null,
                'avgCustVisits': null,
                'avgCustUnits': null
              }, {
                'timePeriod': 2,
                'quantile': 9,
                'totalCount': 2929,
                'totalVisits': 2993,
                'totalSpend': 58052.900000001115,
                'totalUnits': 4226,
                'totalDisc': 12705.23999999998,
                'avgVisits': 1.0218504609081598,
                'avgSpend': 19.820040969614585,
                'avgUnits': 1.4428132468419255,
                'avgDisc': 4.337739842949805,
                'avgVisitSpend': 19.396224523889448,
                'avgVisitUnits': 1.4119612429001003,
                'avgVisitDisc': 4.244984964918135,
                'avgItemSales': 13.737079981069833,
                'avgItemDisc': 3.00644581164221,
                'avgStoreCust': null,
                'avgCustSales': null,
                'avgCustVisits': null,
                'avgCustUnits': null
              }, {
                'timePeriod': 2,
                'quantile': 10,
                'totalCount': 3245,
                'totalVisits': 3279,
                'totalSpend': 34720.790000000474,
                'totalUnits': 3840,
                'totalDisc': 11395.809999999994,
                'avgVisits': 1.010477657935285,
                'avgSpend': 10.699781201849145,
                'avgUnits': 1.1833590138674885,
                'avgDisc': 3.5118058551617857,
                'avgVisitSpend': 10.588835010674131,
                'avgVisitUnits': 1.171088746569076,
                'avgVisitDisc': 3.4753918877706598,
                'avgItemSales': 9.041872395833456,
                'avgItemDisc': 2.967658854166665,
                'avgStoreCust': null,
                'avgCustSales': null,
                'avgCustVisits': null,
                'avgCustUnits': null
              }
            ]
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
    components: {
      ParetoChart
    },
    mounted () {
      this.createPareto()
      this.createTable()
    },
    methods: {
      createTable () {
        var tempObj = []
        for (var i = 0; i < this.jsonMsg.quantileResult.length; i++) {
          if (this.jsonMsg.quantileResult[i].timePeriod === this.TPSelect) {
            tempObj.push({
              'quantile': this.jsonMsg.quantileResult[i].quantile,
              'avgCustSales': this.jsonMsg.quantileResult[i].avgSpend,
              'avgVisitSpend': this.jsonMsg.quantileResult[i].avgVisitSpend,
              'avgItemSales': this.jsonMsg.quantileResult[i].avgItemSales,
              'avgCustUnits': this.jsonMsg.quantileResult[i].avgCustUnits,
              'avgVisitUnits': this.jsonMsg.quantileResult[i].avgVisitUnits,
              'avgCustVisits': this.jsonMsg.quantileResult[i].avgVisits
            })
          }
        }
        this.custItems = tempObj
        console.log(tempObj)
      },
      selectQuantile () {
        if (this.quantileSelect.indexOf('All') !== -1) {
          console.log('contains all')
          this.spendArrayCurr = this.spendArrayFull
          this.unitArrayCurr = this.unitArrayFull
          this.visitArrayCurr = this.visitArrayFull
          this.quantArrayCurr = this.quantileArray
          this.selectMetric()
        } else {
          var sorted = this.quantileSelect.slice().sort((a, b) => (a - b))
          sorted.slice().sort()
          console.log(sorted)
          console.log('no all here')
          var unitArray = []
          var spendArray = []
          var visitArray = []
          var quantArray = []
          var unitPer = []
          var spendPer = []
          var visitPer = []

          for (var i = 0; i < sorted.length; i++) {
            unitArray.push(this.unitArrayFull[sorted[i] - 1])
            spendArray.push(this.spendArrayFull[sorted[i] - 1])
            visitArray.push(this.visitArrayFull[sorted[i] - 1])
            quantArray.push(this.quantileArray[sorted[i] - 1])
            unitPer.push(this.unitPerArray[sorted[i] - 1])
            spendPer.push(this.spendPerArray[sorted[i] - 1])
            visitPer.push(this.visitPerArray[sorted[i] - 1])
          }

          this.spendArrayCurr = spendArray
          this.unitArrayCurr = unitArray
          this.visitArrayCurr = visitArray
          this.quantArrayCurr = quantArray
          this.spendPerArrayCurr = spendPer
          this.visitPerArrayCurr = visitPer
          this.unitPerArrayCurr = unitPer

          this.selectMetric()
        }
      },
      selectMetric () {
        if (this.metricSelect === 'Spend') {
          this.paretoData = {
            labels: this.quantArrayCurr,
            datasets: [
              {
                data: this.spendArrayCurr,
                label: 'Total Spend',
                backgroundColor: '#0087AA'
              }, {
                data: this.spendPerArrayCurr,
                label: 'Cumlative Percent Spend',
                type: 'line',
                yAxisID: 'right-y-axis',
                backgroundColor: '#F7970E',
                borderColor: '#F7970E',
                fill: false
              }
            ]
          }
        } else if (this.metricSelect === 'Units') {
          this.paretoData = {
            labels: this.quantArrayCurr,
            datasets: [
              {
                data: this.unitArrayCurr,
                label: 'Total Units',
                backgroundColor: '#0087AA'
              }, {
                data: this.unitPerArrayCurr,
                label: 'Cumlative Percent Units',
                type: 'line',
                yAxisID: 'right-y-axis',
                backgroundColor: '#F7970E',
                borderColor: '#F7970E',
                fill: false
              }
            ]
          }
        } else if (this.metricSelect === 'Visits') {
          this.paretoData = {
            labels: this.quantArrayCurr,
            datasets: [
              {
                data: this.visitArrayCurr,
                label: 'Total Visits',
                backgroundColor: '#0087AA'
              }, {
                data: this.visitPerArrayCurr,
                label: 'Cumlative Percent Visits',
                type: 'line',
                yAxisID: 'right-y-axis',
                backgroundColor: '#F7970E',
                borderColor: '#F7970E',
                fill: false
              }
            ]
          }
        }
      },
      createPareto () {
        var tempVisit = []
        var tempUnit = []
        var tempSpend = []
        var tempObj = []
        var tempQuant = []
        var tempTP = []

        for (var i = 0; i < this.jsonMsg.quantileResult.length; i++) {
          if (this.jsonMsg.quantileResult[i].timePeriod === this.TPSelect) {
            tempObj.push({
              'quantile': this.jsonMsg.quantileResult[i].quantile,
              'totalSpend': this.jsonMsg.quantileResult[i].totalSpend,
              'totalVisits': this.jsonMsg.quantileResult[i].totalVisits,
              'totalUnits': this.jsonMsg.quantileResult[i].totalUnits
            })
          }

          if (tempTP.indexOf(this.jsonMsg.quantileResult[i].timePeriod) === -1) {
            tempTP.push(this.jsonMsg.quantileResult[i].timePeriod)
          }
        }

        var sortArray = tempObj.sort(function (a, b) {
          var x = a['quantile']
          var y = b['quantile']
          return ((x < y) ? -1 : ((x > y) ? 1 : 0))
        })

        var ttlSpend = 0
        var ttlUnit = 0
        var ttlVisit = 0

        for (var j = 0; j < sortArray.length; j++) {
          tempVisit.push(sortArray[j].totalVisits)
          ttlVisit += sortArray[j].totalVisits
          tempUnit.push(sortArray[j].totalUnits)
          ttlUnit += sortArray[j].totalUnits
          tempSpend.push(sortArray[j].totalSpend)
          ttlSpend += sortArray[j].totalSpend
          tempQuant.push(sortArray[j].quantile)
        }

        this.TPArray = tempTP
        this.spendArrayFull = tempSpend
        this.unitArrayFull = tempUnit
        this.visitArrayFull = tempVisit
        this.quantileArray = tempQuant
        var allArray = ['All']
        this.quantiles = allArray.concat(tempQuant)

        var tempUnitPer = []
        var tempTtlUnit = []
        var tempSpendPer = []
        var tempTtlSpend = []
        var tempVisitPer = []
        var tempTtlVisit = []

        for (var k = 0; k < this.spendArrayFull.length; k++) {
          if (k === 0) {
            tempSpendPer.push(this.spendArrayFull[k] / ttlSpend)
            tempTtlSpend.push(this.spendArrayFull[k])
          } else {
            tempSpendPer.push((tempTtlSpend[k - 1] + this.spendArrayFull[k]) / ttlSpend)
            tempTtlSpend.push(this.spendArrayFull[k] + tempTtlSpend[k - 1])
          }
        }
        for (var l = 0; l < this.unitArrayFull.length; l++) {
          if (l === 0) {
            tempUnitPer.push(this.unitArrayFull[l] / ttlUnit)
            tempTtlUnit.push(this.unitArrayFull[l])
          } else {
            tempUnitPer.push((tempTtlUnit[l - 1] + this.unitArrayFull[l]) / ttlUnit)
            tempTtlUnit.push(this.unitArrayFull[l] + tempTtlUnit[l - 1])
          }
        }
        for (var x = 0; x < this.visitArrayFull.length; x++) {
          if (x === 0) {
            tempVisitPer.push(this.visitArrayFull[x] / ttlVisit)
            tempTtlVisit.push(this.visitArrayFull[x])
          } else {
            tempVisitPer.push((tempTtlVisit[x - 1] + this.visitArrayFull[x]) / ttlVisit)
            tempTtlVisit.push(this.visitArrayFull[x] + tempTtlVisit[x - 1])
          }
        }

        this.spendPerArray = tempSpendPer
        this.unitPerArray = tempUnitPer
        this.visitPerArray = tempVisitPer

        this.paretoData = {
          labels: this.quantileArray,
          datasets: [
            {
              data: this.spendArrayFull,
              label: 'Total Spend',
              backgroundColor: '#0087AA'
            }, {
              data: this.spendPerArray,
              label: 'Cumlative Percent Spend',
              type: 'line',
              yAxisID: 'right-y-axis',
              backgroundColor: '#F7970E',
              borderColor: '#F7970E',
              fill: false
            }
          ]
        }
      },
      selectTP () {
        var tempVisit = []
        var tempUnit = []
        var tempSpend = []
        var tempObj = []

        for (var i = 0; i < this.jsonMsg.quantileResult.length; i++) {
          if (this.jsonMsg.quantileResult[i].timePeriod === this.TPSelect) {
            tempObj.push({
              'quantile': this.jsonMsg.quantileResult[i].quantile,
              'totalSpend': this.jsonMsg.quantileResult[i].totalSpend,
              'totalVisits': this.jsonMsg.quantileResult[i].totalVisits,
              'totalUnits': this.jsonMsg.quantileResult[i].totalUnits
            })
          }
        }

        var sortArray = tempObj.sort(function (a, b) {
          var x = a['quantile']
          var y = b['quantile']
          return ((x < y) ? -1 : ((x > y) ? 1 : 0))
        })

        var ttlSpend = 0
        var ttlUnit = 0
        var ttlVisit = 0

        for (var j = 0; j < sortArray.length; j++) {
          tempVisit.push(sortArray[j].totalVisits)
          ttlVisit += sortArray[j].totalVisits
          tempUnit.push(sortArray[j].totalUnits)
          ttlUnit += sortArray[j].totalUnits
          tempSpend.push(sortArray[j].totalSpend)
          ttlSpend += sortArray[j].totalSpend
        }

        this.spendArrayFull = tempSpend
        this.unitArrayFull = tempUnit
        this.visitArrayFull = tempVisit

        var tempUnitPer = []
        var tempTtlUnit = []
        var tempSpendPer = []
        var tempTtlSpend = []
        var tempVisitPer = []
        var tempTtlVisit = []

        for (var k = 0; k < this.spendArrayFull.length; k++) {
          if (k === 0) {
            tempSpendPer.push(this.spendArrayFull[k] / ttlSpend)
            tempTtlSpend.push(this.spendArrayFull[k])
          } else {
            tempSpendPer.push((tempTtlSpend[k - 1] + this.spendArrayFull[k]) / ttlSpend)
            tempTtlSpend.push(this.spendArrayFull[k] + tempTtlSpend[k - 1])
          }
        }
        for (var l = 0; l < this.unitArrayFull.length; l++) {
          if (l === 0) {
            tempUnitPer.push(this.unitArrayFull[l] / ttlUnit)
            tempTtlUnit.push(this.unitArrayFull[l])
          } else {
            tempUnitPer.push((tempTtlUnit[l - 1] + this.unitArrayFull[l]) / ttlUnit)
            tempTtlUnit.push(this.unitArrayFull[l] + tempTtlUnit[l - 1])
          }
        }
        for (var x = 0; x < this.visitArrayFull.length; x++) {
          if (x === 0) {
            tempVisitPer.push(this.visitArrayFull[x] / ttlVisit)
            tempTtlVisit.push(this.visitArrayFull[x])
          } else {
            tempVisitPer.push((tempTtlVisit[x - 1] + this.visitArrayFull[x]) / ttlVisit)
            tempTtlVisit.push(this.visitArrayFull[x] + tempTtlVisit[x - 1])
          }
        }

        this.spendPerArray = tempSpendPer
        this.unitPerArray = tempUnitPer
        this.visitPerArray = tempVisitPer

        this.paretoData = {
          labels: this.quantileArray,
          datasets: [
            {
              data: this.spendArrayFull,
              label: 'Total Spend',
              backgroundColor: '#0087AA'
            }, {
              data: this.spendPerArray,
              label: 'Cumlative Percent Spend',
              type: 'line',
              yAxisID: 'right-y-axis',
              backgroundColor: '#F7970E',
              borderColor: '#F7970E',
              fill: false
            }
          ]
        }
      }
    }
  }
</script>

<style>

</style>
