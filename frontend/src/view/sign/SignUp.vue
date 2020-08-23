<template>
  <div id="SignUp">
    <div class="name">
      <label for="name">이름</label>
      <input type="text" id="name" v-model="name" />
    </div>
    <div class="add">
      <label for="add">거주지</label>
      <input type="text" id="add" v-model="add" />
    </div>
    <div class="phone">
      <label for="phone">핸드폰번호</label>
      <input type="text" id="phone" v-model="phone" />
    </div>
    <div class="id">
      <label for="login_id">아이디</label>
      <input type="text" id="login_id" v-model="id" />
      <button type="button" @click="checkUserId">중복검사</button>
    </div>
    <div class="psw">
      <label for="login_psw">비밀번호</label>
      <input type="password" id="login_psw" v-model="password" />
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
            .post("/api/v1/signup", {
              address: this.add,
              name: this.name,
              password: this.password,
              phone: this.phone,
              userId: this.id,
            })
            .then(({data})=> {
              alert("회원가입이 완료 되었습니다. 이동합니다.");
              this.$router.push("/");
            })
            .catch((error)=> {
              console.log(error.data);
            });
        } else {
          alert('아이디 중복검사를 확인해 주세요')
        }
      }
    },
    checkUserId() {
      axios
        .get("/api/v1/user/userid", {
          params: {
            userId: this.id,
          },
        })
        .then(({data})=> {
          console.log(data.data);
          if(data.code===0){
            if(data.data === true){
              this.idInspection = false;
              alert('중복된 아이디 입니다.')
            }else{
              this.idInspection = true;
              alert('사용할 수 있는 아이디 입니다.')
            }
          }
        })
    },
  },
};
</script>

<style lang="scss" scoped></style>
