## gif의 로딩이 느릴 수 있습니다

# 1. 기획 의도

유튜브 채널 등 요즘에는 많은 사람들이 시청하는 컨텐츠가 굉장히 많다.

이러한 시대에 어떤 유튜버, 인플루언서들이 어떠한 가게, 관광지 등에 방문했는지 궁금해 하는 사람들이 많다.

관광지와 해당 관광지를 방문한 인플루언서를 결합하여 한눈에  볼 수 있는 서비스를  제공하는 서비스를 구축하면 많은 사람들에게 도움이 될것이라 생각하여 기획하였다.

동시에 인플루언서 랭킹을 구현하여 사람들이 어떤 인플루언서에 관심이 많은지도 알 수 있게 하였다.

__ __ __ __ __ __ __

# 2. 아키텍쳐

![image.png](./image.png)

- SpringBoot를 이용하여 백엔드를 구축
- MyBatis를 RDBMS로 이용하고 Mybatis를 이용하여 Mapping
- Vue3로 SPA구축. Bootstrap5로 디자인.
- Redis를 이용하여 인플루언서 랭킹 시스템 구현. Docker환경에서 작동
- Node.js의 express 라이브러리 이용하여 이미지 서버 구현
- Python과 셀레니움을 이용하여 크롤링

## GPT활용 및 크롤링

![crawling](https://github.com/bardisue/InspiTrip/assets/64695312/3ceb997f-8451-4d99-87b6-962a4145198a)
__ __ __ __ __ __ __

# 3. 유즈케이스 및 다이어그램

[유즈케이스]
![use](https://github.com/bardisue/InspiTrip/assets/64695312/8e490f9c-62eb-46d8-9841-4eedb98e76c5)

[클래스 다이어그램]
![class](https://github.com/bardisue/InspiTrip/assets/64695312/6072ded9-6f2c-4d72-92a6-cfc49e9155e3)

[DB 스키마]
![db](https://github.com/bardisue/InspiTrip/assets/64695312/af6a5a21-379d-47db-802f-b399f9b95b9d)

__ __ __ __ __ __ __

# 4. 기능 시현

https://youtu.be/iOJfYANKap8
