package com.bamartrod.monolith.client;

import com.bamartrod.monolith.platform.kernel.ClientId;


/**
 * Domain model for Client — immutable record representing core business concept.
 *
 * @author Brandon Martinez
 */
public record Client(
        ClientId id,
        String documentType,
        String documentNumber,
        ClientStatus status
) {
    public Client {
        if (id == null) throw new IllegalArgumentException("ClientId cannot be null");
        if (documentNumber == null || documentNumber.isBlank()) throw new IllegalArgumentException("Document number required");
        if (status == null) status = ClientStatus.ACTIVE;
    }

    public boolean isActive() {
        return status == ClientStatus.ACTIVE;
    }

    public static Client of(String rawId, String docType, String docNumber, ClientStatus status) {
        return new Client(ClientId.of(rawId), docType, docNumber, status);
    }
}
