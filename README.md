# ETA Server

### Environment
* * *
```
- Spring Boot 2.7.5
- jwt-api 0.11.2
- gson 2.8.7
- lombok
- Spring Security
```

### ERD
* * *
<img src="https://github.com/Team-PaceMaker/ETA-SERVER/assets/96538554/fe678b86-0eb7-408a-92b6-ad396f729aff" alt="ETA ERD" width="500px">
<br/> (ERDCloud 사용)

### 구현 목록
* * *
- 이미지 집중 처리
1. 클라이언트에서 이미지를 받아온다.
2. `Pytorch`가 동작하는 플라스크 서버와 통신하여 ***prediction*** 값을 촬영시각과 함께저장한다.
3. 클라이언트로 ***prediction*** 값을 반환한다.

- 집중 기록 관리
1. *Start ETA*로 측정을 시작할 때 attention 객체가 생성된다.
2. *Stop ETA*로 측정을 종료하면 `stopAt` 객체에 시간이 저장된다.
3. 측정을 마치면 총사용 시간(totalTime), 집중 시간(attentionTime), 집중 시간대(attentionTimeSlots) 데이터를 전송한다.
4. 측정중 현재 시각 기준 5분 전부터 현재까지 ***prediction***의 개수를 전송한다. - 도넛 차트 (집중/산만) 그래프에 사용된다.
5. 최대 3주까지의 집중 시간 및 총사용 시간을 전송한다. - 마이페이지 라인 그래프에서 사용된다.

- 인증 인가 (카카오 소셜로그인)
1. 회원가입 시 회원 정보(카카오 id, 카카오 회원 이름) 동의를 받는다.
2. 카카오 인증 서버에서 받아온 access, refresh 토큰을 서비스 내 권한 정보와 함께 앱토큰으로 재변환한다.
3. refresh 토큰을 Database에 저장하여 access 토큰이 만료됐을 때 재발급 받을 수 있도록 한다.
4. 자체 변환한 access, refresh를 클라이언트로 전송하여 인증 및 인가를 수행한다.

### API
[Base Url](http://eta-server.kro.kr:8085/)
<img width="500" alt="API 스펙" src="https://github.com/Team-PaceMaker/ETA-SERVER/assets/96538554/d3494b2e-0e54-412c-8635-d348cd40aab8">

### Design Pattern
* * *
`MVC` 패턴을 적용하여 Model의 중요한 데이터가 예기치 않게 변경되는 것을 방지한다.
클라이언트 요청 > Controller > Service 과정을 거쳐 데이터를 저장하거나 변경한다. 
