import Vue from 'vue'
import Router from 'vue-router'
import Hello from '@/components/Hello'
import JobHistory from '@/components/JobHistory'
import NewBalor from '@/components/NewBalor'
import BalorSegmentTrend from '@/components/BalorSegmentTrend'
import BalorSegmentProfile from '@/components/BalorSegmentProfile'
import BalorTrend from '@/components/BalorTrend'
import Cadence from '@/components/Cadence'
import Settings from '@/components/Settings'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Hello',
      component: Hello
    },
    {
      path: '/JobHistory',
      name: 'JobHistory',
      component: JobHistory
    },
    {
      path: '/Help',
      name: 'Help',
      component: Hello
    },
    {
      path: '/Logout',
      name: 'Logout',
      component: Hello
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
      path: '/Balor/SegmentProfile',
      name: 'BalorSegmentProfile',
      component: BalorSegmentProfile
    },
    {
      path: '/Balor/Trend',
      name: 'BalorTrend',
      component: BalorTrend
    }
  ]
})
