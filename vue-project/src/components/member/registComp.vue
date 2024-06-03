<script setup>
import { useRoute } from "vue-router";
import { ref, computed } from "vue";
import { useMemberStore } from "@/stores/member";
const memberStore = useMemberStore();
const route = useRoute();

const memberImage = ref(); // 이미지 file을 담는 변수
const imageSrc = ref(""); //이미지 태그에서 사용하는 변수
const member = ref({
  id: null,
  pw: null,
  name: null,
  email: null,
  gender: null,
  tel: null,
});

// 회원 가입
const registMember = computed(() => {
  console.log(member.value);
  memberStore.member = member.value;
  console.log("regist >> " + memberImage);
  memberStore.imageFile = memberImage.value;
  memberStore.registMember();
});

// 메시지 중복 체크
const message = computed(() => {
  if (member.value.id == null) {
    return "";
  } else if (member.value.id.length < 5 || member.value.id.length > 12) {
    return "5이상 12이하로 작성해주세요.";
  } else {
    memberStore.idCheckMember(member.value.id);
    if (memberStore.idCheckState == "ok") {
      return "사용 가능한 아이디입니다.";
    }
    return "중복된 아이디입니다.";
  }
});

function handleImageUpload(event) {
  const file = event.target.files[0];
  if (file) {
    const reader = new FileReader();
    reader.onload = (e) => {
      imageSrc.value = e.target.result;
    };
    reader.readAsDataURL(file);
    memberImage.value = event.target.files[0];
  } else {
    imageSrc.value = "";
    memberImage.value = null;
  }
}
</script>

<template>
  <div class="container mt-3 pt-3">
    <div class="row mt-3 pt-3">
      <div class="col-3"></div>
      <div class="col-6">
        <h1>회원가입</h1>
        <hr />
        <form @submit.prevent="registMember" enctype="multipart/form-data">
          <div class="input-group mb-4">
            <span class="input-group-text">아이디</span>
            <input
              type="text"
              class="form-control required"
              placeholder="아이디"
              v-model="member.id"
            />
            <span>{{ message }}</span>
          </div>
          <div class="input-group mb-4">
            <span class="input-group-text">비밀번호</span>
            <input
              type="password"
              class="form-control required"
              placeholder="비밀번호"
              v-model="member.pw"
            />
          </div>
          <div class="input-group mb-4">
            <span class="input-group-text">이름</span>
            <input
              type="text"
              class="form-control required"
              placeholder="이름"
              v-model="member.name"
            />
          </div>
          <div class="input-group mb-4">
            <span class="input-group-text">이메일</span>
            <input type="text" class="form-control" placeholder="이메일" v-model="member.email" />
          </div>
          <div class="input-group mb-4">
            <span class="input-group-text">성별</span>
            <div class="row align-content-center" style="margin-left: 5px">
              <div class="form-check mr-2 col-6">
                <input
                  type="radio"
                  class="form-check-input"
                  name="gender"
                  value="1"
                  v-model="member.gender"
                  required
                />남자
              </div>
              <div class="form-check ml-2 col-6">
                <input
                  type="radio"
                  class="form-check-input"
                  name="gender"
                  value="2"
                  v-model="member.gender"
                  required
                />여자
              </div>
            </div>
          </div>
          <div class="input-group mb-4">
            <span class="input-group-text">전화번호</span>
            <input type="text" class="form-control" placeholder="전화번호" v-model="member.tel" />
          </div>
          <div class="input-groupt mb-4" v-if="imageSrc != ''">
            <img :src="imageSrc" class="rounded" style="width: 100px; height: 100px" />
          </div>
          <div class="input-group mb-4">
            <span class="input-group-text">이미지</span>
            <input
              type="file"
              accept="image/jpeg"
              class="form-control"
              placeholder="이미지"
              @change="handleImageUpload"
            />
          </div>
          <div>
            <button class="btn btn-outline-success">회원가입</button>
          </div>
        </form>
      </div>
      <div class="col-3"></div>
    </div>
  </div>
</template>

<style scoped></style>
