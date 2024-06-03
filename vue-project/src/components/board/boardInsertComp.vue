<script setup>
import { ref, onMounted, onBeforeUnmount, computed } from "vue";
import { RouterLink, useRouter } from "vue-router";
import { useBoardStore } from "@/stores/board";
import { useMemberStore } from "@/stores/member";
import { QuillEditor } from "@vueup/vue-quill";
import BlotFormatter from "quill-blot-formatter";
import ImageUploader from "quill-image-uploader";
import { ImageDrop } from "quill-image-drop-module";
import imageCompress from "quill-image-compress";
import axios from "axios";

const router = useRouter();
const boardStore = useBoardStore();
const memberStore = useMemberStore();
const boardContent = ref("");
const fileList = ref([]);

const board = ref({
  mid: "ssafy",
  title: "",
  content: "",
});

const boardInsert = async () => {
  console.log(boardContent.value);
  console.log("동작");
  const parser = new DOMParser();
  const doc = parser.parseFromString(boardContent.value, "text/html");
  const images = doc.querySelectorAll("img"); // 모든 img 태그를 찾습니다.

  // img 태그의 src 속성을 비동기적으로 수정
  for (let i = 0; i < images.length; i++) {
    if (!images[i].src.includes("http://localhost:3000/")) {
      const newSrc = await boardStore.upload_image(fileList.value[i]);
      images[i].src = newSrc;
    }
  }

  // 수정된 HTML을 문자열로 추출
  const modifiedHtml = doc.body.innerHTML; // body 내의 HTML을 가져옵니다.

  // 결과 확인
  console.log(modifiedHtml); // 수정된 HTML 문자열을 콘솔에 출력
  board.value.content = modifiedHtml;
  boardStore.board = board.value;
  boardStore.boardInsert(memberStore.member.id);
};

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
    <input type="email" class="form-control" v-model="board.title" />
    <label for="comment">내용</label>
    <QuillEditor
      theme="snow"
      toolbar="full"
      :modules="modules"
      :options="globalOptions"
      v-model:content="boardContent"
      content-type="html"
      style="min-height: 300px"
    />
    <button type="button" class="btn btn-outline-primary mt-5" @click="boardInsert">
      등록하기
    </button>
  </div>
</template>

<style scoped></style>
