// first stab at creating a store...
import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

const state = {
  appmodule: 'Balor'
}

const store = new Vuex.Store({
  state
})

export default store
