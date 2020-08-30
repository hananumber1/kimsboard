<template>
  <section id="boardList">
    <BoardNav @page="getBoardNav" />
    <div class="flex flex-row-reverse">
    <button
      class="bg-blue-500 hover:bg-blue-700 text-white text-sm px-2 rounded float-right"
      v-if="isWrite"
      @click="showWrite = !showWrite"
    >
      글 작성하기
    </button>
    </div>

    <form v-if="showWrite" @submit="setBoardUpload" class="board_form w-full">
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
      <table class="table-fixed">
        <thead>
          <tr>
            <th class="w-1/8 px-3 py-2">글번호</th>
            <th class="w-1/4 px-3 py-2">제목</th>
            <th class="w-1/6 px-3 py-2">내용</th>
            <th class="w-1/8 px-3 py-2">작성일</th>
          </tr>
        </thead>
        <tbody>
          <tr
            v-for="(list, index) in boardList"
            :key="'list' + index"
            @click="goToDetail(list.postId)"
            :class="{ 'bg-gray-100': index % 2 === 0 }"
          >
            <td class="border px-4 py-2">{{ index + 1 }}</td>
            <td class="border px-4 py-2">{{ list.title }}</td>
            <td class="border px-4 py-2">{{ list.content }}</td>
            <td class="border px-4 py-2">{{ list.createdAt }}</td>
          </tr>
        </tbody>
      </table>
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
      userToken: null,
    };
  },
  computed: {
    isWrite() {
      if (this.userToken === null) {
        return false;
      } else {
        return true;
      }
    },
  },
  mounted() {
    this.getBoardList();
    this.userToken = this.$store.state.userToken;
  },
  watch: {
    page(newValue, oldValue) {
      axios.get("/api/v1/board/" + this.page + "/posts").then(({ data }) => {
        if (data.code === 0) {
          this.boardList = data.list;
        }
      });
    },
    "$store.state.userToken"() {
      this.userToken = this.$store.state.userToken;
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
              "X-AUTH-TOKEN": this.userToken,
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
    goToDetail(pageId) {
      const value = {
        page: this.page,
        id: pageId,
      };
      this.$store.commit("saveDetail", value);
      this.$router.push({
        name: "BoardDetail",
        params: { name: this.page, id: pageId },
      });
    },
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
