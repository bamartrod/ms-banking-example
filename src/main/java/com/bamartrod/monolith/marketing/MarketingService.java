package com.bamartrod.monolith.marketing;

import com.bamartrod.monolith.platform.kernel.CorrelationId;
import com.bamartrod.monolith.platform.kernel.Result;
import com.bamartrod.monolith.platform.persistence.OracleViewReader;
import com.bamartrod.monolith.platform.persistence.ViewTarget;
import org.springframework.stereotype.Service;
/**
 * Application service for Marketing bounded context. Orchestrates domain logic and persistence.
 *
 * @author Brandon Martinez
 */

@Service

public class MarketingService {
    private final OracleViewReader reader;
    public MarketingService(OracleViewReader reader){this.reader=reader;}
    public Result<MarketingModels.Marketing> findMarketing(String id, CorrelationId cid){
        return Result.of(reader.findById(ViewTarget.MARKETING, id, (dbId, field) -> MarketingModels.ofMarketing(id, cid, field)), "MSOC_01","Client "+id+" not found");
    }
    public Result<MarketingModels.Prospect> findProspect(String id, CorrelationId cid){
        return Result.of(reader.findById(ViewTarget.MARKETING, id, (dbId, field) -> MarketingModels.ofProspect(id, cid, field)), "MSOC_01","Client "+id+" not found");
    }
    public Result<MarketingModels.Campaign> findCampaign(String id, CorrelationId cid){
        return Result.of(reader.findById(ViewTarget.MARKETING, id, (dbId, field) -> MarketingModels.ofCampaign(id, cid, field)), "MSOC_01","Client "+id+" not found");
    }
}
