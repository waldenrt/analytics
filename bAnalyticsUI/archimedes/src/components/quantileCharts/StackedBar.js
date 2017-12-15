 /**
 * Created by amerrill on 12/7/17.
 */

 import {HorizontalBar, mixins} from 'vue-chartjs'
 const {reactiveProp} = mixins

 export default HorizontalBar.extend({
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
