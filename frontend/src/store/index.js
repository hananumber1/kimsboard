import Vue from "vue"
import Vuex from "vuex"
import router from '../router/index'
import axios from "axios"

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    userToken:null,
    detailCategory:null,
    detailId:null,
    loginErroMsg:null
  },
  getters: {},
  mutations: {
    saveDetail(state,payload){
      state.detailCategory = payload.page;
      state.detailId = payload.id;
    },
    LOGOUT(state,payload) {
      state.userToken = payload;
    },
    saveUserToken(state,payload){
      state.userToken = payload
    },
    setLoginErroMsg(state,payload){
      state.loginErroMsg = payload
    }

    
  },
  actions: {
    LOGIN({ commit }, { id, password }) {
      axios
      .post("/api/v1/signin", {
        userId: id,
        password: password,
      })
      .then(({data})=> {
        // console.log(data)
        commit("saveUserToken", data.data)
        localStorage.setItem("saveUserToken", data.data);
        router.push("/"); 
      })
      .catch((error)=> {
        // console.log(error.response)
        if(error.response.data.code===-1001){
          commit("setLoginErroMsg", error.response.data.msg);
        } else {
          commit("setLoginErroMsg", error.response.data.msg);
        }
      });
    },
    LOGOUT({ commit }) {
      commit("LOGOUT");
      localStorage.removeItem("saveUserToken");
      router.push("/"); 
    },
  },
})