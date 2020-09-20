<template>
  <div id="board_detail">
    <div v-if="loading">
      <!-- 게시글 수정하기 -->
      <div class="w-full lg:max-w-full lg:flex" v-if="showModify">
        <div class="w-full mt-4">
          <form
            class="bg-white border rounded px-8 pt-6 pb-8 mb-4"
            @submit="putBoardModify"
          >
            <p class="text-base font-bold text-gray-700 mb-3">
              게시글 작성하기
            </p>
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
            <div class="flex ">
              <button
                class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline"
                type="submit"
              >
                작성하기
              </button>
              <button
                class="bg-red-500 hover:bg-red-700 text-white font-bold py-2 px-4 ml-2 rounded focus:outline-none focus:shadow-outline"
                type="button"
                @click="showModify = false"
              >
                취소하기
              </button>
            </div>
          </form>
        </div>
      </div>
      <!-- 게시글 디테일 -->
      <div class="w-full lg:max-w-full lg:flex" v-else>
        <div
          class="w-full border-r border-b border-l border-gray-400 lg:border-t lg:border-gray-400 bg-white rounded-b lg:rounded-l lg:rounded-r p-4 flex flex-col justify-between leading-normal mb-4"
        >
          <div class="mb-8">
            <div class="text-gray-900 font-bold text-xl mb-2">
              {{ postDetail.title }}
            </div>
            <p class="text-gray-700 text-base">
              {{ postDetail.content }}
            </p>
          </div>
          <div class="flex items-center">
            <img
              class="w-10 h-10 rounded-full mr-4"
              src="https://img.pngio.com/fileblank-avatarpng-georgian-civil-code-commentary-avatarpng-400_400.png"
              alt="Avatar of Jonathan Reinink"
            />
            <div class="text-sm">
              <p class="text-gray-900 leading-none">{{ postDetail.writer }}</p>
              <p class="text-gray-600">{{ postDetail.user.createdAt }}</p>
            </div>
          </div>
        </div>
      </div>
      <div class="flex " v-if="userToken !== null && !showModify">
        <button
          class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline"
          type="button"
          @click="showModify = true"
        >
          수정하기
        </button>
        <button
          class="bg-red-500 hover:bg-red-700 text-white font-bold py-2 px-4 ml-2 rounded focus:outline-none focus:shadow-outline"
          type="button"
          @click="delBoard"
        >
          삭제하기
        </button>
      </div>
      <form class="w-full mt-20" v-if="userToken !== null">
        <div class="flex items-center border-b border-teal-500 py-2">
          <input
            class="appearance-none bg-transparent border-none w-full text-gray-700 mr-3 py-1 px-2 leading-tight focus:outline-none"
            type="text"
            placeholder="댓글 내용을 입력해주세요"
            aria-label="Full name"
            v-model="writeReply"
          />
          <button
            class="flex-shrink-0 bg-teal-500 hover:bg-teal-700 border-teal-500 hover:border-teal-700 text-sm border-4 text-white py-1 px-2 rounded"
            type="button"
            @click="postReply()"
          >
            댓글 추가
          </button>
          <button
            class="flex-shrink-0 border-transparent border-4 text-teal-500 hover:text-teal-800 text-sm py-2 px-2 rounded border border-teal-500 hover:border-teal-500 ml-2"
            type="button"
            @click="writeReply = null"
          >
            취소
          </button>
        </div>
      </form>
      <!-- 댓글 리스트 -->
      <div class="board_list mt-10">
        <p class="text-base font-bold text-gray-700 mb-3">
          댓글
        </p>
        <table class="table-fixed w-full">
          <thead>
            <tr>
              <th class="w-1/6 px-3 py-2">글번호</th>
              <th class="w-1/8 px-3 py-2">내용</th>
              <th class="w-1/6 px-3 py-2">작성자</th>
              <th class="w-1/6 px-3 py-2">작성일</th>
            </tr>
          </thead>
          <tbody>
            <tr
              v-for="(list, index) in postDetail.replies"
              :key="'list' + index"
              :class="{ 'bg-gray-100': index % 2 === 0 }"
            >
              <td class="border px-4 py-2 text-center">{{ index + 1 }}</td>
              <td class="border px-4 py-2 text-center">
                {{ list.content }} 
                <button type="button" 
                v-if="userInfo !==null && userInfo.userId === list.replyer"
                @click="delReply(list.replyId)"
                class="float-right bg-blue-500 hover:bg-blue-700 text-white text-sm py-1 px-2 rounded focus:outline-none focus:shadow-outline">
                  삭제하기
                </button>
              </td>
              <td class="border px-4 py-2 text-center">{{ list.replyer }}</td>
              <td class="border px-4 py-2 text-center">{{ list.createdAt }}</td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
    <div v-else style="height:100vh;">
      <Spinner line-fg-color="#38b2ac" size="huge"/>
    </div>
  </div>
</template>

<script>
import Spinner from "vue-simple-spinner";
import { mapState } from "vuex";
import axios from "axios";
export default {
  name: "BoardDetail",
  components: {
    Spinner,
  },
  data() {
    return {
      category: null,
      postId: null,
      postDetail: null,
      userToken: null,
      showModify: false,
      boardTitle: null,
      boardContent: null,
      writeReply: null,
      loading: false,
      userInfo:null,
    };
  },
  watch: {
    "$store.state.userToken"() {
      this.userToken = this.$store.state.userToken;
    },
    "$store.state.userInfo"() {
      this.userInfo = this.$store.state.userInfo;
    },
    "$store.state.detailId"() {
      this.postId = this.$store.state.detailId;
    },
  },
  created() {
    this.userToken = this.$store.state.userToken;
    this.userInfo = this.$store.state.userInfo;
    this.postId = this.$store.state.detailId;
    this.getBoardDetail();
  },
  methods: {
    getBoardDetail() {
      // 게시글 정보 가져오기
      axios.get("/v1/board/post/" + this.postId).then(({ data }) => {
        if (data.code === 0) {
          // console.log(data.data);
          this.postDetail = data.data;
          this.boardTitle = this.postDetail.title;
          this.boardContent = this.postDetail.content;
          this.loading = true;
        } else {
          this.postDetail = "불러오는데 실패하였습니다.";
        }
      });
    },
    putBoardModify() {
      // 게시글 수정하기
      axios
        .put(
          "/v1/board/post/" + this.postId,
          {
            content: this.boardContent,
            title: this.boardTitle,
            writer: this.postDetail.writer,
          },
          {
            headers: {
              "X-AUTH-TOKEN": this.userToken,
            },
          }
        )
        .then(({ data }) => {
          console.log(data);
          if (data.code === -1002) {
            alert(data.msg);
          } else {
            this.$forceUpdate();
            this.postDetail = data.data;
            this.boardTitle = this.postDetail.title;
            this.boardContent = this.postDetail.content;
            alert("수정이 완료되었습니다.");
            this.showModify = false;
          }
        })
        .catch((error) => {
          console.log(error);
        });
    },
    delBoard() {
      // 게시글 삭제하기
      axios
        .delete("/v1/board/post/" + this.postId, {
          headers: {
            "X-AUTH-TOKEN": this.userToken,
          },
        })
        .then(({ data }) => {
          // console.log(data);
          if (data.code === 0) {
            alert("삭제가 완료되었습니다.리스트로 이동합니다.");
            this.$router.push({
              name: "BoardList",
            });
          }
        })
        .catch((error) => {
          console.log(error);
        });
    },
    postReply() {
      // 댓글 추가하가
      axios
        .post(
          "/v1/reply/" + this.postId,
          {
            content: this.writeReply,
          },
          {
            headers: {
              "X-AUTH-TOKEN": this.userToken,
            },
          }
        )
        .then(({ data }) => {
          // console.log(data);
          this.loading = false;
          if(data.code===0){
            alert("댓글이 추가되었습니다.");
            this.getBoardDetail();
            this.writeReply = null;
          }
        })
        .catch((error) => {
          console.log(error);
        });
    },
    delReply(postId) {
      console.log(postId)
      // 댓글 삭제하기
      axios
        .delete(
          "/v1/reply/" + postId,
          {
            headers: {
              "X-AUTH-TOKEN": this.userToken,
            },
          }
        )
        .then(({ data }) => {
          // console.log(data);
          this.loading = false;
          if(data.code===0){
            alert("댓글이 삭제되었습니다.");
            this.getBoardDetail();
          }
        })
        .catch((error) => {
          console.log(error);
        });
    },
  },
};
</script>

<style lang="scss" scoped></style>
