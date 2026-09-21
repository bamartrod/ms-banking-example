package com.bamartrod.monolith.client.ports.out;

import com.bamartrod.monolith.client.domain.Client;
/**
 * Driven adapter (out) for ClientWriter — writes data to persistence.
 *
 * @author Brandon Martinez
 */

public interface ClientWriter {
    void save(Client client);
}
