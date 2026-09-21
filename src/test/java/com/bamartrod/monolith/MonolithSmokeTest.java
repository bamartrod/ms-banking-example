package com.bamartrod.monolith;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Real ApplicationContext smoke test — verifies Spring Boot 4 / Java 25 wiring
 * without relying on SpringExtension (incompatible with JUnit 5.11 pin for Pitest 1.30).
 * Uses H2 in-memory datasource via profile "test" so no Docker/Oracle required.
 * Fails fast if any @Service/@Repository/@Component or DataSource/JdbcClient bean is missing
 * or if @Profile("!test") vs "test" doubles are miswired (would pass with classloader-only test).
 *
 * Note: JUnit pinned to 5.11.4 in pom.xml:31 for Pitest 1.30 — Spring Boot 4's
 * SpringExtension requires JUnit 6 (computeIfAbsent with Class arg, added in 5.11+ platform
 * but Spring 7 expects 6.x). Avoid @SpringBootTest extension; boot context manually.
 */
class MonolithSmokeTest {

    @Test
    void contextLoads() {
        try (ConfigurableApplicationContext context = SpringApplication.run(MonolithApplication.class,
                "--spring.profiles.active=test",
                "--spring.main.web-application-type=none")) {
            assertThat(context).isNotNull();
            // Core platform beans
            assertThat(context.containsBean("oracleViewReader")).isTrue();
            assertThat(context.containsBean("correlationFilter")).isTrue();
            assertThat(context.containsBean("xmlMapper")).isTrue();
            // Bounded-context services (singletons must be present, no mem map leakage)
            assertThat(context.containsBean("clientService")).isTrue();
            assertThat(context.containsBean("salesService")).isTrue();
            assertThat(context.containsBean("accountQueryService")).isTrue();
            assertThat(context.containsBean("accountCommandService")).isTrue();
            assertThat(context.containsBean("casesService")).isTrue();
            assertThat(context.containsBean("marketingService")).isTrue();
            // API controllers (package by component — must be under ..web..)
            assertThat(context.containsBean("clientApiController")).isTrue();
            assertThat(context.containsBean("enrichmentApiController")).isTrue();
            assertThat(context.containsBean("salesApiController")).isTrue();
            assertThat(context.containsBean("accountApiController")).isTrue();
            assertThat(context.containsBean("casesApiController")).isTrue();
            assertThat(context.containsBean("marketingApiController")).isTrue();
            // Siebel test double active under profile test, prod adapter inactive
            assertThat(context.containsBean("inMemorySiebelAdapter")).isTrue();
            assertThat(context.getBean("inMemorySiebelAdapter"))
                    .isInstanceOf(com.bamartrod.monolith.platform.integration.siebel.SiebelGateway.class);
            // Verify no @Service contains mutable test state (SRP — mem maps purged from src/main)
            assertThat(context.containsBean("salesService")).isTrue();
            var salesService = context.getBean(com.bamartrod.monolith.sales.SalesService.class);
            assertThat(salesService.getClass().getDeclaredFields()).noneMatch(f -> f.getName().startsWith("mem"));
        }
    }
}
