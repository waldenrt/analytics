/**
 * Created by amerrill on 8/24/17.
 */

import { Line, mixins } from 'vue-chartjs'
const { reactiveProp } = mixins

export default Line.extend({
  mixins: [reactiveProp],
  data () {
    return {
      options: {
        responsive: true,
        maintainAspectRatio: false,
        scales: {
          xAxes: [{
            scaleLabel: {
              display: true,
              labelString: 'BALOR Time Period Start'
            }
          }],
          yAxes: [{
            scaleLabel: {
              display: true,
              labelString: 'BALOR Ratio'
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
