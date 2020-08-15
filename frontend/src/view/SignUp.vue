<template>
  <div id="SignUp">
    <div class="id">
      <input type="text" id="login_id" v-model="id" />
      <label for="login_id">아이디</label>
    </div>
    <div class="psw">
      <input type="password" id="login_psw" v-model="password" />
      <label for="login_psw">비밀번호</label>
    </div>
    <div class="name">
      <input type="password" id="login_psw" v-model="name" />
      <label for="login_psw">이름</label>
    </div>
    <button type="button" @click="setSignUp">회원가입</button>
  </div>
</template>

<script>
import axios from "axios";
export default {
  name: "Signup",
  data() {
    return {
      id: null,
      password: null,
      name: null,
    };
  },
  methods: {
    setSignUp() {
      var router = this.$router;
      if (this.id === null || this.password === null || this.name === null) {
        alert("항목을 다 입력해 주세요.");
      } else {
        axios
          .post("/v1/signup", {
            userId: this.id,
            password: this.password,
            name: this.name,
          })
          .then(function(response) {
            alert("회원가입이 완료 되었습니다. 게시글페이지로 이동합니다.");
            this.$router.push("board");
          })
          .catch(function(error) {
            console.log(error);
          });
      }
    },
  },
};
</script>

<style lang="scss" scoped></style>
