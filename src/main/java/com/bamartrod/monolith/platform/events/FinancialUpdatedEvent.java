package com.bamartrod.monolith.platform.events;

import com.bamartrod.monolith.platform.kernel.CorrelationId;


/**
 * Domain event for FinancialUpdatedEvent — inter-context communication.
 *
 * @author Brandon Martinez
 */
public record FinancialUpdatedEvent(String clientId, String summary, CorrelationId correlationId) {}
