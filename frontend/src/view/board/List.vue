<template>
  <section id="boardList">
    <BoardNav @page="getBoardNav" />
    <button type="button" v-if="isWrite" @click="showWrite = !showWrite">
      글 작성하기
    </button>
    <form v-if="showWrite" @submit="setBoardUpload" class="board_form">
      <div class="board_form_title">
        <label for="board_form_title">제목</label>
        <input type="text" id="board_form_title" v-model="boardTitle" />
      </div>
      <div class="board_form_content">
        <label for="board_form_content">내용</label>
        <textarea id="board_form_content" v-model="boardContent" />
      </div>
      <button type="submit">작성하기</button>
    </form>
    <div class="board_list">
      <h1>
        {{ page === "notice" ? "공지사항" : "자유게시판" }} <br>
        게시글 {{ boardList.length }}개
      </h1>
      <p>글번호 / 제목 / 내용 / 작성일</p>
      <ul>
        <li v-for="(list, index) in boardList" :key="'list' + index" @click="goToDetail(list.postId)">
          <router-link :to="{ name: 'BoardDetail',params: { name: page, id: list.postId } }">
          {{ index + 1 }} / {{ list.title }} / {{ list.content }} /{{list.createdAt}}
          </router-link>
        </li>
      </ul>
    </div>
  </section>
</template>

<script>
import { mapState } from "vuex";
import axios from "axios";
export default {
  name: "BoardList",
  data() {
    return {
      page: "notice",
      boardList: [],
      boardTitle: null,
      boardContent: null,
      showWrite: false,
    };
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
  watch: {
    page(newValue, oldValue) {
      axios.get("/api/v1/board/" + this.page + "/posts").then(({ data }) => {
        if (data.code === 0) {
          this.boardList = data.list;
        }
      });
    },
  },
  methods: {
    getBoardNav(val) {
      this.page = val;
    },
    getBoardList() {
      axios.get("/api/v1/board/" + this.page + "/posts").then(({ data }) => {
        if (data.code === 0) {
          this.boardList = data.list;
        }
      });
    },
    setBoardUpload() {
      const token = localStorage.getItem("userLoginToken");
      console.log(this.page);
      axios
        .post(
          "/api/v1/board/" + this.page + "/post",
          {
            boardName: this.page,
            title: this.boardTitle,
            content: this.boardContent,
          },
          {
            headers: {
              "X-AUTH-TOKEN": token,
            },
          }
        )
        .then(({ data }) => {
          console.log(data);
          this.$forceUpdate();
          // location.reload;
        })
        .catch((error) => {
          console.log(error);
        });
    },
    goToDetail(pageId){
      const value = {
        page:this.page,
        id:pageId
      }
      this.$store.commit('saveDetail', {value});
      localStorage.setItem("saveDetail", JSON.stringify(value));

    }
  },
};
</script>

<style lang="scss" scoped>
.board_list {
  ul {
    padding: 0;
    li {
      list-style: none;
    }
  }
}
</style>
