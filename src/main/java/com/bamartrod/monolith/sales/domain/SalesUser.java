package com.bamartrod.monolith.sales.domain;

import com.bamartrod.monolith.shared.kernel.CorrelationId;
/**
 * Domain model for SalesUser — immutable record representing core business concept.
 *
 * @author Brandon Martinez
 */

public record SalesUser(String clientId, String correlationId, String sellerName, String territoryId) {
    public static SalesUser of(String clientId, CorrelationId cid, String field) {
        return new SalesUser(clientId, cid.value(), field, field + "-territory");
    }
}
