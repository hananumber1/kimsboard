<template>
  <div id="board_detail">
    <h2>{{ category }}</h2>
    <p>{{ postDetail }}</p>
    <button type="button" @click="putBoardDetail()">수정하기</button>
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
      postDetail: null,
      postId: null,
      userToken: null,
    };
  },
  watch: {
    "$store.state.detailId"() {
      this.postId = this.$store.state.detailId;
      this.getBoardDetail(this.postId);
    },
    "$store.state.detailCategory"() {
      this.category = this.$store.state.detailCategory;
    },
    "$store.state.saveUserToken"() {
      this.userToken = this.$store.state.saveUserToken;
    },
  },
  created() {
    if (this.category === null) {
    }
  },
  methods: {
    getBoardDetail() {
      // 게시글 정보 가져오기
      axios.get("/api/v1/board/post/" + this.postId).then(({ data }) => {
        if (data.code === 0) {
          this.postDetail = data.data;
        } else {
          this.postDetail = "불러오는데 실패하였습니다.";
        }
      });
    },
    putBoardDetail() {
      // 게시글 수정하기
      axios
        .put("/api/v1/board/post/" + this.postId, {
          params: {
            postId: this.postId,
            writer: this.postDetail.writer,
            title: this.postDetail.title,
            content: this.postDetail.content,
          },
          headers: {
            "X-AUTH-TOKEN": this.userToken,
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
