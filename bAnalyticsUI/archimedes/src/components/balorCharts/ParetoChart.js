/**
 * Created by amerrill on 9/14/17.
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
        scales: {
          yAxes: [{
            id: 'left-y-axis',
            type: 'linear',
            position: 'left'
          }, {
            id: 'right-y-axis',
            type: 'linear',
            position: 'right'
          }],
          xAxes: [{
            barPercentage: 0.8
          }]
        }
      }
    }
  },
  mounted () {
    this.renderChart(this.chartData, this.options)
  }
})
