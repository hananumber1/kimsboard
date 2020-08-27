<template>
  <div id="board_detail">
    {{postDetail}}
  </div>
</template>

<script>
import axios from "axios";
export default {
  name: "BoardDetail",
  data() {
    return {
      info:null,
      postDetail:null
    }
  },
   created () {
    this.info = JSON.parse(localStorage.getItem("saveDetail"));
    this.getBoardDetail();
  },
  methods: {
    getBoardDetail() {
      axios.get("/api/v1/board/post/" + this.info.id).then(({ data }) => {
        if(data.code ===0){
          this.postDetail = data.data;
        
        } else {
          this.postDetail = '불러오는데 실패하였습니다.'
        }
      });
      
    }
  },
};
</script>

<style lang="scss" scoped></style>
