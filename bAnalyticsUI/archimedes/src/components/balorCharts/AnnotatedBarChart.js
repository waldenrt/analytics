/**
 * Created by amerrill on 9/4/17.
 */

import {Bar, mixins} from 'vue-chartjs'
const {reactiveProp} = mixins

export default Bar.extend({
  mixins: [reactiveProp],
  data () {
    return {
      options: {
        responsive: true,
        maintainAspectRatio: false,
        annotation: {
          annotations: [
            {
              type: 'line',
              mode: 'vertical',
              scaleID: 'x-axis-0',
              value: 1,
              label: {
                content: 'Cadence Line',
                enabled: true,
                position: 'top'
              }
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
