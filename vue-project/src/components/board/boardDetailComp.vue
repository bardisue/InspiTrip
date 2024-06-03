<script setup>
import { computed, onMounted, readonly, ref } from "vue";
import { RouterLink, useRouter, useRoute } from "vue-router";
import { useBoardStore } from "@/stores/board";
import { useMemberStore } from "@/stores/member";

import { QuillEditor } from "@vueup/vue-quill";
import BlotFormatter from "quill-blot-formatter";
import ImageUploader from "quill-image-uploader";
import { ImageDrop } from "quill-image-drop-module";
import imageCompress from "quill-image-compress";

const router = useRouter();
const route = useRoute();
const boardStore = useBoardStore();
const memberStore = useMemberStore();

onMounted(() => {
  boardStore.boardDetailReadCount(route.params.no);
  boardStore.boardDetail(route.params.no);
});

const boardUpdate = computed(() => {
  router.push({ name: "board-update", params: { no: route.params.no } });
});

const globalOptions = {
  // debug: "info",
  readOnly: true,
  theme: "snow",
};

const modules = [
  {
    name: "imageUploader",
    module: ImageUploader,
    options: {
      upload: (file) => {
        console.log(file);
        return new Promise(() => {
          fileList.value.push(file);
        });
      },
    },
  },
];

// 유저의 로그인 정보를 확인
const memberCheck = computed(() => {
  if (memberStore.isLogin) {
    if (memberStore.member.id == boardStore.board.mid) {
      return true;
    }
    return false;
  }
  return false;
});

const boardDelete = computed(() => {
  alert("삭제하시겠습니까?");
  boardStore.boardDelete(boardStore.board.no);
});
</script>

<template>
  <div>
    <label for="email" class="form-label">제목</label>
    <input type="email" class="form-control" :value="boardStore.board.title" disabled />
    <label for="comment">작성자</label>
    <input type="email" class="form-control" :value="boardStore.board.mid" disabled />
    <label for="comment">작성 시간</label>
    <input type="email" class="form-control" :value="boardStore.board.write_date" disabled />
    <label for="comment">조회수</label>
    <input type="email" class="form-control" :value="boardStore.board.read_count" disabled />
    <QuillEditor
      theme="snow"
      toolbar="full"
      :modules="modules"
      :options="globalOptions"
      v-model:content="boardStore.board.content"
      content-type="html"
      disabled
      style="min-height: 300px"
    />
    <template v-if="memberCheck == true">
      <button type="button" class="btn btn-outline-primary" @click="boardUpdate">수정하기</button>
      <button type="button" class="btn btn-outline-danger" @click="boardDelete">삭제하기</button>
    </template>
  </div>
</template>

<style scoped>
input:disabled {
  color: inherit; /* 일반 상태의 텍스트 색상을 상속받음 */
  background-color: inherit; /* 일반 상태의 배경 색상을 상속받음 */
}
</style>
