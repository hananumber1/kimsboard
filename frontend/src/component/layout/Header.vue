<template>
  <header id="header">
    <nav class="flex items-center justify-between flex-wrap bg-teal-500 p-6">
      <div class="flex items-center flex-shrink-0 text-white mr-6">
        <span class="font-semibold text-xl tracking-tight">Kimsboard's</span>
      </div>
      <div class="w-full block flex-grow lg:flex lg:items-center lg:w-auto">
        <div class="text-sm lg:flex-grow">
          <router-link
            to="/"
            class="block mt-4 lg:inline-block lg:mt-0 text-teal-200 hover:text-white mr-4"
            >Home</router-link
          >
          <router-link
            to="/board/notice"
            class="block mt-4 lg:inline-block lg:mt-0 text-teal-200 hover:text-white mr-4"
            >게시글</router-link
          >
          <template v-if="isSignin">
            <router-link
              to="/signin"
              class="block mt-4 lg:inline-block lg:mt-0 text-teal-200 hover:text-white mr-4"
              >로그인</router-link
            >
            <router-link
              to="/signup"
              class="block mt-4 lg:inline-block lg:mt-0 text-teal-200 hover:text-white mr-4"
              >회원가입</router-link
            >
          </template>
          <button
            type="button"
            v-else
            @click="logout"
            class="block mt-4 lg:inline-block lg:mt-0 text-teal-200 hover:text-white mr-4"
          >
            로그아웃
          </button>
        </div>
      </div>
    </nav>
  </header>
</template>

<script>
export default {
  data() {
    return {
      userToken: null,
    };
  },
  watch: {
    "$store.state.userToken"() {
      this.userToken = this.$store.state.userToken;
    },
  },
  computed: {
    isSignin() {
      if (
        this.userToken === null ||
        this.userToken === undefined ||
        this.userToken === ""
      ) {
        return true;
      } else {
        return false;
      }
    },
  },
  methods: {
    logout() {
      this.$store.dispatch("LOGOUT");
      localStorage.removeItem.userLoginToken;
      location.href = "/";
    },
  },
};
</script>

<style lang="scss" scoped></style>
