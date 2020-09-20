<template>
  <section id="boardList">
    <BoardNav @page="getBoardNav" />
    <div class="flex flex-row-reverse">
      <!-- 글 작성하기 버튼 -->
      <button
        class="bg-blue-500 hover:bg-blue-700 text-white text-sm px-2 py-1 rounded float-right"
        v-if="isWrite"
        @click="showWrite = !showWrite"
      >
        글 작성하기
      </button>
    </div>
    <!-- 작성하기 폼 -->
    <div class="w-full mt-4" v-if="showWrite">
      <form
        class="bg-white border rounded px-8 pt-6 pb-8 mb-4"
        @submit="setBoardUpload"
      >
        <p class="text-base font-bold text-gray-700 mb-3">게시글 작성하기</p>
        <div class="mb-4">
          <label
            class="block text-gray-700 text-sm font-bold mb-2"
            for="board_form_title"
          >
            제목
          </label>
          <input
            class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
            id="board_form_title"
            type="text"
            placeholder="제목"
            v-model="boardTitle"
          />
        </div>
        <div class="mb-4">
          <label
            class="block text-gray-700 text-sm font-bold mb-2"
            for="board_form_content"
          >
            내용
          </label>
          <textarea
            class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
            id="board_form_content"
            type="text"
            placeholder="내용"
            v-model="boardContent"
          />
        </div>
        <div class="flex items-center justify-between">
          <button
            class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline"
            type="submit"
          >
            작성하기
          </button>
        </div>
      </form>
    </div>
    <!-- 게시글 리스트 -->
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
      axios.get("/v1/board/" + this.page + "/posts").then(({ data }) => {
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
      axios.get("/v1/board/" + this.page + "/posts").then(({ data }) => {
        if (data.code === 0) {
          this.boardList = data.list;
        }
      });
    },
    setBoardUpload() {
      axios
        .post(
          "/v1/board/" + this.page + "/post",
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
