package com.bamartrod.monolith.sales.domain;

import com.bamartrod.monolith.shared.kernel.CorrelationId;


/**
 * Domain model for SalesTerritory — immutable record representing core business concept.
 *
 * @author Brandon Martinez
 */
public record SalesTerritory(String clientId, String correlationId, String territoryDescr, String industryDescr) {
    public static SalesTerritory of(String clientId, CorrelationId cid, String field) {
        return new SalesTerritory(clientId, cid.value(), field, field + "-industry");
    }
}
