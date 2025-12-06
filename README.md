---
# 💊 DBEM - 백엔드
Spring Boot 기반으로 개발된 폐의약품 수거 대행 서비스의 백엔드입니다.

---
## 📌 기술 스택
- Java 21
- Spring Boot 3.x
- Spring Security + JWT (Access/Refresh)
- Redis (Save Refresh-Token)
- PostgresSQL (User, SendEmail, Booking, Review, Point, Region)
- SendGrid
- Swagger
- Validation
- JPA / Batch
- Lombok

---

## 📋 주요 기능
### 1. 🔐 회원가입 & 로그인
- Spring-Security 기반 인증/인가
- Access-Token + Refresh-Token 구현
- Refresh-Token은 Redis에 저장 & 재발급 가능

### 2. 📫 e-mail 인증
- SendgGrid Email API를 이용한 e-mail 인증
- 입력된 e-mail 주소로 인증 e-mail 전송
  - e-mail 본문에 유효한 토큰 및 인증 링크를 첨부
  - e-mail 인증은 토큰이 유효한지 확인 후 유효하다면 인증 완료 처리
- e-mail 인증 완료 확인 가능
- e-mail 인증 관련 데이터 삭제
  1. **인증 완료** 후 **10일 지난 데이터**
  2. **인증 미완료** 상태이면서 인증 e-mail 발송 후 **1시간 지난 데이터** 삭제

### 3. 🚕 폐의약품 수거 시스템
- 약품 수거 예약 CRUD API
- 약품 수거 예약 수락 및 완료 기능
  - 수락: 예약 위치와의 거리차가 **5km 이내**일 시, 수락 가능
  - 완료: 예약 위치와의 거리차가 **100m 이내**일 시, 완료 가능
- 약품 수거 예약 / 완료 별로 포인트 지급 및 차감
  - 약품 수거 예약 시 100 포인트 차감
  - 약품 수거 완료 후 100 포인트 지급
- 약품 수거 예약을 예약 위치 별로 조회 가능
- 유저 별로 약품 수거 현황 조회 가능

### 4. 🏷️ 약품 추천 API
- 입력받은 증상 및 주의사항을 바탕으로 약품 추천
- FastAPI 모델 서버와 연동하여 추천 (RAG 기반 DB 검색을 이용)

### 5. 📝 약품 리뷰
- 약품 리뷰 CRUD API
- 유저 별로 자신의 리뷰 조회 및 검색 가능

### 6. 🔎 행정구역 & 공공데이터 조회
- 데이터 베이스에 저장된 행정 지역 조회
- 의약품 공공데이터 조회

---
📁 주요 디렉터리 구조
```bash
src/main/java/com/example/
│
├── batch/ # Batch, Step, Job 설정
│ 
├── config/ # Security, CORS 설정
│ 
├── controller/ # REST API
│ 
├── dto/ # 요청 및 응답 DTO
│ 
├── model/ # JPA Entity
│ 
├── repository/ # JPA Repository
│ 
├── service/ # 비즈니스 로직
│ 
└── security/ # JWT, OAuth, UserDetailsImpl, Exception
```

---

## 🧪 테스트 및 실행

### 1. 의존성 설치
```bash
./gradlew build
```

### 2. 로컬 실행
```bash
./gradlew bootRun
```

### 3. 프로필 설정
- application.yml 사용
- PostgresSQL, Redis 연결 정보 포함

---

## 📡 주요 API 예시
#### 🔐 회원가입
POST /api/user/signup

#### 🚕 수거 예약 수락
POST /api/booking/{bookingId}/accept

#### 📝 약품 리뷰 생성
POST /api/review

---
