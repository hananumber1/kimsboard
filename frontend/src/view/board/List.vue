<template>
  <section id="boardList">
    <BoardNav @page="getBoardNav" />
    <router-view />
    <button type="button" v-if="isWrite" @click="showWrite = !showWrite">
      글 작성하기
    </button>
    <form v-if="showWrite" @submit.prevent="setBoardUpload">
      <div class="board_title">
        <label for="board_title">제목</label>
        <input type="text" id="board_title" v-model="boardTitle" />
      </div>
      <div class="board_content">
        <label for="board_content">내용</label>
        <textarea id="board_content" v-model="boardContent" />
      </div>
      <button type="submit">작성하기</button>
    </form>
    <div>
      <h1>게시글 {{ boardList.length }}개</h1>
      <ul>
        <li v-for="(list, index) in boardList" :key="'list' + index">
          {{ list }}
        </li>
      </ul>
    </div>
  </section>
</template>

<script>
import axios from "axios";
export default {
  name: "BoardList",
  data() {
    return {
      page: "",
      boardList: [],
      boardTitle: null,
      boardContent: null,
      showWrite: false,
    };
  },
  updated() {
    console.log(localStorage.getItem("userLoginToken"));
  },
  computed: {
    isWrite() {
      const token = localStorage.getItem("userLoginToken");
      if (token === null) {
        return false;
      } else {
        return true;
      }
    },
  },
  mounted() {
    this.getBoardList();
  },
  methods: {
    getBoardNav(val) {
      this.page = val;
    },
    getBoardList() {
      axios.get("/v1/board/" + this.page + "/posts").then(({ data }) => {
        if (data.code === 0) {
          this.boardList = data.list;
        }
      });
    },
    setBoardUpload() {
      const token = localStorage.getItem("userLoginToken");
      axios
        .post("/v1/board/" + this.page + "/post", {
          boardName: this.page,
          writer: "test",
          title: this.boardTitle,
          content: this.boardContent,
          headers: {
            Authorization: token,
          },
        })
        .then(({ data }) => {
          console.log(data);
        })
        .catch((error) => {
          console.log(error);
        });
    },
  },
};
</script>

<style lang="scss" scoped></style>
