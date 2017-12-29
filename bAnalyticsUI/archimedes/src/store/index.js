// first stab at creating a store...
import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

const store = new Vuex.Store({
  state: {
    appmodule: 'Balor',
    jobKey: 'test',
    loggedIn: 'true'
  },
  mutations: {
    switchApp (state, app) {
      state.appmodule = app.module
    },
    setJobKey (state, job) {
      state.jobKey = job.jobid
    }
  },
  actions: {
    setJobKey ({commit}, job) {
      commit('setJobKey', job)
    }
  }
})

export default store
