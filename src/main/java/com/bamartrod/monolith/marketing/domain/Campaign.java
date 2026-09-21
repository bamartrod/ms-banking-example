package com.bamartrod.monolith.marketing.domain;
/**
 * Domain model for Campaign — immutable record representing core business concept.
 *
 * @author Brandon Martinez
 */

public record Campaign(String clientId, String correlationId, String campaignId, String segment) {}
