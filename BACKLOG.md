# Backlog — Modular Monolith `com.bamartrod.monolith` (2026-09-21)

> Live state — 106 files, ~5,100 lines, `mvn verify` green (48 tests, JaCoCo 90%, H2 smoke). Real package `com.bamartrod.monolith` (not `com.example.monolith`). Horizontal layers `domain/application/infrastructure/interfaces` deleted 2026-09-19. Every `src/main/java` class has English Javadoc with `@author Brandon Martinez`.

## Objective

Consolidate 59 microservices into 5 bounded contexts + `shared` under Package by Component, with per-context `Oracle*Reader` + `ViewTarget` (JdbcClient) for reads and JPA only for writes (`CLIENT`). JaCoCo gate 90% LINE, Pitest 85/80. English Javadoc enforced.

## Done — Evidence 2026-09-21

- **client (strangler)** — `ClientService` via `query(RowMapper)` + `findById`, `ClientModels.generalInfoMapper` canonical, `ClientApiController implements ClientApi` (client-api.yaml) + `EnrichmentApiController` legacy, `IBRequestParser` Jackson-only (no regex), `ClientEventListener` with `@TransactionalEventListener(AFTER_COMMIT)` + `ClientExistencePort` (platform → shared).
- **sales (hexagonal pilot)** — `sales/domain` (SalesTerritory, SalesUser, ProductIndicators), `application/GetSalesTerritory` use cases, `ports/in+out`, `adapter/out/persistence/OracleSalesReader` (own ViewTarget.SALES), `mapper/SalesApiMapper` (MapStruct), `adapter/in/web/SalesController` (`@Profile("hexagonal")` target). Legacy `SalesService` still active for compatibility.
- **account (CQRS)** — `account/domain` (Account, Expedient, Financial), `application/query` (`GetAccount`) + `command` (`ExtractClient` via `ClientExistencePort` + `ApplicationEventPublisher`), `ports/out/AccountReader`, `adapter/out/persistence/OracleAccountReader`, `mapper/AccountApiMapper` (MapStruct).
- **cases/marketing** — `cases/domain/Case`, `marketing/domain/Campaign` + `ports/out` + `adapter/out/persistence` per-context readers; `shared` only `kernel+web`.
- **platform** — deprecated, remains for legacy reads (`OracleViewReader`, `ViewTarget`, `SiebelGateway`) until full per-context migration; `shared/kernel, shared/web` are the new canonical shared kernel (English Javadoc).

## Next Steps (prioritized)

1. **Structured Oracle views** — Migrate `CLIENT_VIEW` from `(table_id, field)` KV to columns `document_type, document_number, status` in real DDL; remove `field` fallback in `ClientService` when view is migrated.
2. **Complete per-context migration** — Replace remaining `*Service` with `Get*` use cases for `client, cases, marketing`; delete `platform/persistence` and `platform/integration` after all contexts use their own `Oracle*Reader`/`Siebel*Adapter`.
3. **Spring Modulith formal** — `spring-modulith-starter-test` 1.4.1 already added, skipped on Boot 4 until Modulith 2.x for `ApplicationModules.verify()` + `Documenter` C4 docs. Source-based `HexagonalArchitectureTest` currently covers slice isolation.
4. **Observability** — `management.endpoints` + `micrometer-observation` already present; add `FinancialUpdatedEvent` to transactional outbox if guaranteed delivery is required (currently audit log via `FinancialAuditListener`).

## Verification

```bash
mvn generate-sources   # after editing src/main/resources/api/*.yaml
mvn test               # 48 tests, H2
mvn verify             # + JaCoCo 90% check
mvn org.pitest:pitest-maven:mutationCoverage  # 85/80
```

Every `src/main/java` class must keep English `/** ... @author Brandon Martinez */` — verified by `grep -L "@author"`.

## Definition of Done per Context (CSAS-013)

- Hexagonal: `domain` does not import `infrastructure/interfaces` (verified).
- `record`/`sealed` + `RowMapper` canonical without `try/catch (SQLException)`.
- `application.yaml` env-driven (`${DATABASE_URL}` etc.), no hardcoded hosts.
- Tests per use case + `X-Correlation-Id` header.
- MapStruct `unmappedTargetPolicy=ERROR` — OpenAPI change fails compilation.
