<script setup>
import { useBoardStore } from "@/stores/board";
import { useMemberStore } from "@/stores/member";
import { ref, computed } from "vue";
const boardStore = useBoardStore();
const props = defineProps(["boardDetail"]);

const board = ref(props.boardDetail);

const boardDetail = computed(() => {
  console.log(board.value.no);

  boardStore.boardDetail(board.value.no);
});
</script>

<template>
  <tr @click="boardDetail">
    <td>{{ board.no }}</td>
    <td>{{ board.title }}</td>
    <td>{{ board.mid }}</td>
    <td>{{ board.read_count }}</td>
    <td>{{ board.write_date }}</td>
  </tr>
</template>

<style scoped></style>
