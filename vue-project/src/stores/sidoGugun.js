import { ref, computed } from "vue";
import { defineStore } from "pinia";
import axios from "axios";

const URL = import.meta.env.VITE_VUE_API_URL;

export const usesidoGugunStore = defineStore("sidoGugun", () => {
  const sido = ref([]);
  const gugun = ref([]);
  const getSido = () => {
    console.log(URL + "attr/sido");
    axios(URL + "attr/sido").then((resp) => {
      console.log(resp.data);
      sido.value = resp.data;
    });
  };

  const getGugun = (sido) => {
    console.log(URL + "attr/sido");
    axios(URL + "attr/gungu/" + sido).then((resp) => {
      console.log(resp.data);
      gugun.value = resp.data;
    });
  };

  return { getSido, getGugun, sido, gugun };
});
