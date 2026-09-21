package com.bamartrod.monolith.platform.integration.homologation;


/**
 * Homologation integration port/adapter — HomologationPort.
 *
 * @author Brandon Martinez
 */
public interface HomologationPort {
    String homologateDocumentType(String legacyType);
    String homologateProductCode(String legacyCode);
}
