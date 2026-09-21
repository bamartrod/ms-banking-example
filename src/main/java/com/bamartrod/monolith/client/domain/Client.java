package com.bamartrod.monolith.client.domain;

import com.bamartrod.monolith.shared.kernel.CorrelationId;


/**
 * Domain model for Client — immutable record representing core business concept.
 *
 * @author Brandon Martinez
 */
public record Client(String id, String documentType, String documentNumber, String status, String correlationId) {}
