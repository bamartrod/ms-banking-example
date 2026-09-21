package com.bamartrod.monolith.client.ports.out;

import com.bamartrod.monolith.client.domain.Client;
import com.bamartrod.monolith.shared.kernel.CorrelationId;
import java.util.Optional;
/**
 * Driven adapter (out) for ClientReader — reads data from persistence (Oracle view).
 *
 * @author Brandon Martinez
 */

public interface ClientReader {
    Optional<Client> find(String clientId, CorrelationId cid);
}
