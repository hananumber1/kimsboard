import Vue from "vue"
import Vuex from "vuex"
import axios from "axios"

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    userLoginToken: null,
  },
  getters: {},
  mutations: {
    LOGIN(state, { accessToken }) {
      state.userLoginToken = accessToken
      return state.userLoginToken
    },
    LOGOUT(state) {
      state.userLoginToken = null
      return state.userLoginToken
    },
  },
  actions: {
    LOGIN({ commit }, { id, password }) {
      return axios
      .post("/v1/signin", {
        userId: id,
        password: password,
      })
      .then(function(response) {
        commit("LOGIN", response.data.data)
        localStorage.setItem("userLoginToken", response.data.data);
        console.log(response.data)
      })
      .catch(function(error) {
        alert("알수 없는 오류가 발생하였습니다.");
      });
    },
    LOGOUT({ commit }) {
      commit("LOGOUT")
      localStorage.removeItem("userLoginToken");
    },
  },
})