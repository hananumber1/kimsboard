<template>
  <header id="header" class="flex -mx-2">
    <router-link to="/" class="w-1/4 px-2">home</router-link>
    <router-link to="/board/notice" class="w-1/4 px-2">게시글</router-link>
    <template v-if="isSignin">
      <router-link to="/signin" class="w-1/4 px-2">로그인</router-link>
      <router-link to="/signup" class="w-1/4 px-2">회원가입</router-link>
    </template>
    <button type="button" v-else @click="logout" class="w-1/4 px-2">로그아웃</button>
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
