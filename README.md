# 📅 Schedule Management Application (JDBC 기반)

## 📌 프로젝트 개요

* Spring Boot와 JDBC Template을 활용한 **일정 관리 백엔드 어플리케이션**
* 사용자로부터 일정(title, 작성자, 비밀번호, 날짜)을 입력받아 저장하고, 이를 조회·수정·삭제할 수 있음
* **MySQL 연동**하여 JDBC을 통해 실제 데이터베이스 기반의 CRUD 구성

---

## 📁 프로젝트 구조

```
schedule/
├── controller
│   └── ScheduleController.java         // REST API 엔드포인트 정의
├── dto
│   ├── ScheduleRequestDto.java         // 일정 등록 요청 데이터 구조
│   └── ScheduleResponseDto.java        // 일정 응답 데이터 구조
├── entity
│   └── Schedule.java                   // Entity
├── repository
│   ├── ScheduleRepository.java         // CRUD 인터페이스
│   └── JdbcTemplateScheduleRepository.java // JDBC 기반 구현체
└── service
    └── ScheduleServiceImpl.java        // 비즈니스 로직 처리 클래스
```

---

## 🔧 클래스 설명

### `Schedule`

* **Entity**
* 식별id, 일정내용, 작성자, 비밀번호, 작성일, 수정일

### `ScheduleRequestDto / ScheduleResponseDto`

* 요청과 응답을 구분한 DTO (Data Transfer Object)
* 비밀번호는 클라이언트 요청에만 사용되고, 응답에서는 제외

### `ScheduleRepository`

* CRUD 메서드 인터페이스
* 메서드명: `createSchedule`(생성), `findAllSchedules`(전체 조회), `findScheduleById`(단건 조회), `updateSchedule`(수정), `deleteSchedule`(삭제)

### `JdbcTemplateScheduleRepository`

* 실제 SQL을 통해 DB와 통신하는 클래스
* `SimpleJdbcInsert`: 생성된 ID를 받아옴
* `RowMapper`를 통해 ResultSet → 객체 매핑 처리

### `ScheduleServiceImpl`

* Controller와 Repository 사이의 중간 계층 역할
* 수정/삭제 시 실제 반영된 행이 없으면 404 예외 발생

### `ScheduleController`

* REST API 엔드포인트 제공
* 요청을 DTO로 받고, 응답도 DTO로 반환

---

## 📘 API 명세서

| Method | URL               | 설명       | 요청 예시 또는 파라미터                                       |
| ------ | ----------------- | -------- | --------------------------------------------------- |
| POST   | `/schedules`      | 일정 생성    | JSON Body (`title`, `username`, `password`, `date`) |
| GET    | `/schedules`      | 전체 조회 | -                                                   |
| GET    | `/schedules/{id}` | 단건 조회 | PathVariable: `id`                                  |
| PUT    | `/schedules/{id}?password={password}` | 일정 수정    | 쿼리파라미터: `title`, `username`                         |
| DELETE | `/schedules/{id}?password={password}` | 일정 삭제    | PathVariable: `id`                                  |

### 📤 요청 예시 (POST)

```json
{
  "title": "회의",
  "username": "Chris",
  "password": "1234",
  "date": "2025-05-14"
}
```

### 📥 응답 예시 (GET)

```json
{
  "id": 1,
  "title": "회의",
  "username": "Chris",
  "date": "2025-05-14"
}
```

---

## 🗄 ERD (Entity Relationship Diagram)

```
┌──────────────────────────────┐
│          schedule            │
├──────────────────────────────┤
│ id             BIGINT (PK)   │
│ contents       VARCHAR(100)  │
│ username       VARCHAR(100)  │
│ password        VARCHAR(10)  │
│ date           VARCHAR(10)   │
│ modified_date  VARCHAR(10)   │
└──────────────────────────────┘
```

* `id`: 식별 id, 자동 증가
* `contents`: 일정 내용
* `username`: 작성자명
* `password`: 비밀번호 (수정/삭제 시 검증)
* `date`: 작성일
* `modified_date`: 수정일 (수정 시 자동 갱신)

---

## 💡 주요 특징

* **JDBC 기반으로 SQL을 직접 작성하며 DB 연동 학습 가능**
* **DTO, Entity, Repository, Service, Controller**로 분리됨
* 비밀번호 인증, 수정일 자동 갱신, 수정/삭제 예외처리 사용
* CRUD 기능 구현
