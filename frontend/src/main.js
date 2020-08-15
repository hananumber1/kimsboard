import Vue from "vue";
import Vuex from 'vuex'
import 'es6-promise/auto'
Vue.use(Vuex)

import App from "./App.vue";
import router from "./router"
import store from "./store"

// component 등록
Vue.component('Header', require('./component/layout/Header').default);
Vue.component('Body', require('./component/layout/Body').default);
Vue.component('SignIn', require('./view/SignIn').default);
Vue.component('SignUp', require('./view/SignUp').default);
Vue.component('Board', require('./view/Board').default);

Vue.config.productionTip = false;

new Vue({
  render: h => h(App),
  store,
  router,
}).$mount("#app");
