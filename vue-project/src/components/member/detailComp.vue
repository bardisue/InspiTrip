<script setup>
import { useRoute, useRouter } from "vue-router";
import { computed, onMounted, ref } from "vue";
import { useMemberStore } from "@/stores/member";
const memberStore = useMemberStore();
const route = useRoute();
const router = useRouter();

onMounted(() => {
  console.log(">> " + memberStore.token);
  memberStore.getMember();
  console.log(memberStore.member);
  console.log("너는 ? >> " + memberStore.member.gender);
});

const update = computed(() => {
  router.push({ name: "member-update" });
});

const gender = computed(() => {
  if (memberStore.member.gender == "m") {
    return "1";
  } else {
    return "2";
  }
});

const memberDelete = computed(() => {
  const userChoice = confirm("삭제하시겠습니까?");
  if (userChoice) {
    memberStore.deleteMember();
  }
});
</script>

<template>
  <div class="container pt-5 mt-5">
    <div class="row">
      <div class="col-3"></div>
      <div class="col-6 border p-2">
        <h1>사용자 정보</h1>
        <hr />
        <form @submit.prevent="" method="post">
          <div class="input-group">
            <span class="input-group-text centered-text" style="width: 100px">아이디</span>
            <input
              type="text"
              class="form-control"
              placeholder="아이디"
              :value="memberStore.member.id"
              disabled
            />
          </div>

          <div class="input-group">
            <span class="input-group-text centered-text" style="width: 100px">비밀번호</span>
            <input
              type="password"
              class="form-control"
              placeholder="비밀번호"
              :value="memberStore.member.pw"
              disabled
            />
          </div>
          <div class="input-group">
            <span class="input-group-text centered-text" style="width: 100px">이름</span>
            <input
              type="text"
              class="form-control"
              placeholder="이름"
              :value="memberStore.member.name"
              disabled
            />
          </div>
          <div class="input-group">
            <span class="input-group-text centered-text" style="width: 100px">이메일</span>
            <input
              type="text"
              class="form-control"
              placeholder="이메일"
              :value="memberStore.member.email"
              disabled
            />
          </div>
          <div class="input-group">
            <span class="input-group-text centered-text" style="width: 100px">성별</span>
            <div class="row align-content-center" style="margin-left: 5px">
              <div class="form-check mr-2 col-6">
                <input
                  type="radio"
                  class="form-check-input"
                  name="gender"
                  value="1"
                  v-model="gender"
                  disabled
                />남자
                <label class="form-check-label"></label>
              </div>
              <div class="form-check mr-2 col-6">
                <input
                  type="radio"
                  class="form-check-input"
                  name="gender"
                  value="2"
                  v-model="gender"
                  disabled
                />여자
                <label class="form-check-label"></label>
              </div>
            </div>
          </div>
          <div class="input-group">
            <span class="input-group-text centered-text" style="width: 100px">전화번호</span>
            <input
              type="text"
              class="form-control"
              placeholder="전화번호"
              :value="memberStore.member.tel"
              disabled
            />
          </div>
          <div class="input-groupt">
            <img :src="memberStore.member.img" style="width: 100px; height: 100px" />
          </div>
          <div>
            <button type="button" class="btn btn-outline-success" @click="update">수정하기</button>
            <button type="button" class="btn btn-outline-danger" @click="memberDelete">
              삭제하기
            </button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<style scoped>
.centered-text {
  display: flex;
  justify-content: center;
  align-items: center;
}
</style>
