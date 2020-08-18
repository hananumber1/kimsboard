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
      console.log(accessToken)
      return state.userLoginToken
    },
    LOGOUT(state) {
      state.userLoginToken = null
      return state.userLoginToken
    },
  },
  actions: {
    LOGIN({ commit }, { id, password }) {
      console.log(commit)
      axios
      .post("/v1/signin", {
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
        console.log(error)
        if(error.data===-1001){
          alert('아이디 또는 비밀번호를 확인해주세요.');
        } else {
          alert("알수 없는 오류가 발생하였습니다.");
        }
      });
    },
    LOGOUT({ commit }) {
      commit("LOGOUT")
      localStorage.removeItem("userLoginToken");
    },
  },
})