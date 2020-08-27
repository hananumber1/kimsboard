import Vue from "vue"
import Vuex from "vuex"
import axios from "axios"

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    userLoginToken: null,
    headerToken:null,
    detailCategory:null,
    detailId:null,
  },
  getters: {},
  mutations: {
    LOGIN(state, { accessToken }) {
      state.userLoginToken = accessToken;
    },
    LOGOUT(state) {
      state.userLoginToken = null;
    },
    setToken(state, token) {
      state.token = token;
    },
    saveDetail(state,payload){
      state.detailCategory = payload.page;
      state.detailId = payload.id;
    }
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
  saveDetail({commit}){
    commit("saveDetail");

  }
})