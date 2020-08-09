<template>
  <div id="Login">
      <div class="id">
          <input type="text" id="login_id" v-model="id">
          <label for="login_id">아이디</label>
      </div>
      <div class="psw">
          <input type="text" id="login_psw" v-model="password">
          <label for="login_psw">비밀번호</label>
      </div>
      <button type="button">로그인</button>
  </div>
</template>

<script>

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

    }
  }
};
</script>

<style>
</style>
