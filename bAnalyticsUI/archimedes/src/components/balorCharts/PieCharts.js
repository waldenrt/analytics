/**
 * Created by amerrill on 8/24/17.
 */

import { Pie, mixins } from 'vue-chartjs'
const { reactiveProp } = mixins

export default Pie.extend({
  mixins: [reactiveProp],
  props: ['options'],
  options: {
    responsive: true,
    maintainAspectRatio: false
  },
  mounted () {
    this.renderChart(this.chartData, this.options)
  }
})
