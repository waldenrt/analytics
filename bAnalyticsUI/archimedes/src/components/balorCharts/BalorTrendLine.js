/**
 * Created by amerrill on 8/24/17.
 */

import { Line, mixins } from 'vue-chartjs'
import 'chartjs-plugin-annotation'
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
              labelString: 'BALOR Ratio',
              id: 'y-axis-0'
            }
          }]
        },
        annotation: {
          annotations: [
            {
              type: 'line',
              mode: 'horizontal',
              scaleID: 'y-axis-0',
              value: 1,
              borderColor: '#D63A09',
              borderDash: [2, 2]
            }
          ]
        }
      }
    }
  },
  mounted () {
    this.renderChart(this.chartData, this.options)
  }
})
