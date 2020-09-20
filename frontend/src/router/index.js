import Vue from "vue"
import Router from "vue-router"
import Home from '../view/Home'
import SignIn from '../view/sign/SignIn'
import SignUp from '../view/sign/SignUp'
import Board from '../view/board/Board'
import BoardList from '../view/board/List'
import BoardDetail from '../view/board/Detail'

Vue.use(Router)

export default new Router({
  // mode: "hash",
  mode: "history",
  routes: [
    {
      path: "/",
      name: "Home",
        component: Home,
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
      {
        path: "/board",
        name: "Board",
          component: Board},
      {
        path: "/board/:name",
        name: "BoardList",
        component: BoardList,
      },
      {
        path: '/board/:name/detail/:id',
        name: "BoardDetail", 
        component: BoardDetail 
      }
  ],
})