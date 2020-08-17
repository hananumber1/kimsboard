import Vue from "vue"
import Vuex from "vuex"
import axios from "axios"
import createPersistedState from "vuex-persistedstate";
import Cookies from 'js-cookie';
 
Vue.use(Vuex)
export default new Vuex.Store({
  state: {
    userLoginToken: null,
  },
  plugins: [createPersistedState({
    storage: {
      getItem: key => Cookies.get(key),
      setItem: (key, value) => Cookies.set(key, value),
      removeItem: key => Cookies.remove(key)
    }
  })],
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
      .then((response)=> {
        commit("LOGIN", response.data.data)
        // axios.defaults.headers.common['Authorization'] = `Bearer ${response.data.data}`;
        localStorage.setItem("userLoginToken", response.data.data);
        console.log(response.data)
      })
      .catch((error)=> {
        console.log(error.response)
        if(error.response.data===-1001){
          alert(error.response.data.msg);
        } else {
          alert(error.response.data.msg);
        }
      });
    },
    LOGOUT({ commit }) {
      commit("LOGOUT")
      localStorage.removeItem("userLoginToken");
    },
  },
})