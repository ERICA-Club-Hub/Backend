# Hanjari

This project is Club Information Platform of Hanyang University ERICA

## Build & Run Commands

```bash
# Build (skip tests)
./gradlew clean build -x test

# Run all tests
./gradlew test
```

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
- **Error Code**: 'ErrorStatus' contains Error type and messages

## Database

- **MySQL 8+** via Hibernate (no Flyway/Liquibase — schema managed by `JPA_DDL` env var)
- Local dev stack available via `dev-docker-compose.yml` (MySQL, Redis, app, Nginx)
