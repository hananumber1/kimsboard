<template>
  <nav id="boardNav">
    <ul>
      <li>
        <router-link :to="{ name: 'BoardList', params: { name: 'notice' } }"
          >공지사항</router-link
        >
      </li>
      <li>
        <router-link :to="{ name: 'BoardList', params: { name: 'free' } }"
          >자유게시판</router-link
        >
      </li>
    </ul>
  </nav>
</template>

<script>
export default {
  name: "BoardNav",
};
</script>

<style lang="scss" scoped></style>
