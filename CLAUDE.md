# Hanjari

This project is Club Information Platform of Hanyang University ERICA

## Development Principles

### Think Before Coding
- State assumptions explicitly. If uncertain, ask.
- If multiple interpretations exist, present them — don't pick silently.
- If a simpler approach exists, say so. Push back when warranted.
- If something is unclear, stop. Name what's confusing. Ask.

### Simplicity First
- No features beyond what was asked.
- No abstractions for single-use code.
- No "flexibility" or "configurability" that wasn't requested.
- If you write 200 lines and it could be 50, rewrite it.

### Surgical Changes
- Don't "improve" adjacent code, comments, or formatting.
- Don't refactor things that aren't broken.
- Match existing style, even if you'd do it differently.
- If you notice unrelated dead code, mention it — don't delete it.
- Remove imports/variables/functions that YOUR changes made unused.
- Every changed line should trace directly to the user's request.

## Tech Stack

- **Java 21**, **Spring Boot 3.4.1** (Jakarta EE — use `jakarta.*` imports, not `javax.*`)
- **QueryDSL 5.1.0** (jakarta variant)
- **MySQL 8+**, **Redis**, **AWS S3**

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
│   ├── query/            # Read operations
│   │   ├── ClubQueryService.java (interface)
│   │   └── impl/ClubQueryServiceImpl.java
│   └── event/            # Spring application events (optional)
│       ├── SomethingHappenedEvent.java      # record, published via ApplicationEventPublisher
│       └── SomethingHappenedEventListener.java  # @EventListener handler
└── domain/
    ├── entity/           # JPA entities
    ├── repository/       # JPA + QueryDSL repositories
    └── enums/            # Domain enums
```

### Key Patterns

- **CQRS**: `application/command/` handles writes; `application/query/` handles reads
- **QueryDSL**: Used alongside JPA repositories for complex queries
- **BaseEntity**: All entities extend `BaseEntity` (`domain/common/BaseEntity.java`) which provides `createdAt` / `updatedAt` via JPA auditing
- **Global API response**: Standardized via `global/payload/` (response wrapper + error codes)

### Exception Handling

- All business exceptions extend `GeneralException` wrapping an `ErrorStatus`
- `ExceptionAdvice` (`@RestControllerAdvice`) catches all exceptions and returns standardized `ApiResponse`
- Error codes: `ErrorStatus` enum, format `<DOMAIN><HTTP_STATUS>` (e.g., `CLUB404`)

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

### Authentication & Authorization

JWT-based stateless auth. `JwtAuthenticationFilter` extracts the token and populates `SecurityContext` before each request.

Use `@AuthenticationPrincipal CustomUserDetails` in controllers to access auth info (`clubId`, `role`).
Roles: `SERVICE_ADMIN`, `UNION_ADMIN`, `CLUB_ADMIN`.
`clubId` is `0` for `SERVICE_ADMIN` and `UNION_ADMIN`.


## Database

- **MySQL 8+** via Hibernate (no Flyway/Liquibase — schema managed by `JPA_DDL` env var)
- Local dev stack available via `dev-docker-compose.yml` (MySQL, Redis, app, Nginx)
