<template>
  <div id="board_detail">
    <div class="max-w-sm w-full lg:max-w-full lg:flex">
      <div
        class="border-r border-b border-l border-gray-400 lg:border-t lg:border-gray-400 bg-white rounded-b lg:rounded-l lg:rounded-r p-4 flex flex-col justify-between leading-normal"
      >
        <div class="mb-8">
          <div class="text-gray-900 font-bold text-xl mb-2">
            {{postDetail}}
          </div>
          <p class="text-gray-700 text-base">
            Lorem ipsum dolor sit amet, consectetur adipisicing elit.
            Voluptatibus quia, nulla! Maiores et perferendis eaque,
            exercitationem praesentium nihil.
          </p>
        </div>
        <div class="flex items-center">
          <img
            class="w-10 h-10 rounded-full mr-4"
            src="/img/jonathan.jpg"
            alt="Avatar of Jonathan Reinink"
          />
          <div class="text-sm">
            <p class="text-gray-900 leading-none">Jonathan Reinink</p>
            <p class="text-gray-600">Aug 18</p>
          </div>
        </div>
      </div>
    </div>

    <p>{{ postDetail }}</p>
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
      postDetail: null,
      userToken:null
    };
  },
  mounted () {
    console.log(this.$store.state.detailId.userToken)
    this.userToken = this.$store.state.detailId.userToken;

  },
  methods: {
    getBoardDetail() {
      // 게시글 정보 가져오기
      axios.get("/api/v1/board/post/" + this.$store.state.detailId).then(({ data }) => {
        if (data.code === 0) {
          console.log(data.data)
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
            'X-AUTH-TOKEN': this.$store.state.detailId.userToken,
            }
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
