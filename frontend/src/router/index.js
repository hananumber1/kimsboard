import Vue from "vue"
import Router from "vue-router"
import Home from '../view/Home'
import SignIn from '../view/sign/SignIn'
import SignUp from '../view/sign/SignUp'
import Board from '../view/board/Board'

Vue.use(Router)

export default new Router({
  mode: "hash",
  routes: [
    {
      path: "/",
      name: "Home",
        component: Home,
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