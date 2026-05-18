# 📈 StockTalk — 자바 로컬 주식 종목 토론방 시스템

> Java + MySQL(JDBC) 기반의 주식 종목별 토론 게시판 미니 프로젝트

<br>

## 🗂️ 프로젝트 개요

| 항목 | 내용 |
|------|------|
| 개발 기간 | 3일 |
| 개발 환경 | Java 17, MySQL 8.0 |
| 아키텍처 | Layered Architecture (MVC 유사 구조) |
| UI | Java Swing |
| DB 연동 | JDBC (순수 Java, Spring 미사용) |

주식 종목별로 게시글을 작성하고 댓글을 달 수 있는 **로컬 환경 토론방 시스템**입니다.
외부 주가 정보를 수집해 DB에 동기화하고, 3개 테이블 간 관계형 구조(1:N:N)를 JDBC로 직접 다루는 것에 초점을 맞췄습니다.

<br>

## 🏗️ 아키텍처

```
stocktalk-project/
├── src/
│   ├── config/
│   │   ├── DBConnection.java       # 싱글톤 기반 DB 커넥션 관리
│   │   └── StockAPIClient.java     # 외부 주가 API 수집 (Fallback 포함)
│   ├── model/
│   │   ├── dto/
│   │   │   ├── StockDTO.java
│   │   │   ├── PostDTO.java
│   │   │   └── CommentDTO.java
│   │   └── dao/
│   │       ├── StockDAO.java
│   │       ├── PostDAO.java
│   │       └── CommentDAO.java
│   ├── view/
│   │   ├── MainFrame.java          # 3단 분할 메인 윈도우 (JSplitPane)
│   │   └── WriteDialog.java        # 글쓰기/댓글 모달 팝업
│   └── controller/
│       └── StockController.java    # View ↔ DAO 이벤트 중재
└── lib/
    └── mysql-connector-java-8.0.x.jar
```

계층 간 의존 방향: `View → Controller → DAO → DB`
각 클래스는 단일 책임 원칙(SRP)을 준수합니다.

<br>

## 🗄️ ERD

```
Stock (1) ──────< Post (N) ──────< Comment (N)
```

| 테이블 | 역할 |
|--------|------|
| `Stock` | 종목 마스터 + 실시간 종가 동기화 대상 |
| `Post` | 종목별 토론 게시글 |
| `Comment` | 게시글별 댓글 |

외래키에 `ON DELETE CASCADE`를 적용해 상위 데이터 삭제 시 하위 데이터가 자동으로 정리됩니다.

<br>

## ⚙️ 실행 방법

### 1. 데이터베이스 세팅

```sql
CREATE DATABASE stock_db CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE stock_db;

CREATE TABLE Stock (
    stock_code VARCHAR(10) NOT NULL,
    stock_name VARCHAR(50) NOT NULL,
    current_price INT DEFAULT 0,
    PRIMARY KEY (stock_code)
);

CREATE TABLE Post (
    post_id INT AUTO_INCREMENT NOT NULL,
    stock_code VARCHAR(10) NOT NULL,
    title VARCHAR(100) NOT NULL,
    content TEXT NOT NULL,
    writer VARCHAR(30) DEFAULT '익명',
    post_password VARCHAR(20) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (post_id),
    FOREIGN KEY (stock_code) REFERENCES Stock(stock_code) ON DELETE CASCADE
);

CREATE TABLE Comment (
    comment_id INT AUTO_INCREMENT NOT NULL,
    post_id INT NOT NULL,
    reply_content VARCHAR(500) NOT NULL,
    reply_writer VARCHAR(30) DEFAULT '익명',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (comment_id),
    FOREIGN KEY (post_id) REFERENCES Post(post_id) ON DELETE CASCADE
);

INSERT INTO Stock (stock_code, stock_name) VALUES
('005930', '삼성전자'),
('000660', 'SK하이닉스'),
('000270', '기아'),
('035720', '카카오');
```

### 2. DB 연결 정보 수정

`src/config/DBConnection.java` 파일에서 아래 항목을 본인 환경에 맞게 변경합니다.

```java
private static final String PASSWORD = "your_password";
```

### 3. 빌드 및 실행

```bash
# lib 폴더에 mysql-connector-java 추가 후
javac -cp lib/mysql-connector-java-8.0.x.jar -d out src/**/*.java
java -cp out:lib/mysql-connector-java-8.0.x.jar view.MainFrame
```

<br>

## 🖥️ 주요 기능

- **종목 목록 조회** — 앱 실행 시 외부 API로 실시간 종가를 수집해 DB 갱신 후 표시
- **게시글 CRUD** — 종목별 게시글 작성 / 조회 / 삭제 (비밀번호 단순 인증)
- **댓글** — 게시글별 댓글 작성 / 목록 조회
- **복합 조회** — `LEFT JOIN + GROUP BY`로 게시글 목록에 댓글 수를 함께 표시

<br>

## 🔒 기술적 의사결정

**PreparedStatement 강제 사용**
모든 쿼리에서 문자열 연결(`+` 연산자) 방식을 금지하고 `setXXX()` 바인딩만 허용합니다. SQL Injection을 원천 차단합니다.

**try-with-resources 적용**
`Connection`, `PreparedStatement`, `ResultSet` 모두 자동 `close()`를 보장해 리소스 누수를 방지합니다.

**API Fallback 전략**
외부 주가 API 호출 실패 시 하드코딩된 기본값으로 대체해 네트워크 상태에 무관하게 앱이 정상 동작합니다.

> ⚠️ `post_password`는 학습 목적의 단순 구현입니다. 실제 서비스라면 bcrypt 등 단방향 해시를 적용해야 합니다.

<br>

## 👥 역할 분담

| 담당 | 작업 범위 |
|------|-----------|
| A (Model / Infrastructure) | ERD 설계, DDL, DBConnection, StockAPIClient, 전 도메인 DAO |
| B (View / Controller) | Swing UI 레이아웃, ActionListener 연결, StockController, 예외 처리 모달 |

<br>

## 📌 개선 여지 (학습 목적 이후 확장 포인트)

- 비밀번호 bcrypt 해싱 적용
- 커넥션 풀 도입 (HikariCP 등)
- 게시글 페이지네이션 (`LIMIT / OFFSET`)
- 단위 테스트 (JUnit) 추가
