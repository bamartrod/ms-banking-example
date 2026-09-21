package com.bamartrod.monolith.client.web;

import com.bamartrod.monolith.client.ClientMapper;
import com.bamartrod.monolith.client.ClientService;
import com.bamartrod.monolith.interfaces.clientenrichment.api.EnrichmentApi;
import com.bamartrod.monolith.platform.web.CorrelationContext;
import com.bamartrod.monolith.platform.web.ResponseDispatcher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * REST controller for EnrichmentApi bounded context. Handles HTTP requests and delegates to application use cases.
 *
 * @author Brandon Martinez
 */


@RestController
@RequestMapping("/api/v1/client-enrichment")

public class EnrichmentApiController implements EnrichmentApi {

    private final ClientService service;
    private final ClientMapper mapper;

    public EnrichmentApiController(ClientService service, ClientMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @Override
    public ResponseEntity<com.bamartrod.monolith.interfaces.clientenrichment.api.model.BelongsDocument> getBelongsDocument(String id, String xCorrelationId) {
        var cid = CorrelationContext.resolve(xCorrelationId);
        return ResponseDispatcher.dispatch(service.findBelongsDocument(id, cid), cid, mapper::toLegacyApi);
    }

    @Override
    public ResponseEntity<com.bamartrod.monolith.interfaces.clientenrichment.api.model.Destinations> getDestinations(String id, String xCorrelationId) {
        var cid = CorrelationContext.resolve(xCorrelationId);
        return ResponseDispatcher.dispatch(service.findDestinations(id, cid), cid, mapper::toLegacyApi);
    }

    @Override
    public ResponseEntity<com.bamartrod.monolith.interfaces.clientenrichment.api.model.Products> getProducts(String id, String xCorrelationId) {
        var cid = CorrelationContext.resolve(xCorrelationId);
        return ResponseDispatcher.dispatch(service.findProducts(id, cid), cid, mapper::toLegacyApi);
    }
}
