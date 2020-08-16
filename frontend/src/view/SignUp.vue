<template>
  <div id="SignUp">
    <div class="name">
      <input type="text" id="name" v-model="name" />
      <label for="name">이름</label>
    </div>
    <div class="add">
      <input type="text" id="add" v-model="add" />
      <label for="add">이메일</label>
    </div>
    <div class="phone">
      <input type="text" id="phone" v-model="phone" />
      <label for="phone">핸드폰번호</label>
    </div>
    <div class="id">
      <input type="text" id="login_id" v-model="id" />
      <label for="login_id">아이디</label>
      <button type="button" @click="checkUserId">중복검사</button>
    </div>
    <div class="psw">
      <input type="password" id="login_psw" v-model="password" />
      <label for="login_psw">비밀번호</label>
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
      add: null,
      phone: null,
      idInspection: false,
    };
  },
  methods: {
    setSignUp() {
      var router = this.$router;
      if (
        this.id === null ||
        this.password === null ||
        this.name === null ||
        this.add === null ||
        this.phone === null
      ) {
        alert("항목을 다 입력해 주세요.");
      } else {
        if (this.idInspection) {
          axios
            .post("/v1/signup", {
              address: this.add,
              name: this.name,
              password: this.password,
              phone: this.phone,
              userId: this.id,
            })
            .then(function(response) {
              alert("회원가입이 완료 되었습니다. 게시글페이지로 이동합니다.");
              this.$router.push("/");
            })
            .catch(function(error) {
              console.log(error.data);
            });
        } else {
          alert('아이디 중복검사를 확인해 주세요')
        }
      }
    },
    checkUserId() {
      axios
        .get("/v1/user/userid", {
          params: {
            userId: this.id,
          },
        })
        .then(function(response) {
          console.log(response.data)
          if (parseInt(response.data.code) === 0) {
            this.idInspection = true;
          } else {
            this.idInspection = false;
          }
        })
    },
  },
};
</script>

<style lang="scss" scoped></style>
