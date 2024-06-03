<script setup>
import { useMapStore } from "@/stores/map";
import { onMounted, ref, watch } from "vue";
import InfluencerListComp from "./MapInfluencerListComp.vue";
import { useInfluencerStore } from "@/stores/influencer";
import { useMarkerStore } from "@/stores/marker";
import { onBeforeRouteLeave, useRoute, useRouter } from "vue-router";
import { Modal } from "bootstrap";
import { useMemberStore } from "@/stores/member";

const map = useMapStore();
const influencer = useInfluencerStore();
const marker = useMarkerStore();
const member = useMemberStore();
const router = useRouter();
const route = useRoute();

onBeforeRouteLeave((to, from) => {
  marker.markers = [];
  influencer.selectedInfluencer = {
    no: 0,
  };
});

onMounted(async () => {
  map.isCard = false;
  await influencer.getInfluencers();
  console.log(influencer.selectedInfluencer);
  if (influencer.selectedInfluencer.no !== 0) {
    await marker.getMarkerByInfluencer(influencer.selectedInfluencer.no);
    console.log(marker.markers);
    let myModal = new Modal(document.getElementById("exampleModal"));
    myModal.show();
  } else {
    marker.markers = [];
  }
});
const toggleChecked = ref(false);

const keyword = ref("");

const changeKeyword = (e) => {
  keyword.value = e.target.value;
  influencer.getInfluencersByWord(keyword.value);
};

watch(
  () => toggleChecked.value,
  () => {
    if (toggleChecked.value) {
      influencer.getInfluencersByFollow(member.member.id);
    } else {
      influencer.getInfluencers();
    }
  }
);
let compositionData = "";
</script>

<template>
  <div class="container bg-success bg-opacity-50" style="height: 150px">
    <div class="d-flex justify-content-between align-items-center p-3">
      <!-- 검색창 -->
      <input
        type="text"
        class="form-control w-50"
        :value="keyword"
        @input="changeKeyword"
        id="inputField"
        placeholder="Search"
      />

      <!-- 토글 버튼 -->
      <div class="form-check form-switch" v-if="member.isLogin">
        <input
          class="form-check-input"
          type="checkbox"
          id="flexSwitchCheckDefault"
          v-model="toggleChecked"
        />
        <label class="form-check-label" for="flexSwitchCheckDefault"
          >팔로우한 인플루언서만 보기</label
        >
      </div>
    </div>
    <div class="row scrollable-div overflow-auto bg-success bg-opacity-10" style="height: 80px">
      <InfluencerListComp
        v-for="item in influencer.influencerList"
        :key="item.no"
        :item="item"
        class="col-4"
      ></InfluencerListComp>
    </div>
  </div>
</template>

<style scoped></style>
