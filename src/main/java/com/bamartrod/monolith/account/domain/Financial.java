package com.bamartrod.monolith.account.domain;

import com.bamartrod.monolith.shared.kernel.CorrelationId;
/**
 * Domain model for Financial — immutable record representing core business concept.
 *
 * @author Brandon Martinez
 */

public record Financial(String clientId, String correlationId, String totalBalance, String currency) {
    public static Financial of(String clientId, CorrelationId cid, String field) {
        return new Financial(clientId, cid.value(), field, "COP");
    }
}
