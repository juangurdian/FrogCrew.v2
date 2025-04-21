import { createStore } from 'vuex'
import { authService, userService } from '@/services/api'
import router from '@/router'

interface User {
  id: number;
  email: string;
  firstName: string;
  lastName: string;
  role: string;
}

interface State {
  token: string | null;
  user: User | null;
  isAuthenticated: boolean;
  currentMode: string;
}

// Helper function to safely parse JSON
const safeJSONParse = (str: string | null): any => {
  if (!str) return null;
  try {
    return JSON.parse(str);
  } catch (e) {
    return null;
  }
}

export default createStore<State>({
  state: {
    token: localStorage.getItem('token') || null,
    user: safeJSONParse(localStorage.getItem('user')),
    isAuthenticated: !!localStorage.getItem('token'),
    currentMode: localStorage.getItem('currentMode') || 'crew'
  },
  mutations: {
    setToken(state, token) {
      state.token = token
      state.isAuthenticated = !!token
      if (token) {
        localStorage.setItem('token', token)
      } else {
        localStorage.removeItem('token')
      }
    },
    setUser(state, user) {
      state.user = user
      if (user) {
        localStorage.setItem('user', JSON.stringify(user))
      } else {
        localStorage.removeItem('user')
      }
    },
    setMode(state, mode) {
      state.currentMode = mode
      localStorage.setItem('currentMode', mode)
    },
    logout(state) {
      state.token = null
      state.user = null
      state.isAuthenticated = false
      localStorage.removeItem('token')
      localStorage.removeItem('user')
      localStorage.removeItem('currentMode')
    }
  },
  actions: {
    async login({ commit, dispatch }, credentials) {
      try {
        const response = await authService.login(credentials)
        const { token, user } = response.data
        
        if (!token || !user) {
          throw new Error('Invalid response from server')
        }

        commit('setToken', token)
        commit('setUser', user)
        
        // Set mode based on user role
        const mode = user.role === 'ADMIN' ? 'admin' : 'crew'
        dispatch('switchMode', mode)
        router.push(`/${mode}/dashboard`)
        
        return response
      } catch (error) {
        console.error('Login error:', error)
        throw error
      }
    },
    async register({ commit }, userData) {
      try {
        const response = await authService.register(userData)
        return response
      } catch (error) {
        console.error('Registration error:', error)
        throw error
      }
    },
    logout({ commit }) {
      commit('logout')
      router.push('/login')
    },
    switchMode({ commit }, mode) {
      commit('setMode', mode)
    }
  },
  getters: {
    isAuthenticated: state => state.isAuthenticated,
    currentUser: state => state.user,
    isAdminMode: state => state.currentMode === 'admin',
    isCrewMode: state => state.currentMode === 'crew',
    token: state => state.token,
    userRole: state => state.user?.role
  }
}) 