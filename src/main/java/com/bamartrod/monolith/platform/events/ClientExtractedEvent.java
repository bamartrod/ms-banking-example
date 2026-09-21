package com.bamartrod.monolith.platform.events;

import com.bamartrod.monolith.platform.kernel.CorrelationId;


/**
 * Domain event for ClientExtractedEvent — inter-context communication.
 *
 * @author Brandon Martinez
 */
public record ClientExtractedEvent(String clientId, CorrelationId correlationId) {}
