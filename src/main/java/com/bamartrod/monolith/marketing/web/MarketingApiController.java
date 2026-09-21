package com.bamartrod.monolith.marketing.web;

import com.bamartrod.monolith.interfaces.marketing.api.MarketingApi;
import com.bamartrod.monolith.marketing.MarketingMapper;
import com.bamartrod.monolith.marketing.MarketingService;
import com.bamartrod.monolith.platform.kernel.CorrelationId;
import com.bamartrod.monolith.platform.web.CorrelationContext;
import com.bamartrod.monolith.platform.web.ResponseDispatcher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * REST controller for MarketingApi bounded context. Handles HTTP requests and delegates to application use cases.
 *
 * @author Brandon Martinez
 */

@RestController
@RequestMapping("/api/v1/marketing")

public class MarketingApiController implements MarketingApi {
    private final MarketingService service;
    private final MarketingMapper mapper;
    public MarketingApiController(MarketingService service, MarketingMapper mapper){this.service=service; this.mapper=mapper;}
    @Override public ResponseEntity<com.bamartrod.monolith.interfaces.marketing.api.model.Marketing> getMarketing(String id, String xCorrelationId){
        var cid=CorrelationContext.resolve(xCorrelationId);
        return ResponseDispatcher.dispatch(service.findMarketing(id,cid),cid,mapper::toApi);
    }
    @Override public ResponseEntity<com.bamartrod.monolith.interfaces.marketing.api.model.Prospect> getProspect(String id, String xCorrelationId){
        var cid=CorrelationContext.resolve(xCorrelationId);
        return ResponseDispatcher.dispatch(service.findProspect(id,cid),cid,mapper::toApi);
    }
    @Override public ResponseEntity<com.bamartrod.monolith.interfaces.marketing.api.model.Campaign> getCampaign(String id, String xCorrelationId){
        var cid=CorrelationContext.resolve(xCorrelationId);
        return ResponseDispatcher.dispatch(service.findCampaign(id,cid),cid,mapper::toApi);
    }
}
