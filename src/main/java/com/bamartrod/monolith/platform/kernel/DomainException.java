package com.bamartrod.monolith.platform.kernel;


/**
 * Shared kernel primitive for DomainException — value object / error handling.
 *
 * @author Brandon Martinez
 */
public final class DomainException extends RuntimeException {
    private final DomainError error;
    public DomainException(DomainError error){ super(error.formatted()); this.error=error;}
    public DomainError error(){return error;}
}
