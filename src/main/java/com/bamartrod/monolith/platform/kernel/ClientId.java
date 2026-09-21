package com.bamartrod.monolith.platform.kernel;


/**
 * Shared kernel primitive for ClientId — value object / error handling.
 *
 * @author Brandon Martinez
 */
public record ClientId(String value) {
    public ClientId {
        if (value == null || value.isBlank()) throw new IllegalArgumentException("ClientId must not be blank");
    }
    public static ClientId of(String value){ return new ClientId(value);}
}
