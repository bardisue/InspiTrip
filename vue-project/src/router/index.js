import { createRouter, createWebHistory } from "vue-router";
import TheMainView from "../views/TheMainView.vue";
import TheMemberView from "@/views/TheMemberView.vue";
import TheMapView from "@/views/TheMapView.vue";
import UpdateComp from "@/components/member/updateComp.vue";
import RegistComp from "@/components/member/registComp.vue";
import LoginComp from "@/components/member/loginComp.vue";
import DetailComp from "@/components/member/detailComp.vue";
import BoardListComp from "@/components/board/boardListComp.vue";
import TheBoardView from "../views/TheBoardView.vue";
import BoardInsertComp from "@/components/board/boardInsertComp.vue";
import BoardDetailComp from "@/components/board/boardDetailComp.vue";
import BoardUpdateComp from "@/components/board/boardUpdateComp.vue";
import TheInfluencerView from "@/views/TheInfluencerView.vue";
import InfluencerRegistComp from "@/components/influencer/InfluencerRegistComp.vue";
import InfluencerListComp from "@/components/influencer/InfluencerListComp.vue";
import MapInfluencerSearchComp from "@/components/map/MapInfluencerSearchComp.vue";
import MapNoramlSearchCompVue from "@/components/map/MapNoramlSearchComp.vue";
import AlarmListComp from "@/components/alarm/AlarmListComp.vue";
import { useMemberStore } from "@/stores/member";
import TheReviewView from "@/views/TheReviewView.vue";
import ReviewInsertComp from "@/components/review/ReviewInsertComp.vue";
import ReviewListDetailComp from "@/components/review/ReviewListDetailComp.vue";
import InfluencerAcceptComp from "@/components/influencer/InfluencerAcceptComp.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "main",
      component: TheMainView,
    },
    {
      path: "/member",
      name: "member",
      component: TheMemberView,
      children: [
        {
          path: "login",
          name: "member-login",
          component: LoginComp,
          beforeEnter: (to, from) => {
            const member = useMemberStore();
            if (member.isLogin) {
              alert("이미 로그인 하셨습니다.");
              router.push({ name: "main" });
              return false;
            } else {
              return true;
            }
          },
        },
        {
          path: "regist",
          name: "member-regist",
          component: RegistComp,
          beforeEnter: (to, from) => {
            const member = useMemberStore();
            if (member.isLogin) {
              alert("이미 로그인 하셨습니다.");
              router.push({ name: "main" });
              return false;
            } else {
              return true;
            }
          },
        },
        {
          path: "update",
          name: "member-update",
          component: UpdateComp,
          beforeEnter: async (to, from) => {
            const member = useMemberStore();
            if (!member.isLogin) {
              alert("로그인 이후 사용해주세요.");
              router.push({ name: "main" });
              return false;
            } else {
              await member.getMember().catch(() => {
                alert("로그인이 만료되었습니다.");
                member.logoutMember();
                return false;
              });
              return true;
            }
          },
        },
        {
          path: ":id",
          name: "member-detail",
          component: DetailComp,
          beforeEnter: async (to, from) => {
            const member = useMemberStore();
            if (!member.isLogin) {
              alert("로그인 이후 사용해주세요.");
              router.push({ name: "main" });
              return false;
            } else {
              await member.getMember().catch(() => {
                alert("로그인이 만료되었습니다.");
                member.logoutMember();
                return false;
              });
              return true;
            }
          },
        },
      ],
    },
    {
      path: "/map",
      name: "map",
      component: TheMapView,
      redirect: "/map/normal",
      children: [
        {
          path: "normal",
          name: "map-normal",
          component: MapNoramlSearchCompVue,
        },
        {
          path: "influencerSearch",
          name: "map-influencer",
          component: MapInfluencerSearchComp,
        },
      ],
    },
    {
      path: "/board",
      name: "board",
      component: TheBoardView,
      children: [
        {
          path: "",
          name: "board-list",
          component: BoardListComp,
        },
        {
          path: "insert",
          name: "board-insert",
          component: BoardInsertComp,
          beforeEnter: (to, from) => {
            const member = useMemberStore();
            if (!member.isLogin) {
              alert("로그인 이후 사용해주세요.");
              router.push({ name: "board-list" });
              return false;
            } else {
              return true;
            }
          },
        },
        {
          path: ":no",
          name: "board-detail",
          component: BoardDetailComp,
        },
        {
          path: "update/:no",
          name: "board-update",
          component: BoardUpdateComp,
          beforeEnter: (to, from) => {
            const member = useMemberStore();
            if (!member.isLogin) {
              alert("로그인 이후 사용해주세요.");
              router.push({ name: "board-list" });
              return false;
            } else {
              return true;
            }
          },
        },
      ],
      // redirect: { name: "article-list" },
      // children: [
      //   {
      //     path: "list",
      //     name: "article-list",
      //     component: () => import("@/components/board/BoardList.vue"),
      //   },
      //   {
      //     path: "view/:articleno",
      //     name: "article-view",
      //     component: () => import("@/components/board/BoardDetail.vue"),
      //   },
      //   {
      //     path: "write",
      //     name: "article-write",
      //     component: () => import("@/components/board/BoardWrite.vue"),
      //   },
      //   {
      //     path: "modify/:articleno",
      //     name: "article-modify",
      //     component: () => import("@/components/board/BoardModify.vue"),
      //   },
      // ],
    },
    {
      path: "/influencer",
      name: "influencer",
      component: TheInfluencerView,
      children: [
        {
          path: "regist",
          name: "influencer-regist",
          component: InfluencerRegistComp,
          beforeEnter: (to, from) => {
            const member = useMemberStore();
            if (!member.isLogin) {
              alert("로그인 이후 사용해주세요.");
              router.push({ path: from });
              return false;
            } else {
              console.log(to);
              return true;
            }
          },
        },
        {
          path: "list",
          name: "influencer-list",
          component: InfluencerListComp,
        },
        {
          path: "accept",
          name: "influencer-accept",
          component: InfluencerAcceptComp,
          beforeEnter: (to, from) => {
            const member = useMemberStore();
            if (member.member.id != "ssafy") {
              alert("관리자만 사용 가능합니다");
              router.push({ name: "main" });
              return false;
            } else {
              return true;
            }
          },
        },
      ],
    },
    {
      path: "/review",
      name: "review",
      component: TheReviewView,
      children: [
        {
          path: "insert",
          name: "review-insert",
          component: ReviewInsertComp,
        },
        {
          path: "detail",
          name: "review-detail",
          component: ReviewListDetailComp,
        },
      ],
    },
    {
      path: "/alarm",
      name: "alarm",
      component: AlarmListComp,
      beforeEnter: async (to, from) => {
        const member = useMemberStore();
        if (!member.isLogin) {
          alert("로그인 이후 사용해주세요");
          router.push({ name: "main" });
          return false;
        } else {
          await member.getMember().catch(() => {
            alert("로그인이 만료되었습니다.");
            member.logoutMember();
            return false;
          });
          return true;
        }
      },
    },
  ],
});

router.beforeEach((to, from) => {
  const member = useMemberStore();
  if (member.isLogin) {
    member.countAlarm();
  }
});

export default router;
