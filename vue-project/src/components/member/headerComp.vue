<script setup>
import { computed, ref } from "vue";
import { RouterLink } from "vue-router";
import { useMemberStore } from "@/stores/member";
const memberStore = useMemberStore();
const id = computed(() => {
  return memberStore.member.id ? memberStore.member.id : " ";
});

const logout = computed(() => {
  memberStore.logoutMember();
});
</script>

<template>
  <header>
    <router-link :to="{ name: 'member-login' }" v-if="memberStore.token == ''">Login</router-link>
    <span v-if="memberStore.token == ''"> | </span>
    <a href="#" @click="logout" v-if="memberStore.token != ''">Logout</a>
    <span v-if="memberStore.token != ''"> | </span>
    <router-link :to="{ name: 'member-regist' }">회원가입</router-link>
    <span v-if="memberStore.token != ''"> | </span>
    <router-link :to="{ name: 'member-detail', params: { id: id } }" v-if="memberStore.token != ''"
      >detail</router-link
    >
  </header>
</template>

<style scoped></style>
