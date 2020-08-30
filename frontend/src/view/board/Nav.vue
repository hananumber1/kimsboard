<template>
  <nav id="boardNav" class="mb-2">
    <ul class="flex border-b">
      <li
        class="-mb-px mr-1"
        v-for="list in page"
        :key="list.url"
        @click="clickBoardNav(list.url)"
      >
        <router-link
          :to="{ name: 'BoardList', params: { name: list.url } }"
          :class="
            list.url === activePage
              ? 'bg-white inline-block border-l border-t border-r rounded-t py-2 px-4 text-blue-700 font-semibold'
              : 'bg-white inline-block py-2 px-4 text-blue-500 hover:text-blue-800 font-semibold'
          "
        >
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
