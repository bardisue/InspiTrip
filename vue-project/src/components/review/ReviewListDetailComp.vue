<script setup>
import { computed, onMounted, ref, watch } from "vue";
import { useReviewStore } from "@/stores/review";
import StarRating from "vue-star-rating";
import axios from "axios";

const reviewStore = useReviewStore();
const images = ref([]);
const props = defineProps({
  reviewinfo: {
    no: Number,
    ano: Number,
    mid: String,
    content: String,
    writeDate: String,
    grade: Number,
  },
});

onMounted(async () => {
  images.value = await reviewStore.getImageList(props.reviewinfo.no);
  console.log(images.value);
});

watch(
  () => reviewStore.reviewList,
  async (newList, oldList) => {
    // 리스트가 변경되었을 때 이미지 갱신 로직 수행
    if (newList.length !== oldList.length) {
      images.value = await reviewStore.getImageList(props.reviewinfo.no);
    }
  }
);

const rating = ref(props.reviewinfo.grade);

// 이미지 파일 리스트를 저장할 ref
const inputImageList = ref([]);
const previewImageList = ref([]);

const arrayLength = computed(() => images.value.length);

const showPopup = ref(false);
const url = ref("");

const openPop = (src) => {
  showPopup.value = true;
  url.value = src;
};
</script>

<template>
  <div v-if="showPopup" class="popup">
    <div class="popup-content">
      <img
        :src="url"
        class="rounded"
        style="width: 100px; height: 100px; margin-right: 10px"
        @click="openPop"
      />
      <button @click="showPopup = false">닫기</button>
    </div>
  </div>

  <div class="review-component container mt-3">
    <div class="row">
      <div class="col">
        <div class="card mb-3">
          <div class="card-body">
            <div class="mb-3 d-flex justify-content-between">
              <star-rating
                v-model:rating="rating"
                active-color="#FF9600"
                v-bind:star-size="20"
                :read-only="true"
              ></star-rating>
              {{ props.reviewinfo.writeDate }}
            </div>
            <!-- 이미지가 하나라도 있으면 이미지 표시 -->
            <template v-if="arrayLength > 0">
              <div class="mb-3">
                <template v-for="(image, index) in images" :key="index">
                  <img
                    :src="`http://localhost:3000/review/${image.url}`"
                    class="rounded"
                    style="width: 100px; height: 100px; margin-right: 10px"
                    @click="openPop(`http://localhost:3000/review/${image.url}`)"
                  />
                </template>
              </div>
            </template>
            <!-- textarea를 card-body의 크기에 맞춰서 전체 폭을 사용하도록 수정 -->
            <div>
              <textarea
                cols="30"
                rows="5"
                style="width: 100%; resize: none"
                v-model="props.reviewinfo.content"
                disabled
              ></textarea>
            </div>
          </div>
        </div>
      </div>
    </div>
    <!-- Modal Component -->
  </div>
</template>

<style>
.popup {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 10;
}

.popup-content {
  background-color: white;
  padding: 20px;
  border-radius: 5px;
}
</style>
