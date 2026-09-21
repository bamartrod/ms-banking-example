package com.bamartrod.monolith.marketing;
public sealed interface MarketingModels permits MarketingModels.Marketing, MarketingModels.Prospect, MarketingModels.Campaign {
    String clientId(); String correlationId();
/**
 * Domain models for Marketing bounded context — sealed records and factories.
 *
 * @author Brandon Martinez
 */
    record Marketing(String clientId, String correlationId, String campaignId, String segment) implements MarketingModels {}
    record Prospect(String clientId, String correlationId, String prospectId, String stage) implements MarketingModels {}
    record Campaign(String clientId, String correlationId, String campaignCode, String description) implements MarketingModels {}

    static Marketing ofMarketing(String clientId, com.bamartrod.monolith.platform.kernel.CorrelationId cid, String field) {
        return new Marketing(clientId, cid.value(), field, field);
    }
    static Prospect ofProspect(String clientId, com.bamartrod.monolith.platform.kernel.CorrelationId cid, String field) {
        return new Prospect(clientId, cid.value(), field, field);
    }
    static Campaign ofCampaign(String clientId, com.bamartrod.monolith.platform.kernel.CorrelationId cid, String field) {
        return new Campaign(clientId, cid.value(), field, field);
    }
}
