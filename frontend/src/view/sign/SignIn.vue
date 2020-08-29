<template>
  <div id="SignIn">
    <form @submit="setSignIn">
      <div class="id">
        <label for="login_id">아이디</label>
        <input type="text" id="login_id" v-model="id" />
      </div>
      <div class="psw">
        <label for="login_psw">비밀번호</label>
        <input type="password" id="login_psw" v-model="password" />
      </div>
      <button type="submit" :disabled="disable">로그인</button>
    </form>
  </div>
</template>

<script>
export default {
  name: "SignIn",
  data() {
    return {
      id: null,
      password: null,
    };
  },
  computed: {
    disable() {
      if (!this.id && !this.password) {
        return true;
      } else {
        return false;
      }
    },
  },
  methods: {
    setSignIn(e) {
      e.preventDefault();
      const id = this.id;
      const password = this.password;
      this.$store.dispatch("LOGIN", { id, password });
    },
  },
};
</script>
