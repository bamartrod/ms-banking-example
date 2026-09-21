package com.bamartrod.monolith.platform.ports;


/**
 * Outbound port for ClientExistencePort — abstraction for driven adapter.
 *
 * @author Brandon Martinez
 */
public interface ClientExistencePort {
    boolean exists(String clientId);
}
