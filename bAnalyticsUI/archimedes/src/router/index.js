import Vue from 'vue'
import Router from 'vue-router'
import Login from '@/components/Login'
import Home from '@/components/Home'
import JobHistory from '@/components/JobHistory'
import Balor from '@/components/Balor'
import NewBalor from '@/components/NewBalor'
import BalorSegmentTrend from '@/components/BalorSegmentTrend'
import BalorTrend from '@/components/BalorTrend'
import Cadence from '@/components/Cadence'
import Settings from '@/components/Settings'
import Quantile from '@/components/Quantile'
import NewQuantile from '@/components/NewQuantile'
import QuantProfile from '@/components/QuantProfile'
import QuantMigration from '@/components/QuantMigration'
import QuantProducts from '@/components/QuantProducts'
import Lifecycle from '@/components/Lifecycle'
import LifecycleProducts from '@/components/LifecycleProducts'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Home',
      component: Home
    },
    {
      path: '/Login',
      name: 'Login',
      component: Login
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
          component: NewQuantile
        },
        {
          path: 'Summary',
          name: 'QuantSummary',
          component: QuantProfile
        },
        {
          path: 'Migration',
          name: 'QuantMig',
          component: QuantMigration
        },
        {
          path: 'Products',
          name: 'QuantProd',
          component: QuantProducts
        }
      ]
    },
    {
      path: '/Lifecycle',
      name: 'LifecycleMain',
      component: Lifecycle,
      children: [
        {
          path: 'NewLifecycle',
          name: 'NewLifecycle',
          component: Settings
        },
        {
          path: 'Summary',
          name: 'LifecycleSummary',
          component: Settings
        },
        {
          path: 'Migration',
          name: 'LifecycleMig',
          component: Settings
        },
        {
          path: 'Products',
          name: 'LifecycleProd',
          component: LifecycleProducts
        }
      ]
    }
  ]
})
