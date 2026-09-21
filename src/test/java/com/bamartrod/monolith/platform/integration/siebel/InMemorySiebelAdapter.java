package com.bamartrod.monolith.platform.integration.siebel;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * Adaptador determinista para tests — datos semilla in-memory.
 * Activo solo bajo perfil "test".
 */
@Component
@Profile("test")
public class InMemorySiebelAdapter implements SiebelGateway {

    private final Map<String, SiebelRecord> store = new ConcurrentHashMap<>();

    public InMemorySiebelAdapter() {
        store.put("123", new SiebelRecord("123", "CASE-123", "OPEN", "{}"));
        store.put("seed-marketing", new SiebelRecord("seed-marketing", "MKT-001", "ACTIVE", "{}"));
    }

    public void seed(String clientId, SiebelRecord record) {
        store.put(clientId, record);
    }

    @Override
    public Optional<SiebelRecord> fetchCase(String clientId) {
        return Optional.ofNullable(store.get(clientId));
    }

    @Override
    public Optional<SiebelRecord> fetchMarketing(String clientId) {
        return Optional.ofNullable(store.get(clientId));
    }
}
