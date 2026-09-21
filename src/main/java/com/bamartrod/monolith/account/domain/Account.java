package com.bamartrod.monolith.account.domain;

import com.bamartrod.monolith.shared.kernel.CorrelationId;
/**
 * Domain model for Account — immutable record representing core business concept.
 *
 * @author Brandon Martinez
 */

public record Account(String clientId, String correlationId, String accountNumber, String accountType) {
    public static Account of(String clientId, CorrelationId cid, String number, String type) {
        return new Account(clientId, cid.value(), number, type);
    }
}
