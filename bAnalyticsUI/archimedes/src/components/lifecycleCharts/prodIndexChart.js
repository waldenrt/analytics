/**
 * Created by amerrill on 10/2/17.
 */

import { HorizontalBar, mixins } from 'vue-chartjs'
const { reactiveProp } = mixins

export default HorizontalBar.extend({
  mixins: [reactiveProp],
  data () {
    return {
      options: {
        responsive: true,
        maintainAspectRatio: false,
        legend: {
          display: false
        },
        scales: {
          yAxes: [{
            ticks: {
              display: false
            }
          }]
        }
      }
    }
  },
  mounted () {
    this.renderChart(this.chartData, this.options)
  }
})
