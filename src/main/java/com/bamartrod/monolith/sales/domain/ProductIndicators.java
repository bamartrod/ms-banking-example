package com.bamartrod.monolith.sales.domain;

import com.bamartrod.monolith.shared.kernel.CorrelationId;
/**
 * Domain model for ProductIndicators — immutable record representing core business concept.
 *
 * @author Brandon Martinez
 */

public record ProductIndicators(String clientId, String correlationId, String productCode, String status) {
    public static ProductIndicators of(String clientId, CorrelationId cid, String field) {
        return new ProductIndicators(clientId, cid.value(), field, "ACTIVE");
    }
}
