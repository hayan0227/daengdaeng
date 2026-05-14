# 댕댕여지도

> 반려견 동반 가능 장소를 지도 기반으로 탐색하는 웹 서비스

<br>

## 프로젝트 소개

반려견과 함께 갈 수 있는 카페, 음식점, 공원, 숙소 등을 지도에서 쉽게 찾을 수 있는 서비스입니다.  
반려견 크기, 동반 공간(실내/테라스/야외), 케이지 여부 등 세부 조건으로 필터링이 가능하며,  
리뷰 작성과 즐겨찾기 기능을 통해 나만의 장소 목록을 관리할 수 있습니다.

<br>

## 🛠 기술 스택

| 구분 | 기술 |
|------|------|
| Frontend | HTML, CSS, JavaScript (Vanilla) |
| Backend | Java 21, Spring Boot 3.5 |
| Database | MySQL 8.0 |
| ORM | Spring Data JPA / Hibernate |
| 인증 | JWT (JSON Web Token) |
| 지도 | Kakao Maps API |
| 이미지 | Kakao 이미지 검색 API |
| 데이터 | 공공데이터포털 반려동물 동반 가능 문화시설 |

<br>

##  주요 기능

### 지도 기반 장소 탐색
- 카카오맵 기반 현재 위치 주변 반려견 동반 가능 장소 표시
- 카테고리별 필터링 (카페, 음식점, 동물병원, 공원, 숙소, 미용, 반려용품, 동물약국)

### 애견 조건 상세 필터
- 반려견 크기 (소형견 / 중형견 / 대형견 / 전 견종)
- 동반 공간 (실내 / 테라스 / 야외)
- 세부 규정 (케이지 불필요, 케이지 필수, 운동장 보유, 개모차 대여, 펫푸드 판매 등)

### 리뷰 시스템
- 별점 + 텍스트 리뷰 작성
- 본인 리뷰 수정 / 삭제
- 마이페이지에서 내가 쓴 리뷰 모아보기

### 즐겨찾기
- 장소 즐겨찾기 추가 / 제거
- 마이페이지에서 즐겨찾기 목록 카테고리별 조회

### 방문 기록
- 장소 클릭 시 자동으로 방문 기록 저장
- 최근 방문 순으로 정렬, 전체 삭제 가능

### 회원 기능
- 회원가입 시 반려견 정보 (이름, 품종, 나이, 크기) 등록
- 프로필 수정
- JWT 기반 로그인 / 로그아웃

<br>

## 프로젝트 구조

```
daengdaeng/
├── daengdaeng-front/       # 프론트엔드
│   └── daengdaeng_v3.html  # 단일 HTML 파일
└── daengdaeng-server/      # 백엔드 (Spring Boot)
    └── src/
        └── main/
            └── java/com/daengdaeng/daengdaeng_server/
                ├── controller/   # API 컨트롤러
                ├── service/      # 비즈니스 로직
                ├── entity/       # JPA 엔티티
                ├── dto/          # 요청/응답 DTO
                ├── repository/   # JPA 리포지토리
                ├── config/       # Security, CORS 설정
                └── util/         # JWT 유틸
```

<br>

## 실행 방법

### 백엔드
```bash
# application.properties.example을 복사해 application.properties 생성 후 DB 정보 입력
cd daengdaeng-server
./gradlew bootRun
```

### 프론트엔드
```
daengdaeng-front/daengdaeng_v3.html을 VSCode Live Server로 실행
```

<br>

## API 명세

| Method | URL | 설명 | 인증 |
|--------|-----|------|------|
| POST | /api/auth/signup | 회원가입 | ❌ |
| POST | /api/auth/login | 로그인 | ❌ |
| GET | /api/auth/me | 내 정보 조회 | ✅ |
| PUT | /api/auth/me | 내 정보 수정 | ✅ |
| GET | /api/places | 장소 목록 (지도 범위) | ❌ |
| GET | /api/places/{id} | 장소 상세 | ❌ |
| POST | /api/reviews | 리뷰 작성 | ✅ |
| GET | /api/reviews/place/{placeId} | 장소별 리뷰 | ❌ |
| GET | /api/reviews/my | 내가 쓴 리뷰 | ✅ |
| PUT | /api/reviews/{id} | 리뷰 수정 | ✅ |
| DELETE | /api/reviews/{id} | 리뷰 삭제 | ✅ |
| POST | /api/favorites/{placeId} | 즐겨찾기 토글 | ✅ |
| GET | /api/favorites/my | 내 즐겨찾기 | ✅ |
