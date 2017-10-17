/**
 * Created by amerrill on 9/4/17.
 */

import {Bar, mixins} from 'vue-chartjs'
import 'chartjs-plugin-annotation'
const {reactiveProp} = mixins

export default Bar.extend({
  props: ['options', 'chartData'],
  mixins: [reactiveProp],
  /* data () {
    return {
      options: {
        responsive: true,
        maintainAspectRatio: false,
        scales: {
          xAxes: [{
            categoryPercentage: 1.0,
            barPercentage: 1.0,
            id: 'x-axis-0',
            position: 'bottom'
          }]
        },
        annotation: {
          annotations: [
            {
              type: 'line',
              mode: 'vertical',
              scaleID: 'x-axis-0',
             // value: 47,
              label: {
                content: '80%',
                enabled: true,
                position: 'top'
              }
            }
          ]
        },
        legend: {
          display: false
        }
      }
    }
  }, */
  mounted () {
    this.renderChart(this.chartData, this.options)
  }
})
