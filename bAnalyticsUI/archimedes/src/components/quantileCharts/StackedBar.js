 /**
 * Created by amerrill on 12/7/17.
 */

 import {HorizontalBar, mixins} from 'vue-chartjs'
 const {reactiveProp} = mixins

 export default HorizontalBar.extend({
   props: ['options', 'chartData'],
   mixins: [reactiveProp],
   data () {
     return {
       options: {
         responsive: true,
         maintainAspectRatio: false
       }
     }
   },
   mounted () {
     this.renderChart(this.chartData, this.options)
   }
 })
