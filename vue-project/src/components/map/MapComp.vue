<script setup>
import { useMarkerStore } from "@/stores/marker";
import { onMounted, ref, watch, computed } from "vue";
import { KakaoMap, KakaoMapMarker } from "vue3-kakao-maps";
import { Modal } from "bootstrap";
import { useInfluencerStore } from "@/stores/influencer";
import { useMapStore } from "@/stores/map";

const marker = useMarkerStore();
const influencer = useInfluencerStore();

const map = ref();

const useMap = useMapStore();

const coordinate = {
  lat: 37.566826,
  lng: 126.9786567,
};

const latlng = ref({});

watch(
  () => latlng.value,
  () => {
    marker.getMarkerByLatLong(latlng.value.getLat(), latlng.value.getLng(), map.value.getLevel());
  },
  { deep: true }
);

onMounted(() => {});

watch(
  () => marker.centerLat,
  () => {
    console.log(marker.markers);
    console.log("??");
    map.value.panTo(new kakao.maps.LatLng(marker.centerLat, marker.centerLng));
  }
);

watch(
  () => influencer.selectedInfluencer,
  () => {
    console.log(influencer.selectedInfluencer);
  }
);

const onClickKakaoMapMarker = (item) => {
  marker.selectOne(item);
  let myModal = new Modal(document.getElementById("exampleModal"));
  myModal.show();
};

const markerss = computed(() => {
  console.log(marker.markers.length);

  let position = marker.markers.map(function (item) {
    // 마커를 배열 단위로 묶음
    return { lat: item.latitude, lng: item.longitude };
  });
  return { positions: position };
});

const onLoadKakaoMap = (mapRef) => {
  map.value = mapRef;
  latlng.value = map.value.getCenter();
  // 일반 지도와 스카이뷰로 지도 타입을 전환할 수 있는 지도타입 컨트롤을 생성합니다
  const mapTypeControl = new kakao.maps.MapTypeControl();
  // 지도 타입 컨트롤을 지도에 표시합니다
  map.value.addControl(mapTypeControl, kakao.maps.ControlPosition.TOPRIGHT);

  console.log("sasaf" + JSON.stringify(markerss.value[0]));

  kakao.maps.event.addListener(map.value, "dragend", function (mouseEvent) {
    // 클릭한 위도, 경도 정보를 가져옵니다
    const tmpLatlng = map.value.getCenter();
    marker.centerLat = tmpLatlng.getLat();
    marker.centerLng = tmpLatlng.getLng();
    latlng.value = tmpLatlng;
  });
  kakao.maps.event.addListener(map.value, "zoom_changed", function () {
    console.log(map.value.getLevel);
    const tmpLatlng = map.value.getCenter();
    marker.centerLat = tmpLatlng.getLat();
    marker.centerLng = tmpLatlng.getLng();
    marker.level = map.value.getLevel();
    latlng.value = tmpLatlng;
  });
};

document.addEventListener("DOMContentLoaded", function () {
  var buttons = document.querySelectorAll(".btn-group-toggle .btn");

  buttons.forEach(function (button) {
    button.addEventListener("click", function () {
      buttons.forEach(function (btn) {
        btn.classList.remove("active");
      });
      button.classList.add("active");
    });
  });
});
</script>

<template>
  <KakaoMap
    :lat="marker.centerLat"
    :lng="marker.centerLng"
    :draggable="true"
    :level="marker.level"
    width="100%"
    height="100%"
    v-if="marker.level < 5"
    @onLoadKakaoMap="onLoadKakaoMap"
  >
    <template v-if="useMap.isCard">
      <KakaoMapMarker
        v-for="item in marker.markers"
        :key="item.contentId"
        :lat="item.latitude"
        :lng="item.longitude"
        :clickable="true"
        @onClickKakaoMapMarker="onClickKakaoMapMarker(item)"
      ></KakaoMapMarker>
    </template>
    <template v-if="!useMap.isCard">
      <KakaoMapMarker
        v-for="item in marker.markers"
        :key="item.contentId"
        :lat="item.latitude"
        :lng="item.longitude"
        :clickable="true"
        :image="{
          imageSrc: influencer.selectedInfluencer.img,
          imageWidth: 64,
          imageHeight: 64,
          imageOption: {
            StyleSheet: 'rounded',
          },
        }"
        @onClickKakaoMapMarker="onClickKakaoMapMarker(item)"
      >
      </KakaoMapMarker>
    </template>
  </KakaoMap>

  <KakaoMap
    :lat="marker.centerLat"
    :lng="marker.centerLng"
    :draggable="true"
    :level="marker.level"
    width="100%"
    height="100%"
    v-if="marker.level >= 5"
    :markerCluster="{ markers: markerss.positions }"
    :key="markerss.positions.toString()"
    @onLoadKakaoMap="onLoadKakaoMap"
  >
  </KakaoMap>
</template>

<style scoped></style>
