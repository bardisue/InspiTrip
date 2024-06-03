<script setup>
import { onMounted, ref, watch } from "vue";
import { usesidoGugunStore } from "@/stores/sidoGugun";
import { useMarkerStore } from "@/stores/marker";
import { useMapStore } from "@/stores/map";
import MapNoramlSearchComp from "./MapNoramlSearchComp.vue";
import { useInfluencerStore } from "@/stores/influencer";

const sidoGugun = usesidoGugunStore();
const marker = useMarkerStore();
const map = useMapStore();
const influencer = useInfluencerStore();

const selectedType = ref(0);
const selectedSido = ref("1");
const selectedGugun = ref("1");

onMounted(() => {
  map.isCard = true;
  marker.getMarkerByLatLong(marker.centerLat, marker.centerLng);
});

watch(
  selectedType,
  () => {
    console.log(selectedType.value + "selected");
    marker.selectedType = selectedType.value;
  },
  { immediate: true }
);

watch(
  selectedSido,
  () => {
    console.log(selectedSido.value);
    selectedGugun.value = 1;
    sidoGugun.getGugun(selectedSido.value);
  },
  { immediate: true }
);

const contentTypes = ref([
  {
    value: 0,
    contentType: "전체",
  },
  {
    value: 12,
    contentType: "관광지",
  },
  {
    value: 14,
    contentType: "문화시설",
  },
  {
    value: 15,
    contentType: "축제공연행사",
  },
  {
    value: 25,
    contentType: "여행코스",
  },
  {
    value: 28,
    contentType: "레포츠",
  },
  {
    value: 32,
    contentType: "숙박",
  },
  {
    value: 38,
    contentType: "쇼핑",
  },
  {
    value: 39,
    contentType: "음식점",
  },
]);

const searchSido = () => {
  marker.getMarkerBySido(selectedSido.value, selectedGugun.value);
};
</script>

<template>
  <div
    class="ps-4 d-flex flex-column justify-content-between bg-success bg-opacity-50"
    style="height: 150px"
  >
    <div class="row pt-4">
      <div class="col-5">
        <select
          id="sido"
          class="form-select"
          aria-label="Default select example"
          v-model="selectedSido"
        >
          <option v-for="sido in sidoGugun.sido" :key="sido.sidoCode" :value="sido.sidoCode">
            {{ sido.sidoName }}
          </option>
        </select>
      </div>
      <div class="col-5">
        <select
          id="sido"
          class="form-select"
          aria-label="Default select example"
          v-model="selectedGugun"
        >
          <option v-for="gugun in sidoGugun.gugun" :key="gugun.gunguCode" :value="gugun.gunguCode">
            {{ gugun.gunguName }}
          </option>
        </select>
      </div>
      <div class="col-2">
        <button @click="searchSido">검색</button>
      </div>
    </div>
    <div class="pb-4">
      <div
        class="btn-group"
        role="group"
        aria-label="Basic radio toggle button group"
        style="background-color: white"
      >
        <template v-for="content in contentTypes" :key="content.value">
          <input
            type="radio"
            class="btn-check"
            :id="content.contentType"
            :value="content.value"
            autocomplete="off"
            v-model="selectedType"
          />
          <label class="btn btn-outline-primary" :for="content.contentType"
            >{{ content.contentType }}
          </label>
        </template>
      </div>
    </div>
  </div>
</template>

<style scoped></style>
