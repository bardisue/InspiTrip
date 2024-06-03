import { ref, computed, watch } from "vue";
import { defineStore } from "pinia";
import axios from "axios";
import { useMapStore } from "./map";
import { useReviewStore } from "./review";

const URL = import.meta.env.VITE_VUE_API_URL;

export const useMarkerStore = defineStore("marker", () => {
  const markers = ref([]);
  const selectedType = ref(0);
  const level = ref(3);
  const map = useMapStore();
  const div_status = ref(true);

  const limit = ref(0);

  const computedObj = computed(
    () => {
      console.log(markers.value.slice(0, limit.value));
      return markers.value.slice(0, limit.value);
    },
    {
      immediate: true,
    }
  );

  const centerLat = ref(37.566826);
  const centerLng = ref(126.9786567);

  const selectedMarker = ref({
    title: "",
  });
  const markerInfluencer = ref([]);

  watch(
    () => selectedType.value,
    () => {
      console.log(selectedType.value);
      getMarkerByLatLong(centerLat.value, centerLng.value);
    }
  );

  watch(
    () => selectedMarker.value,
    () => {
      const review = useReviewStore();
      console.log("선택");
      review.getAvg(selectedMarker.value.contentId);
      review.getReviewListByAno(selectedMarker.value.contentId);
      div_status.value = true;
    }
  );

  const getMarkerByLatLong = async (lat, lng) => {
    if (map.isCard) {
      await axios({
        method: "POST",
        url: URL + "attr/list/location",
        data: {
          contentId: 0,
          contentTypeId: selectedType.value,
          sidoCode: 0,
          gunguCode: 0,
          latitude: lat,
          longitude: lng,
          level: level.value,
        },
      }).then((resp) => {
        console.log(resp.data);
        markers.value = [];
        markers.value = resp.data;
      });
    }
  };

  const getMarkerByInfluencer = async (no) => {
    if (!map.isCard) {
      try {
        const response = await axios.get(URL + "attr/visit/" + no);
        markers.value = response.data;
      } catch (error) {
        console.error("Error fetching markers:", error);
      }
    }
  };
  const getMarkerBySido = (sido, gugun) => {
    axios({
      method: "POST",
      url: URL + "attr/list/sidoGungu",
      data: {
        contentId: 0,
        contentTypeId: selectedType.value,
        sidoCode: sido,
        gunguCode: gugun,
        latitude: 0,
        longitude: 0,
        level: 0,
      },
    })
      .then((resp) => {
        console.log(resp.data);
        markers.value = [];
        markers.value = resp.data;
        console.log(selectedType.value + "searched");
      })
      .then(() => {
        centerLat.value = markers.value[0].latitude;
        centerLng.value = markers.value[0].longitude;
        console.log(centerLat.value, centerLng.value);
      });
  };

  const selectOne = (item) => {
    selectedMarker.value = item;
    axios.post(URL + "influencer/visit/" + item.contentId).then((resp) => {
      markerInfluencer.value = resp.data;
    });
  };

  const selectOneById = async (ano) => {
    await axios.post(URL + "influencer/visit/" + ano).then((resp) => {
      console.log(resp.data);
      markerInfluencer.value = resp.data;
    });
  };

  const selectById = async (id) => {
    await axios(URL + `attr/attraction/${id}`)
      .then((resp) => resp.data)
      .then((data) => {
        selectedMarker.value = data;
      });
  };

  return {
    markers,
    centerLat,
    centerLng,
    selectedMarker,
    getMarkerByLatLong,
    getMarkerBySido,
    selectOne,
    markerInfluencer,
    selectedType,
    level,
    getMarkerByInfluencer,
    selectById,
    selectOneById,
    div_status,
    limit,
    computedObj,
  };
});
