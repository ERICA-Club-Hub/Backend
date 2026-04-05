# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Build & Run Commands

```bash
# Build (skip tests)
./gradlew clean build -x test

# Run all tests
./gradlew test

# Run a single test class
./gradlew test --tests kr.hanjari.backend.domain.club.application.query.impl.ClubQueryServiceImplTest

# Run a single test method
./gradlew test --tests "kr.hanjari.backend.domain.club.ClubTest.someMethod"
```

## Required Environment Variables

The app requires these env vars (typically set in `.env`):

| Variable | Purpose |
|----------|---------|
| `PROFILE` | Active Spring profile (`local`, `dev`, `prod`) |
| `MYSQL_URL`, `MYSQL_USERNAME`, `MYSQL_PASSWORD` | Database connection |
| `JPA_DDL` | Hibernate ddl-auto (`create`, `update`, `validate`, `none`) |
| `AWS_REGION`, `AWS_ACCESS_KEY`, `AWS_SECRET_KEY`, `S3_BUCKET_NAME` | S3 file storage |
| `REDIS_HOST`, `REDIS_PORT` | Redis cache |
| `MAIL_USERNAME`, `MAIL_PASSWORD` | Gmail SMTP |
| `JWT_SECRET_KEY`, `JWT_EXPIRATION_TIME` | JWT authentication |
| `LOGIN_URL`, `SERVICE_ADMIN`, `UNION_ADMIN` | Auth configuration |
| `SLACK_WEBHOOK_URL` | Slack notifications |

## Architecture

The codebase follows a **domain-driven layered architecture** with CQRS inside each domain.

### Package Structure

```
kr.hanjari.backend
├── domain/           # Business domains (club, auth, activity, announcement, document, faq, file, ...)
├── infrastructure/   # External integrations (jwt, s3, mail, crawl, slack)
├── global/           # Cross-cutting concerns (config, security, payload, exception)
└── monitoring/       # Metrics and monitoring
```

### Within Each Domain

Each domain (e.g., `domain/club/`) follows this internal structure:

```
club/
├── presentation/
│   ├── controller/       # REST controllers
│   └── dto/              # Request/Response DTOs
├── application/
│   ├── command/          # Write operations
│   │   ├── ClubCommandService.java (interface)
│   │   └── impl/ClubCommandServiceImpl.java
│   └── query/            # Read operations
│       ├── ClubQueryService.java (interface)
│       └── impl/ClubQueryServiceImpl.java
└── domain/
    ├── entity/           # JPA entities
    ├── repository/       # JPA + QueryDSL repositories
    └── enums/            # Domain enums
```

### Key Patterns

- **CQRS**: `application/command/` handles writes; `application/query/` handles reads
- **QueryDSL**: Used alongside JPA repositories for complex queries
- **BaseEntity**: All entities extend `BaseEntity` which provides `createdAt` / `updatedAt` via JPA auditing
- **Global API response**: Standardized via `global/payload/` (response wrapper + error codes)
- **Global exception handling**: `ExceptionAdvice` maps domain exceptions to HTTP responses

### Infrastructure Layer

- **JWT**: Stateless authentication; filter registered in `SecurityConfig`
- **S3**: File uploads via Spring Cloud AWS
- **Redis**: Caching
- **Slack**: Webhook-based notifications
- **Crawl**: Web crawler (used for club data)

### Environment-specific Config

- `application-local.properties` — local dev with local AWS credentials
- `application-dev.properties` — dev server AWS credentials
- `application-prod.properties` — production (Swagger disabled, stricter logging)

CORS and Swagger behavior differ per profile (`DevCorsConfig` vs `ProdCorsConfig`; Swagger disabled in prod).

## Database

- **MySQL 8+** via Hibernate (no Flyway/Liquibase — schema managed by `JPA_DDL` env var)
- Local dev stack available via `dev-docker-compose.yml` (MySQL, Redis, app, Nginx)
