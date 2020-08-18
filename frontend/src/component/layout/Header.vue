<template>
  <header id="header">
    <template v-if="isSignin">
      <router-link to="/signin">로그인</router-link>
      <router-link to="/signup">회원가입</router-link>
      
    </template>
    <button type="button" v-else @click="logout">로그아웃</button>
    <router-link to="/board">게시글</router-link>
  
  </header>
</template>

<script>
export default {
  data() {
    return {
      userToken: null,
      isSignin: false,
    };
  },
  methods: {
    logout() {
      var router = this.$router;
      this.$store.dispatch("LOGOUT");
      localStorage.removeItem.userLoginToken;
      location.reload();
    },
  },
  created() {
    this.userToken = localStorage.getItem("userLoginToken");
    if (
      this.userToken === null ||
      this.userToken === undefined ||
      this.userToken === ""
    ) {
      this.isSignin = true;
    } else {
      this.isSignin = false;
    }
  },
};
</script>

<style lang="scss" scoped></style>
