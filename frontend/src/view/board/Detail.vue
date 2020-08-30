<template>
  <div id="board_detail">
    <div class="w-full lg:max-w-full lg:flex">
      <div
        class="w-full border-r border-b border-l border-gray-400 lg:border-t lg:border-gray-400 bg-white rounded-b lg:rounded-l lg:rounded-r p-4 flex flex-col justify-between leading-normal"
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
            <p class="text-gray-900 leading-none">{{postDetail.writer}}</p>
            <p class="text-gray-600">{{ postDetail.user.createdAt }}</p>
          </div>
        </div>
      </div>
    </div>
    <button type="button" @click="putBoardDetail()" v-if="userToken !== null">
      수정하기
    </button>
  </div>
</template>

<script>
import { mapState } from "vuex";
import axios from "axios";
export default {
  name: "BoardDetail",
  data() {
    return {
      category: null,
      postId: null,
      postDetail: null,
      userToken: null,
    };
  },
  watch: {
    "$store.state.userToken"() {
      this.userToken = this.$store.state.userToken;
    },
    "$store.state.detailId"() {
      this.postId = this.$store.state.detailId;
    },
  },
  mounted() {
    this.userToken = this.$store.state.userToken;
    this.postId = this.$store.state.detailId;
    this.getBoardDetail();
  },
  methods: {
    getBoardDetail() {
      // 게시글 정보 가져오기
      axios.get("/api/v1/board/post/" + this.postId).then(({ data }) => {
        if (data.code === 0) {
          console.log(data.data);
          this.postDetail = data.data;
        } else {
          this.postDetail = "불러오는데 실패하였습니다.";
        }
      });
    },
    putBoardDetail() {
      // 게시글 수정하기
      axios
        .put(
          "/api/v1/board/post/" + this.postId,
          {
            content: this.postDetail.content,
            title: this.postDetail.title,
            writer: this.postDetail.writer,
          },
          {
            headers: {
              "X-AUTH-TOKEN": this.userToken,
            },
          }
        )
        .then(({ data }) => {
          // console.log(data);
          if (data.code === -1002) {
            alert(data.msg);
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
