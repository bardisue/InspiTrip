<script setup>
import { useInfluencerStore } from "@/stores/influencer";
import { useMemberStore } from "@/stores/member";
import { onMounted } from "vue";

const props = defineProps({
  item: Object,
});
const influencer = useInfluencerStore();
const member = useMemberStore();

const follow = () => {
    console.log(props.item.no)
    member.follow(props.item.no);
};
</script>

<template>
  <div class="col">
    <div class="text-center card shadow-sm">
      <div>
        <a :href="item.url">
          <img :src="item.img" style="height: 200px" />
        </a>
      </div>
      <div class="card-body">
        <h3>{{ item.name }}</h3>
        <p class="card-text">
          {{ item.content }}
        </p>
        <div class="d-flex justify-content-between align-items-center">
          <div class="btn-group">
            <button
              type="button"
              v-if="member.isLogin"
              class="btn btn-sm btn-outline-secondary"
              @click="follow"
            >
              follow
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped></style>
