import Vue from "vue";
import Vuex from 'vuex'
// import 'es6-promise/auto'
Vue.use(Vuex)

import App from "./app.vue";
import router from "./router"
import store from "./store"
import './asset/style/index.css';

// require('./utils/index');
import './utils/index'

// component 등록
Vue.component('Header', require('./component/layout/Header').default);
Vue.component('Body', require('./component/layout/Body').default);
Vue.component('SignIn', require('./view/sign/SignIn').default);
Vue.component('SignUp', require('./view/sign/SignUp').default);
Vue.component('Board', require('./view/board/Board').default);
Vue.component('BoardNav', require('./view/board/Nav').default);
Vue.component('BoardList', require('./view/board/List').default);
Vue.component('BoardDetail', require('./view/board/Detail').default);

Vue.config.productionTip = false;

new Vue({
  render: h => h(App),
  store,
  router,
}).$mount("#app");
