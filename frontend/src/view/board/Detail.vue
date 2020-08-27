<template>
  <div id="board_detail">
    {{info}}
  </div>
</template>

<script>
export default {
  name: "BoardDetail",
  data() {
    return {
      info:null
    }
  },
  methods: {
    getBoardDetail(id) {
      axios.get("/api/v1/board/post/" + id).then(({ data }) => {
        console.log(data)
      });
      
    }
  },
};
</script>

<style lang="scss" scoped></style>
