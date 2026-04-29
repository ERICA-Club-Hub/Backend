# Hanjari

This project is Club Information Platform of Hanyang University ERICA

## Tech Stack

- **Java 21**, **Spring Boot 3.4.1** (Jakarta EE — use `jakarta.*` imports, not `javax.*`)
- **QueryDSL 5.1.0** (jakarta variant)
- **MySQL 8+**, **Redis**, **AWS S3**

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
- **Error Code**: `ErrorStatus` enum contains error type and message; domain-specific codes use `<DOMAIN><HTTP_STATUS>` format (e.g., `CLUB404`)

### Exception Handling

All business exceptions extend `GeneralException` wrapping an `ErrorStatus`:
```
throw new GeneralException(ErrorStatus._CLUB_NOT_FOUND);
```

### DTO Conventions

- **Response DTOs**: Java `record` types with `@Schema` annotations for Swagger
- **Request DTOs**: Jakarta Validation annotations (`@NotBlank`, `@NotNull`, `@Email`)
- Static factory methods (`of()`, `from()`) to convert from entities or query results

### Custom Repository Pattern

QueryDSL repositories follow `CustomRepository` interface + `CustomRepositoryImpl` convention:

```
club/domain/repository/
├── ClubSearchRepository.java         # interface
└── ClubSearchRepositoryImpl.java     # @Repository, uses JPAQueryFactory
```

Use `Projections.constructor()` to fetch specific columns as projection records instead of full entities.

## Database

- **MySQL 8+** via Hibernate (no Flyway/Liquibase — schema managed by `JPA_DDL` env var)
- Local dev stack available via `dev-docker-compose.yml` (MySQL, Redis, app, Nginx)
