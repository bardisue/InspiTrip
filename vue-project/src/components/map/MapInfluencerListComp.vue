<script setup>
import { useInfluencerStore } from "@/stores/influencer";
import { useMarkerStore } from "@/stores/marker";

const props = defineProps({
  item: Object,
});

const influencer = useInfluencerStore();
const marker = useMarkerStore();

const selectInfluencer = async () => {
  influencer.selectedInfluencer = props.item;
  await marker.getMarkerByInfluencer(props.item.no);
  influencer.rankUp(props.item.no);
  marker.centerLat = marker.markers[0].latitude;
  marker.centerLng = marker.markers[0].longitude;
};
</script>

<template>
  <div
    :class="
      influencer.selectedInfluencer.no == item.no ? `card shadow-sm bg-success` : `card shadow-sm`
    "
    style="max-height: 65px"
  >
    <div @click="selectInfluencer" class="card-body d-flex">
      <div>
        <img :src="item.img" alt="" style="" class="rounded-circle" width="32" height="32" />
      </div>
      <div class="align-self-center ml-1 text-truncate">{{ item.name }}</div>
    </div>
  </div>
</template>

<style scoped></style>
