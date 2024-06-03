const express = require("express");
const fs = require("fs");
const path = require("path");
const cors = require("cors");
const multer = require("multer");

const app = express();
const PORT = 3000;

// CORS 설정
app.use(cors());

// 이미지 업로드를 위한 multer 설정
const upload = multer({ dest: "uploads/" });

// 이미지 업로드를 처리하는 엔드포인트
app.post("/upload/:folder", upload.single("image"), (req, res) => {
    const folder = req.params.folder;

    // 요청으로부터 이미지 파일을 받음
    const imageFile = req.file;
    console.log(imageFile);

    if (!imageFile) {
        return res.status(400).send("No image uploaded");
    }

    const imageName = Math.random().toString(36).substring(7) + path.extname(imageFile.originalname);
    const uploadPath = `./uploads/${folder}`;

    // 폴더가 없는 경우 폴더 생성
    if (!fs.existsSync(uploadPath)) {
        fs.mkdirSync(uploadPath, { recursive: true });
    }

    // 이미지를 서버에 저장
    fs.renameSync(imageFile.path, `${uploadPath}/${imageName}`);

    // 저장된 이미지 파일 이름을 응답으로 보냄
    res.send(imageName);
});

// 이미지를 제공하는 엔드포인트
app.get("/:folder/:filename", (req, res) => {
    const folder = req.params.folder;
    const filename = req.params.filename;
    const filePath = path.join(__dirname, `./uploads/${folder}/${filename}`);

    // 파일이 존재하는지 확인
    fs.exists(filePath, (exists) => {
        if (exists) {
            // 파일을 스트림으로 응답에 보냄
            res.sendFile(filePath);
        } else {
            res.status(404).send("File not found");
        }
    });
});

app.get("/:folder/:subfolder/:filename", (req, res) => {
    const folder = req.params.folder;
    const subfolder = req.params.subfolder;
    const filename = req.params.filename;
    const filePath = path.join(__dirname, `./uploads/${folder}/${subfolder}/${filename}`);

    fs.exists(filePath, (exists) => {
        if (exists) {
            res.sendFile(filePath);
        } else {
            res.status(404).send("File not found");
        }
    });
});

// 서버 시작
app.listen(PORT, () => {
    console.log(`Server is running on port ${PORT}`);
});
