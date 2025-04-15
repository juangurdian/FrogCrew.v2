import { createStore } from 'vuex'

export default createStore({
  state: {
    user: null,
    isAuthenticated: false,
    currentMode: 'crew' // Default mode
  },
  mutations: {
    setUser(state, user) {
      state.user = user
      state.isAuthenticated = !!user
    },
    setMode(state, mode) {
      state.currentMode = mode
    }
  },
  actions: {
    login({ commit }, user) {
      commit('setUser', user)
    },
    logout({ commit }) {
      commit('setUser', null)
    },
    switchMode({ commit }, mode) {
      commit('setMode', mode)
    }
  },
  getters: {
    isAuthenticated: state => state.isAuthenticated,
    currentUser: state => state.user,
    isAdminMode: state => state.currentMode === 'admin',
    isCrewMode: state => state.currentMode === 'crew'
  }
}) 