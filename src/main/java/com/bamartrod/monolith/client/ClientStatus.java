package com.bamartrod.monolith.client;
/**
 * Shared kernel primitive for ClientStatus — value object / error handling.
 *
 * @author Brandon Martinez
 */

public enum ClientStatus {
    ACTIVE,
    INACTIVE,
    BLOCKED,
    PENDING
}
