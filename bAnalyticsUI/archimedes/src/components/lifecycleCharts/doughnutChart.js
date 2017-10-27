/**
 * Created by amerrill on 10/27/17.
 */

import {Doughnut, mixins} from 'vue-chartjs'
const {reactiveProp} = mixins

export default Doughnut.extend({
  mixins: [reactiveProp],
  data () {
    return {
      options: {
        responsive: true,
        maintainAspectRatio: false,
        legend: {
          display: false
        },
        cutoutPercentage: 75
      }
    }
  },
  mounted () {
    var text1 = this.chartData.datasets[0].data[0]
    this.addPlugin({
      id: 'centerText',
      beforeDraw: function (chart) {
        var width = chart.chart.width
        var height = chart.chart.height
        var ctx = chart.chart.ctx

        ctx.restore()
        var fontSize = (height / 114).toFixed(2)
        ctx.font = fontSize + 'em sans-serif'
        ctx.textBaseline = 'middle'

        var text = text1.toFixed(2) + '%'
        var textX = Math.round((width - ctx.measureText(text).width) / 2)
        var textY = height / 2

        ctx.fillText(text, textX, textY)
        ctx.save()
      }
    })
    this.renderChart(this.chartData, this.options)
  }
})
