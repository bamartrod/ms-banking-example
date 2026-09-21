package com.bamartrod.monolith.client;

/**
 * Canonical client models — unified bounded context (clientcore + clientenrichment).
 * Sealed interface consolidates GeneralInfo, SecureData, Belongs, Destinations, Products.
 * Factory methods centralize the single-column view mapping (field → typed attributes)
 * so that the SQL projection (table_id, field) never leaks as r.data() duplication in services.
 */
public sealed interface ClientModels
        permits ClientModels.GeneralInfo, ClientModels.SecureData, ClientModels.Belongs, ClientModels.Destinations, ClientModels.Products {

    String clientId();
    String correlationId();
/**
 * Domain models for Client bounded context — sealed records and factories.
 *
 * @author Brandon Martinez
 */

    record GeneralInfo(String clientId, String correlationId, String documentType, String documentNumber, String status) implements ClientModels {}
    record SecureData(String clientId, String correlationId, String maskedAccount, String status) implements ClientModels {}
    record Belongs(String clientId, String correlationId, String documentType, String belongsFlag) implements ClientModels {}
    record Destinations(String clientId, String correlationId, String destinationCode, boolean active) implements ClientModels {}
    record Products(String clientId, String correlationId, String productCode, String status) implements ClientModels {}

    static GeneralInfo ofGeneralInfo(String clientId, com.bamartrod.monolith.platform.kernel.CorrelationId cid, String field) {
        return new GeneralInfo(clientId, cid.value(), field, field, ClientStatus.ACTIVE.name());
    }
    /** Rich mapping — column-specific, avoids field duplication for documentType/documentNumber. */
    static GeneralInfo ofGeneralInfo(String clientId, com.bamartrod.monolith.platform.kernel.CorrelationId cid, String documentType, String documentNumber) {
        return new GeneralInfo(clientId, cid.value(), documentType, documentNumber, ClientStatus.ACTIVE.name());
    }

    /**
     * Canonical RowMapper for CLIENT_VIEW structured projection.
     * Contrato: columnas document_type, document_number, status (SELECT *). Sin inspección por excepciones.
     * Para vistas KV legacy usar findById(BiFunction); para vistas estructuradas usar este mapper con query().
     */
    static org.springframework.jdbc.core.RowMapper<GeneralInfo> generalInfoMapper(String clientId, com.bamartrod.monolith.platform.kernel.CorrelationId cid) {
        return (rs, rowNum) -> new GeneralInfo(
                clientId,
                cid.value(),
                rs.getString("document_type"),
                rs.getString("document_number"),
                rs.getString("status") != null ? rs.getString("status") : ClientStatus.ACTIVE.name()
        );
    }
    static SecureData ofSecureData(String clientId, com.bamartrod.monolith.platform.kernel.CorrelationId cid, String field) {
        return new SecureData(clientId, cid.value(), field, ClientStatus.ACTIVE.name());
    }
    static Belongs ofBelongs(String clientId, com.bamartrod.monolith.platform.kernel.CorrelationId cid, String field) {
        return new Belongs(clientId, cid.value(), field, field);
    }
    static Destinations ofDestinations(String clientId, com.bamartrod.monolith.platform.kernel.CorrelationId cid, String field) {
        return new Destinations(clientId, cid.value(), field, true);
    }
    static Products ofProducts(String clientId, com.bamartrod.monolith.platform.kernel.CorrelationId cid, String field) {
        return new Products(clientId, cid.value(), field, "ACTIVE");
    }
    static com.bamartrod.monolith.client.Client ofClient(String clientId, String field) {
        return new com.bamartrod.monolith.client.Client(com.bamartrod.monolith.platform.kernel.ClientId.of(clientId), field, field, ClientStatus.ACTIVE);
    }
}
