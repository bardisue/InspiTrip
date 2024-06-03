<script setup>
import { RouterView } from "vue-router";
import ProjectHeader from "@/components/common/ProjectHeader.vue";
import { useMemberStore } from "./stores/member";

const member = useMemberStore();
</script>

<template>
  <div>
    <ProjectHeader :key="{ isLogin: member.isLogin }" />
    <router-view></router-view>
  </div>
</template>

<style scoped></style>
