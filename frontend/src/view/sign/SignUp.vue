<template>
  <form class="w-full max-w-lg mx-auto" id="SignUp" @submit="setSignUp">
    <!-- 이름 -->
    <div class="flex flex-wrap -mx-3 mb-6">
      <div class="w-full px-3 mb-6 md:mb-0">
        <label
          class="block uppercase tracking-wide text-gray-700 text-xs font-bold mb-2"
          for="name"
        >
          이름
        </label>
        <input
          class="appearance-none block w-full bg-gray-200 text-gray-700 border border-gray-200 rounded py-3 px-4 leading-tight focus:outline-none focus:bg-white focus:border-gray-500"
          id="name"
          type="text"
          placeholder="이름"
          v-model="name"
        />
      </div>
    </div>
    <!-- 거주지 -->
     <div class="flex flex-wrap -mx-3 mb-6">
      <div class="w-full px-3 mb-6 md:mb-0">
        <label
          class="block uppercase tracking-wide text-gray-700 text-xs font-bold mb-2"
          for="add"
        >
          거주지
        </label>
        <input
          class="appearance-none block w-full bg-gray-200 text-gray-700 border border-gray-200 rounded py-3 px-4 leading-tight focus:outline-none focus:bg-white focus:border-gray-500"
          id="add"
          type="text"
          placeholder="거주지"
          v-model="add"
        />
      </div>
    </div>
    <!-- 휴대폰번호 -->
     <div class="flex flex-wrap -mx-3 mb-6">
      <div class="w-full px-3 mb-6 md:mb-0">
        <label
          class="block uppercase tracking-wide text-gray-700 text-xs font-bold mb-2"
          for="phone"
        >
          휴대폰번호
        </label>
        <input
          class="appearance-none block w-full bg-gray-200 text-gray-700 border border-gray-200 rounded py-3 px-4 leading-tight focus:outline-none focus:bg-white focus:border-gray-500"
          id="phone"
          type="tel"
          placeholder="휴대폰번호"
          v-model="phone"
        />
      </div>
    </div>
    <!-- 아이디 -->
     <div class="flex flex-wrap -mx-3 mb-6">
      <div class="w-full px-3 mb-6 md:mb-0">
        <label
          class="block uppercase tracking-wide text-gray-700 text-xs font-bold mb-2"
          for="id"
        >
          아이디
        </label>
        <input
          class="appearance-none block w-5/6 bg-gray-200 text-gray-700 border border-gray-200 rounded py-3 px-4 leading-tight focus:outline-none focus:bg-white focus:border-gray-500 float-left"
          id="id"
          type="text"
          placeholder="아이디"
          v-model="id"
        />
        <button
          class="inline-block bg-teal-500 rounded-full px-3 py-1 text-sm font-semibold text-white my-2 float-right"
          type="button"
          @click="checkUserId"
        >
        중복검사
        </button>
        <p class="w-full text-xs italic my-2 float-left" 
        :class="idInspection ? 'text-teal-500' :'text-red-500'">{{erroMsg}}</p>
      </div>
    </div>
    <div class="flex flex-wrap -mx-3 mb-6">
      <div class="w-full px-3">
        <label
          class="block uppercase tracking-wide text-gray-700 text-xs font-bold mb-2"
          for="password"
        >
          비밀번호
        </label>
        <input
          class="appearance-none block w-full bg-gray-200 text-gray-700 border border-gray-200 rounded py-3 px-4 mb-3 leading-tight focus:outline-none focus:bg-white focus:border-gray-500"
          id="password"
          type="password"
          placeholder="비밀번호"
          v-model="password"
        />
      </div>
    </div>
    <div class="flex items-center justify-between">
        <button
          class="w-full bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline"
          type="submit"
          :class="{'opacity-50 cursor-not-allowed': disable}"
        >
          회원가입
        </button>
      </div>
  </form>
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
      erroMsg:null
    };
  },
  computed: {
    disable() {
      if (
        !this.add ||
        !this.name ||
        !this.password ||
        !this.phone ||
        !this.id
      ) {
        return true;
      } else {
        return false;
      }
    },
  },
  methods: {
    setSignUp(e) {
      e.preventDefault();
      var router = this.$router;
      if (this.idInspection) {
        axios
          .post("/v1/signup", {
            address: this.add,
            name: this.name,
            password: this.password,
            phone: this.phone,
            userId: this.id,
          })
          .then(({ data }) => {
            // console.log(data)
            alert("회원가입이 완료 되었습니다. 이동합니다.");
            this.$router.push("/");
          })
          .catch((error) => {
            console.log(error.data);
          });
      } else {
        alert("아이디 중복검사를 확인해 주세요");
      }
    },
    checkUserId() {
      axios
        .get("/v1/user/userid", {
          params: {
            userId: this.id,
          },
        })
        .then(({ data }) => {
          // console.log(data.data);
          if (data.code === 0) {
            if (data.data === true) {
              this.idInspection = false;
              this.erroMsg ="중복된 아이디 입니다.";
            } else {
              this.idInspection = true;
              this.erroMsg ="사용할 수 있는 아이디 입니다.";
            }
          }
        });
    },
  },
};
</script>

<style lang="scss" scoped></style>
