<script setup>
import { useMemberStore } from "@/stores/member";
import { computed, onMounted, ref } from "vue";

const memberStore = useMemberStore();
const id = ref("");
const password = ref("");
const rememberId = ref();

onMounted(() => {
  if ($cookies.isKey("rememberId")) {
    id.value = $cookies.get("rememberId");
    rememberId.value = true;
  } else {
    rememberId.value = false;
  }
});

const login = computed(() => {
  if (rememberId.value) {
    $cookies.set("rememberId", id.value);
  } else {
    if ($cookies.isKey("rememberId")) {
      $cookies.remove("rememberId");
    }
  }
  memberStore.member.id = id.value;
  memberStore.member.pw = password.value;
  memberStore.loginMember();
});
</script>

<template>
  <div class="container mt-5 pt-5">
    <div class="row mt-5 pt-5">
      <div class="col-3"></div>
      <div class="col-6">
        <div class="mb-3 mt-3">
          <label for="id" class="form-label">아이디: </label>
          <input
            type="text"
            class="form-control"
            placeholder="아이디를 입력해주세요."
            v-model="id"
          />
        </div>
        <div class="mb-3">
          <label for="pwd" class="form-label">비밀번호: </label>
          <input
            type="password"
            class="form-control"
            placeholder="비밀번호를 입력해주세요."
            v-model="password"
          />
        </div>
        <div class="form-check mb-3">
          <label class="form-check-label">
            <input class="form-check-input" type="checkbox" name="remember" v-model="rememberId" />
            아이디 저장
          </label>
        </div>
        <button class="btn btn-primary" @click="login">로그인</button>
      </div>
      <div class="col-3"></div>
    </div>
  </div>
</template>

<style scoped></style>
