import Vue from 'vue'
import Vuex from 'vuex'
import axios from 'axios'

Vue.use(Vuex)

const enhanceAccessToeken = () => {
  const accessToken = sessionStorage.getItem('accessToken')
  if (!accessToken) return
  axios.defaults.headers.common['Authorization'] = `Bearer ${accessToken}`
}

export const store = new Vuex.Store({
  state: {
    userName: '',
    accessToken: null,
    errorState: '',
    isAuth: false
  },
  getters: {
    isAuthenticated: state => state.isAuth
  },
  mutations: {
    LOGIN (state, {userName, token}) {
      state.user = userName
      state.accessToken = token
      state.errorState = ''
      state.isAuth = true

      sessionStorage.setItem('accessToken', token)
      sessionStorage.setItem('userName', userName)

      enhanceAccessToeken()
    },
    LOGOUT (state) {
      state.user = ''
      state.accessToken = null
      state.isAuth = false

      sessionStorage.removeItem('accessToken')
      sessionStorage.removeItem('userName')
    },
    ERROR_STATE (state, errorState) {
      state.errorState = errorState
    }
  },
  actions: {
    LOGIN ({commit}, {id, password}) {
      const payload = {
        'userId': id,
        'userPW': password
      }
      return axios.post('/api/login', payload)
        .then(response => {
          console.log(response)
          commit('LOGIN', response.data)
        })
        .catch(err => {
          const msg = err && err.response ? err.response.data.message : err
          console.log('login failed =>', msg)
          commit('ERROR_STATE', msg)
        })
    },
    LOGOUT ({commit}) {
      axios.defaults.headers.common['Authorization'] = undefined
      commit('LOGOUT')
    }
  }
})
