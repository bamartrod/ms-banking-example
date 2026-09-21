# Architecture — Modular Monolith (Java 25) · Hexagonal per Bounded Context

> **Target architecture — 2026-09-21.** Every bounded context is an independent application inside the same process.
> `shared` contains only what is truly shared; `platform/persistence+integration` is being emptied into per-context adapters.
> State: `shared` and `sales` pilot migrated; remaining contexts in incremental migration (`mvn verify` green). All `src/main/java` classes have English Javadoc with `@author Brandon Martinez`.

## Principle

> **Every bounded context must be practically an independent application.**

No global `domain/application/infrastructure/interfaces`, no giant `platform`.

```
monolith/
├── client/
├── account/
├── sales/      # hexagonal pilot — complete
├── cases/
├── marketing/
└── shared/     # only kernel + web
```

## Screaming Layout — Target (DDD + Hexagonal per Context)

```
com.bamartrod.monolith/

├── MonolithApplication.java

├── shared/                                      # Only truly shared
│   ├── kernel/
│   │   ├── ClientId.java        (VO validated ^[A-Za-z0-9_-]{1,64}$)
│   │   ├── CorrelationId.java
│   │   ├── DomainError.java     (sealed NotFound/Validation/Conflict)
│   │   ├── DomainException.java
│   │   └── Result.java          (sealed Either, map/flatMap)
│   └── web/
│       ├── CorrelationFilter.java
│       ├── CorrelationContext.java
│       ├── ResponseDispatcher.java
│       └── GlobalExceptionHandler.java (RFC 7807)

├── sales/                                        # MIGRATED PILOT — reference for others
│   ├── domain/
│   │   ├── SalesTerritory.java   (record, of(field))
│   │   ├── SalesUser.java
│   │   └── ProductIndicators.java
│   ├── application/
│   │   ├── GetSalesTerritory.java  (implements GetSalesTerritoryUseCase)
│   │   ├── GetSalesUser.java
│   │   └── GetProductIndicators.java
│   ├── ports/
│   │   ├── in/  GetSalesTerritoryUseCase, GetSalesUserUseCase, GetProductIndicatorsUseCase
│   │   └── out/ SalesReader
│   ├── adapter/
│   │   ├── in/web/  SalesController.java (driving, implements SalesApi) — @Profile("hexagonal") target
│   │   └── out/persistence/ OracleSalesReader.java (driven, JdbcClient + ViewTarget.SALES)
│   └── mapper/ SalesApiMapper.java (MapStruct, componentModel=spring)

├── client/
│   ├── domain/   Client.java, ClientId, ClientStatus, ClientModels (sealed + RowMapper)
│   ├── application/  (query: GetClient, GetEnrichment / command: ExtractClient)
│   ├── ports/in+out  ClientReader/Writer, ClientEnrichmentReader
│   ├── adapter/in/web  ClientController (implements ClientApi), LegacyClientController
│   │            out/persistence  JpaClientWriter, OracleClientReader (ViewTarget.CLIENT)
│   │            out/integration SiebelClientAdapter (ACL)
│   └── mapper/   ClientApiMapper (MapStruct)

├── account/    # CQRS
│   ├── domain/   Account
│   ├── application/query  GetAccount, GetExpedient, GetFinancial
│   │            command ExtractClient, UpdateFinancial (via shared event or sync port)
│   ├── ports/out AccountReader
│   ├── adapter/out/persistence OracleAccountReader
│   └── mapper/ AccountApiMapper

├── cases/      # ACL Siebel
│   ├── domain/ Case
│   ├── ports/out CaseManagement
│   ├── adapter/out/siebel SiebelCaseAdapter + Mapper
│   └── mapper/

└── marketing/
    ├── domain/ Campaign
    ├── adapter/out/persistence OracleMarketingReader
    └── mapper/

# DEPRECATED but still active for compatibility (being emptied):
platform/
├── kernel/  -> migrated to shared/kernel (copy, will be removed)
├── web/     -> migrated to shared/web
├── persistence/OracleViewReader, ViewTarget  -> move to adapter/out/persistence per context
└── integration/siebel, homologation         -> move to adapter/out/integration per context
```

`src/main/resources/api/` → `client.yaml`, `account.yaml`, `sales.yaml`, `cases.yaml`, `marketing.yaml` (target renames `client-api.yaml` → `client.yaml`).

## Rules

```
RULE 1  Every bounded context is independent.
RULE 2  A context never imports classes from another context.
RULE 3  Domain knows no Spring/JPA/Jackson/HTTP/Oracle/Siebel.
RULE 4  Use cases know no HTTP.
RULE 5  Adapters know infrastructure.
RULE 6  Dependencies point inward (ports).
RULE 7  External DTOs never cross into domain (MapStruct).
RULE 8  Inter-context communication via explicit contracts (port) or Outbox event.
RULE 9  Do not abstract because two classes look similar, only for real complexity.
RULE 11 No shared except kernel+web.
```

Verified by `HexagonalArchitectureTest` (source-based) + `ModularityArchitectureTest` (`ApplicationModules.verify()` with `spring-modulith-starter-test` 1.4.1, skipped on Boot 4 until Modulith 2.x).

## Hexagonal Flow (e.g. sales)

```
Controller (adapter/in) -> UseCase (application) -> SalesReader (port/out) <- OracleSalesReader (adapter/out)
                                              -> SalesApiMapper -> Response
```

## Migration State

- **shared** ✅ `shared/kernel, shared/web` created (copy from `platform`), new contexts import `shared`.
- **sales** ✅ hexagonal pilot complete (`sales/domain, application, ports, adapter, mapper` + `OracleSalesReader`). Legacy `sales/SalesService, sales/web` still active; new `sales/adapter/in/web/SalesController` with `@Profile("hexagonal")` as target.
- **account/client/cases/marketing** 🚧 `domain/ports/adapter/mapper` scaffolding with `Account.java, Client.java` (target domain); migration of `*Service` → `Get*` use cases + `*Reader` ports pending (next iteration). All classes already have English Javadoc with `@author Brandon Martinez`.
- **platform** ⏳ progressive emptying; `OracleViewReader` and `SiebelGateway` still in `platform` for legacy reads, but new adapters do not use them (`sales` already uses its own `OracleSalesReader`).

## Toolchain

- JDK 25, Spring Boot 4.0.0, MapStruct 1.6.3 (`componentModel=spring`, `unmappedTargetPolicy=ERROR`), OpenAPI 7.8.0, H2 test, Modulith 1.4.1 (skipped on Boot 4).
- Tests: 48 GREEN (`MonolithSmokeTest` with H2), `HexagonalArchitectureTest` 13, JaCoCo 90% LINE (excludes `**.api.**`/`**.web.**`), Pitest 1.30 (JUnit 5.11.4 pinned). Every `src/main/java` file has English `/** ... @author Brandon Martinez */`.

## Pattern Decisions (see `docs/ARCHITECTURE_REVIEW.md`)

| Pattern | Decision |
|---|---|
| Modular Monolith, Bounded Context, Hexagonal, Package by Component | Yes |
| Use Case / Application Service | Yes (sales pilot) |
| Data Mapper (MapStruct) | Yes |
| ACL Siebel | Yes (per context) |
| CQRS | Yes only `account` |
| Domain Events (AFTER_COMMIT) + Outbox prepared | Yes |
| Generic Repository, Strategy, Template Method, Facade, Mediator, Specification | No (yet) |
