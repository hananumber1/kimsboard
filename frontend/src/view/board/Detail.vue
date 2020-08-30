<template>
  <div id="board_detail">
    <h2>{{ category }}</h2>
    <p>{{ postDetail }}</p>
    <button type="button" @click="putBoardDetail()" v-if="userToken !==null">수정하기</button>
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
  },
  created() {
    this.userToken = this.$store.state.userToken;
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
            content: this.postDetail.content,
            title: this.postDetail.title,
            writer: this.postDetail.writer,
          },
          {
            headers : {
            'X-AUTH-TOKEN': this.userToken,
            }
          }
        )
        .then(({ data }) => {
          // console.log(data);
          if(data.code===-1002){
            alert(data.msg)
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
