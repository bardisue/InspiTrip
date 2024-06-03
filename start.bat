@echo off
cd ./image-express
start node server.js
cd ..
cd ./vue-project
start npm run dev
cd ..
start chrome.exe http://localhost:5173