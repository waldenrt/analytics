import Vue from 'vue'
import Router from 'vue-router'
import VeeValidate from 'vee-validate'
import Login from '@/components/Login'
import Home from '@/components/Home'
import JobHistory from '@/components/JobHistory'
import Settings from '@/components/Settings'
import Nav from '@/components/Navigation'

import Balor from '@/components/Balor'
import NewBalor from '@/components/NewBalor'
import Cadence from '@/components/Cadence'
import BalorTrend from '@/components/BalorTrend'
import BalorSegmentTrend from '@/components/BalorSegmentTrend'

import Quantile from '@/components/Quantile'
import NewQuantile from '@/components/NewQuantile'
import QuantProfile from '@/components/QuantProfile'
import QuantMigration from '@/components/QuantMigration'
import QuantProducts from '@/components/QuantProducts'

import Lifecycle from '@/components/Lifecycle'
import NewLifecycle from '@/components/NewLifecycle'
import LifecycleSummary from '@/components/LifecycleSummary'
import LifecycleMigration from '@/components/LifecycleMigration'
import LifecycleProducts from '@/components/LifecycleProducts'

import BRelevant from '@/components/BRelevant'
import NewBRelevant from '@/components/NewBRelevant'
import BRelevantProducts from '@/components/BRelevantProducts'
import BRelevantCustomer from '@/components/BRelevantCustomer'

Vue.use(Router)
Vue.use(VeeValidate)

export default new Router({
  routes: [
    {
      path: '/Login',
      name: 'Login',
      component: Login
    },
    {
      path: '/',
      name: 'UserHome',
      component: Nav,
      children: [
        {
          path: 'Home',
          name: 'Home',
          component: Home
        },
        {
          path: 'JobHistory',
          name: 'JobHistory',
          component: JobHistory
        },
        {
          path: 'Help',
          name: 'Help',
          component: Home
        },
        {
          path: 'Logout',
          name: 'Logout',
          component: Home
        },
        {
          path: 'Settings',
          name: 'Settings',
          component: Settings
        },
        {
          path: 'Balor',
          component: Balor,
          children: [
            {
              path: '/',
              name: 'BalorHistory',
              component: JobHistory
            },
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
          path: 'Quantile',
          component: Quantile,
          children: [
            {
              path: '/',
              name: 'QuantHistory',
              component: JobHistory
            },
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
          path: 'Lifecycle',
          component: Lifecycle,
          children: [
            {
              path: '/',
              name: 'LifecycleHistory',
              component: JobHistory
            },
            {
              path: 'NewLifecycle',
              name: 'NewLifecycle',
              component: NewLifecycle
            },
            {
              path: 'Summary',
              name: 'LifecycleSummary',
              component: LifecycleSummary
            },
            {
              path: 'Migration',
              name: 'LifecycleMigration',
              component: LifecycleMigration
            },
            {
              path: 'Products',
              name: 'LifecycleProd',
              component: LifecycleProducts
            }
          ]
        },
        {
          path: 'BRelevant',
          component: BRelevant,
          children: [
            {
              path: '/',
              name: 'bHistory',
              component: JobHistory
            },
            {
              path: 'NewBRelevant',
              name: 'NewBRelevant',
              component: NewBRelevant
            },
            {
              path: 'Products',
              name: 'BRelevantProd',
              component: BRelevantProducts
            },
            {
              path: 'Customer',
              name: 'BRelevantCustomer',
              component: BRelevantCustomer
            }
          ]
        }]
    }
  ]
})
