import Vue from "vue"
import Router from "vue-router"
import App from '../App'
import SignIn from '../view/SignIn'
import SignUp from '../view/SignUp'
import Board from '../view/Board'

Vue.use(Router)

export default new Router({
  mode: "history",
  routes: [
    {
      path: "/",
      name: "Board",
        component: Board,
    },
    {
        path: "/board",
        name: "Board",
          component: Board,
      },
    {
      path: "/signin",
      name: "SignIn",
      component: SignIn,
    },
    {
        path: "/signup",
        name: "SignUp",
        component: SignUp,
      },
    
  ],
})