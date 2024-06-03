<script setup>
import { useInfluencerStore } from "@/stores/influencer";
const props = defineProps({ itemp: Object });
const influencer = useInfluencerStore();

// console.log(props.itemp);

const accept = async () => {
  const resp = await influencer.influencerTempAccept(props.itemp.no);
  influencer.getInfluencerTempList();
};

const refuse = async () => {
  const resp = await influencer.influencerTempRefuse(props.itemp.no);
  influencer.getInfluencerTempList();
};
</script>

<template>
  <tr>
    <td>{{ props.itemp.no }}</td>
    <td>{{ props.itemp.name }}</td>
    <td v-if="props.itemp.img != ' '"><img :src="props.itemp.img" /></td>
    <td>{{ props.itemp.conetnt }}</td>
    <td>{{ props.itemp.url }}</td>
    <td>
      <div class="btn-group btn-group-sm" role="group" aria-label="Basic example">
        <button type="button" class="btn btn-outline-success" @click="accept">수락</button>
        <button type="button" class="btn btn-outline-danger" @click="refuse">거절</button>
      </div>
    </td>
  </tr>
</template>

<style scoped></style>
