<template>
<v-container fluid class="balorSegmentTrend">
  <v-layout row wrap class="mt-0 mb-3 pa-0">
    <v-flex xs12>
      <v-card flat>
        <v-tabs id="mobile-tabs-3" grow class="elevation-1">
          <v-tabs-bar slot="activators" class="grey lighten-2">
            <v-tabs-slider></v-tabs-slider>
            <v-tabs-item router to="/Balor/Trend" class="primary--text">
              BALOR Trends
              <v-icon class="pt-1">timeline</v-icon>
            </v-tabs-item>
            <v-tabs-item router to="/Balor/SegmentTrend">
              Segment Trends
              <v-icon class="pt-1">bar_chart</v-icon>
            </v-tabs-item>
          </v-tabs-bar>
        </v-tabs>
      </v-card>
    </v-flex>
  </v-layout>
  <!--ROW1-->
  <v-layout row wrap class="pt-0 mb-3">
    <v-flex xs12>
      <v-card-title primary-title class="primary">
        <h6 class="white--text text-xs-left mb-0">Segment Trends</h6>
      </v-card-title>
      <v-card class="pa-0 ma-0 grey lighten-2">
        <v-layout row wrap>
        <!--Dropdown1-->
        <v-flex xs12 sm6 md4>
          <v-card flat class="pl-2 pr-2 pt-0 pb-0 grey lighten-2">
            <v-layout row wrap>
              <v-flex xs5>
                <div class="primary--text text-xs-right pl-0 pr-0 pb-0 padT25">
                  Select BALOR<br />metrics:
                </div>
              </v-flex>
              <v-flex xs7>
                <v-card class="white pa-0">
                <v-select v-bind:items="dimensions"
                          v-model="dimensionSelect"
                          label="Select Dimension"
                          single-line
                          bottom
                          v-on:input="selectDimension()"
                          class="pa-1 m-0 v_font">
                </v-select>
                </v-card>
              </v-flex>
            </v-layout>
          </v-card>
        </v-flex>
        <!--//Dropdown1-->
        <!--Dropdown2-->
        <v-flex xs12 sm6 md5>
          <v-card flat class="pl-2 pr-2 grey lighten-2">
            <v-layout row wrap>
              <v-flex xs5>
                <div class="primary--text text-xs-right pl-0 pr-0 pb-0 padT25">
                  Select BALOR<br />segment:
                </div>
              </v-flex>
              <v-flex xs7>
                <v-card class="white">
                <v-select
                    v-if="dimensionSelect === 'Customer'"
                    v-bind:items="custDim"
                    v-model="avgSelect"
                    label="Select Average"
                    single-line
                    bottom
                    v-on:input="selectAvg()"
                    class="pa-1 m-0">
                </v-select>
                <v-select
                    v-else-if="dimensionSelect === 'Visits'"
                    v-bind:items="visitDim"
                    v-model="avgSelect"
                    label="Select Average"
                    single-line
                    bottom
                    v-on:input="selectAvg()"
                    class="pa-1 m-0">
                </v-select>
                <v-select
                    v-else-if="dimensionSelect === 'Spend'"
                    v-bind:items="spendDim"
                    v-model="avgSelect"
                    label="Select Average"
                    single-line
                    bottom
                    v-on:input="selectAvg()"
                    class="pa-1 m-0">
                </v-select>
                <v-select
                    v-else-if="dimensionSelect === 'Discount'"
                    v-bind:items="discDim"
                    v-model="avgSelect"
                    label="Select Average"
                    single-line
                    bottom
                    v-on:input="selectAvg()"
                    class="pa-1 m-0">
                </v-select>
              </v-card>
              </v-flex>
            </v-layout>
          </v-card>
        </v-flex>
        <!--//Dropdown2-->
        </v-layout>
      </v-card>
    </v-flex>
    <!--chart-->
    <v-flex xs12>
      <v-card class="white">
        <bar-chart :chart-data="totalBars" class="pa-2 chart_h"></bar-chart>
      </v-card>
    </v-flex>
    <!--//chart-->
  </v-layout>
  <!--//ROW1-->

  <!--ROW2-->
  <v-layout row wrap class="mt-4 mb-3">
    <v-flex xs12>
      <v-card-title primary-title class="primary">
        <h6 class="white--text text-xs-left mb-0">Segment Profiles</h6>
      </v-card-title>
      <v-card class="pa-0 ma-0 grey lighten-2">
        <v-layout row wrap>
          <!--Dropdown1-->
          <v-flex xs12 sm6 md4>
            <v-card flat class="pl-2 pr-2 pt-0 pb-0 grey lighten-2">
              <v-layout row wrap>
                <v-flex xs5>
                  <div class="primary--text text-xs-right pl-0 pr-0 pb-0 padT25">
                    Select BALOR<br />time period:
                  </div>
                </v-flex>
                <v-flex xs7>
                  <v-card class="white pa-0">
                    <v-select
                        v-bind:items="tpArray"
                        v-model="tpSelect"
                        label="Select Balor Time Period"
                        single-line
                        bottom
                        v-on:input="updateTPMetric"
                        class="pa-1 m-0">
                    </v-select>
                  </v-card>
                </v-flex>
              </v-layout>
            </v-card>
          </v-flex>
          <!--//Dropdown1-->
          <!--Dropdown2-->
          <v-flex xs12 sm6 md5>
            <v-card flat class="pl-2 pr-2 grey lighten-2">
              <v-layout row wrap>
                <v-flex xs5>
                  <div class="primary--text text-xs-right pl-0 pr-0 pb-0 padT25">
                    Select BALOR<br />metric:
                  </div>
                </v-flex>
                <v-flex xs7>
                  <v-card class="white">
                    <v-select
                        v-bind:items="avgList"
                        v-model="tpAvgSelect"
                        label="Select Balor Metric"
                        single-line
                        bottom
                        v-on:input="updateTPMetric"
                        class="pa-1 m-0">
                    </v-select>
                </v-card>
                </v-flex>
              </v-layout>
            </v-card>
          </v-flex>
          <!--//Dropdown2-->
        </v-layout>
      </v-card>
    </v-flex>
    <!--chart-->
    <v-flex xs12>
      <v-card class="white">
        <horizontal-chart :chart-data="avgBars" class="pa-2 chart_h"></horizontal-chart>
      </v-card>
    </v-flex>
    <!--//chart-->
  </v-layout>
  <!--//ROW2-->

</v-container>
</template>

<script>
  import HorizontalChart from './balorCharts/HorizontalChart'
  import BarChart from './balorCharts/BarChart'

  export default {
    name: 'balorSegmentTrend',
    components: {
      BarChart,
      HorizontalChart
    },
    data () {
      return {
        msg: 'Balor Segment Trend Charts will go here!',
        dimensions: [
          'Customer', 'Visits', 'Spend', 'Discount'
        ],
        custDim: [
          'Avg Spend per Customer',
          'Avg Items per Customer',
          'Avg Discount per Customer',
          'Avg Transactions per Customer'
        ],
        visitDim: [
          'Avg Spend per Basket',
          'Avg Items per Basket',
          'Avg Discount per Basket'
        ],
        spendDim: [
          'Avg Spend per Customer',
          'Avg Spend per Basket'
        ],
        discDim: [
          'Avg Discount per Customer',
          'Avg Discount per Basket'
        ],
        dimensionSelect: 'Customer',
        avgSelect: 'Avg Spend per Customer',
        tpSelect: 1,
        tpAvgSelect: 'Spend per Customer',
        avgList: [
          'Spend per Customer',
          'Items per Customer',
          'Discount per Customer',
          'Transactions per Customer',
          'Spend per Basket',
          'Items per Basket',
          'Discount per Basket'
        ],
        incomingJson: {
          'responseCode': 0,
          'isError': 'false',
          'httpStatusCode': 200,
          'data': {
            'normalizedCadence': '2 Months',
            'singleVisit': 6663128632962592816,
            'totalCust': 6663128632962592816,
            'maxDateBalor': '2016-12-31',
            'numRecords': 8188111,
            'timePeriods': [{
              'txnBalor': 2.6226171109164476,
              'returnCustInfo': {
                'returnCustSpendAvg': 428.68989644255026,
                'returnVisitDiscAvg': 70.32642399369023,
                'returnVisitItemAvg': 2.2030319896371244,
                'returnCustVisitAvg': 4.238363728684466,
                'returnCustDiscAvg': 298.0689646229417,
                'returnTxnAmt': 40524913.29050716,
                'returnItemQty': 882669,
                'returnVisitSpendAvg': 101.14514088096212,
                'returnCustItemAvg': 9.337250878009563,
                'returnCustCount': 94532,
                'returnDiscAmt': 28177055.36373592,
                'returnTxnCount': 400661
              },
              'spendBalor': 2.7890993863726354,
              'retention': 0.6220520109496737,
              'newCustInfo': {
                'newCustDiscAvg': 161.90669464436345,
                'newCustCount': 90102,
                'newTxnCount': 171380,
                'newVisitSpendAvg': 105.98972979611375,
                'newCustSpendAvg': 201.59951934982544,
                'newCustItemAvg': 4.205511531375552,
                'newVisitItemAvg': 2.2110222896487337,
                'newCustVisitAvg': 1.9020665468025126,
                'newVisitDiscAvg': 85.12146692056504,
                'newItemQty': 378925,
                'newTxnAmt': 18164519.892457973,
                'newDiscAmt': 14588117.000846436
              },
              'timePeriod': 1,
              'custBalor': 2.0961069712375515,
              'reactCustInfo': {
                'reactTxnCount': 80108,
                'reactCustSpendAvg': 280.04427211261026,
                'reactCustCount': 30290,
                'reactCustDiscAvg': 219.65427239988114,
                'reactCustItemAvg': 6.45694948827996,
                'reactDiscAmt': 6653327.9109924,
                'reactCustVisitAvg': 2.644701221525256,
                'reactVisitDiscAvg': 83.05447534568832,
                'reactVisitItemAvg': 2.441466520197733,
                'reactItemQty': 195581,
                'reactTxnAmt': 8482541.002290964,
                'reactVisitSpendAvg': 105.88881263158441
              },
              'lapsedCustInfo': {
                'lapsedTxnCount': 95892,
                'lapsedCustDiscAvg': 127.49930879846191,
                'lapsedCustSpendAvg': 166.34170068972946,
                'lapsedDiscAmt': 7323050.300148458,
                'lapsedCustVisitAvg': 1.6695452329549412,
                'lapsedCustItemAvg': 3.4672156835434222,
                'lapsedVisitItemAvg': 2.0767425854085846,
                'lapsedVisitSpendAvg': 99.6329403997758,
                'lapsedItemQty': 199143,
                'lapsedVisitDiscAvg': 76.36768760843927,
                'lapsedCustCount': 57436,
                'lapsedTxnAmt': 9554001.920815302
              }
            }, {
              'txnBalor': 1.0079267178362572,
              'returnCustInfo': {
                'returnCustSpendAvg': 325.40792185921475,
                'returnVisitDiscAvg': 65.71605405668227,
                'returnVisitItemAvg': 2.0510738147660006,
                'returnCustVisitAvg': 3.3518296264140344,
                'returnCustDiscAvg': 220.2690169182138,
                'returnTxnAmt': 25486599.25585742,
                'returnItemQty': 538452,
                'returnVisitSpendAvg': 97.08367015281546,
                'returnCustItemAvg': 6.874849978294732,
                'returnCustCount': 78322,
                'returnDiscAmt': 17251909.94306834,
                'returnTxnCount': 262522
              },
              'spendBalor': 1.1457196134055865,
              'retention': 0.5112602321239735,
              'newCustInfo': {
                'newCustDiscAvg': 147.15122489036227,
                'newCustCount': 49678,
                'newTxnCount': 81995,
                'newVisitSpendAvg': 111.21031295593694,
                'newCustSpendAvg': 183.55589216196404,
                'newCustItemAvg': 3.7242642618462902,
                'newVisitItemAvg': 2.25640587840722,
                'newCustVisitAvg': 1.6505294093965135,
                'newVisitDiscAvg': 89.15395512047586,
                'newItemQty': 185014,
                'newTxnAmt': 9118689.61082205,
                'newDiscAmt': 7310178.550103418
              },
              'timePeriod': 2,
              'custBalor': 0.983625387327706,
              'reactCustInfo': {
                'reactTxnCount': 50374,
                'reactCustSpendAvg': 223.17056245875503,
                'reactCustCount': 23968,
                'reactCustDiscAvg': 174.1836377748731,
                'reactCustItemAvg': 4.992740320427236,
                'reactDiscAmt': 4174833.4301881585,
                'reactCustVisitAvg': 2.101718958611482,
                'reactVisitDiscAvg': 82.87675050994875,
                'reactVisitItemAvg': 2.375550879421924,
                'reactItemQty': 119666,
                'reactTxnAmt': 5348952.041011441,
                'reactVisitSpendAvg': 106.18477867573432
              },
              'lapsedCustInfo': {
                'lapsedTxnCount': 131328,
                'lapsedCustDiscAvg': 128.9294318392735,
                'lapsedCustSpendAvg': 168.65528745566604,
                'lapsedDiscAmt': 9653204.420670085,
                'lapsedCustVisitAvg': 1.754033550593012,
                'lapsedCustItemAvg': 3.8666123517469817,
                'lapsedVisitItemAvg': 2.204411854288499,
                'lapsedVisitSpendAvg': 96.15282866091486,
                'lapsedItemQty': 289501,
                'lapsedVisitDiscAvg': 73.50454145856241,
                'lapsedCustCount': 74872,
                'lapsedTxnAmt': 12627558.682380628
              }
            }, {
              'txnBalor': 0.8125507275066812,
              'returnCustInfo': {
                'returnCustSpendAvg': 283.38265878605597,
                'returnVisitDiscAvg': 59.41780468937804,
                'returnVisitItemAvg': 2.0379643735199626,
                'returnCustVisitAvg': 3.2069557646262123,
                'returnCustDiscAvg': 190.5502712700353,
                'returnTxnAmt': 23434045.585654113,
                'returnItemQty': 540460,
                'returnVisitSpendAvg': 88.36500394294829,
                'returnCustItemAvg': 6.535661595762692,
                'returnCustCount': 82694,
                'returnDiscAmt': 15757364.132404298,
                'returnTxnCount': 265196
              },
              'spendBalor': 0.9011965606185668,
              'retention': 0.5008236633639381,
              'newCustInfo': {
                'newCustDiscAvg': 147.57161226525284,
                'newCustCount': 49347,
                'newTxnCount': 80463,
                'newVisitSpendAvg': 111.82979222163043,
                'newCustSpendAvg': 182.34463232879506,
                'newCustItemAvg': 3.9661782884471193,
                'newVisitItemAvg': 2.432409927544337,
                'newCustVisitAvg': 1.6305550489391452,
                'newVisitDiscAvg': 90.50391298427142,
                'newItemQty': 195719,
                'newTxnAmt': 8998160.57152905,
                'newDiscAmt': 7282216.350453432
              },
              'timePeriod': 3,
              'custBalor': 0.8553541530173983,
              'reactCustInfo': {
                'reactTxnCount': 42675,
                'reactCustSpendAvg': 202.71731578410777,
                'reactCustCount': 21153,
                'reactCustDiscAvg': 159.8249794439604,
                'reactCustItemAvg': 4.9787264217841445,
                'reactDiscAmt': 3380777.790178094,
                'reactCustVisitAvg': 2.017444334137002,
                'reactVisitDiscAvg': 79.22150650680946,
                'reactVisitItemAvg': 2.4678383128295254,
                'reactItemQty': 105315,
                'reactTxnAmt': 4288079.380781231,
                'reactVisitSpendAvg': 100.48223505052681
              },
              'lapsedCustInfo': {
                'lapsedTxnCount': 151545,
                'lapsedCustDiscAvg': 133.1897765196266,
                'lapsedCustSpendAvg': 178.8707868332709,
                'lapsedDiscAmt': 10977767.760300664,
                'lapsedCustVisitAvg': 1.8386474484967605,
                'lapsedCustItemAvg': 3.8057436121423893,
                'lapsedVisitItemAvg': 2.069860437493814,
                'lapsedVisitSpendAvg': 97.28389582217726,
                'lapsedItemQty': 313677,
                'lapsedVisitDiscAvg': 72.43899673562747,
                'lapsedCustCount': 82422,
                'lapsedTxnAmt': 14742887.992371853
              }
            }, {
              'txnBalor': 1.1128026842994236,
              'returnCustInfo': {
                'returnCustSpendAvg': 323.4938883854144,
                'returnVisitDiscAvg': 59.91980354046779,
                'returnVisitItemAvg': 1.9744034084269082,
                'returnCustVisitAvg': 3.5187278238597646,
                'returnCustDiscAvg': 210.84147991805483,
                'returnTxnAmt': 27533858.816036157,
                'returnItemQty': 591320,
                'returnVisitSpendAvg': 91.93489936671695,
                'returnCustItemAvg': 6.947388208755316,
                'returnCustCount': 85114,
                'returnDiscAmt': 17945561.72174532,
                'returnTxnCount': 299493
              },
              'spendBalor': 1.201095870863015,
              'retention': 0.5258202620637676,
              'newCustInfo': {
                'newCustDiscAvg': 145.99567730068875,
                'newCustCount': 55824,
                'newTxnCount': 94088,
                'newVisitSpendAvg': 108.62942969432183,
                'newCustSpendAvg': 183.08838100242463,
                'newCustItemAvg': 3.822836056176555,
                'newVisitItemAvg': 2.268153218263753,
                'newCustVisitAvg': 1.6854399541415879,
                'newVisitDiscAvg': 86.62170191346026,
                'newItemQty': 213406,
                'newTxnAmt': 10220725.781079352,
                'newDiscAmt': 8150062.689633649
              },
              'timePeriod': 4,
              'custBalor': 1.0423034330011074,
              'reactCustInfo': {
                'reactTxnCount': 52832,
                'reactCustSpendAvg': 222.9246828136507,
                'reactCustCount': 24178,
                'reactCustDiscAvg': 171.1499371517142,
                'reactCustItemAvg': 5.143560261394656,
                'reactDiscAmt': 4138063.1804541457,
                'reactCustVisitAvg': 2.185126974935892,
                'reactVisitDiscAvg': 78.32493906068568,
                'reactVisitItemAvg': 2.353895366444579,
                'reactItemQty': 124361,
                'reactTxnAmt': 5389872.981068446,
                'reactVisitSpendAvg': 102.0190979154385
              },
              'lapsedCustInfo': {
                'lapsedTxnCount': 132027,
                'lapsedCustDiscAvg': 125.5317657339737,
                'lapsedCustSpendAvg': 169.33050799007816,
                'lapsedDiscAmt': 9635190.678911151,
                'lapsedCustVisitAvg': 1.720109439124487,
                'lapsedCustItemAvg': 3.603621913881832,
                'lapsedVisitItemAvg': 2.094995720572307,
                'lapsedVisitSpendAvg': 98.44170617205911,
                'lapsedItemQty': 276596,
                'lapsedVisitDiscAvg': 72.97894126891583,
                'lapsedCustCount': 76755,
                'lapsedTxnAmt': 12996963.140778448
              }
            }, {
              'txnBalor': 1.0118784471861075,
              'returnCustInfo': {
                'returnCustSpendAvg': 318.50565685215486,
                'returnVisitDiscAvg': 60.81289922646594,
                'returnVisitItemAvg': 1.9762286022768307,
                'returnCustVisitAvg': 3.3800981407506043,
                'returnCustDiscAvg': 205.55356760903138,
                'returnTxnAmt': 25833356.815964576,
                'returnItemQty': 541789,
                'returnVisitSpendAvg': 94.22970682780993,
                'returnCustItemAvg': 6.679846624254081,
                'returnCustCount': 81108,
                'returnDiscAmt': 16672038.761633316,
                'returnTxnCount': 274153
              },
              'spendBalor': 1.1710052175647943,
              'retention': 0.4959187042573876,
              'newCustInfo': {
                'newCustDiscAvg': 142.18402503955267,
                'newCustCount': 45157,
                'newTxnCount': 72667,
                'newVisitSpendAvg': 112.05010857307633,
                'newCustSpendAvg': 180.31191708217415,
                'newCustItemAvg': 3.6633523041831833,
                'newVisitItemAvg': 2.276494144522273,
                'newCustVisitAvg': 1.6092078747481011,
                'newVisitDiscAvg': 88.3565307321216,
                'newItemQty': 165426,
                'newTxnAmt': 8142345.239679738,
                'newDiscAmt': 6420604.01871108
              },
              'timePeriod': 5,
              'custBalor': 0.9795980253023301,
              'reactCustInfo': {
                'reactTxnCount': 70616,
                'reactCustSpendAvg': 209.0004721711345,
                'reactCustCount': 35604,
                'reactCustDiscAvg': 161.53624593547264,
                'reactCustItemAvg': 4.596702617683406,
                'reactDiscAmt': 5751336.500286568,
                'reactCustVisitAvg': 1.9833726547578923,
                'reactVisitDiscAvg': 81.445231962821,
                'reactVisitItemAvg': 2.3176192364336696,
                'reactItemQty': 163661,
                'reactTxnAmt': 7441252.811181073,
                'reactVisitSpendAvg': 105.37630014700738
              },
              'lapsedCustInfo': {
                'lapsedTxnCount': 141601,
                'lapsedCustDiscAvg': 112.97544859708935,
                'lapsedCustSpendAvg': 161.41917409873085,
                'lapsedDiscAmt': 9314034.908689838,
                'lapsedCustVisitAvg': 1.7175624370777385,
                'lapsedCustItemAvg': 3.0247686280217847,
                'lapsedVisitItemAvg': 1.7610821957472051,
                'lapsedVisitSpendAvg': 93.98154653019165,
                'lapsedItemQty': 249371,
                'lapsedVisitDiscAvg': 65.77661816434798,
                'lapsedCustCount': 82443,
                'lapsedTxnAmt': 13307880.970221668
              }
            }, {
              'txnBalor': 0.3085381519225829,
              'returnCustInfo': {
                'returnCustSpendAvg': 249.2722274993688,
                'returnVisitDiscAvg': 52.47148287678544,
                'returnVisitItemAvg': 1.6618869201113182,
                'returnCustVisitAvg': 2.9048365116300627,
                'returnCustDiscAvg': 152.421079279858,
                'returnTxnAmt': 26888247.363674413,
                'returnItemQty': 520729,
                'returnVisitSpendAvg': 85.81282509406648,
                'returnCustItemAvg': 4.82750980373979,
                'returnCustCount': 107867,
                'returnDiscAmt': 16441204.558680441,
                'returnTxnCount': 313336
              },
              'spendBalor': 0.34170676334971295,
              'retention': 0.42725018616220667,
              'newCustInfo': {
                'newCustDiscAvg': 142.1933525030206,
                'newCustCount': 42207,
                'newTxnCount': 65511,
                'newVisitSpendAvg': 115.25215474638712,
                'newCustSpendAvg': 178.88700712181787,
                'newCustItemAvg': 3.3651763925415215,
                'newVisitItemAvg': 2.168093907893331,
                'newCustVisitAvg': 1.552135901627692,
                'newVisitDiscAvg': 91.61140616224742,
                'newItemQty': 142034,
                'newTxnAmt': 7550283.9095905665,
                'newDiscAmt': 6001554.829094991
              },
              'timePeriod': 6,
              'custBalor': 0.3850872400605805,
              'reactCustInfo': {
                'reactTxnCount': 24559,
                'reactCustSpendAvg': 184.52069825844166,
                'reactCustCount': 13477,
                'reactCustDiscAvg': 144.99067523506443,
                'reactCustItemAvg': 3.900942346219485,
                'reactDiscAmt': 1954039.3301429632,
                'reactCustVisitAvg': 1.822289827112859,
                'reactVisitDiscAvg': 79.56510159790558,
                'reactVisitItemAvg': 2.140681623844619,
                'reactItemQty': 52573,
                'reactTxnAmt': 2486785.450429018,
                'reactVisitSpendAvg': 101.25760211853162
              },
              'lapsedCustInfo': {
                'lapsedTxnCount': 291925,
                'lapsedCustDiscAvg': 159.8531751856132,
                'lapsedCustSpendAvg': 203.13373509645118,
                'lapsedDiscAmt': 23114928.985014852,
                'lapsedCustVisitAvg': 2.018831128415433,
                'lapsedCustItemAvg': 4.488018755057019,
                'lapsedVisitItemAvg': 2.223077845336987,
                'lapsedVisitSpendAvg': 100.6194783889079,
                'lapsedItemQty': 648972,
                'lapsedVisitDiscAvg': 79.18105330141253,
                'lapsedCustCount': 144601,
                'lapsedTxnAmt': 29373341.228681937
              }
            }, {
              'txnBalor': 3.660632885510506,
              'returnCustInfo': {
                'returnCustSpendAvg': 438.8856789667842,
                'returnVisitDiscAvg': 68.84106528708973,
                'returnVisitItemAvg': 2.242847683533759,
                'returnCustVisitAvg': 4.426093122193335,
                'returnCustDiscAvg': 304.6969655916502,
                'returnTxnAmt': 46423132.6927116,
                'returnItemQty': 1050034,
                'returnVisitSpendAvg': 99.15870878679027,
                'returnCustItemAvg': 9.927052706216024,
                'returnCustCount': 105775,
                'returnDiscAmt': 32229321.535456803,
                'returnTxnCount': 468170
              },
              'spendBalor': 3.853431158488989,
              'retention': 0.6805095377488982,
              'newCustInfo': {
                'newCustDiscAvg': 163.92228381842148,
                'newCustCount': 112580,
                'newTxnCount': 212414,
                'newVisitSpendAvg': 107.2928976191205,
                'newCustSpendAvg': 202.43838652396394,
                'newCustItemAvg': 4.30119914727305,
                'newVisitItemAvg': 2.2796472925513385,
                'newCustVisitAvg': 1.886782732279268,
                'newVisitDiscAvg': 86.87925801631667,
                'newItemQty': 484229,
                'newTxnAmt': 22790513.55486786,
                'newDiscAmt': 18454370.71227789
              },
              'timePeriod': 7,
              'custBalor': 2.953946838501812,
              'reactCustInfo': {
                'reactTxnCount': 91942,
                'reactCustSpendAvg': 284.5598734746062,
                'reactCustCount': 34113,
                'reactCustDiscAvg': 222.75216905062,
                'reactCustItemAvg': 6.606748160525313,
                'reactDiscAmt': 7598744.7428238,
                'reactCustVisitAvg': 2.6952188315305015,
                'reactVisitDiscAvg': 82.64715519375041,
                'reactVisitItemAvg': 2.4512845054490873,
                'reactItemQty': 225376,
                'reactTxnAmt': 9707190.963839242,
                'reactVisitSpendAvg': 105.57950625219424
              },
              'lapsedCustInfo': {
                'lapsedTxnCount': 83143,
                'lapsedCustDiscAvg': 131.1225553830886,
                'lapsedCustSpendAvg': 169.82372615889707,
                'lapsedDiscAmt': 6511546.100324181,
                'lapsedCustVisitAvg': 1.6742448650825614,
                'lapsedCustItemAvg': 3.610390656463955,
                'lapsedVisitItemAvg': 2.1564292844857653,
                'lapsedVisitSpendAvg': 101.4330279283984,
                'lapsedItemQty': 179292,
                'lapsedVisitDiscAvg': 78.31743021450009,
                'lapsedCustCount': 49660,
                'lapsedTxnAmt': 8433446.241050828
              }
            }, {
              'txnBalor': 1.3117197762101678,
              'returnCustInfo': {
                'returnCustSpendAvg': 324.3743033036833,
                'returnVisitDiscAvg': 64.32956305874914,
                'returnVisitItemAvg': 2.1403098208043567,
                'returnCustVisitAvg': 3.39907947069565,
                'returnCustDiscAvg': 218.66129715181546,
                'returnTxnAmt': 25935023.046342693,
                'returnItemQty': 581672,
                'returnVisitSpendAvg': 95.43004395754754,
                'returnCustItemAvg': 7.275083172824374,
                'returnCustCount': 79954,
                'returnDiscAmt': 17482845.352476254,
                'returnTxnCount': 271770
              },
              'spendBalor': 1.5007361512615383,
              'retention': 0.5909473902053245,
              'newCustInfo': {
                'newCustDiscAvg': 146.5557324851506,
                'newCustCount': 51018,
                'newTxnCount': 83566,
                'newVisitSpendAvg': 111.5167494064584,
                'newCustSpendAvg': 182.66119175389278,
                'newCustItemAvg': 3.824983339213611,
                'newVisitItemAvg': 2.3351961323983437,
                'newCustVisitAvg': 1.6379709122270572,
                'newVisitDiscAvg': 89.47395304223505,
                'newItemQty': 195143,
                'newTxnAmt': 9319008.680900102,
                'newDiscAmt': 7476980.359927414
              },
              'timePeriod': 8,
              'custBalor': 1.3638515466897947,
              'reactCustInfo': {
                'reactTxnCount': 51246,
                'reactCustSpendAvg': 225.4996533945348,
                'reactCustCount': 24463,
                'reactCustDiscAvg': 176.2474598602872,
                'reactCustItemAvg': 5.093447246862609,
                'reactDiscAmt': 4311541.610562205,
                'reactCustVisitAvg': 2.094837100927932,
                'reactVisitDiscAvg': 84.13420775401407,
                'reactVisitItemAvg': 2.431428794442493,
                'reactItemQty': 124601,
                'reactTxnAmt': 5516398.020990505,
                'reactVisitSpendAvg': 107.64543615092894
              },
              'lapsedCustInfo': {
                'lapsedTxnCount': 102775,
                'lapsedCustDiscAvg': 134.03508058940722,
                'lapsedCustSpendAvg': 178.61773077134103,
                'lapsedDiscAmt': 7418037.500140153,
                'lapsedCustVisitAvg': 1.8570215380167678,
                'lapsedCustItemAvg': 4.238959959525874,
                'lapsedVisitItemAvg': 2.2826660180004863,
                'lapsedVisitSpendAvg': 96.18506146250643,
                'lapsedItemQty': 234601,
                'lapsedVisitDiscAvg': 72.17745074327563,
                'lapsedCustCount': 55344,
                'lapsedTxnAmt': 9885419.691809097
              }
            }, {
              'txnBalor': 0.8427594196972408,
              'returnCustInfo': {
                'returnCustSpendAvg': 300.816264302144,
                'returnVisitDiscAvg': 58.599462216646785,
                'returnVisitItemAvg': 2.1948832555523907,
                'returnCustVisitAvg': 3.383697223560811,
                'returnCustDiscAvg': 198.28283760462438,
                'returnTxnAmt': 24345361.08623682,
                'returnItemQty': 601060,
                'returnVisitSpendAvg': 88.90164941695997,
                'returnCustItemAvg': 7.426820377852739,
                'returnCustCount': 80931,
                'returnDiscAmt': 16047228.330179855,
                'returnTxnCount': 273846
              },
              'spendBalor': 0.8837806019634626,
              'retention': 0.5714820359281437,
              'newCustInfo': {
                'newCustDiscAvg': 148.22419978960153,
                'newCustCount': 36390,
                'newTxnCount': 62186,
                'newVisitSpendAvg': 109.57255445285314,
                'newCustSpendAvg': 187.24591566928072,
                'newCustItemAvg': 4.31942841439956,
                'newVisitItemAvg': 2.5276428778181583,
                'newCustVisitAvg': 1.7088760648529815,
                'newVisitDiscAvg': 86.73782893808252,
                'newItemQty': 157184,
                'newTxnAmt': 6813878.871205125,
                'newDiscAmt': 5393878.630343599
              },
              'timePeriod': 9,
              'custBalor': 0.8958886050918679,
              'reactCustInfo': {
                'reactTxnCount': 36743,
                'reactCustSpendAvg': 205.9271719908846,
                'reactCustCount': 17977,
                'reactCustDiscAvg': 160.3830116382067,
                'reactCustItemAvg': 5.221727763253045,
                'reactDiscAmt': 2883205.400220042,
                'reactCustVisitAvg': 2.043889414251544,
                'reactVisitDiscAvg': 78.46951528781106,
                'reactVisitItemAvg': 2.554799553656479,
                'reactItemQty': 93871,
                'reactTxnAmt': 3701952.7708801324,
                'reactVisitSpendAvg': 100.75259970280413
              },
              'lapsedCustInfo': {
                'lapsedTxnCount': 117387,
                'lapsedCustDiscAvg': 145.22901046748558,
                'lapsedCustSpendAvg': 196.0730042304737,
                'lapsedDiscAmt': 8813222.500219362,
                'lapsedCustVisitAvg': 1.9343659883002389,
                'lapsedCustItemAvg': 4.209046716651561,
                'lapsedVisitItemAvg': 2.175930895243937,
                'lapsedVisitSpendAvg': 101.36292998139741,
                'lapsedItemQty': 255426,
                'lapsedVisitDiscAvg': 75.0783519488475,
                'lapsedCustCount': 60685,
                'lapsedTxnAmt': 11898690.261726297
              }
            }, {
              'txnBalor': 1.3031168062251544,
              'returnCustInfo': {
                'returnCustSpendAvg': 343.5894634685884,
                'returnVisitDiscAvg': 59.85154807597186,
                'returnVisitItemAvg': 2.030846198704767,
                'returnCustVisitAvg': 3.6978574220966745,
                'returnCustDiscAvg': 221.3224912767085,
                'returnTxnAmt': 27245613.684668656,
                'returnItemQty': 595503,
                'returnVisitSpendAvg': 92.91582239365361,
                'returnCustItemAvg': 7.509779689017239,
                'returnCustCount': 79297,
                'returnDiscAmt': 17550209.590769153,
                'returnTxnCount': 293229
              },
              'spendBalor': 1.4381213857638675,
              'retention': 0.6010444774580844,
              'newCustInfo': {
                'newCustDiscAvg': 161.77912042308807,
                'newCustCount': 41497,
                'newTxnCount': 75198,
                'newVisitSpendAvg': 113.94679169139339,
                'newCustSpendAvg': 206.48651328070463,
                'newCustItemAvg': 4.3877629708171675,
                'newVisitItemAvg': 2.4213276948855023,
                'newCustVisitAvg': 1.8121309974215003,
                'newVisitDiscAvg': 89.27562116275547,
                'newItemQty': 182079,
                'newTxnAmt': 8568570.8416094,
                'newDiscAmt': 6713348.1601968855
              },
              'timePeriod': 10,
              'custBalor': 1.1839840410373326,
              'reactCustInfo': {
                'reactTxnCount': 48056,
                'reactCustSpendAvg': 240.81385460754646,
                'reactCustCount': 20822,
                'reactCustDiscAvg': 182.7087810959133,
                'reactCustItemAvg': 5.509557199116319,
                'reactDiscAmt': 3804362.239979107,
                'reactCustVisitAvg': 2.307943521275574,
                'reactVisitDiscAvg': 79.16518728107015,
                'reactVisitItemAvg': 2.3872149159314136,
                'reactItemQty': 114720,
                'reactTxnAmt': 5014226.080638332,
                'reactVisitSpendAvg': 104.34131181617971
              },
              'lapsedCustInfo': {
                'lapsedTxnCount': 94584,
                'lapsedCustDiscAvg': 132.28788675227491,
                'lapsedCustSpendAvg': 179.43990709916866,
                'lapsedDiscAmt': 6962972.919205991,
                'lapsedCustVisitAvg': 1.796979196352237,
                'lapsedCustItemAvg': 3.8508596941198823,
                'lapsedVisitItemAvg': 2.1429628689841835,
                'lapsedVisitSpendAvg': 99.85641874064052,
                'lapsedItemQty': 202690,
                'lapsedVisitDiscAvg': 73.6168159435633,
                'lapsedCustCount': 52635,
                'lapsedTxnAmt': 9444819.510164743
              }
            }, {
              'txnBalor': 1.1672216911262618,
              'returnCustInfo': {
                'returnCustSpendAvg': 325.5534292664339,
                'returnVisitDiscAvg': 61.985061903053655,
                'returnVisitItemAvg': 2.0451029200963213,
                'returnCustVisitAvg': 3.3849569078088275,
                'returnCustDiscAvg': 209.81676346969925,
                'returnTxnAmt': 23684337.532562334,
                'returnItemQty': 503625,
                'returnVisitSpendAvg': 96.17653581214223,
                'returnCustItemAvg': 6.922585256560048,
                'returnCustCount': 72751,
                'returnDiscAmt': 15264379.35918409,
                'returnTxnCount': 246259
              },
              'spendBalor': 1.403701893726771,
              'retention': 0.5772880925552681,
              'newCustInfo': {
                'newCustDiscAvg': 152.3118441618323,
                'newCustCount': 28300,
                'newTxnCount': 47500,
                'newVisitSpendAvg': 114.97327642230694,
                'newCustSpendAvg': 192.9763473519286,
                'newCustItemAvg': 3.9485159010600706,
                'newVisitItemAvg': 2.352484210526316,
                'newCustVisitAvg': 1.6784452296819787,
                'newVisitDiscAvg': 90.74579346904956,
                'newItemQty': 111743,
                'newTxnAmt': 5461230.630059579,
                'newDiscAmt': 4310425.189779854
              },
              'timePeriod': 11,
              'custBalor': 1.1109421636537704,
              'reactCustInfo': {
                'reactTxnCount': 63609,
                'reactCustSpendAvg': 220.40865356011088,
                'reactCustCount': 30881,
                'reactCustDiscAvg': 169.1773559727469,
                'reactCustItemAvg': 4.891065703830835,
                'reactDiscAmt': 5224365.929794397,
                'reactCustVisitAvg': 2.059810239305722,
                'reactVisitDiscAvg': 82.13249586999319,
                'reactVisitItemAvg': 2.3745224732349195,
                'reactItemQty': 151041,
                'reactTxnAmt': 6806439.630589784,
                'reactVisitSpendAvg': 107.00434892216171
              },
              'lapsedCustInfo': {
                'lapsedTxnCount': 95191,
                'lapsedCustDiscAvg': 112.62731765530582,
                'lapsedCustSpendAvg': 164.0576018862214,
                'lapsedDiscAmt': 5999769.838815796,
                'lapsedCustVisitAvg': 1.7869197124138838,
                'lapsedCustItemAvg': 3.1446190234836964,
                'lapsedVisitItemAvg': 1.7597987204672711,
                'lapsedVisitSpendAvg': 91.81028154007102,
                'lapsedItemQty': 167517,
                'lapsedVisitDiscAvg': 63.028751024947695,
                'lapsedCustCount': 53271,
                'lapsedTxnAmt': 8739512.5100809
              }
            }, {
              'txnBalor': 0.27325409840030573,
              'returnCustInfo': {
                'returnCustSpendAvg': 248.49487805379312,
                'returnVisitDiscAvg': 51.93686287544588,
                'returnVisitItemAvg': 1.6854602284171185,
                'returnCustVisitAvg': 2.897785367646264,
                'returnCustDiscAvg': 150.50188128191755,
                'returnTxnAmt': 22990994.612414993,
                'returnItemQty': 451882,
                'returnVisitSpendAvg': 85.75337594986682,
                'returnCustItemAvg': 4.884101987656856,
                'returnCustCount': 92521,
                'returnDiscAmt': 13924584.558084294,
                'returnTxnCount': 268106
              },
              'spendBalor': 0.29820031214894716,
              'retention': 0.505134826737133,
              'newCustInfo': {
                'newCustDiscAvg': 155.16380900507178,
                'newCustCount': 23699,
                'newTxnCount': 38331,
                'newVisitSpendAvg': 120.1832553277355,
                'newCustSpendAvg': 194.38560107884,
                'newCustItemAvg': 3.679437950968395,
                'newVisitItemAvg': 2.2748949936083065,
                'newCustVisitAvg': 1.617410017300308,
                'newVisitDiscAvg': 95.93350315961484,
                'newItemQty': 87199,
                'newTxnAmt': 4606744.359967429,
                'newDiscAmt': 3677227.1096111964
              },
              'timePeriod': 12,
              'custBalor': 0.36960503089143865,
              'reactCustInfo': {
                'reactTxnCount': 18158,
                'reactCustSpendAvg': 194.491683341151,
                'reactCustCount': 9802,
                'reactCustDiscAvg': 152.09744438818308,
                'reactCustItemAvg': 4.149153234033871,
                'reactDiscAmt': 1490859.1498929705,
                'reactCustVisitAvg': 1.8524790859008367,
                'reactVisitDiscAvg': 82.10481054592854,
                'reactVisitItemAvg': 2.2397841171935235,
                'reactItemQty': 40670,
                'reactTxnAmt': 1906407.4801099622,
                'reactVisitSpendAvg': 104.98994823824002
              },
              'lapsedCustInfo': {
                'lapsedTxnCount': 206727,
                'lapsedCustDiscAvg': 185.6640276869658,
                'lapsedCustSpendAvg': 240.97013022034773,
                'lapsedDiscAmt': 16828587.46954658,
                'lapsedCustVisitAvg': 2.2807480141218006,
                'lapsedCustItemAvg': 5.351853486319506,
                'lapsedVisitItemAvg': 2.346534318207104,
                'lapsedVisitSpendAvg': 105.65399102764668,
                'lapsedItemQty': 485092,
                'lapsedVisitDiscAvg': 81.4048840719721,
                'lapsedCustCount': 90640,
                'lapsedTxnAmt': 21841532.603172317
              }
            }, {
              'txnBalor': 3.9285845183990618,
              'returnCustInfo': {
                'returnCustSpendAvg': 490.8319945252755,
                'returnVisitDiscAvg': 72.97238851515058,
                'returnVisitItemAvg': 2.382878735108469,
                'returnCustVisitAvg': 4.61478676324886,
                'returnCustDiscAvg': 336.7520126023701,
                'returnTxnAmt': 44586196.718686976,
                'returnItemQty': 998898,
                'returnVisitSpendAvg': 106.36070954223774,
                'returnCustItemAvg': 10.996477245205751,
                'returnCustCount': 90838,
                'returnDiscAmt': 30589879.320774093,
                'returnTxnCount': 419198
              },
              'spendBalor': 4.228091023920902,
              'retention': 0.7377765504694455,
              'newCustInfo': {
                'newCustDiscAvg': 188.41682161469387,
                'newCustCount': 64511,
                'newTxnCount': 135393,
                'newVisitSpendAvg': 112.94253788256916,
                'newCustSpendAvg': 237.03909459680807,
                'newCustItemAvg': 5.037264962564524,
                'newVisitItemAvg': 2.4001166973181776,
                'newCustVisitAvg': 2.0987583512889274,
                'newVisitDiscAvg': 89.77537671213072,
                'newItemQty': 324959,
                'newTxnAmt': 15291629.031534685,
                'newDiscAmt': 12154957.579185516
              },
              'timePeriod': 13,
              'custBalor': 2.859536641268661,
              'reactCustInfo': {
                'reactTxnCount': 78982,
                'reactCustSpendAvg': 313.57089645289614,
                'reactCustCount': 27812,
                'reactCustDiscAvg': 241.78566159925603,
                'reactCustItemAvg': 7.258917014238458,
                'reactDiscAmt': 6724542.820398509,
                'reactCustVisitAvg': 2.8398533007334965,
                'reactVisitDiscAvg': 85.14019422651374,
                'reactVisitItemAvg': 2.556088729077511,
                'reactItemQty': 201885,
                'reactTxnAmt': 8721033.772147948,
                'reactVisitSpendAvg': 110.41799108844987
              },
              'lapsedCustInfo': {
                'lapsedTxnCount': 54568,
                'lapsedCustDiscAvg': 133.2092851352063,
                'lapsedCustSpendAvg': 175.9064449036962,
                'lapsedDiscAmt': 4300794.97987527,
                'lapsedCustVisitAvg': 1.690144335005885,
                'lapsedCustItemAvg': 3.719166202069008,
                'lapsedVisitItemAvg': 2.2005021257880077,
                'lapsedVisitSpendAvg': 104.07776499341621,
                'lapsedItemQty': 120077,
                'lapsedVisitDiscAvg': 78.81533096091611,
                'lapsedCustCount': 32286,
                'lapsedTxnAmt': 5679315.480160736
              }
            }, {
              'txnBalor': 1.0762548262548262,
              'returnCustInfo': {
                'returnCustSpendAvg': 327.1482661636541,
                'returnVisitDiscAvg': 66.94776422637142,
                'returnVisitItemAvg': 2.2268563409032915,
                'returnCustVisitAvg': 3.2810537772444395,
                'returnCustDiscAvg': 219.6592146930061,
                'returnTxnAmt': 23445407.642884437,
                'returnItemQty': 523623,
                'returnVisitSpendAvg': 99.70829141313446,
                'returnCustItemAvg': 7.306435408701477,
                'returnCustCount': 71666,
                'returnDiscAmt': 15742097.280188976,
                'returnTxnCount': 235140
              },
              'spendBalor': 1.2711026802407643,
              'retention': 0.5940927290663263,
              'newCustInfo': {
                'newCustDiscAvg': 159.6234505235224,
                'newCustCount': 31117,
                'newTxnCount': 52648,
                'newVisitSpendAvg': 119.06677025727213,
                'newCustSpendAvg': 201.45346018269316,
                'newCustItemAvg': 4.1459330912363015,
                'newVisitItemAvg': 2.450406473180368,
                'newCustVisitAvg': 1.6919368833756467,
                'newVisitDiscAvg': 94.34362007940372,
                'newItemQty': 129009,
                'newTxnAmt': 6268627.320504863,
                'newDiscAmt': 4967002.909940447
              },
              'timePeriod': 14,
              'custBalor': 1.0509139181047686,
              'reactCustInfo': {
                'reactTxnCount': 42127,
                'reactCustSpendAvg': 232.09313164978553,
                'reactCustCount': 20341,
                'reactCustDiscAvg': 179.6216813457115,
                'reactCustItemAvg': 5.163610441964505,
                'reactDiscAmt': 3653684.6202531173,
                'reactCustVisitAvg': 2.0710387886534587,
                'reactVisitDiscAvg': 86.73023524706524,
                'reactVisitItemAvg': 2.4932466114368457,
                'reactItemQty': 105033,
                'reactTxnAmt': 4721006.390888288,
                'reactVisitSpendAvg': 112.06604768647868
              },
              'lapsedCustInfo': {
                'lapsedTxnCount': 88060,
                'lapsedCustDiscAvg': 132.1775535587458,
                'lapsedCustSpendAvg': 176.56996224500588,
                'lapsedDiscAmt': 6472073.910003988,
                'lapsedCustVisitAvg': 1.7984274481772695,
                'lapsedCustItemAvg': 4.229184111099765,
                'lapsedVisitItemAvg': 2.3516011810129456,
                'lapsedVisitSpendAvg': 98.18019760761655,
                'lapsedItemQty': 207082,
                'lapsedVisitDiscAvg': 73.49618339772869,
                'lapsedCustCount': 48965,
                'lapsedTxnAmt': 8645748.201326713
              }
            }, {
              'txnBalor': 0.8112292391357602,
              'returnCustInfo': {
                'returnCustSpendAvg': 294.83251676438385,
                'returnVisitDiscAvg': 60.04731620915555,
                'returnVisitItemAvg': 2.2522691549195946,
                'returnCustVisitAvg': 3.2300016160310276,
                'returnCustDiscAvg': 193.95292839389856,
                'returnTxnAmt': 21893083.364856087,
                'returnItemQty': 540200,
                'returnVisitSpendAvg': 91.27937128609524,
                'returnCustItemAvg': 7.274833010127128,
                'returnCustCount': 74256,
                'returnDiscAmt': 14402168.65081733,
                'returnTxnCount': 239847
              },
              'spendBalor': 0.878491574669494,
              'retention': 0.5836267605633803,
              'newCustInfo': {
                'newCustDiscAvg': 158.27805837886137,
                'newCustCount': 30284,
                'newTxnCount': 50440,
                'newVisitSpendAvg': 118.06435131599575,
                'newCustSpendAvg': 196.64396646344028,
                'newCustItemAvg': 4.3910315678245935,
                'newVisitItemAvg': 2.6363600317208564,
                'newCustVisitAvg': 1.6655659754325716,
                'newVisitDiscAvg': 95.02959397195555,
                'newItemQty': 132978,
                'newTxnAmt': 5955165.880378826,
                'newDiscAmt': 4793292.719945438
              },
              'timePeriod': 15,
              'custBalor': 0.8753964059196617,
              'reactCustInfo': {
                'reactTxnCount': 32350,
                'reactCustSpendAvg': 208.86588469008356,
                'reactCustCount': 16091,
                'reactCustDiscAvg': 163.49547511385367,
                'reactCustItemAvg': 5.291591572928967,
                'reactDiscAmt': 2630805.6900570192,
                'reactCustVisitAvg': 2.010440618979554,
                'reactVisitDiscAvg': 81.32320525678576,
                'reactVisitItemAvg': 2.6320556414219474,
                'reactItemQty': 85147,
                'reactTxnAmt': 3360860.9505481347,
                'reactVisitSpendAvg': 103.89060125341993
              },
              'lapsedCustInfo': {
                'lapsedTxnCount': 102055,
                'lapsedCustDiscAvg': 145.47846532630243,
                'lapsedCustSpendAvg': 200.17690333604156,
                'lapsedDiscAmt': 7706867.1791261975,
                'lapsedCustVisitAvg': 1.9264383871942012,
                'lapsedCustItemAvg': 4.243374358199939,
                'lapsedVisitItemAvg': 2.202704424085052,
                'lapsedVisitSpendAvg': 103.91035844525146,
                'lapsedItemQty': 224797,
                'lapsedVisitDiscAvg': 75.51680152002545,
                'lapsedCustCount': 52976,
                'lapsedTxnAmt': 10604571.631130138
              }
            }, {
              'txnBalor': 1.3058239551362207,
              'returnCustInfo': {
                'returnCustSpendAvg': 354.6898037079212,
                'returnVisitDiscAvg': 62.62428191805756,
                'returnVisitItemAvg': 2.110869597974806,
                'returnCustVisitAvg': 3.597354726799653,
                'returnCustDiscAvg': 225.28175657035843,
                'returnTxnAmt': 26173269.99521492,
                'returnItemQty': 560343,
                'returnVisitSpendAvg': 98.59739465378414,
                'returnCustItemAvg': 7.593546725932351,
                'returnCustCount': 73792,
                'returnDiscAmt': 16623991.380839888,
                'returnTxnCount': 265456
              },
              'spendBalor': 1.6060810024210677,
              'retention': 0.6257695764997202,
              'newCustInfo': {
                'newCustDiscAvg': 162.20548070748944,
                'newCustCount': 34906,
                'newTxnCount': 61574,
                'newVisitSpendAvg': 117.55995616162825,
                'newCustSpendAvg': 207.3751429753079,
                'newCustItemAvg': 4.317882312496419,
                'newVisitItemAvg': 2.447786403352064,
                'newCustVisitAvg': 1.763994728699937,
                'newVisitDiscAvg': 91.95349513716222,
                'newItemQty': 150720,
                'newTxnAmt': 7238636.740696098,
                'newDiscAmt': 5661944.509575627
              },
              'timePeriod': 16,
              'custBalor': 1.2109675957398596,
              'reactCustInfo': {
                'reactTxnCount': 41812,
                'reactCustSpendAvg': 249.06845963198677,
                'reactCustCount': 18534,
                'reactCustDiscAvg': 185.38615032551513,
                'reactCustItemAvg': 5.504262436603,
                'reactDiscAmt': 3435946.9101330973,
                'reactCustVisitAvg': 2.255962015754829,
                'reactVisitDiscAvg': 82.17609562166597,
                'reactVisitItemAvg': 2.4398737204630248,
                'reactItemQty': 102016,
                'reactTxnAmt': 4616234.830819243,
                'reactVisitSpendAvg': 110.40454488709564
              },
              'lapsedCustInfo': {
                'lapsedTxnCount': 79173,
                'lapsedCustDiscAvg': 124.887755257279,
                'lapsedCustSpendAvg': 167.2613050185449,
                'lapsedDiscAmt': 5511296.639503722,
                'lapsedCustVisitAvg': 1.7940856560163154,
                'lapsedCustItemAvg': 3.691003852254702,
                'lapsedVisitItemAvg': 2.057317519861569,
                'lapsedVisitSpendAvg': 93.22927501128397,
                'lapsedItemQty': 162884,
                'lapsedVisitDiscAvg': 69.61080973947838,
                'lapsedCustCount': 44130,
                'lapsedTxnAmt': 7381241.390468386
              }
            }, {
              'txnBalor': 1.1854048225838072,
              'returnCustInfo': {
                'returnCustSpendAvg': 308.854214499955,
                'returnVisitDiscAvg': 59.30857391154833,
                'returnVisitItemAvg': 1.9716420702001265,
                'returnCustVisitAvg': 3.381286514485334,
                'returnCustDiscAvg': 200.53928116047507,
                'returnTxnAmt': 20511626.09337101,
                'returnItemQty': 442748,
                'returnVisitSpendAvg': 91.34221935255485,
                'returnCustItemAvg': 6.666686743359634,
                'returnCustCount': 66412,
                'returnDiscAmt': 13318214.74042947,
                'returnTxnCount': 224558
              },
              'spendBalor': 1.4393130526771596,
              'retention': 0.0,
              'newCustInfo': {
                'newCustDiscAvg': 141.51940604596155,
                'newCustCount': 23588,
                'newTxnCount': 39456,
                'newVisitSpendAvg': 107.48480231260822,
                'newCustSpendAvg': 179.79143462973843,
                'newCustItemAvg': 3.765685942004409,
                'newVisitItemAvg': 2.251241889699919,
                'newCustVisitAvg': 1.6727149397998982,
                'newVisitDiscAvg': 84.60461653011306,
                'newItemQty': 88825,
                'newTxnAmt': 4240920.36004627,
                'newDiscAmt': 3338159.749812141
              },
              'timePeriod': 17,
              'custBalor': 1.126492586274767,
              'reactCustInfo': {
                'reactTxnCount': 57292,
                'reactCustSpendAvg': 206.76903339727284,
                'reactCustCount': 27922,
                'reactCustDiscAvg': 158.46148054799977,
                'reactCustItemAvg': 4.68848936322613,
                'reactDiscAmt': 4424561.45986125,
                'reactCustVisitAvg': 2.051858749373254,
                'reactVisitDiscAvg': 77.22825978952122,
                'reactVisitItemAvg': 2.2849961600223416,
                'reactItemQty': 130912,
                'reactTxnAmt': 5773404.950518652,
                'reactVisitSpendAvg': 100.77157282899273
              },
              'lapsedCustInfo': {
                'lapsedTxnCount': 81616,
                'lapsedCustDiscAvg': 105.31214187047803,
                'lapsedCustSpendAvg': 152.160952861904,
                'lapsedDiscAmt': 4815502.999169478,
                'lapsedCustVisitAvg': 1.7848926212658007,
                'lapsedCustItemAvg': 3.0397804312644885,
                'lapsedVisitItemAvg': 1.703060674377573,
                'lapsedVisitSpendAvg': 85.24935956875396,
                'lapsedItemQty': 138997,
                'lapsedVisitDiscAvg': 59.0019481372461,
                'lapsedCustCount': 45726,
                'lapsedTxnAmt': 6957711.730563423
              }
            }],
            'minDateBalor': '2013-11-01'
          },
          'errors': '',
          'moreInfo': '',
          'userMessage': 'Success',
          'developerMessage': ''
        },
        totalBars: {},
        avgBars: {},
        tpArray: [],
        ttlReturnCust: [],
        ttlLapsedCust: [],
        ttlNewCust: [],
        ttlReactCust: [],
        ttlReturnTxn: [],
        ttlLapsedTxn: [],
        ttlNewTxn: [],
        ttlReactTxn: [],
        ttlReturnItem: [],
        ttlLapsedItem: [],
        ttlNewItem: [],
        ttlReactItem: [],
        ttlReturnSpend: [],
        ttlLapsedSpend: [],
        ttlNewSpend: [],
        ttlReactSpend: [],
        ttlReturnDisc: [],
        ttlLapsedDisc: [],
        ttlNewDisc: [],
        ttlReactDisc: [],
        avgNewCustSpend: [],
        avgNewCustItems: [],
        avgNewCustDisc: [],
        avgNewCustTxn: [],
        avgNewVisitSpend: [],
        avgNewVisitItems: [],
        avgNewVisitDisc: [],
        avgRetCustSpend: [],
        avgRetCustItems: [],
        avgRetCustDisc: [],
        avgRetCustTxn: [],
        avgRetVisitSpend: [],
        avgRetVisitItems: [],
        avgRetVisitDisc: [],
        avgReactCustSpend: [],
        avgReactCustItems: [],
        avgReactCustDisc: [],
        avgReactCustTxn: [],
        avgReactVisitSpend: [],
        avgReactVisitItems: [],
        avgReactVisitDisc: [],
        avgLapCustSpend: [],
        avgLapCustItems: [],
        avgLapCustDisc: [],
        avgLapCustTxn: [],
        avgLapVisitSpend: [],
        avgLapVisitItems: [],
        avgLapVisitDisc: []
      }
    },
    computed: {
      jsonMsg: function () {
        return this.incomingJson.data
      }
    },
    mounted () {
      this.parseJson()
      this.createBars()
      // put initial chart creation methods here
    },
    methods: {
      parseJson () {
        var tempCustRet = []
        var tempCustLap = []
        var tempCustNew = []
        var tempCustReact = []
        var tempTxnRet = []
        var tempTxnLap = []
        var tempTxnNew = []
        var tempTxnReact = []
        var tempItemRet = []
        var tempItemLap = []
        var tempItemNew = []
        var tempItemReact = []
        var tempSpendRet = []
        var tempSpendLap = []
        var tempSpendNew = []
        var tempSpendReact = []
        var tempDiscRet = []
        var tempDiscLap = []
        var tempDiscNew = []
        var tempDiscReact = []

        var tempNewCustSpend = []
        var tempNewCustItems = []
        var tempNewCustDisc = []
        var tempNewCustTxn = []
        var tempNewVisitSpend = []
        var tempNewVisitItems = []
        var tempNewVisitDisc = []
        var tempRetCustSpend = []
        var tempRetCustItems = []
        var tempRetCustDisc = []
        var tempRetCustTxn = []
        var tempRetVisitSpend = []
        var tempRetVisitItems = []
        var tempRetVisitDisc = []
        var tempReactCustSpend = []
        var tempReactCustItems = []
        var tempReactCustDisc = []
        var tempReactCustTxn = []
        var tempReactVisitSpend = []
        var tempReactVisitItems = []
        var tempReactVisitDisc = []
        var tempLapCustSpend = []
        var tempLapCustItems = []
        var tempLapCustDisc = []
        var tempLapCustTxn = []
        var tempLapVisitSpend = []
        var tempLapVisitItems = []
        var tempLapVisitDisc = []

        var tempTp = []

        // Bar chart arrays
        for (var i = 0; i < this.jsonMsg.timePeriods.length; i++) {
          tempTp.push(this.jsonMsg.timePeriods[i].timePeriod)

          tempCustRet.push(this.jsonMsg.timePeriods[i].returnCustInfo.returnCustCount)
          tempCustLap.push(this.jsonMsg.timePeriods[i].lapsedCustInfo.lapsedCustCount)
          tempCustNew.push(this.jsonMsg.timePeriods[i].newCustInfo.newCustCount)
          tempCustReact.push(this.jsonMsg.timePeriods[i].reactCustInfo.reactCustCount)

          tempTxnRet.push(this.jsonMsg.timePeriods[i].returnCustInfo.returnTxnCount)
          tempTxnLap.push(this.jsonMsg.timePeriods[i].lapsedCustInfo.lapsedTxnCount)
          tempTxnNew.push(this.jsonMsg.timePeriods[i].newCustInfo.newTxnCount)
          tempTxnReact.push(this.jsonMsg.timePeriods[i].reactCustInfo.reactTxnCount)

          tempItemRet.push(this.jsonMsg.timePeriods[i].returnCustInfo.returnItemQty)
          tempItemLap.push(this.jsonMsg.timePeriods[i].lapsedCustInfo.lapsedItemQty)
          tempItemNew.push(this.jsonMsg.timePeriods[i].newCustInfo.newItemQty)
          tempItemReact.push(this.jsonMsg.timePeriods[i].reactCustInfo.reactItemQty)

          tempSpendRet.push(this.jsonMsg.timePeriods[i].returnCustInfo.returnTxnAmt)
          tempSpendLap.push(this.jsonMsg.timePeriods[i].lapsedCustInfo.lapsedTxnAmt)
          tempSpendNew.push(this.jsonMsg.timePeriods[i].newCustInfo.newTxnAmt)
          tempSpendReact.push(this.jsonMsg.timePeriods[i].reactCustInfo.reactTxnAmt)

          tempDiscRet.push(this.jsonMsg.timePeriods[i].returnCustInfo.returnDiscAmt)
          tempDiscLap.push(this.jsonMsg.timePeriods[i].lapsedCustInfo.lapsedDiscAmt)
          tempDiscNew.push(this.jsonMsg.timePeriods[i].newCustInfo.newDiscAmt)
          tempDiscReact.push(this.jsonMsg.timePeriods[i].reactCustInfo.reactDiscAmt)

          tempNewCustSpend.push(this.jsonMsg.timePeriods[i].newCustInfo.newCustSpendAvg)
          tempNewCustItems.push(this.jsonMsg.timePeriods[i].newCustInfo.newCustItemAvg)
          tempNewCustDisc.push(this.jsonMsg.timePeriods[i].newCustInfo.newCustDiscAvg)
          tempNewCustTxn.push(this.jsonMsg.timePeriods[i].newCustInfo.newCustVisitAvg)
          tempNewVisitSpend.push(this.jsonMsg.timePeriods[i].newCustInfo.newVisitSpendAvg)
          tempNewVisitItems.push(this.jsonMsg.timePeriods[i].newCustInfo.newVisitItemAvg)
          tempNewVisitDisc.push(this.jsonMsg.timePeriods[i].newCustInfo.newVisitDiscAvg)

          tempRetCustSpend.push(this.jsonMsg.timePeriods[i].returnCustInfo.returnCustSpendAvg)
          tempRetCustItems.push(this.jsonMsg.timePeriods[i].returnCustInfo.returnCustItemAvg)
          tempRetCustDisc.push(this.jsonMsg.timePeriods[i].returnCustInfo.returnCustDiscAvg)
          tempRetCustTxn.push(this.jsonMsg.timePeriods[i].returnCustInfo.returnCustVisitAvg)
          tempRetVisitSpend.push(this.jsonMsg.timePeriods[i].returnCustInfo.returnVisitSpendAvg)
          tempRetVisitItems.push(this.jsonMsg.timePeriods[i].returnCustInfo.returnVisitItemAvg)
          tempRetVisitDisc.push(this.jsonMsg.timePeriods[i].returnCustInfo.returnVisitDiscAvg)

          tempReactCustSpend.push(this.jsonMsg.timePeriods[i].reactCustInfo.reactCustSpendAvg)
          tempReactCustItems.push(this.jsonMsg.timePeriods[i].reactCustInfo.reactCustItemAvg)
          tempReactCustDisc.push(this.jsonMsg.timePeriods[i].reactCustInfo.reactCustDiscAvg)
          tempReactCustTxn.push(this.jsonMsg.timePeriods[i].reactCustInfo.reactCustVisitAvg)
          tempReactVisitSpend.push(this.jsonMsg.timePeriods[i].reactCustInfo.reactVisitSpendAvg)
          tempReactVisitItems.push(this.jsonMsg.timePeriods[i].reactCustInfo.reactVisitItemAvg)
          tempReactVisitDisc.push(this.jsonMsg.timePeriods[i].reactCustInfo.reactVisitDiscAvg)

          tempLapCustSpend.push(this.jsonMsg.timePeriods[i].lapsedCustInfo.lapsedCustSpendAvg)
          tempLapCustItems.push(this.jsonMsg.timePeriods[i].lapsedCustInfo.lapsedCustItemAvg)
          tempLapCustDisc.push(this.jsonMsg.timePeriods[i].lapsedCustInfo.lapsedCustDiscAvg)
          tempLapCustTxn.push(this.jsonMsg.timePeriods[i].lapsedCustInfo.lapsedCustVisitAvg)
          tempLapVisitSpend.push(this.jsonMsg.timePeriods[i].lapsedCustInfo.lapsedVisitSpendAvg)
          tempLapVisitItems.push(this.jsonMsg.timePeriods[i].lapsedCustInfo.lapsedVisitItemAvg)
          tempLapVisitDisc.push(this.jsonMsg.timePeriods[i].lapsedCustInfo.lapsedVisitDiscAvg)
        }

        this.tpArray = tempTp
        this.ttlReturnCust = tempCustRet
        this.ttlLapsedCust = tempCustLap
        this.ttlNewCust = tempCustNew
        this.ttlReactCust = tempCustReact
        this.ttlReturnTxn = tempTxnRet
        this.ttlLapsedTxn = tempTxnLap
        this.ttlNewTxn = tempTxnNew
        this.ttlReactTxn = tempTxnReact
        this.ttlReturnItem = tempItemRet
        this.ttlLapsedItem = tempItemLap
        this.ttlNewItem = tempItemNew
        this.ttlReactItem = tempItemReact
        this.ttlReturnSpend = tempSpendRet
        this.ttlLapsedSpend = tempSpendLap
        this.ttlNewSpend = tempSpendNew
        this.ttlReactSpend = tempSpendReact
        this.ttlReturnDisc = tempDiscRet
        this.ttlLapsedDisc = tempDiscLap
        this.ttlNewDisc = tempDiscNew
        this.ttlReactDisc = tempDiscReact

        this.avgNewCustSpend = tempNewCustSpend
        this.avgNewCustItems = tempNewCustItems
        this.avgNewCustDisc = tempNewCustDisc
        this.avgNewCustTxn = tempNewCustTxn
        this.avgNewVisitSpend = tempNewVisitSpend
        this.avgNewVisitItems = tempNewVisitItems
        this.avgNewVisitDisc = tempNewVisitDisc
        this.avgRetCustSpend = tempRetCustSpend
        this.avgRetCustItems = tempRetCustItems
        this.avgRetCustDisc = tempRetCustDisc
        this.avgRetCustTxn = tempRetCustTxn
        this.avgRetVisitSpend = tempRetVisitSpend
        this.avgRetVisitItems = tempRetVisitItems
        this.avgRetVisitDisc = tempRetVisitDisc
        this.avgReactCustSpend = tempReactCustSpend
        this.avgReactCustItems = tempReactCustItems
        this.avgReactCustDisc = tempReactCustDisc
        this.avgReactCustTxn = tempReactCustTxn
        this.avgReactVisitSpend = tempReactVisitSpend
        this.avgReactVisitItems = tempReactVisitItems
        this.avgReactVisitDisc = tempReactVisitDisc
        this.avgLapCustSpend = tempLapCustSpend
        this.avgLapCustItems = tempLapCustItems
        this.avgLapCustDisc = tempLapCustDisc
        this.avgLapCustTxn = tempLapCustTxn
        this.avgLapVisitSpend = tempLapVisitSpend
        this.avgLapVisitItems = tempLapVisitItems
        this.avgLapVisitDisc = tempLapVisitDisc
      },

      createBars () {
        this.totalBars = {
          labels: this.tpArray,
          datasets: [
            {
              data: this.ttlReturnCust,
              label: 'Returning',
              backgroundColor: '#F7970E'
            }, {
              data: this.ttlLapsedCust,
              label: 'Lapsed',
              backgroundColor: '#D63A09'
            }, {
              data: this.ttlNewCust,
              label: 'New',
              backgroundColor: '#8EAC1D'
            }, {
              data: this.ttlReactCust,
              label: 'Reactivated',
              backgroundColor: '#0087AA'
            }
          ]
        }
        this.avgBars = {
          type: 'horizontalBar',
          labels: ['Lapsed', 'New', 'Reactivated', 'Returning'],
          datasets: [
            {
              data: [this.avgLapCustSpend[this.tpSelect - 1], this.avgNewCustSpend[this.tpSelect - 1], this.avgReactCustSpend[this.tpSelect - 1], this.avgRetCustSpend[this.tpSelect - 1]],
              label: 'Avg Spend Per Customer',
              backgroundColor: ['#D63A09', '#8EAC1D', '#0087AA', '#F7970E']
            }
          ]
        }
      },

      selectDimension () {
        if (this.dimensionSelect === 'Customer') {
          console.log('Customer selected')
          this.totalBars = {
            labels: this.tpArray,
            datasets: [
              {
                data: this.ttlReturnCust,
                label: 'Returning',
                backgroundColor: '#F7970E'
              }, {
                data: this.ttlLapsedCust,
                label: 'Lapsed',
                backgroundColor: '#D63A09'
              }, {
                data: this.ttlNewCust,
                label: 'New',
                backgroundColor: '#8EAC1D'
              }, {
                data: this.ttlReactCust,
                label: 'Reactivated',
                backgroundColor: '#0087AA'
              }
            ]
          }
        } else if (this.dimensionSelect === 'Visits') {
          console.log('Visits selected')
          this.totalBars = {
            labels: this.tpArray,
            datasets: [
              {
                data: this.ttlReturnTxn,
                label: 'Returning',
                backgroundColor: '#F7970E'
              }, {
                data: this.ttlLapsedTxn,
                label: 'Lapsed',
                backgroundColor: '#D63A09'
              }, {
                data: this.ttlNewTxn,
                label: 'New',
                backgroundColor: '#8EAC1D'
              }, {
                data: this.ttlReactTxn,
                label: 'Reactivated',
                backgroundColor: '#0087AA'
              }
            ]
          }
        } else if (this.dimensionSelect === 'Spend') {
          console.log('Spend selected')
          this.totalBars = {
            labels: this.tpArray,
            datasets: [
              {
                data: this.ttlReturnSpend,
                label: 'Returning',
                backgroundColor: '#F7970E'
              }, {
                data: this.ttlLapsedSpend,
                label: 'Lapsed',
                backgroundColor: '#D63A09'
              }, {
                data: this.ttlNewSpend,
                label: 'New',
                backgroundColor: '#8EAC1D'
              }, {
                data: this.ttlReactSpend,
                label: 'Reactivated',
                backgroundColor: '#0087AA'
              }
            ]
          }
        } else if (this.dimensionSelect === 'Discount') {
          console.log('Discount selected')
          this.totalBars = {
            labels: this.tpArray,
            datasets: [
              {
                data: this.ttlReturnDisc,
                label: 'Returning',
                backgroundColor: '#F7970E'
              }, {
                data: this.ttlLapsedDisc,
                label: 'Lapsed',
                backgroundColor: '#D63A09'
              }, {
                data: this.ttlNewDisc,
                label: 'New',
                backgroundColor: '#8EAC1D'
              }, {
                data: this.ttlReactDisc,
                label: 'Reactivated',
                backgroundColor: '#0087AA'
              }
            ]
          }
        }
      },

      selectAvg () {
        if (this.dimensionSelect === 'Customer' && this.avgSelect === 'Avg Spend per Customer') {
          this.totalBars = {
            labels: this.tpArray,
            datasets: [
              {
                data: this.ttlReturnCust,
                label: 'Returning',
                backgroundColor: '#F7970E'
              }, {
                data: this.ttlLapsedCust,
                label: 'Lapsed',
                backgroundColor: '#D63A09'
              }, {
                data: this.ttlNewCust,
                label: 'New',
                backgroundColor: '#8EAC1D'
              }, {
                data: this.ttlReactCust,
                label: 'Reactivated',
                backgroundColor: '#0087AA'
              }, {
                data: this.avgRetCustSpend,
                label: 'Spend per Returning Customer',
                type: 'line',
                backgroundColor: '#F7970E',
                fill: false
              }, {
                data: this.avgLapCustSpend,
                label: 'Spend per Lapsed Customer',
                type: 'line',
                backgroundColor: '#D63A09',
                fill: false
              }, {
                data: this.avgNewCustSpend,
                label: 'Spend per New Customer',
                type: 'line',
                backgroundColor: '#8EAC1D',
                fill: false
              }, {
                data: this.avgReactCustSpend,
                label: 'Spend per Reactivated Customer',
                type: 'line',
                backgroundColor: '#0087AA',
                fill: false
              }
            ]
          }
        } else if (this.dimensionSelect === 'Customer' && this.avgSelect === 'Avg Items per Customer') {
          this.totalBars = {
            labels: this.tpArray,
            datasets: [
              {
                data: this.ttlReturnCust,
                label: 'Returning',
                backgroundColor: '#F7970E'
              }, {
                data: this.ttlLapsedCust,
                label: 'Lapsed',
                backgroundColor: '#D63A09'
              }, {
                data: this.ttlNewCust,
                label: 'New',
                backgroundColor: '#8EAC1D'
              }, {
                data: this.ttlReactCust,
                label: 'Reactivated',
                backgroundColor: '#0087AA'
              }, {
                data: this.avgRetCustItems,
                label: 'Items per Returning Customer',
                type: 'line',
                backgroundColor: '#F7970E',
                fill: false
              }, {
                data: this.avgLapCustItems,
                label: 'Items per Lapsed Customer',
                type: 'line',
                backgroundColor: '#D63A09',
                fill: false
              }, {
                data: this.avgNewCustItems,
                label: 'Items per New Customer',
                type: 'line',
                backgroundColor: '#8EAC1D',
                fill: false
              }, {
                data: this.avgReactCustItems,
                label: 'Items per Reactivated Customer',
                type: 'line',
                backgroundColor: '#0087AA',
                fill: false
              }
            ]
          }
        } else if (this.dimensionSelect === 'Customer' && this.avgSelect === 'Avg Discount per Customer') {
          this.totalBars = {
            labels: this.tpArray,
            datasets: [
              {
                data: this.ttlReturnCust,
                label: 'Returning',
                backgroundColor: '#F7970E'
              }, {
                data: this.ttlLapsedCust,
                label: 'Lapsed',
                backgroundColor: '#D63A09'
              }, {
                data: this.ttlNewCust,
                label: 'New',
                backgroundColor: '#8EAC1D'
              }, {
                data: this.ttlReactCust,
                label: 'Reactivated',
                backgroundColor: '#0087AA'
              }, {
                data: this.avgRetCustDisc,
                label: 'Discount per Returning Customer',
                type: 'line',
                backgroundColor: '#F7970E',
                fill: false
              }, {
                data: this.avgLapCustDisc,
                label: 'Discount per Lapsed Customer',
                type: 'line',
                backgroundColor: '#D63A09',
                fill: false
              }, {
                data: this.avgNewCustDisc,
                label: 'Discount per New Customer',
                type: 'line',
                backgroundColor: '#8EAC1D',
                fill: false
              }, {
                data: this.avgReactCustDisc,
                label: 'Discount per Reactivated Customer',
                type: 'line',
                backgroundColor: '#0087AA',
                fill: false
              }
            ]
          }
        } else if (this.dimensionSelect === 'Customer' && this.avgSelect === 'Avg Transactions per Customer') {
          this.totalBars = {
            labels: this.tpArray,
            datasets: [
              {
                data: this.ttlReturnCust,
                label: 'Returning',
                backgroundColor: '#F7970E'
              }, {
                data: this.ttlLapsedCust,
                label: 'Lapsed',
                backgroundColor: '#D63A09'
              }, {
                data: this.ttlNewCust,
                label: 'New',
                backgroundColor: '#8EAC1D'
              }, {
                data: this.ttlReactCust,
                label: 'Reactivated',
                backgroundColor: '#0087AA'
              }, {
                data: this.avgRetCustTxn,
                label: 'Visits per Returning Customer',
                type: 'line',
                backgroundColor: '#F7970E',
                fill: false
              }, {
                data: this.avgLapCustTxn,
                label: 'Visits per Lapsed Customer',
                type: 'line',
                backgroundColor: '#D63A09',
                fill: false
              }, {
                data: this.avgNewCustTxn,
                label: 'Visits per New Customer',
                type: 'line',
                backgroundColor: '#8EAC1D',
                fill: false
              }, {
                data: this.avgReactCustTxn,
                label: 'Visits per Reactivated Customer',
                type: 'line',
                backgroundColor: '#0087AA',
                fill: false
              }
            ]
          }
        } else if (this.dimensionSelect === 'Visits' && this.avgSelect === 'Avg Basket Amount') {
          this.totalBars = {
            labels: this.tpArray,
            datasets: [
              {
                data: this.ttlReturnTxn,
                label: 'Returning',
                backgroundColor: '#F7970E'
              }, {
                data: this.ttlLapsedTxn,
                label: 'Lapsed',
                backgroundColor: '#D63A09'
              }, {
                data: this.ttlNewTxn,
                label: 'New',
                backgroundColor: '#8EAC1D'
              }, {
                data: this.ttlReactTxn,
                label: 'Reactivated',
                backgroundColor: '#0087AA'
              }, {
                data: this.avgRetVisitSpend,
                label: 'Spend per Visit - Returning',
                type: 'line',
                backgroundColor: '#F7970E',
                fill: false
              }, {
                data: this.avgLapVisitSpend,
                label: 'Spend per Visit - Lapsed',
                type: 'line',
                backgroundColor: '#D63A09',
                fill: false
              }, {
                data: this.avgNewVisitSpend,
                label: 'Spend per Visit - New',
                type: 'line',
                backgroundColor: '#8EAC1D',
                fill: false
              }, {
                data: this.avgReactVisitSpend,
                label: 'Spend per Visit - Reactivated',
                type: 'line',
                backgroundColor: '#0087AA',
                fill: false
              }
            ]
          }
        } else if (this.dimensionSelect === 'Visits' && this.avgSelect === 'Avg Items per Basket') {
          this.totalBars = {
            labels: this.tpArray,
            datasets: [
              {
                data: this.ttlReturnTxn,
                label: 'Returning',
                backgroundColor: '#F7970E'
              }, {
                data: this.ttlLapsedTxn,
                label: 'Lapsed',
                backgroundColor: '#D63A09'
              }, {
                data: this.ttlNewTxn,
                label: 'New',
                backgroundColor: '#8EAC1D'
              }, {
                data: this.ttlReactTxn,
                label: 'Reactivated',
                backgroundColor: '#0087AA'
              }, {
                data: this.avgRetVisitItems,
                label: 'Items per Visit - Returning',
                type: 'line',
                backgroundColor: '#F7970E',
                fill: false
              }, {
                data: this.avgLapVisitItems,
                label: 'Items per Visit - Lapsed',
                type: 'line',
                backgroundColor: '#D63A09',
                fill: false
              }, {
                data: this.avgNewVisitItems,
                label: 'Items per Visit - New',
                type: 'line',
                backgroundColor: '#8EAC1D',
                fill: false
              }, {
                data: this.avgReactVisitItems,
                label: 'Items per Visit - Reactivated',
                type: 'line',
                backgroundColor: '#0087AA',
                fill: false
              }
            ]
          }
        } else if (this.dimensionSelect === 'Visits' && this.avgSelect === 'Avg Discount per Basket') {
          this.totalBars = {
            labels: this.tpArray,
            datasets: [
              {
                data: this.ttlReturnTxn,
                label: 'Returning',
                backgroundColor: '#F7970E'
              }, {
                data: this.ttlLapsedTxn,
                label: 'Lapsed',
                backgroundColor: '#D63A09'
              }, {
                data: this.ttlNewTxn,
                label: 'New',
                backgroundColor: '#8EAC1D'
              }, {
                data: this.ttlReactTxn,
                label: 'Reactivated',
                backgroundColor: '#0087AA'
              }, {
                data: this.avgRetVisitDisc,
                label: 'Discount per Visit - Returning',
                type: 'line',
                backgroundColor: '#F7970E',
                fill: false
              }, {
                data: this.avgLapVisitDisc,
                label: 'Discount per Visit - Lapsed',
                type: 'line',
                backgroundColor: '#D63A09',
                fill: false
              }, {
                data: this.avgNewVisitDisc,
                label: 'Discount per Visit - New',
                type: 'line',
                backgroundColor: '#8EAC1D',
                fill: false
              }, {
                data: this.avgReactVisitDisc,
                label: 'Discount per Visit - Reactivated',
                type: 'line',
                backgroundColor: '#0087AA',
                fill: false
              }
            ]
          }
        } else if (this.dimensionSelect === 'Spend' && this.avgSelect === 'Avg Spend per Customer') {
          this.totalBars = {
            labels: this.tpArray,
            datasets: [
              {
                data: this.ttlReturnSpend,
                label: 'Returning',
                backgroundColor: '#F7970E'
              }, {
                data: this.ttlLapsedSpend,
                label: 'Lapsed',
                backgroundColor: '#D63A09'
              }, {
                data: this.ttlNewSpend,
                label: 'New',
                backgroundColor: '#8EAC1D'
              }, {
                data: this.ttlReactSpend,
                label: 'Reactivated',
                backgroundColor: '#0087AA'
              }, {
                data: this.avgRetCustSpend,
                label: 'Spend per Customer - Returning',
                type: 'line',
                backgroundColor: '#F7970E',
                fill: false
              }, {
                data: this.avgLapCustSpend,
                label: 'Spend per Customer - Lapsed',
                type: 'line',
                backgroundColor: '#D63A09',
                fill: false
              }, {
                data: this.avgNewCustSpend,
                label: 'Spend per Customer - New',
                type: 'line',
                backgroundColor: '#8EAC1D',
                fill: false
              }, {
                data: this.avgReactCustSpend,
                label: 'Spend per Customer - Reactivated',
                type: 'line',
                backgroundColor: '#0087AA',
                fill: false
              }
            ]
          }
        } else if (this.dimensionSelect === 'Spend' && this.avgSelect === 'Avg Spend per Basket') {
          this.totalBars = {
            labels: this.tpArray,
            datasets: [
              {
                data: this.ttlReturnSpend,
                label: 'Returning',
                backgroundColor: '#F7970E'
              }, {
                data: this.ttlLapsedSpend,
                label: 'Lapsed',
                backgroundColor: '#D63A09'
              }, {
                data: this.ttlNewSpend,
                label: 'New',
                backgroundColor: '#8EAC1D'
              }, {
                data: this.ttlReactSpend,
                label: 'Reactivated',
                backgroundColor: '#0087AA'
              }, {
                data: this.avgRetVisitSpend,
                label: 'Spend per Visit - Returning',
                type: 'line',
                backgroundColor: '#F7970E',
                fill: false
              }, {
                data: this.avgLapVisitSpend,
                label: 'Spend per Visit - Lapsed',
                type: 'line',
                backgroundColor: '#D63A09',
                fill: false
              }, {
                data: this.avgNewVisitSpend,
                label: 'Spend per Visit - New',
                type: 'line',
                backgroundColor: '#8EAC1D',
                fill: false
              }, {
                data: this.avgReactVisitSpend,
                label: 'Spend per Visit - Reactivated',
                type: 'line',
                backgroundColor: '#0087AA',
                fill: false
              }
            ]
          }
        } else if (this.dimensionSelect === 'Discount' && this.avgSelect === 'Avg Discount per Customer') {
          this.totalBars = {
            labels: this.tpArray,
            datasets: [
              {
                data: this.ttlReturnDisc,
                label: 'Returning',
                backgroundColor: '#F7970E'
              }, {
                data: this.ttlLapsedDisc,
                label: 'Lapsed',
                backgroundColor: '#D63A09'
              }, {
                data: this.ttlNewDisc,
                label: 'New',
                backgroundColor: '#8EAC1D'
              }, {
                data: this.ttlReactDisc,
                label: 'Reactivated',
                backgroundColor: '#0087AA'
              }, {
                data: this.avgRetCustDisc,
                label: 'Discount per Customer - Returning',
                type: 'line',
                backgroundColor: '#F7970E',
                fill: false
              }, {
                data: this.avgLapCustDisc,
                label: 'Discount per Customer - Lapsed',
                type: 'line',
                backgroundColor: '#D63A09',
                fill: false
              }, {
                data: this.avgNewCustDisc,
                label: 'Discount per Customer - New',
                type: 'line',
                backgroundColor: '#8EAC1D',
                fill: false
              }, {
                data: this.avgReactCustDisc,
                label: 'Discount per Customer - Reactivated',
                type: 'line',
                backgroundColor: '#0087AA',
                fill: false
              }
            ]
          }
        } else if (this.dimensionSelect === 'Discount' && this.avgSelect === 'Avg Discount per Basket') {
          this.totalBars = {
            labels: this.tpArray,
            datasets: [
              {
                data: this.ttlReturnDisc,
                label: 'Returning',
                backgroundColor: '#F7970E'
              }, {
                data: this.ttlLapsedDisc,
                label: 'Lapsed',
                backgroundColor: '#D63A09'
              }, {
                data: this.ttlNewDisc,
                label: 'New',
                backgroundColor: '#8EAC1D'
              }, {
                data: this.ttlReactDisc,
                label: 'Reactivated',
                backgroundColor: '#0087AA'
              }, {
                data: this.avgRetVisitDisc,
                label: 'Discount per Visit - Returning',
                type: 'line',
                backgroundColor: '#F7970E',
                fill: false
              }, {
                data: this.avgLapVisitDisc,
                label: 'Discount per Visit - Lapsed',
                type: 'line',
                backgroundColor: '#D63A09',
                fill: false
              }, {
                data: this.avgNewVisitDisc,
                label: 'Discount per Visit - New',
                type: 'line',
                backgroundColor: '#8EAC1D',
                fill: false
              }, {
                data: this.avgReactVisitDisc,
                label: 'Discount per Visit - Reactivated',
                type: 'line',
                backgroundColor: '#0087AA',
                fill: false
              }
            ]
          }
        }
      },

      updateTPMetric () {
        if (this.tpAvgSelect === 'Spend per Customer') {
          this.avgBars = {
            type: 'horizontalBar',
            labels: ['Lapsed', 'New', 'Reactivated', 'Returning'],
            datasets: [
              {
                data: [this.avgLapCustSpend[this.tpSelect - 1], this.avgNewCustSpend[this.tpSelect - 1], this.avgReactCustSpend[this.tpSelect - 1], this.avgRetCustSpend[this.tpSelect - 1]],
                label: 'Avg Spend Per Customer',
                backgroundColor: ['#D63A09', '#8EAC1D', '#0087AA', '#F7970E']
              }
            ]
          }
        } else if (this.tpAvgSelect === 'Items per Customer') {
          this.avgBars = {
            type: 'horizontalBar',
            labels: ['Lapsed', 'New', 'Reactivated', 'Returning'],
            datasets: [
              {
                data: [this.avgLapCustItems[this.tpSelect - 1], this.avgNewCustItems[this.tpSelect - 1], this.avgReactCustItems[this.tpSelect - 1], this.avgRetCustItems[this.tpSelect - 1]],
                label: 'Avg Items Per Customer',
                backgroundColor: ['#D63A09', '#8EAC1D', '#0087AA', '#F7970E']
              }
            ]
          }
        } else if (this.tpAvgSelect === 'Discount per Customer') {
          this.avgBars = {
            type: 'horizontalBar',
            labels: ['Lapsed', 'New', 'Reactivated', 'Returning'],
            datasets: [
              {
                data: [this.avgLapCustDisc[this.tpSelect - 1], this.avgNewCustDisc[this.tpSelect - 1], this.avgReactCustDisc[this.tpSelect - 1], this.avgRetCustDisc[this.tpSelect - 1]],
                label: 'Avg Discount Per Customer',
                backgroundColor: ['#D63A09', '#8EAC1D', '#0087AA', '#F7970E']
              }
            ]
          }
        } else if (this.tpAvgSelect === 'Transactions per Customer') {
          this.avgBars = {
            type: 'horizontalBar',
            labels: ['Lapsed', 'New', 'Reactivated', 'Returning'],
            datasets: [
              {
                data: [this.avgLapCustTxn[this.tpSelect - 1], this.avgNewCustTxn[this.tpSelect - 1], this.avgReactCustTxn[this.tpSelect - 1], this.avgRetCustTxn[this.tpSelect - 1]],
                label: 'Avg Transactions Per Customer',
                backgroundColor: ['#D63A09', '#8EAC1D', '#0087AA', '#F7970E']
              }
            ]
          }
        } else if (this.tpAvgSelect === 'Spend per Basket') {
          this.avgBars = {
            type: 'horizontalBar',
            labels: ['Lapsed', 'New', 'Reactivated', 'Returning'],
            datasets: [
              {
                data: [this.avgLapVisitSpend[this.tpSelect - 1], this.avgNewVisitSpend[this.tpSelect - 1], this.avgReactVisitSpend[this.tpSelect - 1], this.avgRetVisitSpend[this.tpSelect - 1]],
                label: 'Avg Spend Per Visit',
                backgroundColor: ['#D63A09', '#8EAC1D', '#0087AA', '#F7970E']
              }
            ]
          }
        } else if (this.tpAvgSelect === 'Items per Basket') {
          this.avgBars = {
            type: 'horizontalBar',
            labels: ['Lapsed', 'New', 'Reactivated', 'Returning'],
            datasets: [
              {
                data: [this.avgLapVisitItems[this.tpSelect - 1], this.avgNewVisitItems[this.tpSelect - 1], this.avgReactVisitItems[this.tpSelect - 1], this.avgRetVisitItems[this.tpSelect - 1]],
                label: 'Avg Items Per Visit',
                backgroundColor: ['#D63A09', '#8EAC1D', '#0087AA', '#F7970E']
              }
            ]
          }
        } else if (this.tpAvgSelect === 'Discount per Basket') {
          this.avgBars = {
            type: 'horizontalBar',
            labels: ['Lapsed', 'New', 'Reactivated', 'Returning'],
            datasets: [
              {
                data: [this.avgLapVisitDisc[this.tpSelect - 1], this.avgNewVisitDisc[this.tpSelect - 1], this.avgReactVisitDisc[this.tpSelect - 1], this.avgRetVisitDisc[this.tpSelect - 1]],
                label: 'Avg Discount Per Visit',
                backgroundColor: ['#D63A09', '#8EAC1D', '#0087AA', '#F7970E']
              }
            ]
          }
        }
      }
      // todo fill in more methods here
    }
  }

</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.chart_h{height:31vh !important;}
.padT25{padding-top:25px !important;}
</style>
