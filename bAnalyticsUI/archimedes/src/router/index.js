import Vue from 'vue'
import Router from 'vue-router'
import Home from '@/components/Home'
import JobHistory from '@/components/JobHistory'
import Balor from '@/components/Balor'
import NewBalor from '@/components/NewBalor'
import BalorSegmentTrend from '@/components/BalorSegmentTrend'
import BalorTrend from '@/components/BalorTrend'
import Cadence from '@/components/Cadence'
import Settings from '@/components/Settings'
import Quantile from '@/components/Quantile'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Home',
      component: Home
    },
    {
      path: '/JobHistory',
      name: 'JobHistory',
      component: JobHistory
    },
    {
      path: '/Help',
      name: 'Help',
      component: Home
    },
    {
      path: '/Logout',
      name: 'Logout',
      component: Home
    },
    {
      path: '/Settings',
      name: 'Settings',
      component: Settings
    },
    {
      path: '/Balor',
      name: 'BalorMain',
      component: Balor,
      children: [
        {
          path: 'NewBalor',
          name: 'NewBalor',
          component: NewBalor
        },
        {
          path: 'Cadence',
          name: 'Cadence',
          component: Cadence
        },
        {
          path: 'SegmentTrend',
          name: 'BalorSegmentTrend',
          component: BalorSegmentTrend
        },
        {
          path: 'Trend',
          name: 'BalorTrend',
          component: BalorTrend
        }
      ]
    },
    {
      path: '/Quantile',
      name: 'QuantileMain',
      component: Quantile,
      children: [
        {
          path: 'NewQuantile',
          name: 'NewQuantile',
          component: Settings
        },
        {
          path: 'Summary',
          name: 'QuantSummary',
          component: Settings
        },
        {
          path: 'Migration',
          name: 'QuantMig',
          component: Settings
        },
        {
          path: 'Products',
          name: 'QuantProd',
          component: Settings
        }
      ]
    }
  ]
})
