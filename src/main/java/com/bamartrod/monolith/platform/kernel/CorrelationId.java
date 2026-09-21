package com.bamartrod.monolith.platform.kernel;

import java.util.UUID;


/**
 * Shared kernel primitive for CorrelationId — value object / error handling.
 *
 * @author Brandon Martinez
 */
public record CorrelationId(String value) {
    public CorrelationId {
        if (value == null || value.isBlank()) throw new IllegalArgumentException("CorrelationId must not be blank");
    }
    public static CorrelationId generate(){ return new CorrelationId(UUID.randomUUID().toString());}
    public static CorrelationId of(String v){ return new CorrelationId(v);}
}
