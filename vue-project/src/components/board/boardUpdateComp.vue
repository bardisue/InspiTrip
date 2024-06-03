<script setup>
import { computed, onMounted, ref } from "vue";
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

const fileList = ref([]);

onMounted(() => {
  boardStore.getBoard(route.params.no);
});

const onUpdate = async () => {
  console.log(boardStore.board.content);
  console.log("동작");
  const parser = new DOMParser();
  const doc = parser.parseFromString(boardStore.board.content, "text/html");
  const images = doc.querySelectorAll("img"); // 모든 img 태그를 찾습니다.

  // img 태그의 src 속성을 비동기적으로 수정
  let cnt = 0;
  for (let i = 0; i < images.length; i++) {
    if (!images[i].src.includes("http://localhost:3000/")) {
      const newSrc = await boardStore.upload_image(fileList.value[cnt]);
      images[i].src = newSrc;
      cnt++;
    }
  }

  // 수정된 HTML을 문자열로 추출
  const modifiedHtml = doc.body.innerHTML; // body 내의 HTML을 가져옵니다.

  // 결과 확인
  console.log(modifiedHtml); // 수정된 HTML 문자열을 콘솔에 출력
  boardStore.board.content = modifiedHtml;
  boardStore.boardUpdate();
};

const cancelUpdate = computed(() => {
  router.replace({ name: "board-detail", params: { no: route.params.no } });
});

const globalOptions = {
  // debug: "info",
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
  {
    name: "blotFormatter",
    module: BlotFormatter,
    options: {
      /* options */
    },
  },
];
</script>

<template>
  <div>
    <label for="email" class="form-label">제목</label>
    <input type="email" class="form-control" :value="boardStore.board.title" />
    <label for="comment">작성자</label>
    <input type="email" class="form-control" :value="boardStore.board.mid" disabled />
    <QuillEditor
      theme="snow"
      toolbar="full"
      :modules="modules"
      :options="globalOptions"
      v-model:content="boardStore.board.content"
      content-type="html"
      style="min-height: 300px"
    />
    <button type="button" class="btn btn-outline-primary" @click="onUpdate">확인</button>
    <button type="button" class="btn btn-outline-danger" @click="cancelUpdate">취소</button>
  </div>
</template>

<style scoped>
input:disabled {
  color: inherit; /* 일반 상태의 텍스트 색상을 상속받음 */
  background-color: inherit; /* 일반 상태의 배경 색상을 상속받음 */
}
</style>
