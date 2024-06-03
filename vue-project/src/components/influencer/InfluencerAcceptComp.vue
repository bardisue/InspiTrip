<script setup>
import { onMounted, ref, watch, computed } from "vue";
import InfluencerAcceptItemComp from "./InfluencerAcceptItemComp.vue";
import { useInfluencerStore } from "@/stores/influencer";

const influencer = useInfluencerStore();

onMounted(() => {
  influencer.getInfluencerTempList();
});

const len = computed(() => {
  return influencer.tempList.length;
});
</script>

<template>
  <div>
    <div class="container mt-3">
      <h2>Hover Rows</h2>
      <table class="table table-hover">
        <thead>
          <tr>
            <th>No</th>
            <th>인플루언서 이름</th>
            <th>이미지</th>
            <th>내용</th>
            <th>url</th>
            <th>요청</th>
          </tr>
        </thead>
        <tbody>
          <template v-if="len == 0">
            <tr>
              <td colspan="6">새로운 요청이 없습니다.</td>
            </tr>
          </template>

          <InfluencerAcceptItemComp
            v-for="temp in influencer.tempList"
            :key="temp.no"
            :itemp="temp"
          />
        </tbody>
      </table>
    </div>
  </div>
</template>

<style scoped></style>
