# ms-banking-example — Modular Monolith (Java 25 · Spring Boot 4)

> Banking modular monolith consolidating 59 microservices into 5 bounded contexts + shared kernel. Package by Component, Hexagonal per context, contract-first OpenAPI, MapStruct, and transactional outbox-ready events.

**All `src/main/java` classes have English Javadoc with `@author Brandon Martinez`.**

## Overview

This repository is a **modular monolith** for a banking domain. Each bounded context (`client`, `account`, `sales`, `cases`, `marketing`) is an independent application inside the same process, sharing only what is truly shared (`shared/kernel` + `shared/web`). The architecture follows **Domain-Driven Design + Hexagonal (Ports & Adapters)** with explicit use cases, compile-time mappers, and view-based reads.

The codebase was migrated from a horizontal `domain/application/infrastructure/interfaces` layout and a global `platform` to the target structure below. `mvn verify` is green (48 tests, JaCoCo 90%).

## Architecture — Target

```text
com.bamartrod.monolith/

├── MonolithApplication.java

├── shared/                         # Only truly shared
│   ├── kernel/
│   │   ├── ClientId.java           (validated VO ^[A-Za-z0-9_-]{1,64}$)
│   │   ├── CorrelationId.java      (generate / of)
│   │   ├── DomainError.java        (sealed NotFound/Validation/Conflict)
│   │   ├── DomainException.java
│   │   └── Result.java             (sealed Either, map/flatMap)
│   └── web/
│       ├── CorrelationFilter.java  (OncePerRequestFilter, HIGHEST_PRECEDENCE)
│       ├── CorrelationContext.java (resolve header)
│       ├── ResponseDispatcher.java (Result → ResponseEntity)
│       └── GlobalExceptionHandler.java (RFC 7807)

├── sales/                          # Hexagonal pilot — reference
│   ├── domain/ SalesTerritory, SalesUser, ProductIndicators
│   ├── application/ GetSalesTerritory, GetSalesUser, GetProductIndicators
│   ├── ports/in  GetSalesTerritoryUseCase ...  / ports/out SalesReader
│   ├── adapter/in/web  SalesController  (@Profile("hexagonal"))
│   │            out/persistence OracleSalesReader (JdbcClient + ViewTarget.SALES)
│   └── mapper/ SalesApiMapper (MapStruct)

├── client/  # Strangler (clientcore + clientenrichment)
│   ├── domain/ Client, ClientModels (RowMapper canonical)
│   ├── application/ GetClient, ExtractClient
│   ├── ports/ ClientReader/Writer
│   ├── adapter/in/web  ClientController (implements ClientApi), LegacyClientController
│   │            out/persistence OracleClientReader, JpaClientWriter
│   │            out/integration SiebelClientAdapter (ACL)
│   └── mapper/ ClientMapper (MapStruct)

├── account/ # CQRS
│   ├── domain/ Account, Expedient, Financial
│   ├── application/query  GetAccount, GetExpedient, GetFinancial
│   │            command ExtractClient, UpdateFinancial (via ClientExistencePort + event)
│   ├── ports/out AccountReader
│   ├── adapter/out/persistence OracleAccountReader
│   └── mapper/ AccountApiMapper

├── cases/   # Siebel ACL per context
│   └── adapter/out/siebel SiebelCaseAdapter
└── marketing/
    └── adapter/out/persistence OracleMarketingReader

platform/  # Deprecated — remains for legacy reads (OracleViewReader, ViewTarget, SiebelGateway), to be removed after per-context migration
```

**Rules:** each context is independent, domain knows no Spring/JPA/Jackson, use cases know no HTTP, adapters own infrastructure, dependencies point inward, DTOs never cross into domain, inter-context via explicit port or `AFTER_COMMIT` event.

## Tech Stack

| Layer | Choice |
|---|---|
| Language | Java 25 (`release 25`), records, sealed interfaces |
| Framework | Spring Boot 4.0.0, Spring Data JPA, JdbcClient |
| DB | Oracle (prod `ojava`), H2 `MODE=Oracle` for tests |
| API | OpenAPI 3.0 contract-first, `openapi-generator 7.8.0` (`interfaceOnly+delegatePattern+useRecords+useSpringBoot3`) |
| Mapping | MapStruct 1.6.3 (`componentModel=spring`, `unmappedTargetPolicy=ERROR`) |
| Test | JUnit 5.11.4 (pinned for Pitest 1.30), ArchUnit, Testcontainers `oracle-free`, H2, Modulith 1.4.1, JaCoCo 0.8.15, Pitest |

## Project Structure

```
src/main/java/com/bamartrod/monolith/
src/main/resources/api/  client-api.yaml (unified), account-api.yaml, sales-api.yaml, cases-api.yaml, marketing-api.yaml, enrichment-api.yaml (legacy)
src/main/resources/application.yaml  (env-driven: DATABASE_URL, etc.)
src/test/java  — 48 tests + MonolithSmokeTest (real context with H2)
```

## Getting Started

**Requirements:** JDK 25+, Maven 3.9+

```bash
# Generate OpenAPI contracts
mvn generate-sources

# Run tests (no Docker needed, H2 in-memory)
mvn test

# Full verification (JaCoCo 90% gate)
mvn verify

# Run app (needs Oracle or H2 for smoke)
mvn spring-boot:run
# or: java -jar target/monolith-0.1.0-SNAPSHOT.jar
```

**Environment variables (all optional with defaults):**

```bash
SERVER_PORT=8080
DATABASE_URL=jdbc:oracle:thin:@//oracle:1521/FREEPDB1
DATABASE_USERNAME=monolith
DATABASE_PASSWORD=changeit
JPA_DDL_AUTO=validate
```

For local H2 smoke, tests already use `src/test/resources/application-test.properties` (`jdbc:h2:mem:testdb`).

## API Contracts

OpenAPI specs are the source of truth. Generated interfaces are in `com.bamartrod.monolith.<context>.api` and `com.bamartrod.monolith.interfaces.<context>.api` (legacy).

| Context | Spec | Base path | Controller |
|---|---|---|---|
| client (unified) | `client-api.yaml` | `/api/v1/clients` | `ClientApiController implements ClientApi` |
| clientenrichment (legacy) | `enrichment-api.yaml` | `/api/v1/client-enrichment` | `EnrichmentApiController` |
| sales | `sales-api.yaml` | `/api/v1/sales` | `SalesController` / `SalesApiController` |
| account | `account-api.yaml` | `/api/v1/account` | `AccountController` |
| cases | `cases-api.yaml` | `/api/v1/cases` | `CasesApiController` |
| marketing | `marketing-api.yaml` | `/api/v1/marketing` | `MarketingApiController` |
| legacy XML | — | `/ms-client-query/client/*` | `LegacyClientXmlController` |

Generate after editing YAML:

```bash
mvn generate-sources
```

Generated `**/api/**` and `**/model/**` are excluded from JaCoCo/Pitest.

## Testing

```bash
mvn test                                   # 48 tests
mvn -Dtest=SalesApiControllerTest test
mvn -Dtest=HexagonalArchitectureTest test  # 13 source-based rules (JDK 25, no ASM)
mvn -Dtest=ModularityArchitectureTest test # Modulith (skipped on Boot 4 until 2.x)
```

- **HexagonalArchitectureTest** — slices must not depend on each other, `shared` never imports bounded contexts, domain is framework-free.
- **MonolithSmokeTest** — boots real `ApplicationContext` with H2 (`profiles=test`, no `@SpringBootTest` to stay on JUnit 5.11), asserts `clientService`, `salesService`, `oracleViewReader`, `inMemorySiebelAdapter`.
- **JaCoCo gate 90% LINE** on `com.bamartrod.monolith.**` excluding `**.api.**`/`**.web.**`/`**/adapter/**`.

## Patterns

| Pattern | Decision |
|---|---|
| Modular Monolith, Bounded Context, Hexagonal, Package by Component | Yes |
| Use Case (GetSalesTerritory etc.) | Yes (sales pilot) |
| MapStruct Data Mapper | Yes (`ERROR` on unmapped) |
| ACL (Siebel per context) | Yes |
| CQRS | Yes only `account` |
| Domain Events `AFTER_COMMIT` + `FinancialAuditListener` | Yes |
| Generic Repository, Strategy, Template Method, Facade | No (yet) |

> **No abstraction because two classes look similar — abstraction only for real variation or boundary.**

## Author

All production classes are documented in English with:

```java
/**
 * English description of what the class does.
 *
 * @author Brandon Martinez
 */
```

## License

Internal example for Banco de Bogotá — not for production use without review.
