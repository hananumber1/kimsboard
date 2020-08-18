<template>
  <div id="SignIn">
    <div class="id">
      <label for="login_id">아이디</label>
      <input type="text" id="login_id" v-model="id" />
    </div>
    <div class="psw">
      <label for="login_psw">비밀번호</label>
      <input type="password" id="login_psw" v-model="password" />
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
    },
  },
};
</script>
