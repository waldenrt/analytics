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
    switchApp (state, app) {
      state.appmodule = app.module
    },
    setJobKey (state, job) {
      state.jobKey = job.key
    }
  }
})

export default store
