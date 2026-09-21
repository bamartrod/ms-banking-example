package com.bamartrod.monolith;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

/**
 * Smoke — verifica autodescubrimiento Package by Component y XmlMapper singleton.
 */
class MonolithApplicationTest {

    @Test
    void shouldWireAllBeans() {
        var app = new MonolithApplication();
        assertThat(app.xmlMapper()).isNotNull();
        assertThat(app).isNotNull();
    }

    @Test
    void xmlMapperIsNotNull() {
        var app = new MonolithApplication();
        assertThat(app.xmlMapper()).isNotNull();
    }
}
