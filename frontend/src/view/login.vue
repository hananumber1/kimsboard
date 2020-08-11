<template>
  <div id="Login">
    <div class="id">
      <input type="text" id="login_id" v-model="id" />
      <label for="login_id">아이디</label>
    </div>
    <div class="psw">
      <input type="password" id="login_psw" v-model="password" />
      <label for="login_psw">비밀번호</label>
    </div>
    <button type="button" @click="setLogin">로그인</button>
  </div>
</template>

<script>
import axios from 'axios';
export default {
  name: "Login",
   data: function() {
         return  {
           id:'',
    password:''
         }
  },
  methods:{
    setLogin(){
      console.log(this.id, this.password)
      axios.post('/v1/signin',{
          userId: this.id,
          password: this.password
      })
      .then(function (response) {
      // document.cookie = `accessToken=${data.token}`
      // axios.defaults.headers.common.Authorization = `jwt ${response.data.token}`
      // localStorage.setItem('userInfo', JSON.stringify(data))
        console.log("성공");
        console.log(response);
      })
      .catch(function (error) {
        console.log("data에 나와있는 msg를 가지고 잘 활용해주세요 ex) 알람이 뜨거나 hover가 된다거나");
         console.log(error.response.data);
      });
    }
  }
}
</script>
