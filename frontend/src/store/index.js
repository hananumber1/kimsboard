import Vue from "vue"
import Vuex from "vuex"
import axios from "axios"

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    userLoginToken: null,
    headerToken:null
  },
  getters: {},
  mutations: {
    LOGIN(state, { accessToken }) {
      state.userLoginToken = accessToken;
      return state.userLoginToken;
    },
    LOGOUT(state) {
      state.userLoginToken = null;
      return state.userLoginToken;
    },
    setToken(state, token) {
      state.token = token;
    },
  },
  actions: {
    LOGIN({ commit }, { id, password }) {
      return axios
      .post("/api/v1/signin", {
        userId: id,
        password: password,
      })
      .then(({data})=> {
        console.log(data)
        commit("LOGIN", data.data)
        localStorage.setItem("userLoginToken", data.data);
        location.href='/'
      
      })
      .catch((error)=> {
        console.log(error.response)
        if(error.response.data.code===-1001){
          alert(error.response.data.msg);
        } else {
          alert(error.response.data.msg);
        }
      });
    },
    LOGOUT({ commit }) {
      commit("LOGOUT");
      location.href='/';
      localStorage.removeItem("userLoginToken");
    },
  },
})