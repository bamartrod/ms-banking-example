package com.bamartrod.monolith.platform.integration.homologation;

import org.springframework.stereotype.Component;

/**
 * Final DrivenAdapter — in-memory homologation table for tests, DB-backed in prod.
 * @author Brandon Martinez : https://github.com/bamartrod - https://www.linkedin.com/in/bamartrod
 */
@Component
public final class HomologationAdapter implements HomologationPort {

    @Override
    public String homologateDocumentType(String legacyType) {
        return switch (legacyType == null ? "" : legacyType.toUpperCase()) {
            case "C", "CC" -> "C";
            case "N", "NIT" -> "N";
            case "P", "PAS" -> "P";
            default -> legacyType;
        };
    }

    @Override
    public String homologateProductCode(String legacyCode) {
        return legacyCode == null ? null : legacyCode.trim().toUpperCase();
    }
}
