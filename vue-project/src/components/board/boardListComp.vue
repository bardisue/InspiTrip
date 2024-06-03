<script setup>
import { computed, onMounted, ref } from "vue";
import { RouterLink, useRouter } from "vue-router";
import { useBoardStore } from "@/stores/board";
import boardListItemComp from "./boardListItemComp.vue";
import { useMemberStore } from "@/stores/member";

const router = useRouter();
const boardStore = useBoardStore();
const memberStore = useMemberStore();

onMounted(() => {
  boardStore.getBoardList();
  // console.log(boardStore.boardList);
  // console.log(boardStore.page);

  console.log("작동함");
});

const page = computed(() => {
  let pNumber = [];
  if (boardStore.page.endPage != null) {
    for (
      let p = Math.floor((boardStore.page.endPage - 1) / 10) * 10 + 1;
      p <= boardStore.page.endPage;
      p++
    ) {
      pNumber.push(p);
    }
  } else {
    pNumber.push(1);
  }

  return pNumber;
});

const boardInsert = computed(() => {
  router.replace({ name: "board-insert" });
});

const boardMovePage = function (page) {
  boardStore.page.curPage = page;
  boardStore.getBoardList();
};

const previous = computed(() => {
  if (boardStore.page.startPage - 10 > 0) {
    boardStore.page.curPage = boardStore.page.startPage - 10;
    boardStore.getBoardList();
  }
});

const next = computed(() => {
  if (boardStore.page.endPage + 1 <= boardStore.page.totalPage) {
    boardStore.page.curPage = boardStore.page.endPage + 1;
    boardStore.getBoardList();
  }
});

// 유저의 로그인 정보를 확인
const memberCheck = computed(() => {
  if (memberStore.isLogin && memberStore.member.id == "ssafy") {
    return true;
  }
  return false;
});
</script>

<template>
  <div>
    <div class="container mt-3">
      <h2>InspiTrip</h2>
      <table class="table table-hover">
        <thead>
          <tr>
            <th>No</th>
            <th>제목</th>
            <th>작성자</th>
            <th>조회수</th>
            <th>작성일</th>
          </tr>
        </thead>
        <tbody>
          <boardListItemComp
            v-for="board in boardStore.boardList"
            :key="board.no"
            :board-detail="board"
          />
        </tbody>
      </table>
      <!-- Center-aligned -->
      <ul class="pagination justify-content-center" style="margin: 20px 0">
        <li class="page-item"><a class="page-link" href="#" @click="previous">Previous</a></li>

        <li class="page-item" v-for="pNumber in page" :key="pNumber">
          <a class="page-link" href="#" @click="boardMovePage(pNumber)">{{ pNumber }}</a>
        </li>
        <li class="page-item"><a class="page-link" href="#" @click="next">Next</a></li>
      </ul>
      <template v-if="memberCheck == true">
        <button type="button" class="btn btn-outline-primary" @click="boardInsert">글쓰기</button>
      </template>
    </div>
  </div>
</template>

<style scoped></style>
