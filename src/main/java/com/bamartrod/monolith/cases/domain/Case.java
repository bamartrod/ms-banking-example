package com.bamartrod.monolith.cases.domain;
/**
 * Domain model for Case — immutable record representing core business concept.
 *
 * @author Brandon Martinez
 */

public record Case(String clientId, String correlationId, String caseId, String status) {}
