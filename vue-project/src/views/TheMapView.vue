<script setup>
import { usesidoGugunStore } from "@/stores/sidoGugun.js";
import { useMarkerStore } from "@/stores/marker.js";
import MapCardComp from "@/components/map/MapCardComp.vue";
import MapComp from "@/components/map/MapComp.vue";
import { ref, onMounted, watch, computed } from "vue";
import MapInfluencerCompVue from "@/components/map/MapInfluencerComp.vue";
import ReviewListComp from "@/components/review/ReviewListComp.vue";
import router from "@/router";
import ReviewInsertComp from "@/components/review/ReviewInsertComp.vue";
import { useMapStore } from "@/stores/map";
import { onBeforeRouteLeave } from "vue-router";
import { useReviewStore } from "@/stores/review";
import StarRating from "vue-star-rating";

const sidoGugun = usesidoGugunStore();

const marker = useMarkerStore();
const map = useMapStore();
const review = useReviewStore();

const loading = ref();
const div_state = ref(true);
const isHovered_inf = ref(false);
const isHovered_user = ref(false);
const normalColor = "#EAEAEA"; // 일반 상태의 색상 코드
const hoveredColor = "#D8D8D8"; // 호버 상태의 색상 코드

onBeforeRouteLeave((to, from) => {
  const backdrop = document.querySelector(".modal-backdrop");
  if (backdrop) {
    backdrop.remove();
  }
});

const handleImageError = (item) => {
  if (item.image == "") {
    event.target.src = "/src/assets/noImage.png";
  } else {
    event.target.src = item.image;
  }
  // 필요하다면 추가적으로 이미지 교체 실패시 다른 대체 이미지를 설정할 수 있습니다.
};

watch(
  () => marker.selectedMarker,
  () => {
    console.log(marker.selectedMarker);
  }
);

onMounted(() => {
  sidoGugun.getSido();
});

const stateChage = (data) => {
  console.log("stateChage >> " + data);
  loading.value = data;
};

const setAvgRating = computed(() => {
  return review.avg;
});
</script>

<template>
  <div class="container-fluid">
    <div class="row" width="100%">
      <div class="col-5">
        <div class="d-flex gap-2 justify-content-center">
          <router-link :to="{ name: 'map-normal' }">
            <button
              :class="
                map.isCard
                  ? `btn btn-primary d-inline-flex align-items-center`
                  : `btn btn-secondary d-inline-flex align-items-center`
              "
              type="button"
            >
              일반검색
            </button></router-link
          >
          <router-link :to="{ name: 'map-influencer' }">
            <button
              :class="
                map.isCard
                  ? `btn btn-secondary d-inline-flex align-items-center`
                  : `btn btn-primary d-inline-flex align-items-center`
              "
              type="button"
            >
              인플루언서 검색
            </button></router-link
          >
        </div>
        <MapCardComp></MapCardComp>
      </div>
      <div class="col-7">
        <MapComp></MapComp>
      </div>
    </div>
  </div>
  <!-- Modal -->
  <div
    class="modal fade"
    id="exampleModal"
    tabindex="-1"
    aria-labelledby="exampleModalLabel"
    aria-hidden="true"
  >
    <div class="modal-dialog modal-lg">
      <div class="modal-content">
        <div class="modal-header">
          <h1 class="modal-title fs-5" id="exampleModalLabel">세부 정보</h1>
          <button
            type="button"
            class="btn-close"
            data-bs-dismiss="modal"
            aria-label="Close"
          ></button>
        </div>

        <div class="container">
          <div class="modal-body d-flex justify-content-between">
            {{ marker.selectedMarker.title }}
            <star-rating
              v-bind:increment="0.01"
              v-model:rating="setAvgRating"
              active-color="#FF9600"
              v-bind:star-size="25"
              read-only=" true"
            ></star-rating>
          </div>
          <div class="row">
            <div class="col-sm-1"></div>
            <div class="col-sm-5">
              <img
                :src="`http://localhost:3000/attr/${marker.selectedMarker.contentId}/first_image.webp`"
                @error="handleImageError(marker.selectedMarker)"
                alt="이미지가없습니다"
                style="height: 300px; max-width: 300px"
              />
            </div>
            <div class="col-sm-5 text-center scrollable-div overflow-auto border p-3">
              {{ marker.selectedMarker.overview }}
            </div>
            <div class="col-sm-1"></div>
          </div>
        </div>

        <div class="container mt-4">
          <div class="row">
            <div class="col-2"></div>
            <div class="col-4" @click="marker.div_status = true">
              <a href="#" style="text-decoration-line: none">
                <div
                  class="card align-items-center"
                  :style="{ backgroundColor: isHovered_inf ? hoveredColor : normalColor }"
                  @mouseover="isHovered_inf = true"
                  @mouseleave="isHovered_inf = false"
                >
                  인플루언서
                </div>
              </a>
            </div>
            <div class="col-4" @click="marker.div_status = false">
              <a href="#" style="text-decoration-line: none">
                <div
                  class="card align-items-center"
                  :style="{ backgroundColor: isHovered_user ? hoveredColor : normalColor }"
                  @mouseover="isHovered_user = true"
                  @mouseleave="isHovered_user = false"
                >
                  유저리뷰
                </div>
              </a>
            </div>
            <div class="col-2"></div>
          </div>
        </div>
        <template v-if="marker.div_status == true">
          <div
            class="d-flex flex-column flex-md-row p-4 gap-4 py-md-5 align-items-center justify-content-center"
          >
            <template v-if="marker.markerInfluencer.length != 0">
              <MapInfluencerCompVue
                v-for="item in marker.markerInfluencer"
                :key="item.contentId"
                :item="item"
              />
            </template>
            <template v-if="marker.markerInfluencer.length == 0">
              <div class="align-centent-center">컨텐츠가 없습니다</div>
            </template>
          </div>
        </template>
        <template v-if="marker.div_status == false">
          <div
            class="d-flex flex-column flex-md-row p-4 gap-4 py-md-5 align-items-center justify-content-center"
          >
            <ReviewInsertComp @state="stateChage" style="width: 70%"></ReviewInsertComp>
            <review-list-comp style="width: 70%"></review-list-comp>
            <template v-if="review.reviewList.length == 0">
              <div style="width: 27%">작성된 리뷰가 없습니다</div>
            </template>
          </div>
        </template>

        <div class="modal-footer">
          <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.d-flex {
  display: flex;
  flex-wrap: wrap; /* 강제로 각 요소들을 개별 행에 표시 */
}

.flex-md-row {
  flex-direction: column; /* 중간 크기 스크린 이상일 때 세로로 나열 */
}
.scrollable-div {
  height: 300px; /* 원하는 높이로 설정 */
}
</style>
