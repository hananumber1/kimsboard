<template>
  <nav id="boardNav">
    <ul>
      <li
        v-for="list in page"
        :key="list.url"
        @click="clickBoardNav(list.url)"
        :class="{ active: list.url === activePage }"
      >
        <router-link :to="{ name: 'BoardList', params: { name: list.url } }">
          {{ list.name }}
        </router-link>
      </li>
    </ul>
  </nav>
</template>

<script>
export default {
  name: "BoardNav",
  data() {
    return {
      page: [
        { name: "공지사항", url: "notice" },
        { name: "자유게시판", url: "free" },
      ],
      activePage: "notice",
    };
  },
  created() {
    this.$emit("page", this.activePage);
  },
  methods: {
    clickBoardNav(val) {
      this.activePage = val;
      this.$emit("page", this.activePage);
    },
  },
};
</script>

<style lang="scss" scoped>
#boardNav .active {
  font-weight: bold;
  color: blue;
}
</style>
