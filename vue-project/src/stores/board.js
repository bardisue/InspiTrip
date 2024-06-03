import { ref, computed } from "vue";
import axios from "axios";
import { defineStore } from "pinia";
import { useMemberStore } from "@/stores/member";
import { useRouter } from "vue-router";

const { VITE_VUE_API_URL } = import.meta.env;

export const useBoardStore = defineStore("board", () => {
  const router = useRouter();
  const memberStore = useMemberStore();
  const page = ref({
    curPage: 1,
    endPage: null,
    startPage: null,
    totalPage: null,
  });
  const boardList = ref([]);
  const board = ref({
    no: null,
    mid: null,
    title: null,
    content: null,
    write_date: null,
    read_count: null,
  });

  const threeNotice = ref([]);

  const getThree = () => {
    axios(VITE_VUE_API_URL + "notice/list/three")
      .then((resp) => resp.data)
      .then((data) => {
        threeNotice.value = data;
      });
  };

  const upload_image = async (file) => {
    console.log(file);
    let resp_url = null;
    const formData = new FormData();
    formData.append("image", file);
    await axios
      .post("http://localhost:3000/upload/board", formData, {
        headers: {
          "Content-Type": "multipart/form-data",
        },
      })
      .then((resp) => {
        console.log(resp);
        resp_url = `http://localhost:3000/board/${resp.data}`;
        console.log(resp_url);
      })
      .catch((err) => {
        console.error("Error:", err);
      });
    return resp_url;
  };

  const getBoardList = () => {
    axios({
      url: `${VITE_VUE_API_URL}notice/list/${page.value.curPage}`,
      method: "GET",
    })
      .then((resp) => {
        console.log(resp);
        page.value.curPage = resp.data.curPage;
        page.value.endPage = resp.data.endPage;
        page.value.startPage = resp.data.startPage;
        page.value.totalPage = resp.data.totalPage;
        boardList.value = resp.data.noticeList;
        console.log(boardList.value);
      })
      .catch((err) => {
        console.log(err);
      });
  };

  const boardInsert = (mid) => {
    console.log("boardInsert >> " + mid);
    console.log(board.value);
    board.value.mid = mid;
    axios({
      url: `${VITE_VUE_API_URL}notice/write`,
      method: "POST",
      data: board.value,
    })
      .then((resp) => {
        console.log(resp);
        router.push({ name: "board-list" });
      })
      .catch((err) => {
        console.log(err);
      });
  };
  const getBoard = (no) => {
    console.log("boardDetai >> " + no);
    axios({
      url: `${VITE_VUE_API_URL}notice/read/${no}`,
      method: "GET",
    })
      .then((resp) => {
        console.log(resp);
        board.value = resp.data;
      })
      .catch((err) => {
        console.log(err);
      });
  };

  const boardDetail = (no) => {
    console.log("boardDetai >> " + no);
    axios({
      url: `${VITE_VUE_API_URL}notice/read/${no}`,
      method: "GET",
    })
      .then((resp) => {
        console.log(resp);
        board.value = resp.data;
        router.push({ name: "board-detail", params: { no: board.value.no } });
      })
      .catch((err) => {
        console.log(err);
      });
  };

  const boardDetailReadCount = (no) => {
    console.log("boardDetailReadCount >> " + no);
    axios({
      url: `${VITE_VUE_API_URL}notice/readcount/${no}`,
      method: "PUT",
    })
      .then((resp) => {
        console.log(resp);
      })
      .catch((err) => {
        console.log(err);
      });
  };

  const boardUpdate = () => {
    console.log("Update: " + board);
    axios({
      url: `${VITE_VUE_API_URL}notice/update`,
      method: "POST",
      data: board.value,
    })
      .then((resp) => {
        console.log(resp);
        router.push({ name: "board-detail", params: { no: board.value.no } });
      })
      .catch((err) => {
        console.log(err);
      });
  };

  const boardDelete = (no) => {
    console.log("Delete : " + no);
    axios({
      url: `${VITE_VUE_API_URL}notice/delete/${no}`,
      method: "DELETE",
    })
      .then((resp) => {
        console.log(resp);
        router.push({ name: "board-list" });
      })
      .catch((err) => {
        console.log(err);
      });
  };

  return {
    page,
    boardList,
    getBoardList,
    board,
    boardInsert,
    boardDetail,
    getBoard,
    boardUpdate,
    page,
    upload_image,
    getThree,
    threeNotice,
    boardDetailReadCount,
    boardDelete,
  };
});
