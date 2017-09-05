import Vue from 'vue'
import Router from 'vue-router'
import Home from '@/components/Home'
import JobHistory from '@/components/JobHistory'
import NewBalor from '@/components/NewBalor'
import BalorSegmentTrend from '@/components/BalorSegmentTrend'
import BalorTrend from '@/components/BalorTrend'
import Cadence from '@/components/Cadence'
import Settings from '@/components/Settings'

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
      path: '/Balor/NewBalor',
      name: 'NewBalor',
      component: NewBalor
    },
    {
      path: '/Balor/Cadence',
      name: 'Cadence',
      component: Cadence
    },
    {
      path: '/Balor/SegmentTrend',
      name: 'BalorSegmentTrend',
      component: BalorSegmentTrend
    },
    {
      path: '/Balor/Trend',
      name: 'BalorTrend',
      component: BalorTrend
    }
  ]
})
