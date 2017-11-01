/**
 * Created by amerrill on 8/30/17.
 */

import { Bar, mixins } from 'vue-chartjs'
const { reactiveProp } = mixins

export default Bar.extend({
  mixins: [reactiveProp],
  data () {
    return {
      options: {
        responsive: true,
        maintainAspectRatio: false,
        legend: {
          display: false
        },
        barThickness: 200
      }
    }
  },
  mounted () {
    this.renderChart(this.chartData, this.options)
  }
})
