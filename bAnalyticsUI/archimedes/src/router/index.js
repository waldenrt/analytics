import Vue from 'vue'
import Router from 'vue-router'
import Login from '@/components/Login'
import Logout from '@/components/Logout'
import Home from '@/components/Home'
import JobHistory from '@/components/JobHistory'
import Settings from '@/components/Settings'
import Feedback from '@/components/Feedback'
import Help from '@/components/Help'
import Nav from '@/components/Navigation'
import PasswordReset from '@/components/PasswordReset'
import PasswordResetConfirm from '@/components/PasswordResetConfirm'
import FileBrowser from '@/components/FileBrowser'

import Balor from '@/components/Balor'
import NewBalor from '@/components/NewBalor'
import BalorResults from '@/components/BalorResults'
import Cadence from '@/components/Cadence'
import BalorTrend from '@/components/BalorTrend'
import BalorSegmentTrend from '@/components/BalorSegmentTrend'
import BalorHistory from '@/components/BalorHistory'

import Quantile from '@/components/Quantile'
import NewQuantile from '@/components/NewQuantile'
import QuantResults from '@/components/QuantResults'
import QuantProfile from '@/components/QuantProfile'
import QuantMigration from '@/components/QuantMigration'
import QuantProducts from '@/components/QuantProducts'
import QuantHistory from '@/components/QuantHistory'

import Lifecycle from '@/components/Lifecycle'
import NewLifecycle from '@/components/NewLifecycle'
import LifecycleResults from '@/components/LifecycleResults'
import LifecycleSummary from '@/components/LifecycleSummary'
import LifecycleMigration from '@/components/LifecycleMigration'
import LifecycleProducts from '@/components/LifecycleProducts'
import LifecycleHistory from '@/components/LifecycleHistory'

import BRelevant from '@/components/BRelevant'
import NewBRelevant from '@/components/NewBRelevant'
import BRelevantResults from '@/components/BRelevantResults'
import BRelevantSummary from '@/components/BRelevantSummary'
import BRelevantDetails from '@/components/BRelevantDetails'
import BRelevantComparison from '@/components/BRelevantComparison'
import BRelevantHistory from '@/components/BRelevantHistory'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/Login',
      name: 'Login',
      component: Login
    },
    {
      path: '/Logout',
      name: 'Logout',
      component: Logout
    },
    {
      path: '/PasswordReset',
      name: 'PasswordReset',
      component: PasswordReset
    },
    {
      path: '/PasswordResetConfirm',
      name: 'PasswordResetConfirm',
      component: PasswordResetConfirm
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
          component: Help
        },
        {
          path: 'Settings',
          name: 'Settings',
          component: Settings
        },
        {
          path: 'Feedback',
          name: 'Feedback',
          component: Feedback
        },
        {
          path: 'Filebrowser',
          name: 'FileBrowser',
          component: FileBrowser
        },
        {
          path: 'Balor',
          component: Balor,
          children: [
            {
              path: '/',
              name: 'BalorHistory',
              component: BalorHistory
            },
            {
              path: 'NewBalor',
              name: 'NewBalor',
              component: NewBalor
            },
            {
              path: '/',
              component: BalorResults,
              children: [
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
            }
          ]
        },
        {
          path: 'Pareto',
          component: Quantile,
          children: [
            {
              path: '/',
              name: 'QuantHistory',
              component: QuantHistory
            },
            {
              path: 'NewPareto',
              name: 'NewQuantile',
              component: NewQuantile
            },
            {
              path: '/',
              component: QuantResults,
              children: [
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
              component: LifecycleHistory
            },
            {
              path: 'NewLifecycle',
              name: 'NewLifecycle',
              component: NewLifecycle
            },
            {
              path: '/',
              component: LifecycleResults,
              children: [
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
            }
          ]
        },
        {
          path: 'BRelevant',
          component: BRelevant,
          children: [
            {
              path: '/',
              name: 'BRelevantHistory',
              component: BRelevantHistory
            },
            {
              path: 'NewBRelevant',
              name: 'NewBRelevant',
              component: NewBRelevant
            },
            {
              path: '/',
              component: BRelevantResults,
              children: [
                {
                  path: 'Details',
                  name: 'BRelevantDetails',
                  component: BRelevantDetails
                },
                {
                  path: 'Summary',
                  name: 'BRelevantSummary',
                  component: BRelevantSummary
                },
                {
                  path: 'Comparison',
                  name: 'BRelevantComparison',
                  component: BRelevantComparison
                }
              ]
            }
          ]
        }]
    }
  ]
})
