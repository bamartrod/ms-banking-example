package com.bamartrod.monolith.platform.events;

import com.bamartrod.monolith.platform.kernel.CorrelationId;


/**
 * Domain event for ClientExtractionFailedEvent — inter-context communication.
 *
 * @author Brandon Martinez
 */
public record ClientExtractionFailedEvent(String clientId, CorrelationId correlationId, String reason) {}
