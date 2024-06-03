import { defineStore } from "pinia";
import { computed, ref } from "vue";
import { useRouter } from "vue-router";
import axios from "axios";
import { method } from "lodash";

const { VITE_VUE_API_URL } = import.meta.env;

export const useMemberStore = defineStore(
  "member",
  () => {
    const router = useRouter();
    const token = ref("");
    const alarms = ref([]);

    const isLogin = ref(false);

    const imageFile = ref(); // 이미지 파일 저장하는 변수
    const idCheckState = ref("fail"); // 아이디의 중복을 체크하는 변수
    const member = ref({
      id: null,
      pw: null,
      name: null,
      email: null,
      gender: null,
      tel: null,
      img: null,
    });

    const countAlarm = () => {
      axios(VITE_VUE_API_URL + "member/alarms/" + member.value.id)
        .then((resp) => resp.data)
        .then((data) => {
          alarms.value = data;
        });
    };

    const hasAlarm = computed(() => {
      console.log("ㅇㅇ");
      return alarms.value.some((item) => item.checked === false);
    });

    const checkAlarm = async (no) => {
      await axios.post(VITE_VUE_API_URL + `member/alarms/${no}`);
    };

    const deleteAlarm = async (no) => {
      await axios.delete(VITE_VUE_API_URL + `member/alarms/${no}`);
    };

    const follow = (ino) => {
      if (isLogin.value) {
        axios({
          url: VITE_VUE_API_URL + `member/follow/${member.value.id}/${ino}`,
          method: "POST",
          headers: { Authorization: `Bearer ${token.value}` },
        })
          .then((resp) => resp.data)
          .then((data) => {
            console.log(data);
            if (data == "already") {
              window.alert("이미 팔로우 하셨습니다");
            } else if (data == "success") {
              window.alert("팔로우 완료!");
            }
          });
      }
    };

    const uploadImage = async () => {
      console.log("uploadImage >> " + imageFile.value);
      const formData = new FormData();
      if (imageFile != null) {
        formData.append("image", imageFile.value);
        await fetch("http://localhost:3000/upload/member", {
          method: "POST",
          body: formData,
        })
          .then((response) => response.text())
          .then((data) => {
            console.log("Success:", data);
            member.value.img = `http://localhost:3000/member/${data}`;
            console.log("uploadImage >> " + member.value);
          })
          .catch((error) => {
            console.error("Error:", error);
          });
        console.log("last >>>>>> ");
      }
    };

    const loginMember = () => {
      console.log(`${VITE_VUE_API_URL}member/login`);
      axios({
        url: `${VITE_VUE_API_URL}member/login`,
        method: "POST",
        data: member.value,
      })
        .then((resp) => {
          token.value = resp.data;
          if (token.value == "fail") {
            alert("아이디 또는 비밀번호가 잘못되었습니다.");
            token.value = "";
            router.replace({ name: "member-login" });
          } else {
            isLogin.value = true;
            router.replace({ name: "main" });
            console.log("token: " + token.value);
          }
        })
        .catch((err) => {
          console.log(err);
        });
    };
    const logoutMember = () => {
      if (token.value == "") {
        alert("잘못된 접근입니다.");
      } else {
        token.value = "";
        member.value = {
          id: null,
          pw: null,
          name: null,
          email: null,
          gender: null,
          tel: null,
          img: null,
        };
        alert("로그아웃 되었습니다.");
        isLogin.value = false;
      }
      router.replace({ name: "main" });
    };

    const idCheckMember = (memberId) => {
      let state = "fail";
      axios({
        url: `${VITE_VUE_API_URL}member/check/${memberId}`,
        method: "GET",
      })
        .then((resp) => {
          state = resp.data;
          if (resp.data == "ok") {
            idCheckState.value = "ok";
          } else {
            idCheckState.value = "fail";
          }
        })
        .catch((err) => {
          console.log(err);
          idCheckState.value = "fail";
        });
    };

    const getMember = async () => {
      console.log(`Bearer ${token.value}`);
      await axios({
        url: `${VITE_VUE_API_URL}member/info/${member.value.id}`,
        method: "POST",
        headers: { Authorization: `Bearer ${token.value}` },
      }).then((resp) => {
        console.log("getMember 성공");
        member.value = resp.data;
        console.log(resp.data);
        console.log("log >> " + member.value.gender);
        console.log("member: " + member.value);
      });
    };

    // 회원 가입
    const registMember = async () => {
      if (idCheckState.value == "ok") {
        await uploadImage();
        console.log("registMember >> " + member.value);
        axios({
          url: `${VITE_VUE_API_URL}member/regist`,
          method: "POST",
          data: member.value,
        })
          .then((response) => {
            console.log(response.data);
            alert("회원가입이 완료되었습니다.");
            router.replace({ name: "main" });
          })
          .catch((error) => {
            console.error("There was an error!", error);
          });
      } else {
        alert("잘못된 정보가 기입되어 있는지 확인해주세요.");
      }
    };
    const updateMember = async () => {
      if (token != "") {
        if (imageFile.value.name != null) {
          await uploadImage();
        }
        console.log("registMember >> " + member.value);
        await axios({
          url: `${VITE_VUE_API_URL}member/update`,
          method: "POST",
          headers: { Authorization: `Bearer ${token.value}` },
          data: member.value,
        })
          .then((response) => {
            console.log(response.data);
            alert("업데이트가 완료되었습니다.");
            router.replace({
              name: "member-detail",
              params: { id: member.value.id },
            });
          })
          .catch((error) => {
            console.error("There was an error!", error);
          });
      } else {
        alert("잘못된 정보가 기입되어 있는지 확인해주세요.");
      }
    };
    const deleteMember = () => {
      console.log(`Bearer ${token.value}`);
      axios({
        url: `${VITE_VUE_API_URL}member/delete`,
        method: "DELETE",
        headers: { Authorization: `Bearer ${token.value}` },
        data: member.value,
      })
        .then((resp) => {
          if (resp.data == "ok") {
            logoutMember();
          }
        })
        .catch((err) => {
          console.log(err);
        });
    };

    return {
      member,
      token,
      isLogin,
      loginMember,
      logoutMember,
      getMember,
      registMember,
      updateMember,
      deleteMember,
      idCheckMember,
      idCheckState,
      imageFile,
      follow,
      alarms,
      countAlarm,
      hasAlarm,
      checkAlarm,
      deleteAlarm,
    };
  },
  {
    persist: true,
  }
);
