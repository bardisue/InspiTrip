<script setup>
import { onMounted, ref } from "vue";
import axios from "axios";
import { useReviewStore } from "@/stores/review";
import ReviewListDetailComp from "./ReviewListDetailComp.vue";
import { useMarkerStore } from "@/stores/marker";
import { onBeforeRouteLeave } from "vue-router";

const reviewStore = useReviewStore();
const marker = useMarkerStore();

onMounted(() => {
  const markerId = marker.selectedMarker.contentId;
  reviewStore.getReviewListByAno(markerId);
});
</script>

<template>
  <div calss="text-center scrollable-div overflow-auto" :key="reviewStore.reviewList">
    <ReviewListDetailComp
      v-for="(review, index) in reviewStore.reviewList"
      :key="index"
      :reviewinfo="review"
    ></ReviewListDetailComp>
  </div>
</template>

<style scoped></style>
