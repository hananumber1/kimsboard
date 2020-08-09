import Vue from "vue";
import App from "./App.vue";


Vue.component('login', require('./view/Login').default);

Vue.config.productionTip = false;

new Vue({
  render: h => h(App)
}).$mount("#app");
