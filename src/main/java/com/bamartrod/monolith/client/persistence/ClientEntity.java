package com.bamartrod.monolith.client.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
/**
 * JPA entity for ClientEntity — maps to database table for transactional writes.
 *
 * @author Brandon Martinez
 */


@Entity
@Table(name = "CLIENT")

public class ClientEntity {

    @Id
    @Column(name = "CLIENT_ID")
    private String clientId;

    @Column(name = "DOCUMENT_TYPE")
    private String documentType;

    @Column(name = "DOCUMENT_NUMBER")
    private String documentNumber;

    @Column(name = "STATUS")
    private String status;

    protected ClientEntity() {}

    public ClientEntity(String clientId, String documentType, String documentNumber, String status) {
        this.clientId = clientId;
        this.documentType = documentType;
        this.documentNumber = documentNumber;
        this.status = status;
    }

    public String getClientId() { return clientId; }
    public String getDocumentType() { return documentType; }
    public String getDocumentNumber() { return documentNumber; }
    public String getStatus() { return status; }

    public void markAsExtracted() {
        this.status = "EXTRACTED";
    }
}
