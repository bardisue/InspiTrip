<script setup>
import { computed, ref } from "vue";
import { useReviewStore } from "@/stores/review";
import StarRating from "vue-star-rating";
import { useMemberStore } from "@/stores/member";
import { useMarkerStore } from "@/stores/marker";

const reviewStore = useReviewStore();
const member = useMemberStore();
const marker = useMarkerStore();

// 이미지 파일 리스트를 저장할 ref
const inputImageList = ref([]);

const resetableRating = ref(0);

const review = ref({
  no: "",
  ano: "",
  mid: member.member.id,
  content: "",
  grade: 0,
  write_date: "",
});

const setRating = (rating) => {
  console.log(rating);
  review.value.grade = rating;
};

const previewImageList = ref([]);

// input element에서 파일을 선택했을 때 실행되는 함수
const handleFileChange = (event) => {
  // 기존 이미지 리스트를 비우고
  inputImageList.value = [];
  previewImageList.value = [];
  // 선택한 모든 파일을 순회
  for (let file of event.target.files) {
    // FileReader API를 사용하여 파일을 읽기
    const reader = new FileReader();
    reader.onload = (e) => {
      // 읽기 완료 후, 이미지 URL을 inputImageList에 추가
      inputImageList.value.push(file);
      previewImageList.value.push(e.target.result);
    };
    reader.readAsDataURL(file); // 데이터 URL 형태로 파일 읽기
  }
};

const arrayLength = computed(() => previewImageList.value.length);

const insertReview = async () => {
  review.value.grade = resetableRating.value;
  console.log(resetableRating.value);
  let resetReview = {
    no: "",
    ano: "",
    mid: member.member.id,
    content: "",
    grade: 0,
    write_date: "",
  };
  reviewStore.inputImageList = inputImageList.value;
  reviewStore.review = review.value;
  console.log(reviewStore.review);
  const rno = await reviewStore.insertReview();
  console.log(inputImageList.value);
  for (let i = 0; i < inputImageList.value.length; i++) {
    const image_name = await reviewStore.upload_image(inputImageList.value[i]);
    await reviewStore.insertReviewImage(rno, image_name);
  }
  reviewStore.getReviewListByAno(marker.selectedMarker.contentId);
  review.value = resetReview;
  resetableRating.value = 0;
  reviewStore.getAvg();
  console.log(resetableRating.value);
};
</script>

<template>
  <div class="review-component container mt-3">
    <div class="row">
      <div class="col">
        <h2>리뷰</h2>
        <div class="card mb-3" v-if="member.isLogin">
          <div class="card-body">
            <div class="mb-3">
              <star-rating
                v-model:rating="resetableRating"
                active-color="#FF9600"
                v-bind:star-size="30"
              ></star-rating>
            </div>
            <!-- 이미지 파일만 선택 가능하며, 여러 파일 업로드 허용 -->
            <div class="mb-3">
              <input type="file" accept="image/*" multiple @change="handleFileChange" />
            </div>
            <!-- 이미지가 하나라도 있으면 이미지 표시 -->
            <template v-if="arrayLength > 0">
              <div class="mb-3">
                <template v-for="(imageSrc, index) in previewImageList" :key="index">
                  <img
                    :src="imageSrc"
                    class="rounded"
                    style="width: 100px; height: 100px; margin-right: 10px"
                  />
                </template>
              </div>
            </template>

            <!-- textarea를 card-body의 크기에 맞춰서 전체 폭을 사용하도록 수정 -->
            <div class="mb-3">
              <textarea
                cols="30"
                rows="5"
                style="width: 100%"
                placeholder="리뷰 내용을 작성해주세요."
                v-model="review.content"
              ></textarea>
            </div>
            <div class="d-flex justify-content-end">
              <button @click="insertReview">작성하기</button>
            </div>
          </div>
        </div>
      </div>
    </div>
    <!-- Modal Component -->
  </div>
</template>

<style scoped></style>
