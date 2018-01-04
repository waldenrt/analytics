/**
 * Created by amerrill on 9/4/17.
 */

import {Bar, mixins} from 'vue-chartjs'
// import 'chartjs-plugin-annotation'
const {reactiveProp} = mixins

export default Bar.extend({
  props: ['options', 'chartData'],
  mixins: [reactiveProp],
  mounted () {
    this.renderChart(this.chartData, this.options)
  }
})
