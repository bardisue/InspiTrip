<script setup>
import { useInfluencerStore } from "@/stores/influencer";
import { useMapStore } from "@/stores/map";
import { useMarkerStore } from "@/stores/marker";
import { useMemberStore } from "@/stores/member";
import { useRouter } from "vue-router";

const Influencer = useInfluencerStore();
const marker = useMarkerStore();
const member = useMemberStore();

const props = defineProps({
  item: Object,
});

const router = useRouter();

const readAlarm = async () => {
  await Influencer.selectInfluencer(props.item.ino);
  await marker.getMarkerByInfluencer(props.item.ino);
  await marker.selectOneById(props.item.ano);
  await marker.selectById(props.item.ano);
  await member.checkAlarm(props.item.no);
  router.push({ name: "map-influencer", params: { alarm: true } });
};

const read = async () => {
  await member.checkAlarm(props.item.no);
  member.countAlarm();
};
const deleteAlarm = async () => {
  await member.deleteAlarm(props.item.no);
  member.countAlarm();
};
</script>

<template>
  <div class="notification-item" :class="{ read: item.checked }" @click="readAlarm">
    <img :src="item.img" alt="Avatar" />
    <div class="content">
      <p class="mb-0">{{ item.iname }}</p>
      <p class="mb-0 text-muted">{{ item.aname }}</p>
    </div>
    <span class="timestamp text-muted">2024-05-17 08:11:39</span>
    <div class="btn" @click.stop="read">읽음확인</div>
    <div class="btn" @click.stop="deleteAlarm">삭제</div>
  </div>
</template>

<style scoped>
.notification-item {
  display: flex;
  align-items: center;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
  transition: background-color 0.3s;
}

.notification-item:hover {
  cursor: pointer;
  background-color: #f0f0f0;
}

.notification-item.read {
  background-color: #e0e0e0;
  color: #888;
}

.notification-item img {
  width: 50px;
  height: 50px;
  border-radius: 50%;
}
.notification-item .content {
  flex-grow: 1;
  padding-left: 10px;
}
.notification-item .timestamp {
  white-space: nowrap;
}
.nav-tabs .nav-link.active {
  color: #495057;
  background-color: #e9ecef;
}
</style>
