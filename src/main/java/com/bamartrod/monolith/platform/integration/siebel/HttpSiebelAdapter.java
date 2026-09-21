package com.bamartrod.monolith.platform.integration.siebel;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
/**
 * Anti-corruption layer for Siebel integration — HttpSiebelAdapter.
 *
 * @author Brandon Martinez
 */


@Component
@Profile("!test")

public class HttpSiebelAdapter implements SiebelGateway {

    private static final Logger log = LoggerFactory.getLogger(HttpSiebelAdapter.class);
    private final RestClient restClient;

    public HttpSiebelAdapter(RestClient.Builder builder,
                             @Value("${siebel.base-url:http://siebel.local}") String baseUrl) {
        this.restClient = builder.baseUrl(baseUrl).build();
    }

    @Override
    public Optional<SiebelRecord> fetchCase(String clientId) {
        try {
            var record = restClient.get()
                    .uri("/cases/{id}", clientId)
                    .retrieve()
                    .body(SiebelRecord.class);
            return Optional.ofNullable(record);
        } catch (Exception e) {
            log.warn("Siebel fetchCase failed for {}: {}", clientId, e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Optional<SiebelRecord> fetchMarketing(String clientId) {
        try {
            var record = restClient.get()
                    .uri("/marketing/{id}", clientId)
                    .retrieve()
                    .body(SiebelRecord.class);
            return Optional.ofNullable(record);
        } catch (Exception e) {
            log.warn("Siebel fetchMarketing failed for {}: {}", clientId, e.getMessage());
            return Optional.empty();
        }
    }
}
