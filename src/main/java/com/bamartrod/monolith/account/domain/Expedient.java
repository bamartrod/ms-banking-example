package com.bamartrod.monolith.account.domain;

import com.bamartrod.monolith.shared.kernel.CorrelationId;
/**
 * Domain model for Expedient — immutable record representing core business concept.
 *
 * @author Brandon Martinez
 */

public record Expedient(String clientId, String correlationId, String expedientId, String status) {
    public static Expedient of(String clientId, CorrelationId cid, String field) {
        return new Expedient(clientId, cid.value(), field, "OPEN");
    }
}
