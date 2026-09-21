# Architecture Review — Modular Monolith (English, 2026-09-21)

**Scope:** `src/main/java/com.bamartrod.monolith` 106 files, 5 bounded contexts + `shared`, Java 25, Hexagonal per context, 48 tests, JaCoCo 90%.

All `src/main/java` classes have English Javadoc with `@author Brandon Martinez`.

## 1. Current Strengths
- **Hexagonal purity:** `domain` has zero `org.springframework/jakarta.persistence/com.fasterxml.jackson` imports — verified `HexagonalArchitectureTest:8` GREEN. `application` depends only on domain ports.
- **Sealed domain models:** `SalesModels`, `ClientModels`, `AccountModels` are `sealed interface + records` with exhaustive `switch` — Java 25.
- **Bounded-context screaming:** `client, sales, account, cases, marketing, shared` — explicit, no generic `*Module`. Pilot `sales` already hexagonal (`domain/application/ports/adapter/mapper`).
- **Arch tests as gate:** 13 hexagonal rules + `ModularityArchitectureTest` (Modulith, skipped on Boot 4), source-based for Java 25 bytecode.
- **Test-infra isolation:** `InMemorySiebelAdapter` with `@Profile("test")` vs `HttpSiebelAdapter` `@Profile("!test")` + H2 `MODE=Oracle` for `MonolithSmokeTest`.

## 2. Weaknesses = Residual Duplication (in migration)
- **Controller mapping:** already solved via MapStruct (`SalesApiMapper`, `AccountApiMapper`, `ClientMapper` with `componentModel=spring`, `unmappedTargetPolicy=ERROR`). No manual `api.setX(d.x())`.
- **Oracle readers:** `OracleViewReader` generic still in `platform/persistence` for legacy contexts; target is per-context `OracleSalesReader`, `OracleAccountReader` etc. (sales pilot done).
- **Services vs Use Cases:** `SalesService` still exists alongside `GetSalesTerritory` use cases; `AccountCommandService` now delegates to `Get*` use cases and `ClientExistencePort`. Full deletion of `*Service` after all contexts migrate.
- **InMemory gateways:** `InMemorySiebelAdapter` remains as test double; generic `InMemoryStore` not needed (single Siebel adapter).

## 3. Pattern Decisions (Updated)

| Pattern | Decision | Reason |
|---|---|---|
| Modular Monolith + Bounded Context + Hexagonal | **INTRODUCED** | `shared` only kernel+web, per-context `domain/ports/adapter` |
| Use Case (GetSalesTerritory etc.) | **INTRODUCED** (sales pilot) | Explicit business intent, replaces God Service |
| Data Mapper (MapStruct) | **INTRODUCED** | Compile-time, `ERROR` on unmapped OpenAPI field |
| ACL Siebel | **KEEP per context** | `cases/adapter/out/siebel` vs `marketing/adapter/out/siebel` separate |
| CQRS | **INTRODUCED** only `account` | `GetAccount` vs `ExtractClient` |
| Domain Events (`@TransactionalEventListener`) | **KEEP** | `ClientExtractedEvent` with `AFTER_COMMIT` + `REQUIRES_NEW` + `ClientExtractionFailedEvent` |
| Repository extra layer | **NOT INTRODUCED** | `ClientReader` port is sufficient |
| Strategy, Template Method, Facade, Mediator, Specification | **NOT INTRODUCED** | No `if/switch` to justify; explicit methods cheaper |

See `ARCHITECTURE.md` for target layout and `AGENTS.md` for verified commands.

## 4. Documentation

- `ARCHITECTURE.md` — target hexagonal per context (sales pilot) + migration state (English, `@author` required).
- `AGENTS.md` — verified `mvn` commands, `shared` vs `platform` deprecation, MapStruct, H2 test setup.
- `BACKLOG.md` — live backlog (106 files, English Javadoc).

All docs are now in English and reflect `com.bamartrod.monolith` (not `com.example.monolith`).
