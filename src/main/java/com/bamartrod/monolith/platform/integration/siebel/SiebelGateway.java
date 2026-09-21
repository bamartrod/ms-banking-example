package com.bamartrod.monolith.platform.integration.siebel;

import java.util.Optional;


/**
 * Anti-corruption layer for Siebel integration — SiebelGateway.
 *
 * @author Brandon Martinez
 */
public interface SiebelGateway {
    Optional<SiebelRecord> fetchCase(String clientId);
    Optional<SiebelRecord> fetchMarketing(String clientId);
}
