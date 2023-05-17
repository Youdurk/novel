import router from '@/router'
import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
  },
  getters: {
  },
  mutations: {
    logout(){
      localStorage.removeItem("user")
      localStorage.removeItem("muen")
      router.push("/login")
    }
  },
  actions: {
  },
  modules: {
  }
})
