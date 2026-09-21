# AGENTS.md — Modular Monolith (Java 25 / Spring Boot 4)

## Stack & Build
- **JDK 25 required** — `maven-enforcer` fails on JDK <25 (`pom.xml:139` `requireJavaVersion [25,)`). `maven.compiler.release=25`, `java.version=25`, Spring Boot `4.0.0`.
- **Single-jar, no multi-module** — `<packaging>jar`, no `<modules>`. Root is `com.bamartrod.monolith`. `MonolithApplication` is assembly-only.
- **JUnit pinned to 5.11.4** — Boot 4 defaults to 6.0.1 which breaks Pitest 1.30 (`pom.xml:31` `junit-jupiter.version`). Do not bump without updating `pitest-junit5-plugin`.
- **All classes have English Javadoc with `@author Brandon Martinez`** — class-level `/** ... @author Brandon Martinez */` in English is required for every `src/main/java` file (106 files). Do not add Spanish comments.

## Commands (verified)
```bash
mvn test                          # 48 tests, fast, no Docker needed
mvn verify                        # full gate: test + JaCoCo 90% LINE check + reports
mvn -Dtest=SalesApiControllerTest test          # single class
mvn -Dtest=SalesApiControllerTest#testName test # single method
mvn -Dtest=HexagonalArchitectureTest test       # arch gates
mvn -Dtest=ModularityArchitectureTest test      # Modulith (skipped on Boot 4 / 1.4.1)
mvn org.pitest:pitest-maven:mutationCoverage    # mutation (thresholds 85/80, ~slow)
mvn generate-sources              # re-run OpenAPI codegen after editing src/main/resources/api/*.yaml
```

## Architecture — Hexagonal per Bounded Context (target)

```
com.bamartrod.monolith/
  shared/  # Only kernel + web (truly shared)
           #   kernel: Result, DomainError, ClientId, CorrelationId, DomainException (shared/kernel)
           #   web: CorrelationFilter, CorrelationContext, ResponseDispatcher, GlobalExceptionHandler
  sales/   # Hexagonal pilot — reference for other contexts
           #   domain: SalesTerritory, SalesUser, ProductIndicators
           #   application: GetSalesTerritory, GetSalesUser, GetProductIndicators
           #   ports/in: GetSalesTerritoryUseCase etc. / ports/out: SalesReader
           #   adapter/in/web: SalesController (hexagonal, @Profile("hexagonal"))
           #   adapter/out/persistence: OracleSalesReader (JdbcClient + ViewTarget.SALES)
           #   mapper: SalesApiMapper (MapStruct)
  client/  # Unified bounded context (clientcore + clientenrichment strangler)
  account/ # CQRS — query (GetAccount) vs command (ExtractClient)
  cases/ | marketing/  # Independent bounded contexts with per-context Siebel ACL
  platform/ # Deprecated — still contains OracleViewReader, ViewTarget, SiebelGateway for legacy contexts; empty after full migration to shared + per-context adapters
```

- **Bounded contexts must not depend on each other**, only on `shared` (and temporarily `platform` for legacy). Enforced by `HexagonalArchitectureTest.componentsShouldBeIndependent` / `ModularityArchitectureTest` (source-based + Modulith, skip if Boot 4 incompat).
- `shared` and `platform` must not import any bounded context. Domain models (`*Models.java`, `Client.java`, `ClientStatus.java`, `domain/*`) must not import `org.springframework`/`jakarta.persistence`/`jackson`.
- Controllers must live under `..web..` or `..adapter/in/web..`, services/use cases must not import `..web..`.
- **Reads via `OracleViewReader`+`JdbcClient`+`ViewTarget` (legacy) or per-context `Oracle*Reader` (target)**, not JPA. JPA (`ClientEntity`/`ClientJpaRepository`) is write-path only. `ViewTarget` is SQL-injection allow-list — never concatenate raw view names.
- **CQRS split only in `account`**: `GetAccount`/`ExtractClient` use cases. Other contexts are query-only.
- **Legacy horizontal layers** (`domain/`, `application/`, `infrastructure/`, `interfaces/` at root) were deleted 2026-09-19 — do not reintroduce.

## OpenAPI Contract-First (codegen gotcha)
- Specs: `src/main/resources/api/{account,cases,sales,marketing,enrichment,client}-api.yaml` (one per context). `client-api.yaml` is unified strangler fig for `client`; `enrichment-api.yaml` kept as legacy bridge at `/api/v1/client-enrichment`.
- Generator: `openapi-generator-maven-plugin:7.8.0`, `generatorName=spring`, `interfaceOnly=true`, `delegatePattern=true`, `useRecords=true`, `useSpringBoot3=true`.
- Output packages: `com.bamartrod.monolith.interfaces.<context>.api` for legacy contexts, **plus** `com.bamartrod.monolith.client.api` for unified client (see `pom.xml:generate-client-api`). `ClientApiController` uses `client.api.model` (no longer imports `interfaces.clientenrichment`); `EnrichmentApiController` is legacy bridge via `toLegacyApi` in `ClientMapper.java:53`.
- **Edit YAML then run `mvn generate-sources` before `mvn test`** — generated sources are not committed and missing them causes compilation errors.
- Generated `**/api/**` and `**/model/**` are excluded from JaCoCo/Pitest (`pom.xml:302` excludes). Don't chase coverage there.

## Persistence & External Integrations
- **Target:** per-context `Oracle*Reader` (`sales/adapter/out/persistence/OracleSalesReader`, `account/adapter/out/persistence/OracleAccountReader`) with `JdbcClient` + `ViewTarget` allow-list. Legacy `OracleViewReader.findById(ViewTarget, id, BiFunction)` remains for non-migrated contexts — prefer per-context reader in new code. All new services use `reader.findById` or `query(RowMapper)` — no intermediate `ClientViewRecord` → `.map` (see `ClientService.java:24`, `ClientModels.generalInfoMapper`).
- Oracle views: `CLIENT_VIEW`, `ACCOUNT_VIEW`, `SALES_VIEW`, `CASES_VIEW`, `MARKETING_VIEW`, `ENRICHMENT_VIEW` (see `ViewTarget.java:6`).
- `platform/integration/siebel/HttpSiebelAdapter` is `@Profile("!test")` prod adapter; tests use `src/test/.../platform/integration/siebel/InMemorySiebelAdapter.java`. Target moves Siebel to `cases/adapter/out/siebel` and `marketing/adapter/out/siebel` (per-context ACL).
- `application.yaml` has env-driven defaults: `DATABASE_URL`, `DATABASE_USERNAME/PASSWORD`, `SERVER_PORT`, `JPA_DDL_AUTO=validate`. `spring.jackson.serialization.write-dates-as-timestamps` was removed — Jackson 3 (Boot 4, `tools.jackson`) removed that `SerializationFeature` — don't re-add.
- `application-test.properties` in `src/test/resources` provides H2 for tests: `jdbc:h2:mem:testdb`, `driver=org.h2.Driver`, `ddl-auto=create-drop`, `dialect=H2Dialect`. `src/main/resources/application-test.properties` only sets `siebel.base-url`/`service.controller.path`.

## Testing & Verification Order
- **Correct order:** `mvn generate-sources` (if YAML changed) → `mvn test` → `mvn verify` (JaCoCo check phase `verify`).
- **JaCoCo gate: 90% LINE** on `com.bamartrod.monolith.**` **excluding** `**.api.**`, `**.api.model.**`, `**.web.**`, `**/api/**`, `**/model/**`, `**/web/**`, `**/adapter/**` (generated). Controllers/DTOs don't count — don't waste time covering them.
- **Pitest targetClasses:** `platform.kernel`, `platform.persistence`, `shared.kernel`, `sales.*`, `account.*`, `cases.*`, `marketing.*`, `client.*` (`pom.xml:327`). Thresholds: `mutationThreshold 85`, `coverageThreshold 80`.
- **Smoke test is real context now:** `MonolithSmokeTest` boots `SpringApplication` with `profiles=test` + H2 (`src/test/resources/application-test.properties`) and asserts beans `clientService`, `salesService`, `oracleViewReader`, `inMemorySiebelAdapter` etc. — **not** `@SpringBootTest` (would require JUnit 6, incompatible with pinned 5.11.4 for Pitest). `MonolithApplicationTest` remains classloader-only.
- Arch tests must stay green after any rename/move: `mvn -Dtest=HexagonalArchitectureTest,CohesionTest,AssemblyArchitectureTest,ModularityArchitectureTest test`.

## Conventions & Pitfalls
- **English Javadoc required:** every class in `src/main/java` must have `/** English description ... @author Brandon Martinez */` before the class declaration (106/106). No Spanish comments.
- Java 25 idioms enforced: `record` for DTOs/models, `sealed interface` for `Result`, `DomainError`, `ClientModels`/`SalesModels` etc., exhaustive `switch`. See `ARCHITECTURE.md` and `docs/ARCHITECTURE_REVIEW.md` for pattern decisions (MapStruct introduced, Template Method/Strategy/Facade intentionally **not** introduced).
- Controllers are thin: `CorrelationContext.resolve(header)` → `useCase.execute(id,cid)` → `ResponseDispatcher.dispatch(Result, cid, mapper::toApi)`. Don't inline mapping. New hexagonal controllers are in `adapter/in/web` and use `shared.web`.
- **MapStruct is the canonical mapper:** `SalesMapper`, `AccountMapper`, `CasesMapper`, `MarketingMapper`, `ClientMapper`, `SalesApiMapper`, `AccountApiMapper` are `@Mapper(componentModel=spring, unmappedTargetPolicy=ERROR)` — zero manual `api.setX(d.x())`. If OpenAPI adds a field, compilation fails until mapper is updated.
- Services are stateless singletons — no `memTerritory`/`ConcurrentHashMap` in `src/main` (purged; test doubles live in `src/test` as `InMemorySiebelAdapter`). `MonolithSmokeTest.java:48` asserts no `mem*` fields via reflection.
- Legacy XML: `client/web/legacy/LegacyClientXmlController` with `XmlMapper` bean (from `MonolithApplication.xmlMapper()`) and `IBRequestParser` — isolated, don't leak XML into domain. `IBRequestParser` is Jackson-only, no regex `Pattern` precompiled.

## References
- `ARCHITECTURE.md` — target hexagonal per context (sales pilot) + migration state
- `docs/MODULAR_MIGRATION.md` — applied patterns and incremental plan
- `docs/ARCHITECTURE_REVIEW.md` — decision matrix for 13 patterns
- `BACKLOG.md` — prioritized context rollout and DoD per context
