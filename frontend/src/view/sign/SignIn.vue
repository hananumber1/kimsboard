<template>
  <div class="max-w-sm sm:max-w-md md:max-w-lg lg:max-w-xl xl:max-w-2xl mx-auto" id="SignIn">
    <form
      class="bg-white shadow-md rounded px-8 pt-6 pb-8 mb-4"
      @submit="setSignIn"
    >
      <div class="mb-4">
        <label
          class="block text-gray-700 text-sm font-bold mb-2"
          for="login_id"
        >
          아이디
        </label>
        <input
          class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
          id="login_id"
          type="text"
          placeholder="아이디"
          v-model="id" 
        />
      </div>
      <div class="mb-4">
        <label
          class="block text-gray-700 text-sm font-bold mb-2"
          for="login_psw"
        >
          비밀번호
        </label>
        <input
          class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
          id="login_psw"
          type="password"
          placeholder="비밀번호"
          v-model="password"
        />
        <p class="text-red-500 text-xs italic my-2" v-if="erroMsg!==null">{{erroMsg}}</p>
      </div>
      <div class="flex items-center justify-between">
        <button
          class="w-full bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline"
          type="submit"
          :class="{'opacity-50 cursor-not-allowed': disable}"
        >
          로그인
        </button>
      </div>
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
      erroMsg:null,
    };
  },
  watch: {
    "$store.state.loginErroMsg"() {
      this.erroMsg = this.$store.state.loginErroMsg;
    },
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
