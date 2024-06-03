<script setup>
import { useInfluencerStore } from "@/stores/influencer";
import { onMounted, ref } from "vue";

const influencer = useInfluencerStore();

onMounted(() => {
  influencer.getInfluencers();
  console.log(influencer.influencerList);
});

const newInfluencer = ref({
  name: "",
  img: "",
  content: "",
  url: "",
});

function handleImageUpload(event) {
  const file = event.target.files[0];
  if (file) {
    const reader = new FileReader();
    reader.onload = (e) => {
      imageSrc.value = e.target.result;
    };
    reader.readAsDataURL(file);
    influencerImage.value = event.target.files[0];
  } else {
    imageSrc.value = "";
    influencerImage.value = null;
  }
}

const insertNew = () => {
  influencer.newInfluencer = newInfluencer.value;
  influencer.registInfluencer(influencerImage.value);
};

const influencerImage = ref(); // 이미지 file을 담는 변수
const imageSrc = ref(""); //이미지 태그에서 사용하는 변수
</script>

<template>
  <div class="container mt-3 pt-3">
    <div class="row mt-3 pt-3">
      <div class="col-3"></div>
      <div class="col-6">
        <h1>인플루언서 등록</h1>
        <hr />
        <form @submit.prevent="insertNew">
          <fieldset>
            <div class="input-group mb-4">
              <span class="input-group-text">이름</span>
              <input
                type="text"
                class="form-control"
                placeholder="이름"
                v-model="newInfluencer.name"
                required
              />
            </div>

            <div class="input-groupt mb-4">
              <img :src="imageSrc" v-if="imageSrc != ''" style="width: 100px; height: 100px" />
            </div>
            <div class="input-group mb-4">
              <span class="input-group-text">이미지</span>
              <input
                type="file"
                class="form-control"
                placeholder="이미지"
                @change="handleImageUpload"
              />
            </div>

            <div class="input-group mb-4">
              <span class="input-group-text">설명</span>
              <input
                type="text"
                class="form-control"
                placeholder="설명"
                v-model="newInfluencer.content"
              />
            </div>
            <div class="input-group mb-4">
              <span class="input-group-text">URL</span>
              <input
                type="text"
                class="form-control"
                placeholder="URL"
                v-model="newInfluencer.url"
                required
              />
            </div>
            <div>
              <button class="btn btn-outline-success">요청하기</button>
              <button type="button" class="btn btn-outline-danger" @clcik="cancel">취소하기</button>
            </div>
          </fieldset>
        </form>
      </div>
      <div class="col-3"></div>
    </div>
  </div>
</template>

<style scoped></style>
