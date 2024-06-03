import { ref, computed } from "vue";
import { defineStore } from "pinia";

export const useMapStore = defineStore("map", () => {
  const isCard = ref(true);

  return { isCard };
});
