/**
 * Created by amerrill on 9/14/17.
 */
import { Bar, mixins } from 'vue-chartjs'
const { reactiveProp } = mixins

export default Bar.extend({
  props: ['options'],
  mixins: [reactiveProp],
  watch: {
    'options': {
      handler (newOption, oldOption) {
        this._chart.destroy()
        this.renderChart(this.chartData, this.options)
      }
    }
  },
  mounted () {
    this.renderChart(this.chartData, this.options)
  }
})
