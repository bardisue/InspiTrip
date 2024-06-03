<script setup>
import { useInfluencerStore } from "@/stores/influencer";
import { onMounted, ref } from "vue";
import InfluencerCardComp from "./InfluencerCardComp.vue";

const influencer = useInfluencerStore();

onMounted(() => {
  influencer.getInfluencers();
  console.log(influencer.influencerList);
});

const keyword = ref("");

const changeKeyword = (e) => {
  keyword.value = e.target.value;
  influencer.getInfluencersByWord(keyword.value);
};
</script>

<template>
  <div class="container">
    <h1>인플루언서 리스트</h1>
    <input
      type="text"
      class="form-control w-50"
      :value="keyword"
      @input="changeKeyword"
      id="inputField"
      placeholder="Search"
    />
    <div class="album py-5 bg-body-tertiary">
      <div class="container">
        <div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 g-3">
          <InfluencerCardComp
            v-for="item in influencer.influencerList"
            :key="item.no"
            :item="item"
          />
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped></style>
