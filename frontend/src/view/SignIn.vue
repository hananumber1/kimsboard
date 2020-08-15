<template>
  <div id="SignIn">
    <div class="id">
      <input type="text" id="login_id" v-model="id" />
      <label for="login_id">아이디</label>
    </div>
    <div class="psw">
      <input type="password" id="login_psw" v-model="password" />
      <label for="login_psw">비밀번호</label>
    </div>
    <button type="button" @click="setSignIn">로그인</button>
  </div>
</template>

<script>
export default {
  name: "SignIn",
  data() {
    return {
      id: "",
      password: "",
    };
  },
  methods: {
    setSignIn() {
      const id = this.id;
      const password = this.password;
      this.$store.dispatch("LOGIN", { id, password });
      this.$router.push('board')
    },
  },
};
</script>
