package com.bamartrod.monolith.sales.web;

import com.bamartrod.monolith.interfaces.sales.api.SalesApi;
import com.bamartrod.monolith.interfaces.sales.api.model.ProductIndicators;
import com.bamartrod.monolith.interfaces.sales.api.model.SalesTerritory;
import com.bamartrod.monolith.interfaces.sales.api.model.SalesUser;
import com.bamartrod.monolith.platform.kernel.CorrelationId;
import com.bamartrod.monolith.platform.web.CorrelationContext;
import com.bamartrod.monolith.platform.web.ResponseDispatcher;
import com.bamartrod.monolith.sales.SalesMapper;
import com.bamartrod.monolith.sales.SalesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * REST controller for SalesApi bounded context. Handles HTTP requests and delegates to application use cases.
 *
 * @author Brandon Martinez
 */

@RestController
@RequestMapping("/api/v1/sales")

public class SalesApiController implements SalesApi {
    private final SalesService service;
    private final SalesMapper mapper;
    public SalesApiController(SalesService service, SalesMapper mapper){ this.service=service; this.mapper=mapper;}
    @Override public ResponseEntity<SalesTerritory> getSalesTerritory(String id, String xCorrelationId){
        var cid=CorrelationContext.resolve(xCorrelationId);
        return ResponseDispatcher.dispatch(service.findTerritory(id,cid),cid,mapper::toApi);
    }
    @Override public ResponseEntity<SalesUser> getSalesUser(String id, String xCorrelationId){
        var cid=CorrelationContext.resolve(xCorrelationId);
        return ResponseDispatcher.dispatch(service.findUser(id,cid),cid,mapper::toApi);
    }
    @Override public ResponseEntity<ProductIndicators> getProductIndicators(String id, String xCorrelationId){
        var cid=CorrelationContext.resolve(xCorrelationId);
        return ResponseDispatcher.dispatch(service.findIndicators(id,cid),cid,mapper::toApi);
    }
}
