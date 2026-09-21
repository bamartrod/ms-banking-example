# Modular Migration — Target Architecture (English, 2026-09-21)

> All `src/main/java` classes have English Javadoc with `@author Brandon Martinez`. `platform` is deprecated in favor of `shared`.

## Current State (Package by Component + Hexagonal Pilot)

```
com.bamartrod.monolith/
├── shared/                         # Only truly shared
│   ├── kernel/ Result, DomainError, ClientId, CorrelationId, DomainException (shared/kernel, English)
│   └── web/ CorrelationContext, CorrelationFilter, ResponseDispatcher, GlobalExceptionHandler
│
├── sales/                          # Hexagonal pilot
│   ├── domain/ SalesTerritory, SalesUser, ProductIndicators
│   ├── application/ GetSalesTerritory, GetSalesUser, GetProductIndicators
│   ├── ports/in+out  GetSalesTerritoryUseCase, SalesReader
│   ├── adapter/in/web  SalesController (@Profile("hexagonal"))
│   │            out/persistence OracleSalesReader (JdbcClient + ViewTarget.SALES)
│   └── mapper/ SalesApiMapper (MapStruct)
│
├── client/  # Strangler (clientcore + clientenrichment)
├── account/ # CQRS (GetAccount vs ExtractClient via ClientExistencePort)
├── cases/ | marketing/  # Per-context Siebel ACL (target)
└── platform/ # Deprecated — still contains OracleViewReader, ViewTarget, SiebelGateway for legacy; will be removed after per-context migration
```

`src/main/resources/api/` → `client-api.yaml` (unified), `account-api.yaml`, `sales-api.yaml`, `cases-api.yaml`, `marketing-api.yaml`, `enrichment-api.yaml` (legacy).

## Patterns Applied

1. **Package by Component + Hexagonal per Context:** `sales` demonstrates `domain -> ports -> application -> adapter`. `shared` only `kernel+web`.
2. **Result/Either + ResponseDispatcher:** `shared.kernel.Result` sealed, `ResponseDispatcher.dispatch(Result, cid, mapper::toApi)` — exhaustive `switch`.
3. **Per-Context Persistence:** `OracleSalesReader`, `OracleAccountReader` own `ViewTarget.SALES/ACCOUNT` + `RowMapper` (`ClientModels.generalInfoMapper`). No `SELECT *` with `try/catch`.
4. **MapStruct Mappers:** `SalesApiMapper`, `AccountApiMapper`, `CasesMapper`, `ClientMapper` (`componentModel=spring`, `unmappedTargetPolicy=ERROR`). Zero manual `setX`.
5. **ACL per Context:** `cases/adapter/out/siebel` and `marketing/adapter/out/siebel` separate (no shared `SiebelGateway` in future).
6. **CQRS:** only `account` (`GetAccount` vs `ExtractClient` with `ClientExistencePort` + `ApplicationEventPublisher` + `@TransactionalEventListener(AFTER_COMMIT)`).

## Next Steps

- Migrate remaining `*Service` → `Get*` use cases for `client, cases, marketing` (same pattern as `sales` pilot).
- Delete `platform/persistence` and `platform/integration` after all contexts own their adapters; `platform` will be removed, only `shared` remains.
- Keep `HexagonalArchitectureTest` source-based + `ModularityArchitectureTest` (skipped until Modulith 2.x for Boot 4).

## Verification

```bash
mvn generate-sources
mvn test          # 48 tests, H2
mvn verify        # JaCoCo 90%
```

English Javadoc enforced: `grep -L "@author" src/main/java -r --include="*.java"` must be 0.
