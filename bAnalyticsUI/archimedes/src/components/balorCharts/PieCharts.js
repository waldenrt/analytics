/**
 * Created by amerrill on 8/24/17.
 */

import { Pie, mixins } from 'vue-chartjs'
const { reactiveProp } = mixins

export default Pie.extend({
  mixins: [reactiveProp],
  data () {
    return {
      options: {
        responsive: true,
        maintainAspectRatio: false,
        legend: {
          display: false
        }
      }
    }
  },
  mounted () {
    this.renderChart(this.chartData, this.options)
  }
})
