package com.bamartrod.monolith.marketing.application;

import com.bamartrod.monolith.marketing.domain.Campaign;
import com.bamartrod.monolith.marketing.ports.out.MarketingReader;
import com.bamartrod.monolith.shared.kernel.CorrelationId;
import com.bamartrod.monolith.shared.kernel.Result;
import org.springframework.stereotype.Component;
/**
 * GetCampaign — component of com.bamartrod.monolith.marketing.application bounded context.
 *
 * @author Brandon Martinez
 */

@Component

public class GetCampaign {
    private final MarketingReader reader;
    public GetCampaign(MarketingReader reader) { this.reader = reader; }
    public Result<Campaign> execute(String clientId, CorrelationId cid) {
        return Result.of(reader.find(clientId, cid), "MSOC_01", "Client " + clientId + " not found");
    }
}
