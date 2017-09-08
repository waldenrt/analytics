/**
 * Created by amerrill on 8/24/17.
 */

import { HorizontalBar, mixins } from 'vue-chartjs'
const { reactiveProp } = mixins

export default HorizontalBar.extend({
  mixins: [reactiveProp],
  data () {
    return {
      options: {
        responsive: true,
        maintainAspectRatio: false
      }
    }
  },
  mounted () {
    this.renderChart(this.chartData, this.options)
  }
})
