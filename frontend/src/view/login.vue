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
      axios.post('/v1/signin', {
      userId: this.id,
      password: this.password
  })
  .then(function (response) {
    // document.cookie = `accessToken=${data.token}`
    // axios.defaults.headers.common.Authorization = `jwt ${response.data.token}`
    // localStorage.setItem('userInfo', JSON.stringify(data))
    console.log(response);
  })
  .catch(function (error) {
    console.log(error);
  });
}
  }
}
</script>
