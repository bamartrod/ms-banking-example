package com.bamartrod.monolith.client.web;

import com.bamartrod.monolith.client.ClientMapper;
import com.bamartrod.monolith.client.ClientService;
import com.bamartrod.monolith.client.api.ClientApi;
import com.bamartrod.monolith.client.api.model.BelongsDocument;
import com.bamartrod.monolith.client.api.model.ClientGeneral;
import com.bamartrod.monolith.client.api.model.ClientSecureData;
import com.bamartrod.monolith.client.api.model.Destinations;
import com.bamartrod.monolith.client.api.model.Products;
import com.bamartrod.monolith.platform.web.CorrelationContext;
import com.bamartrod.monolith.platform.web.ResponseDispatcher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * REST controller for ClientApi bounded context. Handles HTTP requests and delegates to application use cases.
 *
 * @author Brandon Martinez
 */


@RestController
@RequestMapping("/api/v1/clients")

public class ClientApiController implements ClientApi {

    private final ClientService service;
    private final ClientMapper mapper;

    public ClientApiController(ClientService service, ClientMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @Override
    public ResponseEntity<ClientGeneral> getClientGeneral(String id, String xCorrelationId) {
        var cid = CorrelationContext.resolve(xCorrelationId);
        return ResponseDispatcher.dispatch(service.findGeneralInfo(id, cid), cid, mapper::toApi);
    }

    @Override
    public ResponseEntity<ClientSecureData> getClientSecureData(String id, String xCorrelationId) {
        var cid = CorrelationContext.resolve(xCorrelationId);
        return ResponseDispatcher.dispatch(service.findSecureData(id, cid), cid, mapper::toApi);
    }

    @Override
    public ResponseEntity<BelongsDocument> getClientBelongs(String id, String xCorrelationId) {
        var cid = CorrelationContext.resolve(xCorrelationId);
        return ResponseDispatcher.dispatch(service.findBelongsDocument(id, cid), cid, mapper::toApi);
    }

    @Override
    public ResponseEntity<Destinations> getClientDestinations(String id, String xCorrelationId) {
        var cid = CorrelationContext.resolve(xCorrelationId);
        return ResponseDispatcher.dispatch(service.findDestinations(id, cid), cid, mapper::toApi);
    }

    @Override
    public ResponseEntity<Products> getClientProducts(String id, String xCorrelationId) {
        var cid = CorrelationContext.resolve(xCorrelationId);
        return ResponseDispatcher.dispatch(service.findProducts(id, cid), cid, mapper::toApi);
    }
}
