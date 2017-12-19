// first stab at creating a store...
import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

const store = new Vuex.Store({
  state: {
    appmodule: 'Balor',
    jobKey: 'test'
  },
  mutations: {
    switchPareto (state) {
      state.appmodule = 'Pareto'
    },
    switchBalor (state) {
      state.appmodule = 'Balor'
    },
    switchLifecycle (state) {
      state.appmodule = 'Lifecycle'
    },
    setJobKey (state, job) {
      state.jobKey = job.key
    }
  }
})

export default store
