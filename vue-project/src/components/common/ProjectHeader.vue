<script setup>
import { useMemberStore } from "@/stores/member";
import { computed, onBeforeUpdate, onMounted, ref } from "vue";
import { useRouter } from "vue-router";

const member = useMemberStore();
const router = useRouter();
const imgRef = ref();

const id = computed(() => {
  return member.member.id ? member.member.id : " ";
});

const logined = computed(async () => {
  console.log(member.isLogin);
  return member.isLogin;
});

onMounted(async () => {
  await member.getMember();
  imgRef.value = member.member.img;
});

const logout = computed(() => {
  member.logoutMember();
});

const handleImageError = (event) => {
  event.target.src = "/src/assets/default.jpg";
  // 필요하다면 추가적으로 이미지 교체 실패시 다른 대체 이미지를 설정할 수 있습니다.
};
</script>

<template>
  <header class="p-1 mb border-bottom">
    <div class="container">
      <div
        class="d-flex flex-wrap align-items-center justify-content-center justify-content-lg-start"
      >
        <a
          href="/"
          class="d-flex align-items-center mb-2 mb-lg-0 link-body-emphasis text-decoration-none"
        >
          <img src="/src/assets/logo.jpg" width="64px" height="64px" alt="mdo" />
        </a>

        <ul class="nav col-12 col-lg-auto me-lg-auto mb-2 justify-content-center mb-md-0">
          <li>
            <router-link :to="{ name: 'main' }" class="nav-link px-2 link-secondary"
              >HOME</router-link
            >
          </li>
          <li>
            <router-link :to="{ name: 'map' }" class="nav-link px-2 link-body-emphasis"
              >MAP</router-link
            >
          </li>
          <li>
            <router-link :to="{ name: 'board-list' }" class="nav-link px-2 link-body-emphasis"
              >NOTICE</router-link
            >
          </li>
          <div class="dropdown text-end nav-link px-2 link-body-emphasis">
            <p
              class="d-block link-body-emphasis text-decoration-none dropdown-toggle"
              data-bs-toggle="dropdown"
              aria-expanded="false"
            >
              INFLUENCER
            </p>
            <ul class="dropdown-menu text-small">
              <li v-if="member.member.id == 'ssafy'">
                <router-link :to="{ name: 'influencer-accept' }" class="dropdown-item"
                  >인풀러언서 요청 목록</router-link
                >
              </li>
              <li>
                <router-link :to="{ name: 'influencer-regist' }" class="dropdown-item"
                  >인플루언서 요청</router-link
                >
              </li>
              <li>
                <router-link :to="{ name: 'influencer-list' }" class="dropdown-item"
                  >인플루언서 목록</router-link
                >
              </li>
            </ul>
          </div>
        </ul>

        <div class="pe-3">
          <img
            v-if="member.isLogin"
            :src="!member.hasAlarm ? `/src/assets/alarm_off.png` : `/src/assets/alarm_on.png`"
            width="32"
            height="32"
            class="rounded-circle"
            @click="router.push({ name: 'alarm' })"
          />
        </div>

        <div class="dropdown text-end">
          <a
            href="#"
            class="d-block link-body-emphasis text-decoration-none dropdown-toggle"
            data-bs-toggle="dropdown"
            aria-expanded="false"
          >
            <img
              v-if="member.isLogin"
              :src="member.member.img"
              @error="handleImageError"
              alt="mdo"
              width="32"
              height="32"
              class="rounded-circle"
              :key="member.member.img"
            />
            <img
              v-if="!member.isLogin"
              src="@/assets/question.png"
              alt="mdo"
              width="32"
              height="32"
              class="rounded-circle"
            />
          </a>
          <ul v-if="member.isLogin" class="dropdown-menu text-small">
            <li>
              <router-link :to="{ name: 'member-detail', params: { id: id } }" class="dropdown-item"
                >회원정보</router-link
              >
            </li>
            <li>
              <a href="#" class="dropdown-item" @click="logout">로그아웃</a>
              <!-- <router-link :to="{ name: 'member-logout' }" class="dropdown-item"
                >로그아웃</router-link
              > -->
            </li>
          </ul>
          <ul v-if="!member.isLogin" class="dropdown-menu text-small">
            <li>
              <router-link :to="{ name: 'member-regist' }" class="dropdown-item"
                >회원가입</router-link
              >
            </li>
            <li>
              <router-link :to="{ name: 'member-login' }" class="dropdown-item">로그인</router-link>
            </li>
          </ul>
        </div>
      </div>
    </div>
  </header>
</template>

<style scoped></style>
