package com.bamartrod.monolith.cases.web;

import com.bamartrod.monolith.cases.CasesMapper;
import com.bamartrod.monolith.cases.CasesService;
import com.bamartrod.monolith.interfaces.cases.api.CasesApi;
import com.bamartrod.monolith.platform.kernel.CorrelationId;
import com.bamartrod.monolith.platform.web.CorrelationContext;
import com.bamartrod.monolith.platform.web.ResponseDispatcher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * REST controller for CasesApi bounded context. Handles HTTP requests and delegates to application use cases.
 *
 * @author Brandon Martinez
 */

@RestController
@RequestMapping("/api/v1/cases")

public class CasesApiController implements CasesApi {
    private final CasesService service;
    private final CasesMapper mapper;
    public CasesApiController(CasesService service, CasesMapper mapper){this.service=service; this.mapper=mapper;}
    @Override public ResponseEntity<com.bamartrod.monolith.interfaces.cases.api.model.Cases> getCases(String id, String xCorrelationId){
        var cid=CorrelationContext.resolve(xCorrelationId);
        return ResponseDispatcher.dispatch(service.findCases(id,cid),cid,mapper::toApi);
    }
    @Override public ResponseEntity<com.bamartrod.monolith.interfaces.cases.api.model.CasesOrq> getCasesOrq(String id, String xCorrelationId){
        var cid=CorrelationContext.resolve(xCorrelationId);
        return ResponseDispatcher.dispatch(service.findOrq(id,cid),cid,mapper::toApi);
    }
    @Override public ResponseEntity<com.bamartrod.monolith.interfaces.cases.api.model.CasesSummary> getCasesSummary(String id, String xCorrelationId){
        var cid=CorrelationContext.resolve(xCorrelationId);
        return ResponseDispatcher.dispatch(service.findSummary(id,cid),cid,mapper::toApi);
    }
}
